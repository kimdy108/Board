<template>
  <div class="text-left text-black">
    <div class="ml-5 text-6xl p-10">자유게시판 수정</div>
  </div>
  <div class="card flex">
    <div class="flex flex-col gap-2 text-left ml-16">
      <label for="otherTitle" class="text-3xl text-black">제목</label>
      <InputText
        id="otherTitle"
        v-model="otherTitleValue"
        size="large"
        style="width: 1400px; height: 50px; font-size: 20px"
      />
    </div>
  </div>
  <div class="card flex">
    <div class="flex flex-col gap-2 text-left ml-16">
      <label for="otherContent" class="text-3xl text-black mt-5">내용</label>
      <Textarea
        id="otherContent"
        v-model="otherContentValue"
        rows="5"
        cols="30"
        style="width: 1400px; height: 300px; font-size: 20px"
      />
    </div>
  </div>
  <hr class="mt-16 ml-16 mr-16 mb-10" />
  <div class="text-left mr-16 flex justify-end">
    <Button label="뒤로가기" size="large" class="mr-2" @click="goOtherView" />
    <Button label="수정" size="large" severity="info" class="mr-2" @click="otherEditFunction" />
  </div>
</template>

<script setup>
import InputText from 'primevue/inputtext'
import Textarea from 'primevue/textarea'
import Button from 'primevue/button'
import ApiService from '@/services/ApiService'
import { useRouter } from 'vue-router'
import { onMounted, ref } from 'vue'

const props = defineProps({
  boardGuid: String
})

const router = useRouter()

const otherTitleValue = ref('')
const otherContentValue = ref('')

const goOtherView = () => {
  const boardGuid = props.boardGuid
  router.push({ name: 'OtherViewPage', params: { boardGuid } }).catch(() => {
    console.log('OtherViewPageError')
  })
}

const otherEditFunction = () => {
  if (otherTitleValue.value === '') alert('제목을 입력해주세요.')
  else if (otherContentValue.value === '') alert('내용을 입력해주세요.')
  else otherEditApi()
}

const getOtherApi = async () => {
  const result = await ApiService.requestAPI({
    headers: { accept: 'application/json' },
    method: 'GET',
    url: '/board/free/search',
    params: {
      freeGuid: props.boardGuid
    }
  })
  otherTitleValue.value = result.boardTitle
  otherContentValue.value = result.boardContent
}

const otherEditApi = async () => {
  const result = await ApiService.requestAPI({
    headers: { accept: 'application/json' },
    method: 'PUT',
    url: '/board/free/update',
    data: {
      boardGuid: props.boardGuid,
      boardTitle: otherTitleValue.value,
      boardContent: otherContentValue.value
    }
  })
  if (result === 'success') {
    alert('수정이 완료되었습니다.')
    goOtherView()
  }
}

onMounted(() => {
  getOtherApi()
})
</script>

<style lang="scss" scoped></style>

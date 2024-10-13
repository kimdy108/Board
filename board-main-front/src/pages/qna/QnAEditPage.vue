<template>
  <div class="text-left text-black">
    <div class="ml-5 text-6xl p-10">자주하는 문의 수정</div>
  </div>
  <div class="card flex">
    <div class="flex flex-col gap-2 text-left ml-16">
      <label for="qnaTitle" class="text-3xl text-black">제목</label>
      <InputText
        id="qnaTitle"
        v-model="qnaTitleValue"
        size="large"
        style="width: 1400px; height: 50px; font-size: 20px"
      />
    </div>
  </div>
  <div class="card flex">
    <div class="flex flex-col gap-2 text-left ml-16">
      <label for="qnaContent" class="text-3xl text-black mt-5">내용</label>
      <Textarea
        id="qnaContent"
        v-model="qnaContentValue"
        rows="5"
        cols="30"
        style="width: 1400px; height: 300px; font-size: 20px"
      />
    </div>
  </div>
  <hr class="mt-16 ml-16 mr-16 mb-10" />
  <div class="text-left mr-16 flex justify-end">
    <Button label="뒤로가기" size="large" class="mr-2" @click="goQnAView" />
    <Button label="수정" size="large" severity="info" class="mr-2" @click="qnaEditFunction" />
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
  qnaGuid: String
})

const router = useRouter()

const qnaTitleValue = ref('')
const qnaContentValue = ref('')

const goQnAView = () => {
  const qnaGuid = props.qnaGuid
  router.push({ name: 'QnAViewPage', params: { qnaGuid } }).catch(() => {
    console.log('QnAViewPageError')
  })
}

const qnaEditFunction = () => {
  if (qnaTitleValue.value === '') alert('제목을 입력해주세요.')
  else if (qnaContentValue.value === '') alert('내용을 입력해주세요.')
  else qnaEditApi()
}

const getQnAApi = async () => {
  const result = await ApiService.requestAPI({
    headers: { accept: 'application/json' },
    method: 'GET',
    url: '/qna/search',
    params: {
      qnaGuid: props.qnaGuid
    }
  })
  qnaTitleValue.value = result.qnaTitle
  qnaContentValue.value = result.qnaContent
}

const qnaEditApi = async () => {
  const result = await ApiService.requestAPI({
    headers: { accept: 'application/json' },
    method: 'PUT',
    url: '/qna/update',
    data: {
      qnaGuid: props.qnaGuid,
      qnaTitle: qnaTitleValue.value,
      qnaContent: qnaContentValue.value
    }
  })
  if (result === 'success') {
    alert('수정이 완료되었습니다.')
    goQnAView()
  }
}

onMounted(() => {
  getQnAApi()
})
</script>

<style lang="scss" scoped></style>

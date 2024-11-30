<template>
  <div class="text-left text-black">
    <div class="ml-5 text-6xl p-10">개발 / 스택 수정</div>
  </div>
  <div class="card flex">
    <div class="flex flex-col gap-2 text-left ml-16">
      <label for="devAndStackTitle" class="text-3xl text-black">제목</label>
      <InputText
        id="devAndStackTitle"
        v-model="devAndStackTitleValue"
        size="large"
        style="width: 1400px; height: 50px; font-size: 20px"
      />
    </div>
  </div>
  <div class="card flex">
    <div class="flex flex-col gap-2 text-left ml-16">
      <label for="devAndStackContent" class="text-3xl text-black mt-5">내용</label>
      <Textarea
        id="devAndStackContent"
        v-model="devAndStackContentValue"
        rows="5"
        cols="30"
        style="width: 1400px; height: 300px; font-size: 20px"
      />
    </div>
  </div>
  <hr class="mt-16 ml-16 mr-16 mb-10" />
  <div class="text-left mr-16 flex justify-end">
    <Button label="뒤로가기" size="large" class="mr-2" @click="goDevAndStackView" />
    <Button
      label="수정"
      size="large"
      severity="info"
      class="mr-2"
      @click="devAndStackEditFunction"
    />
  </div>
</template>

<script setup>
import InputText from 'primevue/inputtext'
import Textarea from 'primevue/textarea'
import Button from 'primevue/button'
import ApiService from '@/services/ApiService'
import { useToastStore } from '@/stores/toastStore'
import { useRouter } from 'vue-router'
import { onMounted, ref } from 'vue'

const props = defineProps({
  boardGuid: String
})

const router = useRouter()
const toastStore = useToastStore()

const devAndStackTitleValue = ref('')
const devAndStackContentValue = ref('')

const goDevAndStackView = () => {
  const boardGuid = props.boardGuid
  router.push({ name: 'DevAndStackViewPage', params: { boardGuid } }).catch(() => {
    console.log('DevAndStackViewPageError')
  })
}

const devAndStackEditFunction = () => {
  if (devAndStackTitleValue.value === '') {
    toastStore.setToastValue({
      severity: 'warn',
      summary: '게시글 수정',
      detail: '제목을 입력해주세요.',
      life: 3000
    })
  } else if (devAndStackContentValue.value === '') {
    toastStore.setToastValue({
      severity: 'warn',
      summary: '게시글 수정',
      detail: '내용을 입력해주세요.',
      life: 3000
    })
  } else devAndStackEditApi()
}

const getdevAndStackApi = async () => {
  const result = await ApiService.requestAPI({
    headers: { accept: 'application/json' },
    method: 'GET',
    url: '/board/dev/stack/search',
    params: {
      developmentAndStackGuid: props.boardGuid
    }
  })
  devAndStackTitleValue.value = result.boardTitle
  devAndStackContentValue.value = result.boardContent
}

const devAndStackEditApi = async () => {
  const result = await ApiService.requestAPI({
    headers: { accept: 'application/json' },
    method: 'PUT',
    url: '/board/dev/stack/update',
    data: {
      boardGuid: props.boardGuid,
      boardTitle: devAndStackTitleValue.value,
      boardContent: devAndStackContentValue.value
    }
  })
  if (result === 'success') {
    toastStore.setToastValue({
      severity: 'success',
      summary: '게시글 수정',
      detail: '수정이 완료되었습니다.',
      life: 3000
    })
    goDevAndStackView()
  }
}

onMounted(() => {
  getdevAndStackApi()
})
</script>

<style lang="scss" scoped></style>

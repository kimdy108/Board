<template>
  <div>
    <div class="text-left mb-5">
      <div class="text-2xl font-bold text-gray-900">QnA 등록</div>
    </div>

    <div class="w-full mb-24">
      <BoardInput inputType="text" inputTitle="제목" :isDisabled="false" :isRequire="true" :isPassword="false" inputPlaceholder="" v-model:inputValue="qnaTitle" />
      <BoardEditor :isReadOnly="false" inputTitle="내용" :isRequire="true" v-model:inputValue="qnaContent" />
    </div>

    <div class="flex justify-end">
      <Button class="m-2 !px-4 !py-3 !bg-sky-400 !border !border-sky-400 hover:!bg-sky-500 hover:!border hover:!border-sky-500" @click="moveMainPage">메인</Button>
      <Button class="m-2 !px-4 !py-3 !bg-green-400 !border !border-green-400 hover:!bg-green-500 hover:!border hover:!border-green-500" @click="qnaRegist">등록</Button>
    </div>
  </div>
</template>

<script setup lang="ts">
import { onMounted, ref } from 'vue'
import { useRouter } from 'vue-router'
import { useToastStore } from '@/stores/toastStore'

import ApiService from '@/services/ApiService'
import responseData from '@/interfaces/common/responseData'

import Button from 'primevue/button'
import BoardInput from '@/components/element/BoardInput.vue'
import BoardEditor from '@/components/element/BoardEditor.vue'

const router = useRouter()
const toastStore = useToastStore()

const qnaTitle = ref('')
const qnaContent = ref('')

onMounted(() => {
  qnaTitle.value = ''
  qnaContent.value = ''
})

const moveMainPage = () => {
  router.push({ name: 'QnAMain' }).catch(() => {
    console.log('QnAMain Error')
  })
}

const qnaRegist = () => {
  if (qnaTitle.value == null || qnaTitle.value == '') {
    toastStore.setToastValue({
      severity: 'warn',
      summary: 'QnA 관리',
      detail: '제목을 입력해주세요.',
      life: 3000
    })
    return
  }

  if (qnaContent.value == null || qnaContent.value == '') {
    toastStore.setToastValue({
      severity: 'warn',
      summary: 'QnA 관리',
      detail: '내용을 입력해주세요.',
      life: 3000
    })
    return
  }

  qnaRegistAction()
}

const qnaRegistAction = async () => {
  const reqHeader = { accept: 'application/json' }
  const reqData = {
    'qnaTitle': qnaTitle.value,
    'qnaContent': qnaContent.value
  }
  const registResult: responseData = await ApiService.requestAPI({
    headers: reqHeader,
    method: 'POST',
    url: `/board/qna/regist`,
    data: reqData
  })
  if (registResult.retStatus) {
    toastStore.setToastValue({
      severity: 'success',
      summary: 'QnA 관리',
      detail: 'QnA를 등록했습니다.',
      life: 3000
    })
    moveMainPage()
  } else {
    toastStore.setToastValue({
      severity: 'error',
      summary: 'QnA 관리',
      detail: registResult.retData,
      life: 3000
    })
  }
}
</script>

<style lang="css" scoped>

</style>
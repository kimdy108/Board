<template>
  <div>
    <div class="text-left mb-5">
      <div class="text-2xl font-bold text-gray-900">QnA 수정</div>
    </div>

    <div class="w-full mb-24">
      <BoardInput inputType="text" inputTitle="제목" :isDisabled="false" :isRequire="true" :isPassword="false" inputPlaceholder="" v-model:inputValue="qnaTitle" />
      <BoardEditor :isReadOnly="false" inputTitle="내용" :isRequire="true" v-model:inputValue="qnaContent" />
    </div>

    <div class="flex justify-end">
      <Button class="m-2 !px-4 !py-3 !bg-violet-400 !border !border-violet-400 hover:!bg-violet-500 hover:!border hover:!border-violet-500" @click="movePrevPage">이전</Button>
      <Button class="m-2 !px-4 !py-3 !bg-orange-400 !border !border-orange-400 hover:!bg-orange-500 hover:!border hover:!border-orange-500" @click="qnaUpdate">수정</Button>
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

const qnaUUID = ref('')
const qnaTitle = ref('')
const qnaContent = ref('')

onMounted(() => {
  qnaUUID.value = history.state.uuid
  qnaTitle.value = ''
  qnaContent.value = ''

  getQnAInfo()
})

const movePrevPage = () => {
  router.push({ name: 'QnAInfo', state: {uuid: qnaUUID.value} }).catch(() => {
    console.log('QnAInfo Error')
  })
}

const qnaUpdate = () => {
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

  qnaUpdateAction()
}

const getQnAInfo = async () => {
  const reqHeader = { accept: 'application/json' }
  const infoResult: responseData = await ApiService.requestAPI({
    headers: reqHeader,
    method: 'GET',
    url: `/board/qna/info/${qnaUUID.value}`,
  })
  if (infoResult.retStatus) {
    qnaTitle.value = infoResult.retData.qnaTitle
    qnaContent.value = infoResult.retData.qnaContent
  }
}

const qnaUpdateAction = async () => {
  const reqHeader = { accept: 'application/json' }
  const reqData = {
    'qnaUUID': qnaUUID.value,
    'qnaTitle': qnaTitle.value,
    'qnaContent': qnaContent.value,
  }
  const updateResult: responseData = await ApiService.requestAPI({
    headers: reqHeader,
    method: 'PUT',
    url: `/board/qna/update`,
    data: reqData
  })
  if (updateResult.retStatus) {
    toastStore.setToastValue({
      severity: 'success',
      summary: 'QnA 관리',
      detail: 'QnA를 수정했습니다.',
      life: 3000
    })
    movePrevPage()
  } else {
    toastStore.setToastValue({
      severity: 'error',
      summary: 'QnA 관리',
      detail: updateResult.retData,
      life: 3000
    })
  }
} 
</script>

<style lang="css" scoped>

</style>
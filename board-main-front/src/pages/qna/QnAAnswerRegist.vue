<template>
  <Dialog :visible="showModal" 
      modal 
      :closable="false" 
      :draggable="false"
      class="w-2/3"
    >
      <template #header>
        <div class="w-full text-left mb-10">
          <div class="text-2xl font-bold text-gray-900">답글 등록</div>
        </div>
      </template>

      <div class="w-full mb-16">
        <BoardEditor :isReadOnly="false" inputTitle="" :isRequire="false" v-model:inputValue="answerContent" />
      </div>

      <template #footer>
        <div class="w-full flex justify-end">
          <Button class="!bg-green-400 !border !border-green-400 hover:!bg-green-500 hover:!border hover:!border-green-500" @click="answerRegist()">등록</Button>
          <Button class="ml-2 !bg-gray-400 !border !border-gray-400 hover:!bg-gray-500 hover:!border hover:!border-gray-500" @click="closeAnswerRegistModal">닫기</Button>
        </div>
      </template>

  </Dialog>
</template>

<script setup lang="ts">
import { ref, watch } from 'vue';
import { Dialog } from 'primevue';
import { useToastStore } from '@/stores/toastStore';

import BoardEditor from '@/components/element/BoardEditor.vue';

import Button from 'primevue/button';
import type commonAnswerModalProps from '@/interfaces/common/commonAnswerModalProps';

import ApiService from '@/services/ApiService';
import type responseData from '@/interfaces/common/responseData';

const props = defineProps<commonAnswerModalProps>()
const emit = defineEmits<{
  closeAnswerRegistModal: []
}>()

const toastStore = useToastStore()

const answerContent = ref('')

const initData = () => {
  answerContent.value = ''
}

const closeAnswerRegistModal = () => {
  emit('closeAnswerRegistModal')
}

const answerRegist = () => {
  if (answerContent.value == null || answerContent.value == '' || answerContent.value == '<p></p>') {
    toastStore.setToastValue({
      severity: 'warn',
      summary: 'QnA 관리',
      detail: '내용을 입력해주세요.',
      life: 3000
    })
    return
  }

  answerRegistAction()
}

const answerRegistAction = async () => {
  const reqHeader = { accept: 'application/json' }
  const reqData = {
    'qnaUUID': props.qnaUUID,
    'answerContent': answerContent.value,
  }
  const answerResult: responseData = await ApiService.requestAPI({
    headers: reqHeader,
    method: 'POST',
    url: `/board/qna/answer/regist`,
    data: reqData
  })
  if (answerResult.retStatus) {
    toastStore.setToastValue({
      severity: 'success',
      summary: 'QnA 관리',
      detail: '답글을 등록했습니다.',
      life: 3000
    })
    closeAnswerRegistModal()
  }
  else {
    toastStore.setToastValue({
      severity: 'error',
      summary: 'QnA 관리',
      detail: answerResult.retData,
      life: 3000
    })
  }
}

watch(() => props.showModal, (newVal) => {
  if(newVal) initData()
})

</script>

<style lang="scss" scoped>

</style>
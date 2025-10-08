<template>
  <Dialog :visible="showModal" 
      modal 
      :closable="false" 
      :draggable="false"
      class="w-2/3"
    >
      <template #header>
        <div class="w-full text-left mb-10">
          <div class="text-2xl font-bold text-gray-900">답글 수정</div>
        </div>
      </template>

      <div class="w-full mb-16">
        <BoardEditor :isReadOnly="false" inputTitle="" :isRequire="false" v-model:inputValue="answerContent" />
      </div>

      <template #footer>
        <div class="w-full flex justify-end">
          <Button class="!bg-green-400 !border !border-green-400 hover:!bg-green-500 hover:!border hover:!border-green-500" @click="answerUpdate()">등록</Button>
          <Button class="ml-2 !bg-gray-400 !border !border-gray-400 hover:!bg-gray-500 hover:!border hover:!border-gray-500" @click="closeAnswerUpdateModal">닫기</Button>
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
  closeAnswerUpdateModal: []
}>()

const toastStore = useToastStore()

const answerContent = ref('')

const initData = () => {
  answerContent.value = ''

  getAnswerInfo()
}

const closeAnswerUpdateModal = () => {
  emit('closeAnswerUpdateModal')
}

const answerUpdate = () => {
  if (answerContent.value == null || answerContent.value == '' || answerContent.value == '<p></p>') {
    toastStore.setToastValue({
      severity: 'warn',
      summary: 'QnA 관리',
      detail: '내용을 입력해주세요.',
      life: 3000
    })
    return
  }

  answerUpdateAction()
}

const answerUpdateAction = async () => {
  const reqHeader = { accept: 'application/json' }
  const reqData = {
    'answerUUID': props.answerUUID,
    'answerContent': answerContent.value,
  }
  const updateResult: responseData = await ApiService.requestAPI({
    headers: reqHeader,
    method: 'PUT',
    url: `/board/qna/answer/update`,
    data: reqData
  })
  if (updateResult.retStatus) {
    toastStore.setToastValue({
      severity: 'success',
      summary: 'QnA 관리',
      detail: '답글을 수정했습니다.',
      life: 3000
    })
    closeAnswerUpdateModal()
  }
  else {
    toastStore.setToastValue({
      severity: 'error',
      summary: 'QnA 관리',
      detail: updateResult.retData,
      life: 3000
    })
  }
}

const getAnswerInfo = async () => {
  const reqHeader = { accept: 'application/json' }
  const answerResult: responseData = await ApiService.requestAPI({
    headers: reqHeader,
    method: 'GET',
    url: `/board/qna/answer/info/${props.answerUUID}`,
  })
  if (answerResult.retStatus) {
    answerContent.value = answerResult.retData.answerContent
  }
}

watch(() => props.showModal, (newVal) => {
  if(newVal) initData()
})

</script>

<style lang="scss" scoped>

</style>
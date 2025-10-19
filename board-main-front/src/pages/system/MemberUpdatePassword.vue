<template>
  <Dialog :visible="showModal" 
      modal 
      :closable="false" 
      :draggable="false"
      class="w-2/3"
    >
      <template #header>
        <div class="w-full text-left mb-10">
          <div class="text-2xl font-bold text-gray-900">비밀번호 변경</div>
        </div>
      </template>

      <div class="w-full mb-16">
        <BoardCommonInput inputType="password" inputTitle="이전 비밀번호" :isDisabled="false" :isRequire="true" :isPassword="true" inputPlaceholder="" v-model:inputValue="oldPassword" />
        <BoardCommonInput inputType="password" inputTitle="새 비밀번호" :isDisabled="false" :isRequire="true" :isPassword="true" inputPlaceholder="" v-model:inputValue="newPassword" />
        <BoardCommonInput inputType="password" inputTitle="비밀번호 확인" :isDisabled="false" :isRequire="true" :isPassword="true" inputPlaceholder="" v-model:inputValue="confirmPassword" />
      </div>

      <template #footer>
        <div class="w-full flex justify-end">
          <Button class="!bg-green-400 !border !border-green-400 hover:!bg-green-500 hover:!border hover:!border-green-500" @click="updatePassword()">변경</Button>
          <Button class="ml-2 !bg-gray-400 !border !border-gray-400 hover:!bg-gray-500 hover:!border hover:!border-gray-500" @click="closeUpdatePasswordModal">닫기</Button>
        </div>
      </template>

  </Dialog>
</template>

<script setup lang="ts">
import { ref, watch } from 'vue'
import { useToastStore } from '@/stores/toastStore'
import { passwdReg } from '@/references/config'
import { encryptStringSalt } from '@/utils/common'
import ApiService from '@/services/ApiService'
import type responseData from '@/interfaces/common/responseData'

import BoardCommonInput from '@/components/element/BoardCommonInput.vue'

import Dialog from 'primevue/dialog'
import Button from 'primevue/button'

import type commonUpdatePasswordModalProps from '@/interfaces/common/commonUpdatePasswordModalProps'
const props = defineProps<commonUpdatePasswordModalProps>()
const emit = defineEmits<{
  closeUpdatePasswordModal: []
}>()

const toastStore = useToastStore()

const oldPassword = ref('')
const newPassword = ref('')
const confirmPassword = ref('')

const initData = () => {
  oldPassword.value = ''
  newPassword.value = ''
  confirmPassword.value = ''
}

const closeUpdatePasswordModal = () => {
  emit('closeUpdatePasswordModal')
}

const updatePassword = () => {
  if (!passwdReg.test(newPassword.value)) {
    toastStore.setToastValue({
      severity: 'error',
      summary: '사용자 설정',
      detail: '새 비밀번호를 확인해주세요.',
      life: 3000
    })
    return
  }
  if (newPassword.value != confirmPassword.value) {
    toastStore.setToastValue({
      severity: 'error',
      summary: '사용자 설정',
      detail: '비밀번호 확인이 일치하지 않습니다.',
      life: 3000
    })
    return
  }

  updatePasswordAction()
}

const updatePasswordAction = async () => {
  const reqHeader = { accept: 'application/json' }
  const reqData = {
    'memberUUID': props.updateUUID,
    'oldPassword': encryptStringSalt(oldPassword.value),
    'newPassword': encryptStringSalt(newPassword.value)
  }
  const updateResult:responseData = await ApiService.requestAPI({
    headers: reqHeader,
    method: 'PUT',
    url: `/board/system/member/update/password`,
    data: reqData
  })
  if (updateResult.retStatus) {
    toastStore.setToastValue({
      severity: 'success',
      summary: '사용자 설정',
      detail: '비밀번호 변경이 완료되었습니다.',
      life: 3000
    })
    closeUpdatePasswordModal()
  }
  else {
    toastStore.setToastValue({
      severity: 'error',
      summary: '사용자 설정',
      detail: updateResult.retData,
      life: 3000
    })
  }
}

watch(() => props.showModal, (newVal) => {
  if(newVal) initData()
})
</script>

<style scoped>

</style>
<template>
  <Dialog :visible="showModal" 
      modal 
      :closable="false" 
      :draggable="false"
      class="w-2/3"
    >
      <template #header>
        <div class="w-full text-left mb-10">
          <div class="text-2xl font-bold text-gray-900">사용자 정보</div>
        </div>
      </template>

      <div class="w-full mb-16">
        <div class="grid grid-cols-2 gap-6">
          <BoardCommonInput inputType="text" inputTitle="아이디" :isDisabled="true" :isRequire="false" :isPassword="false" inputPlaceholder="" v-model:inputValue="userID"></BoardCommonInput>
          <BoardCommonInput inputType="text" inputTitle="이름" :isDisabled="false" :isRequire="true" :isPassword="false" inputPlaceholder="" v-model:inputValue="userName"></BoardCommonInput>
        </div>

        <div class="grid grid-cols-2 gap-6">
          <BoardCommonInput inputType="text" inputTitle="닉네임" :isDisabled="false" :isRequire="true" :isPassword="false" inputPlaceholder="" v-model:inputValue="userNickName"></BoardCommonInput>

          <div class="grid grid-cols-2 gap-6">
            <div class="w-full text-left mb-3">
              <div class="pl-1">
                <span class="text-base text-gray-700">비밀번호 초기화</span> <span class="text-base mx-1 font-bold text-orange-500">*</span>
              </div>
              <div class="mt-3">
                <button class="text-base text-white bg-green-500 hover:bg-green-600 border-green-500 rounded-lg px-5 py-3" @click="passwordReset()">초기화</button>
              </div>
            </div>

            <div class="w-full text-left mb-3">
              <div class="pl-1">
                <span class="text-base text-gray-700">승인 상태</span> <span class="text-base mx-1 font-bold text-orange-500">*</span>
              </div>
              <div class="mt-3">
                <div>
                  <div class="flex">
                    <Select 
                      v-model="userApproval" 
                      :options="userApprovalType" 
                      optionLabel="name" 
                      optionValue="value"
                    ></Select>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>

        <div class="grid grid-cols-2 gap-6">
          <BoardCommonInput inputType="text" inputTitle="전화번호" :isDisabled="false" :isRequire="true" :isPassword="false" inputPlaceholder="" v-model:inputValue="userPhone"></BoardCommonInput>
          <BoardCommonInput inputType="text" inputTitle="이메일" :isDisabled="false" :isRequire="true" :isPassword="false" inputPlaceholder="" v-model:inputValue="userEmail"></BoardCommonInput>
        </div>

        <BoardCommonTextArea inputTitle="비고" v-model:inputValue="userDescription" />

      </div>

      <template #footer>
        <div class="w-full flex justify-end">
          <Button class="ml-2 !bg-red-400 !border !border-red-400 hover:!bg-red-500 hover:!border hover:!border-red-500" @click="userDelete()">삭제</Button>
          <Button class="ml-2 !bg-orange-400 !border !border-orange-400 hover:!bg-orange-500 hover:!border hover:!border-orange-500" @click="userUpdate()">수정</Button>
          <Button class="ml-2 !bg-gray-400 !border !border-gray-400 hover:!bg-gray-500 hover:!border hover:!border-gray-500" @click="closeInfoModal()">닫기</Button>
        </div>
      </template>

  </Dialog>
</template>

<script setup lang="ts">
import { ref, watch } from 'vue';
import { useToastStore } from '@/stores/toastStore'
import { encryptStringSalt, decryptStringSalt } from '@/utils/common';
import { nameReg, phoneReg, emailReg } from '@/references/config'

import { Dialog } from 'primevue';

import Button from 'primevue/button'
import Select from 'primevue/select'

import BoardCommonInput from '@/components/element/BoardCommonInput.vue'
import BoardCommonTextArea from '@/components/element/BoardCommonTextArea.vue';

import type commonInfoModalProps from '@/interfaces/common/commonInfoModalProps'

import ApiService from '@/services/ApiService';
import responseData from '@/interfaces/common/responseData';

const props = defineProps<commonInfoModalProps>()
const emit = defineEmits<{
  closeInfoModal: []
}>()

const toastStore = useToastStore()

const userUUID = ref('')
const userID = ref('')
const userName = ref('')
const userNickName = ref('')
const userPhone = ref('')
const userEmail = ref('')
const userApproval = ref('')
const userDescription = ref('')

const beforeUserApproval = ref('')

const userApprovalType = ref([
  {name: '승인', value: 'APPROVE'},
  {name: '차단', value: 'REJECT'},
  {name: '대기', value: 'WAIT'}
])

const initData = () => {
  userUUID.value = props.infoUUID
  userID.value = ''
  userName.value = ''
  userNickName.value = ''
  userPhone.value = ''
  userEmail.value = ''
  userApproval.value = 'WAIT'
  userDescription.value = ''

  beforeUserApproval.value = 'WAIT'

  getUserInfo()
}

const closeInfoModal = () => {
  emit('closeInfoModal')
}

const userUpdate = () => {
  if (!nameReg.test(userName.value)) {
    toastStore.setToastValue({
      severity: 'error',
      summary: '사용자관리',
      detail: '이름을 확인해주세요.',
      life: 3000
    })
    return
  }
  if (!phoneReg.test(userPhone.value)) {
    toastStore.setToastValue({
      severity: 'error',
      summary: '사용자관리',
      detail: '전화번호을 확인해주세요.',
      life: 3000
    })
    return
  }
  if (!emailReg.test(userEmail.value)) {
    toastStore.setToastValue({
      severity: 'error',
      summary: '사용자관리',
      detail: '이메일을 확인해주세요.',
      life: 3000
    })
    return
  }

  userUpdateAction()
}

const userUpdateAction = async () => {
  const reqHeader = { accept: 'application/json' }
  const reqData = {
    'userUUID': userUUID.value,
    'userName': encryptStringSalt(userName.value),
    'userNickName': encryptStringSalt(userNickName.value),
    'userPhone': encryptStringSalt(userPhone.value),
    'userEmail': encryptStringSalt(userEmail.value),
    'userDescription': userDescription.value
  }
  const updateResult: responseData = await ApiService.requestAPI({
    headers: reqHeader,
    method: 'PUT',
    url: `/board/user/update`,
    data: reqData
  })
  if (updateResult.retStatus) {
    toastStore.setToastValue({
      severity: 'success',
      summary: '사용자 관리',
      detail: '사용자 정보 수정이 완료되었습니다.',
      life: 3000
    })
    closeInfoModal()
  } else {
    toastStore.setToastValue({
      severity: 'error',
      summary: '사용자관리',
      detail: updateResult.retData,
      life: 3000
    })
  }
}

const userUpdateApproval = async () => {
  const reqHeader = { accept: 'application/json' }
  const reqData = {
    'userUUID': userUUID.value,
    'userApproval': userApproval.value
  }
  const updateResult: responseData = await ApiService.requestAPI({
    headers: reqHeader,
    method: 'PUT',
    url: `/board/user/update/approval`,
    data: reqData
  })
  if (updateResult.retStatus) {
    beforeUserApproval.value = userApproval.value
    toastStore.setToastValue({
      severity: 'success',
      summary: '사용자 관리',
      detail: '승인 상태 변경이 완료되었습니다.',
      life: 3000
    })
  } else {
    toastStore.setToastValue({
      severity: 'error',
      summary: '사용자관리',
      detail: updateResult.retData,
      life: 3000
    })
  }
}

const passwordReset = async () => {
  const reqHeader = { accept: 'application/json' }
  const resetResult: responseData = await ApiService.requestAPI({
    headers: reqHeader,
    method: 'PUT',
    url: `/board/user/reset/password/${userUUID.value}`
  })
  if (resetResult.retStatus) {
    toastStore.setToastValue({
      severity: 'success',
      summary: '사용자 관리',
      detail: '비밀번호 초기화 완료되었습니다.',
      life: 3000
    })
  } else {
    toastStore.setToastValue({
      severity: 'error',
      summary: '사용자관리',
      detail: resetResult.retData,
      life: 3000
    })
  }
}

const userDelete = async () => {
  const reqHeader = { accept: 'application/json' }
  const deleteResult: responseData = await ApiService.requestAPI({
    headers: reqHeader,
    method: 'DELETE',
    url: `/board/user/delete/${userUUID.value}`
  })
  if (deleteResult.retStatus) {
    toastStore.setToastValue({
      severity: 'success',
      summary: '사용자 관리',
      detail: '삭제가 완료되었습니다.',
      life: 3000
    })
    closeInfoModal()
  } else {
    toastStore.setToastValue({
      severity: 'error',
      summary: '사용자관리',
      detail: deleteResult.retData,
      life: 3000
    })
  }
}

const getUserInfo = async () => {
  const reqHeader = { accept: 'application/json' }
  const infoResult: responseData = await ApiService.requestAPI({
    headers: reqHeader,
    method: 'GET',
    url: `/board/user/info/${userUUID.value}`,
  })
  if (infoResult.retStatus) {
    userID.value = decryptStringSalt(infoResult.retData.userID)
    userName.value = decryptStringSalt(infoResult.retData.userName)
    userNickName.value = decryptStringSalt(infoResult.retData.userNickName)
    userPhone.value = decryptStringSalt(infoResult.retData.userPhone)
    userEmail.value = decryptStringSalt(infoResult.retData.userEmail)
    userApproval.value = infoResult.retData.userApproval
    userDescription.value = infoResult.retData.userDescription

    beforeUserApproval.value = infoResult.retData.userApproval
  }
}

watch(() => props.showModal, (newVal) => {
  if(newVal) initData()
})

watch(() => userApproval.value, () => {
  if (userApproval.value != beforeUserApproval.value) userUpdateApproval()
})
</script>
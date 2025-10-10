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
          <BoardModalInput inputType="text" inputTitle="아이디" :isDisabled="true" :isRequire="false" :isPassword="false" inputPlaceholder="" v-model:inputValue="userID"></BoardModalInput>
          <BoardModalInput inputType="text" inputTitle="이름" :isDisabled="false" :isRequire="true" :isPassword="false" inputPlaceholder="" v-model:inputValue="userName"></BoardModalInput>
        </div>

        <div class="grid grid-cols-2 gap-6">
          <BoardModalInput inputType="text" inputTitle="닉네임" :isDisabled="false" :isRequire="true" :isPassword="false" inputPlaceholder="" v-model:inputValue="userNickName"></BoardModalInput>

          <div class="grid grid-cols-2 gap-6">
            <div class="w-full text-left mb-3">
              <div class="pl-1">
                <span class="text-base text-gray-700">비밀번호 초기화</span> <span class="text-base mx-1 font-bold text-orange-500">*</span>
              </div>
              <div class="mt-3">
                <button class="text-base text-white bg-green-500 hover:bg-green-600 border-green-500 rounded-lg px-5 py-3" @click="passwordReset">초기화</button>
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
          <BoardModalInput inputType="text" inputTitle="전화번호" :isDisabled="false" :isRequire="true" :isPassword="false" inputPlaceholder="" v-model:inputValue="userPhone"></BoardModalInput>
          <BoardModalInput inputType="text" inputTitle="이메일" :isDisabled="false" :isRequire="true" :isPassword="false" inputPlaceholder="" v-model:inputValue="userEmail"></BoardModalInput>
        </div>

        <div class="w-full text-left mb-3">
          <div class="pl-1">
            <span class="text-base text-gray-700 dark:text-gray-50">비고</span>
          </div>
          <div class="mt-3">
            <textarea 
              class="w-full border border-gray-300 rounded-lg bg-white py-3 px-5 focus:outline-none"
              v-model="userDescription"
              style="height: 150px;"
            ></textarea>
          </div>
        </div>

      </div>

      <template #footer>
        <div class="w-full flex justify-end">
          <Button class="!bg-orange-400 !border !border-orange-400 hover:!bg-orange-500 hover:!border hover:!border-orange-500" @click="updateUserInfo()">수정</Button>
          <Button class="ml-2 !bg-gray-400 !border !border-gray-400 hover:!bg-gray-500 hover:!border hover:!border-gray-500" @click="closeUserInfoModal()">닫기</Button>
        </div>
      </template>

  </Dialog>
</template>

<script setup lang="ts">
import { ref, watch } from 'vue';
import { useToastStore } from '@/stores/toastStore'
import { decryptStringSalt } from '@/utils/common';

import { Dialog } from 'primevue';

import Button from 'primevue/button'
import Select from 'primevue/select'

import BoardModalInput from '@/components/element/BoardModalInput.vue'

import type commonInfoModalProps from '@/interfaces/common/commonInfoModalProps'

import ApiService from '@/services/ApiService';
import responseData from '@/interfaces/common/responseData';

const props = defineProps<commonInfoModalProps>()
const emit = defineEmits<{
  closeUserInfoModal: []
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

const closeUserInfoModal = () => {
  emit('closeUserInfoModal')
}

const passwordReset = () => {}

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

const getUserInfo = async () => {
  const reqHeader = { accept: 'application/json' }
  const infoResult: responseData = await ApiService.requestAPI({
    headers: reqHeader,
    method: 'GET',
    url: `/board/user/info/${props.infoUUID}`,
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
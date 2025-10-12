<template>
  <div>
    <div class="text-left mb-5">
      <div class="text-2xl font-bold text-gray-900">사용자 설정</div>
    </div>

    <div class="w-full bg-white rounded-lg px-8 py-4 mb-5 border border-gray-200">
      <div class="grid grid-cols-2 gap-6 mb-3">
        <BoardCommonInput inputType="text" inputTitle="아이디" :isDisabled="true" :isRequire="false" :isPassword="false" inputPlaceholder="" v-model:inputValue="memberID" />
        <BoardCommonInput inputType="text" inputTitle="이름" :isDisabled="false" :isRequire="true" :isPassword="false" inputPlaceholder="" v-model:inputValue="memberName" />
        
        <BoardCommonInput inputType="text" inputTitle="닉네임" :isDisabled="false" :isRequire="true" :isPassword="false" inputPlaceholder="" v-model:inputValue="memberNickName" />
        <div class="w-full text-left mb-3">
          <div class="pl-1">
            <span class="text-base text-gray-700">비밀번호 변경</span> <span class="text-base mx-1 font-bold text-orange-500">*</span>
          </div>
          <div class="mt-3">
            <button class="text-base text-white bg-green-500 hover:bg-green-600 border-green-500 rounded-lg px-5 py-3" @click="showUpdatePasswordModal()">비밀번호 변경</button>
          </div>
        </div>

        <BoardCommonInput inputType="text" inputTitle="전화번호" :isDisabled="false" :isRequire="true" :isPassword="false" inputPlaceholder="" v-model:inputValue="memberPhone" />
        <BoardCommonInput inputType="text" inputTitle="이메일" :isDisabled="false" :isRequire="true" :isPassword="false" inputPlaceholder="" v-model:inputValue="memberEmail" />
      </div>

      <BoardCommonTextArea inputTitle="비고" v-model:inputValue="memberDescription" />

      <div class="flex justify-end">
        <Button class="ml-2 !bg-orange-400 !border !border-orange-400 hover:!bg-orange-500 hover:!border hover:!border-orange-500" @click="memberUpdate()">수정</Button>
      </div>
    </div>

    <MemberUpdatePassword :showModal="isShowUpdatePasswordModal" :updateUUID="memberUUID" @closeUpdatePasswordModal="closeUpdatePasswordModal" />

  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useUserStore } from '@/stores/userStore'
import { useToastStore } from '@/stores/toastStore'
import { encryptStringSalt, decryptStringSalt } from '@/utils/common'
import { nameReg, phoneReg, emailReg } from '@/references/config'

import ApiService from '@/services/ApiService'
import type responseData from '@/interfaces/common/responseData'

import Button from 'primevue/button'

import BoardCommonInput from '@/components/element/BoardCommonInput.vue'
import BoardCommonTextArea from '@/components/element/BoardCommonTextArea.vue'

import MemberUpdatePassword from './MemberUpdatePassword.vue'

onMounted(() => {
  memberUUID.value = decryptStringSalt(userStore.getCurrentUser.uud)

  getMemberInfo()
})

const userStore = useUserStore()
const toastStore = useToastStore()

const memberUUID = ref('')
const memberID = ref('')
const memberName = ref('')
const memberNickName = ref('')
const memberPhone = ref('')
const memberEmail = ref('')
const memberDescription = ref('')

const isShowUpdatePasswordModal = ref(false)

const showUpdatePasswordModal = () => {
  isShowUpdatePasswordModal.value = true
}

const closeUpdatePasswordModal = () => {
  isShowUpdatePasswordModal.value = false
}

const memberUpdate = () => {
  if (!nameReg.test(memberName.value)) {
    toastStore.setToastValue({
      severity: 'error',
      summary: '사용자 설정',
      detail: '이름을 확인해주세요.',
      life: 3000
    })
    return
  }
  if (!phoneReg.test(memberPhone.value)) {
    toastStore.setToastValue({
      severity: 'error',
      summary: '사용자 설정',
      detail: '전화번호를 확인해주세요.',
      life: 3000
    })
    return
  }
  if (!emailReg.test(memberEmail.value)) {
    toastStore.setToastValue({
      severity: 'error',
      summary: '사용자 설정',
      detail: '이메일을 확인해주세요.',
      life: 3000
    })
    return 
  }

  memberUpdateAction()
}

const memberUpdateAction = async () => {
  const reqHeader = { accept: 'application/json' }
  const reqData = {
    'memberUUID': memberUUID.value,
    'memberName': encryptStringSalt(memberName.value),
    'memberNickName': encryptStringSalt(memberName.value),
    'memberPhone': encryptStringSalt(memberPhone.value),
    'memberEmail': encryptStringSalt(memberEmail.value),
    'memberDescription': memberDescription.value
  }
  const updateResult: responseData = await ApiService.requestAPI({
    headers: reqHeader,
    method: 'PUT',
    url: `/board/system/member/update`,
    data: reqData
  })
  if (updateResult.retStatus) {
    toastStore.setToastValue({
      severity: 'success',
      summary: '사용자 설정',
      detail: '사용자 수정이 완료되었습니다.',
      life: 3000
    })
  } else {
    toastStore.setToastValue({
      severity: 'error',
      summary: '사용자 설정',
      detail: updateResult.retData,
      life: 3000
    })
  }
}

const getMemberInfo = async () => {
  const reqHeader = { accept: 'application/json' }
  const infoResult: responseData = await ApiService.requestAPI({
    headers: reqHeader,
    method: 'GET',
    url: `/board/system/member/info/${memberUUID.value}`
  })
  if (infoResult.retStatus) {
    memberID.value = decryptStringSalt(infoResult.retData.memberID)
    memberName.value = decryptStringSalt(infoResult.retData.memberName)
    memberNickName.value = decryptStringSalt(infoResult.retData.memberNickName)
    memberPhone.value = decryptStringSalt(infoResult.retData.memberPhone)
    memberEmail.value = decryptStringSalt(infoResult.retData.memberEmail)
    memberDescription.value = infoResult.retData.memberDescription
  }
}
</script>

<style scoped>

</style>
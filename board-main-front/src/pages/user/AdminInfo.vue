<template>
  <Dialog :visible="showModal" 
      modal 
      :closable="false" 
      :draggable="false"
      class="w-2/3"
    >
      <template #header>
        <div class="w-full text-left mb-10">
          <div class="text-2xl font-bold text-gray-900">관리자 정보</div>
        </div>
      </template>

      <div class="w-full mb-16">
        <div class="grid grid-cols-2 gap-6">
          <BoardModalInput inputType="text" inputTitle="아이디" :isDisabled="false" :isRequire="true" :isPassword="false" inputPlaceholder="" v-model:inputValue="adminID"></BoardModalInput>
          <BoardModalInput inputType="text" inputTitle="이름" :isDisabled="false" :isRequire="true" :isPassword="false" inputPlaceholder="" v-model:inputValue="adminName"></BoardModalInput>
        </div>

        <div class="grid grid-cols-2 gap-6">
          <BoardModalInput inputType="text" inputTitle="닉네임" :isDisabled="false" :isRequire="true" :isPassword="false" inputPlaceholder="" v-model:inputValue="adminNickName"></BoardModalInput>
          
          <div class="w-full text-left mb-3">
            <div class="pl-1">
              <span class="text-base text-gray-700">비밀번호 초기화</span> <span class="text-base mx-1 font-bold text-orange-500">*</span>
            </div>
            <div class="mt-3">
              <button class="text-base text-white bg-green-500 hover:bg-green-600 border-green-500 rounded-lg px-5 py-3" @click="adminResetPassword()">초기화</button>
            </div>
          </div>
        </div>

        <div class="w-full text-left mb-3">
          <div class="pl-1">
            <span class="text-base text-gray-700">관리자 권한</span> <span class="text-base mx-1 font-bold text-orange-500">*</span>
          </div>
          <div class="mt-3">
            <div>
              <div class="flex">
                <SelectButton v-model="adminRole" :options="adminRoleOptions" optionLabel="name" optionValue="value" />
              </div>
            </div>
          </div>
        </div>

        <div class="grid grid-cols-2 gap-6">
          <BoardModalInput inputType="text" inputTitle="전화번호" :isDisabled="false" :isRequire="true" :isPassword="false" inputPlaceholder="" v-model:inputValue="adminPhone"></BoardModalInput>
          <BoardModalInput inputType="text" inputTitle="이메일" :isDisabled="false" :isRequire="true" :isPassword="false" inputPlaceholder="" v-model:inputValue="adminEmail"></BoardModalInput>
        </div>

        <div class="w-full text-left mb-3">
          <div class="pl-1">
            <span class="text-base text-gray-700 dark:text-gray-50">비고</span>
          </div>
          <div class="mt-3">
            <textarea 
              class="w-full border border-gray-300 rounded-lg bg-white py-3 px-5 focus:outline-none"
              v-model="adminDescription"
              style="height: 150px;"
            ></textarea>
          </div>
        </div>

      </div>

      <template #footer>
        <div class="w-full flex justify-end">
          <Button class="ml-2 !bg-red-400 !border !border-red-400 hover:!bg-red-500 hover:!border hover:!border-red-500" @click="adminDelete()">삭제</Button>
          <Button class="ml-2 !bg-orange-400 !border !border-orange-400 hover:!bg-orange-500 hover:!border hover:!border-orange-500" @click="adminUpdate()">수정</Button>
          <Button class="ml-2 !bg-gray-400 !border !border-gray-400 hover:!bg-gray-500 hover:!border hover:!border-gray-500" @click="closeInfoModal()">닫기</Button>
        </div>
      </template>

  </Dialog>
</template>

<script setup lang="ts">
import { computed, ref, watch } from 'vue';
import { useToastStore } from '@/stores/toastStore'
import { useUserStore } from '@/stores/userStore'
import { encryptStringSalt, decryptStringSalt } from '@/utils/common';
import { nameReg, phoneReg, emailReg, userRoleList } from '@/references/config'

import { Dialog } from 'primevue';

import Button from 'primevue/button'
import SelectButton from 'primevue/selectbutton'

import BoardModalInput from '@/components/element/BoardModalInput.vue'

import commonInfoModalProps from '@/interfaces/common/commonInfoModalProps';

import ApiService from '@/services/ApiService';
import responseData from '@/interfaces/common/responseData';

const props = defineProps<commonInfoModalProps>()
const emit = defineEmits<{
  closeInfoModal: []
}>()

const userStore = useUserStore()
const toastStore = useToastStore()

const adminUUID = ref('')

const adminID = ref('')
const adminName = ref('')
const adminNickName = ref('')
const adminPhone = ref('')
const adminEmail = ref('')
const adminDescription = ref('')

const adminRole = ref('MANAGER')
const adminRoleOptions = computed(() => {
  let idx = userRoleList.findIndex(x => x.value == decryptStringSalt(userStore.getUserRole))
  return userRoleList.filter(x => x.key > idx && x.key != 3)
})

const initData = () => {
  adminUUID.value = props.infoUUID
  adminID.value = ''
  adminName.value = ''
  adminNickName.value = ''
  adminPhone.value = ''
  adminEmail.value = ''
  adminDescription.value = ''
  adminRole.value = 'MANAGER'

  adminInfo()
}

const closeInfoModal = () => {
  emit('closeInfoModal')
}

const adminUpdate = () => {
  if (!nameReg.test(adminName.value)) {
    toastStore.setToastValue({
      severity: 'error',
      summary: '관리자관리',
      detail: '이름을 확인해주세요.',
      life: 3000
    })
    return
  }
  if (!phoneReg.test(adminPhone.value)) {
    toastStore.setToastValue({
      severity: 'error',
      summary: '관리자관리',
      detail: '전화번호을 확인해주세요.',
      life: 3000
    })
    return
  }
  if (!emailReg.test(adminEmail.value)) {
    toastStore.setToastValue({
      severity: 'error',
      summary: '관리자관리',
      detail: '이메일을 확인해주세요.',
      life: 3000
    })
    return
  }

  adminUpdateAction()
}

const adminUpdateAction = async () => {
  const reqHeader = { accept: 'application/json' }
  const reqData = {
    'adminUUID': adminUUID.value,
    'adminName': encryptStringSalt(adminName.value),
    'adminNickName': encryptStringSalt(adminNickName.value),
    'adminPhone': encryptStringSalt(adminPhone.value),
    'adminEmail': encryptStringSalt(adminEmail.value),
    'adminRole': adminRole.value,
    'adminDescription': adminDescription.value
  }
  const updateResult: responseData = await ApiService.requestAPI({
    headers: reqHeader,
    method: 'PUT',
    url: `/board/admin/update`,
    data: reqData
  })
  if (updateResult.retStatus) {
    toastStore.setToastValue({
      severity: 'success',
      summary: '관리자관리',
      detail: '관리자 정보 수정이 완료되었습니다.',
      life: 3000
    })
    closeInfoModal()
  } else {
    toastStore.setToastValue({
      severity: 'error',
      summary: '관리자관리',
      detail: updateResult.retData,
      life: 3000
    })
  }
}

const adminResetPassword = async () => {
  const reqHeader = { accept: 'application/json' }
  const resetResult: responseData = await ApiService.requestAPI({
    headers: reqHeader,
    method: 'PUT',
    url: `/board/admin/reset/password/${adminUUID.value}`
  })
  if (resetResult.retStatus) {
    toastStore.setToastValue({
      severity: 'success',
      summary: '관리자관리',
      detail: '비밀번호 초기화 완료되었습니다.',
      life: 3000
    })
  } else {
    toastStore.setToastValue({
      severity: 'error',
      summary: '관리자관리',
      detail: resetResult.retData,
      life: 3000
    })
  }
}

const adminDelete = async () => {
  const reqHeader = { accept: 'application/json' }
  const deleteResult: responseData = await ApiService.requestAPI({
    headers: reqHeader,
    method: 'DELETE',
    url: `/board/admin/delete/${adminUUID.value}`
  })
  if (deleteResult.retStatus) {
    toastStore.setToastValue({
      severity: 'success',
      summary: '관리자관리',
      detail: '삭제가 완료되었습니다.',
      life: 3000
    })
    closeInfoModal()
  } else {
    toastStore.setToastValue({
      severity: 'error',
      summary: '관리자관리',
      detail: deleteResult.retData,
      life: 3000
    })
  }
}

const adminInfo = async () => {
  const reqHeader = { accept: 'application/json' }
  const infoResult: responseData = await ApiService.requestAPI({
    headers: reqHeader,
    method: 'GET',
    url: `/board/admin/info/${adminUUID.value}`
  })
  if (infoResult.retStatus) {
    adminID.value = decryptStringSalt(infoResult.retData.adminID)
    adminName.value = decryptStringSalt(infoResult.retData.adminName)
    adminNickName.value = decryptStringSalt(infoResult.retData.adminNickName)
    adminPhone.value = decryptStringSalt(infoResult.retData.adminPhone)
    adminEmail.value = decryptStringSalt(infoResult.retData.adminEmail)
    adminRole.value = infoResult.retData.adminRole
    adminDescription.value = infoResult.retData.adminDescription
  }
}

watch(() => props.showModal, (newVal) => {
  if(newVal) initData()
})
</script>

<style scoped>

</style>
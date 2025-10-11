<template>
  <Dialog :visible="showModal" 
      modal 
      :closable="false" 
      :draggable="false"
      class="w-2/3"
    >
      <template #header>
        <div class="w-full text-left mb-10">
          <div class="text-2xl font-bold text-gray-900">관리자 등록</div>
        </div>
      </template>

      <div class="w-full mb-16">
        <div class="grid grid-cols-2 gap-6">
          <BoardModalInput inputType="text" inputTitle="아이디" :isDisabled="false" :isRequire="true" :isPassword="false" inputPlaceholder="" v-model:inputValue="adminID"></BoardModalInput>
          <BoardModalInput inputType="text" inputTitle="이름" :isDisabled="false" :isRequire="true" :isPassword="false" inputPlaceholder="" v-model:inputValue="adminName"></BoardModalInput>
        </div>

        <div class="grid grid-cols-2 gap-6">
          <BoardModalInput inputType="text" inputTitle="닉네임" :isDisabled="false" :isRequire="true" :isPassword="false" inputPlaceholder="" v-model:inputValue="adminNickName"></BoardModalInput>
          <BoardModalInput inputType="password" inputTitle="비밀번호" :isDisabled="false" :isRequire="true" :isPassword="true" inputPlaceholder="" v-model:inputValue="adminPassword"></BoardModalInput>
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
          <Button class="ml-2 !bg-green-400 !border !border-green-400 hover:!bg-green-500 hover:!border hover:!border-green-500" @click="adminRegist()">등록</Button>
          <Button class="ml-2 !bg-gray-400 !border !border-gray-400 hover:!bg-gray-500 hover:!border hover:!border-gray-500" @click="closeRegistModal()">닫기</Button>
        </div>
      </template>

  </Dialog>
</template>

<script setup lang="ts">
import { computed, ref, watch } from 'vue';
import { useToastStore } from '@/stores/toastStore'
import { useUserStore } from '@/stores/userStore'
import { encryptStringSalt, decryptStringSalt } from '@/utils/common';
import { idReg, nameReg, passwdReg, phoneReg, emailReg, userRoleList } from '@/references/config'

import { Dialog } from 'primevue';

import Button from 'primevue/button'
import SelectButton from 'primevue/selectbutton'

import BoardModalInput from '@/components/element/BoardModalInput.vue'

import type commonRegistModalProps from '@/interfaces/common/commonRegistModalProps'

import ApiService from '@/services/ApiService';
import responseData from '@/interfaces/common/responseData';

const props = defineProps<commonRegistModalProps>()
const emit = defineEmits<{
  closeRegistModal: []
}>()

const userStore = useUserStore()
const toastStore = useToastStore()

const adminID = ref('')
const adminName = ref('')
const adminNickName = ref('')
const adminPassword = ref('')
const adminPhone = ref('')
const adminEmail = ref('')
const adminDescription = ref('')

const adminRole = ref('MANAGER')
const adminRoleOptions = computed(() => {
  let idx = userRoleList.findIndex(x => x.value == decryptStringSalt(userStore.getUserRole))
  return userRoleList.filter(x => x.key > idx && x.key != 3)
})

const initData = () => {
  adminID.value = ''
  adminName.value = ''
  adminNickName.value = ''
  adminPassword.value = ''
  adminPhone.value = ''
  adminEmail.value = ''
  adminDescription.value = ''
  adminRole.value = 'MANAGER'
}

const closeRegistModal = () => {
  emit('closeRegistModal')
}

const adminRegist = () => {
  if (!idReg.test(adminID.value)) {
    toastStore.setToastValue({
      severity: 'error',
      summary: '관리자관리',
      detail: '아이디를 확인해주세요.',
      life: 3000
    })
    return
  }
  if (!passwdReg.test(adminPassword.value)) {
    toastStore.setToastValue({
      severity: 'error',
      summary: '관리자관리',
      detail: '비밀번호를 확인해주세요.',
      life: 3000
    })
    return
  }
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

  adminRegistAction()
}

const adminRegistAction = async () => {
  const reqHeader = { accept: 'application/json' }
  const reqData = {
    'adminID': encryptStringSalt(adminID.value),
    'adminPassword': encryptStringSalt(adminPassword.value),
    'adminName': encryptStringSalt(adminName.value),
    'adminNickName': encryptStringSalt(adminNickName.value),
    'adminPhone': encryptStringSalt(adminPhone.value),
    'adminEmail': encryptStringSalt(adminEmail.value),
    'adminRole': adminRole.value,
    'adminDescription': adminDescription.value
  }
  const registResult: responseData = await ApiService.requestAPI({
    headers: reqHeader,
    method: 'POST',
    url: `/board/admin/regist`,
    data: reqData
  })
  if (registResult.retStatus) {
    toastStore.setToastValue({
      severity: 'success',
      summary: '관리자관리',
      detail: '관리자 등록이 완료되었습니다.',
      life: 3000
    })
    closeRegistModal()
  } else {
    toastStore.setToastValue({
      severity: 'error',
      summary: '관리자관리',
      detail: registResult.retData,
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
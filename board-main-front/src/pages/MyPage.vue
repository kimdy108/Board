<template>
  <div class="text-left text-black">
    <div class="ml-5 text-6xl p-10">마이페이지</div>
  </div>
  <div class="card flex">
    <div class="gap-2 text-left ml-16">
      <label for="userId" class="flex flex-col text-3xl text-black">아이디</label>
      <InputText
        id="userId"
        v-model="userIdValue"
        size="large"
        style="width: 650px; height: 50px; font-size: 20px"
        disabled
      />
    </div>
    <div class="gap-2 text-left ml-16">
      <label for="userName" class="flex flex-col text-3xl text-black">이름</label>
      <InputText
        id="userName"
        v-model="userNameValue"
        size="large"
        style="width: 650px; height: 50px; font-size: 20px"
      />
    </div>
  </div>
  <div class="card flex mt-16">
    <div class="gap-2 text-left ml-16">
      <label for="userNickName" class="flex flex-col text-3xl text-black">닉네임</label>
      <InputText
        id="userNickName"
        v-model="userNickNameValue"
        size="large"
        style="width: 650px; height: 50px; font-size: 20px"
        disabled
      />
    </div>
    <div class="gap-2 text-left ml-16">
      <Button
        label="비밀번호 변경"
        size="large"
        class="mt-10"
        @click="changePasswordFunction"
      ></Button>
    </div>
  </div>
  <div class="card flex mt-16">
    <div class="gap-2 text-left ml-16">
      <label for="userEmail" class="flex flex-col text-3xl text-black">이메일</label>
      <InputText
        id="userEmail"
        v-model="userEmailValue"
        size="large"
        style="width: 650px; height: 50px; font-size: 20px"
      />
    </div>
    <div class="gap-2 text-left ml-16">
      <label for="userPhone" class="flex flex-col text-3xl text-black">전화번호</label>
      <InputText
        id="userPhone"
        v-model="userPhoneValue"
        size="large"
        style="width: 650px; height: 50px; font-size: 20px"
      />
    </div>
  </div>
  <hr class="mt-16 ml-16 mr-16 mb-10" />
  <div class="text-left mr-16 flex justify-end">
    <Button
      label="회원 관리"
      size="large"
      severity="warn"
      class="mr-2"
      :style="isManager ? '' : 'display: none'"
      @click="memberManageFunction"
    />
    <Button
      label="매니저 관리"
      size="large"
      severity="help"
      class="mr-2"
      :style="isManager ? '' : 'display: none'"
      @click="managerFunction"
    />
    <Button
      label="회원탈퇴"
      size="large"
      severity="danger"
      class="mr-2"
      @click="signOutFunction"
      :style="isAdmin ? 'display: none' : ''"
    />
    <Button label="수정" size="large" severity="info" class="mr-2" @click="userUpdateFunction" />
  </div>
  <ChangePasswordPage
    :showModal="isChangePasswordModal"
    @closeChangePasswordModal="closeChangePasswordModal"
  />
</template>

<script setup>
import InputText from 'primevue/inputtext'
import Button from 'primevue/button'
import ApiService from '@/services/ApiService'
import { useRouter } from 'vue-router'
import { useUserStore } from '@/stores/userStore'
import { useToastStore } from '@/stores/toastStore'
import { onMounted, ref } from 'vue'
import { encryptString, decryptStringSalt, encryptStringSalt } from '@/utils/common'
import ChangePasswordPage from './ChangePasswordPage.vue'

const router = useRouter()
const userStore = useUserStore()
const toastStore = useToastStore()

const userIdValue = ref('')
const userNameValue = ref('')
const userNickNameValue = ref('')
const userEmailValue = ref('')
const userPhoneValue = ref('')

const isManager = ref(false)
const isAdmin = ref(false)

const isChangePasswordModal = ref(false)

const checkIsManager = () => {
  if (decryptStringSalt(userStore.getUserRole) === 'manager') isManager.value = true
}

const checkIsAdmin = () => {
  if (decryptStringSalt(userStore.getUserAccess.uid) === encryptString('admin'))
    isAdmin.value = true
}

const changePasswordFunction = () => {
  isChangePasswordModal.value = true
}

const closeChangePasswordModal = () => {
  isChangePasswordModal.value = false
}

const signOutFunction = () => {
  router.push({ name: 'SignOutPage' }).catch(() => {
    console.log('SignOutPageError')
  })
}

const managerFunction = () => {
  router.push({ name: 'PromoteManagerPage' }).catch(() => {
    console.log('PromoteManagerPageError')
  })
}

const memberManageFunction = () => {
  router.push({ name: 'MemberManagePage' }).catch(() => {
    console.log('MemberManagePageError')
  })
}

const userUpdateFunction = () => {
  if (userNameValue.value == null || userNameValue.value == '') {
    toastStore.setToastValue({
      severity: 'warn',
      summary: '개인정보 수정',
      detail: '이름을 입력해주세요.',
      life: 3000
    })
  } else if (userEmailValue.value == null || userEmailValue.value == '') {
    toastStore.setToastValue({
      severity: 'warn',
      summary: '개인정보 수정',
      detail: '이메일을 입력해주세요.',
      life: 3000
    })
  } else if (userPhoneValue.value == null || userPhoneValue.value == '') {
    toastStore.setToastValue({
      severity: 'warn',
      summary: '개인정보 수정',
      detail: '전화번호를 입력해주세요.',
      life: 3000
    })
  } else userUpdateApi()
}

const getUserInfoFunction = async () => {
  const result = await ApiService.requestAPI({
    headers: { accept: 'application/json' },
    method: 'GET',
    url: '/member/info'
  })
  userIdValue.value = decryptStringSalt(result.userId)
  userNameValue.value = decryptStringSalt(result.userName)
  userNickNameValue.value = decryptStringSalt(result.userNickName)
  userEmailValue.value = decryptStringSalt(result.userEmail)
  userPhoneValue.value = decryptStringSalt(result.userPhone)
}

const userUpdateApi = async () => {
  const result = await ApiService.requestAPI({
    headers: { accept: 'application/json' },
    method: 'PUT',
    url: '/member/update',
    data: {
      encryptUserName: encryptStringSalt(userNameValue.value),
      encryptUserEmail: encryptStringSalt(userEmailValue.value),
      encryptUserPhone: encryptStringSalt(userPhoneValue.value)
    }
  })
  if (result === 'success') {
    toastStore.setToastValue({
      severity: 'success',
      summary: '개인정보 수정',
      detail: '수정되었습니다.',
      life: 3000
    })
    getUserInfoFunction()
  } else {
    toastStore.setToastValue({
      severity: 'error',
      summary: '개인정보 수정',
      detail: '수정에 실패했습니다.',
      life: 3000
    })
  }
}

onMounted(() => {
  getUserInfoFunction()
  checkIsManager()
  checkIsAdmin()
})
</script>

<style lang="scss" scoped></style>

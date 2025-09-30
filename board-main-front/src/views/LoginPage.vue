<template>
  <div class="min-h-screen transition-all duration-300 ease-in-out">
    <div class="w-full h-screen flex items-center justify-center">
      <div class="w-1/4 bg-white border rounded-lg shadow-lg">
        <div class="flex items-center justify-center my-10">
          <img class="max-h-10" :src="require('@/assets/images/logo.png')" alt="logo" />
        </div>
        <div class="px-12 py-5 w-full text-gray-600">
          <div class="text-left">
            <IconField class="mb-6">
              <InputIcon class="ml-1 pi pi-user" />
               <input
                class="border border-gray-200 rounded-lg shadow-sm w-full py-4 pl-10 text-gray-900 placeholder-gray-400 text-md focus:outline-none"
                @keyup.enter="userLogin"
                type="text" placeholder="아이디를 입력해주세요." v-model="userID" />
            </IconField>
            <IconField class="mb-6">
              <InputIcon class="ml-1 pi pi-lock" />
               <input
                class="border border-gray-200 rounded-lg shadow-sm w-full py-4 pl-10 text-gray-900 placeholder-gray-400 text-md focus:outline-none"
                @keyup.enter="userLogin"
                type="password" placeholder="비밀번호를 입력해 주세요." v-model="userPassword" />
            </IconField>
            <div class="mb-6">
              <button class="w-full bg-violet-500 hover:bg-violet-600 py-5 rounded-lg focus:outline-none transition-all duration-300 ease-in-out" @click="userLogin()">
                <span class="text-white font-bold text-md">로그인</span>
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted, onBeforeMount } from 'vue'
import IconField from 'primevue/iconfield';
import InputIcon from 'primevue/inputicon';

import AuthService from '@/services/AuthService'
import type responseData from '@/interfaces/common/responseData'

import { encryptStringSalt, decryptString } from '@/utils/common'
import { divisionChar,emptyUUID } from '@/references/config'

import { useUserStore } from '@/stores/userStore'
import { useToastStore } from '@/stores/toastStore'
import { useRouter } from 'vue-router'

const userStore = useUserStore()
const toastStore = useToastStore()
const router = useRouter()

const userID = ref('')
const userPassword = ref('')

const currentUser = computed(() => userStore.getCurrentUser)

onMounted( async () => {
  if (currentUser.value.at) {
    router.push({ name: 'MainPage' }).catch(() => {
      console.log('mainerror')
    })
    return
  }
})

const userLogin = () => {
  if(userID.value == '' || userPassword.value == '') {
    toastStore.setToastValue({
      severity: 'warn',
      summary: '로그인 오류',
      detail: '아이디 또는 비밀번호를 확인해주세요.',
      life: 3000
    })
    return
  }
  userLoginAction()
}

const userLoginAction = async () => {
  const reqData: any = {
    userID: encryptStringSalt(userID.value),
    userPassword: encryptStringSalt(userPassword.value)
  }

  const loginResult: responseData = await AuthService.requestLogin({
    data: reqData
  })
  if (loginResult.retStatus) {
    const userInfo = decryptString(loginResult.retData.enc).split(divisionChar)
    userStore.setCurrentUser({
      at: loginResult.retData.at,
      rt: loginResult.retData.rt,
      uid: encryptStringSalt(userID.value), // userID
      unm: encryptStringSalt(userInfo[0]), // userName
      uud: encryptStringSalt(userInfo[1]), // userUuid
      uro: encryptStringSalt(userInfo[2]), // userRole
      sud: encryptStringSalt(userInfo[3])  // userSessionUuid
    })
    userStore.setUserRole(encryptStringSalt(userInfo[2]))
    router.push({name : 'IndexPage'}).catch(() => {
      console.log('indexPage')
    })
  } else {
    toastStore.setToastValue({
      severity: 'error',
      summary: '로그인 오류',
      detail: loginResult.retData,
      life: 3000
    })
  }
}
</script>

<style lang="scss" scoped>
.login-input {
  height: 48px;
  width:100%;
  background-color: #FFF;
  background-position:5% 50%;
  background-repeat:no-repeat;
}
</style>
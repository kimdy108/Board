<template>
  <div class="min-h-screen transition-all duration-300 ease-in-out">
    <div class="w-full h-screen flex items-center justify-center">
      <div class="w-1/4 bg-white border rounded-lg shadow-lg">
        <div class="flex items-center justify-center my-10">
          <img class="max-h-10" :src="require('@/assets/images/logo.png')" alt="logo" />
        </div>
        <div class="px-12 py-5 w-full text-gray-600">
          <div class="text-left">
            <input
              class="mb-6 border border-gray-200 rounded-lg shadow-sm w-full py-4 pl-4 text-gray-900 placeholder-gray-400 text-md focus:outline-none"
              @keyup.enter="userSignUp"
              type="text" placeholder="아이디" v-model="userID" />
            <input
              class="mb-6 border border-gray-200 rounded-lg shadow-sm w-full py-4 pl-4 text-gray-900 placeholder-gray-400 text-md focus:outline-none"
              @keyup.enter="userSignUp"
              type="password" placeholder="비밀번호" v-model="userPassword" />
            <input
              class="mb-6 border border-gray-200 rounded-lg shadow-sm w-full py-4 pl-4 text-gray-900 placeholder-gray-400 text-md focus:outline-none"
              @keyup.enter="userSignUp"
              type="text" placeholder="이름" v-model="userName" />
            <input
              class="mb-6 border border-gray-200 rounded-lg shadow-sm w-full py-4 pl-4 text-gray-900 placeholder-gray-400 text-md focus:outline-none"
              @keyup.enter="userSignUp"
              type="text" placeholder="닉네임" v-model="userNickName" />
            <input
              class="mb-6 border border-gray-200 rounded-lg shadow-sm w-full py-4 pl-4 text-gray-900 placeholder-gray-400 text-md focus:outline-none"
              @keyup.enter="userSignUp"
              type="text" placeholder="전화번호" v-model="userPhone" />
            <input
              class="mb-6 border border-gray-200 rounded-lg shadow-sm w-full py-4 pl-4 text-gray-900 placeholder-gray-400 text-md focus:outline-none"
              @keyup.enter="userSignUp"
              type="text" placeholder="이메일" v-model="userEmail" />
            <div class="mb-6">
              <button class="w-full bg-violet-500 hover:bg-violet-600 py-5 rounded-lg focus:outline-none transition-all duration-300 ease-in-out" @click="userSignUp()">
                <span class="text-white font-bold text-md">회원가입</span>
              </button>
            </div>
            <div class="flex justify-start mb-6">
              <div class="text-purple-500 underline cursor-pointer" @click="movePage('LoginPage')">Login</div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted } from 'vue'

import ApiService from '@/services/ApiService'
import type responseData from '@/interfaces/common/responseData'

import { encryptStringSalt } from '@/utils/common'
import { idReg, passwdReg, nameReg, phoneReg, emailReg } from '@/references/config'

import { useUserStore } from '@/stores/userStore'
import { useToastStore } from '@/stores/toastStore'
import { useRouter } from 'vue-router'

const userStore = useUserStore()
const toastStore = useToastStore()
const router = useRouter()

const userID = ref('')
const userPassword = ref('')
const userName = ref('')
const userNickName = ref('')
const userPhone = ref('')
const userEmail = ref('')

const currentUser = computed(() => userStore.getCurrentUser)

onMounted( async () => {
  if (currentUser.value.at) {
    movePage('MainPage')
    return
  }
})

const movePage = (page: string) => {
  router.push({ name: page }).catch(() => {
    console.log(page, ' Error')
  })
}

const userSignUp = () => {
  if(!idReg.test(userID.value)) {
    toastStore.setToastValue({
      severity: 'warn',
      summary: '회원가입',
      detail: '아이디를 확인해주세요.',
      life: 3000
    })
    return
  }
  if(!passwdReg.test(userPassword.value)) {
    toastStore.setToastValue({
      severity: 'warn',
      summary: '회원가입',
      detail: '비밀번호를 확인해주세요.',
      life: 3000
    })
    return
  }
  if(!nameReg.test(userName.value)) {
    toastStore.setToastValue({
      severity: 'warn',
      summary: '회원가입',
      detail: '이름을 확인해주세요.',
      life: 3000
    })
    return
  }
  if(!phoneReg.test(userPhone.value)) {
    toastStore.setToastValue({
      severity: 'warn',
      summary: '회원가입',
      detail: '전화번호를 확인해주세요.',
      life: 3000
    })
    return
  }
  if(!emailReg.test(userEmail.value)) {
    toastStore.setToastValue({
      severity: 'warn',
      summary: '회원가입',
      detail: '이메일을 확인해주세요.',
      life: 3000
    })
    return
  }
  userSignUpAction()
}

const userSignUpAction = async () => {
  const reqHeader = { accept: 'application/json' }
  const reqData: any = {
    userID: encryptStringSalt(userID.value),
    userPassword: encryptStringSalt(userPassword.value),
    userName: encryptStringSalt(userName.value),
    userNickName: encryptStringSalt(userNickName.value),
    userPhone: encryptStringSalt(userPhone.value),
    userEmail: encryptStringSalt(userEmail.value)
  }
  const signUpResult: responseData = await ApiService.requestAPI({
    headers: reqHeader,
    method: 'POST',
    url: `/board/user/signup`,
    data: reqData
  })
  if (signUpResult.retStatus) {
    toastStore.setToastValue({
      severity: 'success',
      summary: '회원가입',
      detail: '회원가입이 완료되었습니다.',
      life: 3000
    })
    movePage('IndexPage')
  } else {
    toastStore.setToastValue({
      severity: 'error',
      summary: '회원가입',
      detail: signUpResult.retData,
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
<template>
  <div class="text-left text-black">
    <div class="ml-5 text-6xl p-10">회원 등록</div>
  </div>
  <div class="card flex">
    <div class="gap-2 text-left ml-16">
      <label for="userId" class="flex flex-col text-3xl text-black">아이디</label>
      <InputText
        id="userId"
        v-model="userIdValue"
        size="large"
        style="width: 650px; height: 50px; font-size: 20px"
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
      />
    </div>
    <div class="gap-2 text-left ml-16">
      <label for="userPassword" class="flex flex-col text-3xl text-black">비밀번호</label>
      <InputText
        id="userPassword"
        type="password"
        v-model="userPasswordValue"
        size="large"
        style="width: 650px; height: 50px; font-size: 20px"
      />
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
      label="뒤로"
      size="large"
      severity="help"
      class="mr-2"
      @click="goUserManageMemberPage"
    />
    <Button label="등록" size="large" severity="info" class="mr-2" @click="userJoinFunction" />
  </div>
</template>

<script setup>
import InputText from 'primevue/inputtext'
import Button from 'primevue/button'
import ApiService from '@/services/ApiService'
import { useRouter } from 'vue-router'
import { ref } from 'vue'
import { encryptStringSalt } from '@/utils/common'

const router = useRouter()

const userIdValue = ref('')
const userNameValue = ref('')
const userNickNameValue = ref('')
const userPasswordValue = ref('')
const userEmailValue = ref('')
const userPhoneValue = ref('')

const goUserManageMemberPage = () => {
  router.push({ name: 'MemberManagePage' }).catch(() => {
    console.log('MemberManagePageError')
  })
}

const userJoinFunction = () => {
  if (userIdValue.value == null || userIdValue.value == '') alert('아이디를 입력해주세요.')
  else if (userNameValue.value == null || userNameValue.value == '') alert('이름을 입력해주세요.')
  else if (userNickNameValue.value == null || userNickNameValue.value == '')
    alert('닉네임을 입력해주세요.')
  else if (userPasswordValue.value == null || userPasswordValue.value == '')
    alert('비밀번호를 입력해주세요.')
  else if (userEmailValue.value == null || userEmailValue.value == '')
    alert('이메일을 입력해주세요.')
  else if (userPhoneValue.value == null || userPhoneValue.value == '')
    alert('전화번호를 입력해주세요.')
  else userJoinApi()
}

const userJoinApi = async () => {
  let result = await ApiService.requestAPI({
    headers: { accept: 'application/json' },
    method: 'POST',
    url: '/member/join/manage',
    data: {
      id: encryptStringSalt(userId.value),
      name: encryptStringSalt(userName.value),
      password: encryptStringSalt(userPassword.value),
      nickName: encryptStringSalt(userNickName.value),
      phone: encryptStringSalt(userPhone.value),
      email: encryptStringSalt(userEmail.value)
    }
  })
  if (result === 'success') {
    alert('회원등록이 완료되었습니다.')
    goUserManageMemberPage()
  } else if (result === 'duplicateId') {
    alert('이미 가입된 아이디입니다.')
  } else if (result === 'duplicateNickName') {
    alert('이미 가입된 닉네임입니다.')
  } else if (result === 'duplicatePhone') {
    alert('이미 가입된 전화번호입니다.')
  } else {
    alert('회원 등록이 실패했습니다.')
  }
}
</script>

<style lang="scss" scoped></style>

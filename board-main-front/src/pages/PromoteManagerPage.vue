<template>
  <div class="text-left text-black">
    <div class="ml-5 text-6xl p-10">매니저 관리</div>
  </div>
  <div class="card flex">
    <div class="flex flex-col gap-2 text-left ml-16">
      <label for="userId" class="text-3xl text-black">아이디</label>
      <InputText
        id="userId"
        v-model="userIdValue"
        size="large"
        style="width: 700px; height: 50px; font-size: 20px"
      />
    </div>
  </div>
  <div class="card flex mt-10">
    <div class="flex flex-col gap-2 text-left ml-16">
      <label for="userNickName" class="text-3xl text-black">닉네임</label>
      <InputText
        id="userNickName"
        v-model="userNickNameValue"
        size="large"
        style="width: 700px; height: 50px; font-size: 20px"
      />
    </div>
  </div>
  <hr class="mt-16 ml-16 mr-16 mb-10" />
  <div class="text-left mr-16 flex justify-end">
    <Button label="마이페이지" size="large" class="mr-2" @click="goMyPage" />
    <Button
      label="매니저 지정"
      size="large"
      severity="info"
      class="mr-2"
      @click="promoteMangerFunction"
    />
  </div>
</template>

<script setup>
import InputText from 'primevue/inputtext'
import Button from 'primevue/button'
import ApiService from '@/services/ApiService'
import { useRouter } from 'vue-router'
import { useUserStore } from '@/stores/userStore'
import { ref } from 'vue'
import { decryptStringSalt, encryptString, encryptStringSalt } from '@/utils/common'

const router = useRouter()
const userStore = useUserStore()

const userIdValue = ref('')
const userNickNameValue = ref('')

const goMyPage = () => {
  router.push({ name: 'MyPage' }).catch(() => {
    console.log('MyPageError')
  })
}

const goMainPage = () => {
  router.push({ name: 'MainPage' }).catch(() => {
    console.log('MainPageError')
  })
}

const promoteMangerFunction = () => {
  if (encryptString(userIdValue.value) === decryptStringSalt(userStore.getUserAccess.uid))
    alert('매니저로 지정할 아이디를 입력해주세요.')
  else if (userNickNameValue.value === decryptStringSalt(userStore.getUserAccess.unn))
    alert('매니저로 지정할 닉네임를 입력해주세요.')
  else if (userIdValue.value === '' || userIdValue.value === null)
    alert('매니저로 지정할 아이디를 입력해주세요.')
  else if (userNickNameValue.value === '' || userNickNameValue.value === null)
    alert('매니저로 지정할 닉네임를 입력해주세요.')
  else promoteManagerApi()
}

const promoteManagerApi = async () => {
  const result = await ApiService.requestAPI({
    headers: { accept: 'application/json' },
    method: 'PUT',
    url: '/member/promote/manager',
    data: {
      userId: encryptStringSalt(userIdValue.value),
      userNickName: encryptStringSalt(userNickNameValue.value)
    }
  })
  if (result === 'success') {
    alert('매니저 지정이 완료되었습니다.')
    goMainPage()
  } else alert('매니저 지정이 실패했습니다.')
}
</script>

<style lang="scss" scoped></style>

<template>
  <div class="bg-cover bg-center bg-fixed">
    <div class="h-screen flex justify-center items-center">
      <div class="w-full sm:w-80 flex flex-col gap-6 shadow-lg w-full p-16 md:w-1/3 lg:w-1/4">
        <img src="@/assets/board.png" class="mb-10" />

        <IconField>
          <InputIcon>
            <i class="pi pi-user" />
          </InputIcon>
          <InputText v-model="userId" type="text" placeholder="ID" autofocus fluid />
        </IconField>

        <IconField>
          <InputIcon>
            <i class="pi pi-lock" />
          </InputIcon>
          <InputText v-model="userPassword" type="password" placeholder="Password" fluid />
        </IconField>

        <Button @click="loginFunction" label="로그인" />
        <div>
          <Button @click="signUpFunction" label="회원가입" class="md:w-1/2" />
          <Button @click="changePasswordFunction" label="비밀번호 재설정" class="md:w-1/2" />
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import IconField from 'primevue/iconfield'
import InputIcon from 'primevue/inputicon'
import InputText from 'primevue/inputtext'
import Button from 'primevue/button'
import ApiService from '@/services/ApiService'
import { ref } from 'vue'
import { useRouter } from 'vue-router'

const userId = ref('')
const userPassword = ref('')

let result = null
const router = useRouter()

const loginFunction = async () => {
  result = await ApiService.requestAPI({
    headers: { accept: 'application/json' },
    method: 'POST',
    url: '/login',
    data: {
      id: userId.value,
      password: userPassword.value
    }
  })
  console.log(result)
}

const changePasswordFunction = () =>
  setTimeout(() => {
    router.push({ name: 'ResetPasswordPage' }).catch(() => {
      console.log('resetPasswordError')
    })
  }, 2000)
</script>

<style lang="scss" scoped></style>

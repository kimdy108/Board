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
          <InputText
            v-model="userPassword"
            :type="isVisiblePassword ? 'text' : 'password'"
            placeholder="Password"
            fluid
          />
          <InputIcon>
            <i
              :class="isVisiblePassword ? 'pi pi-eye' : 'pi pi-eye-slash'"
              @click="changeIsVisiblePassword"
            />
          </InputIcon>
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

const isVisiblePassword = ref(false)

let result = null
const router = useRouter()

const loginFunction = async () => {
  result = await ApiService.requestAPI({
    headers: { accept: 'application/json' },
    method: 'POST',
    url: '/member/login',
    data: {
      id: userId.value,
      password: userPassword.value
    }
  })
  if (result === 'success') alert('success')
  else alert('fail')
}

const signUpFunction = () => {
  router.push({ name: 'SignUpPage' }).catch(() => {
    console.log('signUpError')
  })
}

const changePasswordFunction = () => {
  router.push({ name: 'ResetPasswordPage' }).catch(() => {
    console.log('resetPasswordError')
  })
}

const changeIsVisiblePassword = () => {
  isVisiblePassword.value = !isVisiblePassword.value
}
</script>

<style lang="scss" scoped></style>

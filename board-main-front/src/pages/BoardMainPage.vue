<template>
  <div>
    <div>Board Main</div>
    <Button label="logout" @click="logOutFunction"></Button>
    <Button label="test" @click="testFunction"></Button>
  </div>
</template>

<script setup>
import { useUserStore } from '@/stores/userStore'
import { useRouter } from 'vue-router'
import ApiService from '@/services/ApiService'
import Button from 'primevue/button'

const useStore = useUserStore()
const router = useRouter()

const logOutFunction = () => {
  useStore.setUserLogout()
  router.push({ name: 'LoginPage' }).catch(() => {
    console.log('loginPageError')
  })
}
const testFunction = async () => {
  const result = await ApiService.requestAPI({
    headers: { accept: 'application/json' },
    method: 'GET',
    url: '/server/test'
  })
  console.log(result)
}
</script>

<style lang="scss" scoped></style>

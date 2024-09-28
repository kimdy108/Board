<template>
  <div>
    <div>Board Main</div>
    <Button label="logout" @click="logOutFunction"></Button>
  </div>
</template>

<script setup>
import { useUserStore } from '@/stores/userStore'
import { useRouter } from 'vue-router'
import Button from 'primevue/button'

const useStore = useUserStore()
const router = useRouter()

const logOutFunction = () => {
  useStore.setUserLogout()
  router.push({ name: 'LoginPage' }).catch(() => {
    console.log('loginPageError')
  })
}
</script>

<style lang="scss" scoped></style>

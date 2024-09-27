<template></template>

<script setup>
import dayjs from 'dayjs'
import { onMounted, computed } from 'vue'
import { useRouter } from 'vue-router'
import { useBaseStore } from '@/stores/baseStore'
import { useUserStore } from '@/stores/userStore'

const router = useRouter()
const userStore = useUserStore()
const baseStore = useBaseStore()
const currentUser = computed(() => userStore.getUserAccess)
const currentDate = dayjs().format('YYYY-MM-DD HH:mm:ss')

onMounted(async () => {
  console.log('Start Index Page', currentDate)
  if (!baseStore.getServerStatus) {
    router.push({ name: 'ErrorPage' }).catch(() => {
      console.log('error')
    })
    return
  }
  if (currentUser.value.at) {
    router.push({ name: 'MainPage' }).catch(() => {
      console.log('mainerror')
    })
  } else {
    router.push({ name: 'LoginPage' }).catch(() => {
      console.log('loginerror')
    })
  }
})
</script>

<style lang="scss" scoped></style>

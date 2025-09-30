<template>
  <div class="w-full bg-green-50">
    <LoadingCircle></LoadingCircle>
  </div>
</template>

<script setup lang="ts">
import dayjs from 'dayjs'
import { useRouter } from 'vue-router'
import { computed, onMounted } from 'vue'

import { useUserStore } from '@/stores/userStore'

import LoadingCircle from '@/components/common/LoadingCircle.vue'

const router = useRouter()

const userStore = useUserStore()

const currentUser = computed(() => userStore.getCurrentUser)

const currentDate = dayjs().format('YYYY-MM-DD HH:mm:ss')
const currentEnv = process.env.VUE_APP_PROJECT_NAME

onMounted( async () => {
  console.log("Start Index Page", currentDate, currentEnv)

  if (currentUser.value.at) {
    setTimeout(() => {
      router.push({ name: 'MainPage' }).catch(() => {
        console.log('mainerror')
      })
    }, 2000)
  }
  else {
    setTimeout(() => {
      router.push({ name: 'LoginPage' }).catch(() => {
        console.log('loginerror')
      })
    }, 2000)
  }
})
</script>

<style lang="css" scoped>

</style>
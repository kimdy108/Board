<template>
  <router-view></router-view>
</template>

<script setup>
import { onMounted } from 'vue'
import ApiService from '@/services/ApiService'

let serverBaseInfo = null

onMounted(() => {
  getServerInfo()
})

const getServerInfo = async () => {
  serverBaseInfo = await ApiService.requestAPI({
    headers: { accept: 'application/json' },
    method: 'GET',
    url: '/server/info'
  })
  // if (serverBaseInfo != null) {
  //   baseStore.setServerStatus(true)
  // }
}
</script>

<style lang="scss" scoped></style>

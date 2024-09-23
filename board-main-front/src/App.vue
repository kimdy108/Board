<template>
  <router-view></router-view>
</template>

<script setup>
import { onMounted } from 'vue'
import { useBaseStore } from "@/stores/baseStore.js";
import ApiService from '@/services/ApiService'

let serverBaseInfo = null
const baseStore = useBaseStore()

onMounted(() => {
  getServerInfo()
})

const getServerInfo = async () => {
  serverBaseInfo = await ApiService.requestAPI({
    headers: { accept: 'application/json' },
    method: 'GET',
    url: '/server/info'
  })
  if (serverBaseInfo === 'success') {
    baseStore.setServerStatus(true)
  }
}
</script>

<style lang="scss" scoped></style>

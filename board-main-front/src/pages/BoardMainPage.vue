<template>
  <div>
    <div>Board Main</div>
    <Button label="test" @click="testFunction"></Button>
  </div>
</template>

<script setup>
import { useUserStore } from '@/stores/userStore'
import { useRouter } from 'vue-router'
import ApiService from '@/services/ApiService'
import Button from 'primevue/button'

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

<template>
  <Toast class="text-left" />
  <router-view></router-view>
</template>

<script setup>
import { onBeforeMount, ref, watch } from 'vue'
import { useBaseStore } from '@/stores/baseStore.js'
import { useToastStore } from '@/stores/toastStore.js'
import ApiService from '@/services/ApiService'
import Toast from 'primevue/toast'
import { useToast } from 'primevue/usetoast'

const toast = useToast()

let serverBaseInfo = null
const baseStore = useBaseStore()
const toastStore = useToastStore()

const toastSeverity = ref()
const toastSummary = ref()
const toastDetail = ref()
const toastLife = ref()

onBeforeMount(() => {
  getServerInfo()
})

const showToast = () => {
  toast.add({
    severity: toastSeverity.value,
    summary: toastSummary.value,
    detail: toastDetail.value,
    life: toastLife.value
  })
  toastStore.setToastReset()
}

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

watch(
  () => toastStore.getToastValue,
  (newValue) => {
    if (newValue.severity && newValue.summary && newValue.detail) {
      toastSeverity.value = newValue.severity
      toastSummary.value = newValue.summary
      toastDetail.value = newValue.detail
      toastLife.value = newValue.life
      showToast()
    }
  }
)
</script>

<style lang="scss" scoped></style>

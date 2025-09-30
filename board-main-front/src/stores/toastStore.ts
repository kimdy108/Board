import { ref, computed } from 'vue'
import { defineStore } from 'pinia'

import type ToastStorage from '@/interfaces/store/toastStorage'

export const useToastStore = defineStore('toast', () => {
  const toastValue = ref({ severity: null, summary: null, detail: null, life: null })

  const getToastValue = computed(() => {
    return toastValue.value
  })

  function setToastValue(newToastValue: ToastStorage) {
    toastValue.value = newToastValue
  }

  function setToastReset() {
    toastValue.value = { severity: null, summary: null, detail: null, life: null }
  }

  return {
    getToastValue,
    setToastValue,
    setToastReset
  }
})
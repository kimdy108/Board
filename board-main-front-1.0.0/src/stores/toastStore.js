import { defineStore } from 'pinia'
import { computed, ref } from 'vue'

export const useToastStore = defineStore('toast', () => {
  const toastValue = ref({ severity: null, summary: null, detail: null, life: null })

  const getToastValue = computed(() => {
    return toastValue.value
  })

  function setToastValue(newToastValue) {
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

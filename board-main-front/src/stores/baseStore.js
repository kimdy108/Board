import { ref, computed } from 'vue'
import { defineStore } from 'pinia'

export const useBaseStore = defineStore('base', () => {
  const serverStatus = ref(true)

  const getServerStatus = computed(() => serverStatus.value)

  function setServerStatus(newServerStatus) {
    serverStatus.value = newServerStatus
  }

  return {
    getServerStatus,
    setServerStatus
  }
})

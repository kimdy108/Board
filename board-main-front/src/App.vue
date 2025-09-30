<template>
  <Toast class="text-left" />
  <router-view/>
</template>

<script setup lang="ts">
import { ref, onBeforeMount, watch } from 'vue'
import { useToastStore } from '@/stores/toastStore'

import Toast from 'primevue/toast'
import { useToast } from 'primevue/usetoast'
import { updatePreset } from '@primevue/themes';

import type ToastStorage from '@/interfaces/store/toastStorage'

onBeforeMount(() => {
  updatePreset(getPresetExt());
})

const toast = useToast()

const toastStore = useToastStore()

const toastSeverity = ref()
const toastSummary = ref()
const toastDetail = ref()
const toastLife = ref()

const showToast = () => {
  toast.add({
    severity: toastSeverity.value,
    summary: toastSummary.value,
    detail: toastDetail.value,
    life: toastLife.value
  })
  toastStore.setToastReset()
}

watch(
  () => toastStore.getToastValue,
  (newValue: ToastStorage) => {
    if (newValue.severity && newValue.summary && newValue.detail) {
      toastSeverity.value = newValue.severity
      toastSummary.value = newValue.summary
      toastDetail.value = newValue.detail
      toastLife.value = newValue.life
      showToast()
    }
  }
)

const baseColor = {
  name: 'purple',
  palette: {
    50: '#faf5ff',
    100: '#f3e8ff',
    200: '#e9d5ff',
    300: '#d8b4fe',
    400: '#c084fc',
    500: '#a855f7',
    600: '#9333ea',
    700: '#7e22ce',
    800: '#6b21a8',
    900: '#581c87',
    950: '#3b0764',
  },
}

const getPresetExt = () => {
  return {
    semantic: {
      primary: baseColor.palette,
      colorScheme: {
        light: {
          primary: {
            color: '{primary.500}',
            contrastColor: '#ffffff',
            hoverColor: '{primary.600}',
            activeColor: '{primary.700}',
          },
          highlight: {
            background: '{primary.50}',
            focusBackground: '{primary.100}',
            color: '{primary.700}',
            focusColor: '{primary.800}',
          },
        },
      },
    }
  };
}

</script>
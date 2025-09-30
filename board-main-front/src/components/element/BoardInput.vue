<template>
  <div class="w-full text-left mb-3">
    <div class="pl-1">
      <span class="text-base text-gray-700">{{ inputTitle }}</span> <span v-if="isRequire" class="text-base mx-1 font-bold text-orange-500">*</span>
    </div>
  </div>
</template>

<script setup lang="ts">
import { defineModel } from 'vue'
import type boardInputProps from '@/interfaces/element/boardInputProps'

const props = defineProps<boardInputProps>()
const inputValue = defineModel('inputValue', { required: true })
</script>
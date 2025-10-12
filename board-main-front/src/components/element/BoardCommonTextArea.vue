<template>
  <div>
    <div class="w-full text-left mb-3">
      <div class="pl-1">
        <span class="text-base text-gray-700">{{ inputTitle }}</span>
      </div>
      <div class="mt-3">
        <textarea 
          class="w-full border border-gray-200 rounded-lg bg-white py-3 px-5 focus:outline-none"
          v-model="inputValue"
          style="height: 150px;"
        ></textarea>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { defineModel } from 'vue'
import type boardTextareaProps from '@/interfaces/element/boardTextareaProps'

defineProps<boardTextareaProps>()
const inputValue = defineModel('inputValue', { required: true })
</script>
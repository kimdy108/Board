<template>
  <div class="w-full text-left">
    <div class="mx-5">
      <div>
        <div class="flex">
          <input 
            type="text" 
            class="w-full search-input border border-gray-300 border-e-0 bg-white text-gray-700 rounded-s-lg placeholder-gray-300 py-2 px-5 shadow-sm focus:outline-none focus:shadow-outline"
            v-model="inputValue"
            @keyup.enter="searchSubmit"
          />
          <span class="px-4 inline-flex items-center min-w-fit rounded-e-lg border border-s-0 border-gray-300 bg-white text-base text-sm-gray-500 font-bold">
            <i class="pi pi-search"></i>
          </span>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import Select from 'primevue/select';
import type boardSearchProps from '@/interfaces/element/boardSearchProps'

const props = defineProps<boardSearchProps>()
const inputValue = defineModel('inputValue', { required: true })
const emit = defineEmits<{
  searchSubmit: [],
}>()

const searchSubmit = () => {
  emit('searchSubmit')
}
</script>

<style lang="sass" scoped>

</style>
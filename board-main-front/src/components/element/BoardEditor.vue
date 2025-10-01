<template>
  <div class="mb-3">
    <div class="card flex justify-center ">
      <Editor v-model="inputValue" class="h-96 w-3/4" :readonly="isReadOnly">
        <template v-slot:toolbar>
          <span class="ql-formats" v-if="!isReadOnly">
            <select v-tooltip.bottom="'Size'" class="ql-size">
              <option value="huge">Head</option>
              <option value="large">SubHead</option>
              <option selected>Normal</option>
              <option value="small">Small</option>
            </select>
            <button v-tooltip.bottom="'Bold'" class="ql-bold"></button>
            <button v-tooltip.bottom="'Italic'" class="ql-italic"></button>
            <button v-tooltip.bottom="'Underline'" class="ql-underline"></button>
            <button v-tooltip.bottom="'Strike'" class="ql-strike"></button>
            <button v-tooltip.bottom="'Ordered List'" class="ql-list" value="ordered"></button>
            <select v-tooltip.bottom="'Align'" class="ql-align">
              <option selected></option>
              <option value="center"></option>
              <option value="right"></option>
              <option value="justify"></option>
            </select>
            <button v-tooltip.bottom="'Color'" class="ql-color"></button>
            <button v-tooltip.bottom="'Background'" class="ql-background"></button>
            <button v-tooltip.bottom="'Code'" class="ql-code"></button>
          </span>
        </template>
      </Editor>
    </div>
  </div>
</template>

<script setup lang="ts">
import { computed } from 'vue'
import Editor from 'primevue/editor';
import type boardEditorProps from '@/interfaces/element/boardEditorProps'

const props = defineProps<boardEditorProps>()
const inputValue = defineModel('inputValue', { required: true })

const isReadOnly = computed(() => {
  return props.isReadOnly
})
</script>

<style lang="css" scoped>
::v-deep(.ql-toolbar) {
  display: flex;
  justify-content: flex-start !important;
  flex-wrap: wrap;
}
</style>
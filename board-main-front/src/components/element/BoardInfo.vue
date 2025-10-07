<template>
  <div class="text-left text-4xl">
    {{ title }} <i v-if="isUpdate()" class="pi pi-pencil ml-1 !text-xl"></i>
  </div>

  <div class="mb-5">
    <div v-if="viewCount != undefined" class="text-ml font-bold flex justify-end my-1">조회수 : {{ viewCount }}</div>
    <div class="text-ml font-bold flex justify-end my-1">작성자 : {{ author }}</div>
    <div class="text-ml font-bold flex justify-end my-1">등록일 : {{ insertDate }}</div>
  </div>

  <div class="w-full border border-gray-300 my-10"></div>

  <div class="text-left text-xl" v-html="content"></div>

  <div class="w-full border border-gray-300 my-10"></div>
</template>

<script setup lang="ts">
import type boardInfoProps from '@/interfaces/element/boardInfoProps'

const props = defineProps<boardInfoProps>()

const isUpdate = () => {
  return props.insertDate != props.updateDate
}

</script>

<style lang="scss" scoped>

</style>
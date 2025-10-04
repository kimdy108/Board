<template>
  <Dialog :visible="showModal" 
      modal 
      :closable="false" 
      :draggable="false"
      class="w-2/3"
    >
      <template #header>
        <div class="w-full flex justify-between items-center border-b border-gray-100 pb-6">
          <div class="text-xl text-gray-900">댓글</div>
        </div>
      </template>

      <template #footer>
        <div>
          <button class="border border-gray-200" @click="closeCommentModal">닫기</button>
        </div>
      </template>
  </Dialog>
</template>

<script setup lang="ts">
import { Dialog } from 'primevue';
import type commonModalProps from '@/interfaces/common/commonModalProps';

const props = defineProps<commonModalProps>()
const emit = defineEmits<{
  closeCommentModal: []
}>()

const closeCommentModal = () => {
  emit('closeCommentModal')
}


</script>

<style lang="scss" scoped>

</style>
<template>
  <div class="flex">
    <div class="w-full">
      <MainHeader />
      <perfect-scrollbar class="overflow-hidden w-full board-main-contents flex flex-col justify-between items-stretch bg-gray-50 dark:bg-gray-700" id="board-main">
        <div class="flex justify-center">
          <main class="py-8 px-10 w-3/4">
            <router-view />
          </main>
        </div>
      </perfect-scrollbar>
    </div>
  </div>
</template>

<script setup lang="ts">
import MainHeader from "@/layouts/MainHeader.vue";
</script>

<style lang="css">
.board-main-contents {
  height: calc(100vh - 72px);
}
</style>
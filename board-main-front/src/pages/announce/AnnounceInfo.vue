<template>
  <div>
    <div class="text-left mb-10">
      <div class="text-2xl font-bold text-gray-900">공지사항</div>
    </div>

    <div class="w-full">
      <div class="text-left text-4xl">
        {{ announceTitle }}
      </div>

      <div class="mb-5">
        <div class="text-ml font-bold flex justify-end my-1">작성자 : 관리자</div>
        <div class="text-ml font-bold flex justify-end my-1">조회수 : {{ announceViewCounter }}</div>
      </div>

      <div class="w-full border border-gray-300 my-10"></div>

      <div class="text-left text-xl" v-html="announceContent"></div>

      <div class="w-full border border-gray-300 my-10"></div>

      <div class="flex justify-end">
        <Button class="m-2 !px-4 !py-3 !bg-sky-400 !border !border-sky-400 hover:!bg-sky-500 hover:!border hover:!border-sky-500" @click="moveMainPage">메인</Button>
        <Button v-if="isMaster()" class="m-2 !px-4 !py-3 !bg-orange-400 !border !border-orange-400 hover:!bg-orange-500 hover:!border hover:!border-orange-500" @click="moveUpdatePage">수정</Button>
        <Button v-if="isMaster()" class="m-2 !px-4 !py-3 !bg-red-400 !border !border-red-400 hover:!bg-red-500 hover:!border hover:!border-red-500" @click="deleteAction">삭제</Button>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from "vue";
import { useRoute, useRouter } from "vue-router";
import { useUserStore } from "@/stores/userStore";
import { useToastStore } from "@/stores/toastStore"
import { userRoleList } from "@/references/config";
import { decryptStringSalt } from "@/utils/common";

import Button from 'primevue/button'

import responseData from "@/interfaces/common/responseData";
import ApiService from "@/services/ApiService";

onMounted(() => {
  announceUUID.value = history.state.uuid
  announceTitle.value = ''
  announceContent.value = ''
  announceViewCounter.value = 0
  announceAuthor.value = ''

  getAnnounceInfo()
})

const userStore = useUserStore()
const toastStore = useToastStore()

const route = useRoute()
const router = useRouter();

const announceUUID = ref('')
const announceTitle = ref('')
const announceContent = ref('')
const announceViewCounter = ref()
const announceAuthor = ref('')

const isMaster = () => {
  return userRoleList.findIndex(x => x.value == decryptStringSalt(userStore.getCurrentUser)) < 2
}

const moveMainPage = () => {
  router.push({ name: 'AnnounceMain' }).catch(() => {
    console.log('AnnounceMain Error')
  })
}

const moveUpdatePage = () => {
  router.push({ name: 'AnnounceUpdate', state: {uuid: announceUUID.value} }).catch(() => {
    console.log('AnnounceUpdate Error')
  })
}

const getAnnounceInfo = async () => {
  const reqHeader = { accept: 'application/json' }
  const infoResult: responseData = await ApiService.requestAPI({
    headers: reqHeader,
    method: 'GET',
    url: `/board/announce/info/${announceUUID.value}`,
  })
  if (infoResult.retStatus) {
    announceTitle.value = infoResult.retData.announceTitle
    announceContent.value = infoResult.retData.announceContent
    announceViewCounter.value = infoResult.retData.viewCounter
    announceAuthor.value = infoResult.retData.memberName
  }
}

const deleteAction = async () => {
  const reqHeader = { accept: 'application/json' }
  const deleteResult: responseData = await ApiService.requestAPI({
    headers: reqHeader,
    method: 'DELETE',
    url: `/board/announce/delete/${announceUUID.value}`
  })
  if (deleteResult.retStatus) {
    toastStore.setToastValue({
      severity: 'success',
      summary: '공지사항 관리',
      detail: '공지사항 삭제 되었습니다.',
      life: 3000
    })
    router.push({ name: 'AnnounceAdmin' }).catch(() => {
      console.log('AnnounceAdmin Error')
    })
  }
}
</script>

<style lang="css" scoped>

</style>
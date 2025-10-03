<template>
  <div>
    <div class="text-left mb-10">
      <div class="text-2xl font-bold text-gray-900">공지사항</div>
    </div>

    <div class="w-full">

      <BoardInfo :title="announceTitle" :content="announceContent" :viewCounter="announceViewCounter" author="관리자" :insertDate="announceInsertDate" :updateDate="announceUpdateDate" />

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
import { useRouter } from "vue-router";
import { useUserStore } from "@/stores/userStore";
import { useToastStore } from "@/stores/toastStore"
import { userRoleList } from "@/references/config";
import { decryptStringSalt } from "@/utils/common";

import Button from 'primevue/button'
import BoardInfo from '@/components/element/BoardInfo.vue'
import responseData from "@/interfaces/common/responseData";
import ApiService from "@/services/ApiService";

const userStore = useUserStore()
const toastStore = useToastStore()

const router = useRouter();

const announceUUID = ref('')
const announceTitle = ref('')
const announceContent = ref('')
const announceViewCounter = ref()
const announceInsertDate = ref('')
const announceUpdateDate = ref('')

onMounted(() => {
  announceUUID.value = history.state.uuid
  announceTitle.value = ''
  announceContent.value = ''
  announceViewCounter.value = 0
  announceInsertDate.value = ''
  announceUpdateDate.value = ''

  getAnnounceInfo()
})

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
    announceInsertDate.value = infoResult.retData.insertDate.replace("T", " ")
    announceUpdateDate.value = infoResult.retData.updateDate.replace("T", " ")
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
  } else {
    toastStore.setToastValue({
      severity: 'error',
      summary: '공지사항 관리',
      detail: deleteResult.retData,
      life: 3000
    })
  }
}
</script>

<style lang="css" scoped>

</style>
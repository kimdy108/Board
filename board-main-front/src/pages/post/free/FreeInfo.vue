<template>
  <div>
    <div class="text-left mb-10">
      <div class="text-2xl font-bold text-gray-900">자유 게시판</div>
    </div>

    <div class="w-full">

      <BoardInfo :title="freeTitle" :content="freeContent" :viewCount="freeViewCount" :author="freeAuthorName" :insertDate="freeInsertDate" :updateDate="freeUpdateDate" />

      <div class="flex justify-end">
        <Button class="m-2 !px-4 !py-3 !bg-sky-400 !border !border-sky-400 hover:!bg-sky-500 hover:!border hover:!border-sky-500" @click="moveMainPage">메인</Button>
        <Button class="m-2 !px-4 !py-3 !bg-amber-400 !border !border-amber-400 hover:!bg-amber-500 hover:!border hover:!border-amber-500" @click="showCommentModal">댓글</Button>
        <Button v-if="isOwner()" class="m-2 !px-4 !py-3 !bg-orange-400 !border !border-orange-400 hover:!bg-orange-500 hover:!border hover:!border-orange-500" @click="moveUpdatePage">수정</Button>
        <Button v-if="isOwner() || isMaster()" class="m-2 !px-4 !py-3 !bg-red-400 !border !border-red-400 hover:!bg-red-500 hover:!border hover:!border-red-500" @click="deleteAction">삭제</Button>
      </div>
    </div>

    <FreeComment :infoUUID="freeUUID" :infoAuthorUUID="freeAuthorUUID" :showModal="isCommentModal" @closeCommentModal="closeCommentModal" />
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

import FreeComment from "./FreeComment.vue";

const userStore = useUserStore()
const toastStore = useToastStore()

const router = useRouter();

const freeUUID = ref('')
const freeTitle = ref('')
const freeContent = ref('')
const freeViewCount = ref()
const freeAuthorName = ref('')
const freeAuthorUUID = ref('')
const freeInsertDate = ref('')
const freeUpdateDate = ref('')

const isCommentModal = ref(false)

onMounted(() => {
  freeUUID.value = history.state.uuid
  freeTitle.value = ''
  freeContent.value = ''
  freeViewCount.value = 1
  freeAuthorName.value = ''
  freeAuthorUUID.value = ''
  freeInsertDate.value = ''
  freeUpdateDate.value = ''

  getFreeInfo()
})

const showCommentModal = () => {
  isCommentModal.value = true
}

const closeCommentModal = () => {
  isCommentModal.value = false
}

const isOwner = () => {
  return freeAuthorUUID.value == decryptStringSalt(userStore.getCurrentUser.uud)
}

const isMaster = () => {
  return userRoleList.findIndex(x => x.value == decryptStringSalt(userStore.getCurrentUser)) < 2
}

const moveMainPage = () => {
  router.push({ name: 'FreeMain' }).catch(() => {
    console.log('FreeMain Error')
  })
}

const moveUpdatePage = () => {
  router.push({ name: 'FreeUpdate', state: {uuid: freeUUID.value} }).catch(() => {
    console.log('FreeUpdate Error')
  })
}

const getFreeInfo = async () => {
  const reqHeader = { accept: 'application/json' }
  const infoResult: responseData = await ApiService.requestAPI({
    headers: reqHeader,
    method: 'GET',
    url: `/board/post/info/${freeUUID.value}`,
  })
  if (infoResult.retStatus) {
    freeTitle.value = infoResult.retData.postTitle
    freeContent.value = infoResult.retData.postContent
    freeViewCount.value = infoResult.retData.viewCount
    freeAuthorName.value = decryptStringSalt(infoResult.retData.memberName)
    freeAuthorUUID.value = infoResult.retData.memberUUID
    freeInsertDate.value = infoResult.retData.insertDate.replace("T", " ")
    freeUpdateDate.value = infoResult.retData.updateDate.replace("T", " ")
  }
}

const deleteAction = async () => {
  const reqHeader = { accept: 'application/json' }
  const deleteResult: responseData = await ApiService.requestAPI({
    headers: reqHeader,
    method: 'DELETE',
    url: `/board/post/delete/${freeUUID.value}`
  })
  if (deleteResult.retStatus) {
    toastStore.setToastValue({
      severity: 'success',
      summary: '자유 게시판 관리',
      detail: '자유 게시판 삭제 되었습니다.',
      life: 3000
    })
    router.push({ name: 'FreeAdmin' }).catch(() => {
      console.log('FreeAdmin Error')
    })
  } else {
    toastStore.setToastValue({
      severity: 'error',
      summary: '자유 게시판 관리',
      detail: deleteResult.retData,
      life: 3000
    })
  }
}
</script>

<style lang="css" scoped>

</style>
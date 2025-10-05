<template>
  <div>
    <div class="text-left mb-10">
      <div class="text-2xl font-bold text-gray-900">개발</div>
    </div>

    <div class="w-full">

      <BoardInfo :title="developmentTitle" :content="developmentContent" :viewCount="developmentViewCount" :author="developmentAuthorName" :insertDate="developmentInsertDate" :updateDate="developmentUpdateDate" />

      <div class="flex justify-end">
        <Button class="m-2 !px-4 !py-3 !bg-sky-400 !border !border-sky-400 hover:!bg-sky-500 hover:!border hover:!border-sky-500" @click="moveMainPage">메인</Button>
        <Button class="m-2 !px-4 !py-3 !bg-amber-400 !border !border-amber-400 hover:!bg-amber-500 hover:!border hover:!border-amber-500" @click="showCommentModal">댓글</Button>
        <Button v-if="isOwner()" class="m-2 !px-4 !py-3 !bg-orange-400 !border !border-orange-400 hover:!bg-orange-500 hover:!border hover:!border-orange-500" @click="moveUpdatePage">수정</Button>
        <Button v-if="isOwner() || isMaster()" class="m-2 !px-4 !py-3 !bg-red-400 !border !border-red-400 hover:!bg-red-500 hover:!border hover:!border-red-500" @click="deleteAction">삭제</Button>
      </div>
    </div>

    <DevelopmentComment :infoUUID="developmentUUID" :infoAuthorUUID="developmentAuthorUUID" :showModal="isCommentModal" @closeCommentModal="closeCommentModal" />
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

import DevelopmentComment from './DevelopmentComment.vue'

const userStore = useUserStore()
const toastStore = useToastStore()

const router = useRouter();

const developmentUUID = ref('')
const developmentTitle = ref('')
const developmentContent = ref('')
const developmentViewCount = ref()
const developmentAuthorName = ref('')
const developmentAuthorUUID = ref('')
const developmentInsertDate = ref('')
const developmentUpdateDate = ref('')

const isCommentModal = ref(false)

onMounted(() => {
  developmentUUID.value = history.state.uuid
  developmentTitle.value = ''
  developmentContent.value = ''
  developmentViewCount.value = 1
  developmentAuthorName.value = ''
  developmentAuthorUUID.value = ''
  developmentInsertDate.value = ''
  developmentUpdateDate.value = ''

  getDevelopmentInfo()
})

const showCommentModal = () => {
  isCommentModal.value = true
}

const closeCommentModal = () => {
  isCommentModal.value = false
}

const isOwner = () => {
  return developmentAuthorUUID.value == decryptStringSalt(userStore.getCurrentUser.uud)
}

const isMaster = () => {
  return userRoleList.findIndex(x => x.value == decryptStringSalt(userStore.getCurrentUser)) < 2
}

const moveMainPage = () => {
  router.push({ name: 'DevelopmentMain' }).catch(() => {
    console.log('DevelopmentMain Error')
  })
}

const moveUpdatePage = () => {
  router.push({ name: 'DevelopmentUpdate', state: {uuid: developmentUUID.value} }).catch(() => {
    console.log('DevelopmentUpdate Error')
  })
}

const getDevelopmentInfo = async () => {
  const reqHeader = { accept: 'application/json' }
  const infoResult: responseData = await ApiService.requestAPI({
    headers: reqHeader,
    method: 'GET',
    url: `/board/post/info/${developmentUUID.value}`,
  })
  if (infoResult.retStatus) {
    developmentTitle.value = infoResult.retData.postTitle
    developmentContent.value = infoResult.retData.postContent
    developmentViewCount.value = infoResult.retData.viewCount
    developmentAuthorName.value = decryptStringSalt(infoResult.retData.memberName)
    developmentAuthorUUID.value = infoResult.retData.memberUUID
    developmentInsertDate.value = infoResult.retData.insertDate.replace("T", " ")
    developmentUpdateDate.value = infoResult.retData.updateDate.replace("T", " ")
  }
}

const deleteAction = async () => {
  const reqHeader = { accept: 'application/json' }
  const deleteResult: responseData = await ApiService.requestAPI({
    headers: reqHeader,
    method: 'DELETE',
    url: `/board/post/delete/${developmentUUID.value}`
  })
  if (deleteResult.retStatus) {
    toastStore.setToastValue({
      severity: 'success',
      summary: '개발 게시판 관리',
      detail: '개발 게시판 삭제 되었습니다.',
      life: 3000
    })
    router.push({ name: 'DevelopmentAdmin' }).catch(() => {
      console.log('DevelopmentAdmin Error')
    })
  } else {
    toastStore.setToastValue({
      severity: 'error',
      summary: '개발 게시판 관리',
      detail: deleteResult.retData,
      life: 3000
    })
  }
}
</script>

<style lang="css" scoped>

</style>
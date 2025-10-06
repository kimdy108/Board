<template>
  <div>
    <div class="text-left mb-10">
      <div class="text-2xl font-bold text-gray-900">스택 게시판</div>
    </div>

    <div class="w-full">

      <BoardInfo :title="stackTitle" :content="stackContent" :viewCount="stackViewCount" :author="stackAuthorName" :insertDate="stackInsertDate" :updateDate="stackUpdateDate" />

      <div class="flex justify-end">
        <Button class="m-2 !px-4 !py-3 !bg-sky-400 !border !border-sky-400 hover:!bg-sky-500 hover:!border hover:!border-sky-500" @click="moveMainPage">메인</Button>
        <Button class="m-2 !px-4 !py-3 !bg-amber-400 !border !border-amber-400 hover:!bg-amber-500 hover:!border hover:!border-amber-500" @click="showCommentModal">댓글</Button>
        <Button v-if="isOwner()" class="m-2 !px-4 !py-3 !bg-orange-400 !border !border-orange-400 hover:!bg-orange-500 hover:!border hover:!border-orange-500" @click="moveUpdatePage">수정</Button>
        <Button v-if="isOwner() || isMaster()" class="m-2 !px-4 !py-3 !bg-red-400 !border !border-red-400 hover:!bg-red-500 hover:!border hover:!border-red-500" @click="deleteAction">삭제</Button>
      </div>
    </div>

    <StackComment :infoUUID="stackUUID" :infoAuthorUUID="stackAuthorUUID" :showModal="isCommentModal" @closeCommentModal="closeCommentModal" />
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

import StackComment from "./StackComment.vue";

const userStore = useUserStore()
const toastStore = useToastStore()

const router = useRouter();

const stackUUID = ref('')
const stackTitle = ref('')
const stackContent = ref('')
const stackViewCount = ref()
const stackAuthorName = ref('')
const stackAuthorUUID = ref('')
const stackInsertDate = ref('')
const stackUpdateDate = ref('')

const isCommentModal = ref(false)

onMounted(() => {
  stackUUID.value = history.state.uuid
  stackTitle.value = ''
  stackContent.value = ''
  stackViewCount.value = 1
  stackAuthorName.value = ''
  stackAuthorUUID.value = ''
  stackInsertDate.value = ''
  stackUpdateDate.value = ''

  getStackInfo()
})

const showCommentModal = () => {
  isCommentModal.value = true
}

const closeCommentModal = () => {
  isCommentModal.value = false
}

const isOwner = () => {
  return stackAuthorUUID.value == decryptStringSalt(userStore.getCurrentUser.uud)
}

const isMaster = () => {
  return userRoleList.findIndex(x => x.value == decryptStringSalt(userStore.getCurrentUser)) < 2
}

const moveMainPage = () => {
  router.push({ name: 'StackMain' }).catch(() => {
    console.log('StackMain Error')
  })
}

const moveUpdatePage = () => {
  router.push({ name: 'StackUpdate', state: {uuid: stackUUID.value} }).catch(() => {
    console.log('StackUpdate Error')
  })
}

const getStackInfo = async () => {
  const reqHeader = { accept: 'application/json' }
  const infoResult: responseData = await ApiService.requestAPI({
    headers: reqHeader,
    method: 'GET',
    url: `/board/post/info/${stackUUID.value}`,
  })
  if (infoResult.retStatus) {
    stackTitle.value = infoResult.retData.postTitle
    stackContent.value = infoResult.retData.postContent
    stackViewCount.value = infoResult.retData.viewCount
    stackAuthorName.value = decryptStringSalt(infoResult.retData.memberName)
    stackAuthorUUID.value = infoResult.retData.memberUUID
    stackInsertDate.value = infoResult.retData.insertDate.replace("T", " ")
    stackUpdateDate.value = infoResult.retData.updateDate.replace("T", " ")
  }
}

const deleteAction = async () => {
  const reqHeader = { accept: 'application/json' }
  const deleteResult: responseData = await ApiService.requestAPI({
    headers: reqHeader,
    method: 'DELETE',
    url: `/board/post/delete/${stackUUID.value}`
  })
  if (deleteResult.retStatus) {
    toastStore.setToastValue({
      severity: 'success',
      summary: '스택 게시판 관리',
      detail: '스택 게시판 삭제 되었습니다.',
      life: 3000
    })
    router.push({ name: 'StackAdmin' }).catch(() => {
      console.log('StackAdmin Error')
    })
  } else {
    toastStore.setToastValue({
      severity: 'error',
      summary: '스택 게시판 관리',
      detail: deleteResult.retData,
      life: 3000
    })
  }
}
</script>

<style lang="css" scoped>

</style>
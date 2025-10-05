<template>
  <Dialog :visible="showModal" 
      modal 
      :closable="false" 
      :draggable="false"
      class="w-2/3"
    >
      <template #header>
        <div class="w-full text-left mb-10">
          <div class="text-2xl font-bold text-gray-900">댓글</div>
        </div>
      </template>

      <div class="w-full px-5">

        <div class="w-full border border-gray-300 my-3"></div>

        <div v-for="i in contents" :key="i.commentUUID">
          <div class="flex justify-start text-gray-400">
            <span>{{ i.memberNickName }} </span>
            <span v-if="i.insertDate != i.updateDate" class="text-gray-400"> • 수정됨</span>
          </div>

          <div class="flex justify-start text-lg text-gray-600">
            <i v-if="i.isEncrypt == 'YES'" class="mr-1 py-1 !text-sm pi pi-lock"></i>
            <span v-if="i.isEncrypt == 'NO' || isCommentOwner(i.memberUUID) || isInfoOwner()">{{ i.commentContent }}</span>
            <span v-else class="italic">비밀댓글 입니다.</span>
          </div>

          <div class="flex justify-between">
            <div class="text-gray-400">{{ i.insertDate }}</div>
            <div>
              <Button v-if="isCommentOwner(i.memberUUID)" class="m-1 !bg-orange-400 !border !border-orange-400 hover:!bg-orange-500 hover:!border hover:!border-orange-500" @click="closeCommentModal">수정</Button>
              <Button v-if="isCommentOwner(i.memberUUID) || isAdmin()" class="m-1 !bg-red-400 !border !border-red-400 hover:!bg-red-500 hover:!border hover:!border-red-500" @click="closeCommentModal">삭제</Button>
            </div>
          </div>

          <div class="w-full border border-gray-200 my-3"></div>
        </div>
        
        <div class="w-full">
          <BoardInput inputTitle="" inputPlaceholder="댓글을 입력해주세요." :isRequire="false" :isDisabled="false" :isPassword="false" inputType="text" v-model:inputValue="commentContent" />
          <div class="w-full flex justify-end">
            <div class="bg-gray-200 border rounded-lg mr-2 cursor-pointer" @click="changeEncrypt()">
              <div class="border rounded-lg" :class="isEncrypt ? 'bg-white' : ''"><i class="pi !text-base !font-bold py-2 px-3" :class="isEncrypt ? 'pi-lock !text-blue-500' : 'pi-unlock'"></i></div>
            </div>
            <Button class="!bg-green-400 !border !border-green-400 hover:!bg-green-500 hover:!border hover:!border-green-500" @click="commentRegist()">등록</Button>
          </div>
        </div>

        <div class="w-full border border-gray-300 my-3"></div>
      </div>

      <template #footer>
        <div>
          <Button class="m-4 !px-4 !py-3 !bg-gray-400 !border !border-gray-400 hover:!bg-gray-500 hover:!border hover:!border-gray-500" @click="closeCommentModal">닫기</Button>
        </div>
      </template>
  </Dialog>
</template>

<script setup lang="ts">
import { ref, watch } from 'vue';
import { Dialog } from 'primevue';
import { useUserStore } from '@/stores/userStore';
import { useToastStore } from '@/stores/toastStore';
import { decryptStringSalt } from '@/utils/common';

import Button from 'primevue/button';
import BoardInput from '@/components/element/BoardInput.vue';
import type commonModalProps from '@/interfaces/common/commonModalProps';
import type commonComment from '@/interfaces/common/commonComment';

import ApiService from '@/services/ApiService';
import type responseData from '@/interfaces/common/responseData';

const props = defineProps<commonModalProps>()
const emit = defineEmits<{
  closeCommentModal: []
}>()

const userStore = useUserStore()
const toastStore = useToastStore()

const contents = ref<commonComment[]>([])

const commentContent = ref('')
const isEncrypt = ref(false)

const initData = () => {
  contents.value = []
  commentContent.value = ''
  isEncrypt.value = false

  getDevelopmentCommentList()
}

const changeEncrypt = () => {
  isEncrypt.value = !isEncrypt.value
}

const isInfoOwner = () => {
  return props.infoAuthorUUID == decryptStringSalt(userStore.getCurrentUser.uud)
}

const isCommentOwner = (uuid: string) => {
  return uuid == decryptStringSalt(userStore.getCurrentUser.uud)
}

const isAdmin = () => {
  const currentUserRole = decryptStringSalt(userStore.getUserRole)
  return currentUserRole == 'MASTER' || currentUserRole == 'ADMIN'
}

const closeCommentModal = () => {
  emit('closeCommentModal')
}

const commentRegist = () => {
  if (commentContent.value == null || commentContent.value == '') {
    toastStore.setToastValue({
      severity: 'error',
      summary: '댓글 관리',
      detail: '댓글을 입력해주세요.',
      life: 3000
    })
    return
  }
  commnetRegistAction()
}

const commnetRegistAction = async () => {
  const reqHeader = { accept: 'application/json' }
  const reqData = {
    'postUUID': props.infoUUID,
    'commentContent': commentContent.value,
    'isEncrypt': isEncrypt.value ? 'YES' : 'NO'
  }
  const registResult: responseData = await ApiService.requestAPI({
    headers: reqHeader,
    method: 'POST',
    url: `/board/post/comment/regist`,
    data: reqData
  })
  if (registResult.retStatus) {
    toastStore.setToastValue({
      severity: 'success',
      summary: '댓글 관리',
      detail: '댓글을 등록했습니다.',
      life: 3000
    })
    getDevelopmentCommentList()
  } else {
    toastStore.setToastValue({
      severity: 'error',
      summary: '댓글 관리',
      detail: registResult.retData,
      life: 3000
    })
  }
}

const getDevelopmentCommentList = async () => {
  const reqHeader = { accept: 'application/json' }
  const listResult: responseData = await ApiService.requestAPI({
    headers: reqHeader,
    method: 'GET',
    url: `/board/post/comment/list/${props.infoUUID}`,
  })
  if (listResult.retStatus) {
    for (let i = 0; i < listResult.retData.length; i++) {
      listResult.retData[i].insertDate = listResult.retData[i].insertDate.replace("T", " ")
      listResult.retData[i].updateDate = listResult.retData[i].updateDate.replace("T", " ")
    }
    contents.value = listResult.retData
  }
}

watch(() => props.showModal, (newVal) => {
  if(newVal) initData()
})


</script>

<style lang="scss" scoped>

</style>
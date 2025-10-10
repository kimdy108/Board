<template>
  <div>
    <div class="text-left mb-5">
      <div class="text-2xl font-bold text-gray-900">게시글 수정</div>
    </div>

    <div class="w-full mb-24">
      <BoardTitleInput inputType="text" inputTitle="제목" :isDisabled="false" :isRequire="true" :isPassword="false" inputPlaceholder="" v-model:inputValue="freeTitle" />
      <BoardEditor :isReadOnly="false" inputTitle="내용" :isRequire="true" v-model:inputValue="freeContent" />
    </div>

    <div class="flex justify-end">
      <Button class="m-2 !px-4 !py-3 !bg-violet-400 !border !border-violet-400 hover:!bg-violet-500 hover:!border hover:!border-violet-500" @click="movePrevPage">이전</Button>
      <Button class="m-2 !px-4 !py-3 !bg-orange-400 !border !border-orange-400 hover:!bg-orange-500 hover:!border hover:!border-orange-500" @click="freeUpdate">수정</Button>
    </div>
  </div>
</template>

<script setup lang="ts">
import { onMounted, ref } from 'vue'
import { useRouter } from 'vue-router'
import { useToastStore } from '@/stores/toastStore'

import ApiService from '@/services/ApiService'
import responseData from '@/interfaces/common/responseData'

import Button from 'primevue/button'
import BoardTitleInput from '@/components/element/BoardTitleInput.vue'
import BoardEditor from '@/components/element/BoardEditor.vue'

const router = useRouter()
const toastStore = useToastStore()

const freeUUID = ref('')
const freeTitle = ref('')
const freeContent = ref('')

onMounted(() => {
  freeUUID.value = history.state.uuid
  freeTitle.value = ''
  freeContent.value = ''

  getFreeInfo()
})

const movePrevPage = () => {
  router.push({ name: 'FreeInfo', state: {uuid: freeUUID.value} }).catch(() => {
    console.log('FreeInfo Error')
  })
}

const freeUpdate = () => {
  if (freeTitle.value == null || freeTitle.value == '') {
    toastStore.setToastValue({
      severity: 'warn',
      summary: '개발 게시판 관리',
      detail: '제목을 입력해주세요.',
      life: 3000
    })
    return
  }

  if (freeContent.value == null || freeContent.value == '') {
    toastStore.setToastValue({
      severity: 'warn',
      summary: '개발 게시판 관리',
      detail: '내용을 입력해주세요.',
      life: 3000
    })
    return
  }

  freeUpdateAction()
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
  }
}

const freeUpdateAction = async () => {
  const reqHeader = { accept: 'application/json' }
  const reqData = {
    'postUUID': freeUUID.value,
    'postTitle': freeTitle.value,
    'postContent': freeContent.value,
  }
  const updateResult: responseData = await ApiService.requestAPI({
    headers: reqHeader,
    method: 'PUT',
    url: `/board/post/update`,
    data: reqData
  })
  if (updateResult.retStatus) {
    toastStore.setToastValue({
      severity: 'success',
      summary: '자유 게시판 관리',
      detail: '자유 게시글을 수정했습니다.',
      life: 3000
    })
    movePrevPage()
  } else {
    toastStore.setToastValue({
      severity: 'error',
      summary: '자유 게시판 관리',
      detail: updateResult.retData,
      life: 3000
    })
  }
} 
</script>

<style lang="css" scoped>

</style>
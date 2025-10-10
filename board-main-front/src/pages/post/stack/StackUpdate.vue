<template>
  <div>
    <div class="text-left mb-5">
      <div class="text-2xl font-bold text-gray-900">게시글 수정</div>
    </div>

    <div class="w-full mb-24">
      <BoardTitleInput inputType="text" inputTitle="제목" :isDisabled="false" :isRequire="true" :isPassword="false" inputPlaceholder="" v-model:inputValue="stackTitle" />
      <BoardEditor :isReadOnly="false" inputTitle="내용" :isRequire="true" v-model:inputValue="stackContent" />
    </div>

    <div class="flex justify-end">
      <Button class="m-2 !px-4 !py-3 !bg-violet-400 !border !border-violet-400 hover:!bg-violet-500 hover:!border hover:!border-violet-500" @click="movePrevPage">이전</Button>
      <Button class="m-2 !px-4 !py-3 !bg-orange-400 !border !border-orange-400 hover:!bg-orange-500 hover:!border hover:!border-orange-500" @click="stackUpdate">수정</Button>
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

const stackUUID = ref('')
const stackTitle = ref('')
const stackContent = ref('')

onMounted(() => {
  stackUUID.value = history.state.uuid
  stackTitle.value = ''
  stackContent.value = ''

  getStackInfo()
})

const movePrevPage = () => {
  router.push({ name: 'StackInfo', state: {uuid: stackUUID.value} }).catch(() => {
    console.log('StackInfo Error')
  })
}

const stackUpdate = () => {
  if (stackTitle.value == null || stackTitle.value == '') {
    toastStore.setToastValue({
      severity: 'warn',
      summary: '개발 게시판 관리',
      detail: '제목을 입력해주세요.',
      life: 3000
    })
    return
  }

  if (stackContent.value == null || stackContent.value == '') {
    toastStore.setToastValue({
      severity: 'warn',
      summary: '개발 게시판 관리',
      detail: '내용을 입력해주세요.',
      life: 3000
    })
    return
  }

  stackUpdateAction()
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
  }
}

const stackUpdateAction = async () => {
  const reqHeader = { accept: 'application/json' }
  const reqData = {
    'postUUID': stackUUID.value,
    'postTitle': stackTitle.value,
    'postContent': stackContent.value,
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
      summary: '스택 게시판 관리',
      detail: '스택 게시글을 수정했습니다.',
      life: 3000
    })
    movePrevPage()
  } else {
    toastStore.setToastValue({
      severity: 'error',
      summary: '스택 게시판 관리',
      detail: updateResult.retData,
      life: 3000
    })
  }
} 
</script>

<style lang="css" scoped>

</style>
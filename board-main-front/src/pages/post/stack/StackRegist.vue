<template>
  <div>
    <div class="text-left mb-5">
      <div class="text-2xl font-bold text-gray-900">게시글 등록</div>
    </div>

    <div class="w-full mb-24">
      <BoardInput inputType="text" inputTitle="제목" :isDisabled="false" :isRequire="true" :isPassword="false" inputPlaceholder="" v-model:inputValue="stackTitle" />
      <BoardEditor :isReadOnly="false" inputTitle="내용" :isRequire="true" v-model:inputValue="stackContent" />
    </div>

    <div class="flex justify-end">
      <Button class="m-2 !px-4 !py-3 !bg-sky-400 !border !border-sky-400 hover:!bg-sky-500 hover:!border hover:!border-sky-500" @click="moveMainPage">메인</Button>
      <Button class="m-2 !px-4 !py-3 !bg-green-400 !border !border-green-400 hover:!bg-green-500 hover:!border hover:!border-green-500" @click="stackRegist">등록</Button>
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
import BoardInput from '@/components/element/BoardInput.vue'
import BoardEditor from '@/components/element/BoardEditor.vue'

const router = useRouter()
const toastStore = useToastStore()

const stackTitle = ref('')
const stackContent = ref('')

onMounted(() => {
  stackTitle.value = ''
  stackContent.value = ''
})

const moveMainPage = () => {
  router.push({ name: 'StackMain' }).catch(() => {
    console.log('StackMain Error')
  })
}

const stackRegist = () => {
  if (stackTitle.value == null || stackTitle.value == '') {
    toastStore.setToastValue({
      severity: 'warn',
      summary: '스택 게시판 관리',
      detail: '제목을 입력해주세요.',
      life: 3000
    })
    return
  }

  if (stackContent.value == null || stackContent.value == '') {
    toastStore.setToastValue({
      severity: 'warn',
      summary: '스택 게시판 관리',
      detail: '내용을 입력해주세요.',
      life: 3000
    })
    return
  }

  stackRegistAction()
}

const stackRegistAction = async () => {
  const reqHeader = { accept: 'application/json' }
  const reqData = {
    'postTitle': stackTitle.value,
    'postContent': stackContent.value,
    'postType': 'STACK'
  }
  const registResult: responseData = await ApiService.requestAPI({
    headers: reqHeader,
    method: 'POST',
    url: `/board/post/regist`,
    data: reqData
  })
  if (registResult.retStatus) {
    toastStore.setToastValue({
      severity: 'success',
      summary: '스택 게시판 관리',
      detail: '게시글을 등록했습니다.',
      life: 3000
    })
    moveMainPage()
  } else {
    toastStore.setToastValue({
      severity: 'error',
      summary: '스택 게시판 관리',
      detail: registResult.retData,
      life: 3000
    })
  }
}
</script>

<style lang="css" scoped>

</style>
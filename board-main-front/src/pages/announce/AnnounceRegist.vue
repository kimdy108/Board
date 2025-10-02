<template>
  <div>
    <div class="text-left mb-5">
      <div class="text-2xl font-bold text-gray-900">공지사항 등록</div>
    </div>

    <div class="w-full mb-24">
      <BoardInput inputType="text" inputTitle="제목" :isDisabled="false" :isRequire="true" :isPassword="false" inputPlaceholder="" v-model:inputValue="announceTitle" />
      <BoardEditor :isReadOnly="false" inputTitle="내용" :isRequire="true" v-model:inputValue="announceContent" />
    </div>

    <div class="flex justify-end">
      <Button class="m-2 !px-4 !py-3 !bg-sky-400 !border !border-sky-400 hover:!bg-sky-500 hover:!border hover:!border-sky-500" @click="moveMainPage">메인</Button>
      <Button class="m-2 !px-4 !py-3 !bg-green-400 !border !border-green-400 hover:!bg-green-500 hover:!border hover:!border-green-500" @click="announceRegist">등록</Button>
    </div>
  </div>
</template>

<script setup lang="ts">
import { onMounted, ref } from 'vue'
import { useRouter } from 'vue-router'
import { useToastStore } from '@/stores/toastStore'
import { decryptStringSalt } from '@/utils/common'

import ApiService from '@/services/ApiService'
import responseData from '@/interfaces/common/responseData'

import Button from 'primevue/button'
import BoardInput from '@/components/element/BoardInput.vue'
import BoardEditor from '@/components/element/BoardEditor.vue'

const router = useRouter()
const toastStore = useToastStore()

const announceTitle = ref('')
const announceContent = ref('')

onMounted(() => {
  announceTitle.value = ''
  announceContent.value = ''
})

const moveMainPage = () => {
  router.push({ name: 'AnnounceMain' }).catch(() => {
    console.log('AnnounceMain Error')
  })
}

const announceRegist = () => {
  if (announceTitle.value == null || announceTitle.value == '') {
    toastStore.setToastValue({
      severity: 'warn',
      summary: '공지사항 관리',
      detail: '제목을 입력해주세요.',
      life: 3000
    })
    return
  }

  if (announceContent.value == null || announceContent.value == '') {
    toastStore.setToastValue({
      severity: 'warn',
      summary: '공지사항 관리',
      detail: '내용을 입력해주세요.',
      life: 3000
    })
    return
  }

  announceRegistAction()
}

const announceRegistAction = async () => {
  const reqHeader = { accept: 'application/json' }
  const reqData = {
    'announceTitle': announceTitle.value,
    'announceContent': announceContent.value
  }
  const registResult: responseData = await ApiService.requestAPI({
    headers: reqHeader,
    method: 'POST',
    url: `/board/announce/regist`,
    data: reqData
  })
  if (registResult.retStatus) {
    toastStore.setToastValue({
      severity: 'success',
      summary: '공지사항 관리',
      detail: '공지사항을 등록했습니다.',
      life: 3000
    })
    moveMainPage()
  } else {
    toastStore.setToastValue({
      severity: 'error',
      summary: '공지사항 관리',
      detail: registResult.retData,
      life: 3000
    })
  }
}
</script>

<style lang="css" scoped>

</style>
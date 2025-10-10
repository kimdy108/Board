<template>
  <div>
    <div class="text-left mb-5">
      <div class="text-2xl font-bold text-gray-900">공지사항 수정</div>
    </div>

    <div class="w-full mb-24">
      <BoardTitleInput inputType="text" inputTitle="제목" :isDisabled="false" :isRequire="true" :isPassword="false" inputPlaceholder="" v-model:inputValue="announceTitle" />
      <BoardEditor :isReadOnly="false" inputTitle="내용" :isRequire="true" v-model:inputValue="announceContent" />
    </div>

    <div class="flex justify-end">
      <Button class="m-2 !px-4 !py-3 !bg-violet-400 !border !border-violet-400 hover:!bg-violet-500 hover:!border hover:!border-violet-500" @click="movePrevPage">이전</Button>
      <Button class="m-2 !px-4 !py-3 !bg-orange-400 !border !border-orange-400 hover:!bg-orange-500 hover:!border hover:!border-orange-500" @click="announceUpdate">수정</Button>
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

const announceUUID = ref('')
const announceTitle = ref('')
const announceContent = ref('')

onMounted(() => {
  announceUUID.value = history.state.uuid
  announceTitle.value = ''
  announceContent.value = ''

  getAnnounceInfo()
})

const movePrevPage = () => {
  router.push({ name: 'AnnounceInfo', state: {uuid: announceUUID.value} }).catch(() => {
    console.log('AnnounceInfo Error')
  })
}

const announceUpdate = () => {
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

  announceUpdateAction()
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
  }
}

const announceUpdateAction = async () => {
  const reqHeader = { accept: 'application/json' }
  const reqData = {
    'announceUUID': announceUUID.value,
    'announceTitle': announceTitle.value,
    'announceContent': announceContent.value
  }
  const updateResult: responseData = await ApiService.requestAPI({
    headers: reqHeader,
    method: 'PUT',
    url: `/board/announce/update`,
    data: reqData
  })
  if (updateResult.retStatus) {
    toastStore.setToastValue({
      severity: 'success',
      summary: '공지사항 관리',
      detail: '공지사항을 수정했습니다.',
      life: 3000
    })
    movePrevPage()
  } else {
    toastStore.setToastValue({
      severity: 'error',
      summary: '공지사항 관리',
      detail: updateResult.retData,
      life: 3000
    })
  }
} 
</script>

<style lang="css" scoped>

</style>
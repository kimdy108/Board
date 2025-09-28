<template>
  <div class="text-left ml-16 mt-16">
    <h2 class="text-8xl mb-5 text-black">{{ noticeTitle }}</h2>
    <p class="text-5xl mb-5 text-black">
      {{ noticeContent }}
    </p>
    <p class="text-2xl text-black">작성자 : {{ noticeWriter }}</p>
    <p class="text-2xl text-black">작성일 : {{ noticeDate }}</p>
  </div>
  <hr class="mt-16 ml-16 mr-16 mb-10" />
  <div class="text-left mr-16 flex justify-end">
    <Button label="목록" size="large" class="mr-2" @click="goNoticeList" />
    <Button
      label="수정"
      size="large"
      severity="info"
      class="mr-2"
      :style="isOwner ? '' : 'display: none'"
      @click="goNoticeEdit"
    />
    <Button
      label="삭제"
      size="large"
      severity="danger"
      :style="isOwner ? '' : 'display: none'"
      @click="deleteNoticeApi"
    />
  </div>
</template>

<script setup>
import Button from 'primevue/button'
import ApiService from '@/services/ApiService'
import { useRouter } from 'vue-router'
import { onMounted, ref } from 'vue'
import { useUserStore } from '@/stores/userStore'
import { useToastStore } from '@/stores/toastStore'
import { decryptStringSalt } from '@/utils/common'

const userStore = useUserStore()
const toastStore = useToastStore()
const router = useRouter()
const props = defineProps({
  noticeGuid: String
})

const noticeTitle = ref('')
const noticeContent = ref('')
const noticeWriter = ref('')
const noticeDate = ref('')
const writerGuid = ref('')

const isOwner = ref(false)

const isOwnerFunction = () => {
  const userStoreGuid = userStore.getUserAccess.ugd
  if (decryptStringSalt(userStoreGuid) === writerGuid.value) isOwner.value = true
  else isOwner.value = false
}

const goNoticeList = () => {
  router.push({ name: 'NoticePage' }).catch(() => {
    console.log('NoticePageError')
  })
}

const goNoticeEdit = () => {
  router.push({ name: 'NoticeEditPage' }).catch(() => {
    console.log('NoticeEditPage')
  })
}

const getNoticeApi = async () => {
  const result = await ApiService.requestAPI({
    headers: { accept: 'application/json' },
    method: 'GET',
    url: '/notice/search',
    params: {
      noticeGuid: props.noticeGuid
    }
  })
  noticeTitle.value = result.noticeTitle
  noticeContent.value = result.noticeContent
  noticeWriter.value = result.memberNickName
  noticeDate.value = result.noticeInsertDate.split('T')[0]
  writerGuid.value = result.memberGuid
  isOwnerFunction()
}

const deleteNoticeApi = async () => {
  const result = await ApiService.requestAPI({
    headers: { accept: 'application/json' },
    method: 'DELETE',
    url: `/notice/delete/${props.noticeGuid}`
  })
  if (result === 'success') {
    toastStore.setToastValue({
      severity: 'success',
      summary: '공지사항 삭제',
      detail: '삭제가 완료되었습니다.',
      life: 3000
    })
    router.push({ name: 'NoticePage' }).catch(() => {
      console.log('NoticePageError')
    })
  }
}

onMounted(() => {
  getNoticeApi()
})
</script>

<style lang="scss" scoped></style>

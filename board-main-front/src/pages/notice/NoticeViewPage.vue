<template>
  <div class="text-left ml-16 mt-16">
    <h2 class="text-8xl mb-5 text-black">{{ noticeTitle }}</h2>
    <p class="text-5xl mb-5 text-black">
      {{ noticeContent }}
    </p>
    <p class="text-2xl text-black">{{ noticeDate }}</p>
  </div>
  <hr class="mt-16 ml-16 mr-16 mb-10" />
  <div class="text-left mr-16 flex justify-end">
    <Button label="목록" size="large" class="mr-2" @click="goNoticeList" />
    <Button label="수정" size="large" severity="info" class="mr-2" @click="goNoticeEdit" />
    <Button label="삭제" size="large" severity="danger" @click="deleteNoticeApi" />
  </div>
</template>

<script setup>
import Button from 'primevue/button'
import ApiService from '@/services/ApiService'
import { useRouter } from 'vue-router'
import { onMounted, ref } from 'vue'

const router = useRouter()
const props = defineProps({
  noticeGuid: String
})

const noticeTitle = ref('')
const noticeContent = ref('')
const noticeDate = ref('')

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
  noticeDate.value = result.noticeInsertDate.split('T')[0]
}

const deleteNoticeApi = async () => {
  const result = await ApiService.requestAPI({
    headers: { accept: 'application/json' },
    method: 'DELETE',
    url: `/notice/delete/${props.noticeGuid}`
  })
  if (result === 'success') {
    alert('삭제가 완료되었습니다.')
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

<template>
  <div class="text-left text-black">
    <div class="ml-5 text-6xl p-10">공지사항 수정</div>
  </div>
  <div class="card flex">
    <div class="flex flex-col gap-2 text-left ml-16">
      <label for="noticeTitle" class="text-3xl text-black">제목</label>
      <InputText
        id="noticeTitle"
        v-model="noticeTitleValue"
        size="large"
        style="width: 1400px; height: 50px; font-size: 20px"
      />
    </div>
  </div>
  <div class="card flex">
    <div class="flex flex-col gap-2 text-left ml-16">
      <label for="noticeContent" class="text-3xl text-black mt-5">내용</label>
      <Textarea
        id="noticeContent"
        v-model="noticeContentValue"
        rows="5"
        cols="30"
        style="width: 1400px; height: 300px; font-size: 20px"
      />
    </div>
  </div>
  <hr class="mt-16 ml-16 mr-16 mb-10" />
  <div class="text-left mr-16 flex justify-end">
    <Button label="뒤로가기" size="large" class="mr-2" @click="goNoticeView" />
    <Button label="수정" size="large" severity="info" class="mr-2" @click="noticeEditFunction" />
  </div>
</template>

<script setup>
import InputText from 'primevue/inputtext'
import Textarea from 'primevue/textarea'
import Button from 'primevue/button'
import ApiService from '@/services/ApiService'
import { useRouter } from 'vue-router'
import { onMounted, ref } from 'vue'

const props = defineProps({
  noticeGuid: String
})

const router = useRouter()

const noticeTitleValue = ref('')
const noticeContentValue = ref('')

const goNoticeView = () => {
  const noticeGuid = props.noticeGuid
  router.push({ name: 'NoticeViewPage', params: { noticeGuid } }).catch(() => {
    console.log('NoticeViewPageError')
  })
}

const noticeEditFunction = () => {
  if (noticeTitleValue.value === '') alert('제목을 입력해주세요.')
  else if (noticeContentValue.value === '') alert('내용을 입력해주세요.')
  else noticeEditApi()
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
  noticeTitleValue.value = result.noticeTitle
  noticeContentValue.value = result.noticeContent
}

const noticeEditApi = async () => {
  const result = await ApiService.requestAPI({
    headers: { accept: 'application/json' },
    method: 'PUT',
    url: '/notice/update',
    data: {
      noticeGuid: props.noticeGuid,
      noticeTitle: noticeTitleValue.value,
      noticeContent: noticeContentValue.value
    }
  })
  if (result === 'success') {
    alert('수정이 완료되었습니다.')
    goNoticeView()
  }
}

onMounted(() => {
  getNoticeApi()
})
</script>

<style lang="scss" scoped></style>

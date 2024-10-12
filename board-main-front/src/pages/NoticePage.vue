<template>
  <div class="text-left text-black">
    <div class="ml-5 text-6xl p-10">공지사항</div>
  </div>
  <div class="text-right items-end mr-10">
    <Button label="글쓰기" size="large" @click="goNoticeCreate" />
  </div>
  <hr class="m-5" />
  <div class="card">
    <DataTable
      :value="items"
      paginator
      :rows="10"
      tableStyle="min-width: 50rem"
      style="margin-left: 15px; margin-right: 15px"
      @row-click="goNoticeView"
    >
      <Column field="noticeTitle" header="제목" style="width: 25%"></Column>
      <Column field="noticeContent" header="내용" style="width: 45%"></Column>
      <Column field="memberNickName" header="닉네임" style="width: 10%"></Column>
      <Column field="noticeInsertDate" header="등록날짜" style="width: 10%"></Column>
    </DataTable>
  </div>
</template>

<script setup>
import DataTable from 'primevue/datatable'
import Column from 'primevue/column'
import Button from 'primevue/button'
import ApiService from '@/services/ApiService'
import { useRouter } from 'vue-router'
import { onMounted, ref } from 'vue'

const router = useRouter()
const items = ref()

const goNoticeCreate = () => {
  router.push({ name: 'NoticeCreatePage' }).catch(() => {
    console.log('NoticeCreatePageError')
  })
}

const goNoticeView = (event) => {
  const noticeGuid = event.data.noticeGuid
  router.push({ name: 'NoticeViewPage', params: { noticeGuid } }).catch(() => {
    console.log('NoticeViewPageError')
  })
}

const getNoticeList = async () => {
  const result = await ApiService.requestAPI({
    headers: { accept: 'application/json' },
    method: 'GET',
    url: '/notice/search/list/all'
  })
  for (let i in result) {
    result[i].noticeInsertDate = result[i].noticeInsertDate.split('T')[0]
  }
  items.value = result
}
onMounted(() => {
  getNoticeList()
})
</script>

<style lang="scss" scoped></style>

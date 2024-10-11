<template>
  <div class="text-left text-black">
    <div class="ml-5 mb-5 text-6xl">
      <div class="p-10 bg-sky-200 w-72 rounded-lg">공지사항</div>
    </div>
  </div>
  <div class="card">
    <DataTable
      :value="items"
      paginator
      :rows="10"
      tableStyle="min-width: 50rem"
      style="margin-left: 15px; margin-right: 15px"
      @row-click="onRowClick"
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
import ApiService from '@/services/ApiService'
import { onMounted, ref } from 'vue'

const items = ref()
const onRowClick = (event) => {
  const noticeGuid = event.data.noticeGuid
  alert(noticeGuid)
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

const alertGuid = (guid) => {
  alert(guid)
}

onMounted(() => {
  getNoticeList()
})
</script>

<style lang="scss" scoped></style>

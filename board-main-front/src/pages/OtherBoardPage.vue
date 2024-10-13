<template>
  <div class="text-left text-black">
    <div class="ml-5 text-6xl p-10">자유게시판</div>
  </div>
  <div class="text-right items-end mr-10">
    <Button label="글쓰기" size="large" @click="goOtherCreate" />
  </div>
  <hr class="m-5" />
  <div class="card">
    <DataTable
      :value="items"
      paginator
      :rows="10"
      tableStyle="min-width: 50rem"
      style="margin-left: 15px; margin-right: 15px"
      @row-click="goOtherView"
    >
      <Column field="boardTitle" header="제목" style="width: 25%"></Column>
      <Column field="boardContent" header="내용" style="width: 45%"></Column>
      <Column field="memberNickName" header="닉네임" style="width: 10%"></Column>
      <Column field="boardInsertDate" header="등록날짜" style="width: 10%"></Column>
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

const goOtherCreate = () => {
  router.push({ name: 'OtherCreatePage' }).catch(() => {
    console.log('OtherCreatePageError')
  })
}

const goOtherView = (event) => {
  const boardGuid = event.data.boardGuid
  router.push({ name: 'OtherViewPage', params: { boardGuid } }).catch(() => {
    console.log('OtherViewPage')
  })
}

const getOtherList = async () => {
  const result = await ApiService.requestAPI({
    headers: { accept: 'application/json' },
    method: 'GET',
    url: '/board/free/search/list/all'
  })
  for (let i in result) {
    result[i].boardInsertDate = result[i].boardInsertDate.split('T')[0]
  }
  items.value = result
}

onMounted(() => {
  getOtherList()
})
</script>

<style lang="scss" scoped></style>

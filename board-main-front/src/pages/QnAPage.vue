<template>
  <div class="text-left text-black">
    <div class="ml-5 text-6xl p-10">자주하는 문의</div>
  </div>
  <div class="text-right items-end mr-10">
    <Button
      label="글쓰기"
      size="large"
      @click="goQnACreate"
      :style="isManager ? '' : 'display:none'"
    />
  </div>
  <hr class="m-5" />
  <div class="card">
    <DataTable
      :value="items"
      paginator
      :rows="10"
      tableStyle="min-width: 50rem"
      style="margin-left: 15px; margin-right: 15px"
      @row-click="goQnAView"
    >
      <Column field="qnaTitle" header="제목" style="width: 25%"></Column>
      <Column field="qnaContent" header="내용" style="width: 45%"></Column>
      <Column field="memberNickName" header="작성자" style="width: 10%"></Column>
      <Column field="qnaInsertDate" header="등록날짜" style="width: 10%"></Column>
    </DataTable>
  </div>
</template>

<script setup>
import DataTable from 'primevue/datatable'
import Column from 'primevue/column'
import Button from 'primevue/button'
import ApiService from '@/services/ApiService'
import { useUserStore } from '@/stores/userStore'
import { decryptStringSalt } from '@/utils/common'
import { useRouter } from 'vue-router'
import { onMounted, ref } from 'vue'

const userStore = useUserStore()
const router = useRouter()
const items = ref()
const isManager = ref(false)

const checkUserRole = () => {
  const userRole = userStore.getUserRole
  if (decryptStringSalt(userRole) === 'manager') isManager.value = true
  else isManager.value = false
}

const goQnACreate = () => {
  router.push({ name: 'QnACreatePage' }).catch(() => {
    console.log('QnACreatePageError')
  })
}

const goQnAView = (event) => {
  const qnaGuid = event.data.qnaGuid
  router.push({ name: 'QnAViewPage', params: { qnaGuid } }).catch(() => {
    console.log('QnAViewPageError')
  })
}

const getQnAList = async () => {
  const result = await ApiService.requestAPI({
    headers: { accept: 'application/json' },
    method: 'GET',
    url: '/qna/search/list/all'
  })
  for (let i in result) {
    result[i].qnaInsertDate = result[i].qnaInsertDate.split('T')[0]
  }
  items.value = result
}
onMounted(() => {
  getQnAList()
  checkUserRole()
})
</script>

<style lang="scss" scoped></style>

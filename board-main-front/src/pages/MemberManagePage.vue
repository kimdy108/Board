<template>
  <div class="text-left text-black">
    <div class="ml-5 text-6xl p-10">회원 관리</div>
  </div>
  <div class="card">
    <DataTable
      :value="items"
      paginator
      :rows="10"
      tableStyle="min-width: 50rem"
      style="margin-left: 15px; margin-right: 15px"
    >
      <Column field="userId" header="아이디" style="width: 15%"></Column>
      <Column field="userName" header="이름" style="width: 15%"></Column>
      <Column field="userNickName" header="닉네임" style="width: 15%"></Column>
      <Column field="userPhone" header="전화번호" style="width: 15%"></Column>
      <Column field="userEmail" header="이메일" style="width: 15%"></Column>
      <Column field="insertDate" header="가입일" style="width: 15%"></Column>
      <Column style="width: 5%">
        <template #body="items">
          <Button
            v-if="items.data.userId !== 'admin'"
            label="수정"
            rounded
            class="mr-2"
            severity="info"
            :disabled="items.data.userRole === decryptStringSalt(userStore.getUserAccess.rol)"
            @click="updateMember(items)"
          />
        </template>
      </Column>
      <Column style="width: 5%">
        <template #body="items">
          <Button
            v-if="items.data.userId !== 'admin'"
            label="삭제"
            rounded
            class="mr-2"
            severity="danger"
            :disabled="items.data.userRole === decryptStringSalt(userStore.getUserAccess.rol)"
            @click="deleteMember(items)"
          />
        </template>
      </Column>
    </DataTable>
  </div>
</template>

<script setup>
import DataTable from 'primevue/datatable'
import Column from 'primevue/column'
import Button from 'primevue/button'
import ApiService from '@/services/ApiService'
import { useUserStore } from '@/stores/userStore'
import { onMounted, ref } from 'vue'
import { decryptStringSalt } from '@/utils/common'

const items = ref()
const userStore = useUserStore()

const updateMember = (items) => {
  // router.push({ name: 'UpdateMemberPage', param: 'items.data.userGuid' }).catch(() => {
  //   console.log('UpdateMemberPageError')
  // })
  alert('updateMember : ' + items.data.userGuid)
}

const deleteMember = async (value) => {
  const userGuid = value.data.userGuid
  const result = await ApiService.requestAPI({
    headers: { accept: 'application/json' },
    method: 'DELETE',
    url: `/member/manage/delete/${userGuid}`
  })
  if (result === 'success') {
    alert('회원삭제가 완료되었습니다.')
    getMemberList()
  }
}

const getMemberList = async () => {
  const result = await ApiService.requestAPI({
    headers: { accept: 'application/json' },
    method: 'GET',
    url: '/member/info/list'
  })
  for (let i in result) {
    result[i].userId = decryptStringSalt(result[i].userId)
    result[i].userGuid = decryptStringSalt(result[i].userGuid)
    result[i].userName = decryptStringSalt(result[i].userName)
    result[i].userNickName = decryptStringSalt(result[i].userNickName)
    result[i].userEmail = decryptStringSalt(result[i].userEmail)
    result[i].userPhone = decryptStringSalt(result[i].userPhone)
    result[i].userRole = decryptStringSalt(result[i].userRole)
    result[i].insertDate = result[i].insertDate.split('T')[0]
  }
  items.value = result
}

onMounted(() => {
  getMemberList()
})
</script>

<style lang="scss" scoped></style>

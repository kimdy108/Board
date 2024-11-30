<template>
  <div class="text-left text-black">
    <div class="ml-5 text-6xl p-10">매니저 관리</div>
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
      <Column style="width: 10%">
        <template #body="items">
          <Button
            v-if="items.data.userRole !== 'manager'"
            label="매니저 지정"
            rounded
            class="mr-2"
            @click="promoteManager(items)"
          />
          <Button
            v-else-if="items.data.userRole === 'manager' && items.data.userId !== 'admin'"
            label="매니저 삭제"
            rounded
            severity="danger"
            @click="relegateManager(items)"
            :disabled="
              items.data.userId === 'admin' ||
              items.data.userGuid === decryptStringSalt(userStore.getUserAccess.ugd)
            "
          />
          <Button v-else label="최고 관리자" rounded severity="info" />
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
import { useToastStore } from '@/stores/toastStore'
import { onMounted, ref } from 'vue'
import { decryptStringSalt } from '@/utils/common'

const items = ref()
const userStore = useUserStore()
const toastStore = useToastStore()

const promoteManager = async (event) => {
  const result = await ApiService.requestAPI({
    headers: { accept: 'application/json' },
    method: 'PUT',
    url: '/member/promote/manager',
    data: {
      userGuid: event.data.userGuid
    }
  })
  if (result === 'success') {
    toastStore.setToastValue({
      severity: 'success',
      summary: '매니저 관리',
      detail: '매니저 수정이 완료되었습니다.',
      life: 3000
    })
    getMemberList()
  } else {
    toastStore.setToastValue({
      severity: 'error',
      summary: '매니저 관리',
      detail: '매니저 수정이 실패했습니다.',
      life: 3000
    })
  }
}

const relegateManager = async (event) => {
  const result = await ApiService.requestAPI({
    headers: { accept: 'application/json' },
    method: 'PUT',
    url: '/member/relegate/manager',
    data: {
      userGuid: event.data.userGuid
    }
  })
  if (result === 'success') {
    toastStore.setToastValue({
      severity: 'success',
      summary: '매니저 관리',
      detail: '매니저 수정이 완료되었습니다.',
      life: 3000
    })
    getMemberList()
  } else {
    toastStore.setToastValue({
      severity: 'error',
      summary: '매니저 관리',
      detail: '매니저 수정이 실패했습니다.',
      life: 3000
    })
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

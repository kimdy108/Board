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
            :disabled="
              encryptString(items.data.userId) === decryptStringSalt(userStore.getUserAccess.uid)
            "
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
            :disabled="
              encryptString(items.data.userId) === decryptStringSalt(userStore.getUserAccess.uid)
            "
            @click="deleteMember(items)"
          />
        </template>
      </Column>
    </DataTable>
  </div>
  <hr class="mr-5 ml-5 mt-5 mb-5" />
  <div class="text-left mr-5 flex justify-end">
    <Button label="회원 등록" size="large" severity="help" @click="joinMember" />
  </div>
</template>

<script setup>
import DataTable from 'primevue/datatable'
import Column from 'primevue/column'
import Button from 'primevue/button'
import ApiService from '@/services/ApiService'
import { useRouter } from 'vue-router'
import { useUserStore } from '@/stores/userStore'
import { useToastStore } from '@/stores/toastStore'
import { onMounted, ref } from 'vue'
import { decryptStringSalt, encryptString } from '@/utils/common'

const items = ref()
const router = useRouter()
const userStore = useUserStore()
const toastStore = useToastStore()

const joinMember = () => {
  router.push({ name: 'JoinMemberPage' }).catch(() => {
    console.log('joinMemberPageError')
  })
}

const updateMember = (items) => {
  const userGuid = items.data.userGuid
  router.push({ name: 'UpdateMemberPage', params: { userGuid } }).catch(() => {
    console.log('UpdateMemberPageError')
  })
}

const deleteMember = async (value) => {
  const userGuid = value.data.userGuid
  const result = await ApiService.requestAPI({
    headers: { accept: 'application/json' },
    method: 'DELETE',
    url: `/member/manage/delete/${userGuid}`
  })
  if (result === 'success') {
    toastStore.setToastValue({
      severity: 'success',
      summary: '회원 관리',
      detail: '회원이 삭제되었습니다.',
      life: 3000
    })
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

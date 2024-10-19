<template>
  <div class="text-left ml-16 mt-16">
    <h2 class="text-8xl mb-5 text-black">{{ boardTitle }}</h2>
    <p class="text-5xl mb-5 text-black">
      {{ boardContent }}
    </p>
    <p class="text-2xl text-black">작성자 : {{ boardWriter }}</p>
    <p class="text-2xl text-black">작성일 : {{ boardDate }}</p>
  </div>
  <hr class="mt-16 ml-16 mr-16 mb-10" />
  <div class="text-left mr-16 flex justify-end">
    <Button label="목록" size="large" class="mr-2" @click="goOtherList" />
    <Button
      label="수정"
      size="large"
      severity="info"
      class="mr-2"
      :style="isEditOwner ? '' : 'display: none'"
      @click="goOtherEdit"
    />
    <Button
      label="삭제"
      size="large"
      severity="danger"
      :style="isDeleteOwner ? '' : 'display: none'"
      @click="deleteOtherApi"
    />
  </div>
</template>

<script setup>
import Button from 'primevue/button'
import ApiService from '@/services/ApiService'
import { useRouter } from 'vue-router'
import { onMounted, ref } from 'vue'
import { useUserStore } from '@/stores/userStore'
import { decryptStringSalt } from '@/utils/common'

const userStore = useUserStore()
const router = useRouter()
const props = defineProps({
  boardGuid: String
})

const boardTitle = ref('')
const boardContent = ref('')
const boardWriter = ref('')
const boardDate = ref('')
const writerGuid = ref('')

const isEditOwner = ref(false)
const isDeleteOwner = ref(false)

const isOwnerFunction = () => {
  const userStoreGuid = userStore.getUserAccess.ugd
  const userStoreRole = userStore.getUserRole
  if (decryptStringSalt(userStoreGuid) === writerGuid.value)
    (isEditOwner.value = true), (isDeleteOwner.value = true)
  else if (decryptStringSalt(userStoreRole) === 'manager') isDeleteOwner.value = true
  else isOwner.value = false
}

const goOtherList = () => {
  router.push({ name: 'OtherBoardPage' }).catch(() => {
    console.log('OtherBoardPageError')
  })
}

const goOtherEdit = () => {
  router.push({ name: 'OtherEditPage' }).catch(() => {
    console.log('OtherEditPageError')
  })
}

const getOtherApi = async () => {
  const result = await ApiService.requestAPI({
    headers: { accept: 'application/json' },
    method: 'GET',
    url: '/board/free/search',
    params: {
      freeGuid: props.boardGuid
    }
  })
  boardTitle.value = result.boardTitle
  boardContent.value = result.boardContent
  boardWriter.value = result.memberNickName
  boardDate.value = result.boardInsertDate.split('T')[0]
  writerGuid.value = result.memberGuid
  isOwnerFunction()
}

const deleteOtherApi = async () => {
  const result = await ApiService.requestAPI({
    headers: { accept: 'application/json' },
    method: 'DELETE',
    url: `/board/free/delete/${props.boardGuid}`
  })
  if (result === 'success') {
    alert('삭제가 완료되었습니다.')
    goOtherList()
  }
}

onMounted(() => {
  getOtherApi()
})
</script>

<style lang="scss" scoped></style>

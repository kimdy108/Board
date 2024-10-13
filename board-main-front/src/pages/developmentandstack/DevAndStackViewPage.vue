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
    <Button label="목록" size="large" class="mr-2" @click="goDevAndStackList" />
    <Button
      label="수정"
      size="large"
      severity="info"
      class="mr-2"
      :style="isOwner ? '' : 'display: none'"
      @click="goDevAndStackEdit"
    />
    <Button
      label="삭제"
      size="large"
      severity="danger"
      :style="isOwner ? '' : 'display: none'"
      @click="deleteDevAndStackApi"
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

const isOwner = ref(false)

const isOwnerFunction = () => {
  const userStoreGuid = userStore.getUserAccess.ugd
  const userStoreRole = userStore.getUserRole
  if (decryptStringSalt(userStoreGuid) === writerGuid.value) isOwner.value = true
  else if (decryptStringSalt(userStoreRole) === 'manager') isOwner.value = true
  else isOwner.value = false
}

const goDevAndStackList = () => {
  router.push({ name: 'DevelopmentAndStackPage' }).catch(() => {
    console.log('DevelopmentAndStackPageError')
  })
}

const goDevAndStackEdit = () => {
  router.push({ name: 'DevAndStackEditPage' }).catch(() => {
    console.log('DevAndStackEditPage')
  })
}

const getDevAndStackApi = async () => {
  const result = await ApiService.requestAPI({
    headers: { accept: 'application/json' },
    method: 'GET',
    url: '/board/dev/stack/search',
    params: {
      developmentAndStackGuid: props.boardGuid
    }
  })
  boardTitle.value = result.boardTitle
  boardContent.value = result.boardContent
  boardWriter.value = result.memberNickName
  boardDate.value = result.boardInsertDate.split('T')[0]
  writerGuid.value = result.memberGuid
  isOwnerFunction()
}

const deleteDevAndStackApi = async () => {
  const result = await ApiService.requestAPI({
    headers: { accept: 'application/json' },
    method: 'DELETE',
    url: `/board/dev/stack/delete/${props.boardGuid}`
  })
  if (result === 'success') {
    alert('삭제가 완료되었습니다.')
    goDevAndStackList()
  }
}

onMounted(() => {
  getDevAndStackApi()
})
</script>

<style lang="scss" scoped></style>

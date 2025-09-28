<template>
  <div class="text-left ml-16 mt-16">
    <h2 class="text-8xl mb-5 text-black">{{ qnaTitle }}</h2>
    <p class="text-5xl mb-5 text-black">
      {{ qnaContent }}
    </p>
    <p class="text-2xl text-black">작성자 : {{ qnaWriter }}</p>
    <p class="text-2xl text-black">작성일 : {{ qnaDate }}</p>
  </div>
  <hr class="mt-16 ml-16 mr-16 mb-10" />
  <div class="text-left mr-16 flex justify-end">
    <Button label="목록" size="large" class="mr-2" @click="goQnAList" />
    <Button
      label="수정"
      size="large"
      severity="info"
      class="mr-2"
      :style="isOwner ? '' : 'display: none'"
      @click="goQnAEdit"
    />
    <Button
      label="삭제"
      size="large"
      severity="danger"
      :style="isOwner ? '' : 'display: none'"
      @click="deleteQnAApi"
    />
  </div>
</template>

<script setup>
import Button from 'primevue/button'
import ApiService from '@/services/ApiService'
import { useRouter } from 'vue-router'
import { onMounted, ref } from 'vue'
import { useUserStore } from '@/stores/userStore'
import { useToastStore } from '@/stores/toastStore'
import { decryptStringSalt } from '@/utils/common'

const userStore = useUserStore()
const toastStore = useToastStore()
const router = useRouter()
const props = defineProps({
  qnaGuid: String
})

const qnaTitle = ref('')
const qnaContent = ref('')
const qnaWriter = ref('')
const qnaDate = ref('')
const writerGuid = ref('')

const isOwner = ref(false)

const isOwnerFunction = () => {
  if (decryptStringSalt(userStore.getUserAccess.rol) === 'manager') isOwner.value = true
  else isOwner.value = false
}

const goQnAList = () => {
  router.push({ name: 'QnAPage' }).catch(() => {
    console.log('QnAPageError')
  })
}

const goQnAEdit = () => {
  router.push({ name: 'QnAEditPage' }).catch(() => {
    console.log('QnAEditPage')
  })
}

const getQnAApi = async () => {
  const result = await ApiService.requestAPI({
    headers: { accept: 'application/json' },
    method: 'GET',
    url: '/qna/search',
    params: {
      qnaGuid: props.qnaGuid
    }
  })
  qnaTitle.value = result.qnaTitle
  qnaContent.value = result.qnaContent
  qnaWriter.value = result.memberNickName
  qnaDate.value = result.qnaInsertDate.split('T')[0]
  writerGuid.value = result.memberGuid
  isOwnerFunction()
}

const deleteQnAApi = async () => {
  const result = await ApiService.requestAPI({
    headers: { accept: 'application/json' },
    method: 'DELETE',
    url: `/qna/delete/${props.qnaGuid}`
  })
  if (result === 'success') {
    toastStore.setToastValue({
      severity: 'success',
      summary: '자주하는 문의 삭제',
      detail: '삭제가 완료되었습니다.',
      life: 3000
    })
    goQnAList()
  }
}

onMounted(() => {
  getQnAApi()
})
</script>

<style lang="scss" scoped></style>

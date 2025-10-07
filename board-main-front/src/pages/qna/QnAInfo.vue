<template>
  <div>
    <div class="text-left mb-10">
      <div class="text-2xl font-bold text-gray-900">QnA</div>
    </div>

    <div class="w-full">

      <BoardInfo :title="qnaTitle" :content="qnaContent" :viewCount=undefined :author="qnaAuthorName" :insertDate="qnaInsertDate" :updateDate="qnaUpdateDate" />

      <div v-if="qnaAnswer == 'NO'" class="text-left text-xl text-red-500 italic">
        답변이 없습니다.
      </div>

      <div v-else class="text-left text-xl text-gray-500 italic">
        답변이 있습니다.
      </div>

      <div class="w-full border border-gray-300 my-10"></div>

      <div class="flex justify-end">
        <Button class="m-2 !px-4 !py-3 !bg-sky-400 !border !border-sky-400 hover:!bg-sky-500 hover:!border hover:!border-sky-500" @click="moveMainPage">메인</Button>
        <Button class="m-2 !px-4 !py-3 !bg-orange-400 !border !border-orange-400 hover:!bg-orange-500 hover:!border hover:!border-orange-500" @click="moveUpdatePage">수정</Button>
        <Button class="m-2 !px-4 !py-3 !bg-red-400 !border !border-red-400 hover:!bg-red-500 hover:!border hover:!border-red-500" @click="deleteAction">삭제</Button>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";
import { useToastStore } from "@/stores/toastStore"
import { decryptStringSalt } from "@/utils/common";

import Button from 'primevue/button'
import BoardInfo from '@/components/element/BoardInfo.vue'
import responseData from "@/interfaces/common/responseData";
import ApiService from "@/services/ApiService";

const toastStore = useToastStore()

const router = useRouter();

const qnaUUID = ref('')
const qnaTitle = ref('')
const qnaContent = ref('')
const qnaAnswer = ref('NO')
const qnaAuthorName = ref('')
const qnaAuthorUUID = ref('')
const qnaInsertDate = ref('')
const qnaUpdateDate = ref('')

onMounted(() => {
  qnaUUID.value = history.state.uuid
  qnaTitle.value = ''
  qnaContent.value = ''
  qnaAnswer.value = 'NO'
  qnaAuthorName.value = ''
  qnaAuthorUUID.value = ''
  qnaInsertDate.value = ''
  qnaUpdateDate.value = ''

  getQnAInfo()
})

const moveMainPage = () => {
  router.push({ name: 'QnAMain' }).catch(() => {
    console.log('QnAMain Error')
  })
}

const moveUpdatePage = () => {
  router.push({ name: 'QnAUpdate', state: {uuid: qnaUUID.value} }).catch(() => {
    console.log('QnAUpdate Error')
  })
}

const getQnAInfo = async () => {
  const reqHeader = { accept: 'application/json' }
  const infoResult: responseData = await ApiService.requestAPI({
    headers: reqHeader,
    method: 'GET',
    url: `/board/qna/info/${qnaUUID.value}`,
  })
  if (infoResult.retStatus) {
    qnaTitle.value = infoResult.retData.qnaTitle
    qnaContent.value = infoResult.retData.qnaContent
    qnaAnswer.value = infoResult.retData.qnaAnswer
    qnaAuthorName.value = decryptStringSalt(infoResult.retData.memberNickName)
    qnaAuthorUUID.value = infoResult.retData.memberUUID
    qnaInsertDate.value = infoResult.retData.insertDate.replace("T", " ")
    qnaUpdateDate.value = infoResult.retData.updateDate.replace("T", " ")
  }
}

const deleteAction = async () => {
  const reqHeader = { accept: 'application/json' }
  const deleteResult: responseData = await ApiService.requestAPI({
    headers: reqHeader,
    method: 'DELETE',
    url: `/board/qna/delete/${qnaUUID.value}`
  })
  if (deleteResult.retStatus) {
    toastStore.setToastValue({
      severity: 'success',
      summary: 'QnA 관리',
      detail: 'QnA 삭제 되었습니다.',
      life: 3000
    })
    router.push({ name: 'QnAAdmin' }).catch(() => {
      console.log('QnAAdmin Error')
    })
  } else {
    toastStore.setToastValue({
      severity: 'error',
      summary: 'QnA 관리',
      detail: deleteResult.retData,
      life: 3000
    })
  }
}
</script>

<style lang="css" scoped>

</style>
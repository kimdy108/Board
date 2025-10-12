<template>
  <div>
    <div class="text-left mb-10">
      <div class="text-2xl font-bold text-gray-900">QnA</div>
    </div>

    <div class="w-full">

      <BoardInfo :title="qnaTitle" :content="qnaContent" :viewCount=undefined :author="qnaAuthorName" :insertDate="qnaInsertDate" :updateDate="qnaUpdateDate" />

      <div v-if="qnaAnswer == 'NO' || qnaAnswerContent.length == 0" class="text-center text-xl text-purple-400 font-bold italic">
        답변이 없습니다.

        <div class="w-full border border-gray-300 my-10"></div>
      </div>

      <div v-else>
        <div class="text-left text-2xl font-bold mt-10 mb-5 text-gray-700">Answer</div>

        <div v-for="answer in qnaAnswerContent" :key="answer.answerUUID">
          <div v-if="answer.isActive == 'YES'" class="bg-gray-200 rounded-lg p-5 my-5">
            <div class="text-left text-xl" v-html="answer.answerContent"></div>
            <div  class="flex justify-between">
              <div class="text-ml text-gray-400 my-1">{{ answer.insertDate.replace("T", " ") }} <i v-if="isUpdate(answer.insertDate, answer.updateDate)" class="pi pi-pencil ml-1"></i></div>
              <div>
                <Button v-if="isOverManager()" class="m-1 !bg-orange-400 !border !border-orange-400 hover:!bg-orange-500 hover:!border hover:!border-orange-500" @click="showAnswerUpdateModal(answer.answerUUID)">수정</Button>
                <Button v-if="isOverManager()" class="m-1 !bg-red-400 !border !border-red-400 hover:!bg-red-500 hover:!border hover:!border-red-500" @click="answerDeleteAction(answer.answerUUID)">삭제</Button>
              </div>
            </div>
          </div>

          <div v-else class="bg-gray-200 rounded-lg p-5 my-5">
            <div class="text-left text-xl italic">삭제된 답변입니다.</div>
            <div class="flex justify-start text-ml text-gray-400 my-1 italic">{{ answer.insertDate.replace("T", " ") }}</div>
          </div>
        </div>
      </div>

      <div class="flex justify-end">
        <Button class="m-2 !px-4 !py-3 !bg-sky-400 !border !border-sky-400 hover:!bg-sky-500 hover:!border hover:!border-sky-500" @click="moveMainPage">메인</Button>
        <Button v-if="isOverManager()" class="m-2 !px-4 !py-3 !bg-amber-400 !border !border-amber-400 hover:!bg-amber-500 hover:!border hover:!border-amber-500" @click="showAnswerRegistModal">답글</Button>
        <Button v-if="isOwner()" class="m-2 !px-4 !py-3 !bg-orange-400 !border !border-orange-400 hover:!bg-orange-500 hover:!border hover:!border-orange-500" @click="moveUpdatePage">수정</Button>
        <Button class="m-2 !px-4 !py-3 !bg-red-400 !border !border-red-400 hover:!bg-red-500 hover:!border hover:!border-red-500" @click="deleteAction">삭제</Button>
      </div>
    </div>

    <QnAAnswerRegist :showModal="isAnswerRegistModal" :qnaUUID="qnaUUID" answerUUID="" @closeAnswerRegistModal="closeAnswerRegistModal" />
    <QnAAnswerUpdate :showModal="isAnswerUpdateModal" :qnaUUID="qnaUUID" :answerUUID="answerUUID" @closeAnswerUpdateModal="closeAnswerUpdateModal" />
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";
import { useUserStore } from "@/stores/userStore";
import { useToastStore } from "@/stores/toastStore"
import { decryptStringSalt } from "@/utils/common";
import { userRoleList } from "@/references/config";

import Button from 'primevue/button'
import BoardInfo from '@/components/element/BoardInfo.vue'
import responseData from "@/interfaces/common/responseData";
import ApiService from "@/services/ApiService";

import QnAAnswerRegist from './QnAAnswerRegist.vue'
import QnAAnswerUpdate from './QnAAnswerUpdate.vue'

import type commonAnswer from "@/interfaces/common/commonAnswer";

const userStore = useUserStore()
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
const qnaAnswerContent = ref<commonAnswer[]>([])
const isAnswerRegistModal = ref(false)
const isAnswerUpdateModal = ref(false)
const answerUUID = ref('')

onMounted(() => {
  qnaUUID.value = history.state.uuid
  qnaTitle.value = ''
  qnaContent.value = ''
  qnaAnswer.value = 'NO'
  qnaAuthorName.value = ''
  qnaAuthorUUID.value = ''
  qnaInsertDate.value = ''
  qnaUpdateDate.value = ''
  qnaAnswerContent.value = []

  isAnswerRegistModal.value = false
  isAnswerUpdateModal.value = false
  answerUUID.value = ''

  getQnAInfo()
})

const showAnswerRegistModal = () => {
  isAnswerRegistModal.value = true
}

const closeAnswerRegistModal = () => {
  isAnswerRegistModal.value = false
  getQnAInfo()
}

const showAnswerUpdateModal = (uuid: string) => {
  answerUUID.value = uuid
  isAnswerUpdateModal.value = true
}

const closeAnswerUpdateModal = () => {
  answerUUID.value = ''
  isAnswerUpdateModal.value = false
  getQnAInfo()
}

const isUpdate = (insertDate: string, updateDate: string) => {
  return insertDate != updateDate
}

const isOwner = () => {
  return qnaAuthorUUID.value == decryptStringSalt(userStore.getCurrentUser.uud)
}

const isOverManager = () => {
  return userRoleList.findIndex(x => x.value == decryptStringSalt(userStore.getUserRole)) <= 2
}

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
    qnaAuthorName.value = infoResult.retData.memberNickName
    qnaAuthorUUID.value = infoResult.retData.memberUUID
    qnaInsertDate.value = infoResult.retData.insertDate.replace("T", " ")
    qnaUpdateDate.value = infoResult.retData.updateDate.replace("T", " ")
    qnaAnswerContent.value = infoResult.retData.qnaAnswerLists
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

const answerDeleteAction = async (uuid: string) => {
  const reqHeader = { accept: 'application/json' }
  const deleteResult: responseData = await ApiService.requestAPI({
    headers: reqHeader,
    method: 'DELETE',
    url: `/board/qna/answer/delete/${uuid}`
  })
  if (deleteResult.retStatus) {
    toastStore.setToastValue({
      severity: 'success',
      summary: 'QnA 관리',
      detail: '답변 삭제 되었습니다.',
      life: 3000
    })
    getQnAInfo()
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
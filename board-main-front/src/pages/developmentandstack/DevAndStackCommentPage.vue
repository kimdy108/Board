<template>
  <Dialog :visible="showModal" modal :closable="false" class="w-11/12">
    <div class="text-left mb-10 ml-5">
      <h2 class="text-4xl">댓글</h2>
    </div>
    <div class="text-left divide-y ml-5">
      <div v-if="items.length === 0">
        <div class="text-2xl font-bold mt-5 mb-5">등록된 댓글이 없습니다.</div>
      </div>
      <div v-for="item in items" :key="item.boardCommentGuid" class="mb-5">
        <div class="font-bold mt-5">
          {{ item.boardCommentMemberNickName }}
        </div>
        <div class="text-xl">{{ item.boardCommentDate }}</div>
        <div class="text-2xl mt-3">
          <InputText
            v-if="isFixComment && fixCommentGuid === item.boardCommentGuid"
            class="w-full mb-1"
            type="text"
            v-model="fixComment"
          ></InputText>
          <div v-else>{{ item.boardComment }}</div>
        </div>
        <div
          v-if="isFixComment && fixCommentGuid === item.boardCommentGuid"
          class="flex justify-end"
        >
          <Button label="수정" class="mr-2" severity="info" @click="updateCommentFunction()" />
          <Button label="취소" class="mr-2" severity="danger" @click="cancelUpdateComment()" />
        </div>
        <div v-else class="flex justify-end">
          <Button
            label="수정"
            class="mr-2"
            severity="info"
            @click="updateComment(item)"
            :style="isEditOwnerFunction(item.boardCommentMemberGuid) ? '' : 'display: none'"
          />
          <Button
            label="삭제"
            class="mr-2"
            severity="danger"
            @click="deleteComment(item.boardCommentGuid)"
            :style="isDeleteOwnerFunction(item.boardCommentMemberGuid) ? '' : 'display: none'"
          />
        </div>
      </div>
    </div>
    <hr class="mb-5 ml-5" />
    <div class="mb-5 ml-5">
      <InputText class="w-11/12" type="text" v-model="newComment" />
      <Button @click="registCommentFunction" class="ml-5" label="등록" />
    </div>
    <hr class="mb-5 ml-5" />
    <div class="flex justify-end mr-1">
      <Button label="닫기" @click="closeCommentModal" />
    </div>
  </Dialog>
</template>

<script setup>
import Dialog from 'primevue/dialog'
import Button from 'primevue/button'
import InputText from 'primevue/inputtext'
import ApiService from '@/services/ApiService'
import { useUserStore } from '@/stores/userStore'
import { useToastStore } from '@/stores/toastStore'
import { decryptStringSalt } from '@/utils/common'
import { ref, watch } from 'vue'

const props = defineProps({
  showModal: Boolean,
  boardGuid: String
})
const emit = defineEmits({
  closeCommentModal: Boolean
})

const userStore = useUserStore()
const toastStore = useToastStore()

const newComment = ref('')

const isFixComment = ref(false)
const fixComment = ref('')
const fixCommentGuid = ref('')

const items = ref([
  // boardGuid
  // boardCommentGuid
  // boardComment
  // boardCommentMemberGuid
  // boardCommentMemberNickName
  // boardCommentDate
])

const initValue = () => {
  items.value = []
  newComment.value = ''
  isFixComment.value = false
  fixComment.value = ''
  fixCommentGuid.value = ''
}

const closeCommentModal = () => {
  initValue()
  emit('closeCommentModal')
}

const updateComment = (item) => {
  isFixComment.value = true
  fixComment.value = item.boardComment
  fixCommentGuid.value = item.boardCommentGuid
}

const cancelUpdateComment = () => {
  isFixComment.value = false
  fixComment.value = ''
  fixCommentGuid.value = ''
}

const isEditOwnerFunction = (commentWriterGuid) => {
  const userStoreGuid = userStore.getUserAccess.ugd
  if (!userStoreGuid) return false
  else return decryptStringSalt(userStoreGuid) === commentWriterGuid
}

const isDeleteOwnerFunction = (commentWriterGuid) => {
  const userStoreGuid = userStore.getUserAccess.ugd
  if (!userStoreGuid) return false
  else
    return (
      decryptStringSalt(userStoreGuid) === commentWriterGuid ||
      decryptStringSalt(userStore.getUserRole) === 'manager'
    )
}

const registCommentFunction = () => {
  if (newComment.value === '' || newComment.value === null) {
    toastStore.setToastValue({
      severity: 'warn',
      summary: '댓글 등록',
      detail: '댓글을 입력해주세요.',
      life: 3000
    })
  } else registCommentApi()
}

const updateCommentFunction = () => {
  if (fixComment.value === '' || fixComment.value === null) {
    toastStore.setToastValue({
      severity: 'warn',
      summary: '댓글 등록',
      detail: '댓글을 입력해주세요.',
      life: 3000
    })
  } else updateCommentApi()
}

const registCommentApi = async () => {
  const result = await ApiService.requestAPI({
    headers: { accept: 'application/json' },
    method: 'POST',
    url: '/board/dev/stack/comment/regist',
    data: {
      boardGuid: props.boardGuid,
      comment: newComment.value
    }
  })
  if (result === 'success') {
    toastStore.setToastValue({
      severity: 'success',
      summary: '댓글 등록',
      detail: '댓글 등록에 성공했습니다.',
      life: 3000
    })
    newComment.value = ''

    isFixComment.value = false
    fixComment.value = ''
    fixCommentGuid.value = ''

    devAndStackCommentListApi()
  }
}

const updateCommentApi = async () => {
  const result = await ApiService.requestAPI({
    headers: { accept: 'application/json' },
    method: 'PUT',
    url: '/board/dev/stack/comment/update',
    data: {
      commentGuid: fixCommentGuid.value,
      commentString: fixComment.value
    }
  })
  if (result === 'success') {
    toastStore.setToastValue({
      severity: 'success',
      summary: '댓글 수정',
      detail: '댓글 수정에 성공했습니다.',
      life: 3000
    })
    isFixComment.value = false
    newComment.value = ''
    fixComment.value = ''
    fixCommentGuid.value = ''

    devAndStackCommentListApi()
  }
}

const deleteComment = async (event) => {
  const result = await ApiService.requestAPI({
    headers: { accept: 'application/json' },
    method: 'DELETE',
    url: `/board/dev/stack/comment/delete/${event}`
  })
  if (result === 'success') {
    toastStore.setToastValue({
      severity: 'success',
      summary: '댓글 삭제',
      detail: '댓글이 삭제되었습니다.',
      life: 3000
    })
    newComment.value = ''

    isFixComment.value = false
    fixComment.value = ''
    fixCommentGuid.value = ''

    devAndStackCommentListApi()
  }
}

const devAndStackCommentListApi = async () => {
  const result = await ApiService.requestAPI({
    headers: { accept: 'application/json' },
    method: 'GET',
    url: '/board/dev/stack/comment/list/all',
    params: {
      developmentStackGuid: props.boardGuid
    }
  })
  for (let i in result) {
    result[i].boardCommentDate = result[i].boardCommentDate.split('T')[0]
  }
  items.value = result
}

watch(
  () => props.showModal,
  (newVal) => {
    if (newVal) devAndStackCommentListApi()
  }
)
</script>

<style lang="scss" scoped></style>

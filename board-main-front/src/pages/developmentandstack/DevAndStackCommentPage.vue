<template>
  <div class="text-left ml-16 mt-16 mb-10">
    <h2 class="text-8xl mb-5 text-black">댓글</h2>
  </div>
  <Panel class="ml-16 mr-16 mt-5" v-for="item in items" :key="item.commentSeq">
    <p class="text-left text-3xl">{{ item.boardComment }}</p>
    <p class="text-left text-lg">{{ item.boardCommentMemberNickName }}</p>
    <p class="text-left text-lg">{{ item.boardCommentDate }}</p>
    <div class="flex justify-end">
      <Button
        label="수정"
        size="large"
        severity="info"
        class="mr-2"
        @click="updateComment(item.boardCommentGuid, item.boardComment)"
        :style="isEditOwnerFunction(item.boardCommentMemberGuid) ? '' : 'display: none'"
      />
      <Button
        label="삭제"
        size="large"
        severity="danger"
        @click="deleteComment(item.boardCommentGuid)"
        :style="isDeleteOwnerFunction(item.boardCommentMemberGuid) ? '' : 'display: none'"
      />
    </div>
  </Panel>
  <hr class="mt-5 ml-16 mr-16 mb-5" />
  <div class="flex ml-16 mr-16" v-if="!isFixComment">
    <InputText style="width: 1350px; height: 50px; font-size: 20px" v-model="newCommnet" />
    <Button label="등록" size="large" style="width: 65px" @click="registCommentFunction()" />
  </div>
  <div class="flex ml-16 mr-16" v-else>
    <InputText style="width: 1350px; height: 50px; font-size: 20px" v-model="fixComment" />
    <Button
      label="수정"
      size="large"
      severity="info"
      style="width: 65px"
      @click="updateCommentFunction()"
    />
    <Button label="취소" size="large" style="width: 65px" @click="updateCommentCancel()" />
  </div>
  <hr class="mt-5 ml-16 mr-16 mb-10" />
  <div class="text-left mr-16 flex justify-end">
    <Button label="뒤로가기" size="large" class="mr-2" @click="goDevAndStackView()" />
  </div>
</template>

<script setup>
import Button from 'primevue/button'
import Panel from 'primevue/panel'
import InputText from 'primevue/inputtext'
import ApiService from '@/services/ApiService'
import { useRouter } from 'vue-router'
import { useUserStore } from '@/stores/userStore'
import { decryptStringSalt } from '@/utils/common'
import { onMounted, ref } from 'vue'

const props = defineProps({
  boardGuid: String
})

const router = useRouter()
const userStore = useUserStore()

const items = ref([])

const newCommnet = ref('')

const isFixComment = ref(false)
const fixComment = ref('')
const fixCommentGuid = ref('')

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
  if (newCommnet.value === '' || newCommnet.value === null) alert('댓글을 입력해주세요.')
  else registCommentApi()
}

const updateCommentFunction = () => {
  if (fixComment.value === '' || fixComment.value === null) alert('댓글을 입력해주세요.')
  else updateCommentApi()
}

const updateComment = (commentGuid, commentValue) => {
  isFixComment.value = true
  fixCommentGuid.value = commentGuid
  fixComment.value = commentValue
}

const updateCommentCancel = () => {
  isFixComment.value = false
  fixComment.value = ''
  fixCommentGuid.value = ''
}

const goDevAndStackView = () => {
  const boardGuid = props.boardGuid
  router.push({ name: 'DevAndStackViewPage', params: { boardGuid } }).catch(() => {
    console.log('DevAndStackViewPageError')
  })
}

const registCommentApi = async () => {
  const result = await ApiService.requestAPI({
    headers: { accept: 'application/json' },
    method: 'POST',
    url: '/board/dev/stack/comment/regist',
    data: {
      boardGuid: props.boardGuid,
      comment: newCommnet.value
    }
  })
  if (result === 'success') {
    alert('댓글 등록에 성공했습니다.')
    newCommnet.value = ''

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
    alert('댓글 수정에 성공했습니다.')
    isFixComment.value = false
    newCommnet.value = ''
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
    alert('댓글이 삭제되었습니다.')
    newCommnet.value = ''
    devAndStackCommentListApi()

    isFixComment.value = false
    fixComment.value = ''
    fixCommentGuid.value = ''
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

onMounted(() => {
  devAndStackCommentListApi()
})
</script>

<style lang="scss" scoped></style>

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
        @click="alertGuid(item.boardCommentGuid)"
      />
      <Button
        label="삭제"
        size="large"
        severity="danger"
        @click="alertGuid(item.boardCommentGuid)"
      />
    </div>
  </Panel>
  <hr class="mt-5 ml-16 mr-16 mb-10" />
  <div class="text-left mr-16 flex justify-end">
    <Button label="뒤로가기" size="large" class="mr-2" @click="goDevAndStackView()" />
  </div>
</template>

<script setup>
import Button from 'primevue/button'
import Panel from 'primevue/panel'
import { useRouter } from 'vue-router'
import ApiService from '@/services/ApiService'
import { onMounted, ref } from 'vue'

const props = defineProps({
  boardGuid: String
})

const router = useRouter()

const goDevAndStackView = () => {
  const boardGuid = props.boardGuid
  router.push({ name: 'DevAndStackViewPage', params: { boardGuid } }).catch(() => {
    console.log('DevAndStackViewPageError')
  })
}

const alertGuid = (event) => {
  console.log(event)
}

const items = ref()

const devAndStackCommentListApi = async () => {
  const result = await ApiService.requestAPI({
    headers: { accept: 'application/json' },
    method: 'GET',
    url: '/board/dev/stack/comment/list/all',
    params: {
      developmentStackGuid: props.boardGuid
    }
  })
  console.log(result)
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

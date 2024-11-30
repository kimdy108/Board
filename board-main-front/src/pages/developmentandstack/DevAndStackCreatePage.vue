<template>
  <div class="text-left text-black">
    <div class="ml-5 text-6xl p-10">개발 / 스택 등록</div>
  </div>
  <div class="card flex">
    <div class="flex flex-col gap-2 text-left ml-16">
      <label for="devAndStackTitle" class="text-3xl text-black">제목</label>
      <InputText
        id="devAndStackTitle"
        v-model="devAndStackTitleValue"
        size="large"
        style="width: 1400px; height: 50px; font-size: 20px"
      />
    </div>
  </div>
  <div class="card flex">
    <div class="flex flex-col gap-2 text-left ml-16">
      <label for="devAndStackContent" class="text-3xl text-black mt-5">내용</label>
      <Textarea
        id="devAndStackContent"
        v-model="devAndStackContentValue"
        rows="5"
        cols="30"
        style="width: 1400px; height: 300px; font-size: 20px"
      />
    </div>
  </div>
  <hr class="mt-16 ml-16 mr-16 mb-10" />
  <div class="text-left mr-16 flex justify-end">
    <Button label="목록" size="large" class="mr-2" @click="goDevAndStackList" />
    <Button
      label="등록"
      size="large"
      severity="info"
      class="mr-2"
      @click="devAndStackRegistFunction"
    />
  </div>
</template>

<script setup>
import InputText from 'primevue/inputtext'
import Textarea from 'primevue/textarea'
import Button from 'primevue/button'
import ApiService from '@/services/ApiService'
import { useToastStore } from '@/stores/toastStore'
import { useRouter } from 'vue-router'
import { ref } from 'vue'

const router = useRouter()
const toastStore = useToastStore()

const devAndStackTitleValue = ref('')
const devAndStackContentValue = ref('')

const goDevAndStackList = () => {
  router.push({ name: 'DevelopmentAndStackPage' }).catch(() => {
    console.log('DevelopmentAndStackPageError')
  })
}

const devAndStackRegistFunction = () => {
  if (devAndStackTitleValue.value === '') {
    toastStore.setToastValue({
      severity: 'warn',
      summary: '게시글 등록',
      detail: '제목을 입력해주세요.',
      life: 3000
    })
  } else if (devAndStackContentValue.value === '') {
    toastStore.setToastValue({
      severity: 'warn',
      summary: '게시글 등록',
      detail: '내용을 입력해주세요.',
      life: 3000
    })
  } else devAndStackRegistApi()
}

const devAndStackRegistApi = async () => {
  const result = await ApiService.requestAPI({
    headers: { accept: 'application/json' },
    method: 'POST',
    url: '/board/dev/stack/regist',
    data: {
      boardTitle: devAndStackTitleValue.value,
      boardContent: devAndStackContentValue.value
    }
  })
  if (result === 'success') {
    toastStore.setToastValue({
      severity: 'success',
      summary: '게시글 등록',
      detail: '등록이 완료되었습니다.',
      life: 3000
    })
    goDevAndStackList()
  }
}
</script>

<style lang="scss" scoped></style>

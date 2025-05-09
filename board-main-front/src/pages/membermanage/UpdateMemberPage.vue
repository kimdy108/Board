<template>
  <Dialog :visible="showModal" modal :closable="false" class="w-2/3">
    <div class="text-left text-black">
      <div class="ml-5 text-3xl p-10">회원 수정</div>
    </div>
    <div class="card flex">
      <div class="gap-2 text-left ml-16">
        <label for="userId" class="flex flex-col text-xl text-black">아이디</label>
        <InputText id="userId" v-model="userIdValue" style="width: 400px" size="large" disabled />
      </div>
      <div class="gap-2 text-left ml-16">
        <label for="userName" class="flex flex-col text-xl text-black">이름</label>
        <InputText id="userName" v-model="userNameValue" style="width: 400px" size="large" />
      </div>
    </div>
    <div class="card flex mt-16">
      <div class="gap-2 text-left ml-16">
        <label for="userNickName" class="flex flex-col text-xl text-black">닉네임</label>
        <InputText
          id="userNickName"
          v-model="userNickNameValue"
          style="width: 400px"
          size="large"
          disabled
        />
      </div>
      <div class="gap-2 text-left ml-16">
        <Button
          label="비밀번호 초기화"
          size="large"
          class="mt-10"
          @click="resetPasswordFunction"
        ></Button>
      </div>
    </div>
    <div class="card flex mt-16">
      <div class="gap-2 text-left ml-16">
        <label for="userEmail" class="flex flex-col text-xl text-black">이메일</label>
        <InputText id="userEmail" v-model="userEmailValue" style="width: 400px" size="large" />
      </div>
      <div class="gap-2 text-left ml-16">
        <label for="userPhone" class="flex flex-col text-xl text-black">전화번호</label>
        <InputText id="userPhone" v-model="userPhoneValue" style="width: 400px" size="large" />
      </div>
    </div>
    <hr class="mt-16 ml-16 mr-16 mb-10" />
    <div class="text-left mr-16 flex justify-end">
      <Button label="닫기" size="large" severity="help" class="mr-2" @click="closeUpdateModal()" />
      <Button label="수정" size="large" severity="info" class="mr-2" @click="userUpdateFunction" />
    </div>
  </Dialog>
</template>

<script setup>
import InputText from 'primevue/inputtext'
import Button from 'primevue/button'
import Dialog from 'primevue/dialog'
import ApiService from '@/services/ApiService'
import { useToastStore } from '@/stores/toastStore'
import { ref, watch } from 'vue'
import { decryptStringSalt, encryptStringSalt } from '@/utils/common'

const toastStore = useToastStore()
const props = defineProps({
  userGuid: String,
  showModal: Boolean
})
const emit = defineEmits({
  closeUpdateModal: Boolean
})

const userIdValue = ref('')
const userNameValue = ref('')
const userNickNameValue = ref('')
const userEmailValue = ref('')
const userPhoneValue = ref('')

const initValue = () => {
  userIdValue.value = ''
  userNameValue.value = ''
  userNickNameValue.value = ''
  userEmailValue.value = ''
  userPhoneValue.value = ''
}

const closeUpdateModal = () => {
  initValue()
  emit('closeUpdateModal')
}

const userUpdateFunction = () => {
  if (userNameValue.value == null || userNameValue.value == '') {
    toastStore.setToastValue({
      severity: 'warn',
      summary: '회원수정',
      detail: '이름을 입력해주세요.',
      life: 3000
    })
  } else if (userEmailValue.value == null || userEmailValue.value == '') {
    toastStore.setToastValue({
      severity: 'warn',
      summary: '회원수정',
      detail: '이메일을 입력해주세요.',
      life: 3000
    })
  } else if (userPhoneValue.value == null || userPhoneValue.value == '') {
    toastStore.setToastValue({
      severity: 'warn',
      summary: '회원수정',
      detail: '전화번호를 입력해주세요.',
      life: 3000
    })
  } else userUpdateApi()
}

const getUserInfoFunction = async () => {
  const result = await ApiService.requestAPI({
    headers: { accept: 'application/json' },
    method: 'GET',
    url: '/member/info/manage',
    params: {
      userGuid: props.userGuid
    }
  })
  userIdValue.value = decryptStringSalt(result.userId)
  userNameValue.value = decryptStringSalt(result.userName)
  userNickNameValue.value = decryptStringSalt(result.userNickName)
  userEmailValue.value = decryptStringSalt(result.userEmail)
  userPhoneValue.value = decryptStringSalt(result.userPhone)
}

const userUpdateApi = async () => {
  const result = await ApiService.requestAPI({
    headers: { accept: 'application/json' },
    method: 'PUT',
    url: '/member/update/manage',
    data: {
      userGuid: props.userGuid,
      encryptUserName: encryptStringSalt(userNameValue.value),
      encryptUserEmail: encryptStringSalt(userEmailValue.value),
      encryptUserPhone: encryptStringSalt(userPhoneValue.value)
    }
  })
  if (result === 'success') {
    toastStore.setToastValue({
      severity: 'success',
      summary: '회원수정',
      detail: '회원정보가 수정되었습니다',
      life: 3000
    })
    closeUpdateModal()
  } else {
    toastStore.setToastValue({
      severity: 'error',
      summary: '회원수정',
      detail: '수정에 실패했습니다.',
      life: 3000
    })
  }
}

const resetPasswordFunction = async () => {
  const result = await ApiService.requestAPI({
    headers: { accept: 'application/json' },
    method: 'PUT',
    url: '/member/reset/password/manage',
    data: {
      userGuid: props.userGuid
    }
  })
  if (result === 'success') {
    toastStore.setToastValue({
      severity: 'success',
      summary: '회원수정',
      detail: '비밀번호가 초기화 되었습니다.',
      life: 3000
    })
  }
}

watch(
  () => props.showModal,
  (newVal) => {
    if (newVal) getUserInfoFunction()
  }
)
</script>

<style lang="scss" scoped></style>

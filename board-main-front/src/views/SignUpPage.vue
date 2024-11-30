<template>
  <div class="bg-cover bg-center bg-fixed">
    <div class="h-screen flex justify-center items-center">
      <div class="w-full sm:w-80 flex flex-col gap-6 shadow-lg p-16 md:w-1/3 lg:w-1/4">
        <div class="flex items-center">
          <IconField class="md:w-2/3">
            <InputIcon>
              <i class="pi pi-user" />
            </InputIcon>
            <InputText
              v-model="userId"
              type="text"
              placeholder="ID"
              autofocus
              fluid
              v-bind:disabled="checkPossibleToJoinId"
            />
          </IconField>
          <Button label="중복 확인" class="md:w-1/3" @click="checkPossibleToJoinIdFunction" />
        </div>

        <IconField>
          <InputIcon>
            <i class="pi pi-user-plus" />
          </InputIcon>
          <InputText v-model="userName" type="text" placeholder="이름" fluid />
        </IconField>

        <IconField>
          <InputIcon>
            <i class="pi pi-lock" />
          </InputIcon>
          <InputText
            v-model="userPassword"
            :type="isVisibleSignUpPassword ? 'text' : 'password'"
            placeholder="비밀번호"
            fluid
          />
          <InputIcon>
            <i
              :class="isVisibleSignUpPassword ? 'pi pi-eye' : 'pi pi-eye-slash'"
              @click="changeIsVisibleSignUpPassword"
            />
          </InputIcon>
        </IconField>

        <div class="flex items-center">
          <IconField class="md:w-2/3">
            <InputIcon>
              <i class="pi pi-user-minus" />
            </InputIcon>
            <InputText
              v-model="userNickName"
              type="text"
              placeholder="닉네임"
              fluid
              v-bind:disabled="checkPossibleToJoinNickName"
            />
          </IconField>
          <Button label="중복 확인" class="md:w-1/3" @click="checkPossibleToJoinNickNameFunction" />
        </div>

        <div class="flex items-center">
          <IconField class="md:w-2/3">
            <InputIcon>
              <i class="pi pi-mobile" />
            </InputIcon>
            <InputText
              v-model="userPhone"
              type="text"
              placeholder="전화번호"
              fluid
              v-bind:disabled="checkPossibleToJoinPhone"
            />
          </IconField>
          <Button label="중복 확인" class="md:w-1/3" @click="checkPossibleToJoinPhoneFunction" />
        </div>

        <IconField>
          <InputIcon>
            <i class="pi pi-send" />
          </InputIcon>
          <InputText v-model="userEmail" type="text" placeholder="이메일" fluid />
        </IconField>

        <div class="flex items-center">
          <Button class="md:w-1/2" @click="toLoginPage" label="뒤로" />
          <Button class="md:w-1/2" @click="apiSingUpFunction" label="회원가입" />
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import IconField from 'primevue/iconfield'
import InputIcon from 'primevue/inputicon'
import InputText from 'primevue/inputtext'
import Button from 'primevue/button'
import ApiService from '@/services/ApiService'
import { useToastStore } from '@/stores/toastStore'
import { encryptStringSalt } from '@/utils/common'
import { ref } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()

const toastStore = useToastStore()

const isVisibleSignUpPassword = ref(false)

const userId = ref('')
const userName = ref('')
const userPassword = ref('')
const userNickName = ref('')
const userPhone = ref('')
const userEmail = ref('')

const checkPossibleToJoinId = ref(false)
const checkPossibleToJoinNickName = ref(false)
const checkPossibleToJoinPhone = ref(false)

const toLoginPage = () => {
  router.push({ name: 'LoginPage' }).catch(() => {
    console.log('loginerror')
  })
}

const checkPossibleToJoinIdFunction = () => {
  if (userId.value == null || userId.value == '') {
    toastStore.setToastValue({
      severity: 'warn',
      summary: '회원가입 오류',
      detail: '아이디를 입력해주세요.',
      life: 3000
    })
  } else checkPossibleToJoinIdAPI()
}
const checkPossibleToJoinNickNameFunction = () => {
  if (userNickName.value == null || userNickName.value == '') {
    toastStore.setToastValue({
      severity: 'warn',
      summary: '회원가입 오류',
      detail: '닉네임을 입력해주세요.',
      life: 3000
    })
  } else checkPossibleToJoinNickNameAPI()
}
const checkPossibleToJoinPhoneFunction = () => {
  if (userPhone.value == null || userPhone.value == '') {
    toastStore.setToastValue({
      severity: 'warn',
      summary: '회원가입 오류',
      detail: '전화번호를 입력해주세요.',
      life: 3000
    })
  } else checkPossibleToJoinPhoneAPI()
}

const changeIsVisibleSignUpPassword = () => {
  isVisibleSignUpPassword.value = !isVisibleSignUpPassword.value
}

const apiSingUpFunction = () => {
  if (!checkPossibleToJoinId.value) {
    toastStore.setToastValue({
      severity: 'warn',
      summary: '회원가입 오류',
      detail: '아이디 중복확인이 되어있지 않습니다.',
      life: 3000
    })
  } else if (!checkPossibleToJoinNickName.value) {
    toastStore.setToastValue({
      severity: 'warn',
      summary: '회원가입 오류',
      detail: '닉네임 중복확인이 되어있지 않습니다.',
      life: 3000
    })
  } else if (!checkPossibleToJoinPhone.value) {
    toastStore.setToastValue({
      severity: 'warn',
      summary: '회원가입 오류',
      detail: '전화번호 중복확인이 되어있지 않습니다.',
      life: 3000
    })
  } else if (userName.value == null || userName.value == '') {
    toastStore.setToastValue({
      severity: 'warn',
      summary: '회원가입 오류',
      detail: '이름을 입력해주세요.',
      life: 3000
    })
  } else if (userPassword.value == null || userPassword.value == '') {
    toastStore.setToastValue({
      severity: 'warn',
      summary: '회원가입 오류',
      detail: '비밀번호를 입력해주세요.',
      life: 3000
    })
  } else if (userEmail.value == null || userEmail.value == '') {
    toastStore.setToastValue({
      severity: 'warn',
      summary: '회원가입 오류',
      detail: '이메일을 입력해주세요.',
      life: 3000
    })
  } else apiSingUpFunctionAPI()
}

const apiSingUpFunctionAPI = async () => {
  let result = await ApiService.requestAPI({
    headers: { accept: 'application/json' },
    method: 'POST',
    url: '/member/join',
    data: {
      id: encryptStringSalt(userId.value),
      name: encryptStringSalt(userName.value),
      password: encryptStringSalt(userPassword.value),
      nickName: encryptStringSalt(userNickName.value),
      phone: encryptStringSalt(userPhone.value),
      email: encryptStringSalt(userEmail.value)
    }
  })
  if (result === 'success') {
    toastStore.setToastValue({
      severity: 'success',
      summary: '회원가입 성공',
      detail: '회원가입이 완료되었습니다.',
      life: 3000
    })
    router.push({ name: 'LoginPage' }).catch(() => {
      console.log('loginerror')
    })
  } else {
    toastStore.setToastValue({
      severity: 'error',
      summary: '회원가입 오류',
      detail: '회원가입에 실패했습니다.',
      life: 3000
    })
  }
}

const checkPossibleToJoinIdAPI = async () => {
  let checkIdResult = await ApiService.requestAPI({
    headers: { accept: 'application/json' },
    method: 'GET',
    url: '/member/check/join/id',
    params: {
      id: encryptStringSalt(userId.value)
    }
  })
  if (checkIdResult === true) {
    if (!checkPossibleToJoinId.value) {
      toastStore.setToastValue({
        severity: 'info',
        summary: '회원가입',
        detail: '사용 가능한 아이디 입니다.',
        life: 3000
      })
    }
    checkPossibleToJoinId.value = !checkPossibleToJoinId.value
  } else {
    toastStore.setToastValue({
      severity: 'error',
      summary: '회원가입 오류',
      detail: '사용할 수 없는 아이디 입니다.',
      life: 3000
    })
  }
}

const checkPossibleToJoinNickNameAPI = async () => {
  let checkIdResult = await ApiService.requestAPI({
    headers: { accept: 'application/json' },
    method: 'GET',
    url: '/member/check/join/nickname',
    params: {
      nickName: encryptStringSalt(userNickName.value)
    }
  })
  if (checkIdResult === true) {
    if (!checkPossibleToJoinNickName.value) {
      toastStore.setToastValue({
        severity: 'info',
        summary: '회원가입',
        detail: '사용 가능한 닉네임 입니다.',
        life: 3000
      })
    }
    checkPossibleToJoinNickName.value = !checkPossibleToJoinNickName.value
  } else {
    toastStore.setToastValue({
      severity: 'error',
      summary: '회원가입',
      detail: '사용할 수 없는 닉네임 입니다.',
      life: 3000
    })
  }
}

const checkPossibleToJoinPhoneAPI = async () => {
  let checkIdResult = await ApiService.requestAPI({
    headers: { accept: 'application/json' },
    method: 'GET',
    url: '/member/check/join/phone',
    params: {
      phone: encryptStringSalt(userPhone.value)
    }
  })
  if (checkIdResult === true) {
    if (!checkPossibleToJoinPhone.value) {
      toastStore.setToastValue({
        severity: 'info',
        summary: '회원가입',
        detail: '가입 가능한 전화번호 입니다.',
        life: 3000
      })
    }
    checkPossibleToJoinPhone.value = !checkPossibleToJoinPhone.value
  } else {
    toastStore.setToastValue({
      severity: 'error',
      summary: '회원가입',
      detail: '가입되어있는 전화번호 입니다.',
      life: 3000
    })
  }
}
</script>

<style lang="scss" scoped></style>

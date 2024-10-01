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
import { encryptStringSalt } from '@/utils/common'
import { ref } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()

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
  if (userId.value == null || userId.value == '') alert('아이디를 입력해주세요.')
  else checkPossibleToJoinIdAPI()
}
const checkPossibleToJoinNickNameFunction = () => {
  if (userNickName.value == null || userNickName.value == '') alert('닉네임을 입력해주세요.')
  else checkPossibleToJoinNickNameAPI()
}
const checkPossibleToJoinPhoneFunction = () => {
  if (userPhone.value == null || userPhone.value == '') alert('전화번호를 입력해주세요.')
  else checkPossibleToJoinPhoneAPI()
}

const changeIsVisibleSignUpPassword = () => {
  isVisibleSignUpPassword.value = !isVisibleSignUpPassword.value
}

const apiSingUpFunction = () => {
  if (!checkPossibleToJoinId.value) alert('아이디 중복확인이 되어있지 않습니다.')
  else if (!checkPossibleToJoinNickName.value) alert('닉네임 중복확인이 되어있지 않습니다.')
  else if (!checkPossibleToJoinPhone.value) alert('전화번호 중복확인이 되어있지 않습니다.')
  else if (userName.value == null || userName.value == '') alert('이름을 입력해주세요.')
  else if (userPassword.value == null || userPassword.value == '') alert('비밀번호를 입력해주세요.')
  else if (userEmail.value == null || userEmail.value == '') alert('이메일을 입력해주세요.')
  else apiSingUpFunctionAPI()
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
    alert('회원가입이 완료되었습니다.')
    router.push({ name: 'LoginPage' }).catch(() => {
      console.log('loginerror')
    })
  } else {
    alert('회원가입에 실패했습니다.')
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
    if (!checkPossibleToJoinId.value) alert('사용 가능한 아이디 입니다.')
    checkPossibleToJoinId.value = !checkPossibleToJoinId.value
  } else {
    alert('사용할 수 없는 아이디 입니다.')
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
    if (!checkPossibleToJoinNickName.value) alert('사용 가능한 닉네임 입니다.')
    checkPossibleToJoinNickName.value = !checkPossibleToJoinNickName.value
  } else {
    alert('사용할 수 없는 닉네임 입니다.')
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
    if (!checkPossibleToJoinPhone.value) alert('가입 가능한 전화번호 입니다.')
    checkPossibleToJoinPhone.value = !checkPossibleToJoinPhone.value
  } else {
    alert('가입되어있는 전화번호 입니다.')
  }
}
</script>

<style lang="scss" scoped></style>

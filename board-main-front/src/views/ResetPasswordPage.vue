<template>
  <div v-if="!isPossible">
    <div class="bg-cover bg-center bg-fixed">
      <div class="h-screen flex justify-center items-center">
        <div class="w-full sm:w-80 flex flex-col gap-6 shadow-lg w-full p-16 md:w-1/3 lg:w-1/4">
          <img src="@/assets/image/board.png" class="mb-10" />
          <IconField>
            <InputIcon>
              <i class="pi pi-user" />
            </InputIcon>
            <InputText v-model="userId" type="text" placeholder="ID" autofocus fluid />
          </IconField>

          <IconField>
            <InputIcon>
              <i class="pi pi-mobile" />
            </InputIcon>
            <InputText v-model="userPhone" type="text" placeholder="전화번호" fluid />
          </IconField>

          <div class="flex items-center">
            <Button class="md:w-1/2" @click="toLoginPage" label="뒤로"></Button>
            <Button
              class="md:w-1/2"
              @click="checkPossibleToChangePassword"
              label="비밀번호 재설정"
            ></Button>
          </div>
        </div>
      </div>
    </div>
  </div>
  <div v-else>
    <div class="bg-cover bg-center bg-fixed">
      <div class="h-screen flex justify-center items-center">
        <div class="w-full sm:w-80 flex flex-col gap-6 shadow-lg w-full p-16 md:w-1/3 lg:w-1/4">
          <img src="@/assets/image/board.png" class="mb-10" />
          <IconField>
            <InputIcon>
              <i class="pi pi-lock" />
            </InputIcon>
            <InputText
              v-model="userNewPassword"
              :type="isVisibleNewPassword ? 'text' : 'password'"
              placeholder="비밀번호"
              autofocus
              fluid
            />
            <InputIcon>
              <i
                :class="isVisibleNewPassword ? 'pi pi-eye' : 'pi pi-eye-slash'"
                @click="changeIsVisibleNewPassword"
              />
            </InputIcon>
          </IconField>

          <IconField>
            <InputIcon>
              <i class="pi pi-lock" />
            </InputIcon>
            <InputText
              v-model="confirmUserNewPassword"
              :type="isVisibleConfirmNewPassword ? 'text' : 'password'"
              placeholder="비밀번호 확인"
              fluid
            />
            <InputIcon>
              <i
                :class="isVisibleConfirmNewPassword ? 'pi pi-eye' : 'pi pi-eye-slash'"
                @click="changeIsVisibleConfirmNewPassword"
              />
            </InputIcon>
          </IconField>

          <Button @click="changePassword" label="비밀번호 재설정"></Button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import Button from 'primevue/button'
import IconField from 'primevue/iconfield'
import InputIcon from 'primevue/inputicon'
import InputText from 'primevue/inputtext'
import ApiService from '@/services/ApiService'
import { useRouter } from 'vue-router'
import { encryptStringSalt, decryptStringSalt } from '@/utils/common'

const router = useRouter()

let isPossible = ref(false)

const isVisibleNewPassword = ref(false)
const isVisibleConfirmNewPassword = ref(false)

const userId = ref('')
const userPhone = ref('')

const userNewPassword = ref('')
const confirmUserNewPassword = ref('')

let result = null
const boardMember = ref(null)

const toLoginPage = () => {
  router.push({ name: 'LoginPage' }).catch(() => {
    console.log('loginerror')
  })
}

const checkPossibleToChangePassword = () => {
  if (userId.value == '') alert('noID')
  else if (userPhone.value == '') alert('noPhone')
  else checkPossibleToChangePasswordAPI()
}

const changePassword = () => {
  if (!userNewPassword.value === confirmUserNewPassword.value)
    alert('비밀번호가 일치하지 않습니다.')
  else changePasswordAPI()
}

const changeIsVisibleNewPassword = () => {
  isVisibleNewPassword.value = !isVisibleNewPassword.value
}

const changeIsVisibleConfirmNewPassword = () => {
  isVisibleConfirmNewPassword.value = !isVisibleConfirmNewPassword.value
}

const checkPossibleToChangePasswordAPI = async () => {
  try {
    result = await ApiService.requestAPI({
      headers: { accept: 'application/json' },
      method: 'GET',
      url: '/member/check/change/password',
      params: {
        id: encryptStringSalt(userId.value),
        userPhone: encryptStringSalt(userPhone.value)
      }
    })
    console.log(decryptStringSalt(result.memberId))
    if (decryptStringSalt(result.memberId) === userId.value) {
      boardMember.value = result
      isPossible.value = true
    } else {
      alert('NoUser')
    }
  } catch (error) {
    console.error('API 호출 오류:', error)
    alert('API 호출에 실패했습니다.')
  }
}

const changePasswordAPI = async () => {
  try {
    result = await ApiService.requestAPI({
      headers: { accept: 'application/json' },
      method: 'PUT',
      url: '/member/change/password',
      data: {
        userId: boardMember.value.memberId,
        userGuid: boardMember.value.memberGuid,
        password: encryptStringSalt(confirmUserNewPassword.value)
      }
    })
    if (result === 'success') {
      alert('수정이 완료되었습니다.')
      router.push({ name: 'LoginPage' }).catch(() => {
        console.log('loginerror')
      })
    } else {
      alert('NoUser')
    }
  } catch (error) {
    console.error('API 호출 오류:', error)
    alert('API 호출에 실패했습니다.')
  }
}
</script>

<style lang="scss" scoped></style>

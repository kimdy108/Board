<template>
  <div class="bg-cover bg-center bg-fixed">
    <div class="h-screen flex justify-center items-center">
      <div class="w-full sm:w-80 flex flex-col gap-6 shadow-lg w-full p-16 md:w-1/3 lg:w-1/4">
        <img src="@/assets/image/board.png" class="mb-10" />
        <IconField>
          <InputIcon>
            <i class="pi pi-lock" />
          </InputIcon>
          <InputText
            v-model="userOldPassword"
            :type="isVisibleOldPassword ? 'text' : 'password'"
            placeholder="이전 비밀번호"
            autofocus
            fluid
          />
          <InputIcon>
            <i
              :class="isVisibleOldPassword ? 'pi pi-eye' : 'pi pi-eye-slash'"
              @click="changeIsVisibleOldPassword"
            />
          </InputIcon>
        </IconField>

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

        <div class="flex">
          <Button
            @click="moveMyPage"
            label="마이페이지"
            style="width: 150px"
            severity="info"
            outlined
          ></Button>
          <Button
            @click="changePassword"
            label="비밀번호 변경"
            style="width: 150px"
            severity="danger"
            outlined
          ></Button>
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
import { encryptStringSalt } from '@/utils/common'

const router = useRouter()

const isVisibleOldPassword = ref(false)
const isVisibleNewPassword = ref(false)
const isVisibleConfirmNewPassword = ref(false)

const userOldPassword = ref('')
const userNewPassword = ref('')
const confirmUserNewPassword = ref('')

const changeIsVisibleOldPassword = () => {
  isVisibleOldPassword.value = !isVisibleOldPassword.value
}

const changeIsVisibleNewPassword = () => {
  isVisibleNewPassword.value = !isVisibleNewPassword.value
}

const changeIsVisibleConfirmNewPassword = () => {
  isVisibleConfirmNewPassword.value = !isVisibleConfirmNewPassword.value
}

const changePassword = () => {
  if (userNewPassword.value === confirmUserNewPassword.value) changePasswordAPI()
  else alert('비밀번호가 일치하지 않습니다.')
}

const moveMyPage = () => {
  router.push({ name: 'MyPage' }).catch(() => {
    console.log('MyPageError')
  })
}

const changePasswordAPI = async () => {
  try {
    const result = await ApiService.requestAPI({
      headers: { accept: 'application/json' },
      method: 'PUT',
      url: '/member/update/password',
      data: {
        oldPassword: encryptStringSalt(userOldPassword.value),
        newPassword: encryptStringSalt(userNewPassword.value)
      }
    })
    if (result === 'success') {
      alert('수정이 완료되었습니다.')
      moveMyPage()
    } else {
      alert('수정에 실패했습니다.')
    }
  } catch (error) {
    console.error('API 호출 오류:', error)
    alert('API 호출에 실패했습니다.')
  }
}
</script>

<style lang="scss" scoped></style>

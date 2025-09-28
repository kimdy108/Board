<template>
  <Dialog :visible="showModal" modal :closable="false" class="w-1/3 h-1/2">
    <div class="bg-cover bg-center bg-fixed">
      <div class="flex justify-center items-center">
        <div class="flex flex-col gap-6 w-2/3 mt-10">
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
              @click="closeChangePasswordModal()"
              label="닫기"
              style="width: 200px"
              severity="info"
              outlined
            ></Button>
            <Button
              @click="changePassword"
              label="비밀번호 변경"
              style="width: 200px"
              severity="danger"
              outlined
            ></Button>
          </div>
        </div>
      </div>
    </div>
  </Dialog>
</template>

<script setup>
import { ref } from 'vue'
import Button from 'primevue/button'
import IconField from 'primevue/iconfield'
import InputIcon from 'primevue/inputicon'
import InputText from 'primevue/inputtext'
import Dialog from 'primevue/dialog'
import ApiService from '@/services/ApiService'
import { useToastStore } from '@/stores/toastStore'
import { encryptStringSalt } from '@/utils/common'

const props = defineProps({
  showModal: Boolean
})
const emit = defineEmits({
  closeChangePasswordModal: Boolean
})

const toastStore = useToastStore()

const isVisibleOldPassword = ref(false)
const isVisibleNewPassword = ref(false)
const isVisibleConfirmNewPassword = ref(false)

const userOldPassword = ref('')
const userNewPassword = ref('')
const confirmUserNewPassword = ref('')

const initValue = () => {
  isVisibleOldPassword.value = false
  isVisibleNewPassword.value = false
  isVisibleConfirmNewPassword.value = false
  userOldPassword.value = ''
  userNewPassword.value = ''
  confirmUserNewPassword.value = ''
}

const closeChangePasswordModal = () => {
  initValue()
  emit('closeChangePasswordModal')
}

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
  if (userOldPassword.value === '' || userOldPassword.value === null) {
    toastStore.setToastValue({
      severity: 'warn',
      summary: '비밀번호 변경 오류',
      detail: '이전 비밀번호를 입력해주세요.',
      life: 3000
    })
  } else if (userNewPassword.value === confirmUserNewPassword.value) changePasswordAPI()
  else {
    toastStore.setToastValue({
      severity: 'warn',
      summary: '비밀번호 변경 오류',
      detail: '비밀번호가 일치하지 않습니다.',
      life: 3000
    })
  }
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
      toastStore.setToastValue({
        severity: 'success',
        summary: '비밀번호 변경',
        detail: '수정이 완료되었습니다.',
        life: 3000
      })
      closeChangePasswordModal()
    } else {
      toastStore.setToastValue({
        severity: 'error',
        summary: '비밀번호 변경 오류',
        detail: '수정에 실패했습니다.',
        life: 3000
      })
    }
  } catch (error) {
    console.error('API 호출 오류:', error)
    toastStore.setToastValue({
      severity: 'error',
      summary: '비밀번호 변경 오류',
      detail: '수정에 실패했습니다.',
      life: 3000
    })
  }
}
</script>

<style lang="scss" scoped></style>

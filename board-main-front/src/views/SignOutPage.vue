<template>
  <div class="bg-cover bg-center bg-fixed">
    <div class="h-screen flex justify-center items-center">
      <div class="w-full sm:w-80 flex flex-col gap-6 shadow-lg w-full p-16 md:w-1/3 lg:w-1/2">
        <img src="@/assets/image/board.png" class="mb-10" />

        <IconField>
          <InputIcon>
            <i class="pi pi-lock" />
          </InputIcon>
          <InputText
            v-model="userPassword"
            :type="isVisiblePassword ? 'text' : 'password'"
            placeholder="Password"
            fluid
          />
          <InputIcon>
            <i
              :class="isVisiblePassword ? 'pi pi-eye' : 'pi pi-eye-slash'"
              @click="changeIsVisiblePassword"
            />
          </InputIcon>
        </IconField>

        <Card>
          <template #content>
            회원 탈퇴를 하시려면 비밀번호를 입력 후 회원탈퇴 버튼을 눌러주세요.
          </template>
        </Card>

        <ConfirmDialog></ConfirmDialog>
        <Button @click="signOutFunction" label="회원탈퇴" severity="danger" outlined></Button>
      </div>
    </div>
  </div>
</template>

<script setup>
import Card from 'primevue/card'
import IconField from 'primevue/iconfield'
import InputIcon from 'primevue/inputicon'
import InputText from 'primevue/inputtext'
import Button from 'primevue/button'
import ApiService from '@/services/ApiService'
import ConfirmDialog from 'primevue/confirmdialog'
import { useConfirm } from 'primevue/useconfirm'
import { encryptStringSalt, decryptStringSalt } from '@/utils/common'
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { useUserStore } from '@/stores/userStore'

const userPassword = ref('')

const userStore = useUserStore()

const isVisiblePassword = ref(false)

let result = null
const router = useRouter()

const confirm = useConfirm()

const changeIsVisiblePassword = () => {
  isVisiblePassword.value = !isVisiblePassword.value
}

const signOutFunction = () => {
  if (userPassword.value === '' || userPassword.value === null) alert('비밀번호를 입력해주세요.')
  else confirmSignOut()
}

const moveLoginPageFunction = () => {
  userStore.setUserLogout()
  router.push({ name: 'LoginPage' }).catch(() => {
    console.log('loginPageError')
  })
}

const cancelFunction = () => {
  alert('회원탈퇴를 취소하셨습니다. 메인페이지로 이동합니다.')
  router.push({ name: 'MainPage' }).catch(() => {
    console.log('mainPageError')
  })
}

const signOutApi = async () => {
  const decryptGuid = decryptStringSalt(userStore.getUserAccess.ugd)
  result = await ApiService.requestAPI({
    headers: { accept: 'application/json' },
    method: 'DELETE',
    url: `/member/delete/${decryptGuid}`,
    data: {
      password: encryptStringSalt(userPassword.value)
    }
  })
  if (result === 'success') {
    alert('회원탈퇴가 완료되었습니다.')
    moveLoginPageFunction()
  } else {
    alert('비밀번호가 일치하지 않습니다.')
  }
}

const confirmSignOut = () => {
  confirm.require({
    message: '회원 탈퇴를 하시겠습니까? 탈퇴 후에는 복구되지 않습니다.',
    header: '회원 탈퇴',
    icon: 'pi pi-info-circle',
    rejectLabel: '취소',
    rejectProps: {
      label: 'Cancel',
      severity: 'secondary',
      outlined: true
    },
    acceptProps: {
      label: '탈퇴',
      severity: 'danger'
    },
    accept: () => {
      signOutApi()
    },
    reject: () => {
      cancelFunction()
    }
  })
}
</script>

<style lang="scss" scoped></style>

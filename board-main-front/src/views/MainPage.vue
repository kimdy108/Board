<template>
  <div class="">
    <Menubar :model="items">
      <template #start>
        <img src="@/assets/image/board.png" class="w-24" @click="moveMainPage" />
      </template>
      <template #end>
        <SplitButton label="마이페이지" :model="userItems" />
      </template>
    </Menubar>
  </div>
  <div>
    <router-view></router-view>
  </div>
</template>

<script setup>
import Menubar from 'primevue/menubar'
import SplitButton from 'primevue/splitbutton'
import { useUserStore } from '@/stores/userStore'
import { useRouter } from 'vue-router'
import { ref } from 'vue'

const useStore = useUserStore()
const router = useRouter()

const moveMainPage = () => {
  console.log('moveMainPage')
  router.push({ name: 'MainPage' }).catch(() => {
    console.log(MainPageError)
  })
}

const logOutFunction = () => {
  useStore.setUserLogout()
  router.push({ name: 'LoginPage' }).catch(() => {
    console.log('loginPageError')
  })
}

const signOutFunction = () => {
  useStore.setUserLogout()
  router.push({ name: 'SignOutPage' }).catch(() => {
    console.log('SignOutPageError')
  })
}

const items = ref([
  {
    label: '공지사항'
  },
  {
    label: '게시판',
    items: [
      {
        label: '개발 / 스택'
      },
      {
        label: '자유게시판'
      }
    ]
  },
  {
    label: '문의'
  }
])

const userItems = ref([
  {
    label: '로그아웃',
    command: () => {
      logOutFunction()
    }
  },
  {
    label: '회원탈퇴',
    command: () => {
      signOutFunction()
    }
  }
])
</script>

<style lang="scss" scoped></style>

<template>
  <div class="mt-2 ml-2 mr-2 mb-3">
    <Menubar :model="items" style="height: 50px; background-color: #8adafd; border-color: #8adafd">
      <template #start>
        <img src="@/assets/image/board.png" class="w-24" @click="moveMainPage" />
      </template>
      <template #end>
        <SplitButton
          label="마이페이지"
          :model="userItems"
          style="color: #4b5565"
          @click="moveMyPage"
        />
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
import { onMounted, ref } from 'vue'

const useStore = useUserStore()
const router = useRouter()

const moveMainPage = () => {
  router.push({ name: 'MainPage' }).catch(() => {
    console.log('MainPageError')
  })
}

const moveNoticePage = () => {
  router.push({ name: 'NoticePage' }).catch(() => {
    console.log('NoticePageError')
  })
}

const moveDevelopmentAndStackPage = () => {
  router.push({ name: 'DevelopmentAndStackPage' }).catch(() => {
    console.log('DevelopmentAndStackPageError')
  })
}

const moveOtherBoardPage = () => {
  router.push({ name: 'OtherBoardPage' }).catch(() => {
    console.log('OtherBoardPageError')
  })
}

const moveQnAPage = () => {
  router.push({ name: 'QnAPage' }).catch(() => {
    console.log('QnAPageError')
  })
}

const moveMyPage = () => {
  useStore.setUserLogout()
  router.push({ name: 'MyPage' }).catch(() => {
    console.log('MyPageError')
  })
}

const logOutFunction = () => {
  useStore.setUserLogout()
  router.push({ name: 'LoginPage' }).catch(() => {
    console.log('loginPageError')
  })
}

const signOutFunction = () => {
  router.push({ name: 'SignOutPage' }).catch(() => {
    console.log('SignOutPageError')
  })
}

const items = ref([
  {
    label: '공지사항',
    command: () => {
      moveNoticePage()
    }
  },
  {
    label: '게시판',
    items: [
      {
        label: '개발 / 스택',
        command: () => {
          moveDevelopmentAndStackPage()
        }
      },
      {
        label: '자유게시판',
        command: () => {
          moveOtherBoardPage()
        }
      }
    ]
  },
  {
    label: '문의',
    command: () => {
      moveQnAPage()
    }
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

onMounted(() => {
  if (!useStore.getUserAccess.at) {
    alert('로그인을 해주세요.')
    router.push({ name: 'LoginPage' }).catch(() => {
      console.log('mainerror')
    })
  }
})
</script>

<style lang="scss" scoped></style>

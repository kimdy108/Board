<template>
  <div class="m-2">
    <Menubar :model="items" class="h-16">
      <template #start>
        <img src="@/assets/images/logo.png" class="w-24" />
      </template>
      <template #end>
        <SplitButton :model="userItems" class="h-10">
          {{ userName }}
        </SplitButton>
      </template>
    </Menubar>
  </div>
</template>

<script setup lang="ts">
import Menubar from 'primevue/menubar'
import SplitButton from 'primevue/splitbutton'
import { useUserStore } from '@/stores/userStore'
import { useRouter } from 'vue-router'
import { onMounted, ref, computed } from 'vue'
import { decryptStringSalt } from '@/utils/common'
import { userRoleList } from '@/references/config'

const userStore = useUserStore()
const router = useRouter()
const userName = ref('')
const isOverAdmin = ref(false)

onMounted(() => {
  userName.value = decryptStringSalt(userStore.getCurrentUser.unm)
  isOverAdmin.value = userRoleList.findIndex(x => x.value == decryptStringSalt(userStore.getUserRole)) < 3
})

const movePage = (pageName: string) => {
  router.push({ name: pageName }).catch(() => {
    console.log(pageName,'Error')
  })
}

const items = computed(() => {
  const baseItems = [
    {
      label: '공지사항',
      command: () => {
        movePage('AnnounceAdmin')
      }
    },
    {
      label: '게시판',
      items: [
        {
          label: '개발',
          command: () => {
            movePage('DevelopmentAdmin')
          }
        },
        {
          label: '스택',
          command: () => {
            movePage('StackAdmin')
          }
        },
        {
          label: '자유게시판',
          command: () => {
            movePage('FreeAdmin')
          }
        }
      ]
    },
    {
      label: 'QnA',
      command: () => {
        movePage('QnAAdmin')
      }
    }
  ]

  if (isOverAdmin.value) {
    baseItems.push({
      label: '사용자관리',
      command: () => {
        movePage('UserMain')
      }
    })
  }

  return baseItems
})

const userItems = ref([
  {
    label: '설정',
    command: () => {
      movePage('AdminConfig')
    }
  },
  {
    label: '로그아웃',
    command: () => {
      userStore.setUserLogout()
      movePage('IndexPage')
    }
  }
])
</script>

<style lang="sass" scoped>

</style>
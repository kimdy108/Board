<template>
  <div>
    <div class="text-left mb-5">
      <div class="text-2xl font-bold text-gray-900">사용자 관리</div>
    </div>
    
    <div class="bg-white rounded-lg flex justify-between items-center px-8 py-4 mb-5 border border-gray-200">
      <div class="text-left flex justify-start items-center">
        <Select v-model="searchCategory" :options="searchCategoryList" optionLabel="name" optionValue="key" placeholder="검색조건" class="w-56 !bg-white border !border-gray-300 !text-gray-700 !rounded-lg !placeholder-gray-300 !focus:outline-none"></Select>
        <BoardSearch v-model:inputValue="searchValue" :isDisabled="false" @searchSubmit="searchSubmit" />
        <button class="min-w-fit flex justify-center items-center bg-emerald-500 text-white hover:bg-emerald-600 px-5 py-2 rounded-lg" @click="searchSubmit">
          <span class="pi pi-search mr-3"></span>검색
        </button>
      </div>
    </div>
    
    <div>
      <BoardTable
        :contents="contents"
        :columnHeader="columnHeader"
        :totalCount="totalCount"
        :isUpdate="true"
        :isDelete="true"
        keyName="userUUID"
        @showInfo="showInfoModal"
      />
    </div>

    <UserInfoModal :showModal="isShowInfoModal" :infoUUID="userInfoUUID" @closeUserInfoModal="closeUserInfoModal"></UserInfoModal>
  </div>
</template>

<script setup lang="ts">
import { onMounted, ref, watch } from 'vue'
import { useRouter } from 'vue-router'
import Select from 'primevue/select'
import BoardTable from '@/components/element/BoardTable.vue'
import BoardSearch from '@/components/element/BoardSearch.vue'
import ApiService from '@/services/ApiService'
import responseData from '@/interfaces/common/responseData'

import UserInfoModal from './UserInfoModal.vue'

const props = defineProps({
  selectedTab: Number
})

onMounted(() => {
  getUserList()

  let el: HTMLElement = document.getElementById('board-main') as HTMLElement
  el.scrollTo({ top: 0 })
})

const router = useRouter()

const searchValue = ref('')
const searchCategory = ref('userID')
const searchCategoryList = ref([
  { key: 'userID', name: '아이디' },
  { key: 'userName', name: '이름' },
  { key: 'userApproval', name: '승인여부' },
])

const totalCount = ref(0)
const contents = ref([])

const isShowInfoModal = ref(false)
const userInfoUUID = ref('')

const columnHeader = ref([
  { seq: 1, field: 'userID', header: '아이디', style: 'padding-left: 7rem; width: 20%' },
  { seq: 2, field: 'userName', header: '이름', style: 'width: 20%' },
  { seq: 3, field: 'userNickName', header: '닉네임', style: 'width: 20%' },
  { seq: 4, field: 'userApproval', header: '승인 상태', style: 'width: 20%' },
  { seq: 5, field: 'insertDate', header: '등록일', style: 'width: 20%' }
])

const searchSubmit = () => {
  getUserList()
}

const showInfoModal = (uuid: string) => {
  userInfoUUID.value = uuid
  isShowInfoModal.value = true
}

const closeUserInfoModal = () => {
  userInfoUUID.value = ''
  isShowInfoModal.value = false

  getUserList()
}

const makeUserApproval = (type: string) => {
  switch(type) {
    case 'APPROVE':
      return '승인'
    case 'REJECT':
      return '차단'
    case 'WAIT':
      return '대기'
  }
}

const makeSearchByApproval = (value: string) => {
  if (searchCategory.value == 'userApproval') {
    switch(value) {
      case '승인':
        return 'APPROVE'
      case '차단':
        return 'REJECT'
      case '대기':
        return 'WAIT'
      default:
        return value
    }
  }
  else return value
}

const getUserList = async () => {
  const reqHeader = { accept: 'application/json' }
  const reqParams = {
    searchType: searchCategory.value ? searchCategory.value : '',
    searchValue: searchValue.value ? makeSearchByApproval(searchValue.value) : ''
  }
  const userResult: responseData = await ApiService.requestAPI({
    headers: reqHeader,
    method: 'GET',
    url: `/board/user/list/page`,
    params: reqParams
  })
  if (userResult.retStatus) {
    if (userResult.retData.userContents && Array.isArray(userResult.retData.userContents)) {
      for (let i = 0; i < userResult.retData.userContents.length; i++) {
        if (userResult.retData.userContents[i] && userResult.retData.userContents[i].insertDate) {
          userResult.retData.userContents[i].userApproval = makeUserApproval(userResult.retData.userContents[i].userApproval)
          userResult.retData.userContents[i].insertDate = userResult.retData.userContents[i].insertDate.replace("T", " ")
        }
      }
    }

    contents.value = userResult.retData.userContents || []
    totalCount.value = userResult.retData.totalCount || 0
  }
}

watch(() => props.selectedTab, (newVal) => {
  if(newVal == 0) getUserList()
})
</script>
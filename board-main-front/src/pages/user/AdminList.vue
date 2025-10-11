<template>
  <div>
    <div class="text-left mb-5">
      <div class="text-2xl font-bold text-gray-900">관리자 관리</div>
    </div>
    
    <div class="bg-white rounded-lg flex justify-between items-center px-8 py-4 mb-5 border border-gray-200">
      <div class="text-left flex justify-start items-center">
        <Select v-model="searchCategory" :options="searchCategoryList" optionLabel="name" optionValue="key" placeholder="검색조건" class="w-56 !bg-white border !border-gray-300 !text-gray-700 !rounded-lg !placeholder-gray-300 !focus:outline-none"></Select>
        <BoardSearch v-model:inputValue="searchValue" :isDisabled="false" @searchSubmit="searchSubmit" />
        <button class="min-w-fit flex justify-center items-center bg-emerald-500 text-white hover:bg-emerald-600 px-5 py-2 rounded-lg" @click="searchSubmit">
          <span class="pi pi-search mr-3"></span>검색
        </button>
      </div>
      <div class="flex justify-end items-center">
        <button class="min-w-fit flex justify-center items-center text-white bg-sky-500 hover:bg-sky-600 text-base px-5 py-2 rounded-lg" @click="showRegistModal">
          <i class="pi pi-upload mr-2" />관리자 등록
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
        keyName="adminUUID"
        @showInfo="showInfoModal"
      />
    </div>

    <AdminRegist :showModal="isShowRegistModal" @closeRegistModal="closeRegistModal" />
    <AdminInfo :showModal="isShowInfoModal" :infoUUID="infoUUID" @closeInfoModal="closeInfoModal" />

  </div>
</template>

<script setup lang="ts">
import { onMounted, ref, watch } from 'vue'
import Select from 'primevue/select'
import BoardTable from '@/components/element/BoardTable.vue'
import BoardSearch from '@/components/element/BoardSearch.vue'
import ApiService from '@/services/ApiService'
import responseData from '@/interfaces/common/responseData'

import AdminRegist from './AdminRegist.vue'
import AdminInfo from './AdminInfo.vue'

const props = defineProps({
  selectedTab: Number
})

onMounted(() => {
  getAdminList()

  let el: HTMLElement = document.getElementById('board-main') as HTMLElement
  el.scrollTo({ top: 0 })
})

const searchValue = ref('')

const searchCategory = ref('memberID')
const searchCategoryList = ref([
  { key: 'memberID', name: '아이디' },
  { key: 'memberName', name: '이름' },
  { key: 'memberRole', name: '권한' },
])

const totalCount = ref(0)
const contents = ref([])

const isShowRegistModal = ref(false)
const isShowInfoModal = ref(false)
const infoUUID = ref('')

const columnHeader = ref([
  { seq: 1, field: 'adminID', header: '아이디', style: 'padding-left: 7rem; width: 20%' },
  { seq: 2, field: 'adminName', header: '이름', style: 'width: 20%' },
  { seq: 3, field: 'adminNickName', header: '닉네임', style: 'width: 20%' },
  { seq: 4, field: 'adminRole', header: '권한', style: 'width: 20%' },
  { seq: 5, field: 'insertDate', header: '등록일', style: 'width: 20%' }
])

const searchSubmit = () => {
  getAdminList()
}

const showRegistModal = () => {
  isShowRegistModal.value = true
}

const closeRegistModal = () => {
  isShowRegistModal.value = false
  getAdminList()
}

const showInfoModal = (uuid: string) => {
  infoUUID.value = uuid
  isShowInfoModal.value = true
}

const closeInfoModal = () => {
  infoUUID.value = ''
  isShowInfoModal.value = false

  getAdminList()
}

const makeAdminRole = (type: string) => {
  switch(type) {
    case 'MASTER':
      return '최고관리자'
    case 'ADMIN':
      return '관리자'
    case 'MANAGER':
      return '매니저'
  }
}

const makeSearchByRole = (value: string) => {
  if (searchCategory.value == 'memberApproval') {
    switch(value) {
      case '최고관리자':
        return 'MASTER'
      case '관리자':
        return 'ADMIN'
      case '매니저':
        return 'MANAGER'
      default:
        return value
    }
  }
  else return value
}

const getAdminList = async () => {
  const reqHeader = { accept: 'application/json' }
  const reqParams = {
    searchType: searchCategory.value ? searchCategory.value : '',
    searchValue: searchValue.value ? makeSearchByRole(searchValue.value) : ''
  }
  const adminResult: responseData = await ApiService.requestAPI({
    headers: reqHeader,
    method: 'GET',
    url: `/board/admin/list/page`,
    params: reqParams
  })
  if (adminResult.retStatus) {
    if (adminResult.retData.adminContents && Array.isArray(adminResult.retData.adminContents)) {
      for (let i = 0; i < adminResult.retData.adminContents.length; i++) {
        if (adminResult.retData.adminContents[i] && adminResult.retData.adminContents[i].insertDate) {
          adminResult.retData.adminContents[i].adminRole = makeAdminRole(adminResult.retData.adminContents[i].adminRole)
          adminResult.retData.adminContents[i].insertDate = adminResult.retData.adminContents[i].insertDate.replace("T", " ")
        }
      }
    }

    contents.value = adminResult.retData.adminContents || []
    totalCount.value = adminResult.retData.totalCount || 0
  }
}

watch(() => props.selectedTab, (newVal) => {
  if(newVal == 1) getAdminList()
})
</script>
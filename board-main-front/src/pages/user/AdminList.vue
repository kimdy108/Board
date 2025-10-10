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
        keyName="userUUID"
        @showInfo="showInfoModal"
      />
    </div>
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
  { key: 'userRole', name: '권한' },
])

const totalCount = ref(0)
const contents = ref([])

const columnHeader = ref([
  { seq: 1, field: 'userID', header: '아이디', style: 'padding-left: 7rem; width: 20%' },
  { seq: 2, field: 'userName', header: '이름', style: 'width: 20%' },
  { seq: 3, field: 'userNickName', header: '닉네임', style: 'width: 20%' },
  { seq: 4, field: 'userRole', header: '권한', style: 'width: 20%' },
  { seq: 5, field: 'insertDate', header: '등록일', style: 'width: 20%' }
])

const searchSubmit = () => {
  getUserList()
}

const getUserList = () => {
}

const showInfoModal = () => {
}

watch(() => props.selectedTab, (newVal) => {
  if(newVal == 1) getUserList()
})
</script>
<template>
  <div>
    <div class="text-left mb-5">
      <div class="text-2xl font-bold text-gray-900">공지사항</div>
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
        <button class="min-w-fit flex justify-center items-center text-white bg-sky-500 hover:bg-sky-600 text-base px-5 py-2 rounded-lg" @click="moveRegistPage">
          <i class="pi pi-upload mr-2" />공지사항 등록
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
        keyName="announceUUID"
        @showInfo="moveInfoPage"
      />
    </div>
  </div>
</template>

<script setup lang="ts">
import { onMounted, ref } from 'vue'
import { useRouter } from 'vue-router'
import Select from 'primevue/select'
import BoardTable from '@/components/element/BoardTable.vue'
import BoardSearch from '@/components/element/BoardSearch.vue'
import ApiService from '@/services/ApiService'
import responseData from '@/interfaces/common/responseData'

const router = useRouter()

onMounted(() => {
  getAnnounceList()

  let el: HTMLElement = document.getElementById('board-main') as HTMLElement
  el.scrollTo({ top: 0 })
})

const searchValue = ref('')

const searchCategory = ref('announceTitle')
const searchCategoryList = ref([
  { key: 'announceTitle', name: '제목' },
  { key: 'announceContent', name: '내용' }
])

const totalCount = ref(0)

const contents = ref([])

const columnHeader = ref([
  { seq: 1, field: 'announceTitle', header: '제목', style: 'padding-left: 7rem; width: 40%' },
  { seq: 2, field: 'authorName', header: '작성자', style: 'width: 10%' },
  { seq: 3, field: 'viewCount', header: '조회수', style: 'width: 10%' },
  { seq: 4, field: 'insertDate', header: '등록일', style: 'width: 20%' }
])

const searchSubmit = () => {
  getAnnounceList()
}

const moveRegistPage = () => {
  router.push({ name: 'AnnounceRegist' }).catch(() => {
    console.log('AnnounceRegist Error')
  })
}

const moveInfoPage = (uuid: string) => {
  router.push({ name: 'AnnounceInfo', state: {uuid: uuid} }).catch(() => {
    console.log('AnnounceInfo Error')
  })
}

const getAnnounceList = async () => {
  const reqHeader = { accept: 'application/json' }
  const reqParams = {
    searchType: searchCategory.value ? searchCategory.value : '',
    searchValue: searchValue.value ? searchValue.value : '',
  }
  const announceResult: responseData = await ApiService.requestAPI({
    headers: reqHeader,
    method: 'GET',
    url: `/board/announce/list/page`,
    params: reqParams
  })
  if (announceResult.retStatus) {
    for (let i = 0; i < announceResult.retData.totalCount; i++) {
      announceResult.retData.announceContents[i].authorName = '관리자'
      announceResult.retData.announceContents[i].insertDate = announceResult.retData.announceContents[i].insertDate.replace("T", " ")
    }

    contents.value = announceResult.retData.announceContents
    totalCount.value = announceResult.retData.totalCount
  }
}
</script>
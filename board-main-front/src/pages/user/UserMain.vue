<template>
  <div>
    <div class="card" v-if="isOverAdmin()">
      <Tabs value="0">
        <TabList class="shadow-sm">
          <Tab value="0" @click="selectTab(0)">사용자</Tab>
          <Tab value="1" @click="selectTab(1)">관리자</Tab>
        </TabList>
        <TabPanels>
          <TabPanel value="0">
            <div class="pt-3">
              <UserList :selectedTab="selectedTab"></UserList>
            </div>
          </TabPanel>

          <TabPanel value="1">
            <div class="pt-3">
              <AdminList :selectedTab="selectedTab"></AdminList>
            </div>
          </TabPanel>
        </TabPanels>
      </Tabs>
    </div>

    <UserList v-else></UserList>

  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'

import Tabs from 'primevue/tabs'
import TabList from 'primevue/tablist'
import Tab from 'primevue/tab'
import TabPanels from 'primevue/tabpanels'
import TabPanel from 'primevue/tabpanel'

import UserList from './UserList.vue'
import AdminList from './AdminList.vue'

import { decryptStringSalt } from '@/utils/common'
import { useUserStore } from '@/stores/userStore'
import { userRoleList } from '@/references/config'

const userStore = useUserStore()

const selectedTab = ref(0)

const isOverAdmin = () => {
  return userRoleList.findIndex(x => x.value == decryptStringSalt(userStore.getUserRole)) < 2
}

const selectTab = (selectedNum: number) => {
  if(selectedTab.value!=selectedNum) {
    selectedTab.value = selectedNum
  }
}
</script>

<style lang="css" scoped>
.p-tabpanels {
  background-color: transparent !important;
}
</style>
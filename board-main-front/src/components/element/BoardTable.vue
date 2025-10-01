<template>
  <div>
    <div class="flex justify-between items-center mb-6">
      <div>
        <span class="text-base font-bold text-gray-700 mr-2">전체 : </span> 
        <span class="text-base text-gray-700">{{ totalCount }}</span>
      </div>
    </div>

    <div class="card mb-10 border border-gray-200 rounded-lg">
      <DataTable :value="contents" paginator :rows="15" :rowsPerPageOptions="[15, 20, 25, 30]" @row-click="showInfo">
        <Column v-for="column in columnHeader" class="cursor-pointer" :field="column.field" :header="column.header" :style="column.style" :key="column.key"></Column>
      </DataTable>
    </div>
  </div>
</template>

<script setup lang="ts">
import DataTable from 'primevue/datatable';
import Column from 'primevue/column';
import Row from 'primevue/row';
import Button from 'primevue/button';
import type boardTableProps from '@/interfaces/element/boardTableProps'

const props = defineProps<boardTableProps>()
const emit = defineEmits<{
  showInfo: [uuid: string]
}>()

const showInfo = (event: any) => {
  emit('showInfo', event.data[props.keyName])
}
</script>
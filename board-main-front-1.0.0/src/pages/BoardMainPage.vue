<template>
  <div class="mt-2 ml-2 mr-2 mb-3">
    <Card style="background-color: #007cb2">
      <template #title>
        <div class="h-16 text-5xl text-left ml-5">Board</div>
      </template>
      <template #content>
        <div class="card">
          <Splitter style="height: 680px" class="mb-5">
            <SplitterPanel :size="50">
              <Splitter layout="vertical">
                <SplitterPanel
                  class="flex items-center justify-center border-none text-black"
                  style="background-color: #fffff1"
                >
                  공지사항
                </SplitterPanel>
                <SplitterPanel
                  class="flex items-center justify-center border-none text-black"
                  style="background-color: #fffff1"
                >
                  개발/스택
                </SplitterPanel>
              </Splitter>
            </SplitterPanel>
            <SplitterPanel :size="50">
              <Splitter layout="vertical">
                <SplitterPanel
                  class="flex items-center justify-center border-none text-black"
                  style="background-color: #fffff1"
                >
                  자유게시판
                </SplitterPanel>
                <SplitterPanel
                  class="flex items-center justify-center border-none text-black"
                  style="background-color: #fffff1"
                >
                  qna
                </SplitterPanel>
              </Splitter>
            </SplitterPanel>
          </Splitter>
        </div>
      </template>
    </Card>
  </div>
</template>

<script setup>
import Splitter from 'primevue/splitter'
import SplitterPanel from 'primevue/splitterpanel'
import Card from 'primevue/card'
</script>

<style lang="scss" scoped></style>

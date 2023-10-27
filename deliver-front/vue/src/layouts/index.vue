<script lang="ts" setup>
import Header from './components/Header/index.vue'
import SideBar from './components/SideBar/index.vue'
// import Breadcrumb from './components/Breadcrumb/index.vue'
</script>

<template>
  <a-layout style="height: 100%">
    <Header></Header>
    <a-layout>
      <SideBar></SideBar>
      <a-layout style="padding: 0 12px 12px">
        <!-- <Breadcrumb></Breadcrumb> -->
        <a-layout-content :style="{ background: '#fff', padding: '24px' }">
          <RouterView></RouterView>
        </a-layout-content>
      </a-layout>
    </a-layout>
  </a-layout>
</template>
<style scoped lang="scss">
.ant-layout {
  background: #ffffff;
}
</style>

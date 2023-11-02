<script lang="ts" setup>
import Header from './components/Header/index.vue'
import SideBar from './components/SideBar/index.vue'
import Breadcrumb from './components/Breadcrumb/index.vue'
import Banner from '../views/Banner/index.vue'
</script>

<template>
  <a-layout style="min-height: 100%">
    <Banner></Banner>
    <Header></Header>
    <a-layout>
      <SideBar></SideBar>
      <a-layout style="padding: 0 24px 12px; background: #f8f8f8">
        <Breadcrumb></Breadcrumb>
        <a-layout-content>
          <RouterView></RouterView>
        </a-layout-content>
      </a-layout>
    </a-layout>
  </a-layout>
</template>
<style scoped lang="scss"></style>

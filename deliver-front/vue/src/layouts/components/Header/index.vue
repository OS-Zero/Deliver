<script lang="ts" setup>
import { ref } from 'vue'
import { ExclamationCircleOutlined, QuestionCircleOutlined, GithubOutlined, UserOutlined } from '@ant-design/icons-vue'
const showAbout = ref(false)
const setShowAbout = (open: boolean): void => {
  showAbout.value = open
}
</script>
<template>
  <a-layout-header class="header">
    <div class="left">
      <RouterLink to="/"><img src="vite.svg" alt="空" /></RouterLink>
      <h1>Deliver 企业消息推送平台</h1>
    </div>
    <div class="right">
      <RouterLink to="" style="color: #6c6c6c" @click="showAbout = true"><ExclamationCircleOutlined /></RouterLink>
      <a-modal
        v-model:open="showAbout"
        title="关于"
        centered
        @ok="setShowAbout(false)"
        :ok-button-props="{ disabled: true }"
        :cancel-button-props="{ disabled: true }"
      >
        <div style="display: flex; margin-top: 20px">
          <div style="display: flex">
            <img src="vite.svg" style="width: 60px; height: 60px" alt="" />
            <h1 style="display: inline-block; height: 60px; line-height: 60px">Deliver</h1>
          </div>
          <div style="margin-left: 60px">
            <p>产品：Deliver 企业消息推送平台</p>
            <p>版本：1.0.0</p>
            <p>http://localhost:8080</p>
          </div>
        </div>
      </a-modal>
      <RouterLink to="" style="color: #6c6c6c"><QuestionCircleOutlined /></RouterLink>
      <RouterLink to="" style="color: #6c6c6c"><GithubOutlined /></RouterLink>
      <span class="avatar">
        <a-avatar style="width: 35px; height: 35px">
          <template #icon><UserOutlined /></template>
        </a-avatar>
        <span class="name">admin</span>
      </span>
    </div>
  </a-layout-header>
</template>
<style lang="scss" scoped>
.header {
  display: flex;
  justify-content: space-between;
  height: 60px;
  background-color: #ffffff;
  .left {
    height: 100%;
    h1 {
      height: 60px;
      line-height: 60px;
      display: inline-block;
    }
  }
  .right {
    font-size: 20px;
    color: #6c6c6c;
    .router-link-active {
      margin-right: 10px;
    }
    .avatar {
      display: inline-block;
      height: 100%;
      border-left: 1px solid #c0c0c0;
      padding-left: 10px;
      .name {
        margin-left: 10px;
        font-size: 16px;
      }
    }
  }
}
</style>

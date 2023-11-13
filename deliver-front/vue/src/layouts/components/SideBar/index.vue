<script lang="ts" setup>
import {
  MessageOutlined,
  FunnelPlotOutlined,
  FileTextOutlined,
  SettingOutlined,
  DashboardOutlined,
  LeftOutlined,
  RightOutlined,
  SmileOutlined
} from '@ant-design/icons-vue'
import { ref } from 'vue'

import ItemLink from '../ItemLink/index.vue'
const selectedKeys2 = ref<string[]>([])
const openKeys = ref<string[]>([])
const collapsed = ref(false)
const toggleCollapsed = (): void => {
  collapsed.value = !collapsed.value
}
</script>

<template>
  <a-layout-sider width="190" style="background: #fff; position: relative" :collapsed="collapsed">
    <a-button
      shape="circle"
      size="small"
      style="margin-bottom: 16px; position: absolute; right: -12px; top: 20px"
      @click="toggleCollapsed"
    >
      <RightOutlined v-if="collapsed" />
      <LeftOutlined v-else />
    </a-button>
    <a-menu v-model:selectedKeys="selectedKeys2" v-model:openKeys="openKeys" mode="inline">
      <a-sub-menu key="sub6">
        <template #title>
          <span>
            <SmileOutlined />
            <span>欢迎</span>
          </span>
        </template>
        <ItemLink itemKey="6" info="欢迎" to="welcome" />
      </a-sub-menu>
      <a-sub-menu key="sub1">
        <template #title>
          <span>
            <DashboardOutlined />
            <span>系统监控看板</span>
          </span>
        </template>
        <ItemLink itemKey="1" info="平台数据看板" to="dashboard" />
      </a-sub-menu>
      <a-sub-menu key="sub2">
        <template #title>
          <span>
            <MessageOutlined />
            <span>消息模板配置</span>
          </span>
        </template>
        <ItemLink itemKey="2" info="消息模板" to="message" />
      </a-sub-menu>
      <a-sub-menu key="sub3">
        <template #title>
          <span>
            <FunnelPlotOutlined />
            <span>渠道 APP 配置</span>
          </span>
        </template>
        <ItemLink itemKey="3" info="APP 配置" to="channel" />
      </a-sub-menu>
      <a-sub-menu key="sub4">
        <template #title>
          <span>
            <FileTextOutlined />
            <span>平台文件管理</span>
          </span>
        </template>
        <ItemLink itemKey="4" info="文件管理" to="file" />
      </a-sub-menu>
      <a-sub-menu key="sub5">
        <template #title>
          <span>
            <SettingOutlined />
            <span>系统设置</span>
          </span>
        </template>
      </a-sub-menu>
    </a-menu>
  </a-layout-sider>
</template>
<style lang="scss" scoped></style>

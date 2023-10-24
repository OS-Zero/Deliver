<script lang="ts" setup>
import { ref } from 'vue'
import {
  DashboardOutlined,
  MessageOutlined,
  FunnelPlotOutlined,
  FileTextOutlined,
  SettingOutlined
} from '@ant-design/icons-vue'
import ItemLink from '../ItemLink/index.vue'
const selectedKeys2 = ref<string[]>(['1'])
const openKeys = ref<string[]>(['sub1'])
</script>

<template>
  <a-layout-sider width="200" style="background: #fff; overflow: auto">
    <a-menu
      v-model:selectedKeys="selectedKeys2"
      v-model:openKeys="openKeys"
      mode="inline"
      :style="{ height: '100%', borderRight: 0 }"
    >
      <a-sub-menu key="sub1">
        <template #title>
          <span>
            <DashboardOutlined />
            系统监控看板
          </span>
        </template>
        <ItemLink itemKey="1" info="平台数据看板" to="dashboard" />
      </a-sub-menu>
      <a-sub-menu key="sub2">
        <template #title>
          <span>
            <MessageOutlined />
            消息模板配置
          </span>
        </template>
        <ItemLink itemKey="2" info="消息模板" to="message" />
      </a-sub-menu>
      <a-sub-menu key="sub3">
        <template #title>
          <span>
            <FunnelPlotOutlined />
            渠道APP配置
          </span>
        </template>
        <ItemLink itemKey="3" info="APP配置" to="channel" />
      </a-sub-menu>
      <a-sub-menu key="sub4">
        <template #title>
          <span>
            <FileTextOutlined />
            后台操作日志
          </span>
        </template>
        <ItemLink itemKey="4" info="操作日志" to="log" />
      </a-sub-menu>
      <a-sub-menu key="sub4">
        <template #title>
          <span>
            <SettingOutlined />
            系统日志
          </span>
        </template>
      </a-sub-menu>
    </a-menu>
  </a-layout-sider>
</template>
<style lang="scss" scoped></style>

<script lang="ts" setup>
import { reactive, type UnwrapRef } from 'vue'
import type { TableColumnType } from 'ant-design-vue'
interface FormState {
  appName: string
  channelType: string
  status: string
}
interface TableDataType {
  key: string
  name: string
  age: number
  address: string
}
const formState: UnwrapRef<FormState> = reactive({
  appName: '',
  channelType: '',
  status: ''
})
// const wrapperCol = { span: 10 }
const wrapperCol = { span: 14 }
const columns: Array<TableColumnType<TableDataType>> = [
  {
    title: 'appId',
    dataIndex: 'name'
  },
  {
    title: '应用名称',
    dataIndex: 'age'
  },
  {
    title: 'channelType',
    dataIndex: 'address'
  },
  {
    title: '累计使用数',
    dataIndex: 'address'
  },
  {
    title: '状态',
    dataIndex: 'status'
  },
  {
    title: '创建者',
    dataIndex: 'address'
  }
]
const data: TableDataType[] = [
  {
    key: '1',
    name: 'John Brown',
    age: 32,
    address: 'New York No. 1 Lake Park'
  }
]
</script>
<template>
  <div id="channel-container">
    <div class="channel-header">
      <a-form :model="formState" layout="inline" :wrapper-col="wrapperCol">
        <a-form-item label="App 名" style="width: 180px">
          <a-input v-model:value="formState.appName" />
        </a-form-item>
        <a-form-item label="渠道类型">
          <a-select ref="select" v-model:value="formState.channelType" style="width: 120px">
            <a-select-option :value="1">电话</a-select-option>
            <a-select-option :value="2">短信</a-select-option>
            <a-select-option :value="3">邮件</a-select-option>
            <a-select-option :value="4">钉钉</a-select-option>
            <a-select-option :value="5">企微</a-select-option>
            <a-select-option :value="6">飞书</a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="状态">
          <a-select ref="select" v-model:value="formState.status" style="width: 120px">
            <a-select-option :value="0">禁用</a-select-option>
            <a-select-option :value="1">启用</a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item>
          <a-button type="primary" html-type="submit" @click="searchMessage">查询</a-button>
          <a-button>清空</a-button>
        </a-form-item>
      </a-form>
    </div>
    <div class="channel-table">
      <a-table :columns="columns" :data-source="data" @change="onChange" />
      <div id="components-pagination-demo-mini">
        <a-pagination size="small" :total="50" show-size-changer show-quick-jumper />
      </div>
    </div>
  </div>
</template>
<style lang="scss" scoped>
#components-pagination-demo-mini .ant-pagination:not(:last-child) {
  margin-bottom: 24px;
}
</style>

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
    title: 'Name',
    dataIndex: 'name',
    filters: [
      {
        text: 'Joe',
        value: 'Joe'
      },
      {
        text: 'Jim',
        value: 'Jim'
      },
      {
        text: 'Submenu',
        value: 'Submenu',
        children: [
          {
            text: 'Green',
            value: 'Green'
          },
          {
            text: 'Black',
            value: 'Black'
          }
        ]
      }
    ],
    // specify the condition of filtering result
    // here is that finding the name started with `value`
    sorter: (a: TableDataType, b: TableDataType) => a.name.length - b.name.length,
    sortDirections: ['descend']
  },
  {
    title: 'Age',
    dataIndex: 'age',
    defaultSortOrder: 'descend',
    sorter: (a: TableDataType, b: TableDataType) => a.age - b.age
  },
  {
    title: 'Address',
    dataIndex: 'address',
    filters: [
      {
        text: 'London',
        value: 'London'
      },
      {
        text: 'New York',
        value: 'New York'
      }
    ],
    filterMultiple: false,
    sorter: (a: TableDataType, b: TableDataType) => a.address.length - b.address.length,
    sortDirections: ['descend', 'ascend']
  }
]
const data: TableDataType[] = [
  {
    key: '1',
    name: 'John Brown',
    age: 32,
    address: 'New York No. 1 Lake Park'
  },
  {
    key: '2',
    name: 'Jim Green',
    age: 42,
    address: 'London No. 1 Lake Park'
  },
  {
    key: '3',
    name: 'Joe Black',
    age: 32,
    address: 'Sidney No. 1 Lake Park'
  },
  {
    key: '4',
    name: 'Jim Red',
    age: 32,
    address: 'London No. 2 Lake Park'
  }
]
</script>
<template>
  <div id="channel-container">
    <div class="channel-header">
      <a-form :model="formState" layout="inline" :wrapper-col="wrapperCol">
        <a-form-item label="App名">
          <a-input v-model:value="formState.appName" />
        </a-form-item>
        <a-form-item label="渠道类型">
          <a-select ref="select" v-model:value="formState.channelType" style="width: 120px">
            <a-select-option value="jack">Jack</a-select-option>
            <a-select-option value="lucy">Lucy</a-select-option>
            <a-select-option value="disabled" disabled>Disabled</a-select-option>
            <a-select-option value="Yiminghe">yiminghe</a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="状态">
          <a-select ref="select" v-model:value="formState.status" style="width: 120px">
            <a-select-option value="jack">Jack</a-select-option>
            <a-select-option value="lucy">Lucy</a-select-option>
            <a-select-option value="disabled" disabled>Disabled</a-select-option>
            <a-select-option value="Yiminghe">yiminghe</a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item style="margin-left: 150px">
          <a-button>查询</a-button>
          <a-button type="primary" style="margin-left: 10px">新增</a-button>
        </a-form-item>
      </a-form>
    </div>
    <div class="channel-table">
      <a-table :columns="columns" :data-source="data" @change="onChange" />
    </div>
  </div>
</template>
<style lang="scss" scoped></style>

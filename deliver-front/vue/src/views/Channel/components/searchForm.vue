<script setup lang="ts">
import { ref, reactive } from 'vue'
import { DownOutlined, UpOutlined } from '@ant-design/icons-vue'
import type { appSearch } from '../type'
import type { FormInstance } from 'ant-design-vue'

const expand = ref(false)

const formRef = ref<FormInstance>()

const searchForm = reactive<appSearch>({
  appName: undefined,
  channelType: undefined,
  currentPage: 1,
  pageSize: 10,
  startTime: undefined,
  endTime: undefined,
  perid: undefined
})

const onFinish = (): void => {
  console.log('searchForm: ', searchForm)
}

// 等接口出来在该
const searchMessage = (): void => {
  console.log('查询成功')
}
</script>

<template>
  <a-form ref="formRef" name="advanced_search" class="search" :model="searchForm" @finish="onFinish">
    <a-row :gutter="24">
      <a-col :span="8">
        <a-form-item name="appName" label="APP 名">
          <a-input v-model:value="searchForm.appName" placeholder="请输入 APP 名"></a-input>
        </a-form-item>
      </a-col>
      <a-col :span="8">
        <a-form-item name="channelType" label="渠道类型">
          <a-select v-model:value="searchForm.channelType" placeholder="请选择渠道类型">
            <a-select-option value="1">电话</a-select-option>
            <a-select-option value="2">短信</a-select-option>
            <a-select-option value="3">邮件</a-select-option>
            <a-select-option value="4">钉钉</a-select-option>
            <a-select-option value="5">企微</a-select-option>
            <a-select-option value="6">飞书</a-select-option>
          </a-select>
        </a-form-item>
      </a-col>
      <a-col :span="16" v-if="expand">
        <a-form-item name="perid" label="创建时间">
          <a-range-picker
            :locale="locale"
            v-model:value="searchForm.perid"
            @change="onRangeChange"
            format="YYYY-MM-DD"
            :placeholder="['起始日期', '结束日期']"
          />
        </a-form-item>
      </a-col>
      <a-col
        :span="8"
        :style="{
          'text-align': expand === true ? 'right' : 'center',
          'margin-bottom': expand === true ? '24px' : '0'
        }"
      >
        <a-button type="primary" html-type="submit" @click="searchMessage">查询</a-button>
        <a-button style="margin: 0 8px" @click="() => (formRef as FormInstance).resetFields()">清空</a-button>
        <a style="font-size: 14px" @click="expand = !expand">
          <template v-if="expand">
            <UpOutlined />
            收起
          </template>
          <template v-else>
            <DownOutlined />
            展开
          </template>
        </a>
      </a-col>
    </a-row>
  </a-form>
</template>

<style scoped></style>

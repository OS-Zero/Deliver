<script setup lang="ts">
import { ref, reactive } from 'vue'
import { DownOutlined, UpOutlined } from '@ant-design/icons-vue'
import type { messageTemplate } from '../type'
import type { FormInstance } from 'ant-design-vue'

const expand = ref(false)

const formRef = ref<FormInstance>()

const searchForm: messageTemplate = reactive({
  templateName: '',
  pushRange: '',
  userType: '',
  channel: '',
  messageType: '',
  channelApp: ''
})

const onFinish = (): void => {
  console.log('searchForm: ', searchForm)
}

// 等接口出来在该
const searchMessage = (): void => {
  console.log('查询成功')
}

const rangeOptions = [{ value: '默认标签1' }, { value: '默认标签2' }, { value: '默认标签3' }]
const qudaoOptions = [{ value: '默认标签1' }, { value: '默认标签2' }, { value: '默认标签3' }]
</script>

<template>
  <a-form ref="formRef" name="advanced_search" class="search" :model="searchForm" @finish="onFinish">
    <a-row :gutter="24">
      <a-col :span="8">
        <a-form-item name="templateName" label="模板名">
          <a-input v-model:value="searchForm.templateName" placeholder="请输入模板名"></a-input>
        </a-form-item>
      </a-col>
      <a-col :span="8">
        <a-form-item name="pushRange" label="推送范围">
          <a-select v-model:value="searchForm.pushRange" placeholder="请选择推送范围">
            <a-select-option value="范围1">范围1</a-select-option>
            <a-select-option value="范围2">范围2</a-select-option>
          </a-select>
        </a-form-item>
      </a-col>
      <a-col :span="8" v-if="expand">
        <a-form-item name="userType" label="用户类型">
          <a-select v-model:value="searchForm.userType" placeholder="请选择用户类型">
            <a-select-option value="类型1">类型1</a-select-option>
            <a-select-option value="类型2">类型2</a-select-option>
          </a-select>
        </a-form-item>
      </a-col>
      <a-col :span="8" v-if="expand">
        <a-form-item name="messageType" label="消息类型">
          <a-select v-model:value="searchForm.messageType" placeholder="请选择消息类型" :options="rangeOptions" />
        </a-form-item>
      </a-col>
      <a-col :span="8" v-if="expand">
        <a-form-item name="channel" label="渠道选择">
          <a-select v-model:value="searchForm.channel" placeholder="请选择渠道" :options="qudaoOptions" />
        </a-form-item>
      </a-col>
      <a-col :span="8" v-if="expand">
        <a-form-item name="channelApp" label="渠道App">
          <a-select v-model:value="searchForm.channelApp" placeholder="请选择渠道App">
            <a-select-option value="类型1">类型1</a-select-option>
            <a-select-option value="类型2">类型2</a-select-option>
          </a-select>
        </a-form-item>
      </a-col>
      <a-col
        :span="expand === true ? 24 : 8"
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

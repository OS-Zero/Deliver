<script setup lang="ts">
import { ref, reactive } from 'vue'
import { DownOutlined, UpOutlined } from '@ant-design/icons-vue'
import type { searchMessage } from '../type'
import type { FormInstance } from 'ant-design-vue'

const expand = ref(false)

const formRef = ref<FormInstance>()

const searchPage: searchMessage = reactive({
  templateName: '',
  pushRange: undefined,
  usersType: undefined,
  currentPage: 1,
  pageSize: 10
})

const searchMes = (): void => {
  console.log(123123)
}

const onFinish = (): void => {
  console.log('searchPage: ', searchPage)
  console.log('查询成功')
}

defineExpose({
  searchPage
})
</script>

<template>
  <a-form ref="formRef" name="advanced_search" class="search" :model="searchPage" @finish="onFinish">
    <a-row :gutter="24">
      <a-col :span="8">
        <a-form-item name="templateName" label="模板名">
          <a-input v-model:value="searchPage.templateName" placeholder="请输入模板名"></a-input>
        </a-form-item>
      </a-col>
      <a-col :span="8">
        <a-form-item name="pushRange" label="推送范围">
          <a-select v-model:value="searchPage.pushRange" placeholder="请选择推送范围">
            <a-select-option value="范围1">范围1</a-select-option>
            <a-select-option value="范围2">范围2</a-select-option>
          </a-select>
        </a-form-item>
      </a-col>
      <a-col :span="8" v-if="expand">
        <a-form-item name="usersType" label="用户类型">
          <a-select v-model:value="searchPage.usersType" placeholder="请选择用户类型">
            <a-select-option value="1">类型1</a-select-option>
            <a-select-option value="2">类型2</a-select-option>
            <a-select-option value="3">类型3</a-select-option>
            <a-select-option value="4">类型4</a-select-option>
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
        <a-button type="primary" html-type="submit" @click="searchMes">查询</a-button>
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

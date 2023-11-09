<script setup lang="ts">
import { ref, reactive } from 'vue'
import { DownOutlined, UpOutlined } from '@ant-design/icons-vue'
import locale from 'ant-design-vue/es/date-picker/locale/zh_CN'
import 'dayjs/locale/zh-cn'
import type { searchMessage } from '../type'
import type { FormInstance } from 'ant-design-vue'
import type { Dayjs } from 'dayjs'

const expand = ref(false)

const formRef = ref<FormInstance>()

const searchPage: searchMessage = reactive({
  templateName: undefined,
  pushRange: undefined,
  usersType: undefined,
  currentPage: 1,
  pageSize: 10,
  startTime: undefined,
  endTime: undefined
})

const selectedRange = ref<Array<Dayjs | null>>([null, null])

// 在 setup 函数中定义 clearForm 函数
const clearForm = (): void => {
  // 重置日期选择框的内容为null
  selectedRange.value = [null, null]
  // 使用 ant-design-vue 提供的方法重置其他表单项的内容
  formRef.value?.resetFields()
}

const onRangeChange = (value: [Dayjs, Dayjs] | [string, string], dateString: [string, string]): void => {
  if (Array.isArray(value)) {
    console.log('Selected Time: ', value)
  } else {
    console.log('Formatted Selected Time: ', value)
  }
  console.log('Formatted Selected Time: ', dateString)
}

// 新增自定义事件，当查询按钮被点击时，可以立即告知父组件
const $emit = defineEmits(['mes'])

const searchMes = (): void => {
  $emit('mes')
}

defineExpose({
  searchPage
})
</script>

<template>
  <a-form ref="formRef" name="advanced_search" class="search" :model="searchPage">
    <a-row :gutter="24">
      <a-col :span="8">
        <a-form-item name="templateName" label="模板名">
          <a-input v-model:value="searchPage.templateName" placeholder="请输入模板名"></a-input>
        </a-form-item>
      </a-col>
      <a-col :span="8">
        <a-form-item name="pushRange" label="推送范围">
          <a-select v-model:value="searchPage.pushRange" placeholder="请选择推送范围">
            <a-select-option value="0">范围0</a-select-option>
            <a-select-option value="1">范围1</a-select-option>
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
      <a-col :span="16" v-if="expand">
        <a-form-item name="useType" label="起始日期-结束日期">
          <a-range-picker
            :locale="locale"
            v-model:value="selectedRange"
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
        <a-button type="primary" html-type="submit" @click="searchMes">查询</a-button>
        <a-button style="margin: 0 8px" @click="clearForm">清空</a-button>
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

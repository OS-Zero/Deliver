<script lang="ts" setup>
import { DownOutlined, UpOutlined, ReloadOutlined } from '@ant-design/icons-vue'
import { ref, reactive, h } from 'vue'
import type { UnwrapRef } from 'vue'
import type { FormInstance } from 'ant-design-vue'
import type { Rule } from 'ant-design-vue/es/form'
// import { message } from 'ant-design-vue'

interface messageTemplate {
  templateName: string
  pushRange: string
  userType: string
  channel: string[]
  messageType: string[]
  channelApp: string
}

// 搜索部分
const expand = ref(false)

const formRef = ref<FormInstance>()

const searchForm: messageTemplate = reactive({
  templateName: '',
  pushRange: '',
  userType: '',
  channel: [],
  messageType: [],
  channelApp: ''
})

const onFinish = (): void => {
  console.log('searchForm: ', searchForm)
}

// 等接口出来在该
const searchMessage = (): void => {
  console.log('查询成功')
}

/// 表格部分
///
// 新增
const open = ref<boolean>(false)
const addModules = (): void => {
  open.value = true
}

const labelCol = { span: 4 }

const wrapperCol = { span: 20 }

const addTemplate: UnwrapRef<messageTemplate> = reactive({
  templateName: '',
  pushRange: '',
  userType: '',
  channel: [],
  messageType: [],
  channelApp: ''
})

const rules: Record<string, Rule[]> = {
  templateName: [
    { required: true, message: '请输入模板名', trigger: 'change' },
    { min: 3, max: 20, message: '长度在 3 到 20 个字符', trigger: 'blur' }
  ],
  pushRange: [{ required: true, message: '请选择推送范围', trigger: 'change' }],
  userType: [{ required: true, message: '请选择用户类型', trigger: 'change' }],
  messageType: [
    {
      type: 'array',
      required: true,
      message: '请选择至少一个消息类型',
      trigger: 'change'
    }
  ],
  channel: [
    {
      type: 'array',
      required: true,
      message: '请选择至少一个渠道',
      trigger: 'change'
    }
  ],
  channelApp: [{ required: true, message: '请选择渠道App', trigger: 'change' }]
}

const templateForm = ref()
const handleOk = (): void => {
  templateForm.value
    .validate()
    .then(() => {
      console.log('values', addTemplate)
      open.value = false
    })
    .catch(error => {
      console.log('error', error)
    })
}
const handleCancel = (): void => {
  templateForm.value.resetFields()
}
const rangeOptions = [{ value: '默认标签1' }, { value: '默认标签2' }, { value: '默认标签3' }]
const qudaoOptions = [{ value: '默认标签1' }, { value: '默认标签2' }, { value: '默认标签3' }]

const columns = [
  {
    title: '模版 ID',
    dataIndex: 'projectName',
    key: 'projectname'
  },
  {
    title: '模板名',
    dataIndex: 'admin1',
    key: 'admin1'
  },
  {
    title: '推送范围',
    dataIndex: 'admin2',
    key: 'admin2'
  },
  {
    title: '用户类型',
    dataIndex: 'admin2',
    key: 'admin2'
  },
  {
    title: '模板累计使用数',
    dataIndex: 'admin2',
    key: 'admin2'
  },
  {
    title: '模板状态',
    dataIndex: 'admin2',
    key: 'admin2'
  },
  {
    title: '操作',
    key: 'control'
  }
]
// data到时候肯定是需要去接口获取到res然后渲染的
const data = ref([
  {
    key: '1',
    projectName: '1.txt',
    admin1: 'a',
    admin2: 'b'
  }
])
</script>

<template>
  <!-- 搜索部分 -->
  <div id="message-container">
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
              <a-select-option :value="0">不限</a-select-option>
              <a-select-option :value="1">企业内部</a-select-option>
              <a-select-option :value="2">企业外部</a-select-option>
            </a-select>
          </a-form-item>
        </a-col>
        <a-col :span="8" v-if="expand">
          <a-form-item name="userType" label="用户类型">
            <a-select v-model:value="searchForm.userType" placeholder="请选择用户类型">
              <a-select-option :value="1">企业账号</a-select-option>
              <a-select-option :value="2">电话</a-select-option>
              <a-select-option :value="3">邮箱</a-select-option>
              <a-select-option :value="4">平台 UserId</a-select-option>
            </a-select>
          </a-form-item>
        </a-col>
        <a-col :span="8" v-if="expand">
          <a-form-item name="messageType" label="消息类型">
            <a-select
              v-model:value="searchForm.messageType"
              mode="tags"
              placeholder="请选择消息类型"
              :options="rangeOptions"
            />
          </a-form-item>
        </a-col>
        <a-col :span="8" v-if="expand">
          <a-form-item name="channel" label="渠道选择">
            <a-select v-model:value="searchForm.channel" mode="tags" placeholder="请选择渠道" :options="qudaoOptions" />
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
            'text-align': expand === true ? 'right' : 'right',
            'margin-bottom': expand === true ? '24px' : '0'
          }"
        >
          <a-button type="primary" html-type="submit" @click="searchMessage">查询</a-button>
          <a-button style="margin: 0 8px" @click="() => formRef.resetFields()">清空</a-button>
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
    <div class="message-section">
      <div class="splitter">
        <a-tooltip title="刷新">
          <a-button shape="circle" :icon="h(ReloadOutlined)" />
        </a-tooltip>
        <a-button type="primary" class="addModule" @click="addModules">新增模板</a-button>
        <a-modal v-model:open="open" title="新增模板" width="650px" :footer="null" @cancel="handleCancel">
          <a-form
            ref="templateForm"
            :model="addTemplate"
            :rules="rules"
            :label-col="labelCol"
            :wrapper-col="wrapperCol"
            class="temform"
          >
            <a-form-item ref="templateName" label="模板名" name="templateName" class="tem-item">
              <a-input
                v-model:value="addTemplate.templateName"
                placeholder="请填写长度在3到20个字符的模板名"
                style="width: 70%"
              />
            </a-form-item>
            <a-form-item label="推送范围" name="pushRange" class="tem-item">
              <a-radio-group v-model:value="addTemplate.pushRange" button-style="solid">
                <a-radio-button :value="0">不限</a-radio-button>
                <a-radio-button :value="1">企业内部</a-radio-button>
                <a-radio-button :value="2">企业外部</a-radio-button>
              </a-radio-group>
            </a-form-item>
            <a-form-item label="用户类型" name="userType" class="tem-item">
              <a-radio-group v-model:value="addTemplate.userType" button-style="solid">
                <a-radio-button :value="1">企业账号</a-radio-button>
                <a-radio-button :value="2">电话</a-radio-button>
                <a-radio-button :value="3">邮箱</a-radio-button>
                <a-radio-button :value="4">平台 UserId</a-radio-button>
              </a-radio-group>
            </a-form-item>
            <a-form-item label="消息类型" name="messageType" class="tem-item">
              <a-checkbox-group v-model:value="addTemplate.messageType">
                <a-checkbox value="1" name="type">Online</a-checkbox>
                <a-checkbox value="2" name="type">Promotion</a-checkbox>
                <a-checkbox value="3" name="type">Offline</a-checkbox>
              </a-checkbox-group>
            </a-form-item>
            <a-form-item label="渠道选择" name="channel" class="tem-item">
              <a-select
                v-model:value="addTemplate.channel"
                mode="tags"
                placeholder="请选择渠道"
                :options="rangeOptions"
                style="width: 70%"
              />
            </a-form-item>
            <a-form-item label="渠道App" name="channelApp" class="tem-item">
              <a-select v-model:value="addTemplate.channelApp" style="width: 50%">
                <a-select-option value="shanghai">渠道one</a-select-option>
                <a-select-option value="beijing">渠道two</a-select-option>
              </a-select>
            </a-form-item>
            <a-form-item :wrapper-col="{ span: 14, offset: 4 }" class="tem-item">
              <a-button type="primary" @click="handleOk">确认新建</a-button>
              <a-button style="margin-left: 10px" @click="handleCancel">重置</a-button>
            </a-form-item>
          </a-form>
        </a-modal>
      </div>

      <!-- 表格部分 -->
      <a-table :columns="columns" :data-source="data" bordered>
        <template #headerCell="{ column }">
          <template v-if="column.key === 'projectName'">
            <span>{{ column.title }}</span>
          </template>
        </template>

        <template #bodyCell="{ column, record }">
          <template v-if="column.key === 'projectName'">
            <a>
              {{ record.name }}
            </a>
          </template>
          <template v-else-if="column.key === 'control'">
            <span>
              <a>编辑</a>
              <a-divider type="vertical" />
              <a>删除</a>
            </span>
          </template>
        </template>
      </a-table>
    </div>
    <!-- 对表格的操作 -->
  </div>
</template>

<style lang="scss" scoped>
// 表格斑马纹设置
:deep(.ant-table-tbody tr:nth-child(2n)) {
  background-color: #fafafa;
}

:deep(.ant-layout-content) {
  background-color: aqua;
}
#message-container {
  height: 100%;
  overflow: auto;
}
.search {
  padding: 24px 24px 0 24px;
  background: #ffffff;
  border-radius: 6px;
  .search-item:nth-child(1) {
    margin-left: 15px;
  }
  .search-item:nth-child(2) {
    margin-left: 30px;
  }
}
#components-form-demo-advanced-search .ant-form {
  max-width: none;
}
#components-form-demo-advanced-search .search-result-list {
  margin-top: 16px;
  border: 1px dashed #e9e9e9;
  border-radius: 2px;
  background-color: #fafafa;
  min-height: 200px;
  text-align: center;
  padding-top: 80px;
}
[data-theme='dark'] .ant-advanced-search-form {
  background: rgba(255, 255, 255, 0.04);
  border: 1px solid #434343;
  padding: 24px;
  border-radius: 2px;
}
[data-theme='dark'] #components-form-demo-advanced-search .search-result-list {
  border: 1px dashed #434343;
  background: rgba(255, 255, 255, 0.04);
}

.splitter {
  width: 100%;
  height: 60px;
  display: flex;
  align-items: center;
  justify-content: right;
  .addModule {
    margin: 0px 20px;
  }
}

.temform {
  .tem-item {
    margin-top: 20px;
  }
  .tem-item:nth-child(7) {
    text-align: right;
    margin-left: 300px;
  }
}
.message-section {
  height: 100%;
  border-radius: 6px;
  margin-top: 12px;
  background: #ffffff;
  padding: 12px;
}
</style>

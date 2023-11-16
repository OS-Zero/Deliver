<script lang="ts" setup>
import { ReloadOutlined, EyeTwoTone, EyeInvisibleOutlined } from '@ant-design/icons-vue'
import { ref, reactive, h, watch, computed, onMounted } from 'vue'
import type { UnwrapRef } from 'vue'
import type { TableColumnsType } from 'ant-design-vue'
import type { Rule } from 'ant-design-vue/es/form'
import type { messageTemplate } from './type'
import searchForm from './components/searchForm.vue'
// import { message } from 'ant-design-vue'

import Prism from 'prismjs' // 导入代码高亮插件的core（里面提供了其他官方插件及代码高亮样式主题，你只需要引入即可）
import 'prismjs/themes/prism.css' // 引入代码高亮主题（这个去node_modules的安装prismjs中找到想使用的主题即可）

/// 表格部分
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
  channel: '',
  messageType: '',
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
      required: true,
      message: '请选择消息类型',
      trigger: 'change'
    }
  ],
  channel: [
    {
      required: true,
      message: '请选择渠道',
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

const channelData = ['默认标签1', '默认标签2', '默认标签3']

const messageData = {
  默认标签1: ['Online'],
  默认标签2: ['Promotion'],
  默认标签3: ['Offline']
}

const appData = {
  默认标签1: ['IOS'],
  默认标签2: ['安卓'],
  默认标签3: ['MAC']
}

const mesType = computed(() => {
  return messageData[addTemplate.channel] === undefined ? [] : messageData[addTemplate.channel]
})

const appType = computed(() => {
  return appData[addTemplate.channel] === undefined ? [] : appData[addTemplate.channel]
})

watch(
  () => addTemplate.channel,
  newVal => {
    if (messageData[newVal] !== undefined) {
      addTemplate.messageType = messageData[newVal][0]
    } else {
      // 未定义或空数组的情况
      addTemplate.messageType = ''
      messageData[newVal] = []
    }
    if (appData[newVal] !== undefined) {
      addTemplate.channelApp = appData[newVal][0]
    } else {
      // 处理未定义或空数组的情况
      addTemplate.channelApp = ''
      appData[newVal] = []
    }
  }
)

const columns: TableColumnsType = [
  {
    title: 'AppId',
    dataIndex: 'appId',
    key: 'appId'
  },
  {
    title: 'App名称',
    dataIndex: 'appName',
    key: 'appName'
  },
  {
    title: '渠道类型',
    dataIndex: 'channelType',
    key: 'channelType'
  },
  {
    title: '使用次数',
    dataIndex: 'useCount',
    key: 'useCount'
  },
  {
    title: 'app状态',
    dataIndex: 'appStatus',
    key: 'appStatus'
  },
  {
    title: '操作',
    key: 'operation',
    fixed: 'right',
    width: 180
  }
]
// data到时候肯定是需要去接口获取到res然后渲染的
const data = ref([
  {
    key: '1',
    appId: '1',
    appName: '飞书消息推送',
    channelType: 'b',
    useCount: 0,
    appStatus: 1,
    appConfig: 'xxx',
    createUser: 1,
    createTime: '2023/12/6'
  },
  {
    key: '2',
    appId: '1',
    appName: '飞书消息推送',
    channelType: 'b',
    useCount: 0,
    appStatus: 1,
    appConfig: 'xxx',
    createUser: 1,
    createTime: '2023/12/6'
  },
  {
    key: '3',
    appId: '1',
    appName: '飞书消息推送',
    channelType: 'b',
    useCount: 0,
    appStatus: 1,
    appConfig: 'xxx',
    createUser: 1,
    createTime: '2023/12/6'
  },
  {
    key: '4',
    appId: '1',
    appName: '飞书消息推送',
    channelType: 'b',
    useCount: 0,
    appStatus: 1,
    appConfig: 'xxx',
    createUser: 1,
    createTime: '2023/12/6'
  },
  {
    key: '',
    appId: '1',
    appName: '飞书消息推送',
    channelType: 'b',
    useCount: 0,
    appStatus: 1,
    appConfig: 'xxx',
    createUser: 1,
    createTime: '2023/12/6'
  }
])
const current = ref()
const pageSize = ref()
const visible = ref(false)
const pagination = computed(() => ({
  total: 200,
  current: current.value,
  pageSize: pageSize.value
}))
onMounted(() => {
  Prism.highlightAll()
})
</script>

<template>
  <!-- 搜索部分 -->
  <searchForm />
  <!-- 表格部分 -->
  <div id="message-container">
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
                <a-radio-button value="杭州">杭州</a-radio-button>
                <a-radio-button value="上海">上海</a-radio-button>
                <a-radio-button value="北京">北京</a-radio-button>
                <a-radio-button value="成都">成都</a-radio-button>
              </a-radio-group>
            </a-form-item>
            <a-form-item label="用户类型" name="userType" class="tem-item">
              <a-radio-group v-model:value="addTemplate.userType" button-style="solid">
                <a-radio-button value="类型1">类型1</a-radio-button>
                <a-radio-button value="类型2">类型2</a-radio-button>
                <a-radio-button value="类型3">类型3</a-radio-button>
                <a-radio-button value="类型4">类型4</a-radio-button>
              </a-radio-group>
            </a-form-item>
            <a-form-item label="渠道选择" name="channel" class="tem-item">
              <a-select
                v-model:value="addTemplate.channel"
                :options="channelData.map(pro => ({ value: pro }))"
                style="width: 70%"
              />
            </a-form-item>
            <a-form-item label="消息类型" name="messageType" class="tem-item">
              <a-select
                v-model:value="addTemplate.messageType"
                :options="mesType.map((pro: any) => ({ value: pro }))"
                style="width: 70%"
              />
            </a-form-item>
            <a-form-item label="渠道App" name="channelApp" class="tem-item">
              <a-select
                v-model:value="addTemplate.channelApp"
                :options="appType.map((pro: any) => ({ value: pro }))"
                style="width: 70%"
              />
            </a-form-item>
            <a-form-item :wrapper-col="{ span: 14, offset: 4 }" class="tem-item">
              <a-button type="primary" @click="handleOk">确认新建</a-button>
              <a-button style="margin-left: 10px" @click="handleCancel">重置</a-button>
            </a-form-item>
          </a-form>
        </a-modal>
      </div>
      <!-- 表格部分 -->

      <a-table
        :pagination="pagination"
        :columns="columns"
        :data-source="data"
        :scroll="{ x: 1200, y: 300 }"
        bordered
        :expand-column-width="100"
      >
        <template #bodyCell="{ column }">
          <template v-if="column.key === 'operation'">
            <a-button type="primary" class="btn-manager">编辑</a-button>
            <a-button type="primary" danger>删除</a-button>
          </template>
        </template>
        <template #expandedRowRender="{ record }">
          <a-descriptions>
            <a-descriptions-item>
              <pre class="language-json" style="width: 100%">
              <code class="language-json" >
                {
                   "a":1,
                   "b":2
                }
              </code>
              </pre>
            </a-descriptions-item>

            <a-descriptions-item label="APP 配置">
              <div style="display: flex; justify-content: center; align-items: center">
                <span style="margin-right: 10px">{{ visible ? record.appConfig : '***' }}</span>
                <EyeTwoTone v-if="visible" @click="visible = !visible"></EyeTwoTone>
                <EyeInvisibleOutlined v-else @click="visible = !visible"></EyeInvisibleOutlined>
              </div>
            </a-descriptions-item>
            <!-- <a-descriptions-item label="创建者">{{ record.createUser }}</a-descriptions-item>
            <a-descriptions-item label="创建时间">{{ record.createTime }}</a-descriptions-item> -->
          </a-descriptions>
        </template>
        <template #expandColumnTitle>
          <span style="color: red">详情</span>
        </template>
      </a-table>
    </div>
    <!-- 对表格的操作 -->
  </div>
</template>
<style lang="scss">
#message-container {
  height: 100%;
  overflow: auto;
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

    .btn-manager {
      margin-right: 10px;
    }
  }
}
</style>

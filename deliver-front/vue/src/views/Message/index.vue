<script lang="ts" setup>
import { ReloadOutlined } from '@ant-design/icons-vue'
import { ref, reactive, h, onMounted } from 'vue' // watch, computed,
import type { UnwrapRef } from 'vue'
// import type { Rule } from 'ant-design-vue/es/form'
import type { TableColumnsType } from 'ant-design-vue'
import type { messageTemplate, searchMessage } from './type'
import searchForm from './components/searchForm.vue'
import { getTemplatePages } from '@/api/message'
import { getDate } from '@/utils/date'
// import { message } from 'ant-design-vue'

/// 增删改查
const searchform = ref()
// 新增
const open = ref<boolean>(false)
const addModules = (): void => {
  open.value = true
}

// const labelCol = { span: 4 }

// const wrapperCol = { span: 20 }
// const sea = ref()

const templateTable: UnwrapRef<messageTemplate[]> = reactive([])

const searchItem: searchMessage = reactive({
  templateName: undefined,
  pushRange: undefined,
  usersType: undefined,
  currentPage: 1,
  pageSize: 10
})

const searchTemplate = (): void => {
  getTemplatePages(searchItem)
    .then(res => {
      templateTable.length = 0
      if (res.records.length > 0) {
        res.records.forEach((item: any, index: number) => {
          item.channelType = JSON.parse(item.pushWays).channelType
          item.messageType = JSON.parse(item.pushWays).messageType
          item.createTime = getDate(item.createTime)
          item.key = index
          templateTable.push(item)
        })
        console.warn(templateTable)
      }
    })
    .catch(err => {
      console.error('An error occurred:', err)
    })
}
// const rules: Record<string, Rule[]> = {
//   templateName: [
//     { required: true, message: '请输入模板名', trigger: 'change' },
//     { min: 3, max: 20, message: '长度在 3 到 20 个字符', trigger: 'blur' }
//   ],
//   pushRange: [{ required: true, message: '请选择推送范围', trigger: 'change' }],
//   usersType: [{ required: true, message: '请选择用户类型', trigger: 'change' }],
//   messageType: [
//     {
//       required: true,
//       message: '请选择消息类型',
//       trigger: 'change'
//     }
//   ],
//   channel: [
//     {
//       required: true,
//       message: '请选择渠道',
//       trigger: 'change'
//     }
//   ],
//   channelApp: [{ required: true, message: '请选择渠道App', trigger: 'change' }]
// }

// const templateForm = ref()
// const handleOk = (): void => {
//   templateForm.value
//     .validate()
//     .then(() => {
//       console.log('values', templateTable)
//       open.value = false
//     })
//     .catch(error => {
//       console.log('error', error)
//     })
// }
// const handleCancel = (): void => {
//   templateForm.value.resetFields()
// }

// const channelData = ['默认标签1', '默认标签2', '默认标签3']

// const messageData = {
//   默认标签1: ['Online'],
//   默认标签2: ['Promotion'],
//   默认标签3: ['Offline']
// }

// const appData = {
//   默认标签1: ['IOS'],
//   默认标签2: ['安卓'],
//   默认标签3: ['MAC']
// }

// const mesType = computed(() => {
//   return messageData[templateTable.channel] === undefined ? [] : messageData[templateTable.channel]
// })

// const appType = computed(() => {
//   return appData[templateTable.channel] === undefined ? [] : appData[templateTable.channel]
// })

// watch(
//   () => templateTable.channel,
//   newVal => {
//     if (messageData[newVal] !== undefined) {
//       templateTable.messageType = messageData[newVal][0]
//     } else {
//       // 未定义或空数组的情况
//       templateTable.messageType = ''
//       messageData[newVal] = []
//     }
//     if (appData[newVal] !== undefined) {
//       templateTable.channelApp = appData[newVal][0]
//     } else {
//       // 处理未定义或空数组的情况
//       templateTable.channelApp = ''
//       appData[newVal] = []
//     }
//   }
// )

// 表格数据
const columns: TableColumnsType = [
  {
    title: '模版 ID',
    dataIndex: 'templateId',
    key: 'templateId'
  },
  {
    title: '模板名',
    dataIndex: 'templateName',
    key: 'templateName'
  },
  {
    title: '推送范围',
    dataIndex: 'pushRange',
    key: 'pushRange'
  },
  {
    title: '用户类型',
    dataIndex: 'usersType',
    key: 'usersType'
  },
  {
    title: '模板累计使用数',
    dataIndex: 'useCount',
    key: 'useCount'
  },
  {
    title: '模板状态',
    dataIndex: 'templateStatus',
    key: 'templateStatus'
  },
  {
    title: '操作',
    key: 'operation',
    fixed: 'right',
    width: 180
  }
]

const innerColumns = [
  {
    title: '渠道选择',
    dataIndex: 'channelType',
    key: 'channelType'
  },
  {
    title: '消息类型',
    dataIndex: 'messageType',
    key: 'messageType'
  },
  {
    title: '创建用户',
    dataIndex: 'createUser',
    key: 'createUser'
  },
  {
    title: '创建时间',
    dataIndex: 'createTime',
    key: 'createTime'
  },
  {
    title: '渠道App ID',
    dataIndex: 'appId',
    key: 'appId'
  },
  {
    title: '渠道App名称',
    dataIndex: 'appName',
    key: 'appName'
  }
]

// 渲染 data
const innertemplatedata: UnwrapRef<messageTemplate[]> = reactive([])

const expandedRowKeys: number[] = reactive([])

const getInnerData = (expanded, record): void => {
  // 判断是否点开
  expandedRowKeys.length = 0
  if (expanded === true) {
    const b = record.key.toString()
    expandedRowKeys.push(Number(b.slice(-1)))
    innertemplatedata.length = 0
    innertemplatedata.push(record)
  } else {
    expandedRowKeys.length = 0
  }
}

onMounted(() => {
  getTemplatePages(searchItem)
    .then(res => {
      if (res.records.length > 0) {
        res.records.forEach((item: any, index: number) => {
          item.channelType = JSON.parse(item.pushWays).channelType
          item.messageType = JSON.parse(item.pushWays).messageType
          item.createTime = getDate(item.createTime)
          item.key = index
          templateTable.push(item)
        })
        console.warn(templateTable)
      }
    })
    .catch(err => {
      console.error('An error occurred:', err)
    })
})
</script>

<template>
  <!-- 搜索部分 -->
  <searchForm ref="searchform" @searchMes="searchTemplate" />
  <!-- 表格部分 -->
  <div id="message-container">
    <div class="message-section">
      <div class="splitter">
        <a-tooltip title="刷新">
          <a-button shape="circle" :icon="h(ReloadOutlined)" />
        </a-tooltip>
        <a-button type="primary" class="addModule" @click="addModules">新增模板</a-button>
        <a-modal v-model:open="open" title="新增模板" width="650px" :footer="null" @cancel="handleCancel">
          <!-- <a-form ref="templateForm" :model="templateTable" :rules="rules" :label-col="labelCol" :wrapper-col="wrapperCol"
            class="temform">
            <a-form-item ref="templateName" label="模板名" name="templateName" class="tem-item">
              <a-input v-model:value="templateTable.templateName" placeholder="请填写长度在3到20个字符的模板名" style="width: 70%" />
            </a-form-item>
            <a-form-item label="推送范围" name="pushRange" class="tem-item">
              <a-radio-group v-model:value="templateTable.pushRange" button-style="solid">
                <a-radio-button :value="0">不限</a-radio-button>
                <a-radio-button :value="1">企业内部</a-radio-button>
                <a-radio-button :value="2">企业外部</a-radio-button>
              </a-radio-group>
            </a-form-item>
            <a-form-item label="用户类型" name="usersType" class="tem-item">
              <a-radio-group v-model:value="templateTable.usersType" button-style="solid">
                <a-radio-button :value="1">企业账号</a-radio-button>
                <a-radio-button :value="2">电话</a-radio-button>
                <a-radio-button :value="3">邮箱</a-radio-button>
                <a-radio-button :value="4">平台 UserId</a-radio-button>
              </a-radio-group>
            </a-form-item>
            <a-form-item label="渠道选择" name="channel" class="tem-item">
              <a-select v-model:value="templateTable.channel" :options="channelData.map(pro => ({ value: pro }))"
                style="width: 70%" />
            </a-form-item>
            <a-form-item label="消息类型" name="messageType" class="tem-item">
              <a-select v-model:value="templateTable.messageType" :options="mesType.map((pro: any) => ({ value: pro }))"
                style="width: 70%" />
            </a-form-item>
            <a-form-item label="渠道App" name="channelApp" class="tem-item">
              <a-select v-model:value="templateTable.channelApp" :options="appType.map((pro: any) => ({ value: pro }))"
                style="width: 70%" />
            </a-form-item>
            <a-form-item :wrapper-col="{ span: 14, offset: 4 }" class="tem-item">
              <a-button type="primary" @click="handleOk">确认新建</a-button>
              <a-button style="margin-left: 10px" @click="handleCancel">重置</a-button>
            </a-form-item>
          </a-form> -->
        </a-modal>
      </div>

      <!-- 表格部分 -->
      <a-table
        :columns="columns"
        :data-source="templateTable"
        :scroll="{ x: 1200, y: 900 }"
        bordered
        class="components-table-demo-nested"
        @expand="getInnerData"
        :expandedRowKeys="expandedRowKeys"
      >
        <template #bodyCell="{ column, record }">
          <template v-if="column.key === 'templateStatus'">
            <span v-if="record.templateStatus === 1">
              <a-button type="primary" style="background-color: rgb(102, 193, 58)" @click="record.templateStatus = 0">
                启用
              </a-button>
            </span>
            <span v-else>
              <a-button type="primary" danger @click="record.templateStatus = 1">禁用</a-button>
            </span>
          </template>
          <template v-if="column.key === 'operation'">
            <a-button type="primary" class="btn-manager">编辑</a-button>
            <a-button type="primary" danger>删除</a-button>
          </template>
        </template>
        <template #expandedRowRender>
          <a-table :columns="innerColumns" :data-source="innertemplatedata" :pagination="false">
            <template #bodyCell></template>
          </a-table>
        </template>
      </a-table>
    </div>
    <!-- 对表格的操作 -->
  </div>
</template>

<style lang="scss" scoped>
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
</style>

<script lang="ts" setup>
import { ReloadOutlined } from '@ant-design/icons-vue'
import { ref, reactive, h, onMounted } from 'vue' // watch, ,
import type { UnwrapRef } from 'vue'
// import type { Rule } from 'ant-design-vue/es/form'
import type { TableColumnsType } from 'ant-design-vue'
import { message } from 'ant-design-vue'
import type { messageTemplate, searchMessage } from './type'
import searchForm from './components/searchForm.vue'
import { getTemplatePages } from '@/api/message'
import { getDate } from '@/utils/date'

/**
 * 表格初始化
 */
const templateTable: UnwrapRef<messageTemplate[]> = reactive([])
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
    width: 140
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

/**
 * 渲染 data
 */
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

/**
 * 相关操作: 增删改查
 */
const searchform = ref()

// 新增操作
const open = ref<boolean>(false)
const addModules = (): void => {
  open.value = true
}

// const labelCol = { span: 4 }

// const wrapperCol = { span: 20 }

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

/// 查询操作
const searchItem: searchMessage = reactive({
  templateName: undefined,
  pushRange: undefined,
  usersType: undefined,
  currentPage: 1,
  pageSize: 10,
  startTime: undefined,
  endTime: undefined
})

const total = ref()

const current = ref()

const change = (page: number, pageSize: number): void => {
  console.log(page, pageSize)
  searchTemplate(page, pageSize)
}

const locale = {
  items_per_page: '条/页', // 每页显示条数的文字描述
  jump_to: '跳至', // 跳转到某页的文字描述
  page: '页', // 页的文字描述
  prev_page: '上一页', // 上一页按钮文字描述
  next_page: '下一页' // 下一页按钮文字描述
}

// 条件查询
const searchTemplate = (page?: number, pageSize?: number): void => {
  // 对象解构
  const { perid, ...rest } = searchform.value.searchPage
  const searchNeedMes = { ...rest }
  console.warn(searchNeedMes)
  searchNeedMes.currentPage = page
  searchNeedMes.pageSize = pageSize
  getTemplatePages(searchNeedMes)
    .then(res => {
      templateTable.length = 0
      if (res.records.length > 0) {
        total.value = res.total
        res.records.forEach((item: any, index: number) => {
          item.channelType = JSON.parse(item.pushWays).channelType
          item.messageType = JSON.parse(item.pushWays).messageType
          item.createTime = getDate(item.createTime)
          // eslint-disable-next-line
          item.templateStatus = item.templateStatus === 1 ? true : false
          item.key = index
          templateTable.push(item)
        })
        void message.success('查询成功~ (*^▽^*)')
        console.warn('查询数据', templateTable)
      } else {
        void message.success('未查询到任何数据   ≧ ﹏ ≦')
        console.warn('查询数据', templateTable)
      }
      searchform.value.iconLoading = false
    })
    .catch(err => {
      searchform.value.iconLoading = false
      void message.error('查询失败~ (＞︿＜)')
      console.error('An error occurred:', err)
    })
}

onMounted(() => {
  getTemplatePages(searchItem)
    .then(res => {
      if (res.records.length > 0) {
        total.value = res.total
        res.records.forEach((item: any, index: number) => {
          item.channelType = JSON.parse(item.pushWays).channelType
          item.messageType = JSON.parse(item.pushWays).messageType
          item.createTime = getDate(item.createTime)
          // eslint-disable-next-line
          item.templateStatus = item.templateStatus === 1 ? true : false
          item.key = index
          templateTable.push(item)
        })
        console.warn('初始化数据', templateTable)
      }
    })
    .catch(err => {
      console.error('An error occurred:', err)
    })
})
</script>

<template>
  <!-- 搜索部分 -->
  <searchForm ref="searchform" @mes="searchTemplate" />
  <!-- 表格部分 -->
  <div id="message-container">
    <div class="message-section">
      <div class="splitter">
        <a-tooltip title="刷新">
          <a-button shape="circle" :icon="h(ReloadOutlined)" @click="searchTemplate()" />
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
            <span>
              <a-switch v-model:checked="record.templateStatus" checked-children="启用" un-checked-children="禁用" />
            </span>
          </template>
          <template v-if="column.key === 'operation'">
            <a-button type="primary" class="btn-manager" size="small" style="font-size: 13px">编辑</a-button>
            <a-button type="primary" danger size="small" style="font-size: 13px">删除</a-button>
          </template>
        </template>
        <template #expandedRowRender>
          <a-table :columns="innerColumns" :data-source="innertemplatedata" :pagination="false">
            <template #bodyCell></template>
          </a-table>
        </template>
      </a-table>
      <a-pagination
        v-model:current="current"
        class="pagination"
        show-quick-jumper
        :total="total"
        @change="change"
        showSizeChanger
        :locale="locale"
      />
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

  .pagination {
    margin-top: 20px;
    display: flex;
    justify-content: right;
  }
}
</style>

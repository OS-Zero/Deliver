<script lang="ts" setup>
import { ReloadOutlined, DownOutlined, UpOutlined, ExclamationCircleOutlined } from '@ant-design/icons-vue'
import { ref, reactive, h, onMounted, computed } from 'vue'
import type { UnwrapRef } from 'vue'
import type { TableColumnsType } from 'ant-design-vue'
import { message, Modal } from 'ant-design-vue'
import type { messageTemplate, searchMessage } from './type'
import searchForm from './components/searchForm.vue'
import addTemplate from './components/addTemplate.vue'
import modifyTemplate from './components/modifyTemplate.vue'
import { addTemplatePages, deleteTemplate, getTemplatePages, updateStatus } from '@/api/message'
import { getAllMessage, getDate } from '@/utils/date'
import { useStore } from '@/store'

/**
 * 表格初始化
 */
const templateTable: UnwrapRef<messageTemplate[]> = reactive([])
// 表格数据
const columns: TableColumnsType = [
  {
    title: 'TemplateId',
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
    width: 270
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
    title: '渠道 AppId',
    dataIndex: 'appId',
    key: 'appId'
  },
  {
    title: '渠道 APP 名',
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
    const b = record.key
    expandedRowKeys.push(b)
    innertemplatedata.length = 0
    innertemplatedata.push(record)
  } else {
    expandedRowKeys.length = 0
    innertemplatedata.length = 0
  }
}

const judgeInclude = (record): boolean => {
  return innertemplatedata.includes(record)
}

/**
 * 相关操作: 增删改查
 */
interface SearchOptions {
  page?: number
  pageSize?: number
  opt?: number // 操作标识符，识别操作，保证message消息提示不重复
}

const searchform = ref()
const addtemplate = ref()
const modifytemplate = ref()

/// 新增操作
const saveTemplate = (): void => {
  const { channelType, messageType, ...rest } = addtemplate.value.templateItem
  const savetemplate = { ...rest }
  console.warn(savetemplate)
  addtemplate.value.iconLoading = true
  addTemplatePages(savetemplate)
    .then(res => {
      if (res.code === 200) {
        addtemplate.value.iconLoading = false
        addtemplate.value.open = false
        void message.success('新增成功~ (*^▽^*)')
        searchTemplate({ opt: 2 }) // 更新表单
      }
    })
    .catch(err => {
      addtemplate.value.open = false
      addtemplate.value.iconLoading = false
      void message.error('新增失败，请检查网络~ (＞︿＜)')
      console.error('An error occurred:', err)
    })
}

/// 删除操作
type Key = string | number

const store = useStore()

const state = reactive<{
  selectedRowKeys: Key[]
  loading: boolean
}>({
  selectedRowKeys: [],
  loading: false
})

const openDelete = ref(false)

const hasSelected = computed(() => state.selectedRowKeys.length > 0)

const startDelete = (): void => {
  state.loading = true
  const templates = {
    ids: state.selectedRowKeys as number[]
  }
  deleteTemplate(templates)
    .then(res => {
      if (res.code === 200) {
        void message.success('删除成功~ (*^▽^*)')
        searchTemplate({ opt: 4 }) //
      }
      state.loading = false
    })
    .catch(err => {
      void message.error('删除失败，请检查网络~ (＞︿＜)')
      console.error('An error occurred:', err)
      state.loading = false
    })
}

const cancelSelect = (): void => {
  state.selectedRowKeys.length = 0
}

const onSelectChange = (selectedRowKeys: Key[]): void => {
  console.log(selectedRowKeys)
  state.selectedRowKeys = selectedRowKeys
  if (state.selectedRowKeys.length !== 0) {
    openDelete.value = true
  }
}

const onDelete = (id: number): void => {
  const arr: number[] = []
  arr.push(id)
  const templates = {
    ids: arr
  }
  deleteTemplate(templates)
    .then(res => {
      if (res.code === 200) {
        void message.success('删除成功~ (*^▽^*)')
        searchTemplate({ opt: 4 }) //
      }
      state.loading = false
    })
    .catch(err => {
      void message.error('删除失败，请检查网络~ (＞︿＜)')
      console.error('An error occurred:', err)
      state.loading = false
    })
}

const [modal, contextHolder] = Modal.useModal()

const showDeleteConfirm = (): void => {
  modal.confirm({
    title: '确认删除吗?',
    icon: h(ExclamationCircleOutlined),
    content: '删除后不可恢复，请谨慎删除！',
    okText: '确认',
    okType: 'danger',
    cancelText: '取消',
    onOk() {
      startDelete()
    }
  })
}

/// 修改操作
const changeStatus = (id: number, status: number | boolean): void => {
  // eslint-disable-next-line
  const sta = status === true ? 1 : 0
  const obj = {
    templateId: id,
    templateStatus: sta
  }
  updateStatus(obj)
    .then(res => {
      if (res.code === 200) {
        void message.success('修改成功~ (*^▽^*)')
        searchTemplate({ opt: 3 }) // 更新表单
      }
    })
    .catch(err => {
      void message.error('查询失败，请检查网络~ (＞︿＜)')
      console.error('An error occurred:', err)
    })
}

// 传递相关数据，初始化data
const startModify = (record): void => {
  modifytemplate.value.openModify = true
  console.log(record)
  getAllMessage(modifytemplate.value, record)
  console.log(modifytemplate.value.updateTemp)
}

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
  searchTemplate({ page, pageSize })
}

const locale = {
  items_per_page: '条/页', // 每页显示条数的文字描述
  jump_to: '跳至', // 跳转到某页的文字描述
  page: '页', // 页的文字描述
  prev_page: '上一页', // 上一页按钮文字描述
  next_page: '下一页' // 下一页按钮文字描述
}

// 条件查询
const searchTemplate = ({ page, pageSize, opt }: SearchOptions = {}): void => {
  // 对象解构
  const { perid, ...rest } = searchform.value.searchPage
  const searchNeedMes = { ...rest }
  console.warn(searchNeedMes)
  searchNeedMes.currentPage = page
  searchNeedMes.pageSize = pageSize
  getTemplatePages(searchNeedMes)
    .then(res => {
      templateTable.length = 0
      if (res.data.records.length > 0) {
        total.value = res.data.total
        res.data.records.forEach((item: any) => {
          item.channelType = JSON.parse(item.pushWays).channelType
          item.messageType = JSON.parse(item.pushWays).messageType
          item.createTime = getDate(item.createTime)
          // eslint-disable-next-line
          item.templateStatus = item.templateStatus === 1 ? true : false
          item.key = item.templateId
          const i = item
          templateTable.push(i)
        })
        if (opt === 1) {
          void message.success('查询成功~ (*^▽^*)')
        }
      } else {
        if (opt === 1) {
          void message.success('未查询到任何数据   ≧ ﹏ ≦')
        }
      }
      console.warn('查询数据', templateTable)
      searchform.value.iconLoading = false
    })
    .catch(err => {
      searchform.value.iconLoading = false
      void message.error('查询失败，请检查网络~ (＞︿＜)')
      console.error('An error occurred:', err)
    })
}

onMounted(() => {
  getTemplatePages(searchItem)
    .then(res => {
      if (res.data.records.length > 0) {
        total.value = res.data.total
        res.data.records.forEach((item: any) => {
          item.channelType = JSON.parse(item.pushWays).channelType
          item.messageType = JSON.parse(item.pushWays).messageType
          item.createTime = getDate(item.createTime)
          // eslint-disable-next-line
          item.templateStatus = item.templateStatus === 1 ? true : false
          item.key = item.templateId
          templateTable.push(item)
        })
        console.warn('初始化数据', templateTable)
      }
    })
    .catch(err => {
      console.error('An error occurred:', err)
    })
})

const a = computed(() => {
  return store.getCollapse() ? 80 : 200 // 计算输入框应该有的高度
})
</script>

<template>
  <!-- 搜索部分 -->
  <searchForm ref="searchform" @mes="searchTemplate({ opt: 1 })" />
  <!-- 表格部分 -->
  <div id="message-container" :style="{ height: hasSelected ? '100%' : 'auto' }">
    <div class="message-section">
      <div class="splitter">
        <a-tooltip title="刷新">
          <a-button shape="circle" :icon="h(ReloadOutlined)" @click="searchTemplate({ opt: 1 })" />
        </a-tooltip>
        <addTemplate ref="addtemplate" @add="saveTemplate()" />
      </div>

      <div class="describe" v-if="hasSelected">
        <template v-if="hasSelected">
          <span class="count">
            {{ `已选择 ${state.selectedRowKeys.length} 项` }}
          </span>
          <a-button type="link" class="cancel" @click="cancelSelect">取消选择</a-button>
        </template>
      </div>
      <!-- 表格部分 -->
      <a-table
        :columns="columns"
        :data-source="templateTable"
        :scroll="{ x: 1200, y: undefined, scrollToFirstRowOnChange: true }"
        class="components-table-demo-nested"
        @expand="getInnerData"
        :expandIconColumnIndex="-1"
        :expandIconAsCell="false"
        :pagination="false"
        :expandedRowKeys="expandedRowKeys"
        :row-selection="{ selectedRowKeys: state.selectedRowKeys, onChange: onSelectChange }"
      >
        >
        <template #bodyCell="{ column, record }">
          <template v-if="column.key === 'templateStatus'">
            <span>
              <a-switch
                v-model:checked="record.templateStatus"
                checked-children="启用"
                un-checked-children="禁用"
                @change="changeStatus(record.templateId, record.templateStatus)"
              />
            </span>
          </template>
          <template v-if="column.key === 'operation'">
            <a-button
              type="link"
              size="small"
              style="font-size: 14px"
              @click="getInnerData(false, record)"
              v-if="judgeInclude(record)"
            >
              <UpOutlined />
              收起
            </a-button>
            <a-button
              type="link"
              size="small"
              style="font-size: 14px"
              @click="getInnerData(true, record)"
              v-if="!judgeInclude(record)"
              ><DownOutlined />展开</a-button
            >
            <a-button type="link" class="btn-manager" size="small" style="font-size: 14px" @click="startModify(record)">
              编辑
            </a-button>
            <modifyTemplate ref="modifytemplate" :mod="record" />
            <a-button type="link" class="btn-manager" size="small" style="font-size: 14px; margin-left: -5px">
              测试发送
            </a-button>
            <a-popconfirm title="确认删除吗?" @confirm="onDelete(record.templateId)" ok-text="确定" cancel-text="取消">
              <a-button type="link" danger size="small" style="font-size: 14px; margin-left: -5px">删除</a-button>
            </a-popconfirm>
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
        :show-total="total => `共 ${total} 条数据`"
      />
    </div>
    <!-- 对表格的操作 -->
    <div class="showDelete" :style="{ width: `calc(100% - ${a}px)` }" v-if="hasSelected">
      <div class="box">{{ `已选择 ${state.selectedRowKeys.length} 项` }}</div>
      <div class="del">
        <a-button type="primary" style="font-size: 14px" :loading="state.loading" @click="showDeleteConfirm">
          批量删除
        </a-button>
        <contextHolder />
      </div>
    </div>
  </div>
</template>

<style lang="scss" scoped>
#message-container {
  overflow: auto;
}

.splitter {
  width: 100%;
  height: 60px;
  display: flex;
  align-items: center;
  justify-content: right;
  margin-bottom: 6px;
}

.message-section {
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

  .describe {
    width: 100%;
    background-color: rgb(248, 248, 248);
    height: 40px;
    margin-bottom: 20px;
    line-height: 40px;
    border-radius: 10px;
    .count {
      color: gray;
      margin-left: 30px;
    }
    .cancel {
      position: absolute;
      right: 50px;
      padding-top: 7px;
    }
  }
}

#message-container {
  position: relative;
  width: 100%;
  // height: 100%;
  .showDelete {
    position: fixed; /* 将showDelete盒子设置为固定定位 */
    inset-inline-end: 0;
    bottom: 0; /* 将showDelete盒子的底部与页面底部对齐 */
    right: 0; /* 将showDelete盒子的左侧与页面左侧对齐 */
    background-color: rgba(255, 255, 255, 0.9);
    display: flex;
    align-items: center;
    padding: 10px;
    height: 60px;
    z-index: 999;
    box-sizing: border-box; /* 确保padding不会撑开盒子 */
    transition: 0.2s;
    line-height: 40px;
    .box {
      width: 15%;
      height: 100%;
      margin-left: 2%;
    }
    .del {
      margin-left: 75%;
    }
  }
}
</style>

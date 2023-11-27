<script lang="ts" setup>
import { ReloadOutlined, ExclamationCircleOutlined } from '@ant-design/icons-vue'
import { ref, reactive, h, onMounted, computed } from 'vue'
import type { UnwrapRef } from 'vue'
import type { TableColumnsType } from 'ant-design-vue'
import { message, Modal } from 'ant-design-vue'
import type { messageTemplate, searchMessage } from './type'
import searchForm from './components/searchForm.vue'
import addTemplate from './components/addTemplate.vue'
import modifyTemplate from './components/modifyTemplate.vue'
import sendTest from './components/sendTest.vue'
import {
	addTemplatePages,
	deleteTemplate,
	getTemplatePages,
	updateStatus,
	updatetemplate
} from '@/api/message'
import {
	changeTable,
	getPushRange,
	getUsersType,
	getMessageTypeArr,
	getAllMessage
} from '@/utils/date'
import { useStore } from '@/store'
import { getPushWays } from '@/utils/date'
import {
	CopyTwoTone,
	EditTwoTone,
	DeleteTwoTone,
	DownCircleTwoTone,
	UpCircleTwoTone,
	SettingOutlined,
	ThunderboltOutlined
} from '@ant-design/icons-vue'
/**
 * 表格初始化
 */
const templateTable: UnwrapRef<messageTemplate[]> = reactive([])
// 表格数据
const columns: TableColumnsType = [
	{
		title: 'TemplateId',
		dataIndex: 'templateId',
		key: 'templateId',
		width: 106
	},
	{
		title: '模板名',
		dataIndex: 'templateName',
		key: 'templateName'
	},
	{
		title: '消息类型',
		dataIndex: 'messageType',
		key: 'messageType'
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
		title: '渠道类型',
		dataIndex: 'channelType',
		key: 'channelType'
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
	// eslint-disable-next-line
	const { channelType, messageType, ...rest } = addtemplate.value.templateItem
	const savetemplate = { ...rest }
	addtemplate.value.iconLoading = true
	addTemplatePages(savetemplate)
		.then((res) => {
			if (res.code === 200) {
				addtemplate.value.open = false
				void message.success('新增成功~ (*^▽^*)')
				searchTemplate({ page: 1, pageSize: pageSize.value, opt: 2 }) // 更新表单
			}
			addtemplate.value.iconLoading = false
		})
		.catch((err) => {
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
		.then((res) => {
			if (res.code === 200) {
				void message.success('删除成功~ (*^▽^*)')
				searchTemplate({ page: 1, pageSize: pageSize.value, opt: 2 })
				cancelSelect()
			}
			state.loading = false
		})
		.catch((err) => {
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
		.then((res) => {
			if (res.code === 200) {
				void message.success('删除成功~ (*^▽^*)')
				searchTemplate({ page: 1, pageSize: pageSize.value, opt: 2 })
			}
			state.loading = false
		})
		.catch((err) => {
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
const changeStatus = (id: number, status: number): void => {
	// eslint-disable-next-line
	const obj = {
		templateId: id,
		templateStatus: status
	}
	updateStatus(obj)
		.then((res) => {
			if (res.code === 200) {
				void message.success('修改成功~ (*^▽^*)')
			}
		})
		.catch((err) => {
			void message.error('查询失败，请检查网络~ (＞︿＜)')
			console.error('An error occurred:', err)
		})
}

const updateTemplate = (): void => {
	modifytemplate.value.updateiconLoading = true
	// 处理templateItem的pushways
	modifytemplate.value.updateTemp.pushWays = getPushWays(
		modifytemplate.value.updateTemp.channelType as string,
		modifytemplate.value.updateTemp.messageType
	)

	const m = modifytemplate.value
	// eslint-disable-next-line
	const { messageType, channelType, ...rest } = m.updateTemp
	// 浅拷贝
	const obj = { ...rest }
	updatetemplate(obj)
		.then((res) => {
			console.log(res)
			if (res.code === 200) {
				modifytemplate.value.updateiconLoading = false
				modifytemplate.value.openModify = false
				message.success('修改成功~ (*^▽^*)')
				searchTemplate({ page: current.value, pageSize: pageSize.value, opt: 2 }) // 更新表单
			} else {
				modifytemplate.value.updateiconLoading = false
			}
		})
		.catch((err) => {
			modifytemplate.value.updateiconLoading = false
			message.error('修改失败，请检查网络~ (＞︿＜)')
			console.error('An error occurred:', err)
		})
}

// 传递相关数据，初始化data
const startModify = (record): void => {
	modifytemplate.value.openModify = true
	getAllMessage(modifytemplate.value, record)
}

/// 查询操作

// 表格加载中标志
const tableLoadFlag = ref<boolean>(true)

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

const current = ref(1)
const pageSize = ref(10)

const change = (page: number, pageSize: number): void => {
	searchTemplate({ page, pageSize, opt: 1 })
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
	// eslint-disable-next-line
	const { perid, ...rest } = searchform.value.searchPage
	const searchNeedMes = { ...rest }
	searchNeedMes.currentPage = page
	searchNeedMes.pageSize = pageSize
	tableLoadFlag.value = true
	getTemplatePages(searchNeedMes)
		.then((res) => {
			templateTable.length = 0
			total.value = res.data.total
			current.value = page
			if (res.data.records.length > 0) {
				res.data.records.forEach((item: any) => {
					item = changeTable(item)
					templateTable.push(item)
				})
				if (opt === 1) {
					void message.success('查询成功~ (*^▽^*)')
				}
			} else {
				if (opt === 1) {
					void message.success('未查询到任何数据   ≧ ﹏ ≦')
				}
			}
			tableLoadFlag.value = false
			searchform.value.iconLoading = false
		})
		.catch((err) => {
			searchform.value.iconLoading = false
			void message.error('查询失败，请检查网络~ (＞︿＜)')
			console.error('An error occurred:', err)
		})
}
// copy 文件 Key
const copyTemplateId = (fileKey: string): void => {
	navigator.clipboard.writeText(fileKey)
	message.success('复制成功')
}
onMounted(() => {
	getTemplatePages(searchItem)
		.then((res) => {
			total.value = res.data.total
			if (res.data.records.length > 0) {
				res.data.records.forEach((item: any) => {
					item = changeTable(item)
					templateTable.push(item)
				})
			}
			tableLoadFlag.value = false
		})
		.catch((err) => {
			console.error('An error occurred:', err)
		})
})

const a = computed(() => {
	return store.getCollapse() ? 80 : 200 // 计算输入框应该有的高度
})
</script>

<template>
	<!-- 搜索部分 -->
	<searchForm ref="searchform" @mes="searchTemplate({ page: 1, pageSize, opt: 1 })" />
	<!-- 表格部分 -->
	<div id="message-container" :style="{ height: hasSelected ? '100%' : 'auto' }">
		<div class="message-section">
			<div class="splitter">
				<a-tooltip title="刷新">
					<a-button
						shape="circle"
						:icon="h(ReloadOutlined)"
						@click="searchTemplate({ page: 1, pageSize, opt: 2 })" />
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
				:loading="tableLoadFlag"
				:expandIconColumnIndex="-1"
				:expandIconAsCell="false"
				:pagination="false"
				:expandedRowKeys="expandedRowKeys"
				:row-selection="{ selectedRowKeys: state.selectedRowKeys, onChange: onSelectChange }">
				>
				<template #headerCell="{ column }">
					<template v-if="column.key === 'templateId'">
						<span>
							<ThunderboltOutlined />
							模版 ID
						</span>
					</template>
					<template v-if="column.key === 'operation'">
						<span>
							<SettingOutlined />
							操作
						</span>
					</template>
				</template>
				<template #bodyCell="{ column, record }">
					<!-- 表格数据渲染 -->
					<template v-if="column.key === 'templateId'">
						<span style="font-weight: bold">
							{{ record.templateId }}
							<a-tooltip :color="'blue'">
								<template #title>复制 TemplateId 发送消息吧~</template>
								<CopyTwoTone @click="copyTemplateId(record.templateId)" />
							</a-tooltip>
						</span>
					</template>
					<template v-if="column.key === 'pushRange'">
						<a-tag
							color="green"
							v-if="record.pushRange == 0"
							style="width: 70px; text-align: center">
							<span>{{ getPushRange(Number(record.pushRange)) }}</span>
						</a-tag>
						<a-tag
							color="blue"
							v-if="record.pushRange == 1"
							style="width: 70px; text-align: center">
							<span>{{ getPushRange(Number(record.pushRange)) }}</span>
						</a-tag>
						<a-tag
							color="purple"
							v-if="record.pushRange == 2"
							style="width: 70px; text-align: center">
							<span>{{ getPushRange(Number(record.pushRange)) }}</span>
						</a-tag>
					</template>
					<template v-if="column.key === 'usersType'">
						<a-tag
							color="cyan"
							v-if="record.usersType == 1"
							style="width: 80px; text-align: center">
							<span>{{ getUsersType(Number(record.usersType)) }}</span>
						</a-tag>
						<a-tag
							color="orange"
							v-if="record.usersType == 2"
							style="width: 80px; text-align: center">
							<span>{{ getUsersType(Number(record.usersType)) }}</span>
						</a-tag>
						<a-tag
							color="pink"
							v-if="record.usersType == 3"
							style="width: 80px; text-align: center">
							<span>{{ getUsersType(Number(record.usersType)) }}</span>
						</a-tag>
						<a-tag color="red" v-if="record.usersType == 4" style="width: 80px; text-align: center">
							<span>{{ getUsersType(Number(record.usersType)) }}</span>
						</a-tag>
					</template>
					<template v-if="column.key === 'channelType'">
						<span>
							<!-- 根据 appType 的值显示不同的图片 -->
							<img
								style="height: 30px; width: 30px"
								v-if="record.channelType === 1"
								src="电话.png"
								alt="电话" />
							<img
								style="height: 30px; width: 30px"
								v-else-if="record.channelType === 2"
								src="短信.png"
								alt="短信" />
							<img
								style="height: 30px; width: 30px"
								v-else-if="record.channelType === 3"
								src="邮件.png"
								alt="邮件" />
							<img
								style="height: 30px; width: 30px"
								v-else-if="record.channelType === 4"
								src="钉钉.png"
								alt="钉钉" />
							<img
								style="height: 30px; width: 30px"
								v-else-if="record.channelType === 5"
								src="企业微信.png"
								alt="企业微信" />
							<img
								style="height: 30px; width: 30px"
								v-else-if="record.channelType === 6"
								src="飞书.png"
								alt="飞书" />
							<!-- 添加更多条件根据需要显示不同的图片 -->
						</span>
					</template>
					<template v-if="column.key === 'messageType'">
						<span style="color: #1677ff">{{ getMessageTypeArr(record.messageType) }}</span>
					</template>
					<template v-if="column.key === 'templateStatus'">
						<span>
							<a-switch
								v-model:checked="record.templateStatus"
								checked-children="启用"
								un-checked-children="禁用"
								:checkedValue="1"
								:unCheckedValue="0"
								@click="changeStatus(record.templateId, record.templateStatus)" />
						</span>
					</template>
					<template v-if="column.key === 'operation'">
						<a-button
							type="link"
							size="small"
							style="font-size: 14px"
							@click="getInnerData(false, record)"
							v-if="judgeInclude(record)">
							<UpCircleTwoTone style="font-size: 18px" />
						</a-button>
						<a-tooltip v-if="!judgeInclude(record)">
							<template #title>查看消息模版更多信息</template>
							<a-button
								type="link"
								size="small"
								style="font-size: 14px"
								@click="getInnerData(true, record)"
								v-if="!judgeInclude(record)">
								<DownCircleTwoTone style="font-size: 18px" />
							</a-button>
						</a-tooltip>

						<a-divider type="vertical" />
						<a-tooltip>
							<template #title>修改消息模版</template>
							<a-button
								type="link"
								class="btn-manager"
								size="small"
								style="font-size: 14px"
								@click="startModify(record)">
								<EditTwoTone two-tone-color="#1677FF" style="font-size: 18px" />
							</a-button>
						</a-tooltip>
						<modifyTemplate ref="modifytemplate" :mod="record" @update="updateTemplate()" />
						<a-divider type="vertical" />
						<sendTest
							:test="record.templateId"
							:message-type="record.messageType"
							:channel-type="record.channelType" />
						<a-divider type="vertical" />
						<a-popconfirm
							title="确认删除吗?"
							@confirm="onDelete(record.templateId)"
							ok-text="确定"
							cancel-text="取消">
							<a-tooltip placement="bottom">
								<template #title>删除消息模版</template>
								<a-button
									type="link"
									danger
									size="small"
									style="font-size: 14px; margin-left: -5px">
									<DeleteTwoTone two-tone-color="red" style="font-size: 18px" />
								</a-button>
							</a-tooltip>
						</a-popconfirm>
					</template>
				</template>
				<template #expandedRowRender="{ record }">
					<a-descriptions :column="4">
						<a-descriptions-item label="模板累计使用数" :span="2">
							<a-tag color="red">
								<span>{{ record.useCount }}</span>
							</a-tag>
						</a-descriptions-item>
						<a-descriptions-item label="关联 AppId">
							<strong>{{ record.appId }}</strong>
						</a-descriptions-item>
						<a-descriptions-item label="关联 AppName">
							{{ record.appName }}
						</a-descriptions-item>
						<a-descriptions-item label="创建者">{{ record.createUser }}</a-descriptions-item>
						<a-descriptions-item label="创建时间">{{ record.createTime }}</a-descriptions-item>
					</a-descriptions>
				</template>
			</a-table>
			<a-pagination
				v-model:current="current"
				v-model:pageSize="pageSize"
				class="pagination"
				show-quick-jumper
				:total="total"
				@change="change"
				showSizeChanger
				:locale="locale"
				:show-total="(total) => `共 ${total} 条数据`" />
		</div>
		<!-- 对表格的操作 -->
		<div class="showDelete" :style="{ width: `calc(100% - ${a}px)` }" v-if="hasSelected">
			<div class="box">{{ `已选择 ${state.selectedRowKeys.length} 项` }}</div>
			<div class="del">
				<a-button
					type="primary"
					style="font-size: 14px"
					:loading="state.loading"
					@click="showDeleteConfirm">
					批量删除
				</a-button>
				<contextHolder />
			</div>
		</div>
	</div>
</template>

<style lang="scss" scoped>
#message-container {
	position: relative;
	width: 100%;
	overflow: auto;
	// height: 100%;

	.box {
		width: 15%;
		height: 100%;
		margin-left: 2%;
	}

	.del {
		margin-left: 75%;
	}

	.splitter {
		display: flex;
		align-items: center;
		justify-content: right;
		width: 100%;
		height: 60px;
		margin-bottom: 6px;
	}

	.message-section {
		padding: 12px;
		margin-top: 12px;
		background: rgb(255, 255, 255);
		border-radius: 6px;

		.btn-manager {
			margin-right: 10px;
		}

		.pagination {
			display: flex;
			justify-content: right;
			margin-top: 20px;
		}

		.describe {
			width: 100%;
			height: 40px;
			margin-bottom: 20px;
			line-height: 40px;
			background-color: rgb(248, 248, 248);
			border-radius: 10px;

			.count {
				margin-left: 30px;
				color: gray;
			}

			.cancel {
				position: absolute;
				right: 50px;
				padding-top: 7px;
			}
		}
	}

	.showDelete {
		position: fixed; /* 将showDelete盒子设置为固定定位 */
		right: 0; /* 将showDelete盒子的左侧与页面左侧对齐 */
		bottom: 0; /* 将showDelete盒子的底部与页面底部对齐 */
		z-index: 999;
		box-sizing: border-box; /* 确保padding不会撑开盒子 */
		display: flex;
		align-items: center;
		height: 60px;
		padding: 10px;
		line-height: 40px;
		background-color: rgb(255 255 255 / 90%);
		transition: 0.2s;
		inset-inline-end: 0;
	}
}

.between {
	display: flex;
	justify-content: space-between;
	padding: 0;
	margin: 0;
}
</style>

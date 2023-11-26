<script lang="ts" setup>
import {
	ReloadOutlined,
	EditTwoTone,
	DeleteTwoTone,
	DownCircleTwoTone,
	UpCircleTwoTone,
	ExclamationCircleOutlined
} from '@ant-design/icons-vue'
import { ref, reactive, h, onMounted, computed } from 'vue'
import type { UnwrapRef } from 'vue'
import type { TableColumnsType } from 'ant-design-vue'
import { message, Modal } from 'ant-design-vue'
import type { appTemplate, searchMessage, updateTemp } from './type'
import searchForm from './components/searchForm.vue'
import addTemplate from './components/addApp.vue'
import { getDate } from '@/utils/date'
import { useStore } from '@/store'
import {
	addAppItem,
	getAppInfo,
	deleteAppInfo,
	updateAppStatus,
	updateAppItem
} from '@/api/channel'
import JsonEditorVue from 'json-editor-vue3'
import type { Rule } from 'ant-design-vue/es/form'
/**
 * 表格初始化
 */
const templateTable: UnwrapRef<appTemplate[]> = reactive([])

const open = ref<boolean>(false)

const labelCol = { style: { width: '80px' } }
const wrapperCol = { span: 36 }

const updateDate = ref<updateTemp>({})

const jsonstr = ref<string>('{}')

const jsonobj = ref<object>(JSON.parse(jsonstr.value))

const update = (appData): void => {
	open.value = true
	jsonstr.value = appData.appConfig ?? '{}'
	jsonobj.value = JSON.parse(jsonstr.value)
	updateDate.value = JSON.parse(JSON.stringify(appData))
}

interface Channel {
	value: number
	label: string
}

const channelData = ref<Channel[]>([])
channelData.value = [
	{ value: 1, label: '电话' },
	{ value: 2, label: '短信' },
	{ value: 3, label: '邮件' },
	{ value: 4, label: '钉钉' },
	{ value: 5, label: '企业微信' },
	{ value: 6, label: '飞书' }
]

// 表格数据
const columns: TableColumnsType = [
	{
		title: 'AppId',
		dataIndex: 'appId',
		key: 'appId'
	},
	{
		title: 'APP 名称',
		dataIndex: 'appName',
		key: 'appName'
	},
	{
		title: '渠道类型',
		dataIndex: 'channelType',
		key: 'channelType'
	},
	{
		title: '累计使用次数',
		dataIndex: 'useCount',
		key: 'useCount'
	},
	{
		title: 'APP 状态',
		dataIndex: 'appStatus',
		key: 'appStatus'
	},
	{
		title: '操作',
		key: 'operation',
		fixed: 'right',
		width: 200
	}
]

/**
 * 渲染 data
 */

const innertemplatedata: UnwrapRef<appTemplate[]> = reactive([])

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

/// 新增操作
const saveApp = (): void => {
	const saveApp = addtemplate.value.templateItem
	addAppItem(saveApp)
		.then((res) => {
			if (res.code === 200) {
				void message.success('新增成功~ (*^▽^*)')
				addtemplate.value.open = false
				searchTemplate({ page: 1, pageSize: pageSize.value, opt: 2 }) // 更新表单
				addtemplate.value.iconLoading = false
			}
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
	deleteAppInfo(templates)
		.then((res) => {
			if (res.code === 200) {
				void message.success('删除成功~ (*^▽^*)')
				searchTemplate({ page: 1, pageSize: pageSize.value, opt: 2 }) //
			}
			state.loading = false
		})
		.catch((err) => {
			void message.error('删除失败，请检查网络~ (＞︿＜)')
			console.error('An error occurred:', err)
			state.loading = false
		})
	state.selectedRowKeys.length = 0
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
	deleteAppInfo(templates)
		.then((res) => {
			if (res.code === 200) {
				void message.success('删除成功~ (*^▽^*)')
				searchTemplate({ page: 1, pageSize: pageSize.value, opt: 2 }) //
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

/// 查询操作
const searchItem: searchMessage = reactive({
	appName: undefined,
	channelType: undefined,
	currentPage: 1,
	pageSize: 10,
	startTime: undefined,
	endTime: undefined
})
// 表格加载中标志
const tableLoadFlag = ref<boolean>(true)
/// 分页参数
const total = ref()

const current = ref()

const pageSize = ref()

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
	tableLoadFlag.value = true
	// 对象解构
	const { perid, ...rest } = searchform.value.searchPage
	console.log(perid)
	const searchNeedMes = { ...rest }
	searchNeedMes.currentPage = page
	searchNeedMes.pageSize = pageSize
	getAppInfo(searchNeedMes)
		.then((res) => {
			templateTable.length = 0
			current.value = page
			tableLoadFlag.value = false
			if (res.data.records.length > 0) {
				total.value = res.data.total
				res.data.records.forEach((item: any) => {
					item.createTime = getDate(item.createTime)
					item.key = item.appId
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
			searchform.value.iconLoading = false
		})
		.catch((err) => {
			searchform.value.iconLoading = false
			void message.error('查询失败，请检查网络~ (＞︿＜)')
			console.error('An error occurred:', err)
		})
}

/// 修改操作
const changeStatus = (id: number, status: number): void => {
	// eslint-disable-next-line
	const obj = {
		appId: id,
		appStatus: status
	}
	updateAppStatus(obj)
		.then((res) => {
			if (res.code === 200) {
				message.success('修改成功~ (*^▽^*)')
			}
		})
		.catch((err) => {
			void message.error('修改失败，请检查网络~ (＞︿＜)')
			console.error('An error occurred:', err)
		})
}
/// 修改 APP
const templateForm = ref()

interface DelayLoading {
	delay: number
}
const iconLoading = ref<boolean | DelayLoading>(false)

const handleOk = (): void => {
	// 异步关闭，先添加，渲染成功后关闭
	templateForm.value
		.validate()
		.then(() => {
			updateDate.value.appConfig = JSON.stringify(jsonobj.value)
			updateAppItem(updateDate.value)
				.then((res) => {
					if (res.code === 200) {
						void message.success('修改成功~ (*^▽^*)')
						searchTemplate({ page: current.value, pageSize: pageSize.value, opt: 2 }) // 更新表单
					}
				})
				.catch((err) => {
					void message.error('修改失败，请检查网络~ (＞︿＜)')
					console.error('An error occurred:', err)
				})
			handleCancel()
		})
		.catch((error) => {
			console.log('error', error)
		})
}

const handleCancel = (): void => {
	// userdisabled.value = userdisabled.value.map(item => ({ ...item, disabled: true }))
	updateDate.value.channelType = undefined
	updateDate.value.appName = ''
	updateDate.value.appStatus = 1
	updateDate.value.appConfig = ''
	open.value = false
}
const jsonChange = () => {
	templateForm.value?.validate('appConfig').then(() => {})
}

const appConfigValidate = async (): Promise<any> => {
	const newjsonstr = JSON.stringify(jsonobj.value)
	if (newjsonstr == '{}') {
		throw new Error('请正确输入 APP 配置')
	}
	await Promise.resolve()
}

const rules: Record<string, Rule[]> = {
	appName: [
		{ required: true, message: '请输入 APP 名', trigger: 'change' },
		{ min: 3, max: 20, message: '长度在 3 到 20 个字符', trigger: 'blur' }
	],
	channelType: [
		{
			required: true,
			message: '请选择渠道',
			trigger: 'change'
		}
	],
	appConfig: [{ required: true, validator: appConfigValidate, trigger: 'change' }],
	appStatus: [{ required: true, message: '请选择 APP 状态', trigger: 'change' }]
}

const options = ref({
	search: false,
	history: false
})
const modeList = ref(['code']) // 可选模式

onMounted(() => {
	getAppInfo(searchItem)
		.then((res) => {
			total.value = res.data.total
			tableLoadFlag.value = false
			if (res.data.records.length > 0) {
				res.data.records.forEach((item: any) => {
					item.createTime = getDate(item.createTime)
					item.key = item.appId
					templateTable.push(item)
				})
			}
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
						@click="searchTemplate({ page: 1, pageSize, opt: 1 })" />
				</a-tooltip>
				<addTemplate ref="addtemplate" @add="saveApp()" />
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
				:loading="tableLoadFlag"
				:row-selection="{ selectedRowKeys: state.selectedRowKeys, onChange: onSelectChange }">
				>

				<template #bodyCell="{ column, record }">
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
					<template v-if="column.key === 'appStatus'">
						<span>
							<a-switch
								v-model:checked="record.appStatus"
								checked-children="启用"
								un-checked-children="禁用"
								:checkedValue="1"
								:unCheckedValue="0"
								@change="changeStatus(record.appId, record.appStatus)" />
						</span>
					</template>
					<template v-if="column.key === 'useCount'">
						<a-tag color="red">
							{{ record.useCount }}
						</a-tag>
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
						<a-tooltip>
							<template v-if="!judgeInclude(record)" #title>查看 APP 更多信息</template>
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
							<template #title>修改 APP</template>
							<a-button
								type="link"
								class="btn-manager"
								size="small"
								style="font-size: 14px"
								@click="update(record)">
								<EditTwoTone two-tone-color="#1677FF" style="font-size: 18px" />
							</a-button>
						</a-tooltip>
						<a-divider type="vertical" />
						<a-popconfirm
							title="确认删除吗?"
							@confirm="onDelete(record.appId)"
							ok-text="确定"
							cancel-text="取消">
							<a-tooltip placement="bottom">
								<template #title>删除 APP</template>
								<a-button
									type="link"
									danger
									size="small"
									style="font-size: 14px; margin-left: -5px">
									<DeleteTwoTone two-tone-color="red" />
								</a-button>
							</a-tooltip>
						</a-popconfirm>
					</template>
				</template>
				<template #expandedRowRender="{ record }">
					<a-row :gutter="[16, 16]">
						<a-col :span="24">
							APP 配置：
							<Code type="json" :code="record.appConfig"></Code>
						</a-col>
						<a-col :span="6">创建者：{{ record.createUser }}</a-col>
						<a-col :span="6">创建时间：{{ record.createTime }}</a-col>
					</a-row>
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
	<a-modal
		v-model:open="open"
		title="修改 APP "
		width="650px"
		:footer="null"
		@cancel="handleCancel">
		<a-form
			ref="templateForm"
			:model="updateDate"
			:label-col="labelCol"
			:wrapper-col="wrapperCol"
			class="temform"
			:rules="rules">
			<a-form-item ref="appName" label="APP 名称" name="appName" class="tem-item">
				<a-input
					:maxlength="20"
					v-model:value="updateDate.appName"
					placeholder="请填写长度在 3 到 20 个字符的 APP 名"
					style="width: 70%" />
			</a-form-item>

			<a-form-item label="渠道选择" name="channelType" class="tem-item">
				<a-select
					v-model:value="updateDate.channelType"
					:options="channelData.map((pro) => ({ value: pro.value, label: pro.label }))"
					style="width: 70%" />
			</a-form-item>
			<a-form-item label="APP 配置" name="appConfig" class="tem-item" :wrapper-col="{ span: 16 }">
				<json-editor-vue
					class="editor"
					v-model="jsonobj"
					currentMode="code"
					:modeList="modeList"
					:options="options"
					@change="jsonChange"
					language="cn" />
			</a-form-item>
			<a-form-item label="APP 状态" name="appStatus" class="tem-item">
				<a-switch
					v-model:checked="updateDate.appStatus"
					checked-children="启用"
					un-checked-children="禁用"
					:checkedValue="1"
					:unCheckedValue="0" />
			</a-form-item>
			<a-form-item :wrapper-col="{ span: 20, offset: 17 }" class="tem-item">
				<div class="between">
					<a-button @click="handleCancel">取消</a-button>
					<a-button type="primary" @click="handleOk" :loading="iconLoading">确认修改</a-button>
				</div>
			</a-form-item>
		</a-form>
	</a-modal>
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
		background: #fff;
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
			background-color: rgb(248 248 248);
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
	margin-right: 16px;
}
</style>

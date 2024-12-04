<script lang="ts" setup>
import { ReloadOutlined, EditTwoTone, CopyOutlined } from '@ant-design/icons-vue'
import { ref, reactive, h, onMounted, computed } from 'vue'
import type { UnwrapRef } from 'vue'
import { message } from 'ant-design-vue'
import type { AppInterface, searchMessage, updateTemp } from './type'
import searchForm from './components/searchForm.vue'
import addTemplate from './components/addApp.vue'
import { getDate } from '@/utils/date.ts'
import {
	addAppItem,
	getAppInfo,
	deleteAppInfo,
	updateAppStatus,
	updateAppItem,
	getAppConfigByChannelType,
} from '@/api/app.ts'
import JsonEditorVue from 'json-editor-vue3'
import type { Rule } from 'ant-design-vue/es/form'

/**
 * 表格初始化
 */
const appTable: UnwrapRef<AppInterface[]> = reactive([])

const open = ref<boolean>(false)

const labelCol = { style: { width: '80px' } }
const wrapperCol = { span: 36 }

const updateDate = ref<updateTemp>({})

const jsonstr = ref<string>('{}')

const jsonobj = ref<object>(JSON.parse(jsonstr.value))

const update = (appData): void => {
	open.value = true
	jsonobj.value = JSON.parse(appData.appConfig)
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
	{ value: 6, label: '飞书' },
]

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
		.then(() => {
			void message.success('新增成功~ (*^▽^*)')
			addtemplate.value.open = false
			searchApp({ page: 1, pageSize: pageSize.value, opt: 2 }) // 更新表单
			addtemplate.value.iconLoading = false
		})
		.catch((err) => {
			addtemplate.value.iconLoading = false
			void message.error('新增失败，' + err + '~ (＞︿＜)')
			console.error('An error occurred:', err)
		})
}

/// 删除操作
type Key = string | number

const state = reactive<{
	selectedRowKeys: Key[]
	loading: boolean
}>({
	selectedRowKeys: [],
	loading: false,
})

const hasSelected = computed(() => state.selectedRowKeys.length > 0)

const onDelete = (id: number): void => {
	const arr: number[] = []
	arr.push(id)
	const templates = {
		ids: arr,
	}
	deleteAppInfo(templates)
		.then((res) => {
			if (res.code === 200) {
				void message.success('删除成功~ (*^▽^*)')
				searchApp({ page: 1, pageSize: pageSize.value, opt: 2 }) //
			}
			state.loading = false
		})
		.catch((err) => {
			console.log(err)
			void message.error('删除失败，' + err + '~ (＞︿＜)')
			console.error('An error occurred:', err)
			state.loading = false
		})
}

/// 查询操作
const searchItem: searchMessage = reactive({
	appName: undefined,
	channelType: undefined,
	currentPage: 1,
	pageSize: 10,
	startTime: undefined,
	endTime: undefined,
})
// 表格加载中标志
const tableLoadFlag = ref<boolean>(true)
/// 分页参数
const total = ref()

const current = ref(1)

const pageSize = ref(12)

const change = (page: number, pageSize: number): void => {
	searchApp({ page, pageSize, opt: 1 })
}

const locale = {
	items_per_page: '条/页', // 每页显示条数的文字描述
	jump_to: '跳至', // 跳转到某页的文字描述
	page: '页', // 页的文字描述
	prev_page: '上一页', // 上一页按钮文字描述
	next_page: '下一页', // 下一页按钮文字描述
}

// 条件查询
const searchApp = ({ page, pageSize, opt }: SearchOptions = {}): void => {
	tableLoadFlag.value = true
	// 对象解构
	const { perid, ...rest } = searchform.value.searchPage
	console.log(perid)
	const searchNeedMes = { ...rest }
	searchNeedMes.currentPage = page
	searchNeedMes.pageSize = pageSize
	getAppInfo(searchNeedMes)
		.then((res) => {
			appTable.length = 0
			current.value = page
			tableLoadFlag.value = false
			if (res.data.records.length > 0) {
				res.data.records.forEach((item: any) => {
					item.createTime = getDate(item.createTime)
					item.key = item.appId
					const i = item
					appTable.push(i)
				})
				if (opt === 1) {
					void message.success('查询成功~ (*^▽^*)')
				}
			} else {
				if (opt === 1) {
					void message.success('未查询到任何数据   ≧ ﹏ ≦')
				}
			}
			total.value = res.data.total
			searchform.value.iconLoading = false
		})
		.catch((err) => {
			searchform.value.iconLoading = false
			void message.error('查询失败，' + err + '~ (＞︿＜)')
			console.error('An error occurred:', err)
		})
}

/// 修改操作
const changeStatus = (id: number, status: number): void => {
	const obj = {
		appId: id,
		appStatus: status,
	}
	updateAppStatus(obj)
		.then(() => {
			message.success('修改成功~ (*^▽^*)')
		})
		.catch((err) => {
			void message.error('修改失败，' + err + '~ (＞︿＜)')
		})
		.finally(() => {
			searchApp({ page: current.value, pageSize: pageSize.value, opt: 2 }) // 更新表单
		})
}

/// 修改 APP
const appForm = ref()

interface DelayLoading {
	delay: number
}

const iconLoading = ref<boolean | DelayLoading>(false)

const handleOk = (): void => {
	// 异步关闭，先添加，渲染成功后关闭
	appForm.value
		.validate()
		.then(() => {
			updateDate.value.appConfig = JSON.stringify(jsonobj.value)
			updateAppItem(updateDate.value)
				.then(() => {
					void message.success('修改成功~ (*^▽^*)')
					handleCancel()
					searchApp({ page: current.value, pageSize: pageSize.value, opt: 2 }) // 更新表单
				})
				.catch((err) => {
					void message.error('修改失败，' + err + '~ (＞︿＜)')
				})
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
	appForm.value?.validate('appConfig').then(() => {})
}
const channelTypeSelect = (value) => {
	getAppConfigByChannelType({ channelType: value }).then((res) => {
		jsonobj.value = JSON.parse(res.data)
		jsonstr.value = JSON.stringify(jsonobj.value)
	})
}
const appConfigValidate = async (): Promise<any> => {
	const newjsonstr = JSON.stringify(jsonobj.value)
	if (jsonstr.value == newjsonstr) {
		throw new Error('请正确输入 APP 配置')
	}
	await Promise.resolve()
}

const rules: Record<string, Rule[]> = {
	appName: [
		{ required: true, message: '请输入 APP 名', trigger: 'change' },
		{ min: 3, max: 20, message: '长度在 3 到 20 个字符', trigger: 'blur' },
	],
	channelType: [
		{
			required: true,
			message: '请选择渠道',
			trigger: 'change',
		},
	],
	appConfig: [{ required: true, validator: appConfigValidate, trigger: 'change' }],
	appStatus: [{ required: true, message: '请选择 APP 状态', trigger: 'change' }],
}

const options = ref({
	search: false,
	history: false,
})
const modeList = ref(['code']) // 可选模式

// 应用图片地址
const imageAddress: string[] = [
	'',
	'/assets/电话.png',
	'/assets/短信.png',
	'/assets/邮件.png',
	'/assets/钉钉.png',
	'/assets/企业微信.png',
	'/assets/飞书.png',
]
// 复制AppId
const copyAppId = async (appId: number) => {
	try {
		await navigator.clipboard.writeText(String(appId))
		message.success('已成功复制应用ID')
	} catch (err) {
		message.error('复制到剪贴板时出现错误：' + err)
	}
}

onMounted(() => {
	getAppInfo(searchItem)
		.then((res) => {
			total.value = res.data.total
			tableLoadFlag.value = false
			if (res.data.records.length > 0) {
				res.data.records.forEach((item: any) => {
					item.createTime = getDate(item.createTime)
					item.key = item.appId
					appTable.push(item)
				})
			}
		})
		.catch((err) => {
			console.error('An error occurred:', err)
		})
})
</script>

<template>
	<!-- 搜索部分 -->
	<searchForm ref="searchform" @mes="searchApp({ page: 1, pageSize, opt: 1 })" />
	<!-- 卡片部分 -->
	<div id="message-container" :style="{ height: hasSelected ? 'calc(100% + 94px)' : 'auto' }">
		<div class="message-section">
			<div class="splitter">
				<a-tooltip title="刷新">
					<a-button shape="circle" :icon="h(ReloadOutlined)" @click="searchApp({ page: 1, pageSize, opt: 1 })" />
				</a-tooltip>
				<addTemplate ref="addtemplate" @add="saveApp()" />
			</div>
			<!-- 卡片部分 -->
			<div v-show="tableLoadFlag" style="text-align: center">
				<a-spin size="large" />
			</div>
			<a-row v-show="!tableLoadFlag" :span="24">
				<a-col :span="6" style="padding-left: 10px; padding-top: 15px" v-for="(record, index) in appTable" :key="index">
					<a-drawer
						v-model:open="record.appInfoOpen"
						title="应用信息"
						width="600px"
						:footer="null"
						:placement="'left'"
						@close="record.showAppConfig = false"
					>
						<div>
							<span style="color: #646a73">应用ID：</span>
							<span>{{ record.appId }}</span>
							&nbsp;
							<a-tooltip title="复制应用ID">
								<CopyOutlined @click.stop="copyAppId(record.appId)" style="color: #1677ff" />
							</a-tooltip>
						</div>
						<div>
							<span style="color: #646a73">应用名：</span>
							<span>{{ record.appName }}</span>
						</div>
						<a-divider />
						<a-descriptions title="APP 配置" :column="1" layout="vertical">
							<a-descriptions-item
								:label="label"
								v-for="(value, label, index) in JSON.parse(record.appConfig)"
								:key="index"
							>
								<div v-show="record.showAppConfig" style="color: #1677ff">{{ value }}</div>
								<div v-show="!record.showAppConfig">{{ '*'.repeat((value + '').length) }}</div>
							</a-descriptions-item>
						</a-descriptions>
						<a v-show="!record.showAppConfig" @click="record.showAppConfig = true">显示</a>
						<a v-show="record.showAppConfig" @click="record.showAppConfig = false">隐藏</a>
						<a-divider />
						<a-statistic title="累计使用量" :value="record.useCount" style="margin-right: 50px" />
						<a-divider />
						<div>
							<span style="color: #646a73">创建时间：</span>
							<span>{{ record.createTime }}</span>
						</div>
					</a-drawer>
					<div
						style="
							border-color: #dee0e3;
							border-style: solid;
							border-width: 1px;
							padding: 15px 15px 8px;
							border-radius: 6px;
						"
					>
						<div>
							<div style="position: relative">
								<span>
									<!-- 根据 appType 的值显示不同的图片 -->
									<img style="height: 48px; width: 48px" :src="imageAddress[record.channelType]" alt="图片" />
									<!-- 添加更多条件根据需要显示不同的图片 -->
								</span>
								<span style="position: absolute; top: 0; margin-left: 10px">
									<strong style="font-size: 15px">{{
										record.appName.length > 10 ? record.appName.substring(0, 10) + '...' : record.appName
									}}</strong>
									<span
										:style="{
											display: 'inline-block',
											height: '8px',
											width: '8px',
											background: record.appStatus == 1 ? 'rgb(10, 191, 91)' : 'rgb(229, 69, 69)',
											borderRadius: '7px',
											verticalAlign: 'middle',
											marginLeft: '10px',
											marginBottom: '5px',
										}"
									></span>
								</span>
								<a-tooltip>
									<template #title>修改 APP</template>
									<a style="font-size: 14px; position: absolute; right: 0" @click="update(record)">
										<EditTwoTone two-tone-color="#1677FF" style="font-size: 18px" />
									</a>
								</a-tooltip>
								<span style="position: absolute; top: 24px; margin-left: 10px">
									<span style="color: #646a73">创建者：</span>
									<span style="color: #1677ff">{{ record.createUser }}</span>
								</span>
							</div>
							<div style="margin-top: 10px">
								<span>
									<span style="color: #646a73">最新动态：</span>
									<span>{{ record.updateTime }}</span>
								</span>
							</div>
						</div>
						<a-divider style="margin-top: 8px" />
						<div style="margin-top: -18px; position: relative">
							<a-popconfirm
								:title="record.appStatus == 1 ? '请确认停用操作' : '确定要启用该应用吗？'"
								ok-text="确认"
								cancel-text="取消"
								@confirm="changeStatus(record.appId, record.appStatus == 1 ? 0 : 1)"
							>
								<a-button shape="default" :type="record.appStatus == 0 ? 'primary' : 'default'">
									{{ record.appStatus == 1 ? '禁用' : '启用' }}
								</a-button>
							</a-popconfirm>
							<a-popconfirm title="确认删除吗？" @confirm="onDelete(record.appId)" ok-text="确定" cancel-text="取消">
								<a-button style="margin-left: 10px" v-show="record.appStatus == 0">删除</a-button>
							</a-popconfirm>
							<a style="position: absolute; right: 0; bottom: 5px" @click="record.appInfoOpen = true">更多信息</a>
						</div>
					</div>
				</a-col>
			</a-row>
			<a-pagination
				v-model:current="current"
				v-model:pageSize="pageSize"
				class="pagination"
				show-quick-jumper
				:total="total"
				@change="change"
				showSizeChanger
				:pageSizeOptions="['12', '24', '48', '96']"
				:locale="locale"
				:show-total="(total) => `共 ${total} 条数据`"
			/>
		</div>
	</div>
	<a-drawer v-model:open="open" title="修改 APP " width="660px" :footer="null" @cancel="handleCancel">
		<a-form
			ref="appForm"
			:model="updateDate"
			:label-col="labelCol"
			:wrapper-col="wrapperCol"
			class="temform"
			:rules="rules"
		>
			<a-form-item ref="appName" label="APP 名称" name="appName" class="tem-item">
				<a-input
					:maxlength="20"
					v-model:value="updateDate.appName"
					placeholder="请填写长度在 3 到 20 个字符的 APP 名"
				/>
			</a-form-item>

			<a-form-item label="渠道选择" name="channelType" class="tem-item">
				<a-select
					v-model:value="updateDate.channelType"
					:options="channelData.map((pro) => ({ value: pro.value, label: pro.label }))"
					@select="channelTypeSelect"
				/>
			</a-form-item>
			<a-form-item label="APP 配置" name="appConfig" class="tem-item">
				<json-editor-vue
					class="editor"
					v-model="jsonobj"
					currentMode="code"
					:modeList="modeList"
					:options="options"
					@change="jsonChange"
					language="cn"
				/>
			</a-form-item>
			<a-form-item label="APP 状态" name="appStatus" class="tem-item">
				<a-switch
					v-model:checked="updateDate.appStatus"
					checked-children="启用"
					un-checked-children="禁用"
					:checkedValue="1"
					:unCheckedValue="0"
				/>
			</a-form-item>
		</a-form>
		<template #extra>
			<a-button @click="handleCancel">取消</a-button>
			<a-button style="margin-left: 10px" type="primary" @click="handleOk" :loading="iconLoading">确认修改</a-button>
		</template>
	</a-drawer>
</template>

<style lang="scss" scoped>
#message-container {
	position: relative;
	width: 100%;
	overflow: auto;
	//height: calc(100% + 94px);

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
		}
	}

	.between {
		display: flex;
		justify-content: space-between;
		padding: 0;
		margin-right: 16px;
	}
}
</style>

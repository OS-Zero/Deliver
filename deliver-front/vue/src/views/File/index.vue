<script lang="ts" setup>
import type { UnwrapRef } from 'vue'
import { h, onMounted, reactive, ref } from 'vue'
import type { TableColumnsType } from 'ant-design-vue'
import { ReloadOutlined } from '@ant-design/icons-vue'
import { message } from 'ant-design-vue'
import { platformFile, searchPlatformFile } from './type'
import { getPagePlatformFile } from '@/api/platformFile'
import searchForm from './components/searchForm.vue'
import uploadFile from './components/uploadFile.vue'
import {
	UpCircleTwoTone,
	DownCircleTwoTone,
	CopyTwoTone,
	SettingOutlined
} from '@ant-design/icons-vue'
/**
 * 表格初始化
 */
const platformFileTable: UnwrapRef<platformFile[]> = reactive([])
// 表格数据
const columns: TableColumnsType = [
	{
		title: '文件 ID',
		dataIndex: 'id',
		key: 'id'
	},
	{
		title: '文件名',
		dataIndex: 'fileName',
		key: 'fileName'
	},
	{
		title: 'APP 类型',
		dataIndex: 'appType',
		key: 'appType'
	},
	{
		title: '文件类型',
		dataIndex: 'fileType',
		key: 'fileType'
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
		title: '操作',
		key: 'operation',
		fixed: 'right',
		width: 120
	}
]

// 表格加载中标志
const tableLoadFlag = ref<boolean>(true)

/**
 * 渲染 data
 */

const innerPlatformFileData: UnwrapRef<platformFile[]> = reactive([])

const expandedRowKeys: number[] = reactive([])

const getInnerData = (expanded, record): void => {
	// 判断是否点开
	expandedRowKeys.length = 0
	if (expanded === true) {
		const b = record.key
		expandedRowKeys.push(b)
		innerPlatformFileData.length = 0
		innerPlatformFileData.push(record)
	} else {
		expandedRowKeys.length = 0
		innerPlatformFileData.length = 0
	}
}

const judgeInclude = (record): boolean => {
	return innerPlatformFileData.includes(record)
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

/// 查询操作
const searchItem: searchPlatformFile = reactive({
	fileName: undefined,
	appType: undefined,
	fileType: undefined,
	fileKey: undefined,
	appId: undefined,
	currentPage: 1,
	pageSize: 10,
	startTime: undefined,
	endTime: undefined
})

const total = ref()

const current = ref()
const pageSize = ref()

const change = (page: number, pageSize: number): void => {
	getPagesPlatformFile({ page, pageSize, opt: 1 })
}

const locale = {
	items_per_page: '条/页', // 每页显示条数的文字描述
	jump_to: '跳至', // 跳转到某页的文字描述
	page: '页', // 页的文字描述
	prev_page: '上一页', // 上一页按钮文字描述
	next_page: '下一页' // 下一页按钮文字描述
}

// 条件查询
const getPagesPlatformFile = ({ page, pageSize, opt }: SearchOptions = {}): void => {
	tableLoadFlag.value = true
	// 对象解构
	// eslint-disable-next-line
	const { perid, ...rest } = searchform.value.searchPage
	const searchNeedMes = { ...rest }
	console.warn(searchNeedMes)
	searchNeedMes.currentPage = page
	searchNeedMes.pageSize = pageSize
	getPagePlatformFile(searchNeedMes)
		.then((res) => {
			platformFileTable.length = 0
			total.value = res.data.total
			current.value = page
			tableLoadFlag.value = false
			if (res.data.records.length > 0) {
				res.data.records.forEach((item: any) => {
					item.key = item.id
					platformFileTable.push(item)
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

// copy 文件 Key
const copyFileKey = (fileKey: string): void => {
	navigator.clipboard.writeText(fileKey)
	message.success('复制成功')
}

onMounted(() => {
	tableLoadFlag.value = true
	getPagePlatformFile(searchItem)
		.then((res) => {
			tableLoadFlag.value = false
			total.value = res.data.total
			if (res.data.records.length > 0) {
				res.data.records.forEach((item: any) => {
					item.key = item.id
					platformFileTable.push(item)
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
	<searchForm ref="searchform" @mes="getPagesPlatformFile({ page: 1, pageSize, opt: 1 })" />
	<!-- 表格部分 -->
	<div id="message-container">
		<div class="message-section">
			<div class="splitter">
				<a-tooltip title="刷新">
					<a-button
						shape="circle"
						:icon="h(ReloadOutlined)"
						@click="getPagesPlatformFile({ page: current, pageSize, opt: 1 })" />
				</a-tooltip>
				<uploadFile @mes="getPagesPlatformFile({ page: 1, pageSize, opt: 1 })" />
			</div>
			<!-- 表格部分 -->
			<a-table
				:columns="columns"
				:data-source="platformFileTable"
				:scroll="{ x: 1200, y: undefined, scrollToFirstRowOnChange: true }"
				class="components-table-demo-nested"
				@expand="getInnerData"
				:expandIconColumnIndex="-1"
				:expandIconAsCell="false"
				:pagination="false"
				:loading="tableLoadFlag"
				:expandedRowKeys="expandedRowKeys">
				<template #headerCell="{ column }">
					<template v-if="column.key === 'operation'">
						<span>
							<SettingOutlined />
							操作
						</span>
					</template>
				</template>
				<template #bodyCell="{ column, record }">
					<!-- 表格数据渲染 -->
					<template v-if="column.key === 'appType'">
						<span>
							<!-- 根据 appType 的值显示不同的图片 -->
							<img
								style="height: 30px; width: 30px"
								v-if="record.appType === 1"
								src="钉钉.png"
								alt="钉钉" />
							<img
								style="height: 30px; width: 30px"
								v-else-if="record.appType === 2"
								src="企业微信.png"
								alt="企业微信" />
							<img
								style="height: 30px; width: 30px"
								v-else-if="record.appType === 3"
								src="飞书.png"
								alt="飞书" />
							<!-- 添加更多条件根据需要显示不同的图片 -->
						</span>
					</template>
					<template v-if="column.key === 'fileType'">
						<span style="color: #1677ff">
							{{ record.fileType }}
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
							<template #title>查看平台文件更多信息</template>
							<a-button
								type="link"
								size="small"
								style="font-size: 14px"
								@click="getInnerData(true, record)"
								v-if="!judgeInclude(record)">
								<DownCircleTwoTone style="font-size: 18px" />
							</a-button>
						</a-tooltip>
					</template>
				</template>
				<template #expandedRowRender="{ record }">
					<a-row :gutter="[16, 16]">
						<a-col :span="24">
							FileKey：
							<span style="color: #1677ff">
								{{ record.fileKey }}
								<a-tooltip title="复制 fileKey, 发送你的多媒体消息吧~">
									<CopyTwoTone @click="copyFileKey(record.fileKey)" />
								</a-tooltip>
							</span>
						</a-col>
						<a-col :span="6">
							关联 AppId：
							<strong>{{ record.appId }}</strong>
						</a-col>
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
	</div>
</template>

<style lang="scss" scoped>
#message-container {
	position: relative;
	width: 100%;
	overflow: auto;

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
}

.between {
	display: flex;
	justify-content: space-between;
	padding: 0;
	margin: 0;
}
</style>

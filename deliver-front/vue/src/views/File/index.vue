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
import { DownOutlined, UpOutlined } from '@ant-design/icons-vue'
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

const change = (page: number, pageSize: number): void => {
	getPagesPlatformFile({ page, pageSize })
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
			current.value = res.data.page
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
			console.warn('查询数据', platformFileTable)
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
	console.log(fileKey)
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
				console.warn('初始化数据', platformFileTable)
			}
		})
		.catch((err) => {
			console.error('An error occurred:', err)
		})
})
</script>

<template>
	<!-- 搜索部分 -->
	<searchForm ref="searchform" @mes="getPagesPlatformFile({ opt: 1 })" />
	<!-- 表格部分 -->
	<div id="message-container">
		<div class="message-section">
			<div class="splitter">
				<a-tooltip title="刷新">
					<a-button
						shape="circle"
						:icon="h(ReloadOutlined)"
						@click="getPagesPlatformFile({ opt: 1 })" />
				</a-tooltip>
				<uploadFile @mes="getPagesPlatformFile({ opt: 1 })" />
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
				<template #bodyCell="{ column, record }">
					<!-- 表格数据渲染 -->
					<template v-if="column.key === 'appType'">
						<span>{{ record.appType }}</span>
					</template>
					<template v-if="column.key === 'operation'">
						<a-button
							type="link"
							size="small"
							style="font-size: 14px"
							@click="getInnerData(false, record)"
							v-if="judgeInclude(record)">
							<UpOutlined />
							收起
						</a-button>
						<a-button
							type="link"
							size="small"
							style="font-size: 14px"
							@click="getInnerData(true, record)"
							v-if="!judgeInclude(record)">
							<DownOutlined />
							展开
						</a-button>
					</template>
				</template>
				<template #expandedRowRender="{ record }">
					<a-row :gutter="[16, 16]">
						<a-col :span="24">
							FileKey：
							<strong>
								{{ record.fileKey }}
								<a-button type="link" @click="copyFileKey(record.fileKey)">copy</a-button>
							</strong>
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

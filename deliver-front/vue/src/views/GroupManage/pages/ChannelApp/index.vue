<script lang="ts" setup>
import { ref, onBeforeMount, reactive, h, onUnmounted, watch } from 'vue'
import { channelAppColumns, filterSchema, filterSchemaMaps } from "@/config/channelApp"
import { CopyOutlined, DownOutlined, ExclamationCircleOutlined, FilterOutlined, CloseOutlined } from '@ant-design/icons-vue';
import { copyToClipboard, dynamic, getDataFromSchema } from '@/utils/utils';
import { FormInstance, message, Modal, TableProps } from 'ant-design-vue';
import SearchInput from '@/components/SearchInput/index.vue'
import { debounce } from 'lodash'
import { usePagination } from '@/hooks/table';
import { Key } from 'ant-design-vue/lib/_util/type';
import { deleteChannelApp, getChannelApp, updateChannelAppStatus } from '@/api/channelApp';
import { ChannelApp } from '@/types/channelApp';
import ChannelAppDrawer from './components/ChannelAppDrawer.vue';
import { getColor } from '@/utils/table';
type Operation = 'add' | 'edit' | 'delete' | 'more'
const dataSource = ref<ChannelApp[]>([])
const { dynamicData: filterForm, stop } = dynamic(filterSchema, filterSchemaMaps)
const searchValue = ref('')
const handleSearch = async () => {
	const { records, total } = await getChannelApp({ appName: searchValue.value, ...getDataFromSchema(filterForm), pageSize: pagination.pageSize, currentPage: pagination.current })
	dataSource.value = records
	pagination.total = total
}
const debounceSearch = debounce(handleSearch, 200)
const { pagination } = usePagination(handleSearch)
watch(filterForm, () => {
	debounceSearch()
})
const copyId = async (text: string) => {
	try {
		await copyToClipboard(text)
		message.success('复制成功')
	} catch (error) {
		message.error('复制失败')
	}
}
const drawerState = reactive<{
	open: boolean
	operation: 'add' | 'edit' | 'more' | 'testSend'
	record: Record<string, any>
}>({
	open: false,
	operation: 'add',
	record: {}
})
const filterState = reactive({
	open: false
})


const formRef = ref<FormInstance>();
const rowSelection: TableProps['rowSelection'] = reactive({
	selectedRowKeys: [],
	onChange: (selectedRowKeys: Key[]) => {
		rowSelection && (rowSelection.selectedRowKeys = selectedRowKeys)
	},
})
const operationDispatch = {
	delete: async (record: Record<string, any> = {}) => {
		Modal.confirm({
			title: '确认删除该应用?',
			icon: h(ExclamationCircleOutlined),
			okText: '确认',
			cancelText: '取消',
			async onOk() {
				await deleteChannelApp({ ids: record && record.appId })
				message.success('删除成功')
			},
		});
	}
}
const handleActions = async (operation: Operation, record: Record<string, any> = {}) => {
	(operation === 'add' || operation === 'edit' || operation === 'more') && (drawerState.operation = operation, drawerState.open = true);
	(operation === 'edit' || operation === 'more') && (drawerState.record = record);
	(operation === 'delete') && operationDispatch[operation](record);
}

const handleBatchDelete = () => {
	Modal.confirm({
		title: '确认批量删除应用?',
		icon: h(ExclamationCircleOutlined),
		okText: '确认',
		cancelText: '取消',
		async onOk() {
			await deleteChannelApp({ ids: rowSelection.selectedRowKeys as number[] })
			message.success('删除成功')
		},
	});
}

const handleFilterClose = () => {
	filterState.open = false
	formRef.value?.resetFields()
}
const handleDrawerClose = () => {
	drawerState.open = false
}
const changeStatus = async (record: Record<string, any>) => {
	record.appStatus = !record.appStatus
	await updateChannelAppStatus({ appId: record.appId, appStatus: Number(record.appStatus) })
}
onBeforeMount(() => {
	handleSearch()
})
onUnmounted(() => {
	//停止监听动态数据
	stop()
})
</script>

<template>
	<div class="container">
		<div class="container-table">
			<div class="table-header">
				<SearchInput class="search_input" placeholder="请输入应用名" v-model="searchValue" @search="debounceSearch()">
				</SearchInput>
				<div class="operation">
					<a-button class="btn--add" @click="handleActions('add')" type="primary">新增</a-button>
					<a-button :icon="h(FilterOutlined)" shape="circle" type="text"
						@click="filterState.open = !filterState.open"></a-button>
				</div>
			</div>
			<div class="selections--delete" v-show="rowSelection.selectedRowKeys?.length">
				<div>已选择 {{ rowSelection.selectedRowKeys?.length }} 项</div>
				<a-button type="link" danger @click="handleBatchDelete">批量删除</a-button>
			</div>
			<a-table row-key="appId" :dataSource="dataSource" :columns="channelAppColumns" :row-selection="rowSelection"
				:pagination="pagination" :scroll="{ x: 1400, y: 680 }">
				<template #bodyCell="{ column, text, record, index }">
					<template v-if="column.key === 'appId'">
						{{ text }}
						<CopyOutlined class="id--copy" @click="copyId(text)" />
					</template>
					<template v-else-if="column.key === 'appStatus'">
						<a-switch :checked="Boolean(record[column.key])" @change="changeStatus(record)" checked-children="开启"
							un-checked-children="关闭" />
					</template>
					<template v-if="['channelProviderTypeName', 'channelTypeName'].includes(column.key)">
						<a-tag :color="getColor(index)">{{ text }}</a-tag>
					</template>
					<template v-else-if="column.key === 'actions'">
						<a-button type="link" @click="handleActions('edit', record)">编辑 </a-button>
						<a-button type="link" danger @click="handleActions('delete', record)">删除</a-button>
						<a-dropdown placement="bottom">
							<a-button type="link">
								更多操作
								<DownOutlined />
							</a-button>
							<template #overlay>
								<a-menu>
									<a-menu-item>
										<div @click="handleActions('more', record)">查看更多</div>
									</a-menu-item>
								</a-menu>
							</template>
						</a-dropdown>
					</template>
				</template>
			</a-table>
		</div>
		<a-card size="small" class="filter-form" :class="{ open: filterState.open }" title="筛选">
			<template #extra><a-button type="text" :icon="h(CloseOutlined)" @click="handleFilterClose"></a-button></template>
			<Form ref="formRef" :form-schema="filterForm" />
		</a-card>
		<ChannelAppDrawer v-bind="drawerState" @close="handleDrawerClose">
		</ChannelAppDrawer>
	</div>
</template>

<style lang="scss" scoped>
.container {
	display: flex;
	height: 100%;
}

.btn--add {
	margin-right: var(--spacing-md);
}

.container-table {
	width: calc(100% - 300px);
	flex: 1 auto;
}

.ant-card {
	border-radius: 0;
	padding: 0;
	margin-left: var(--spacing-md);
}

:deep(.ant-card-head) {
	border: none;
	font-size: large;
}

.filter-form {
	height: 100%;
	overflow: hidden;
	transition: width 120ms;
	border: none;
	width: 0;

	&.open {
		border-left: 1px solid var(--gray-lighter);
		width: 300px
	}
}


.search_input {
	width: 200px;
}

.table-header {
	display: flex;
	justify-content: space-between;
	align-items: center;
	margin-bottom: var(--spacing-xs);
	padding: var(--spacing-xs) 0;
}

.id--copy {
	cursor: pointer;
	color: var(--blue-darker);
}

.selections--delete {
	border-radius: var(--border-radius-small);
	padding: var(--spacing-sm);
	background-color: var(--gray-lightest);
	margin-bottom: var(--spacing-md);
	color: var(--gray-dark);
	display: flex;
	align-items: center;
	justify-content: space-between;
}
</style>

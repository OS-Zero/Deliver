<script lang="ts" setup>
import { ref, onBeforeMount, reactive, h, watch } from 'vue'
import { channelAppColumns, filterForm, setFilterOptionsDispatch } from "@/config/channelApp"
import { CopyOutlined, DownOutlined, ExclamationCircleOutlined, FilterOutlined, CloseOutlined, AppstoreOutlined, MenuOutlined } from '@ant-design/icons-vue';
import { copyToClipboard, getDataFromSchema } from '@/utils/utils';
import { FormInstance, message, Modal, TableProps, Empty } from 'ant-design-vue';
import SearchInput from '@/components/SearchInput/index.vue'
import { debounce } from 'lodash'
import { usePagination } from '@/hooks/table';
import { Key } from 'ant-design-vue/lib/_util/type';
import { deleteChannelApp, getChannelApp, updateChannelAppStatus } from '@/api/channelApp';
import { ChannelApp } from '@/types/channelApp';
import ChannelAppDrawer from './components/ChannelAppDrawer.vue';
import { getColor } from '@/utils/table';
import Card from './components/Card.vue'
const simpleImage = Empty.PRESENTED_IMAGE_SIMPLE;
type Operation = 'add' | 'edit' | 'delete' | 'more'
const dataSource = ref<ChannelApp[]>([])
const loading = ref(false)
const searchValue = ref('')
const handleSearch = async () => {
	try {
		loading.value = true
		const { records, total } = await getChannelApp({ appName: searchValue.value, ...getDataFromSchema(filterForm), pageSize: pagination.pageSize, currentPage: pagination.current })
		dataSource.value = records
		pagination.total = total
		loading.value = false
	} catch (error) {
		loading.value = false
	}

}
const debounceSearch = debounce(handleSearch, 200)
const { pagination, resetPagination } = usePagination(handleSearch)
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
	operation: 'add' | 'edit' | 'more'
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
				await deleteChannelApp({ ids: [record.appId] })
				resetPagination()
				handleSearch()
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
			resetPagination()
			handleSearch()
			rowSelection.selectedRowKeys = []
			message.success('删除成功')
		},
	});
}
const handleCancelBatchDelete = () => {
	rowSelection.selectedRowKeys = []
}
const handleFilterClose = () => {
	filterState.open = false
	formRef.value?.resetFields()
}
const handleDrawerClose = () => {
	drawerState.open = false;
	drawerState.operation === 'add' && resetPagination();
	(drawerState.operation === 'add' || drawerState.operation === 'edit') && handleSearch();
}
const changeStatus = async (record: Record<string, any>) => {
	record.appStatus = !record.appStatus
	await updateChannelAppStatus({ appId: record.appId, appStatus: Number(record.appStatus) })
}
const tableView = ref(true)
onBeforeMount(() => {
	setFilterOptionsDispatch['channelType']()
	handleSearch()
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
					<div class="toggle_btns">
						<a-tooltip placement="top">
							<template #title>
								<span>表格视图</span>
							</template>
							<a-button class="toggle_btn" :icon="h(MenuOutlined)" size="small" type="text" @click="tableView = true"
								:class="{ active: tableView }"></a-button>
						</a-tooltip>
						<a-tooltip placement="top">
							<template #title>
								<span>卡片视图</span>
							</template>
							<a-button class="toggle_btn" :icon="h(AppstoreOutlined)" size="small" type="text"
								@click="tableView = false" :class="{ active: !tableView }"></a-button>
						</a-tooltip>
					</div>
					<a-button :icon="h(FilterOutlined)" @click="filterState.open = !filterState.open"></a-button>
				</div>
			</div>
			<div class="selections--delete" v-show="rowSelection.selectedRowKeys?.length">
				<div>已选择 {{ rowSelection.selectedRowKeys?.length }} 项</div>
				<div class="selections_btns">
					<a-button type="link" @click="handleCancelBatchDelete">取消选择</a-button>
					<a-button type="link" danger @click="handleBatchDelete">批量删除</a-button>
				</div>
			</div>
			<a-table v-show="tableView" row-key="appId" :dataSource="dataSource" :columns="channelAppColumns"
				:row-selection="rowSelection" :pagination="false" :scroll="{ x: 1400, y: 680 }" :loading="loading">
				<template #bodyCell="{ column, text, record }">
					<template v-if="column.key === 'appId'">
						{{ text }}
						<CopyOutlined class="id--copy" @click="copyId(text)" />
					</template>
					<template v-else-if="column.key === 'appStatus'">
						<a-switch :checked="Boolean(record[column.key])" @change="changeStatus(record)" checked-children="开启"
							un-checked-children="关闭" />
					</template>
					<template v-else-if="['channelProviderTypeName', 'channelTypeName'].includes(column.key as string)">
						<a-tag :color="getColor(text)">{{ text }}</a-tag>
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
			<div class="card-list" v-show="!tableView">
				<Card v-for="item in dataSource" :key="item.appId" :data="item" @change-status="changeStatus"
					@handleActions="handleActions">
				</Card>
			</div>
			<a-empty class="empty" v-if="!dataSource.length" :image="simpleImage" />
			<a-pagination class="pagination" v-bind="pagination" />
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
	position: relative;
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

.operation {
	display: flex;
	align-items: center;
	gap: var(--spacing-xs);
}

.toggle_btns {
	border: 1px solid var(--gray-lighter);
	padding: var(--spacing-xs);
	border-radius: var(--border-radius-small);
	display: flex;
	align-items: center;
	gap: var(--spacing-xs);
}

.card-list {
	display: grid;
	gap: var(--spacing-md);
	grid-template-columns: repeat(6, 250px);
}

.pagination {
	margin-top: var(--spacing-md);
	position: absolute;
	right: 0;
}

.toggle_btn.active {
	color: var(--primary-color);
}
</style>

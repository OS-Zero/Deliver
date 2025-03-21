<script lang="ts" setup>
import { ref, onBeforeMount, reactive, h, watch } from 'vue'
import { deleteTask, updateTaskStatus, getTask, sendRealTimeMessage } from '@/api/task'
import { taskColumns, filterFormSchema } from "@/config/task"
import { CopyOutlined, DownOutlined, ExclamationCircleOutlined, FilterOutlined, CloseOutlined } from '@ant-design/icons-vue';
import { message, Modal, TableProps } from 'ant-design-vue';
import SearchInput from '@/components/SearchInput/index.vue'
import { debounce } from 'lodash'
import { usePagination } from '@/hooks/table';
import { Key } from 'ant-design-vue/lib/_util/type';
import TaskGroupDrawer from './components/TaskGroupDrawer.vue';
import { getColor } from '@/utils/table';
import useRequest from '@/hooks/request';
import { useForm } from '@/hooks/form';
import { useClipboard } from '@/hooks/clipboard';
import { Task } from '@/types/task';

type Operation = 'add' | 'edit' | 'delete' | 'more' | 'sendTask'
const searchValue = ref('')
const { formRef, formData: filterForm } = useForm(filterFormSchema)
const { loading, data: tableData, run: handleSearch } = useRequest(
	getTask,
	() => ({ taskName: searchValue.value, ...filterForm.value, pageSize: pagination.pageSize, currentPage: pagination.current }),
	{
		debounce: true,
		onFinish(data) {
			pagination.total = data.total
		},
		cacheTime: 1000 * 60
	}
)
const debounceSearch = debounce(handleSearch, 200)
const { pagination, resetPagination } = usePagination(handleSearch)
watch(filterForm, () => {
	resetPagination()
	debounceSearch()
})
const { copy } = useClipboard()
const drawerState = reactive<{
	open: boolean
	operation: 'add' | 'edit' | 'more'
	record: Task
}>({
	open: false,
	operation: 'add',
	record: {} as Task
})
const filterState = reactive({
	open: false
})

const rowSelection: TableProps['rowSelection'] = reactive({
	selectedRowKeys: [],
	onChange: (selectedRowKeys: Key[]) => {
		rowSelection && (rowSelection.selectedRowKeys = selectedRowKeys)
	},
})
const operationDispatch = {
	delete: async (record: Record<string, any> = {}) => {
		Modal.confirm({
			title: '确认删除该任务?',
			icon: h(ExclamationCircleOutlined),
			okText: '确认',
			cancelText: '取消',
			async onOk() {
				await deleteTask({ ids: [record.taskId] })
				resetPagination()
				message.success('删除成功')
			},
		});
	},
	sendTask: async (record: Record<string, any> = {}) => {
		await sendRealTimeMessage({ taskId: record.taskId })
		message.success('发送成功')
	}
}
const handleActions = async (operation: Operation, record: Task = (drawerState.record = {} as Task)) => {
	(operation === 'add' || operation === 'edit' || operation === 'more') && (drawerState.operation = operation, drawerState.open = true);
	(operation === 'edit' || operation === 'more') && (drawerState.record = record);
	(operation === 'delete' || operation === 'sendTask') && operationDispatch[operation](record);
}

const handleBatchDelete = () => {
	Modal.confirm({
		title: '确认批量删除任务?',
		icon: h(ExclamationCircleOutlined),
		okText: '确认',
		cancelText: '取消',
		async onOk() {
			await deleteTask({ ids: rowSelection.selectedRowKeys as number[] })
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
const changeStatus = (record: Record<string, any>) => {
	record.taskStatus = !record.taskStatus
	updateTaskStatus({ taskId: record.taskId, taskStatus: Number(record.taskStatus) })
}
const handleFilterClose = () => {
	filterState.open = false
	formRef.value?.resetFields()
}
const handleDrawerClose = (flash: boolean = false) => {
	drawerState.open = false;
	flash && (resetPagination(), handleSearch());
}
onBeforeMount(() => {
	handleSearch()
})
</script>

<template>
	<div class="container">
		<div class="container-table">
			<div class="table-header">
				<SearchInput class="search_input" placeholder="请输入任务名" v-model="searchValue" @search="debounceSearch()">
				</SearchInput>
				<div class="operation">
					<a-button class="btn--add" @click="handleActions('add')" type="primary">新增</a-button>
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
			<a-table row-key="taskId" :dataSource="tableData?.records" :columns="taskColumns" :row-selection="rowSelection"
				:pagination="pagination" :scroll="{ x: 1400 }" :loading="loading">
				<template #bodyCell="{ column, text, record }">
					<template v-if="column.key === 'taskId'">
						{{ text }}
						<CopyOutlined class="id--copy" @click="copy(text)" />
					</template>
					<template v-else-if="column.key === 'taskStatus'">
						<a-switch :checked="Boolean(record[column.key])" checked-children="开启" un-checked-children="关闭"
							@click="changeStatus(record)" />
					</template>
					<template v-else-if="column.key === 'taskType'">
						<a-tag v-if="text === 1" :color="getColor(1)">实时任务</a-tag>
						<a-tag v-else-if="text === 2" :color="getColor(2)">定时循环任务
						</a-tag>
						<a-tag v-else-if="text === 3" :color="getColor(3)">定时单次任务</a-tag>
					</template>
					<template v-else-if="column.key === 'peopleGroupName'">
						<span style="color: var(--primary-color);">{{ text }}</span>
					</template>
					<template v-else-if="column.key === 'templateName'">
						<span style="color: var(--primary-color);">{{ text }}</span>
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
									<a-menu-item v-if="record.taskType === 1">
										<div @click="handleActions('sendTask', record)">发送实时任务</div>
									</a-menu-item>
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
			<Form ref="formRef" :form-schema="filterFormSchema" />
		</a-card>
		<TaskGroupDrawer v-bind="drawerState" @close="handleDrawerClose"></TaskGroupDrawer>
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
	overflow: hidden;
	transition: width 120ms;
	border: none;
	width: 0;
	height: 0;

	&.open {
		border-left: 1px solid var(--gray-lighter);
		width: 300px;
		height: 100%;
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

:deep(.ant-descriptions-item-content) {
	align-items: center !important;
}
</style>

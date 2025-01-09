<script lang="ts" setup>
import { ref, onBeforeMount, reactive, nextTick, h, watch } from 'vue'
import { saveTask, deleteTask, updateTaskStatus, getTask, updateTask, sendRealTimeMessage } from '@/api/task'
import { taskColumns, taskSchema, filterSchema, taskLocale } from "@/config/task"
import { CopyOutlined, DownOutlined, ExclamationCircleOutlined, FilterOutlined, CloseOutlined } from '@ant-design/icons-vue';
import { copyToClipboard, dynamic, getDataFromSchema } from '@/utils/utils';
import { FormInstance, message, Modal, TableProps } from 'ant-design-vue';
import Drawer from '@/components/Drawer/index.vue'
import SearchInput from '@/components/SearchInput/index.vue'
import { debounce } from 'lodash'
import { usePagination } from '@/hooks/table';
import { Key } from 'ant-design-vue/lib/_util/type';
import { Task } from '@/types/task';
const dataSource = reactive<Task[]>([])


const { dynamicData: TaskForm } = dynamic(taskSchema)
const { dynamicData: filterForm } = dynamic(filterSchema)
const handleSearch = async () => {
	const { records, total } = await getTask({ ...getDataFromSchema(filterForm), pageSize: pagination.pageSize, currentPage: pagination.current })
	Object.assign(dataSource, records)
	pagination.total = total
}
const debounceSearch = debounce(handleSearch, 200)
const { pagination } = usePagination(handleSearch)
watch(filterForm, () => {
	debounceSearch()
})
const testMessageForm = reactive(taskSchema)
const moreInfo = reactive<Array<{ label: string; value: any }>>([])
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
	title: string
	operation: string
	placement: 'left' | 'right' | 'top' | 'bottom' | undefined
	extra: boolean
}>({
	open: false,
	title: '',
	operation: '',
	placement: "right",
	extra: true
})
const filterState = reactive({
	open: false
})
const searchValue = ref('')

const formRef = ref<FormInstance>();
const rowSelection: TableProps['rowSelection'] = reactive({
	selectedRowKeys: [],
	onChange: (selectedRowKeys: Key[]) => {
		rowSelection && (rowSelection.selectedRowKeys = selectedRowKeys)
	},
})
const handleActions = async (action: 'add' | 'edit' | 'delete' | 'more' | 'sendTask', record?: Record<string, any>) => {
	drawerState.placement = 'right'
	if (action === 'add') {
		drawerState.title = '新增任务'
		drawerState.open = true
		drawerState.operation = 'add'
	} else if (action === 'edit') {
		drawerState.title = '编辑任务'
		drawerState.open = true
		drawerState.operation = 'edit'
		//异步使重置数据为空
		nextTick(() => {
			for (const key in TaskForm) {
				if (key === 'taskParam') TaskForm[key].value = record && JSON.parse(record[key] || '{}')
				else TaskForm[key].value = record && record[key]
			}
		})
	} else if (action === 'delete') {
		Modal.confirm({
			title: '确认删除该任务?',
			icon: h(ExclamationCircleOutlined),
			okText: '确认',
			cancelText: '取消',
			async onOk() {
				await deleteTask({ ids: record && record.taskId })
				message.success('删除成功')
			},
		});
	} else if (action === 'sendTask') {
		await sendRealTimeMessage({ taskId: record && record.taskId })
		message.success('发送成功')
	} else if (action === 'more') {
		drawerState.title = '任务详情'
		drawerState.open = true
		drawerState.operation = 'showMore'
		drawerState.placement = 'left'
		drawerState.extra = false
		const set = new Set(['taskId', 'peopleGroupId'])
		const arr: Array<{ label: string; value: any }> = []
		for (const key in record) {
			if (!set.has(key)) {
				arr.push({
					label: taskLocale[key],
					value: record[key]
				})
			}
		}
		Object.assign(moreInfo, arr)
	}
}
const handleBatchDelete = () => {
	Modal.confirm({
		title: '确认批量删除任务?',
		icon: h(ExclamationCircleOutlined),
		okText: '确认',
		cancelText: '取消',
		async onOk() {
			await deleteTask({ ids: rowSelection.selectedRowKeys as number[] })
			message.success('删除成功')
		},
	});
}
const handleDrawer = {
	ok: async () => {
		try {
			await formRef.value?.validate()
			if (drawerState.operation === 'add') {
				await saveTask(getDataFromSchema(TaskForm))
				message.success('添加成功')
			} else if (drawerState.operation == 'edit') {
				await updateTask(getDataFromSchema(TaskForm))
				message.success('编辑成功')
			} else if (drawerState.operation === 'more') {
				drawerState.placement = 'right'
			}
			formRef.value?.resetFields()
			drawerState.open = false
		} catch (error) {
			console.log(error);
		}
	},
	cancel: () => {
		formRef.value?.resetFields()
		drawerState.open = false
		drawerState.extra = true
	}
}
const changeStatus = (record: Record<string, any>) => {
	updateTaskStatus({ taskId: record.taskId, taskStatus: Number(record.taskStatus) })
}
const handleFilterClose = () => {
	filterState.open = false
	formRef.value?.resetFields()
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
					<a-button :icon="h(FilterOutlined)" shape="circle" type="text"
						@click="filterState.open = !filterState.open"></a-button>
				</div>
			</div>
			<div class="selections--delete" v-show="rowSelection.selectedRowKeys?.length">
				<div>已选择 {{ rowSelection.selectedRowKeys?.length }} 项</div>
				<a-button type="link" danger @click="handleBatchDelete">批量删除</a-button>
			</div>
			<a-table row-key="taskId" :dataSource="dataSource" :columns="taskColumns" :row-selection="rowSelection"
				:pagination="pagination" :scroll="{ x: 1400, y: 680 }">
				<template #bodyCell="{ column, text, record }">
					<template v-if="column.key === 'taskId'">
						{{ text }}
						<CopyOutlined class="id--copy" @click="copyId(text)" />
					</template>
					<template v-if="column.key === 'taskStatus'">
						<a-switch v-model:checked="record[column.key]" checked-children="开启" un-checked-children="关闭"
							@click="changeStatus(record)" />
					</template>
					<template v-if="column.key === 'taskType'">
						<a-tag v-if="record[column.key] === 1" color="success">实时</a-tag>
						<a-tag v-else="record[column.key] === 2" color="error">定时</a-tag>
					</template>
					<template v-if="column.key === 'actions'">
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
			<Form ref="formRef" :form-schema="filterForm" />
		</a-card>
		<Drawer :placement="drawerState.placement" :open="drawerState.open" :title="drawerState.title"
			:extra="drawerState.extra" @ok="handleDrawer.ok" @close="handleDrawer.cancel">
			<Form v-if="drawerState.operation === 'add' || drawerState.operation === 'edit'" ref="formRef"
				:label-col="{ span: 4 }" :form-schema="TaskForm" />
			<Form v-else-if="drawerState.operation === 'testSend'" ref="formRef" :label-col="{ span: 7 }"
				:form-schema="testMessageForm" />
			<div v-else-if="drawerState.operation === 'showMore'">
				<Descriptions :data="moreInfo" :config="{ column: 1 }">
					<template #content="{ item }">
						<template v-if="item.label === '任务类型'">
							{{ item.value === 1 ? '实时' : '定时' }}
						</template>
						<template v-else-if="item.label === '任务状态'">
							{{ !!item.value ? '开启' : '关闭' }}
						</template>
					</template>
				</Descriptions>
			</div>
		</Drawer>
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

:deep(.ant-descriptions-item-content) {
	align-items: center !important;
}
</style>

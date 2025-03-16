<script lang="ts" setup>
import { ref, onBeforeMount, reactive, h, watch } from 'vue'
import { deleteMessageTemplate, getMessageTemplates, updateMessageTemplateStatus } from '@/api/messageTemplate'
import { messageTemplateColumns, filterFormSchema } from "@/config/messageTemplate"
import { CopyOutlined, DownOutlined, ExclamationCircleOutlined, FilterOutlined, CloseOutlined } from '@ant-design/icons-vue';
import { message, Modal, TableProps } from 'ant-design-vue';
import SearchInput from '@/components/SearchInput/index.vue'
import { usePagination } from '@/hooks/table';
import { Key } from 'ant-design-vue/lib/_util/type';
import { MessageTemplate, SearchParams } from '@/types/messageTemplate';
import MessageTemplateDrawer from './components/MessageTemplateDrawer.vue';
import { getColor } from '@/utils/table';
import { useForm } from '@/hooks/form';
import useRequest from '@/hooks/request';
import { TableData } from '@/types';
import { useClipboard } from '@/hooks/clipboard';
type Operation = 'add' | 'edit' | 'delete' | 'more' | 'testSend'

const searchValue = ref('')
const { formRef, formData: filterForm } = useForm(filterFormSchema)
const { loading, data: tableData, run: handleSearch } = useRequest<SearchParams, TableData<MessageTemplate>>(
	getMessageTemplates,
	() => ({ templateName: searchValue.value, ...filterForm.value, pageSize: pagination.pageSize, currentPage: pagination.current }),
	{
		debounce: true,
		onFinish(data) {
			pagination.total = data.total
		},
		cacheTime: 1000 * 60
	}
)
const { pagination, resetPagination } = usePagination(handleSearch)
watch(filterForm, () => {
	resetPagination()
	handleSearch()
})
const drawerState = reactive<{
	open: boolean
	operation: 'add' | 'edit' | 'more' | 'testSend'
	record: MessageTemplate
}>({
	open: false,
	operation: 'add',
	record: {} as MessageTemplate
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
	delete: async (record: MessageTemplate) => {
		Modal.confirm({
			title: '确认删除该模板?',
			icon: h(ExclamationCircleOutlined),
			okText: '确认',
			cancelText: '取消',
			async onOk() {
				await deleteMessageTemplate({ ids: [record.templateId] })
				message.success('删除成功')
				resetPagination()
				handleSearch()
			},
		});
	}
}
const handleActions = async (operation: Operation, record: MessageTemplate = (drawerState.record = {} as MessageTemplate)) => {
	(operation === 'add' || operation === 'edit' || operation === 'more' || operation === 'testSend') && (drawerState.operation = operation, drawerState.open = true);
	(operation === 'edit' || operation === 'more' || operation === 'testSend') && (drawerState.record = record);
	(operation === 'delete') && operationDispatch[operation](record);
}
const handleBatchDelete = () => {
	Modal.confirm({
		title: '确认批量删除模板?',
		icon: h(ExclamationCircleOutlined),
		okText: '确认',
		cancelText: '取消',
		async onOk() {
			await deleteMessageTemplate({ ids: rowSelection.selectedRowKeys as number[] })
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
const { copy } = useClipboard()
const changeStatus = (record: Record<string, any>) => {
	record.templateStatus = !record.templateStatus
	updateMessageTemplateStatus({ templateId: record.templateId, templateStatus: Number(record.templateStatus) })
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
				<SearchInput class="search_input" placeholder="请输入模板名" v-model="searchValue" @search="handleSearch()">
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
			<a-table row-key="templateId" :dataSource="tableData?.records" :columns="messageTemplateColumns"
				:row-selection="rowSelection" :pagination="pagination" :scroll="{ x: 1400 }" :loading="loading">
				<template #bodyCell="{ column, text, record }">
					<template v-if="column.key === 'templateId'">
						{{ text }}
						<CopyOutlined class="id--copy" @click="copy(text)" />
					</template>
					<template v-else-if="column.key === 'templateStatus'">
						<a-switch :checked="Boolean(record[column.key])" checked-children="开启" un-checked-children="关闭"
							@change="changeStatus(record)" />
					</template>
					<template v-else-if="column.key === 'appName'">
						<span style="color: var(--primary-color);">{{ text }}</span>
					</template>
					<template
						v-else-if="['usersTypeName', 'channelProviderTypeName', 'messageTypeName', 'channelTypeName'].includes(column.key as string)">
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
										<div @click="handleActions('testSend', record)">测试发送</div>
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
		<MessageTemplateDrawer v-bind="drawerState" @close="handleDrawerClose">
		</MessageTemplateDrawer>
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
		height: auto;
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

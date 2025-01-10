<script lang="ts" setup>
import { ref, onBeforeMount, reactive, h, onUnmounted, watch } from 'vue'
import { deleteMessageTemplate, getMessageTemplates, updateMessageTemplateStatus } from '@/api/messageTemplate'
import { messageTemplateColumns, filterSchema, filterSchemaMaps } from "@/config/messageTemplate"
import { CopyOutlined, DownOutlined, ExclamationCircleOutlined, FilterOutlined, CloseOutlined } from '@ant-design/icons-vue';
import { copyToClipboard, dynamic, getDataFromSchema } from '@/utils/utils';
import { FormInstance, message, Modal, TableProps } from 'ant-design-vue';
import SearchInput from '@/components/SearchInput/index.vue'
import { debounce } from 'lodash'
import { usePagination } from '@/hooks/table';
import { Key } from 'ant-design-vue/lib/_util/type';
import { MessageTemplate } from '@/types/messageTemplate';
import MessageTemplateDrawer from './components/MessageTemplateDrawer.vue';
import { getColor } from '@/utils/table';
const dataSource = reactive<MessageTemplate[]>([])

type Operation = 'add' | 'edit' | 'delete' | 'more' | 'testSend'
const searchValue = ref('')
const { dynamicData: filterForm, stop: stopFilterDynamic } = dynamic(filterSchema, filterSchemaMaps)
const handleSearch = async () => {
	const { records, total } = await getMessageTemplates({ templateName: searchValue.value, ...getDataFromSchema(filterForm), pageSize: pagination.pageSize, currentPage: pagination.current })
	Object.assign(dataSource, records)
	pagination.total = total
}
const debounceSearch = debounce(handleSearch, 200)
const { pagination } = usePagination(handleSearch)
watch(filterForm, () => {
	debounceSearch()
})

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
			title: '确认删除该模板?',
			icon: h(ExclamationCircleOutlined),
			okText: '确认',
			cancelText: '取消',
			async onOk() {
				await deleteMessageTemplate({ ids: record.taskId })
				message.success('删除成功')
			},
		});
	}
}
const handleActions = async (operation: Operation, record: Record<string, any> = {}) => {
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
			message.success('删除成功')
		},
	});
}
const copyId = async (text: string) => {
	try {
		await copyToClipboard(text)
		message.success('复制成功')
	} catch (error) {
		message.error('复制失败')
	}
}
const changeStatus = (record: Record<string, any>) => {
	record.templateStatus = !record.templateStatus
	updateMessageTemplateStatus({ templateId: record.templateId, templateStatus: Number(record.templateStatus) })
}
const handleFilterClose = () => {
	filterState.open = false
	formRef.value?.resetFields()
}
const handleDrawerClose = () => {
	drawerState.open = false
}
onBeforeMount(() => {
	handleSearch()
})
onUnmounted(() => {
	//停止监听动态数据
	stopFilterDynamic()
})
</script>

<template>
	<div class="container">
		<div class="container-table">
			<div class="table-header">
				<SearchInput class="search_input" placeholder="请输入模板名" v-model="searchValue" @search="debounceSearch()">
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
			<a-table row-key="templateId" :dataSource="dataSource" :columns="messageTemplateColumns"
				:row-selection="rowSelection" :pagination="pagination" :scroll="{ x: 1400, y: 680 }">
				<template #bodyCell="{ column, text, record, index }">
					<template v-if="column.key === 'templateId'">
						{{ text }}
						<CopyOutlined class="id--copy" @click="copyId(text)" />
					</template>
					<template v-if="column.key === 'templateStatus'">
						<a-switch :checked="Boolean(record[column.key])" checked-children="开启" un-checked-children="关闭"
							@change="changeStatus(record)" />
					</template>
					<template
						v-if="['usersTypeName', 'channelProviderTypeName', 'messageTypeName', 'channelTypeName'].includes(column.key)">
						<a-tag :color="getColor(index)">{{ text }}</a-tag>
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
			<Form ref="formRef" :form-schema="filterForm" />
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

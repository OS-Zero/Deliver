<script lang="ts" setup>
import { ref, onBeforeMount, reactive, h, watch, onUnmounted } from 'vue'
import { deletePeopleGroup, getExcelTemplateFile, getPeopleGroup } from '@/api/peopleGroup'
import { filterSchema, peopleGroupColumns } from "@/config/peopleGroup"
import { CopyOutlined, DownOutlined, ExclamationCircleOutlined, FilterOutlined, CloseOutlined } from '@ant-design/icons-vue';
import { copyToClipboard, getDataFromSchema } from '@/utils/utils';
import { FormInstance, message, Modal, TableProps } from 'ant-design-vue';
import SearchInput from '@/components/SearchInput/index.vue'
import { debounce } from 'lodash'
import { usePagination } from '@/hooks/table';
import { Key } from 'ant-design-vue/lib/_util/type';
import { PeopleGroup } from '@/types/peopleGroup';
import PeopleGroupDrawer from './components/PeopleGroupDrawer.vue';

type Operation = 'add' | 'edit' | 'delete' | 'more' | 'download'
const dataSource = ref<PeopleGroup[]>([])
const filterForm = reactive(filterSchema)
const searchValue = ref('')
const handleSearch = async () => {
	const { records, total } = await getPeopleGroup({ peopleGroupName: searchValue.value, ...getDataFromSchema(filterForm), pageSize: pagination.pageSize, currentPage: pagination.current })
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
			title: '确认删除该人群?',
			icon: h(ExclamationCircleOutlined),
			okText: '确认',
			cancelText: '取消',
			async onOk() {
				await deletePeopleGroup({ ids: [record.taskId] })
				pagination.current = 1
				pagination.pageSize = 10
				handleSearch()
				message.success('删除成功')
			},
		});
	},
	download: () => {
		getExcelTemplateFile()
	}
}
const handleActions = async (operation: Operation, record: Record<string, any> = {}) => {
	(operation === 'add' || operation === 'edit' || operation === 'more') && (drawerState.operation = operation, drawerState.open = true);
	(operation === 'edit' || operation === 'more') && (drawerState.record = record);
	(operation === 'delete') && operationDispatch[operation](record);
	(operation === 'download') && operationDispatch[operation]();
}
const handleBatchDelete = () => {
	Modal.confirm({
		title: '确认批量删除人群?',
		icon: h(ExclamationCircleOutlined),
		okText: '确认',
		cancelText: '取消',
		async onOk() {
			await deletePeopleGroup({ ids: rowSelection.selectedRowKeys as number[] })
			pagination.current = 1
			pagination.pageSize = 10
			handleSearch()
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
	(drawerState.operation === 'add' || drawerState.operation === 'edit') && handleSearch();
}
onBeforeMount(() => {
	handleSearch()
})
onUnmounted(() => {
	stop()
})
</script>

<template>
	<div class="container">
		<div class="container-table">
			<div class="table-header">
				<SearchInput class="search_input" placeholder="请输入人群名" v-model="searchValue" @search="debounceSearch()">
				</SearchInput>
				<div class="operation">
					<a-button class="btn--add" @click="handleActions('download')">下载人群模板文件</a-button>
					<a-button class="btn--add" @click="handleActions('add')" type="primary">新增</a-button>
					<a-button :icon="h(FilterOutlined)" shape="circle" type="text"
						@click="filterState.open = !filterState.open"></a-button>
				</div>
			</div>
			<div class="selections--delete" v-show="rowSelection.selectedRowKeys?.length">
				<div>已选择 {{ rowSelection.selectedRowKeys?.length }} 项</div>
				<div class="selections_btns">
					<a-button type="link" @click="handleCancelBatchDelete">取消选择</a-button>
					<a-button type="link" danger @click="handleBatchDelete">批量删除</a-button>
				</div>
			</div>
			<a-table row-key="peopleGroupId" :dataSource="dataSource" :columns="peopleGroupColumns"
				:row-selection="rowSelection" :pagination="pagination" :scroll="{ x: 1400, y: 680 }">
				<template #bodyCell="{ column, text, record }">
					<template v-if="column.key === 'peopleGroupId'">
						{{ text }}
						<CopyOutlined class="id--copy" @click="copyId(text)" />
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
		<PeopleGroupDrawer v-bind="drawerState" @close="handleDrawerClose">
		</PeopleGroupDrawer>
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

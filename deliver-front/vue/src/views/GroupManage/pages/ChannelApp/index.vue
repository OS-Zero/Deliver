<script lang="ts" setup>
import { ref, onBeforeMount, reactive, nextTick, h, onUnmounted, watch } from 'vue'
import { channelAppColumns, channelAppSchema, channelAppSchemaDeps, filterSchema, filterSchemaMaps, channelAppLocale } from "@/config/channelApp"
import { CopyOutlined, DownOutlined, ExclamationCircleOutlined, FilterOutlined, CloseOutlined } from '@ant-design/icons-vue';
import { copyToClipboard, dynamic, getDataFromSchema } from '@/utils/utils';
import { FormInstance, message, Modal, TableProps } from 'ant-design-vue';
import Drawer from '@/components/Drawer/index.vue'
import SearchInput from '@/components/SearchInput/index.vue'
import { debounce } from 'lodash'
import { usePagination } from '@/hooks/table';
import { Key } from 'ant-design-vue/lib/_util/type';
import { deleteChannelApp, getChannelApp, saveChannelApp, updateChannelApp } from '@/api/channelApp';
import { ChannelApp } from '@/types/channelApp';
import { updateAppStatus } from '@/api/app';
const dataSource = reactive<ChannelApp[]>([])


const { dynamicData: channelAppForm, stop } = dynamic(channelAppSchema, channelAppSchemaDeps)
const { dynamicData: filterForm, stop: stopFilterDynamic } = dynamic(filterSchema, filterSchemaMaps)
const handleSearch = async () => {
	const { records, total } = await getChannelApp({ ...getDataFromSchema(filterForm), pageSize: pagination.pageSize, currentPage: pagination.current })
	Object.assign(dataSource, records)
	pagination.total = total
}
const debounceSearch = debounce(handleSearch, 200)
const { pagination } = usePagination(handleSearch)
watch(filterForm, () => {
	debounceSearch()
})
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
const handleActions = async (action: 'add' | 'edit' | 'delete' | 'more', record?: Record<string, any>) => {
	drawerState.placement = 'right'
	if (action === 'add') {
		drawerState.title = '新增应用'
		drawerState.open = true
		drawerState.operation = 'add'
	} else if (action === 'edit') {
		drawerState.title = '编辑应用'
		drawerState.open = true
		drawerState.operation = 'edit'
		//异步使重置数据为空
		nextTick(() => {
			for (const key in channelAppForm) {
				channelAppForm[key].value = record && record[key]
			}
		})
	} else if (action === 'delete') {
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
	} else if (action === 'more') {
		drawerState.title = '应用详情'
		drawerState.operation = 'showMore'
		drawerState.placement = 'left'
		drawerState.extra = false
		drawerState.open = true
		const set = new Set(['usersType', 'channelType', 'channelProviderType', 'appId'])
		const arr: Array<{ label: string; value: any }> = []
		for (const key in record) {
			if (!set.has(key)) {
				arr.push({
					label: channelAppLocale[key],
					value: record[key]
				})
			}
		}
		Object.assign(moreInfo, arr)
	}
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
const handleDrawer = {
	ok: async () => {
		try {
			await formRef.value?.validate()
			if (drawerState.operation === 'add') {
				await saveChannelApp(getDataFromSchema(channelAppForm))
				message.success('添加成功')
			} else if (drawerState.operation == 'edit') {
				await updateChannelApp(getDataFromSchema(channelAppForm))
				message.success('编辑成功')
			} else if (drawerState.operation === 'more') {
				drawerState.placement = 'right'
			}
			drawerState.open = false
			formRef.value?.resetFields()
		} catch (error) {
			console.log(error);
		}
	},
	cancel: () => {
		drawerState.open = false
		drawerState.extra = true
		formRef.value?.resetFields()
	}
}
const handleFilterClose = () => {
	filterState.open = false
	formRef.value?.resetFields()
}
const changeStatus = (record: Record<string, any>) => {
	updateAppStatus({ appId: record.appId, appStatus: Number(record.appStatus) })
}
onBeforeMount(() => {
	handleSearch()
})
onUnmounted(() => {
	//停止监听动态数据
	stop()
	stopFilterDynamic()
})
</script>

<template>
	<div class="container">
		<div class="container-table">
			<div class="table-header">
				<SearchInput placeholder="请输入应用名" v-model="searchValue" @search="debounceSearch()">
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
				<template #bodyCell="{ column, text, record }">
					<template v-if="column.key === 'appId'">
						{{ text }}
						<CopyOutlined class="id--copy" @click="copyId(text)" />
					</template>
					<template v-else-if="column.key === 'appStatus'">
						<a-switch v-model:checked="record[column.key]" checked-children="开启" un-checked-children="关闭"
							@click="changeStatus(record)" />
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
			<Form layout="vertical" ref="formRef" :form-schema="filterForm" />
		</a-card>
		<Drawer :placement="drawerState.placement" :open="drawerState.open" :title="drawerState.title"
			:extra="drawerState.extra" @ok="handleDrawer.ok" @close="handleDrawer.cancel">
			<Form v-if="drawerState.operation === 'add' || drawerState.operation === 'edit'" ref="formRef"
				:label-col="{ span: 7 }" :form-schema="channelAppForm" />
			<div v-else-if="drawerState.operation === 'showMore'">
				<a-descriptions :column="1">
					<a-descriptions-item v-for="item in moreInfo" :label="item.label">{{ item.value }}</a-descriptions-item>
				</a-descriptions>
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


.input--search {
	width: 300px;
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

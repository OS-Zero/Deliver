<script lang="ts" setup>
import { ref, onBeforeMount, reactive, nextTick, h, onUnmounted, watch } from 'vue'
import { addMessageTemplate, deleteMessageTemplate, getMessageTemplates, testSendMessage, updateMessageTemplate, updateMessageTemplateStatus } from '@/api/messageTemplate'
import { messageTemplateColumns, messageTemplateSchema, messageTemplateSchemaDeps, filterSchema, testMessageSchema, filterSchemaMaps, messageTemplateLocale } from "@/config/messageTemplate"
import { CopyOutlined, DownOutlined, ExclamationCircleOutlined, FilterOutlined, CloseOutlined } from '@ant-design/icons-vue';
import { copyToClipboard, dynamic, getDataFromSchema } from '@/utils/utils';
import { FormInstance, message, Modal, TableProps } from 'ant-design-vue';
import Drawer from '@/components/Drawer/index.vue'
import SearchInput from '@/components/SearchInput/index.vue'
import { debounce } from 'lodash'
import { usePagination } from '@/hooks/table';
import { Key } from 'ant-design-vue/lib/_util/type';
import { MessageTemplate } from '@/types/messageTemplate';
const dataSource = reactive<MessageTemplate[]>([])


const { dynamicData: messageTemplateForm, stop } = dynamic(messageTemplateSchema, messageTemplateSchemaDeps)
const { dynamicData: filterForm, stop: stopFilterDynamic } = dynamic(filterSchema, filterSchemaMaps)
const handleSearch = async () => {
	const { records, total } = await getMessageTemplates({ ...getDataFromSchema(filterForm), pageSize: pagination.pageSize, currentPage: pagination.current })
	Object.assign(dataSource, records)
	pagination.total = total
}
const debounceSearch = debounce(handleSearch, 200)
const { pagination } = usePagination(handleSearch)
watch(filterForm, () => {
	debounceSearch()
})
const testMessageForm = reactive(testMessageSchema)
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
}>({
	open: false,
	title: '',
	operation: '',
	placement: "right"
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
const handleActions = async (action: 'add' | 'edit' | 'delete' | 'more' | 'testSend', record?: Record<string, any>) => {
	drawerState.placement = 'right'
	if (action === 'add') {
		drawerState.title = '新增模板'
		drawerState.open = true
		drawerState.operation = 'add'
	} else if (action === 'edit') {
		drawerState.title = '编辑模板'
		drawerState.open = true
		drawerState.operation = 'edit'
		//异步使重置数据为空
		nextTick(() => {
			for (const key in messageTemplateForm) {
				messageTemplateForm[key].value = record && record[key]
			}
		})
	} else if (action === 'delete') {
		Modal.confirm({
			title: '确认删除该分组?',
			icon: h(ExclamationCircleOutlined),
			okText: '确认',
			cancelText: '取消',
			async onOk() {
				await deleteMessageTemplate({ ids: record && record.templateId })
				message.success('删除成功')
			},
		});
	} else if (action === 'testSend') {
		drawerState.title = '测试发送'
		drawerState.open = true
		drawerState.operation = 'testSend'
	} else if (action === 'more') {
		drawerState.title = '模板详情'
		drawerState.open = true
		drawerState.operation = 'showMore'
		drawerState.placement = 'left'
		const set = new Set(['usersType', 'channelType', 'channelProviderType', 'messageType'])
		const arr: Array<{ label: string; value: any }> = []
		for (const key in record) {
			if (!set.has(key)) {
				arr.push({
					label: messageTemplateLocale[key],
					value: record[key]
				})
			}
		}
		Object.assign(moreInfo, arr)
	}
}
const handleBatchDelete = () => {
	Modal.confirm({
		title: '确认批量删除分组?',
		icon: h(ExclamationCircleOutlined),
		okText: '确认',
		cancelText: '取消',
		async onOk() {
			await deleteMessageTemplate({ ids: rowSelection.selectedRowKeys as number[] })
			message.success('删除成功')
		},
	});
}
const handleDrawer = {
	ok: async () => {
		try {
			await formRef.value?.validate()
			if (drawerState.operation === 'add') {
				await addMessageTemplate(getDataFromSchema(messageTemplateForm))
				message.success('添加成功')
			} else if (drawerState.operation == 'edit') {
				await updateMessageTemplate(getDataFromSchema(messageTemplateForm))
				message.success('编辑成功')
			} else if (drawerState.operation === 'testSend') {
				await testSendMessage(getDataFromSchema(testMessageForm))
				message.success('发送成功')
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
		formRef.value?.resetFields()
	}
}
const changeStatus = (record: Record<string, any>) => {
	updateMessageTemplateStatus({ templateId: record.templateId, templateStatus: Number(record.templateStatus) })
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
				<SearchInput placeholder="模糊查询模板" v-model="searchValue" @search="debounceSearch()">
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
				:row-selection="rowSelection" :pagination="pagination">
				<template #bodyCell="{ column, text, record }">
					<template v-if="column.key === 'templateId'">
						{{ text }}
						<CopyOutlined class="id--copy" @click="copyId(text)" />
					</template>
					<template v-if="column.key === 'templateStatus'">
						<a-switch v-model:checked="record[column.key]" @click="changeStatus(record)" />
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
										<div @click="handleActions('testSend')">测试发送</div>
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
			<template #extra><a-button type="text" :icon="h(CloseOutlined)"
					@click="filterState.open = false"></a-button></template>
			<Form layout="vertical" ref="formRef" :form-schema="filterForm" />
		</a-card>
		<Drawer :placement="drawerState.placement" :open="drawerState.open" :title="drawerState.title" @ok="handleDrawer.ok"
			@close="handleDrawer.cancel">
			<Form v-if="drawerState.operation === 'add' || drawerState.operation === 'edit'" ref="formRef"
				:label-col="{ span: 7 }" :form-schema="messageTemplateForm" />
			<Form v-else-if="drawerState.operation === 'testSend'" ref="formRef" :label-col="{ span: 7 }"
				:form-schema="testMessageForm" layout="vertical" />
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

.ant-table {
	border: 1px solid var(--gray-lighter);
}

.ant-card {
	border-radius: 0;
	padding: 0;
	margin-left: var(--spacing-md);
}

::v-deep .ant-card-head {
	border: none;
	font-size: large;
}

.filter-form {
	height: 100%;
	overflow: hidden;
	width: 0;
	transition: width 120ms;
	border: none;

	&.open {
		border-left: 1px solid var(--gray-lighter);
		width: 300px;
	}
}

.container-table {
	flex: 1;
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

	&:active {
		color: var(--blue-darker);
	}
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

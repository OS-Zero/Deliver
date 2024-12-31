<script lang="ts" setup>
import { ref, onBeforeMount, reactive, nextTick, h, onUnmounted } from 'vue'
import { addMessageTemplate, deleteMessageTemplate, getMessageTemplates, testSendMessage, updateMessageTemplate, updateMessageTemplateStatus } from '@/api/messageTemplate'
import { SearchParams } from '@/types/messageTemplate';
import { messageTemplateColumns, messageTemplateSchema, messageTemplateSchemaDeps, filterSchema, testMessageSchema } from "@/config/messageTemplate"
import { MessageTemplate } from './type';
import { CopyOutlined, DownOutlined, ExclamationCircleOutlined, FilterOutlined, CloseOutlined } from '@ant-design/icons-vue';
import { copyToClipboard, dynamic, getDataFromSchema } from '@/utils/utils';
import { FormInstance, message, Modal } from 'ant-design-vue';
import Drawer from '@/components/Drawer/index.vue'
import SearchInput from '@/components/SearchInput/index.vue'
const dataSource = reactive<MessageTemplate[]>([])

const searchParams = reactive<SearchParams>(
	{
		templateName: "String",
		usersType: 0,
		channelType: 0,
		channelProviderType: 0,
		messageType: '',
		templateStatus: 0,
		startTime: "yyyy-MM-dd HH:mm:ss",
		endTime: "yyyy-MM-dd HH:mm:ss",
		currentPage: 1,
		pageSize: 10
	}
)
const { dynamicData: messageTemplateForm, stop } = dynamic(messageTemplateSchema, messageTemplateSchemaDeps)
const filterForm = reactive(filterSchema)
const testMessageForm = reactive(testMessageSchema)
const moreInfo = reactive<Array<{ label: string; value: any }>>([])
const copyId = async (text: string) => {
	try {
		await copyToClipboard(text)
		message.success('复制成功')
	} catch (error) {
		console.log(error);
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
		const set = new Set()
		messageTemplateColumns.forEach(col => set.add(col.key))
		const arr: Array<{ label: string; value: any }> = []
		for (const key in record) {
			if (!set.has(key)) {
				arr.push({
					label: key,
					value: record[key]
				})
			}
		}
		Object.assign(moreInfo, arr)
	}
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
	updateMessageTemplateStatus({ templateId: record.templateId, templateStatus: record.templateStatus })
}
onBeforeMount(async () => {
	const { records } = await getMessageTemplates(searchParams)
	Object.assign(dataSource, records)
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
				<SearchInput placeholder="模糊查询模板" @search=""></SearchInput>
				<div class="operation">
					<a-button class="btn--add" @click="handleActions('add')" type="primary">新增</a-button>
					<a-tooltip placement="top">
						<template #title>
							<span>筛选</span>
						</template>
						<a-button :icon="h(FilterOutlined)" shape="circle" type="text"
							@click="filterState.open = !filterState.open"></a-button>
					</a-tooltip>
				</div>
			</div>
			<a-table :dataSource="dataSource" :columns="messageTemplateColumns" :scroll="{ x: 800 }">
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
			<Form layout="vertical" :label-col="{ span: 4 }" ref="formRef" :form-schema="filterForm" />
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
}

.btn--add {
	margin-right: var(--spacing-md);
}

.ant-table {
	border: 1px solid var(--gray-lighter);
}

.ant-card {
	border-radius: 0;
}

.filter-form {
	margin-left: var(--spacing-xs);
	overflow: hidden;
	width: 0;
	transition: width 120ms;
	border: none;

	&.open {
		border: 1px solid var(--gray-lighter);
		padding: var(--spacing-md);
		width: 450px;
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
	padding: var(--spacing-xs);
	border: 1px solid var(--gray-lighter);
}

.id--copy {
	cursor: pointer;

	&:active {
		color: var(--blue-darker);
	}
}
</style>

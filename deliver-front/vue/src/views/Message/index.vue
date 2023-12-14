<script lang="ts" setup>
import { ref, onMounted } from 'vue'
import { message } from 'ant-design-vue'
import { useMessageStore } from '@/store/modules/message'
import type { AddTemp, MessageTemplate, SearchModel, SendTestMessage } from './type'
import { searchForm } from '@/config/form'
import { tableHeader, tableColumns, tableHeaderOptions, tableOptions } from '@/config/table'
import emitter from '@/utils/mitt'
import { deleteTemplate } from '@/api/message'
const messageStore = useMessageStore()

//搜索框配置
const fieldList = searchForm.messageSearchForm
//搜索框数据
const searchModel = ref<SearchModel>({
	templateName: '',
	pushRange: undefined,
	usersType: undefined,
	period: undefined,
	startTime: undefined,
	endTime: undefined
})

/**
 * 搜索请求表单数据
 * @param data
 */
const searchTemplate = async (data: SearchModel | undefined, msg: string): Promise<void> => {
	try {
		const { records } = await messageStore.getTemplatePages(data !== undefined ? data : searchModel.value)
		emitter.emit('loading', false)
		emitter.emit('iconLoading', false)
		tableModel.value = records
		if (msg !== '') message.success(msg)
	} catch (error) {
		console.error('An error occurred:', error)
		message.error(error)
	}
}

//表格头数据
const tableHeaderData = ref<AddTemp>({
	templateName: '',
	pushRange: undefined,
	usersType: undefined,
	pushWays: '',
	templateStatus: 0,
	appId: undefined,
	channelType: undefined,
	messageType: ''
})
/**
 * 添加模板
 * @param param
 */
const addTemp = async (param: AddTemp) => {
	try {
		await messageStore.addTemplatePages(param)
		searchTemplate(undefined, '')
		message.success('添加成功')
	} catch (error) {
		console.error('An error occurred:', error)
		message.error(error)
	}
}

//表格数据
const tableModel = ref<MessageTemplate[]>([])

/**
 * 修改表格数据
 * @param param
 */
const updatetemplate = async (param: AddTemp) => {
	try {
		await messageStore.updatetemplate(param)
		emitter.emit('loading', true)
		searchTemplate(undefined, '')
		message.success('修改成功')
	} catch (error) {
		console.error('An error occurred:', error)
		message.error(error)
	}
}

/**
 * 删除表格数据
 * @param param
 */
const deleteTemp = async (ids: Array<number>) => {
	try {
		await deleteTemplate({ ids })
		emitter.emit('loading', true)
		searchTemplate(undefined, '')
		message.success('删除成功')
	} catch (error) {
		console.error('An error occurred:', error)
		message.error(error)
	}
}

/**
 * 发送测试消息
 */
const sendTestMessage = async (data: SendTestMessage) => {
	try {
		await messageStore.sendTestMes(data)
		message.success('发送成功')
	} catch (error) {
		console.error('An error occurred:', error)
		message.error(error)
	}
}

/**
 * 按钮点击事件
 * @param command
 * @param callback
 */
const handleAction = (command: string, val: any, callback: any) => {
	console.log(val)

	switch (command) {
		case 'edit':
			callback(updatetemplate)
			break
		case 'send':
			callback(sendTestMessage)
			break
		case 'delete':
			if (Array.isArray(val)) deleteTemp(val)
			else deleteTemp([val])
			break
		default:
			break
	}
}
onMounted(() => {
	searchTemplate(undefined, '')
})
</script>

<template>
	<div id="message-container">
		<SearchForm :fieldList="fieldList" :model="searchModel" @submit="searchTemplate"></SearchForm>
		<section>
			<TableHeader
				:options="tableHeaderOptions.templateOption"
				:config="tableHeader.editTemplateField"
				:model="tableHeaderData"
				@reflash="searchTemplate(undefined, '')"
				@submit="addTemp" />
			<Table :options="tableOptions.templateOption" :model="tableModel" :columns="tableColumns" @actions="handleAction"></Table>
		</section>
	</div>
</template>

<style lang="scss" scoped>
#message-container {
	section {
		padding: 12px;
		margin-top: 12px;
		background-color: #fff;
	}
}
</style>

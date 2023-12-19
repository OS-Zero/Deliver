<script lang="ts" setup>
import { ref, onBeforeMount } from 'vue'
import { message } from 'ant-design-vue'
import { useMessageStore } from '@/store/modules/message'
import type { AddTemp, MessageTemplate, SearchModel, SendTestMessage, TemplateItem } from './type'
import { searchForm } from '@/config/form'
import { tableHeader, tableColumns, tableHeaderOptions, tableOptions } from '@/config/table'
import emitter from '@/utils/mitt'
import { deleteTemplate, updateStatus } from '@/api/message'
const messageStore = useMessageStore()

//搜索框配置
const fieldList = searchForm.messageSearchForm
//搜索框数据
const searchModel = ref<SearchModel>({
	templateName: undefined,
	pushRange: undefined,
	usersType: undefined,
	period: undefined,
	startTime: undefined,
	endTime: undefined
})
const searchSubmit = (val: any, msg: string) => {
	searchTemplate(val, msg, {
		currentPage: tableOptions.templateOption.paginationConfig.current,
		pageSize: tableOptions.templateOption.paginationConfig.pageSize
	})
}
/**
 * 搜索请求表单数据
 * @param data
 */
const searchTemplate = async (data: SearchModel | undefined, msg: string, page?: { currentPage: number; pageSize: number }): Promise<void> => {
	console.log(data)

	try {
		const { records, total } = await messageStore.getTemplatePages(data !== undefined ? data : searchModel.value, page)
		tableOptions.templateOption.paginationConfig.total = total

		tableModel.value = records
		if (msg !== '') message.success(msg)
	} catch (error) {
		console.error('An error occurred:', error)
		message.error(error)
	} finally {
		emitter.emit('loading', false)
		emitter.emit('iconLoading', false)
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
const addTemp = async (param: AddTemp, callback: (err: boolean) => void) => {
	try {
		await messageStore.addTemplatePages(param)
		tableOptions.templateOption.paginationConfig.current = 1
		emitter.emit('loading', true)
		callback(false)
		searchTemplate(undefined, '', {
			currentPage: tableOptions.templateOption.paginationConfig.current,
			pageSize: tableOptions.templateOption.paginationConfig.pageSize
		})
		message.success('添加成功')
	} catch (error) {
		callback(true)
		console.error('An error occurred:', error)
		message.error(error)
	}
}

const reflash = () => {
	emitter.emit('loading', true)
	searchTemplate(undefined, '', {
		currentPage: tableOptions.templateOption.paginationConfig.current,
		pageSize: tableOptions.templateOption.paginationConfig.pageSize
	})
}

//表格数据
const tableModel = ref<MessageTemplate[]>([])

/**
 * 修改表格数据
 * @param param
 */
const updatetemplate = async (param: AddTemp, callback: (err: boolean) => void) => {
	try {
		await messageStore.updatetemplate(param)
		callback(false)
		emitter.emit('loading', true)
		searchTemplate(undefined, '', {
			currentPage: tableOptions.templateOption.paginationConfig.current,
			pageSize: tableOptions.templateOption.paginationConfig.pageSize
		})
		message.success('修改成功')
	} catch (error) {
		callback(true)
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
		tableOptions.templateOption.paginationConfig.current = 1
		searchTemplate(undefined, '', {
			currentPage: tableOptions.templateOption.paginationConfig.current,
			pageSize: tableOptions.templateOption.paginationConfig.pageSize
		})
		message.success('删除成功')
	} catch (error) {
		console.error('An error occurred:', error)
		message.error(error)
	}
}

/**
 * 发送测试消息
 */
const sendTestMessage = async (data: SendTestMessage, callback: (err: boolean) => void) => {
	try {
		await messageStore.sendTestMes(data)
		message.success('发送成功')
		callback(false)
	} catch (error) {
		callback(true)
		console.error('An error occurred:', error)
		message.error(error)
	}
}

/**
 * 更新状态
 */
const updateStatu = async (obj: TemplateItem) => {
	try {
		await updateStatus(obj)
		message.success('修改成功')
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
	switch (command) {
		case 'search':
			tableOptions.templateOption.paginationConfig.current = val.currentPage
			tableOptions.templateOption.paginationConfig.pageSize = val.pageSize
			searchTemplate(undefined, '', val)
			break
		case 'status':
			updateStatu(val)
			break
		case 'edit':
			callback(updatetemplate)
			break
		case 'clone':
			callback(addTemp)
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
onBeforeMount(() => {
	searchTemplate(undefined, '')
})
</script>

<template>
	<div id="message-container">
		<SearchForm :fieldList="fieldList" :model="searchModel" @submit="searchSubmit"></SearchForm>
		<section>
			<TableHeader
				:options="tableHeaderOptions.templateOption"
				:config="tableHeader.editTemplateField"
				:model="tableHeaderData"
				@reflash="reflash"
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

<script lang="ts" setup>
import { ref } from 'vue'
import { useMessageStore } from '@/store/modules/message'
import type { AddTemp, MessageTemplate, SearchModel } from './type'
import { searchForm } from '@/config/form'
import { tableHeader, tableColumns } from '@/config/table'
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
const searchTemplate = async (data: SearchModel | undefined): Promise<void> => {
	try {
		const { records } = await messageStore.getTemplatePages(data !== undefined ? data : searchModel.value)
		tableModel.value = records
	} catch (error) {
		console.error('An error occurred:', error)
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

//表单数据
const tableModel = ref<MessageTemplate[]>([])
</script>

<template>
	<div id="message-container">
		<SearchForm :fieldList="fieldList" :model="searchModel" @submit="searchTemplate"></SearchForm>
		<section>
			<TableHeader :config="tableHeader.templateField" :model="tableHeaderData" @reflash="searchTemplate" />
			<Table :model="tableModel" :columns="tableColumns"></Table>
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

import request from '@/utils/request'
import type { searchMessage, addTemp, sendMessageTest } from '@/views/Message/type'

export async function getTemplatePages(data: searchMessage): Promise<any> {
	return await request({
		url: '/template/search',
		method: 'post',
		data
	})
}

export async function getMessageType(data: { channelType: number }): Promise<any> {
	return await request({
		url: '/template/getMessageTypeByChannelType',
		method: 'post',
		data
	})
}

export async function getApp(data: { channelType: number }): Promise<any> {
	return await request({
		url: '/app/getAppByChannelType',
		method: 'post',
		data
	})
}

export async function addTemplatePages(data: addTemp): Promise<any> {
	return await request({
		url: '/template/saveTemplate',
		method: 'post',
		data
	})
}

export async function updateStatus(data: {
	templateId: number
	templateStatus: number
}): Promise<any> {
	return await request({
		url: '/template/updateStatusById',
		method: 'post',
		data
	})
}

export async function updatetemplate(data: any): Promise<any> {
	return await request({
		url: '/template/updateById',
		method: 'post',
		data
	})
}

export async function deleteTemplate(data: { ids: number[] }): Promise<any> {
	return await request({
		url: '/template/deleteByIds',
		method: 'post',
		data
	})
}

export async function sendTestMes(data: sendMessageTest): Promise<any> {
	return await request({
		url: '/template/testSendMessage',
		method: 'post',
		data
	})
}

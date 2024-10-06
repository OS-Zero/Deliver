import request from '@/utils/request.ts'
import type { searchMessage, addTemp, updateTemp } from '@/views/Channel/type'

export async function getAppInfo(data: searchMessage): Promise<any> {
	return await request({
		url: '/app/search',
		method: 'post',
		data
	})
}

export async function addAppItem(data: addTemp): Promise<any> {
	return await request({
		url: '/app/save',
		method: 'post',
		data
	})
}

export async function deleteAppInfo(data: { ids: number[] }): Promise<any> {
	return await request({
		url: '/app/deleteByIds',
		method: 'post',
		data
	})
}

export async function updateAppStatus(data: { appId: number; appStatus: number }): Promise<any> {
	return await request({
		url: '/app/updateStatusById',
		method: 'post',
		data
	})
}

export async function updateAppItem(data: updateTemp): Promise<any> {
	return await request({
		url: '/app/updateById',
		method: 'post',
		data
	})
}

export async function getAppConfigByChannelType(data: { channelType: number }): Promise<any> {
	return await request({
		url: '/app/getAppConfigByChannelType',
		method: 'post',
		data
	})
}

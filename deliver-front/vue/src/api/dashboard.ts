import request from '@/utils/request'

export interface DashboardHeadData {
	numberOfMessagesToday?: string
	numberOfPlatformFiles?: string
	accumulatedTemplateOwnership?: string
	numberOfApps?: string
}

type barDataPoint = [string, number, number]
interface pieDataPoint {
	value: number
	name: string
}

export type messageDataSource = barDataPoint[]
export type templeteDataSource = pieDataPoint[]
export type channelDataSource = pieDataPoint[]
export type userDataSource = pieDataPoint[]

export async function getDashboardHeadData(): Promise<any> {
	return await request({
		url: '/dashboard/getDashboardHeadData',
		method: 'post'
	})
}
export async function getMessageInfo(data: { dateSelect: number }): Promise<any> {
	return await request({
		url: '/dashboard/getMessageInfo',
		method: 'post',
		data
	})
}
export async function getTemplateInfo(data: { dateSelect: number }): Promise<any> {
	return await request({
		url: '/dashboard/getTemplateInfo',
		method: 'post',
		data
	})
}
export async function getAppInfo(data: { dateSelect: number }): Promise<any> {
	return await request({
		url: '/dashboard/getAppInfo',
		method: 'post',
		data
	})
}
export async function getPushUserInfo(data: { dateSelect: number }): Promise<any> {
	return await request({
		url: '/dashboard/getPushUserInfo',
		method: 'post',
		data
	})
}

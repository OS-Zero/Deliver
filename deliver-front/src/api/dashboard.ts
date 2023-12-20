import request from '@/utils/request.ts'
import { R } from '@/types'
import { DashboardHeadData, MessageInfoList, DashboardInfoList } from '@/views/Dashboard/type'

/**
 * 请求控制面板头部数据
 * @returns {Promise<R<DashboardHeadData>>} 控制面板头部数据
 */
export async function getDashboardHeadData(): Promise<R<DashboardHeadData>> {
	return await request({
		url: '/dashboard/getDashboardHeadData',
		method: 'post'
	})
}

/**
 * 请求消息配置数据
 * @param {{dateSelect: number}} data - 0、1、2、3 分别代表 日、周、月、年
 * @returns {Promise<R<DashboardHeadData>>} 消息配置数据
 */
export async function getMessageInfo(data: { dateSelect: number }): Promise<R<MessageInfoList>> {
	return await request({
		url: '/dashboard/getMessageInfo/vue',
		method: 'post',
		data
	})
}

/**
 * 请求模板配置数据
 * @param {{dateSelect: number}} data - 0、1、2、3 分别代表 日、周、月、年
 * @returns {Promise<R<DashboardHeadData>>} 模板配置数据
 */
export async function getTemplateInfo(data: { dateSelect: number }): Promise<R<DashboardInfoList>> {
	return await request({
		url: '/dashboard/getTemplateInfo',
		method: 'post',
		data
	})
}

/**
 * 请求 APP 配置数据
 * @param {{dateSelect: number}} data - 0、1、2、3 分别代表 日、周、月、年
 * @returns {Promise<R<DashboardHeadData>>} APP 配置数据
 */
export async function getAppInfo(data: { dateSelect: number }): Promise<R<DashboardInfoList>> {
	return await request({
		url: '/dashboard/getAppInfo',
		method: 'post',
		data
	})
}

/**
 * 请求推送用户配置数据
 * @param {{dateSelect: number}} data - 0、1、2、3 分别代表 日、周、月、年
 * @returns {Promise<R<DashboardHeadData>>} 推送用户数据
 */
export async function getPushUserInfo(data: { dateSelect: number }): Promise<R<DashboardInfoList>> {
	return await request({
		url: '/dashboard/getPushUserInfo',
		method: 'post',
		data
	})
}

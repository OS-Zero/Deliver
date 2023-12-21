import type { Dayjs } from 'dayjs'
import { Ref } from 'vue'

export interface AppInterface {
	/**
	 * Appid
	 */
	appId: number
	/**
	 * app名称
	 */
	appName: string
	/**
	 * 渠道类型
	 */
	channelType: number
	/**
	 * app配置
	 */
	appconfig: string
	/**
	 * app使用次数
	 */
	userCount: number
	/**
	 * app状态
	 */
	appStatus: number
	/**
	 * 创建用户
	 */
	createUser: string
	/**
	 * 创建时间
	 */
	createTime: string

	/**
	 * 最近更新时间
	 */
	updateTime: string

	showAppConfig: boolean

	appInfoOpen: Ref<boolean>
}

// 搜索框接口
export interface searchMessage {
	/**
	 * 模板名
	 */
	appName?: string
	/**
	 * 推送范围
	 */
	channelType?: number
	/**
	 * 当前页面序号
	 */
	currentPage: number
	/**
	 * 页面大小
	 */
	pageSize: number
	/**
	 * 起始日期
	 */
	startTime?: string
	/**
	 * 结束日期
	 */
	endTime?: string
	/**
	 * 时间段
	 */
	perid?: [Dayjs, Dayjs]
}

// 新增接口
export interface addTemp {
	/**
	 * app名称
	 */
	appName: string
	/**
	 * 渠道类型
	 */
	channelType: number | undefined
	/**
	 * App配置
	 */
	appConfig: string
	/**
	 * App状态
	 */
	appStatus: number | boolean
}

// 修改接口
export interface updateTemp {
	/**
	 * appId
	 */
	appId?: number
	/**
	 * app名称
	 */
	appName?: string
	/**
	 * 渠道类型
	 */
	channelType?: number | undefined
	/**
	 * App配置
	 */
	appConfig?: string
	/**
	 * App状态
	 */
	appStatus?: number | boolean
}

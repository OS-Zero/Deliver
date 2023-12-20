import type { Dayjs } from 'dayjs'

// 平台文件表格
export interface platformFile {
	id?: number
	fileName?: string
	appType?: number
	fileType?: string
	fileKey?: string
	fileStatus?: number
	createUser?: string
	createTime?: string
	appId?: number
}
export interface uploadFile {
	fileName?: string
	appType?: number
	fileType?: string
	appId?: number
}
// 搜索框接口
export interface searchPlatformFile {
	/**
	 * 文件名
	 */
	fileName?: string

	/**
	 * App 类型
	 */
	appType?: number

	/**
	 * 文件类型
	 */
	fileType?: string

	/**
	 * 文件 Key
	 */
	fileKey?: string

	/**
	 * appId
	 */
	appId?: number

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

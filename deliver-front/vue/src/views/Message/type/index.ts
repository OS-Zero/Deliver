import type { Dayjs } from 'dayjs'

export interface AppItem {
	appId: number
	appName: string
}

export interface MessageItem {
	code: string
	name: string
}

export interface AddTemplate {
	templateName: string
	pushRange: number
	usersType: number
	pushWays: string
	templateStatus: number
	appId: number
}
export interface TemplateItem {
	templateId: number
	templateStatus: number
}
/**
 * 消息接口
 */
export interface MessageTemplate {
	/**
	 * 模板id
	 */
	templateId: number
	/**
	 * 模板名
	 */
	templateName: string
	/**
	 * 推送范围
	 */
	pushRange: string
	/**
	 * 用户类型
	 */
	usersType: string
	/**
	 * 推送方式
	 */
	pushWays?: string
	/**
	 * 模板累计使用数
	 */
	useCount: number
	/**
	 * 模板状态
	 */
	templateStatus: number | boolean
	/**
	 * 创建用户
	 */
	createUser: string
	/**
	 * 创建时间
	 */
	createTime: string
	/**
	 * Appid
	 */
	appId: number
	/**
	 * App名称
	 */
	appName: string
}

/**
 * 搜索框接口
 */
export interface SearchMessage {
	/**
	 * 模板名
	 */
	templateName?: string
	/**
	 * 推送范围
	 */
	pushRange?: number
	/**
	 * 用户类型
	 */
	usersType?: number
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

/**
 * 新增接口
 */
export interface addTemp {
	/**
	 * 模板名
	 */
	templateName: string
	/**
	 * 推送范围
	 */
	pushRange: number | undefined
	/**
	 * 用户类型
	 */
	usersType: number | undefined
	/**
	 * 用户类型
	 */
	pushWays: string
	/**
	 * 渠道选择
	 */
	channelType?: string | undefined
	/**
	 * 消息类型
	 */
	messageType?: string
	/**
	 * Appid
	 */
	appId: number | undefined
	/**
	 * 模板状态
	 */
	templateStatus: number | boolean
}

/**
 * 编辑模板接口
 */
export interface modiTemp {
	/**
	 * 模板id
	 */
	templateId: number
	/**
	 * 模板名
	 */
	templateName: string
	/**
	 * 推送范围
	 */
	pushRange: number | undefined
	/**
	 * 用户类型
	 */
	usersType: number | undefined
	/**
	 * 用户类型
	 */
	pushWays: string
	/**
	 * 渠道选择
	 */
	channelType?: string | undefined | number
	/**
	 * 消息类型
	 */
	messageType?: string
	/**
	 * 模板状态
	 */
	templateStatus: number
	/**
	 * Appid
	 */
	appId: number | undefined | string
}

/**
 * 发送消息接口
 */
export interface sendMessageTest {
	/**
	 * 模板id
	 */
	templateId: number
	/**
	 * 用户列表
	 */
	users: string[]
	/**
	 * 传递参数
	 */
	paramMap: JSON
	/**
	 * 重试次数
	 */
	retry: number | undefined
}

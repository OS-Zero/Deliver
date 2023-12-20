// import { getApp, getMessageType } from '@/api/message'
import { getApp, getMessageType } from '@/api/message.ts'
import type { MessageTemplate } from '@/views/Message/type'

interface Channel {
	value: string
	label: string
}

// 表格数据渲染方法
export const getDate = (d): string => {
	const date = new Date(d)
	const year = date.getFullYear()
	const month = String(date.getMonth() + 1).padStart(2, '0')
	const day = String(date.getDate()).padStart(2, '0')
	const hour = String(date.getHours()).padStart(2, '0')
	const minute = String(date.getMinutes()).padStart(2, '0')
	const second = String(date.getSeconds()).padStart(2, '0')
	return `${year}-${month}-${day} ${hour}:${minute}:${second}`
}

export const getPushWays = (channelType: string | undefined, messageType: string | undefined): string => {
	const obj = {
		channelType: Number(channelType),
		messageType
	}
	return JSON.stringify(obj)
}

export const getPushRange = (s: number): string => {
	const pusharr = ['不限', '企业内部', '企业外部']
	return pusharr[s]
}

export const getUsersType = (s: number): string => {
	const usersarr = ['', '企业账号', '电话', '邮箱', '平台 UserId']
	return usersarr[s]
}

export const getMessageTypeArr = (s: string): string | undefined => {
	const arr = [
		{ id: '1', name: 'Text 消息' },
		//
		{ id: '4-1', name: '钉钉-图片消息' },
		{ id: '4-2', name: '钉钉-语音消息' },
		{ id: '4-3', name: '钉钉-文件消息' },
		{ id: '4-4', name: '钉钉-链接消息' },
		{ id: '4-5', name: '钉钉-OA 消息' },
		{ id: '4-6', name: '钉钉-markdown 消息' },
		{ id: '4-7', name: '钉钉-卡片消息' },
		//
		{ id: '5-1', name: '企业微信-图片消息' },
		{ id: '5-2', name: '企业微信-语音消息' },
		{ id: '5-3', name: '企业微信-视频消息' },
		{ id: '5-4', name: '企业微信-文件消息' },
		{ id: '5-5', name: '企业微信-文本卡片消息' },
		{ id: '5-6', name: '企业微信-图文消息(mpnews)' },
		{ id: '5-7', name: '企业微信-markdown 消息' },
		{ id: '5-8', name: '企业微信-小程序通知消息' },
		//
		{ id: '6-1', name: '飞书-富文本 post' },
		{ id: '6-2', name: '飞书-图片 image' },
		{ id: '6-3', name: '飞书-消息卡片 interactive' },
		{ id: '6-4', name: '飞书-分享群名片 share_chat' },
		{ id: '6-5', name: '飞书-分享个人名片 share_user' },
		{ id: '6-6', name: '飞书-语音 audio' },
		{ id: '6-7', name: '飞书-视频 media' },
		{ id: '6-8', name: '飞书-文件 file' },
		{ id: '6-9', name: '飞书-表情包 sticker' }
	]
	return arr.find((it) => it.id === s)?.name
}

export const changeTable = (item): any => {
	item.channelType = JSON.parse(item.pushWays).channelType
	item.messageType = JSON.parse(item.pushWays).messageType
	item.createTime = getDate(item.createTime)
	item.key = item.templateId
	return item
}
///

export const getAllMessage = (mod, record: MessageTemplate): void => {
	mod.updateTemp.templateId = record.templateId
	mod.updateTemp.templateName = record.templateName
	mod.updateTemp.pushRange = Number(record.pushRange)
	mod.updateTemp.usersType = Number(record.usersType)
	mod.updateTemp.pushWays = record.pushWays
	mod.updateTemp.messageType = record.messageType
	mod.updateTemp.appId = record.appId
	mod.updateTemp.channelType = record.channelType.toString()
	mod.updateTemp.templateStatus = record.templateStatus
	// 选项渲染
	mod.messageData.length = 0
	mod.appData.length = 0

	const Data: Channel[] = [
		{ value: '1', label: '电话' },
		{ value: '2', label: '短信' },
		{ value: '3', label: '邮件' },
		{ value: '4', label: '钉钉' },
		{ value: '5', label: '企业微信' },
		{ value: '6', label: '飞书' }
	]
	const user = mod.updateTemp.usersType
	if (user === 1) {
		mod.channelData = [...Data]
	} else if (user === 2) {
		mod.channelData = Data.filter((item) => item.value !== '3')
	} else if (user === 3) {
		mod.channelData = Data.filter((item) => item.value === '3')
	} else if (user === 4) {
		mod.channelData = Data.slice(3)
	}

	getMessageType({ channelType: mod.updateTemp.channelType })
		.then((res) => {
			res.data.forEach((item) => {
				mod.messageData.push(item)
			})
		})
		.catch((err) => {
			console.error('An error occurred:', err)
		})
	getApp({ channelType: mod.updateTemp.channelType })
		.then((res) => {
			res.data.forEach((item) => {
				mod.appData.push(item)
			})
		})
		.catch((err) => {
			console.error('An error occurred:', err)
		})
}

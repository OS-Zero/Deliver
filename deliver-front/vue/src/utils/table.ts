export function getColor(num: number) {
	const colors = ['green', 'blue', 'purple', 'cyan', 'orange', 'pink', 'red']
	return colors[num]
}

export const getMessageType = (s: string): string | undefined => {
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
		{ id: '6-9', name: '飞书-表情包 sticker' },
	]
	return arr.find((it) => it.id === s)?.name
}
export const getPushRange = (num: number): string => {
	const pusharr = ['不限', '企业内部', '企业外部']
	return pusharr[num]
}
export const getUsersType = (num: number): string => {
	const usersarr = ['', '企业账号', '电话', '邮箱', '平台 UserId']
	return usersarr[num]
}
export const getImg = (num: number): string => {
	const imgPaths = [
		'',
		'/assets/电话.png',
		'/assets/短信.png',
		'/assets/邮件.png',
		'/assets/钉钉.png',
		'/assets/企业微信.png',
		'/assets/飞书.png',
	]
	return imgPaths[num]
}

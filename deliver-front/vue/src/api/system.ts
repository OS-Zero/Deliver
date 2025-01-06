import { Channel, ChannelProvider } from '@/types/channelApp';
import { Message, User } from '@/types/messageTemplate';
import request from '@/utils/request.ts';

export function getChannelType(data: { usersType: User['usersType'] }): Promise<Channel[]> {
	return request({
		url: '/systemParam/getChannelType',
		method: 'post',
		data,
	});
}
export function getParam(data: { channelType: Channel['channelType'] }): Promise<{
	channelProviderTypeList: ChannelProvider[];
	messageTypeList: Message[];
}> {
	return request({
		url: '/systemParam/getParam',
		method: 'post',
		data,
	});
}
export function getMessageParam(data: { channelType: Channel['channelType']; messageType: Message['messageType'] }): Promise<string> {
	return request({
		url: '/systemParam/getMessageParam',
		method: 'post',
		data,
	});
}
export function getAppConfig(data: { channelType: Channel['channelType']; channelProviderType: Channel['channelTypeName'] }): Promise<string> {
	return request({
		url: '/systemParam/getAppConfig',
		method: 'post',
		data,
	});
}

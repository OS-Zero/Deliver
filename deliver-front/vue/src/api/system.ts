import { Channel, ChannelProvider } from '@/types/channelApp';
import { Message, User } from '@/types/messageTemplate';
import { PlatformFile } from '@/types/platformFile';
import request from '@/utils/request.ts';

export function getChannelType(data: { usersType: User['usersType'] }): Promise<Channel[]> {
	return request({
		url: '/systemParam/getChannelType',
		method: 'post',
		data,
	});
}
export function getChannelProviderType(data: { channelType: Channel['channelType'] }): Promise<ChannelProvider[]> {
	return request({
		url: '/systemParam/getChannelProviderType',
		method: 'post',
		data,
	});
}
export function getMessageParam(data: { messageType: Message['messageType'] }): Promise<string> {
	return request({
		url: '/systemParam/getMessageParam',
		method: 'post',
		data,
	});
}
export function getAppConfig(data: {
	channelType: Channel['channelType'];
	channelProviderType: ChannelProvider['channelProviderType'];
}): Promise<string> {
	return request({
		url: '/systemParam/getAppConfig',
		method: 'post',
		data,
	});
}
export function getPlatformFileType(data: {
	channelType: Channel['channelType'];
}): Promise<Array<Pick<PlatformFile, 'platformFileType' | 'platformFileTypeName'>>> {
	return request({
		url: '/systemParam/getPlatformFileType',
		method: 'post',
		data,
	});
}
export function getMessageType(data: {
	channelType: Channel['channelType'];
	channelProviderType: ChannelProvider['channelProviderType'];
}): Promise<Array<Message>> {
	return request({
		url: '/systemParam/getMessageType',
		method: 'post',
		data,
	});
}

import { Channel, channelProvider } from '@/types/channelApp';
import { Message } from '@/types/messageTemplate';
import request from '@/utils/request.ts';

export function getChannelType(data: { userType: number }): Promise<Channel[]> {
	return request({
		url: '/systemParam/getChannelType',
		method: 'post',
		data,
	});
}
export function getParam(data: { channelType: number }): Promise<{
	channelProviderTypeList: channelProvider[];
	messageTypeList: Message[];
}> {
	return request({
		url: '/systemParam/getParam',
		method: 'post',
		data,
	});
}

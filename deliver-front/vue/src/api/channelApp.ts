import { App, Channel, ChannelProvider } from '@/types/channelApp';
import request from '@/utils/request.ts';

export function getAppByChannel(data: {
	channelType: Pick<Channel, 'channelType'>;
	channelProviderType: Pick<ChannelProvider, 'channelProviderType'>;
}): Promise<App[]> {
	return request({
		url: '/channelApp/getAppByChannel',
		method: 'post',
		data,
	});
}

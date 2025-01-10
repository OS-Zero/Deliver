import { TableData } from '@/types';
import { App, Channel, ChannelApp, ChannelProvider, ChannelAppForm, SearchParams } from '@/types/channelApp';
import request from '@/utils/request.ts';

export function getChannelApp(data: SearchParams): Promise<TableData<ChannelApp>> {
	return request({
		url: '/channelApp/search',
		method: 'post',
		data,
	});
}

export function saveChannelApp(data: ChannelAppForm): Promise<never> {
	data.appConfig = JSON.stringify(data.appConfig || {});
	return request({
		url: '/channelApp/save',
		method: 'post',
		data,
	});
}
export function updateChannelApp(data: ChannelAppForm): Promise<never> {
	data.appConfig = JSON.stringify(data.appConfig || {});
	return request({
		url: '/channelApp/update',
		method: 'post',
		data,
	});
}
export function updateChannelAppStatus(data: { appId: App['appId']; appStatus: ChannelApp['appStatus'] }): Promise<never> {
	return request({
		url: '/channelApp/updateStatus',
		method: 'post',
		data,
	});
}
export function deleteChannelApp(data: { ids: App['appId'][] }): Promise<never> {
	return request({
		url: '/channelApp/delete',
		method: 'post',
		data,
	});
}

export function getAppByChannel(data: {
	channelType: Channel['channelType'];
	channelProviderType: ChannelProvider['channelProviderType'];
}): Promise<App[]> {
	return request({
		url: '/channelApp/getAppByChannel',
		method: 'post',
		data,
	});
}

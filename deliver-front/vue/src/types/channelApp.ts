import { TableSearchParams } from '.';
export interface App {
	appId: number;
	appName: string;
}
export interface Channel {
	channelType: number;
	channelTypeName: string;
}
export interface ChannelProvider {
	channelProviderType: number;
	channelProviderTypeName: string;
}

export interface ChannelApp extends App, Channel, ChannelProvider {
	appDescription: string;
	appConfig: string;
	appStatus: number;
	createUser: string;
	createTime: string;
}
export interface SearchParams extends TableSearchParams, Pick<Partial<ChannelApp>, 'appName' | 'channelType' | 'channelProviderType'> {
	startTime?: string;
	endTime?: string;
}

export type ChannelAppForm = Pick<ChannelApp, 'appName' | 'appDescription' | 'channelType' | 'channelProviderType' | 'appConfig' | 'appId'>;

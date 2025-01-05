import { Pagination } from '@/types';

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

export type SaveChannelApp = Pick<
  ChannelApp,
  'appName' | 'appDescription' | 'channelType' | 'channelProviderType' | 'appConfig'
>;

export type UpdateChannelApp = Pick<
  ChannelApp,
  'appId' | 'appStatus'
>;

export interface SearchParams
  extends Pagination,
    Pick<Partial<ChannelApp>, 'appName' | 'channelType' | 'channelProviderType'> {
  startTime?: string;
  endTime?: string;
}

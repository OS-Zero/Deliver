import { R, SearchData } from '@/types';
import {
  App,
  ChannelApp,
  SaveChannelApp,
  SearchParams,
  UpdateChannelApp
} from '@/views/GroupManage/pages/ChannelApp/type';
import request from '@/utils/request.ts';

export function getChannelApp(data: SearchParams): Promise<R<SearchData<ChannelApp>>> {
  return request({
    url: '/channelApp/search',
    method: 'post',
    data
  });
}

export function saveChannelApp(data: SaveChannelApp): Promise<never> {
  return request({
    url: '/channelApp/save',
    method: 'post',
    data
  });
}
export function updateChannelApp(data: UpdateChannelApp): Promise<never> {
  return request({
    url: '/channelApp/update',
    method: 'post',
    data
  });
}
export function updateChannelAppStatus(data: {
  appId: App['appId'];
  appStatus: ChannelApp['appStatus'];
}): Promise<never> {
  return request({
    url: '/channelApp/updateStatus',
    method: 'post',
    data
  });
}
export function deleteChannelApp(data: { ids: App['appId'][] }): Promise<never> {
  return request({
    url: '/channelApp/delete',
    method: 'post',
    data
  });
}

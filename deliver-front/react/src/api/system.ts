import { Channel, ChannelProvider, PlatFormFile } from '@/types';
import request from '@/utils/request.ts';
import { Message } from '@/views/GroupManage/pages/MessageTemplate/type';

// 根据用户类型获取渠道类型
export function getChannelType(data: { usersType: number }): Promise<Channel[]> {
  return request({
    url: '/systemParam/getChannelType',
    method: 'post',
    data
  });
}

// 根据渠道类型获取渠道供应商以及消息类型
export function getChannelProviderType(data: { channelType: number }): Promise<ChannelProvider[]> {
  return request({
    url: '/systemParam/getChannelProviderType',
    method: 'post',
    data
  });
}

// 根据渠道类型和渠道供应商获取消息类型
export function getMessageType(data: {
  channelType: number;
  channelProviderType: number;
}): Promise<Message[]> {
  return request({
    url: '/systemParam/getMessageType',
    method: 'post',
    data
  });
}

// 根据渠道类型和渠道供应商获取参数配置
export function getAppConfig(data: {
  channelType: number;
  channelProviderType: number;
}): Promise<string> {
  return request({
    url: '/systemParam/getAppConfig',
    method: 'post',
    data
  });
}

// 根据渠道类型获取平台文件类型
export function getFileType(data: { channelType: number }): Promise<PlatFormFile[]> {
  return request({
    url: '/systemParam/getPlatformFileType',
    method: 'post',
    data
  });
}

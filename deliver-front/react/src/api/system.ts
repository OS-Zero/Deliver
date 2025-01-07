import { Message } from '@/types/messageTemplate';
import request from '@/utils/request.ts';

interface Channel {
  channelType: number;
  channelTypeName: string;
}
interface ChannelProvider {
  channelProviderType: number;
  channelProviderTypeName: string;
}

// 根据用户类型获取渠道类型
export function getChannelType(data: { usersType: number }): Promise<Channel[]> {
  return request({
    url: '/systemParam/getChannelType',
    method: 'post',
    data
  });
}

// 根据渠道类型获取渠道供应商以及消息类型
export function getParam(data: { channelType: number }): Promise<{
  channelProviderTypeList: ChannelProvider[];
  messageTypeList: Message[];
}> {
  return request({
    url: '/systemParam/getParam',
    method: 'post',
    data
  });
}

// 根据渠道类型和渠道供应商获取参数配置
export function getAppConfig(data: { channelType: number, channelProviderType: number }): Promise<string> {
  return request({
    url: '/systemParam/getAppConfig',
    method: 'post',
    data
  });
}
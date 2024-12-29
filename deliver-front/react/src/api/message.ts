import request from '@/utils/request.ts';
import { R, SearchData } from '@/types';
import type {
  MessageTemplate,
  SearchMessage,
  SendTestMessage,
  AppItem,
  MessageItem,
  TemplateItem,
  AddTemp
} from '@/views/GroupManage/pages/MessageTemplate/type';

/**
 * 模版分页查询
 * @param data 搜索框数据
 * @returns 返回模板数据
 */
export async function getTemplatePages(data: SearchMessage): Promise<R<SearchData<MessageTemplate>>> {
  return await request({
    url: '/messageTemplate/search',
    method: 'post',
    data
  });
}

/**
 * 根据渠道类型查询具体的消息类型
 * @param data 1-电话、2-短信、3-邮件、4-钉钉、5-企业微信、6-飞书
 * @returns 返回消息类型
 */
export async function getMessageType(data: {
  channelType: number;
}): Promise<R<Array<MessageItem>>> {
  return await request({
    url: '/messageTemplate/getMessageTypeByChannelType',
    method: 'post',
    data
  });
}

/**
 * 根据渠道类型查询 APP
 * @param data 1-电话、2-短信、3-邮件、4-钉钉、5-企业微信、6-飞书
 * @returns 返回消息类型
 */
export async function getApp(data: { channelType: number }): Promise<R<Array<AppItem>>> {
  return await request({
    url: '/app/getAppByChannelType',
    method: 'post',
    data
  });
}

/**
 * 新增模版
 * @param data 新增模板数据
 * @returns
 */
export async function addTemplatePages(data: AddTemp): Promise<R<null>> {
  return await request({
    url: '/messageTemplate/saveTemplate',
    method: 'post',
    data
  });
}

/**
 * 更新模板状态
 * @param data
 * @returns
 */
export async function updateStatus(data: TemplateItem): Promise<R<null>> {
  return await request({
    url: '/messageTemplate/updateStatusById',
    method: 'post',
    data
  });
}

/**
 * 更新模板
 * @param data
 * @returns
 */
export async function updatetemplate(data: AddTemp): Promise<R<null>> {
  return await request({
    url: '/messageTemplate/updateById',
    method: 'post',
    data
  });
}

/**
 * 删除模板
 * @param data
 * @returns
 */
export async function deleteTemplate(data: { ids: Array<number> }): Promise<R<null>> {
  return await request({
    url: '/messageTemplate/delete',
    method: 'post',
    data
  });
}

/**
 * 模板测试发送消息
 * @param data
 * @returns
 */
export async function sendTestMes(data: SendTestMessage): Promise<R<null>> {
  return await request({
    url: '/messageTemplate/testSendMessage',
    method: 'post',
    data
  });
}

/**
 * 请求默认模板
 * @param data
 * @returns
 */
export async function getMessageParamByMessageType(data: {
  messageType: number;
  channelType: number;
}): Promise<any> {
  return await request({
    url: '/messageTemplate/getMessageParamByMessageType',
    method: 'post',
    data
  });
}

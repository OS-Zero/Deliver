import request from '@/utils/request.ts';
import { R, SearchData } from '@/types';
import type {
  MessageTemplate,
  Message,
  TestSendMessage,
  SearchMessage
} from '@/views/GroupManage/pages/MessageTemplate/type';

/**
 * 模版分页查询
 * @param data 搜索框数据
 * @returns 返回模板数据
 */
export async function getTemplatePages(
  data: SearchMessage
): Promise<R<SearchData<MessageTemplate>>> {
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
export async function getMessageTypeByChannelType(data: {
  channelType: number;
}): Promise<Array<Message>> {
  return await request({
    url: '/template/getMessageTypeByChannelType',
    method: 'post',
    data
  });
}

/**
 * 根据渠道类型查询 APP
 * @param data 1-电话、2-短信、3-邮件、4-钉钉、5-企业微信、6-飞书
 * @returns 返回消息类型
 */
export async function getApp(data: {
  channelType: number;
  channelProviderType: number;
}): Promise<never> {
  return await request({
    url: '/channelApp/getAppByChannel',
    method: 'post',
    data
  });
}

/**
 * 新增模版
 * @param data 新增模板数据
 * @returns
 */
export async function saveMessageTemplate(data: MessageTemplate): Promise<never> {
  return await request({
    url: '/messageTemplate/save',
    method: 'post',
    data
  });
}

/**
 * 更新模板状态
 * @param data
 * @returns
 */
export async function updateMessageTemplateStatus(
  data: Pick<MessageTemplate, 'templateId' | 'templateStatus'>
): Promise<never> {
  return await request({
    url: '/messageTemplate/updateStatus',
    method: 'post',
    data
  });
}

/**
 * 更新模板
 * @param data
 * @returns
 */
export async function updateMessageTemplate(data: MessageTemplate): Promise<never> {
  return await request({
    url: '/messageTemplate/update',
    method: 'post',
    data
  });
}

/**
 * 删除模板
 * @param data
 * @returns
 */
export async function deleteMessageTemplate(data: { ids: Array<number> }): Promise<never> {
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
export async function testSendMessage(data: TestSendMessage): Promise<never> {
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
    url: '/template/getMessageParamByMessageType',
    method: 'post',
    data
  });
}

import request from '@/utils/request.ts';
import { TableData } from '@/types';
import type { MessageTemplate, SearchParams, Template, TestSendMessage } from '@/types/messageTemplate';

/**
 * 模版分页查询
 * @param data 搜索框数据
 * @returns 返回模板数据
 */
export async function getMessageTemplates(data: SearchParams): Promise<TableData<MessageTemplate>> {
	return await request({
		url: '/messageTemplate/search',
		method: 'post',
		data: data,
	});
}

/**
 * 新增模版
 * @param data 新增模板数据
 * @returns
 */
export async function addMessageTemplate(data: MessageTemplate): Promise<never> {
	return await request({
		url: '/messageTemplate/save',
		method: 'post',
		data,
	});
}

/**
 * 更新模板状态
 * @param data
 * @returns
 */
export async function updateMessageTemplateStatus(data: Pick<MessageTemplate, 'templateId' | 'templateStatus'>): Promise<never> {
	return await request({
		url: '/messageTemplate/updateStatus',
		method: 'post',
		data,
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
		data,
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
		data,
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
		data,
	});
}
export async function searchTemplateByName(data: Pick<MessageTemplate, 'templateName'>): Promise<Array<Template>> {
	return await request({
		url: '/messageTemplate/searchByName',
		method: 'post',
		data,
	});
}
export async function getMessageParam(data: Pick<MessageTemplate, 'templateId'>): Promise<string> {
	return await request({
		url: '/messageTemplate/getMessageParam',
		method: 'post',
		data,
	});
}

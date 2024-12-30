import { FormItem } from '@/types/form';
import type { ColumnsType } from 'ant-design-vue/es/table/interface';
import { getRequiredRule, getRangeRule } from './rules';
import { SelectProps } from 'ant-design-vue';
import { MessageTemplateForm, TestSendMessage } from '@/types/messageTemplate';
import { getChannelType, getParam } from '@/api/system';
export const messageTemplateColumns: ColumnsType = [
	{
		title: '模板 ID',
		dataIndex: 'templateId',
		key: 'templateId',
	},
	{
		title: '模板名',
		dataIndex: 'templateName',
		key: 'templateName',
	},
	{
		title: '消息类型',
		dataIndex: 'messageTypeName',
		key: 'messageType',
	},
	{
		title: '用户类型',
		dataIndex: 'usersTypeName',
		key: 'usersTypeName',
	},
	{
		title: '渠道类型',
		dataIndex: 'channelTypeName',
		key: 'channelTypeName',
	},
	{
		title: '渠道供应商类型',
		dataIndex: 'channelProviderTypeName',
		key: 'channelProviderTypeName',
	},
	{
		title: '模板状态',
		dataIndex: 'templateStatus',
		key: 'templateStatus',
	},
	{
		title: '操作',
		dataIndex: 'actions',
		key: 'actions',
	},
];
const userTypes: SelectProps['options'] = JSON.parse(localStorage.getItem('startup') || '{}')?.usersTypeParamList.map(
	(item: Record<string, any>) => ({
		value: item.usersType,
		label: item.usersTypeName,
	}),
);

export const messageTemplateSchema: Record<string, FormItem<keyof MessageTemplateForm>> = {
	templateId: {
		value: '',
		type: 'none',
		fieldName: 'templateId',
	},
	appId: {
		value: '',
		type: 'none',
		fieldName: 'appId',
	},
	templateName: {
		value: '',
		type: 'input',
		fieldName: 'templateName',
		label: '模板名',
		rules: [getRequiredRule('请输入模板名'), ...getRangeRule(3, 20, '字符长度限制在3-20')],
	},
	templateDescription: {
		value: '',
		type: 'textarea',
		fieldName: 'templateDescription',
		label: '模板描述',
		rules: [...getRangeRule(3, 20, '字符长度限制在3-20')],
	},
	usersType: {
		value: '',
		type: 'select',
		fieldName: 'usersType',
		label: '用户类型',
		rules: [getRequiredRule('请选择用户类型')],
		options: userTypes,
	},
	channelType: {
		value: '',
		type: 'select',
		fieldName: 'channelType',
		label: '渠道类型',
		rules: [getRequiredRule('请选择渠道类型')],
		options: [],
	},
	channelProviderType: {
		value: '',
		type: 'select',
		fieldName: 'channelProviderType',
		label: '渠道供应商类型',
		rules: [getRequiredRule('请选择渠道供应商类型')],
		options: [],
	},
	messageType: {
		value: '',
		type: 'select',
		fieldName: 'messageType',
		label: '消息类型',
		rules: [getRequiredRule('请选消息类型')],
		options: [],
	},
};
export const messageTemplateSchemaDeps = [
	async () => {
		console.log('watch usersType');
		try {
			messageTemplateSchema.channelType.options = (await getChannelType({ userType: messageTemplateSchema.usersType.value })).map((item) => ({
				value: item.channelType,
				label: item.channelTypeName,
			}));
		} catch (error) {
			messageTemplateSchema.channelType.options = [];
		}
	},
	async () => {
		try {
			console.log('watch two');
			const { channelProviderTypeList, messageTypeList } = await getParam({ channelType: messageTemplateSchema.channelType.value });
			messageTemplateSchema.channelProviderType.options = channelProviderTypeList.map((item) => ({
				value: item.channelProviderType,
				label: item.channelProviderTypeName,
			}));
			messageTemplateSchema.messageType.options = messageTypeList.map((item) => ({
				value: item.messageType,
				label: item.messageTypeName,
			}));
			messageTemplateSchema.messageType.disabled = false;
		} catch (error) {
			messageTemplateSchema.channelProviderType.options = [];
			messageTemplateSchema.messageType.options = [];
		}
	},
];
export const filterSchema: Record<string, FormItem<string>> = {
	templateName: {
		value: '',
		type: 'input',
		fieldName: 'templateName',
		label: '模板名',
	},
	usersType: {
		value: '',
		type: 'select',
		fieldName: 'usersType',
		label: '用户类型',
		options: userTypes,
	},
};

export const testMessageSchema: Record<string, FormItem<keyof TestSendMessage>> = {
	users: {
		value: [],
		type: 'list',
		fieldName: 'users',
		label: '用户ID列表',
		placeholder: '请输入用户ID',
		rules: [getRequiredRule('请输入用户ID')],
	},
	paramMap: {
		value: {},
		type: 'jsonEditor',
		fieldName: 'paramMap',
		label: '发送参数',
		editorConfig: {
			modeList: ['code'],
			lang: 'zh',
		},
	},
};

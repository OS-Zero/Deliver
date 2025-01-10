import { FormItem } from '@/types/form';
import type { ColumnsType } from 'ant-design-vue/es/table/interface';
import { SelectProps } from 'ant-design-vue';
import { MessageTemplateForm, SearchParams, TestSendMessage } from '@/types/messageTemplate';
import { getChannelType, getParam } from '@/api/system';
import { getAppByChannel } from '@/api/channelApp';
import { notUndefined } from '@/utils/utils';
import { getRangeRule, getRequiredRule } from '@/utils/validate';
export const messageTemplateLocale = {
	templateId: '模板 Id',
	templateDescription: '模板描述',
	appId: '应用 Id',
	appName: '应用名',
	templateName: '模板名',
	messageTypeName: '消息类型',
	usersTypeName: '用户类型',
	channelTypeName: '渠道类型',
	channelProviderTypeName: '渠道供应商类型',
	templateStatus: '模板状态',
	createUser: '创建者',
	createTime: '创建时间',
};
export const messageTemplateColumns: ColumnsType = [
	{
		title: '模板 Id',
		dataIndex: 'templateId',
		key: 'templateId',
	},
	{
		title: '模板名',
		dataIndex: 'templateName',
		key: 'templateName',
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
		title: '消息类型',
		dataIndex: 'messageTypeName',
		key: 'messageTypeName',
	},
	{
		title: '模板状态',
		dataIndex: 'templateStatus',
		key: 'templateStatus',
	},
	{
		title: '创建者',
		dataIndex: 'createUser',
		key: 'createUser',
	},
	{
		title: '创建时间',
		dataIndex: 'createTime',
		key: 'createTime',
	},
	{
		title: '操作',
		dataIndex: 'actions',
		key: 'actions',
		fixed: 'right',
		width: 270,
	},
];
const userTypes: SelectProps['options'] = JSON.parse(localStorage.getItem('startup') || '{}')?.usersTypeParamList.map(
	(item: Record<string, any>) => ({
		value: item.usersType,
		label: item.usersTypeName,
	}),
);

type MessageTemplateSchema = Record<keyof MessageTemplateForm, FormItem<keyof MessageTemplateForm>>;
export const messageTemplateSchema: MessageTemplateSchema = {
	templateId: {
		type: 'none',
		fieldName: 'templateId',
	},
	templateName: {
		type: 'input',
		fieldName: 'templateName',
		label: '模板名',
		rules: [getRequiredRule('请输入模板名'), ...getRangeRule(3, 20, '字符长度限制在3-20')],
	},
	templateDescription: {
		type: 'textarea',
		fieldName: 'templateDescription',
		label: '模板描述',
		rules: [getRequiredRule('请输入模板描述'), ...getRangeRule(3, 20, '字符长度限制在3-20')],
	},
	usersType: {
		type: 'select',
		fieldName: 'usersType',
		label: '用户类型',
		rules: [getRequiredRule('请选择用户类型')],
		options: userTypes,
	},
	channelType: {
		type: 'select',
		fieldName: 'channelType',
		label: '渠道类型',
		rules: [getRequiredRule('请选择渠道类型')],
		options: [],
	},
	channelProviderType: {
		type: 'select',
		fieldName: 'channelProviderType',
		label: '渠道供应商类型',
		rules: [getRequiredRule('请选择渠道供应商类型')],
		options: [],
	},
	messageType: {
		type: 'select',
		fieldName: 'messageType',
		label: '消息类型',
		rules: [getRequiredRule('请选消息类型')],
		options: [],
	},
	appId: {
		type: 'select',
		fieldName: 'appId',
		label: '应用 ID',
		rules: [getRequiredRule('请选择应用 ID')],
		options: [],
	},
};

export const messageTemplateSchemaDeps = [
	async (data: MessageTemplateSchema) => {
		try {
			if (notUndefined(data.usersType.value)) {
				data.channelType.value = undefined;
				console.log('hello');

				data.channelType.options = (await getChannelType({ usersType: data.usersType.value })).map((item) => ({
					value: item.channelType,
					label: item.channelTypeName,
				}));
			} else {
				data.channelType.options = [];
			}
		} catch (error) {
			data.channelType.options = [];
		}
	},
	async (data: MessageTemplateSchema) => {
		try {
			if (notUndefined(data.channelType.value)) {
				data.channelProviderType.value = undefined;
				data.messageType.value = undefined;
				const { channelProviderTypeList, messageTypeList } = await getParam({ channelType: data.channelType.value });
				data.channelProviderType.options = channelProviderTypeList.map((item) => ({
					value: item.channelProviderType,
					label: item.channelProviderTypeName,
				}));
				data.messageType.options = messageTypeList.map((item) => ({
					value: item.messageType,
					label: item.messageTypeName,
				}));
			} else {
				data.channelProviderType.options = [];
				data.messageType.options = [];
			}
		} catch (error) {
			data.channelProviderType.options = [];
			data.messageType.options = [];
		}
	},
	async (data: MessageTemplateSchema) => {
		try {
			if (notUndefined(data.channelType.value) && notUndefined(data.channelProviderType.value)) {
				data.appId.value = undefined;
				const appOptions = await getAppByChannel({
					channelType: data.channelType.value,
					channelProviderType: data.channelProviderType.value,
				});
				data.appId.options = appOptions.map((item) => ({
					value: item.appId,
					label: item.appName,
				}));
			} else {
				data.appId.options = [];
			}
		} catch (error) {
			data.appId.options = [];
		}
	},
];
export const filterSchema: Record<string, FormItem<keyof SearchParams>> = {
	usersType: {
		type: 'select',
		fieldName: 'usersType',
		label: '用户类型',
		options: userTypes,
	},
	channelType: {
		type: 'select',
		fieldName: 'channelType',
		label: '渠道类型',
		options: [],
	},
	channelProviderType: {
		type: 'select',
		fieldName: 'channelProviderType',
		label: '渠道供应商类型',
		options: [],
	},
	messageType: {
		type: 'select',
		fieldName: 'messageType',
		label: '消息类型',
		options: [],
	},
	templateStatus: {
		type: 'select',
		fieldName: 'templateStatus',
		label: '模板状态',
		options: [
			{
				value: 0,
				label: '关闭',
			},
			{
				value: 1,
				label: '开启',
			},
		],
	},
	startTime: {
		type: 'datePicker',
		fieldName: 'startTime',
		label: '开始时间',
	},
	endTime: {
		type: 'datePicker',
		fieldName: 'endTime',
		label: '结束时间',
	},
};
export const filterSchemaMaps = messageTemplateSchemaDeps.slice(0, 2);
export const testMessageSchema: Record<string, FormItem<keyof TestSendMessage>> = {
	users: {
		value: [],
		type: 'list',
		fieldName: 'users',
		label: '用户ID列表',
		placeholder: '请输入用户ID',
		rules: [getRequiredRule('请输入用户ID')],
		max: 2,
	},
	messageParam: {
		value: {},
		type: 'jsonEditor',
		fieldName: 'messageParam',
		label: '发送参数',
		editorConfig: {
			modeList: ['code'],
			lang: 'zh',
		},
	},
};

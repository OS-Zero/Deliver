import { FormItem } from '@/types/form';
import type { ColumnsType } from 'ant-design-vue/es/table/interface';
import { ChannelAppForm, SearchParams } from '@/types/channelApp';
import { getAppConfig, getChannelType, getParam } from '@/api/system';
import { notUndefined } from '@/utils/utils';
import { getRangeRule, getRequiredRule } from '@/utils/validate';
export const channelAppLocale = {
	appId: '关联应用',
	appName: '应用名',
	appDescription: '应用描述',
	channelTypeName: '渠道类型',
	channelProviderTypeName: '渠道供应商类型',
	appConfig: '应用配置',
	appStatus: '应用状态',
	createUser: '创建者',
	createTime: '创建时间',
};
export const channelAppColumns: ColumnsType = [
	{
		title: '关联应用',
		dataIndex: 'appId',
		key: 'appId',
	},
	{
		title: '应用名',
		dataIndex: 'appName',
		key: 'appName',
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
		title: '应用状态',
		dataIndex: 'appStatus',
		key: 'appStatus',
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
type Schema<T> = Record<string, FormItem<keyof T>>;
export const channelAppSchema: Schema<ChannelAppForm> = {
	appId: {
		type: 'none',
		fieldName: 'appId',
	},
	appName: {
		type: 'input',
		fieldName: 'appName',
		label: '应用名',
		rules: [getRequiredRule('请输入应用名'), ...getRangeRule(3, 20, '字符长度限制在3-20')],
	},
	appDescription: {
		type: 'textarea',
		fieldName: 'appDescription',
		label: '应用描述',
		rules: [getRequiredRule('请输入应用描述'), ...getRangeRule(3, 20, '字符长度限制在3-20')],
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
	appConfig: {
		type: 'jsonEditor',
		fieldName: 'appConfig',
		label: '应用配置',
	},
};
export const channelAppSchemaDeps = [
	async (data: Schema<ChannelAppForm>) => {
		try {
			data.channelType.value = undefined;
			data.channelType.options = (await getChannelType({ usersType: -1 })).map((item) => ({
				value: item.channelType,
				label: item.channelTypeName,
			}));
		} catch (error) {
			data.channelType.options = [];
		}
	},
	async (data: Schema<ChannelAppForm>) => {
		try {
			if (notUndefined(data.channelType.value)) {
				data.channelProviderType.value = undefined;
				const { channelProviderTypeList } = await getParam({ channelType: data.channelType.value });
				data.channelProviderType.options = channelProviderTypeList.map((item) => ({
					value: item.channelProviderType,
					label: item.channelProviderTypeName,
				}));
			} else {
				data.channelProviderType.options = [];
			}
		} catch (error) {
			data.channelProviderType.options = [];
		}
	},
	async (data: Schema<ChannelAppForm>) => {
		try {
			if (notUndefined(data.channelType.value) && notUndefined(data.channelProviderType.value)) {
				data.appConfig.value = undefined;
				const appConfig = await getAppConfig({
					channelType: data.channelType.value,
					channelProviderType: data.channelProviderType.value,
				});
				data.appConfig.value = JSON.parse(appConfig || '{}');
			} else {
				data.appConfig.value = {};
			}
		} catch (error) {
			data.appConfig.value = {};
		}
	},
];
export const filterSchema: Schema<SearchParams> = {
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
export const filterSchemaMaps = channelAppSchemaDeps.slice(0, 2);

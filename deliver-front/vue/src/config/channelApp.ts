import type { ColumnsType } from 'ant-design-vue/es/table/interface';
import { Channel, ChannelApp, ChannelAppForm, ChannelProvider, SearchParams } from '@/types/channelApp';
import { getAppConfig, getChannelProviderType, getChannelType } from '@/api/system';
import { notUndefined } from '@/utils/utils';
import { getRangeRule, getRequiredRule } from '@/utils/validate';
import { reactive } from 'vue';
import { Schema } from '@/types';
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
const generateDispatch = (form: Schema<any>) => {
	return {
		channelType: async () => {
			form.channelType.selectConfig!.options = (await getChannelType({ usersType: -1 })).map((item) => ({
				value: item.channelType,
				label: item.channelTypeName,
			}));
		},
		channelProviderType: async (data: { channelType: Channel['channelType'] }) => {
			if (notUndefined(data.channelType)) {
				form.channelProviderType.selectConfig!.options = (await getChannelProviderType(data)).map((item) => ({
					value: item.channelProviderType,
					label: item.channelProviderTypeName,
				}));
				!form.channelProviderType.selectConfig!.options.some((item) => item.value === form.channelProviderType.value) &&
					(form.channelProviderType.value = undefined);
			}
		},
		appConfig: async (data: { channelType: Channel['channelType']; channelProviderType: ChannelProvider['channelProviderType'] }) => {
			if (notUndefined(data.channelType) && notUndefined(data.channelProviderType)) {
				const appConfig = await getAppConfig(data);
				form.appConfig.value = JSON.parse(appConfig || '{}');
			} else {
				form.appConfig.value = JSON.parse('{}');
			}
		},
	};
};

export const channelAppFormSchema = reactive<Schema<ChannelAppForm>>({
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
		selectConfig: {
			options: [],
			onChange: (value: any) => {
				setOptionsDispatch['channelProviderType']({ channelType: value });
				setOptionsDispatch['appConfig']({ channelType: value, channelProviderType: channelAppFormSchema.channelProviderType.value });
			},
		},
		init: async () => {
			await setOptionsDispatch['channelType']();
		},
	},
	channelProviderType: {
		type: 'select',
		fieldName: 'channelProviderType',
		label: '渠道供应商类型',
		rules: [getRequiredRule('请选择渠道供应商类型')],
		selectConfig: {
			options: [],
			onChange: (value: any) => {
				setOptionsDispatch['appConfig']({ channelType: channelAppFormSchema.channelType.value, channelProviderType: value });
			},
		},
		init: async ({ channelType }: ChannelApp) => {
			await setOptionsDispatch['channelProviderType']({ channelType });
		},
	},
	appConfig: {
		value: {},
		type: 'jsonEditor',
		fieldName: 'appConfig',
		label: '应用配置',
		init: async ({ appConfig }: ChannelApp) => {
			channelAppFormSchema.appConfig.value = JSON.parse(appConfig || '{}');
		},
	},
});
export const setOptionsDispatch = generateDispatch(channelAppFormSchema);
export const filterFormSchema = reactive<Schema<SearchParams>>({
	channelType: {
		type: 'select',
		fieldName: 'channelType',
		label: '渠道类型',
		selectConfig: {
			options: [],
			onChange: (value: any) => {
				setFilterOptionsDispatch['channelProviderType']({ channelType: value });
			},
		},
		init: async () => {
			await setFilterOptionsDispatch['channelType']();
		},
	},
	channelProviderType: {
		type: 'select',
		fieldName: 'channelProviderType',
		label: '渠道供应商类型',
		selectConfig: {
			options: [],
		},
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
});
export const setFilterOptionsDispatch = generateDispatch(filterFormSchema);

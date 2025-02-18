import { FormItem } from '@/types/form';
import type { ColumnsType } from 'ant-design-vue/es/table/interface';
import { SelectProps } from 'ant-design-vue';
import { MessageTemplateForm, SearchParams, TestSendMessage, User } from '@/types/messageTemplate';
import { getChannelType, getChannelProviderType, getMessageType } from '@/api/system';
import { getAppByChannel } from '@/api/channelApp';
import { notUndefined } from '@/utils/utils';
import { getRangeRule, getRequiredRule } from '@/utils/validate';
import { computed, h, reactive } from 'vue';
import { DeleteOutlined, PlusOutlined } from '@ant-design/icons-vue';
import { Schema } from '@/types';
import { Channel, ChannelProvider } from '@/types/channelApp';
export const messageTemplateLocale = {
	templateId: '模板 Id',
	templateDescription: '模板描述',
	appId: '关联应用',
	appName: '关联应用',
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
		fixed: 'left',
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
		width: 180,
	},
	{
		title: '模板状态',
		dataIndex: 'templateStatus',
		key: 'templateStatus',
	},
	{
		title: '关联应用',
		dataIndex: 'appName',
		key: 'appName',
		width: 150,
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
const generateDispatch = (form: Schema<any>) => {
	return {
		channelType: async (data: { usersType: User['usersType'] }) => {
			if (notUndefined(data.usersType)) {
				form.channelType.selectConfig!.options = (await getChannelType(data)).map((item) => ({
					value: item.channelType,
					label: item.channelTypeName,
				}));
				!form.channelType.selectConfig!.options.some((item) => item.value === form.channelType.value) && (form.channelType.value = undefined);
			}
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
		messageType: async (data: { channelType: Channel['channelType']; channelProviderType: ChannelProvider['channelProviderType'] }) => {
			if (notUndefined(data.channelType) && notUndefined(data.channelProviderType)) {
				form.messageType.selectConfig!.options = (await getMessageType(data)).map((item) => ({
					value: item.messageType,
					label: item.messageTypeName,
				}));
				!form.messageType.selectConfig!.options.some((item) => item.value === form.messageType.value) && (form.messageType.value = undefined);
			}
		},
		appId: async (data: { channelType: Channel['channelType']; channelProviderType: ChannelProvider['channelProviderType'] }) => {
			if (notUndefined(data.channelType) && notUndefined(data.channelProviderType)) {
				form.appId.selectConfig!.options = (await getAppByChannel(data)).map((item) => ({
					value: item.appId,
					label: item.appName,
				}));
				!form.appId.selectConfig!.options.some((item) => item.value === form.appId.value) && (form.appId.value = undefined);
			}
		},
	};
};

type MessageTemplateSchema = Record<keyof MessageTemplateForm, FormItem<keyof MessageTemplateForm>>;
export const messageTemplateForm = reactive<MessageTemplateSchema>({
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
		selectConfig: {
			options: userTypes,
			onChange: async (value: any) => {
				setOptionsDispatch['channelType']({ usersType: value });
			},
		},
	},
	channelType: {
		type: 'select',
		fieldName: 'channelType',
		label: '渠道类型',
		rules: [getRequiredRule('请选择渠道类型')],
		selectConfig: {
			options: [],
			onChange: async (value: any) => {
				setOptionsDispatch['channelProviderType']({ channelType: value });
				setOptionsDispatch['messageType']({ channelType: value, channelProviderType: messageTemplateForm.channelProviderType.value });
				setOptionsDispatch['appId']({ channelType: value, channelProviderType: messageTemplateForm.channelProviderType.value });
			},
		},
	},
	channelProviderType: {
		type: 'select',
		fieldName: 'channelProviderType',
		label: '渠道供应商类型',
		rules: [getRequiredRule('请选择渠道供应商类型')],
		selectConfig: {
			options: [],
			onChange: async (value: any) => {
				setOptionsDispatch['messageType']({ channelType: messageTemplateForm.channelType.value, channelProviderType: value });
				setOptionsDispatch['appId']({ channelType: messageTemplateForm.channelType.value, channelProviderType: value });
			},
		},
	},
	messageType: {
		type: 'select',
		fieldName: 'messageType',
		label: '消息类型',
		rules: [getRequiredRule('请选消息类型')],
		selectConfig: {
			options: [],
		},
	},
	appId: {
		type: 'select',
		fieldName: 'appId',
		label: '关联应用',
		rules: [getRequiredRule('请选择关联应用')],
		selectConfig: {
			options: [],
		},
	},
});
export const setOptionsDispatch = generateDispatch(messageTemplateForm);
export const filterForm = reactive<Record<string, FormItem<keyof SearchParams>>>({
	usersType: {
		type: 'select',
		fieldName: 'usersType',
		label: '用户类型',
		selectConfig: {
			options: userTypes,
			onChange: async (value: any) => {
				setFilterOptionsDispatch['channelType']({ usersType: value });
			},
		},
	},
	channelType: {
		type: 'select',
		fieldName: 'channelType',
		label: '渠道类型',
		selectConfig: {
			options: [],
			onChange: async (value: any) => {
				setFilterOptionsDispatch['channelProviderType']({ channelType: value });
				setFilterOptionsDispatch['messageType']({ channelType: value, channelProviderType: filterForm.channelProviderType.value });
			},
		},
	},
	channelProviderType: {
		type: 'select',
		fieldName: 'channelProviderType',
		label: '渠道供应商类型',
		selectConfig: {
			options: [],
			onChange: async (value: any) => {
				setFilterOptionsDispatch['messageType']({ channelType: filterForm.channelType.value, channelProviderType: value });
			},
		},
	},
	messageType: {
		type: 'select',
		fieldName: 'messageType',
		label: '消息类型',
		selectConfig: {
			options: [],
		},
	},
	templateStatus: {
		type: 'select',
		fieldName: 'templateStatus',
		label: '模板状态',
		selectConfig: {
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
export const setFilterOptionsDispatch = generateDispatch(filterForm);
export const testMessageForm = reactive<Schema<TestSendMessage>>({
	templateId: {
		type: 'none',
		fieldName: 'templateId',
	},
	users: {
		value: [],
		type: 'list',
		fieldName: 'users',
		label: '用户ID列表',
		rules: [getRequiredRule('请输入用户ID')],
		customConfig: {
			deleteConfig: {
				icon: h(DeleteOutlined),
				danger: true,
				type: 'text',
				shape: 'circle',
				click: (id: string) => {
					const list = testMessageForm.users.value;
					const index = list.indexOf(id);
					list.splice(index, 1);
				},
			},
			addConfig: {
				icon: h(PlusOutlined),
				type: 'dashed',
				onClick: () => {
					const list = testMessageForm.users.value;
					list.push('');
				},
			},
		},
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
});
testMessageForm.users.customConfig!.addConfig.disabled = computed(() => testMessageForm.users.value.length >= 2);

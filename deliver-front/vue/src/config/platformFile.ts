import type { ColumnsType } from 'ant-design-vue/es/table/interface';
import { getRangeRule, getRequiredRule } from '@/utils/validate';
import { SearchParams, UploadPlatformFile } from '@/types/platformFile';
import { getChannelProviderType, getChannelType, getMessageType, getPlatformFileType } from '@/api/system';
import { getAppByChannel } from '@/api/channelApp';
import { notUndefined } from '@/utils/utils';
import { reactive } from 'vue';
import { Schema } from '@/types';
import { Channel, ChannelProvider } from '@/types/channelApp';
export const platformFileLocale = {
	platformFileId: '文件 Id',
	platformFileName: '文件名',
	platformFileDescription: '文件描述',
	platformFileTypeName: '文件类型',
	platformFileKey: '文件 Key',
	platformFileStatus: '文件状态',
	channelTypeName: '渠道类型',
	appName: '应用名',
	createUser: '创建者',
	createTime: '创建时间',
};
export const platformFileColumns: ColumnsType = [
	{
		title: '文件名',
		dataIndex: 'platformFileName',
		key: 'platformFileName',
	},
	{
		title: '文件类型',
		dataIndex: 'platformFileTypeName',
		key: 'platformFileTypeName',
	},
	{
		title: '渠道类型',
		dataIndex: 'channelTypeName',
		key: 'channelTypeName',
	},
	{
		title: '关联应用',
		dataIndex: 'appName',
		key: 'appName',
	},
	{
		title: '文件状态',
		dataIndex: 'platformFileStatus',
		key: 'platformFileStatus',
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
			form.channelType.selectConfig!.options = (await getChannelType({ usersType: -2 })).map((item) => ({
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
		messageType: async (data: { channelType: Channel['channelType']; channelProviderType: ChannelProvider['channelProviderType'] }) => {
			if (notUndefined(data.channelType) && notUndefined(data.channelProviderType)) {
				form.messageType.selectConfig!.options = (await getMessageType(data)).map((item) => ({
					value: item.messageType,
					label: item.messageTypeName,
				}));
				!form.messageType.selectConfig!.options.some((item) => item.value === form.messageType.value) && (form.messageType.value = undefined);
			}
		},
		platformFileType: async (data: { channelType: Channel['channelType'] }) => {
			form.platformFileType.selectConfig!.options = (await getPlatformFileType(data)).map((item) => ({
				value: item.platformFileType,
				label: item.platformFileTypeName,
			}));
			!form.platformFileType.selectConfig!.options.some((item) => item.value === form.platformFileType.value) &&
				(form.platformFileType.value = undefined);
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

export const platformFileForm: Schema<UploadPlatformFile> = reactive({
	platformFile: {
		value: [],
		type: 'upload',
		fieldName: 'platformFile',
		label: '文件',
		rules: [getRequiredRule('请上传文件')],
		uploadConifg: {
			maxCount: 1,
			customRequest: (options) => {
				setTimeout(() => {
					options.onSuccess && options.onSuccess('');
				}, 0);
			},
		},
	},
	platformFileName: {
		type: 'input',
		fieldName: 'platformFileName',
		label: '文件名',
		rules: [getRequiredRule('请输入文件名'), ...getRangeRule(1, 10, '字符长度限制在1-10')],
	},
	platformFileDescription: {
		type: 'textarea',
		fieldName: 'platformFileDescription',
		label: '文件描述',
		rules: [getRequiredRule('请输入文件描述'), ...getRangeRule(3, 20, '字符长度限制在3-20')],
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
				setOptionsDispatch['platformFileType']({ channelType: value });
				setOptionsDispatch['messageType']({ channelType: value, channelProviderType: platformFileForm.channelProviderType.value });
				setOptionsDispatch['appId']({ channelType: value, channelProviderType: platformFileForm.channelProviderType.value });
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
			onChange: (value: any) => {
				setOptionsDispatch['messageType']({ channelType: platformFileForm.channelType.value, channelProviderType: value });
				setOptionsDispatch['appId']({ channelType: platformFileForm.channelType.value, channelProviderType: value });
			},
		},
	},
	platformFileType: {
		type: 'select',
		fieldName: 'platformFileType',
		label: '文件类型',
		rules: [getRequiredRule('请选择文件类型')],
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
export const setOptionsDispatch = generateDispatch(platformFileForm);
export const filterForm: Schema<SearchParams> = reactive({
	platformFileKey: {
		type: 'input',
		fieldName: 'platformFileKey',
		label: '文件 Key',
	},
	channelType: {
		type: 'select',
		fieldName: 'channelType',
		label: '渠道类型',
		selectConfig: {
			options: [],
			onChange: (value: any) => {
				setFilterOptionsDispatch['channelProviderType']({ channelType: value });
				setFilterOptionsDispatch['platformFileType']({ channelType: value });
				setFilterOptionsDispatch['messageType']({ channelType: value, channelProviderType: platformFileForm.channelProviderType.value });
				setFilterOptionsDispatch['appId']({ channelType: value, channelProviderType: platformFileForm.channelProviderType.value });
			},
		},
	},
	channelProviderType: {
		type: 'select',
		fieldName: 'channelProviderType',
		label: '渠道供应商类型',
		selectConfig: {
			options: [],
			onChange: (value: any) => {
				setFilterOptionsDispatch['messageType']({ channelType: platformFileForm.channelType.value, channelProviderType: value });
				setFilterOptionsDispatch['appId']({ channelType: platformFileForm.channelType.value, channelProviderType: value });
			},
		},
	},
	platformFileType: {
		type: 'select',
		fieldName: 'platformFileType',
		label: '文件类型',
		selectConfig: {
			options: [],
		},
	},
	appId: {
		type: 'select',
		fieldName: 'appId',
		label: '关联应用',
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
export const setFilterOptionsDispatch = generateDispatch(filterForm);

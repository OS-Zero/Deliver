import { FormItem } from '@/types/form';
import type { ColumnsType } from 'ant-design-vue/es/table/interface';
import { getRangeRule, getRequiredRule } from '@/utils/validate';
import { SearchParams, UploadPlatformFile } from '@/types/platformFile';
import { getChannelType, getParam, getPlatformFileType } from '@/api/system';
import { getAppByChannel } from '@/api/channelApp';
import { notUndefined } from '@/utils/utils';
export const platformFileLocale = {
	platformFileId: '文件 Id',
	platformFileName: '文件名',
	platformFileDescription: '文件描述',
	platformFileTypeName: '文件类型',
	platformFileKey: '文件 Key',
	platformFileStatus: '文件状态',
	channelType: '渠道类型',
	appName: '应用名',
	createUser: '创建者',
	createTime: '创建时间',
};
export const platformFileColumns: ColumnsType = [
	{
		title: '文件 Id',
		dataIndex: 'platformFileId',
		key: 'platformFileId',
	},
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
		title: '文件状态',
		dataIndex: 'platformFileStatus',
		key: 'platformFileStatus',
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
export const platformFileSchema: Schema<UploadPlatformFile> = {
	platformFile: {
		type: 'upload',
		fieldName: 'platformFile',
		label: '文件',
		rules: [getRequiredRule('请上传文件')],
		uploadConifg: {
			maxCount: 1,
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
		options: [],
	},
	channelProviderType: {
		type: 'select',
		fieldName: 'channelProviderType',
		label: '渠道供应商类型',
		rules: [getRequiredRule('请选择渠道供应商类型')],
		options: [],
	},
	platformFileType: {
		type: 'select',
		fieldName: 'platformFileType',
		label: '文件类型',
		rules: [getRequiredRule('请选择文件类型')],
		options: [],
	},
	appId: {
		type: 'select',
		fieldName: 'appId',
		label: '关联应用',
		rules: [getRequiredRule('请选择关联应用')],
		options: [],
	},
};
export const platformFileSchemaDeps = [
	async (data: Schema<UploadPlatformFile>) => {
		data.channelType.value = undefined;
		try {
			data.channelType.options = (await getChannelType({ usersType: -1 })).map((item) => ({
				value: item.channelType,
				label: item.channelTypeName,
			}));
		} catch (error) {
			data.channelType.options = [];
		}
	},
	async (data: Schema<UploadPlatformFile>) => {
		try {
			if (notUndefined(data.channelType.value)) {
				const { channelProviderTypeList } = await getParam({ channelType: data.channelType.value });
				data.channelProviderType.options = channelProviderTypeList.map((item) => ({
					value: item.channelProviderType,
					label: item.channelProviderTypeName,
				}));
				!data.channelProviderType.options.some((item) => item.value === data.channelProviderType.value) &&
					(data.channelProviderType.value = undefined);
			} else {
				data.channelProviderType.value = undefined;
				data.channelProviderType.options = [];
			}
		} catch (error) {
			data.channelProviderType.value = undefined;
			data.channelProviderType.options = [];
		}
	},
	async (data: Schema<UploadPlatformFile>) => {
		try {
			if (notUndefined(data.channelType.value)) {
				data.platformFileType.options = (await getPlatformFileType({ channelType: data.channelType.value })).map((item) => ({
					value: item.platformFileType,
					label: item.platformFileTypeName,
				}));
				!data.platformFileType.options.some((item) => item.value === data.platformFileType.value) && (data.platformFileType.value = undefined);
			} else {
				data.platformFileType.value = undefined;
				data.platformFileType.options = [];
			}
		} catch (error) {
			data.platformFileType.value = undefined;
			data.platformFileType.options = [];
		}
	},
	async (data: Schema<UploadPlatformFile>) => {
		try {
			if (notUndefined(data.channelType.value) && notUndefined(data.channelProviderType.value)) {
				const appOptions = await getAppByChannel({
					channelType: data.channelType.value,
					channelProviderType: data.channelProviderType.value,
				});
				data.appId.options = appOptions.map((item) => ({
					value: item.appId,
					label: item.appName,
				}));
				!data.appId.options.some((item) => item.value === data.appId.value) && (data.appId.value = undefined);
			} else {
				data.appId.value = undefined;
				data.appId.options = [];
			}
		} catch (error) {
			data.appId.value = undefined;
			data.appId.options = [];
		}
	},
];
export const filterSchema: Schema<SearchParams> = {
	platformFileKey: {
		type: 'input',
		fieldName: 'platformFileKey',
		label: '文件 Key',
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
	platformFileType: {
		type: 'select',
		fieldName: 'platformFileType',
		label: '文件类型',
		options: [],
	},
	appId: {
		type: 'select',
		fieldName: 'appId',
		label: '关联应用',
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
export const filterSchemaMaps = platformFileSchemaDeps;

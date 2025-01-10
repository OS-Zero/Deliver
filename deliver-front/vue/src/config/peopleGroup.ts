import { FormItem } from '@/types/form';
import type { ColumnsType } from 'ant-design-vue/es/table/interface';
import { SearchParams, PeopleGroupForm } from '@/types/peopleGroup';
import { SelectProps } from 'ant-design-vue';
import { analysisExcelTemplateFile } from '@/api/peopleGroup';
import { getRangeRule, getRequiredRule } from '@/utils/validate';
import { notUndefined } from '@/utils/utils';
export const peopleGroupLocale = {
	peopleGroupId: '人群 Id',
	peopleGroupDescription: '人群描述',
	peopleGroupName: '人群名',
	peopleGroupList: '人群列表',
	usersTypeName: '用户类型名',
	createUser: '创建者',
	createTime: '创建时间',
};
export const peopleGroupColumns: ColumnsType = [
	{
		title: '人群 Id',
		dataIndex: 'peopleGroupId',
		key: 'peopleGroupId',
	},
	{
		title: '人群名',
		dataIndex: 'peopleGroupName',
		key: 'peopleGroupName',
	},
	{
		title: '用户类型',
		dataIndex: 'usersTypeName',
		key: 'usersTypeName',
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
export const peopleGroupSchema: Schema<PeopleGroupForm> = {
	peopleGroupId: {
		type: 'none',
		fieldName: 'peopleGroupId',
	},
	peopleGroupName: {
		type: 'input',
		fieldName: 'peopleGroupName',
		label: '人群名',
		rules: [getRequiredRule('请输入人群名'), ...getRangeRule(3, 20, '字符长度限制在3-20')],
	},
	peopleGroupDescription: {
		type: 'textarea',
		fieldName: 'peopleGroupDescription',
		label: '人群描述',
		rules: [getRequiredRule('请输入人群描述'), ...getRangeRule(3, 20, '字符长度限制在3-20')],
	},
	excelTemplateFile: {
		type: 'upload',
		fieldName: 'excelTemplateFile',
		label: '人群文件',
		uploadConifg: {
			name: '解析excel人群文件',
			maxCount: 1,
		},
	},
	peopleGroupList: {
		type: 'textarea',
		fieldName: 'peopleGroupList',
		label: '人群列表',
		rules: [getRequiredRule('请输入人群列表')],
	},
	usersType: {
		type: 'select',
		fieldName: 'usersType',
		label: '人群类型',
		rules: [getRequiredRule('请选择人群类型')],
		options: [
			{
				value: 1,
				label: '实时',
			},
			{
				value: 2,
				label: '定时',
			},
		],
	},
};
export const peopleGroupSchemaDeps = [
	async (data: Schema<PeopleGroupForm>) => {
		if (notUndefined(data.excelTemplateFile.value)) {
			data.peopleGroupList.value = undefined;
			data.peopleGroupList.value = await analysisExcelTemplateFile({ file: data.excelTemplateFile.value });
		}
	},
];
const userTypes: SelectProps['options'] = JSON.parse(localStorage.getItem('startup') || '{}')?.usersTypeParamList.map(
	(item: Record<string, any>) => ({
		value: item.usersType,
		label: item.usersTypeName,
	}),
);

export const filterSchema: Record<string, FormItem<keyof SearchParams>> = {
	usersType: {
		type: 'select',
		fieldName: 'usersType',
		label: '用户类型',
		options: userTypes,
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

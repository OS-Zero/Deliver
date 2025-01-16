import { FormItem } from '@/types/form';
import type { ColumnsType } from 'ant-design-vue/es/table/interface';
import { SearchParams, PeopleGroupForm } from '@/types/peopleGroup';
import { SelectProps } from 'ant-design-vue';
import { analysisExcelTemplateFile } from '@/api/peopleGroup';
import { getRangeRule, getRequiredRule } from '@/utils/validate';
import { reactive } from 'vue';
import { Schema } from '@/types';
export const peopleGroupLocale = {
	peopleGroupId: '关联人群 Id',
	peopleGroupDescription: '人群描述',
	peopleGroupName: '关联人群',
	peopleGroupList: '人群列表',
	usersTypeName: '用户类型名',
	createUser: '创建者',
	createTime: '创建时间',
};
export const peopleGroupColumns: ColumnsType = [
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
const userTypes: SelectProps['options'] = JSON.parse(localStorage.getItem('startup') || '{}')?.usersTypeParamList.map(
	(item: Record<string, any>) => ({
		value: item.usersType,
		label: item.usersTypeName,
	}),
);
interface PeopleGroupSchema extends PeopleGroupForm {
	$radioGroup: number;
	$excelTemplateFile: File;
}

const validatePeopleGroupList = (_rule: any, value: any) => {
	const regExp = /^([^\s,]{1,30})(,([^\s,]{1,30}))*$/;
	if (!value) {
		return Promise.reject('请输入人群列表');
	} else if (regExp.test(value)) {
		return Promise.resolve();
	}
	return Promise.reject('人群列表格式错误');
};
const getTextareaDescription = (num: number) => {
	return `使用英文逗号分割，最多可输入100个；共输入了 <span style="color:red">${num}</span> 个号码`;
};
export const setPeopleGroupListTip = (list: string) => {
	if (list) {
		peopleGroupForm.peopleGroupList.customConfig!.tip = getTextareaDescription(list.split(',').length);
	} else {
		peopleGroupForm.peopleGroupList.customConfig!.tip = getTextareaDescription(0);
	}
};
export const peopleGroupForm: Schema<PeopleGroupSchema> = reactive({
	peopleGroupId: {
		type: 'none',
		fieldName: 'peopleGroupId',
	},
	peopleGroupName: {
		type: 'input',
		fieldName: 'peopleGroupName',
		label: '关联人群',
		rules: [getRequiredRule('请输入关联人群'), ...getRangeRule(3, 20, '字符长度限制在3-20')],
	},
	peopleGroupDescription: {
		type: 'textarea',
		fieldName: 'peopleGroupDescription',
		label: '人群描述',
		rules: [getRequiredRule('请输入人群描述'), ...getRangeRule(3, 20, '字符长度限制在3-20')],
	},
	usersType: {
		type: 'select',
		fieldName: 'usersType',
		label: '用户类型',
		rules: [getRequiredRule('请选择用户类型')],
		selectConfig: {
			options: userTypes,
		},
	},
	$radioGroup: {
		value: 0,
		type: 'radioGroup',
		fieldName: '$radioGroup',
		radioGroupConfig: {
			options: [
				{
					label: '上传文件解析人群列表',
					value: 0,
				},
				{
					label: '手动输入人群列表',
					value: 1,
				},
			],
			onChange: ({ target }) => {
				const value = target.value;
				if (value === 0) {
					peopleGroupForm.peopleGroupList.type = 'none';
					peopleGroupForm.$excelTemplateFile.type = 'upload';
				} else if (value === 1) {
					peopleGroupForm.$excelTemplateFile.type = 'none';
					peopleGroupForm.peopleGroupList.type = 'textarea';
				}
			},
		},
	},
	$excelTemplateFile: {
		type: 'upload',
		fieldName: '$excelTemplateFile',
		label: '人群文件',
		uploadConifg: {
			maxCount: 1,
			customRequest: (options) => {
				peopleGroupForm.peopleGroupList.value = undefined;
				analysisExcelTemplateFile({ file: options.file as File })
					.then((res) => {
						peopleGroupForm.peopleGroupList.value = res;
						options.onSuccess && options.onSuccess(res);
						setPeopleGroupListTip(res);
					})
					.catch((err) => {
						options.onError && options.onError(err);
					});
			},
		},
		customConfig: {
			title: '解析人群文件',
			description: '单次人群最多支持上传100条，请上传csv、xlsx格式文件，大小10MB以内',
		},
	},
	peopleGroupList: {
		type: 'none',
		fieldName: 'peopleGroupList',
		label: '人群列表',
		rules: [{ required: true, validator: validatePeopleGroupList, trigger: 'blur' }],
		textareaConfig: {
			rows: 10,
			onChange: ({ target }) => {
				setPeopleGroupListTip(target.value || '');
			},
		},
		customConfig: {
			tip: getTextareaDescription(0),
		},
	},
});
export const filterForm: Record<string, FormItem<keyof SearchParams>> = reactive({
	usersType: {
		type: 'select',
		fieldName: 'usersType',
		label: '用户类型',
		selectConfig: {
			options: userTypes,
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

import { FormItem } from '@/types/form';
import type { ColumnsType } from 'ant-design-vue/es/table/interface';
import { getRequiredRule, getRangeRule } from './rules';
import { SearchParams, TaskForm } from '@/types/task';
export const taskLocale = {
	taskId: '任务 Id',
	taskDescription: '任务描述',
	taskName: '任务名',
	taskStatus: '任务状态',
	taskParam: '任务参数',
	taskType: '任务类型',
	peopleGroupName: '人群名',
	createUser: '创建者',
	createTime: '创建时间',
};
export const taskColumns: ColumnsType = [
	{
		title: '任务 Id',
		dataIndex: 'taskId',
		key: 'taskId',
	},
	{
		title: '任务名',
		dataIndex: 'taskName',
		key: 'taskName',
	},
	{
		title: '任务状态',
		dataIndex: 'taskStatus',
		key: 'taskStatus',
	},
	{
		title: '任务类型',
		dataIndex: 'taskType',
		key: 'taskType',
	},
	{
		title: '人群名',
		dataIndex: 'peopleGroupName',
		key: 'peopleGroupName',
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
export const taskSchema: Schema<TaskForm> = {
	taskId: {
		type: 'none',
		fieldName: 'taskId',
	},
	taskName: {
		type: 'input',
		fieldName: 'taskName',
		label: '任务名',
		rules: [getRequiredRule('请输入任务名'), ...getRangeRule(3, 20, '字符长度限制在3-20')],
	},
	taskDescription: {
		type: 'textarea',
		fieldName: 'taskDescription',
		label: '任务描述',
		rules: [getRequiredRule('请输入任务描述'), ...getRangeRule(3, 20, '字符长度限制在3-20')],
	},
	taskParam: {
		value: JSON.parse('{"templateId":1,"messageParam":{},"cronExpression":"0 0 6 * * ?"}'),
		type: 'jsonEditor',
		fieldName: 'taskParam',
		label: '任务参数',
	},
	taskType: {
		type: 'select',
		fieldName: 'taskType',
		label: '任务类型',
		rules: [getRequiredRule('请选择任务类型')],
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
	peopleGroupId: {
		type: 'input',
		fieldName: 'peopleGroupId',
		label: '人群 Id',
		rules: [getRequiredRule('请输入人群 Id')],
	},
};
export const filterSchema: Record<string, FormItem<keyof SearchParams>> = {
	taskName: {
		type: 'input',
		fieldName: 'taskName',
		label: '任务名',
	},
	taskType: {
		type: 'select',
		fieldName: 'taskType',
		label: '消息类型',
		options: [
			{
				key: 1,
				value: '实时',
			},
			{
				key: 2,
				value: '定时',
			},
		],
	},
	taskStatus: {
		type: 'select',
		fieldName: 'taskStatus',
		label: '任务状态',
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

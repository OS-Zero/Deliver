import { FormItem } from '@/types/form';
import type { ColumnsType } from 'ant-design-vue/es/table/interface';
import { getRangeRule, getRequiredRule } from '@/utils/validate';
import { SearchParams, TaskForm } from '@/types/task';
import { getMessageParam, searchTemplateByName } from '@/api/messageTemplate';
import { searchPeopleGroupByName } from '@/api/peopleGroup';
import { h, reactive } from 'vue';
import { debounce } from 'lodash';
import { Spin } from 'ant-design-vue';
import { isValidCron } from 'cron-validator';
export const taskLocale = {
	taskId: '任务 Id',
	taskDescription: '任务描述',
	taskName: '任务名',
	taskStatus: '任务状态',
	taskMessageParam: '任务消息参数',
	taskTimeExpression: '任务时间表达式',
	taskType: '任务类型',
	templateName: '关联模板',
	peopleGroupName: '关联人群',
	createUser: '创建者',
	createTime: '创建时间',
};
export const taskColumns: ColumnsType = [
	{
		title: '任务名',
		dataIndex: 'taskName',
		key: 'taskName',
	},
	{
		title: '任务类型',
		dataIndex: 'taskType',
		key: 'taskType',
	},
	{
		title: '任务状态',
		dataIndex: 'taskStatus',
		key: 'taskStatus',
	},
	{
		title: '关联模板',
		dataIndex: 'templateName',
		key: 'templateName',
	},
	{
		title: '关联人群',
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
export const validateCronExpression = (_rule: any, value: any) => {
	const isValidate = isValidCron(value, {
		seconds: true,
		alias: true,
		allowBlankDay: true,
		allowSevenAsSunday: true,
	});
	if (isValidate) {
		return Promise.resolve();
	}
	return Promise.reject('请提供正确的 Cron 表达式');
};
type Schema<T> = Record<string, FormItem<keyof T>>;
export const taskForm: Schema<TaskForm> = reactive({
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
	taskType: {
		value: 1,
		type: 'radioGroup',
		fieldName: 'taskType',
		label: '任务类型',
		radioGroupConfig: {
			options: [
				{
					value: 1,
					label: '实时任务',
				},
				{
					value: 2,
					label: '定时循环任务',
				},
				{
					value: 3,
					label: '定时单次任务',
				},
			],
			onChange: ({ target }) => {
				taskForm.taskTimeExpression.value = undefined;
				if (target.value === 1) {
					taskForm.taskTimeExpression.type = 'none';
				} else if (target.value === 2) {
					taskForm.taskTimeExpression.type = 'input';
					taskForm.taskTimeExpression.rules = [{ validator: validateCronExpression }];
					taskForm.taskTimeExpression.customConfig!.tip =
						'Cron 表达式格式：秒(0-59) 分钟(0-59) 小时(0-23) 日(1-31) 月(1-12) 星期(0-6或SUN-SAT) 年(可选,1970-2099)。例如：20 3 * * * ? 表示每天 3:20 执行任务；20 3 8 * * ? 表示每月 8 号 3:20 执行任务；* 表示任意值；? 用于表示不指定某个字段的值。';
				} else if (target.value === 3) {
					taskForm.taskTimeExpression.rules = [getRequiredRule('请选择日期')];
					taskForm.taskTimeExpression.type = 'datePicker';
				}
			},
		},
	},
	taskTimeExpression: {
		type: 'none',
		fieldName: 'taskTimeExpression',
		label: '任务时间表达式',
		customConfig: {
			tip: '',
		},
	},
	templateId: {
		type: 'select',
		fieldName: 'templateId',
		label: '关联模板',
		rules: [getRequiredRule('请输入关联模板')],
		selectConfig: {
			options: [],
			showSearch: true,
			filterOption: false,
			onSearch: debounce((value: string) => {
				if (!value) return;
				taskForm.templateId.selectConfig!.notFoundContent = h(Spin);
				searchTemplateByName({ templateName: value }).then((res) => {
					taskForm.templateId.selectConfig!.options = res.map((item) => ({
						value: item.templateId,
						label: item.templateName,
					}));
					taskForm.templateId.selectConfig!.notFoundContent = undefined;
				});
			}, 200),
			onChange: (value: any) => {
				getMessageParam({ templateId: Number(value) }).then((res) => {
					taskForm.taskMessageParam.value = JSON.parse(res || '{}');
				});
			},
		},
	},
	peopleGroupId: {
		type: 'select',
		fieldName: 'peopleGroupId',
		label: '关联人群',
		rules: [getRequiredRule('请输入关联人群')],
		selectConfig: {
			showSearch: true,
			filterOption: false,
			onSearch: debounce((value: string) => {
				if (!value) return;
				taskForm.peopleGroupId.selectConfig!.notFoundContent = h(Spin);
				searchPeopleGroupByName({ peopleGroupName: value }).then((res) => {
					taskForm.peopleGroupId.selectConfig!.options = res.map((item) => ({
						value: item.peopleGroupId,
						label: item.peopleGroupName,
					}));
					taskForm.peopleGroupId.selectConfig!.notFoundContent = undefined;
				});
			}, 200),
		},
	},
	taskMessageParam: {
		value: JSON.parse('{\"title\":\"oszero每天起床\",\"content\":\"兄弟们该起床啦，6点啦，太阳晒屁股了\",\"htmlFlag\":true}'),
		type: 'jsonEditor',
		fieldName: 'taskMessageParam',
		label: '任务消息参数',
	},
});
export const filterForm: Record<string, FormItem<keyof SearchParams>> = reactive({
	taskType: {
		type: 'select',
		fieldName: 'taskType',
		label: '任务类型',
		selectConfig: {
			options: [
				{
					value: 1,
					label: '实时任务',
				},
				{
					value: 2,
					label: '定时循环任务',
				},
				{
					value: 3,
					label: '定时单次任务',
				},
			],
		},
	},
	taskStatus: {
		type: 'select',
		fieldName: 'taskStatus',
		label: '任务状态',
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

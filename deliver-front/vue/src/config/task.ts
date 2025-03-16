import type { ColumnsType } from 'ant-design-vue/es/table/interface';
import { getRangeRule, getRequiredRule } from '@/utils/validate';
import { SearchParams, Task, TaskForm } from '@/types/task';
import { getMessageParam, searchTemplateByName } from '@/api/messageTemplate';
import { searchPeopleGroupByName } from '@/api/peopleGroup';
import { h, reactive, watch } from 'vue';
import { debounce } from 'lodash';
import { Spin } from 'ant-design-vue';
import { isValidCron } from 'cron-validator';
import { Schema } from '@/types';
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
export const taskFormSchema: Schema<TaskForm> = reactive({
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
				taskFormSchema.taskTimeExpression.value = undefined;
				if (target.value === 1) {
					taskFormSchema.taskTimeExpression.type = 'none';
				} else if (target.value === 2) {
					taskFormSchema.taskTimeExpression.type = 'input';
					taskFormSchema.taskTimeExpression.rules = [{ validator: validateCronExpression }];
					taskFormSchema.taskTimeExpression.customConfig!.tip =
						'Cron 表达式格式：秒(0-59) 分钟(0-59) 小时(0-23) 日(1-31) 月(1-12) 星期(0-6或SUN-SAT) 年(可选,1970-2099)。例如：20 3 * * * ? 表示每天 3:20 执行任务；20 3 8 * * ? 表示每月 8 号 3:20 执行任务；* 表示任意值；? 用于表示不指定某个字段的值。';
				} else if (target.value === 3) {
					taskFormSchema.taskTimeExpression.rules = [getRequiredRule('请选择日期')];
					taskFormSchema.taskTimeExpression.type = 'datePicker';
				}
			},
		},
		init: async ({ taskType }: Task) => {
			taskFormSchema.taskType.value = taskType;
		},
	},
	taskTimeExpression: {
		type: 'none',
		fieldName: 'taskTimeExpression',
		label: '任务时间表达式',
		customConfig: {
			tip: '',
		},
		init: async ({ taskType }: Task) => {
			if (taskType === 1) {
				taskFormSchema.taskTimeExpression.type = 'none';
			} else if (taskType === 2) {
				taskFormSchema.taskTimeExpression.type = 'input';
				taskFormSchema.taskTimeExpression.rules = [{ validator: validateCronExpression }];
				taskFormSchema.taskTimeExpression.customConfig!.tip =
					'Cron 表达式格式：秒(0-59) 分钟(0-59) 小时(0-23) 日(1-31) 月(1-12) 星期(0-6或SUN-SAT) 年(可选,1970-2099)。例如：20 3 * * * ? 表示每天 3:20 执行任务；20 3 8 * * ? 表示每月 8 号 3:20 执行任务；* 表示任意值；? 用于表示不指定某个字段的值。';
			} else if (taskType === 3) {
				taskFormSchema.taskTimeExpression.rules = [getRequiredRule('请选择日期')];
				taskFormSchema.taskTimeExpression.type = 'datePicker';
			}
		},
		watch: ({ clearValidate }) => {
			watch(
				() => taskFormSchema.taskTimeExpression.type,
				() => {
					clearValidate(['taskTimeExpression', 'value']);
				},
			);
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
				taskFormSchema.templateId.selectConfig!.notFoundContent = h(Spin);
				searchTemplateByName({ templateName: value }).then((res) => {
					taskFormSchema.templateId.selectConfig!.options = res.map((item) => ({
						value: item.templateId,
						label: item.templateName,
					}));
					taskFormSchema.templateId.selectConfig!.notFoundContent = undefined;
				});
			}, 200),
			onChange: (value: any) => {
				getMessageParam({ templateId: Number(value) }).then((res) => {
					taskFormSchema.taskMessageParam.value = JSON.parse(res || '{}');
				});
			},
		},
		init: async ({ templateName }: Task) => {
			taskFormSchema.templateId.value = templateName;
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
				taskFormSchema.peopleGroupId.selectConfig!.notFoundContent = h(Spin);
				searchPeopleGroupByName({ peopleGroupName: value }).then((res) => {
					taskFormSchema.peopleGroupId.selectConfig!.options = res.map((item) => ({
						value: item.peopleGroupId,
						label: item.peopleGroupName,
					}));
					taskFormSchema.peopleGroupId.selectConfig!.notFoundContent = undefined;
				});
			}, 200),
		},
		init: async ({ peopleGroupName }: Task) => {
			taskFormSchema.peopleGroupId.value = peopleGroupName;
		},
	},
	taskMessageParam: {
		value: JSON.parse('{\"title\":\"oszero每天起床\",\"content\":\"兄弟们该起床啦，6点啦，太阳晒屁股了\",\"htmlFlag\":true}'),
		type: 'jsonEditor',
		fieldName: 'taskMessageParam',
		label: '任务消息参数',
		init: async ({ taskMessageParam }: Task) => {
			taskFormSchema.taskMessageParam.value = JSON.parse(taskMessageParam || '{}');
		},
	},
});
export const filterFormSchema: Schema<Omit<SearchParams, 'taskName'>> = reactive({
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

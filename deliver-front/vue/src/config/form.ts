// 搜索表单配置示例
export const searchForm: Record<string, Form.FieldItem[]> = {
	messageSearchForm: [
		{
			label: '模板名',
			field: 'templateName',
			type: 'input',
			placeholder: '请输入模板名',
		},
		{
			label: '推送范围',
			field: 'pushRange',
			type: 'select',
			placeholder: '请选择推送范围',
			options: [
				{ label: '不限', value: 0 },
				{ label: '企业内部', value: 1 },
				{ label: '企业外部', value: 2 },
			],
		},
		{
			label: '用户类型',
			field: 'usersType',
			type: 'select',
			placeholder: '请选择用户类型',
			options: [
				{ label: '企业账号', value: 0 },
				{ label: '电话', value: 1 },
				{ label: '邮箱', value: 2 },
				{ label: '平台 UserId', value: 3 },
			],
		},
		{
			label: '创建时间',
			field: 'creatTmie',
			type: 'timePicker',
			format: 'YYYY-MM-DD',
			placeholder: ['起始日期', '结束日期'],
		},
	],
}

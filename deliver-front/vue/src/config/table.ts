// 基本表格配置
export const tableColumn: Table.Column[] = [
	{ type: 'selection', width: '50' },
	{ type: 'index', width: '50', label: 'No.' },
	{ prop: 'name', label: '名字', sortable: true },
	{ type: 'date', prop: 'date', label: '日期' },
	{ prop: 'address', label: '地址', slot: 'address', showOverflowTooltip: true },
	{
		width: '120',
		label: '操作',
		buttons: [
			{ name: '编辑', type: 'success', command: 'edit' },
			{ name: '删除', type: 'danger', command: 'delete' }
		]
	}
]
// 带有分页的表格配置
export const tableDemoColumn: Table.Column[] = [
	{ type: 'index', width: '65', label: 'No.', align: 'center' },
	{ prop: 'avatar', type: 'image', label: '头像', width: '100', align: 'center' },
	{ prop: 'name', label: '姓名', width: '100' },
	{ prop: 'age', label: '年龄', width: '90', align: 'center' },
	{ prop: 'gender', label: '性别', width: '90', slot: 'gender', align: 'center' },
	{ prop: 'mobile', label: '手机号', width: '180' },
	{ prop: 'email', label: '邮箱', showOverflowTooltip: true },
	{ prop: 'address', label: '地址', showOverflowTooltip: true },
	{
		width: '120',
		label: '操作',
		buttons: [
			{ name: '编辑', type: 'success', command: 'edit' },
			{ name: '删除', type: 'danger', command: 'delete' }
		]
	}
]

//
export const tableHeader: Record<string, Table.TableHeader> = {
	templateField: {
		type: 'center',
		name: '新增模板',
		formData: [
			{
				label: '模板名',
				field: 'templateName',
				type: 'input',
				placeholder: '请填写长度在3到20个字符的模板名'
			},
			{
				label: '推送范围',
				field: 'pushRange',
				type: 'radio',
				options: [
					{
						label: '不限',
						value: 0
					},
					{
						label: '企业内部',
						value: 1
					},
					{
						label: '企业外部',
						value: 2
					}
				]
			},
			{
				label: '用户类型',
				field: 'usersType',
				type: 'radio',
				options: [
					{ value: 1, label: '企业账号', disabled: true },
					{ value: 2, label: '电话', disabled: true },
					{ value: 3, label: '邮箱', disabled: true },
					{ value: 4, label: '平台 UserId', disabled: true }
				]
			},
			{
				label: '渠道选择',
				field: 'channelType',
				type: 'select',
				options: []
			},
			{
				label: '渠道 App',
				field: 'appId',
				type: 'select',
				options: []
			},
			{
				label: '消息类型',
				field: 'templateName',
				type: 'select',
				options: []
			},
			{
				label: '模板状态',
				field: 'templateStatus',
				type: 'switch'
			}
		]
	}
}

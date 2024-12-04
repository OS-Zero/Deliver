import {
	EditTwoTone,
	DeleteTwoTone,
	DownCircleTwoTone,
	ApiTwoTone,
	ThunderboltOutlined,
	SettingOutlined,
	CopyTwoTone,
} from '@ant-design/icons-vue'
import { message } from 'ant-design-vue'
import { getMessageType, getPushRange, getUsersType, getImg } from '@/utils/table.ts'
import { useMessageStore } from '@/store/modules/message.ts'
import Table from '@/types/table'
const messageStore = useMessageStore()
const editTemplateField: Form.Feedback = {
	type: 'drawer',
	title: '新增模板',
	rules: {
		templateName: [
			{ required: true, message: '请输入模板名', trigger: 'change' },
			{ min: 3, max: 20, message: '长度在 3 到 20 个字符', trigger: 'blur' },
		],
		pushRange: [{ required: true, message: '请选择推送范围', trigger: 'change' }],
		usersType: [{ required: true, message: '请选择用户类型', trigger: 'change' }],
		channelType: [
			{
				required: true,
				message: '请选择渠道',
				trigger: 'change',
			},
		],
		appId: [
			{
				required: true,
				trigger: 'blur',
			},
		],
		messageType: [{ required: true, message: '请选择消息类型', trigger: 'change' }],
		templateStatus: [{ required: true, message: '请选择模板状态', trigger: 'change' }],
	},
	filter: (nums: Array<string | number>) => {
		const res: Array<Record<string, any>> = [
			[
				{ label: '不限', value: 0 },
				{ label: '企业内部', value: 1 },
				{ label: '企业外部', value: 2 },
			],
		]
		if (nums[0] === 0 || nums[0] === 1) {
			res.push([
				{ value: 1, label: '企业账号' },
				{ value: 2, label: '电话' },
				{ value: 3, label: '邮箱' },
				{ value: 4, label: '平台 UserId' },
			])
			switch (nums[1]) {
				case 1:
					res.push([
						{ value: 1, label: '电话' },
						{ value: 2, label: '短信' },
						{ value: 3, label: '邮件' },
						{ value: 4, label: '钉钉' },
						{ value: 5, label: '企业微信' },
						{ value: 6, label: '飞书' },
					])
					break
				case 2:
					res.push([
						{ value: 1, label: '电话' },
						{ value: 2, label: '短信' },
						{ value: 4, label: '钉钉' },
						{ value: 5, label: '企业微信' },
						{ value: 6, label: '飞书' },
					])
					break
				case 3:
					res.push([{ value: 3, label: '邮件' }])
					break
				case 4:
					res.push([
						{ value: 4, label: '钉钉' },
						{ value: 5, label: '企业微信' },
						{ value: 6, label: '飞书' },
					])
					break
				default:
					res.push([])
					break
			}
		} else {
			res.push([
				{ value: 2, label: '电话' },
				{ value: 3, label: '邮箱' },
			])
			switch (nums[1]) {
				case 2:
					res.push([
						{ value: 1, label: '电话' },
						{ value: 2, label: '短信' },
					])
					break
				case 3:
					res.push([{ value: 3, label: '邮件' }])
					break
				default:
					res.push([])
					break
			}
		}
		return res
	},
	requests: [
		{
			field: 'appId',
			param: ['appId'],
			request: async (num: number) => {
				try {
					return await messageStore.getApp(num)
				} catch (error) {
					console.log(error)
					throw error
				}
			},
		},
		{
			field: 'messageType',
			param: ['messageType'],
			request: async (num: number) => {
				try {
					return await messageStore.getMessageType(num)
				} catch (error) {
					console.log(error)
					throw error
				}
			},
		},
	],
	cascader: [{ field: 'pushRange' }, { field: 'usersType' }, { field: 'channelType' }],
	formData: [
		{
			label: '模板名',
			field: 'templateName',
			type: 'input',
			placeholder: '请填写长度在3到20个字符的模板名',
		},
		{
			type: 'cascader',
			field: 'cascader',
			options: [
				{
					label: '推送范围',
					field: 'pushRange',
					options: [
						{ label: '不限', value: 0 },
						{ label: '企业内部', value: 1 },
						{ label: '企业外部', value: 2 },
					],
				},
				{
					label: '用户类型',
					field: 'usersType',
					options: [
						{ value: 1, label: '企业账号' },
						{ value: 2, label: '电话' },
						{ value: 3, label: '邮箱' },
						{ value: 4, label: '平台 UserId' },
					],
				},
				{
					label: '渠道选择',
					field: 'channelType',
					options: [
						{ value: 1, label: '电话' },
						{ value: 2, label: '短信' },
						{ value: 3, label: '邮件' },
						{ value: 4, label: '钉钉' },
						{ value: 5, label: '企业微信' },
						{ value: 6, label: '飞书' },
					],
				},
			],
		},
		{
			label: '渠道 App',
			field: 'appId',
			type: 'select',
			options: 'function',
		},
		{
			label: '消息类型',
			field: 'messageType',
			type: 'select',
			options: 'function',
		},
		{
			label: '模板状态',
			field: 'templateStatus',
			type: 'switch',
		},
	],
}
//测试发送表单
const sendTest: Form.Feedback = {
	type: 'drawer',
	title: '测试消息模版发送',
	requests: [
		{
			field: 'paramMap',
			params: ['messageType', 'channelType'],
			request: async (messageType: number, channelType: number) => {
				try {
					return await messageStore.getMessageParamByMessageType(messageType, channelType)
				} catch (error) {
					console.log(error)
					throw error
				}
			},
		},
	],
	rules: {},
	formData: [
		{
			type: 'list',
			label: '用户列表',
			field: 'users',
			options: {
				header: '用户 ID 列表',
				tip: '添加用户',
				placeholder: '请输入用户 ID 添加至用户列表',
			},
		},
		{
			type: 'jsonEditor',
			label: '发送参数',
			field: 'paramMap',
		},
		{
			type: 'inputNumber',
			label: '重试次数',
			field: 'retry',
		},
	],
}
// 基本表格配置
export const tableColumns: Table.Columns[] = [
	{
		title: '模板 ID',
		dataIndex: 'templateId',
		key: 'templateId',
		width: 106,
		head: 'icon',
		type: 'icon',
		headIcon: ThunderboltOutlined,
		bodyIcon: CopyTwoTone,
		options: {
			tip: '复制 TemplateId 发送消息吧~',
			func: (text: string): void => {
				navigator.clipboard.writeText(text)
				message.success('复制成功')
			},
		},
	},
	{
		title: '模板名',
		dataIndex: 'templateName',
		key: 'templateName',
	},
	{
		type: 'blue',
		title: '消息类型',
		dataIndex: 'messageType',
		key: 'messageType',
		filter: getMessageType,
	},
	{
		type: 'tag',
		title: '推送范围',
		dataIndex: 'pushRange',
		key: 'pushRange',
		filter: getPushRange,
	},
	{
		type: 'tag',
		title: '用户类型',
		dataIndex: 'usersType',
		key: 'usersType',
		filter: getUsersType,
	},
	{
		type: 'img',
		title: '渠道类型',
		dataIndex: 'channelType',
		key: 'channelType',
		filter: getImg,
	},
	{
		type: 'switch',
		title: '模板状态',
		dataIndex: 'templateStatus',
		key: 'templateStatus',
	},
	{
		type: 'operation',
		title: '操作',
		fixed: 'right',
		width: 270,
		head: 'icon',
		headIcon: SettingOutlined,
		buttons: [
			{
				command: 'expand',
				type: 'link',
				size: 'large',
				tip: '查看消息模版更多信息',
				icon: DownCircleTwoTone,
			},
			{
				command: 'edit',
				type: 'link',
				size: 'large',
				feedback: 'drawer',
				config: { ...editTemplateField, title: '修改模板' },
				tip: '修改消息模版',
				icon: EditTwoTone,
				options: { rowKey: 'templateId', resetButtonText: '重置', submitButtonText: '确认修改' },
			},
			{
				command: 'clone',
				type: 'link',
				size: 'large',
				feedback: 'drawer',
				config: { ...editTemplateField, title: '新增模板' },
				tip: '克隆消息模版',
				icon: CopyTwoTone,
				options: { resetButtonText: '重置', submitButtonText: '确认新建' },
			},
			{
				command: 'send',
				type: 'link',
				size: 'large',
				feedback: 'drawer',
				config: sendTest,
				tip: '测试消息模板发送',
				icon: ApiTwoTone,
				options: { rowKey: 'templateId', resetButtonText: '清空', submitButtonText: '发送' },
			},
			{
				command: 'delete',
				type: 'link',
				size: 'large',
				tip: '删除消息模版',
				icon: DeleteTwoTone,
				color: 'red',
				confirm: true,
				options: { rowKey: 'templateId', title: '确认删除吗' },
			},
		],
	},
]

//表头表单配置
export const tableHeader: Record<string, Form.Feedback> = { editTemplateField }

//表头选项配置
export const tableHeaderOptions: Record<string, Form.Options> = {
	templateOption: {
		resetButtonText: '重置',
		submitButtonText: '确认新建',
	},
}
//表单选项
export const tableOptions: Record<string, Table.Options> = {
	templateOption: {
		rowKey: 'templateId',
		expands: [
			{ field: 'useCount', alias: '模板累计使用数' },
			{ field: 'appId', alias: '关联 AppId' },
			{ field: 'appName', alias: '关联 AppName' },
			{ field: 'createUser', alias: '创建者' },
			{ field: 'createTime', alias: '创建时间' },
		],
		paginationConfig: {
			current: 1,
			pageSize: 10,
			total: 0,
			pageSizeOptions: ['10', '20', '50', '100'],
			showQuickJumper: true,
			showSizeChanger: true,
			locale: {
				items_per_page: '条/页', // 每页显示条数的文字描述
				jump_to: '跳至', // 跳转到某页的文字描述
				page: '页', // 页的文字描述
				prev_page: '上一页', // 上一页按钮文字描述
				next_page: '下一页', // 下一页按钮文字描述
			},
			showTotal: (total: number) => `共 ${total} 条数据`,
		},
	},
}
//表格表单配置
export const tableForm = {}

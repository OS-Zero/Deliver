import { EditTwoTone, DeleteTwoTone, DownCircleTwoTone, ApiTwoTone, ThunderboltOutlined, SettingOutlined } from '@ant-design/icons-vue'
import { validatePass } from '@/utils/validate'
import { getMessageType, getPushRange, getUsersType, getImg } from '@/utils/table'

const templateField: Form.Modal = {
	type: 'center',
	title: '新增模板',
	rules: {
		templateName: [
			{ required: true, message: '请输入模板名', trigger: 'change' },
			{ min: 3, max: 20, message: '长度在 3 到 20 个字符', trigger: 'blur' }
		],
		pushRange: [{ required: true, message: '请选择推送范围', trigger: 'change' }],
		usersType: [{ required: true, message: '请选择用户类型', trigger: 'change' }],
		channelType: [
			{
				required: true,
				message: '请选择渠道',
				trigger: 'change'
			}
		],
		appId: [
			{
				required: true,
				validator: validatePass,
				trigger: 'blur'
			}
		],
		messageType: [{ required: true, message: '请选择消息类型', trigger: 'change' }],
		templateStatus: [{ required: true, message: '请选择模板状态', trigger: 'change' }]
	},
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
// 基本表格配置
export const tableColumns: Table.Columns[] = [
	{
		title: '模板 ID',
		dataIndex: 'templateId',
		width: 106,
		head: 'icon',
		icon: ThunderboltOutlined
	},
	{
		title: '模板名',
		dataIndex: 'templateName'
	},
	{
		type: 'blue',
		title: '消息类型',
		dataIndex: 'messageType',
		filter: getMessageType
	},
	{
		type: 'tag',
		title: '推送范围',
		dataIndex: 'pushRange',
		filter: getPushRange
	},
	{
		type: 'tag',
		title: '用户类型',
		dataIndex: 'usersType',
		filter: getUsersType
	},
	{
		type: 'img',
		title: '渠道类型',
		dataIndex: 'channelType',
		filter: getImg
	},
	{
		type: 'switch',
		title: '模板状态',
		dataIndex: 'templateStatus'
	},
	{
		type: 'operation',
		title: '操作',
		fixed: 'right',
		width: 270,
		head: 'icon',
		icon: SettingOutlined,
		buttons: [
			{ command: 'view', type: 'link', size: 'small', tip: '查看消息模版更多信息', icon: DownCircleTwoTone },
			{ command: 'eidt', type: 'link', size: 'small', modal: 'center', config: templateField, tip: '修改消息模版', icon: EditTwoTone },
			{ command: 'send', type: 'link', size: 'small', modal: 'center', config: templateField, tip: '测试消息模板发送', icon: ApiTwoTone },
			{ command: 'delete', type: 'link', size: 'small', tip: '删除消息模版', icon: DeleteTwoTone, color: 'red' }
		]
	}
]

//表头表单配置
export const tableHeader: Record<string, Form.Modal> = { templateField }

//表格表单配置
export const tableForm = {}

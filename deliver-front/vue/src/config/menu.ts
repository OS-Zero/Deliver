import { h } from 'vue'
import {
	MessageOutlined,
	FileTextOutlined,
	DashboardOutlined,
	SmileOutlined,
	AppstoreOutlined,
} from '@ant-design/icons-vue'
import { ItemType } from 'ant-design-vue'
import ItemLink from '../layouts/components/ItemLink/index.vue'

export const menuConfig: ItemType[] = [
	{
		key: 'WC',
		icon: h(SmileOutlined),
		label: '欢迎',
		children: [
			{
				key: 'welcome',
				label: h(ItemLink, { label: '欢迎', to: 'welcome' }),
			},
		],
	},
	{
		key: 'CDB',
		icon: h(DashboardOutlined),
		label: '控制面板',
		children: [
			{
				key: 'SysMD',
				label: h(ItemLink, { label: '系统监控看板', to: 'dashboard' }),
			},
		],
	},
	{
		key: 'MC',
		icon: h(MessageOutlined),
		label: '消息配置',
		children: [
			{
				key: 'MTC',
				label: h(ItemLink, { label: '消息模板配置', to: 'template' }),
			},
		],
	},
	{
		key: 'AC',
		icon: h(AppstoreOutlined),
		label: '应用配置',
		children: [
			{
				key: 'CAC',
				label: h(ItemLink, { label: '渠道应用配置', to: 'app' }),
			},
		],
	},
	{
		key: 'FM',
		icon: h(FileTextOutlined),
		label: '文件管理',
		children: [
			{
				key: 'PFM',
				label: h(ItemLink, { label: '平台文件管理', to: 'file' }),
			},
		],
	},
]

import { h } from 'vue'
import { MessageOutlined, FileTextOutlined, AppstoreOutlined, SettingOutlined } from '@ant-design/icons-vue'
import { ItemType } from 'ant-design-vue'
import ItemLink from '@/views/Layout/components/ItemLink/index.vue'
export const menuConfig: ItemType[] = [
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
	{
		key: 'SS',
		icon: h(SettingOutlined),
		label: h(ItemLink, { label: '系统设置', to: 'systemSettings' }),
	},
]

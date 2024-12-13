import { h } from 'vue'
import { MessageOutlined, FileTextOutlined, AppstoreOutlined, UserOutlined } from '@ant-design/icons-vue'
import { ItemType } from 'ant-design-vue'
import ItemLink from '@/views/Layout/components/ItemLink/index.vue'
export const menuConfig: Record<string, ItemType[]> = {
	groupManage: [
		{
			key: 'MC',
			icon: h(MessageOutlined),
			label: '消息配置',
			children: [
				{
					key: 'MTC',
					label: h(ItemLink, { label: '消息模板配置', to: '/groupManage/template' }),
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
					label: h(ItemLink, { label: '渠道应用配置', to: '/groupManage/app' }),
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
					label: h(ItemLink, { label: '平台文件管理', to: '/groupManage/file' }),
				},
			],
		},
	],
	systemManage: [
		{
			key: 'myAccount',
			icon: h(UserOutlined),
			label: h(ItemLink, { label: '我的账户', to: '/systemManage/myAccount' }),
		},
	],
}

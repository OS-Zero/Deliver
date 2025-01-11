import { h } from 'vue';
import { MessageOutlined, FileTextOutlined, AppstoreOutlined, UserOutlined, SoundOutlined } from '@ant-design/icons-vue';
import { ItemType } from 'ant-design-vue';
import ItemLink from '@/views/Layout/components/ItemLink/index.vue';
export const menuConfig: Record<string, ItemType[]> = {
	groupManage: [
		{
			key: 'MC',
			icon: h(MessageOutlined),
			label: '消息配置',
			children: [
				{
					key: '/groupManage/template',
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
					key: '/groupManage/app',
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
					key: '/groupManage/file',
					label: h(ItemLink, { label: '平台文件管理', to: '/groupManage/file' }),
				},
			],
		},
		{
			key: 'MST',
			icon: h(SoundOutlined),
			label: '群发任务',
			children: [
				{
					key: '/groupManage/task',
					label: h(ItemLink, { label: '群发任务配置', to: '/groupManage/task' }),
				},
				{
					key: '/groupManage/peopleGroup',
					label: h(ItemLink, { label: '人群模板配置', to: '/groupManage/peopleGroup' }),
				},
			],
		},
	],
	systemManage: [
		{
			key: '/systemManage/myAccount',
			icon: h(UserOutlined),
			label: h(ItemLink, { label: '我的账户', to: '/systemManage/myAccount' }),
		},
	],
};

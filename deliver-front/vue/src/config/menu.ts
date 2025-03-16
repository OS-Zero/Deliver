import { h } from 'vue';
import { ItemType } from 'ant-design-vue';
import ItemLink from '@/views/Layout/components/ItemLink/index.vue';
import { createIcon } from '@/utils/createIcon';

const data = {
	groupManage: [
		{
			key: 'MC',
			icon: 'MessageOutlined',
			label: '模板配置',
			children: [
				{
					key: '/groupManage/template',
					label: ['消息模板配置', '/groupManage/template'],
				},
			],
		},
		{
			key: 'AC',
			icon: 'AppstoreOutlined',
			label: '应用配置',
			children: [
				{
					key: '/groupManage/app',
					label: ['渠道应用配置', '/groupManage/app'],
				},
			],
		},
		{
			key: 'FM',
			icon: 'FileTextOutlined',
			label: '文件管理',
			children: [
				{
					key: '/groupManage/file',
					label: ['平台文件管理', '/groupManage/file'],
				},
			],
		},
		{
			key: 'MST',
			icon: 'SoundOutlined',
			label: '群发任务',
			children: [
				{
					key: '/groupManage/task',
					label: ['群发任务配置', '/groupManage/task'],
				},
				{
					key: '/groupManage/peopleGroup',
					label: ['人群模板配置', '/groupManage/peopleGroup'],
				},
			],
		},
	],
	systemManage: [
		{
			key: '/systemManage/myAccount',
			icon: 'UserOutlined',
			label: ['我的账户', '/systemManage/myAccount'],
		},
		{
			key: '/systemManage/sentinel',
			icon: 'DashboardOutlined',
			label: ['Sentinel控制台', '/systemManage/sentinel'],
		},
	],
};
const generateMenuConfig = (menuList: any) => {
	return menuList.map((item: any) => {
		const obj = {
			...item,
			icon: createIcon({ name: item.icon }),
			label: Array.isArray(item.label) ? h(ItemLink, { label: item.label[0], to: item.label[1] }) : item.label,
		};
		obj.children && (obj.children = generateMenuConfig(item.children));
		return obj;
	});
};
export const menuConfig: Record<string, ItemType[]> = {
	groupManage: generateMenuConfig(data.groupManage),
	systemManage: generateMenuConfig(data.systemManage),
};

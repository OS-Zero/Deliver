import { h, reactive } from 'vue';
import { ItemType } from 'ant-design-vue';
import ItemLink from '@/views/Layout/components/ItemLink/index.vue';
import { createIcon } from '@/utils/createIcon';

const generateMenuConfig = (menuList: any) => {
	return menuList.map((item: any) => {
		const obj = {
			...item,
			icon: createIcon({ name: item.icon }),
			label: item.children ? item.label : h(ItemLink, { label: item.label, to: item.key }),
		};
		obj.children && (obj.children = generateMenuConfig(item.children));
		return obj;
	});
};
export function useMenuConfig() {
	const data = JSON.parse(localStorage.getItem('startup') || '{}').currentLoginUserMenu;
	const updateMenuConfig = () => {
		const data = JSON.parse(localStorage.getItem('startup') || '{}').currentLoginUserMenu;
		menuConfig.groupManage = generateMenuConfig(data.groupManage);
		menuConfig.systemManage = generateMenuConfig(data.systemManage);
	};
	const menuConfig = reactive<Record<string, ItemType[]>>({
		groupManage: generateMenuConfig(data.groupManage),
		systemManage: generateMenuConfig(data.systemManage),
	});
	return { menuConfig, updateMenuConfig };
}

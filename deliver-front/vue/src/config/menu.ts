import { h } from 'vue';
import { ItemType } from 'ant-design-vue';
import ItemLink from '@/views/Layout/components/ItemLink/index.vue';
import { createIcon } from '@/utils/createIcon';

const data = JSON.parse(localStorage.getItem('startup') || '{}').currentLoginUserMenu;
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
export const menuConfig: Record<string, ItemType[]> = {
	groupManage: generateMenuConfig(data.groupManage),
	systemManage: generateMenuConfig(data.systemManage),
};

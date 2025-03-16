import * as icons from '@ant-design/icons-vue';
import { h } from 'vue';

export const createIcon = ({ name }) => {
	const Icon = icons[name];
	return Icon ? h(Icon) : name;
};

import { SelectProps } from 'ant-design-vue';
import { Rule } from 'ant-design-vue/es/form';

type ItemType =
	| 'none'
	| 'input'
	| 'textarea'
	| 'inputNumber'
	| 'timePicker'
	| 'select'
	| 'radio'
	| 'switch'
	| 'cascader'
	| 'jsonEditor'
	| 'list'
	| 'datePicker';

export interface FormItem<T> {
	value?: any;
	type: ItemType;
	fieldName: T;
	label?: string;
	placeholder?: string;
	initValue?: any;
	rules?: Rule[];
	options?: SelectProps['options'];
	disabled?: boolean;
	editorConfig?: Record<string, any>;
	max?: number;
}

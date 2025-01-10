import { SelectProps, UploadProps } from 'ant-design-vue';
import { Rule } from 'ant-design-vue/es/form';

type ItemType =
	| 'none'
	| 'input'
	| 'inputPassword'
	| 'textarea'
	| 'inputNumber'
	| 'timePicker'
	| 'select'
	| 'radio'
	| 'switch'
	| 'cascader'
	| 'jsonEditor'
	| 'list'
	| 'datePicker'
	| 'upload'
	| 'button'
	| 'verificationCode';

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
	uploadConifg?: UploadProps;
	buttonConfig?: Record<string, any>;
	max?: number;
}

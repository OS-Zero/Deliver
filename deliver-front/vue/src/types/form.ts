import { RadioGroupProps, SelectProps, UploadProps } from 'ant-design-vue';
import { Rule } from 'ant-design-vue/es/form';
export interface UploadConfig extends UploadProps {
	title?: string;
	description?: string;
}
type ItemType =
	| 'none'
	| 'input'
	| 'inputPassword'
	| 'textarea'
	| 'inputNumber'
	| 'select'
	| 'radio'
	| 'switch'
	| 'cascader'
	| 'jsonEditor'
	| 'list'
	| 'datePicker'
	| 'upload'
	| 'button'
	| 'verificationCode'
	| 'radioGroup';

export interface FormItem<T> {
	value?: any;
	type: ItemType;
	fieldName: T;
	label?: string;
	depSock?: boolean;
	placeholder?: string;
	initValue?: any;
	rules?: Rule[];
	options?: SelectProps['options'];
	disabled?: boolean;
	textareaConfig?: Record<string, any>;
	editorConfig?: Record<string, any>;
	uploadConifg?: UploadConfig;
	buttonConfig?: Record<string, any>;
	radioGroupConfig?: RadioGroupProps;
	selectConfig?: SelectProps;
	max?: number;
	tip?: string;
}

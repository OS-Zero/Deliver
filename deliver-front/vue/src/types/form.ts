import { InputProps, RadioGroupProps, SelectProps, TextAreaProps, UploadProps } from 'ant-design-vue';
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
	rules?: Rule[];
	textareaConfig?: TextAreaProps;
	editorConfig?: Record<string, any>;
	uploadConifg?: UploadConfig;
	buttonConfig?: Record<string, any>;
	radioGroupConfig?: RadioGroupProps;
	inputConfig?: InputProps;
	selectConfig?: SelectProps;
	customConfig?: Record<string, any>;
}

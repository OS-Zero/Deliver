import { ButtonProps, InputProps, RadioGroupProps, SelectProps, TextAreaProps, UploadProps } from 'ant-design-vue';
import { Rule } from 'ant-design-vue/es/form';
interface ExtendButtonProps extends ButtonProps {
	name?: string;
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
	| 'verifyCode'
	| 'radioGroup';

export interface FormItem<T> {
	value?: any;
	init?: (...args: any[]) => Promise<void>;
	watch?: (...args: any[]) => void;
	type: ItemType;
	fieldName: T;
	label?: string;
	depSock?: boolean;
	rules?: Rule[];
	textareaConfig?: TextAreaProps;
	editorConfig?: Record<string, any>;
	uploadConifg?: UploadProps;
	buttonConfig?: ExtendButtonProps;
	radioGroupConfig?: RadioGroupProps;
	inputConfig?: InputProps;
	selectConfig?: SelectProps;
	verifyCodeConfig?: {
		disabled?: boolean;
		submit?: () => void;
	};
	customConfig?: Record<string, any>;
}

import type { Rule } from 'ant-design-vue'
export = Form
export as namespace Form
declare namespace Form {
	type ItemType = 'input' | 'inputNumber' | 'timePicker' | 'select' | 'radio' | 'switch' | 'cascader' | 'jsonEditor' | 'list'
	interface FieldItem {
		label?: string
		field: string
		type?: ItemType
		value?: any
		placeholder?: string | Array<string>
		options?: any
		rules?: FormItemRule[]
		format?: string // 当输入为事件选择器时，指定日期格式
		title?: string
	}
	interface Options {
		labelWidth?: string | number
		labelPosition?: 'left' | 'right' | 'top'
		disabled?: boolean
		size?: 'large' | 'small' | 'default'
		showResetButton?: boolean // 是否展示重置按钮
		showCancelButton?: boolean // 是否展示取消按钮
		submitButtonText?: string
		resetButtonText?: string
		cancelButtonText?: string
	}
	interface Feedback {
		type: 'modal' | 'drawer'
		title: string
		rules?: Record<string, Rule[]>
		formData: FieldItem[]
		requests?: Array<any>
		filter?: any
		cascader?: Array<Record<string, any>>
	}
}

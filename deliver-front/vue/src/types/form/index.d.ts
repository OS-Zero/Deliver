import type { Rule } from 'ant-design-vue'
export = Form
export as namespace Form
declare namespace Form {
	type ItemType = 'input' | 'timePicker' | 'select' | 'radio' | 'switch'
	interface FieldItem {
		label?: string
		field: string
		type?: ItemType
		value?: string
		placeholder?: string | Array<string>
		options?: Array<Record<string, any>>
		rules?: FormItemRule[]
		format?: string // 当输入为事件选择器时，指定日期格式
	}
	interface Modal {
		type: 'center' | 'right'
		title: string
		rules?: Record<string, Rule[]>
		formData: FieldItem[]
	}
}

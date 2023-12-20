import { FunctionalComponent } from 'vue'
import { AntdIconProps } from '../components/AntdIcon'
export = Table
export as namespace Table
declare namespace Table {
	type VNode = import('vue').VNode
	type Type = 'selection' | 'index' | 'expand' | 'image' | 'date'
	type Size = 'large' | 'default' | 'small'
	type Align = 'center' | 'left' | 'right'
	type Command = string | number
	type DateFormat = 'YYYY-MM-DD' | 'YYYY-MM-DD HH:mm:ss' | 'YYYY-MM-DD HH:mm' | 'YYYY-MM'
	type Order = 'ascending' | 'descending'

	interface ButtonItem {
		command: Command
		size?: Size
		type?: 'primary' | 'dashed' | 'text' | 'link'
		tip?: string
		color?: string
		feedback?: 'modal' | 'drawer'
		config?: Form.Modal
		icon?: FunctionalComponent<AntdIconProps>
		name?: string
		confirm?: boolean
		options?: any
	}
	interface Sort {
		prop: string
		order: Order
		init?: any
		silent?: any
	}
	interface Columns {
		type?: 'tag' | 'blue' | 'img' | 'switch' | 'operation' | 'icon'
		title?: string
		dataIndex?: string
		key?: string
		width?: number
		fixed?: string
		head?: string
		headIcon?: FunctionalComponent<AntdIconProps>
		bodyIcon?: FunctionalComponent<AntdIconProps>
		buttons?: ButtonItem[]
		options?: any
		filter?: (val: any) => any
	}
	interface Options {
		height?: string | number
		// Table 的高度， 默认为自动高度。 如果 height 为 number 类型，单位 px；如果 height 为 string 类型，则这个高度会设置为 Table 的 style.height 的值，Table 的高度会受控于外部样式。
		stripe?: boolean // 是否为斑马纹 table
		maxHeight?: string | number // Table 的最大高度。 合法的值为数字或者单位为 px 的高度。
		size?: Size // Table 的尺寸
		showHeader?: boolean // 是否显示表头,
		tooltipEffect?: 'dark' | 'light' // tooltip effect 属性
		showPagination?: boolean // 是否展示分页器
		paginationConfig: Pagination // 分页器配置项，详情见下方 paginationConfig 属性,
		rowStyle?: ({ row, rowIndex }) => stirng | object // 行的 style 的回调方法，也可以使用一个固定的 Object 为所有行设置一样的 Style。
		headerCellStyle?: import('vue').CSSProperties // 表头单元格的style样式，是一个object为所有表头单元格设置一样的 Style。注：CSSProperties类型就是一个对象，像正常在style中写css一样 {color: #f00}
		defaultSort?: Sort // 默认的排序列的 prop 和顺序。 它的 prop 属性指定默认的排序的列，order 指定默认排序的顺序。
		rowKey: string // 行数据的 Key，用来优化 Table 的渲染。
		expands?: Array<Record<string, any>>
	}
	interface Pagination {
		total?: number // 总条目数
		current: number // 当前页数，支持 v-model 双向绑定
		pageSize: number // 每页显示条目个数，支持 v-model 双向绑定
		defaultPageSize?: number // 每页显示个数选择器的选项设置
		layout?: string // 组件布局，子组件名用逗号分隔
		background?: boolean // 是否为分页按钮添加背景色
		pageSizeOptions?: string[] | number[]
		showQuickJumper?: boolean
		showSizeChanger?: boolean
		locale?: Record<string, any>
		showTotal?: any
	}
}

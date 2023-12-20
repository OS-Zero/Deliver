<script lang="ts" setup>
import { h, ref, reactive, onMounted, onUnmounted, watch } from 'vue'
import Table from '@/types/table'
import { getColor } from '@/utils/table.ts'
import emitter from '@/utils/mitt.ts'
import { Modal } from 'ant-design-vue'
import { ExclamationCircleOutlined, UpCircleTwoTone, DownCircleTwoTone } from '@ant-design/icons-vue'
type Key = string | number
interface Props {
	model: Array<Record<string, any>>
	columns: Table.Columns[]
	options: Table.Options
}
interface EmitEvent {
	(e: 'actions', command: any, val?: any, callback?: any): any
}
interface State {
	selectedRowKeys: Key[]
	loading: boolean
	collapsed: boolean
}
const props = defineProps<Props>()
const emit = defineEmits<EmitEvent>()
const state = reactive<State>({
	selectedRowKeys: [],
	loading: true,
	collapsed: false
})
watch(
	props,
	() => {
		state.selectedRowKeys = []
	},
	{
		immediate: true
	}
)
const onSelectChange = (selectedRowKeys: Key[]): void => {
	state.selectedRowKeys = selectedRowKeys
}
let submit: any = null
const handleAction = (command: string) => {
	emit('actions', command, undefined, (func: any) => {
		submit = func
	})
}
const cancelSelect = () => {
	state.selectedRowKeys = []
}
const [modal, contextHolder] = Modal.useModal()
const showDeleteConfirm = () => {
	modal.confirm({
		title: '确认删除吗?',
		icon: h(ExclamationCircleOutlined),
		content: '删除后不可恢复，请谨慎删除！',
		okText: '确认',
		okType: 'danger',
		cancelText: '取消',
		onOk() {
			emit('actions', 'delete', state.selectedRowKeys)
		}
	})
}
const expandedRowKeys = ref<number[]>([])
const toggleExpand = (expand: boolean, record: any) => {
	expandedRowKeys.value = []
	if (expand) {
		expandedRowKeys.value.push(record[props.options.rowKey])
	}
}
const change = (page: number, pageSize: number) => {
	console.log(props.options.paginationConfig)

	emitter.emit('loading', true)
	emit('actions', 'search', { currentPage: page, pageSize: pageSize })
}
const changeStatus = (name1: string, val1: any, name2: string, val2: string) => {
	const obj = {}
	obj[name1] = val1
	obj[name2] = val2
	emit('actions', 'status', obj)
}
onMounted(() => {
	emitter.on('loading', (loading) => {
		state.loading = loading
	})
	emitter.on('collapsed', (collapsed) => {
		state.collapsed = collapsed
	})
})
onUnmounted(() => {
	emitter.off('loading')
	emitter.off('collapsed')
})
</script>
<template>
	<div class="table">
		<div class="describe" v-show="state.selectedRowKeys.length">
			<span>
				{{ `已选择 ${state.selectedRowKeys.length} 项` }}
			</span>
			<a-button type="link" @click="cancelSelect">取消选择</a-button>
		</div>
		<a-table
			:columns="columns"
			:data-source="model"
			:pagination="false"
			:scroll="{ x: 1200, y: undefined, scrollToFirstRowOnChange: true }"
			:expandedRowKeys="expandedRowKeys"
			:expandIconAsCell="false"
			:expandIconColumnIndex="-1"
			:loading="state.loading"
			:row-key="(record: any) => record[props.options.rowKey]"
			:row-selection="{ selectedRowKeys: state.selectedRowKeys, onChange: onSelectChange }">
			<template #headerCell="{ column }">
				<template v-if="column.head === 'icon'">
					<span>
						<component :is="h(column.headIcon)"></component>
						{{ column.title }}
					</span>
				</template>
			</template>
			<template #bodyCell="{ column, record, index }">
				<template v-if="column.type === 'icon'">
					<span style="font-weight: 700; margin-right: 5px">
						{{ column.filter ? column.filter(record[column.dataIndex]) : record[column.dataIndex] }}
					</span>
					<a-tooltip>
						<template #title>{{ column.options.tip }}</template>
						<component :is="h(column.bodyIcon)" @click="column.options.func(record[column.dataIndex])"></component>
					</a-tooltip>
				</template>
				<template v-else-if="column.type === 'tag'">
					<a-tag :color="getColor(record[column.dataIndex])" style="width: 80px; text-align: center">
						<span>{{ column.filter ? column.filter(record[column.dataIndex]) : record[column.dataIndex] }}</span>
					</a-tag>
				</template>
				<template v-else-if="column.type === 'blue'">
					<span style="color: #1677ff">{{ column.filter ? column.filter(record[column.dataIndex]) : record[column.dataIndex] }}</span>
				</template>
				<template v-else-if="column.type === 'img'">
					<img style="height: 30px; width: 30px" :src="column.filter(record[column.dataIndex])" :alt="record[column.dataIndex]" />
				</template>
				<template v-else-if="column.type === 'switch'">
					<a-switch
						v-model:checked="record[column.dataIndex]"
						checked-children="启用"
						un-checked-children="禁用"
						:checkedValue="1"
						:unCheckedValue="0"
						@click="changeStatus(options.rowKey, record[options.rowKey], column.dataIndex, record[column.dataIndex])" />
				</template>
				<template v-else-if="column.type === 'operation'">
					<template v-for="btn in column.buttons" :key="btn.command">
						<span v-if="btn.command === 'expand'">
							<a-button
								type="link"
								size="small"
								style="font-size: 14px"
								@click="toggleExpand(false, record)"
								v-show="expandedRowKeys[0] === record[props.options.rowKey]">
								<UpCircleTwoTone style="font-size: 18px" />
							</a-button>
							<a-tooltip>
								<template #title>查看消息模版更多信息</template>
								<a-button
									type="link"
									size="small"
									style="font-size: 14px"
									@click="toggleExpand(true, record)"
									v-show="expandedRowKeys[0] !== record[props.options.rowKey]">
									<DownCircleTwoTone style="font-size: 18px" />
								</a-button>
							</a-tooltip>
						</span>
						<a-popconfirm
							:title="btn.options.title"
							@confirm="emit('actions', btn.command, record[props.options.rowKey])"
							ok-text="确定"
							cancel-text="取消"
							v-else-if="btn.confirm">
							<a-tooltip placement="bottom">
								<template #title>{{ btn.tip }}</template>
								<a-button :type="btn.type" :size="btn.size">
									<component :is="h(btn.icon)" :two-tone-color="btn.color"></component>
								</a-button>
							</a-tooltip>
						</a-popconfirm>
						<a-tooltip v-else>
							<template #title>{{ btn.tip }}</template>
							<Modal
								@click="handleAction(btn.command)"
								@submit="submit"
								:_options="btn.options"
								:config="btn.config"
								:model="model[index]"
								v-if="btn.feedback === 'modal'">
								<template #button="{ openModel }">
									<a-button :type="btn.type" :size="btn.size" @click="openModel">
										<component :is="h(btn.icon)" :two-tone-color="btn.color"></component>
									</a-button>
								</template>
							</Modal>
							<Drawer
								@click="handleAction(btn.command)"
								@submit="submit"
								:_options="btn.options"
								:config="btn.config"
								:model="model[index]"
								v-else-if="btn.feedback === 'drawer'">
								<template #button="{ openModel }">
									<a-button :type="btn.type" :size="btn.size" @click="openModel">
										<component :is="h(btn.icon)" :two-tone-color="btn.color"></component>
									</a-button>
								</template>
							</Drawer>
							<a-button @click="emit('actions', btn.command, record[props.options.rowKey])" v-else :type="btn.type" :size="btn.size">
								<component :is="h(btn.icon)" :two-tone-color="btn.color"></component>
							</a-button>
						</a-tooltip>
					</template>
				</template>
			</template>
			<template #expandedRowRender="{ record }">
				<a-row :gutter="24">
					<a-col :span="8" v-for="item in options.expands" :key="item">{{ item.alias }} : {{ record[item.field] }}</a-col>
				</a-row>
			</template>
		</a-table>
		<div class="pagination">
			<a-pagination
				v-model:current="options.paginationConfig.current"
				v-model:pageSize="options.paginationConfig.pageSize"
				:total="options.paginationConfig.total"
				:pageSizeOptions="options.paginationConfig.pageSizeOptions"
				:show-total="options.paginationConfig.showTotal"
				:showQuickJumper="options.paginationConfig.showQuickJumper"
				:showSizeChanger="options.paginationConfig.showSizeChanger"
				:locale="options.paginationConfig.locale"
				@change="change" />
		</div>
		<div class="showDelete" v-show="state.selectedRowKeys.length" :style="{ left: state.collapsed ? '80px' : '200px' }">
			<div class="box">{{ `已选择 ${state.selectedRowKeys.length} 项` }}</div>
			<div class="del">
				<a-button type="primary" style="font-size: 14px" :loading="state.loading" @click="showDeleteConfirm">批量删除</a-button>
				<contextHolder />
			</div>
		</div>
	</div>
</template>
<style lang="scss" scoped>
.describe {
	width: 100%;
	height: 40px;
	display: flex;
	justify-content: space-between;
	align-items: center;
	background-color: #f8f8f8;
	margin-bottom: 12px;
	padding: 0 12px;
	color: gray;
	border-radius: 6px;
}
.showDelete {
	background-color: #fefefe;
	z-index: 100;
	position: fixed;
	height: 60px;
	display: flex;
	justify-content: space-between;
	align-items: center;
	padding: 0 24px;
	bottom: 0;
	right: 0;
	transition: 0.2s;
}
.pagination {
	height: 60px;
	display: flex;
	justify-content: end;
	align-items: end;
}
</style>

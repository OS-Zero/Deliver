<script lang="ts" setup>
import { h } from 'vue'
import { ReloadOutlined } from '@ant-design/icons-vue'
import Form from '@/types/form'
interface EmitEvent {
	(e: 'reflash'): void
	(e: 'submit', param: any, callback: (err: boolean) => void): void
}
interface Props {
	config: Form.Feedback
	model: Record<Form.FieldItem['field'], Form.FieldItem['value']>
	options: Form.Options
}
const emit = defineEmits<EmitEvent>()
defineProps<Props>()

/**
 * 重新加载表格
 */
const reflash = () => {
	emit('reflash')
}

/**
 * 提交表格
 */
const submit = (params: any, callback: (err: boolean) => void) => {
	emit('submit', params, callback)
}
</script>
<template>
	<div class="tableHeader">
		<span>
			<a-tooltip title="刷新">
				<a-button shape="circle" :icon="h(ReloadOutlined)" @click="reflash" />
			</a-tooltip>
		</span>
		<span>
			<Drawer :_options="options" :config="config" @submit="submit" :model="model" />
		</span>
	</div>
</template>
<style lang="scss" scoped>
.tableHeader {
	height: 70px;
	display: flex;
	justify-content: end;
	align-items: center;
	& > span {
		margin-right: 12px;
	}
}
</style>

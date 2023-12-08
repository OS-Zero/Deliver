<script lang="ts" setup>
import { h } from 'vue'
import { ReloadOutlined } from '@ant-design/icons-vue'
import Form from '@/types/form'
interface EmitEvent {
	(e: 'reflash'): void
}
interface Props {
	config: Form.Modal
	model: Record<Form.FieldItem['field'], Form.FieldItem['value']>
}
const emit = defineEmits<EmitEvent>()
defineProps<Props>()

/**
 * 重新加载表格
 */
const reflash = () => {
	emit('reflash')
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
			<CenterModal :config="config" :model="model" v-if="config.type === 'center'" />
			<RightModal :config="config" :model="model" v-else />
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

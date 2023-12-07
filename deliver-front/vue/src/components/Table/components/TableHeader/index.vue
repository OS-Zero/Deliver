<script lang="ts" setup>
import { h, ref } from 'vue'
import { ReloadOutlined } from '@ant-design/icons-vue'
import Table from '@/types/table'
import Form from '@/types/form'
interface EmitEvent {
	(e: 'reflash'): void
}
interface Props {
	config: Table.TableHeader
	model: Record<Form.FieldItem['field'], Form.FieldItem['value']>
}
const emit = defineEmits<EmitEvent>()
defineProps<Props>()

const open = ref<boolean>(false)

/**
 * 打开对话框
 */
const openModal = () => {
	open.value = true
}
/**
 * 重新加载表格
 */
const reflash = () => {
	emit('reflash')
}
</script>
<template>
	<div class="tableHeader">
		<a-tooltip title="刷新">
			<a-button shape="circle" :icon="h(ReloadOutlined)" @click="reflash" />
		</a-tooltip>
		<a-button @click="openModal" type="primary">{{ config.name }}</a-button>
		<CenterModal v-model:open="open" :fieldList="config.formData" :model="model" v-if="config.type === 'center'" />
		<RightModal v-model:open="open" :fieldList="config.formData" :model="model" v-else />
	</div>
</template>
<style lang="scss" scoped></style>

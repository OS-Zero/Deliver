<script lang="ts" setup>
import { SearchOutlined } from '@ant-design/icons-vue';
import { ChangeEvent } from 'ant-design-vue/es/_util/EventInterface';
defineProps<{
	placeholder?: string
	modelValue: string
}>()
const emit = defineEmits(['search', 'update:modelValue'])
const handleChange = (e: ChangeEvent) => {
	emit('update:modelValue', e.target.value)
	emit('search')
}
</script>

<template>
	<a-input class="input--search" :value="modelValue" @change="handleChange" :placeholder="placeholder">
		<template #prefix>
			<SearchOutlined />
		</template>
	</a-input>
</template>

<style lang="scss" scoped>
.input--search {
	width: 100%;
	border-radius: var(--border-radius-large);
}
</style>

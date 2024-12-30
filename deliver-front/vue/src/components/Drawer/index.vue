<script lang="ts" setup>
withDefaults(defineProps<{
	open: boolean
	title: string
	placement?: 'left' | 'right' | 'top' | 'bottom' | undefined
}>(), {
	open: false,
	placement: 'right'
})

const emit = defineEmits(['close', 'ok'])
</script>

<template>
	<a-drawer :width="500" :title="title" :placement="placement" :open="open" @close="emit('close')">
		<template #extra>
			<a-button style="margin-right: 8px" @click="emit('close')">取消</a-button>
			<a-button type="primary" @click="emit('ok')">确定</a-button>
		</template>
		<slot></slot>
	</a-drawer>
</template>

<style lang="scss" scoped></style>

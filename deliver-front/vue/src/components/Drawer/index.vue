<script lang="ts" setup>
import { DrawerProps } from '@/types/components';

withDefaults(defineProps<DrawerProps>(), {
	open: false,
	placement: 'right',
	extra: true,
	okText: '确认',
	cancelText: '取消',
})

const emit = defineEmits(['close', 'ok'])
</script>

<template>
	<a-drawer :width="500" :title="title" :placement="placement" :open="open" @close="emit('close')">
		<template v-if="extra" #extra>
			<a-button style="margin-right: 8px" @click="emit('close')">{{ cancelText }}</a-button>
			<a-button type="primary" @click="emit('ok')">{{ okText }}</a-button>
		</template>
		<slot></slot>
	</a-drawer>
</template>

<style lang="scss" scoped></style>

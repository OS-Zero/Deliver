<script lang="ts" setup>
import { DescriptionsProps } from 'ant-design-vue';

defineProps<{
	data: Array<{ label: string; value: any }>
	config?: DescriptionsProps
}>()
</script>

<template>
	<a-descriptions v-bind="config">
		<a-descriptions-item v-for="item in data" :label="item.label" :key="item.label">
			<slot name="content" :item="item">
				{{ item.value }}
			</slot>
		</a-descriptions-item>
	</a-descriptions>
</template>

<style lang="scss" scoped></style>

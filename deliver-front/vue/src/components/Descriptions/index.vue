<script lang="ts" setup>
import { CardProps } from 'ant-design-vue';
interface Group {
	config: CardProps
	data: Array<{ label: string, value: any, extra?: Record<string, any> }>
}
defineProps<{
	groups: Group[]
}>()
</script>

<template>
	<a-card size="small" v-for="item in groups" v-bind="item.config">
		<div class="item" v-for="_item in item.data">
			<div class="item--label">
				<slot name="label" :item="_item">
					{{ _item.label }}
				</slot>
			</div>
			<div class="item--value">
				<slot name="value" :item="_item">
					{{ _item.value }}
				</slot>
			</div>
		</div>
	</a-card>
</template>

<style lang="scss" scoped>
.ant-card {
	margin-bottom: var(--spacing-md);
}

.item {
	margin-bottom: var(--spacing-md);
}

.item--label {
	color: var(--secondary-color);
	margin-bottom: var(--spacing-xs);
	width: 100%;
}

.item--value {
	display: flex;
	align-items: center;
	font-size: var(--font-size-base);
	width: 100%;
	word-wrap: break-word;
	word-break: break-all;

}
</style>

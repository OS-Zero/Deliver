<script lang="ts" setup>
defineProps<{
	success: boolean | number
}>()
</script>

<template>
	<span class="status" :class="Boolean(success) ? 'open' : 'close'"></span>
</template>

<style lang="scss" scoped>
.status {
	display: inline-block;
	height: 8px;
	width: 8px;
	border-radius: 7px;
	vertical-align: middle;
	margin-left: 5px;
	margin-top: 5px;
	margin-right: 5px;

	&.open {
		background-color: rgb(10, 191, 91);
	}

	&.close {
		background-color: rgb(229, 69, 69);
	}
}
</style>

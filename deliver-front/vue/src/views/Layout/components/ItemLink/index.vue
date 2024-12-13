<script lang="ts" setup>
import { RouterLink } from 'vue-router';
defineProps(['label', 'to'])
</script>
<template>
	<div class="item-link">
		<RouterLink :to="to">
			{{ label }}
		</RouterLink>
	</div>
</template>
<style lang="scss" scoped>
a {
	text-decoration: none;
	color: var(--white-color);
}

.router-link-active {
	text-decoration: none;
	color: var(--white-color);
}
</style>

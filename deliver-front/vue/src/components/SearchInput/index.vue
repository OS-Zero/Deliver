<script lang="ts" setup>
import { h, ref } from "vue"
import { SearchOutlined } from '@ant-design/icons-vue';
defineProps<{
	placeholder?: string
}>()
const emit = defineEmits(['search'])
const searchData = ref('')
</script>

<template>
	<div class="search-box">
		<a-button class="search-btn" shape="circle" :icon="h(SearchOutlined)" @click="emit('search', searchData)" />
		<input v-model="searchData" type="text" class="search-txt" :placeholder="placeholder" />
	</div>
</template>

<style lang="scss" scoped>
.search-box {
	position: relative;
	background-color: white;
	height: 40px;
	border-radius: 40px;
	display: flex;
	margin-right: var(--spacing-md);

	&::after {
		position: absolute;
		content: '';
		width: calc(100% - 65px);
		height: 2px;
		bottom: 0;
		right: 20px;
		background-color: var(--gray-lighter);
	}
}

.search-txt {
	border: none;
	background: none;
	outline: none;
	padding: 0;
	color: #222;
	font-size: var(--font-size-small);
	width: 0;
	transition: 0.4s;
	padding: 0 12px;

}

.search-btn {
	border: none;
	width: 40px;
	height: 40px;
	border-radius: 50%;
	display: flex;
	justify-content: center;
	align-items: center;
	cursor: pointer;
	transition: 0.4s;
}

.search-box:hover .search-txt {
	width: 200px;
}


.search-txt:focus {
	width: 200px;
}


@keyframes rotate {
	0% {
		transform: rotate(0deg);
	}

	100% {
		transform: rotate(360deg);
	}
}
</style>

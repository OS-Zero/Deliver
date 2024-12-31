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
	<a-input class="input--search" v-model:value="searchData" :placeholder="placeholder" @click="emit('search')">
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

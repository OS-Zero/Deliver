<script lang="ts" setup>
import { reactive, watch } from 'vue';
import { useRouter } from 'vue-router';
import emitter from '@/utils/mitt'
const router = useRouter()
const state = reactive({
	mainPage: true
})
const handleMore = () => {
	router.push('groupManage')
	state.mainPage = false
	localStorage.setItem("group_id", 'test')
	emitter.emit("card", 'test')
}
watch(() => router.currentRoute.value.path, (to: string) => {
	if (!!!localStorage.getItem('group_id') && to === '/groupManage') return state.mainPage = true
	state.mainPage = false
}, { immediate: true })
</script>

<template>
	<template v-if="state.mainPage">
		<a-card title="Default size card" style="width: 300px">
			<template #extra><a href="#" @click="handleMore">more</a></template>
			<p>card content</p>
			<p>card content</p>
			<p>card content</p>
		</a-card>
	</template>
	<template v-else>
		<RouterView />
	</template>
</template>

<style lang="scss" scoped></style>

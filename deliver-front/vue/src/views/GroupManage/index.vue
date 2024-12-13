<script lang="ts" setup>
import { reactive } from 'vue';
import { useRouter } from 'vue-router';
import emitter from '@/utils/mitt'
const router = useRouter()
const state = reactive({
	mainPage: true
})
const handleMore = () => {
	router.push('/groupManage/template')
	state.mainPage = false
	emitter.emit("card", 'test')
}
if (localStorage.getItem('group_id')) {
	state.mainPage = false
	router.push('groupManage/template')
}
</script>

<template>
	<template v-if="state.mainPage">
		<a-card title="Default size card" style="width: 300px">
			<template #extra><a href="#" @click="handleMore">more</a></template>
			<p>card content</p>
			<p>card content</p>
			<p>card content</p>
		</a-card>
		<RouterView></RouterView>
	</template>
	<template v-else>
		<RouterView />
	</template>
</template>

<style lang="scss" scoped></style>

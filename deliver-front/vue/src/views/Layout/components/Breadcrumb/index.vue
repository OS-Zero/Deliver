<script lang="ts" setup>
import { ref, watch } from 'vue'
import { useRoute, useRouter } from 'vue-router'
interface Route {
	path: string
	breadcrumbName: string
	children?: Array<{
		path: string
		breadcrumbName: string
	}>
}
const routes = ref<Route[]>([])
const route = useRoute()
const router = useRouter()
watch(
	route,
	() => {
		routes.value = []
		route.matched.forEach((item) => {
			routes.value.push({ path: item.path, breadcrumbName: item.name as string })
		})
	},
	{
		immediate: true,
	},
)
const handleClick = (path: string) => {
	if (path === '/groupManage') localStorage.removeItem('group_id')
	router.push(path)
}

</script>
<template>
	<a-breadcrumb :routes="routes" separator="/">
		<template #itemRender="{ route }">
			<span v-if="routes.indexOf(route) === routes.length - 1">
				{{ route.breadcrumbName }}
			</span>
			<a @click="handleClick(route.path)" v-else>
				{{ route.breadcrumbName }}
			</a>
		</template>
	</a-breadcrumb>
</template>
<style lang="sass" scoped></style>

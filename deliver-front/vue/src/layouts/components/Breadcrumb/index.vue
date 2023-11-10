<script lang="ts" setup>
import { ref, watch } from 'vue'
import { useRoute } from 'vue-router'
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
watch(
  route,
  () => {
    routes.value = []
    route.matched.forEach(item => {
      routes.value.push({ path: item.path, breadcrumbName: item.name as string })
    })
  },
  {
    immediate: true
  }
)
</script>
<template>
  <a-breadcrumb :routes="routes" separator=">">
    <template #itemRender="{ route, paths }">
      <span v-if="routes.indexOf(route) === routes.length - 1">
        {{ route.breadcrumbName }}
      </span>
      <router-link v-else :to="`${paths.join('/')}`">
        {{ route.breadcrumbName }}
      </router-link>
    </template>
  </a-breadcrumb>
</template>
<style lang="sass" scoped></style>

import { createRouter, type RouteRecordRaw, createWebHistory } from 'vue-router'
const routes: RouteRecordRaw[] = [
  {
    path: '/test',
    name: 'test',
    component: async () => await import('@/views/Test.vue'),
    meta: {
      title: 'test'
    }
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})
export default router

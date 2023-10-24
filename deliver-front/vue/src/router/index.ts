import { createRouter, type RouteRecordRaw, createWebHistory } from 'vue-router'
const routes: RouteRecordRaw[] = [
  {
    path: '/',
    name: 'home',
    component: async () => await import('@/layouts/index.vue'),
    children: [
      {
        path: 'dashboard',
        name: 'dashboard',
        component: async () => await import('@/views/Dashboard/index.vue')
      },
      {
        path: 'message',
        name: 'message',
        component: async () => await import('@/views/Message/index.vue')
      },
      {
        path: 'channel',
        name: 'channel',
        component: async () => await import('@/views/Channel/index.vue')
      },
      {
        path: 'log',
        name: 'log',
        component: async () => await import('@/views/Log/index.vue')
      },
      {
        path: 'setting',
        name: 'setting',
        component: async () => await import('@/views/Setting/index.vue')
      }
    ]
  },
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

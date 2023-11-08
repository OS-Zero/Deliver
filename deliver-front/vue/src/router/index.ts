import { createRouter, type RouteRecordRaw, createWebHistory } from 'vue-router'
const routes: RouteRecordRaw[] = [
  {
    path: '/',
    name: 'home',
    component: async () => await import('@/layouts/index.vue'),
    redirect: 'welcome',
    children: [
      {
        path: 'welcome',
        name: 'welcome',
        component: async () => await import('@/views/Welcome/index.vue'),
        meta: {
          parent: 'welcome',
          title: '欢迎使用 Deliver'
        }
      },
      {
        path: 'dashboard',
        name: 'dashboard',
        component: async () => await import('@/views/Dashboard/index.vue'),
        meta: {
          parent: 'home',
          title: '控制面板'
        }
      },
      {
        path: 'message',
        name: 'message',
        component: async () => await import('@/views/Message/index.vue'),
        meta: {
          parent: 'home',
          title: '消息配置'
        }
      },
      {
        path: 'channel',
        name: 'channel',
        component: async () => await import('@/views/Channel/index.vue'),
        meta: {
          parent: 'home',
          title: '渠道 APP 配置'
        }
      },
      {
        path: 'log',
        name: 'log',
        component: async () => await import('@/views/Log/index.vue'),
        meta: {
          parent: 'home',
          name: '平台文件管理'
        }
      },
      {
        path: 'setting',
        name: 'setting',
        component: async () => await import('@/views/Setting/index.vue'),
        meta: {
          parent: 'home',
          name: '系统设置'
        }
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

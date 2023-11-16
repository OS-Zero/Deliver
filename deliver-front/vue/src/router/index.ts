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
          title: '欢迎 - Deliver 企业消息推送平台'
        }
      },
      {
        path: 'dashboard',
        name: 'dashboard',
        component: async () => await import('@/views/Dashboard/index.vue'),
        meta: {
          parent: 'home',
          title: '控制面板 - Deliver 企业消息推送平台'
        }
      },
      {
        path: 'message',
        name: 'message',
        component: async () => await import('@/views/Message/index.vue'),
        meta: {
          parent: 'home',
          title: '消息配置 - Deliver 企业消息推送平台'
        }
      },
      {
        path: 'channel',
        name: 'channel',
        component: async () => await import('@/views/Channel/index.vue'),
        meta: {
          parent: 'home',
          title: 'APP 配置 - Deliver 企业消息推送平台'
        }
      },
      {
        path: 'file',
        name: 'file',
        component: async () => await import('@/views/File/index.vue'),
        meta: {
          parent: 'home',
          name: '平台文件管理 - Deliver 企业消息推送平台'
        }
      },
      {
        path: 'flowControlRule',
        name: 'flowControlRule',
        component: async () => await import('@/views/flowControlRule/index.vue'),
        meta: {
          parent: 'home',
          name: '规则配置 - Deliver 企业消息推送平台'
        }
      }
    ]
  },
  {
    path: '/:pathMatch(.*)*',
    name: '404',
    component: async () => await import('@/views/404.vue'),
    meta: {
      title: 'Deliver'
    }
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})
export default router

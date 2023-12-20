import { createRouter, type RouteRecordRaw, createWebHistory } from 'vue-router'
const routes: RouteRecordRaw[] = [
	{
		path: '/',
		name: '首页',
		component: async () => await import('@/layouts/index.vue'),
		redirect: 'welcome',
		children: [
			{
				path: 'welcome',
				name: '欢迎',
				component: async () => await import('@/views/Welcome/index.vue'),
				meta: {
					parent: 'welcome',
					title: '欢迎 - Deliver 企业消息推送平台'
				}
			},
			{
				path: 'dashboard',
				name: '控制面板',
				component: async () => await import('@/views/Dashboard/index.vue'),
				meta: {
					parent: '首页',
					title: '控制面板 - Deliver 企业消息推送平台'
				}
			},
			{
				path: 'message',
				name: '消息配置',
				component: async () => await import('@/views/Message/index.vue'),
				meta: {
					parent: '首页',
					title: '消息配置 - Deliver 企业消息推送平台'
				}
			},
			{
				path: 'channel',
				name: 'APP 配置',
				component: async () => await import('@/views/Channel/index.vue'),
				meta: {
					parent: '首页',
					title: 'APP 配置 - Deliver 企业消息推送平台'
				}
			},
			{
				path: 'file',
				name: '文件管理',
				component: async () => await import('@/views/File/index.vue'),
				meta: {
					parent: '首页',
					title: '平台文件管理 - Deliver 企业消息推送平台'
				}
			},
			{
				path: 'flowControlRule',
				name: '规则配置',
				component: async () => await import('@/views/FlowControlRule/index.vue'),
				meta: {
					parent: '首页',
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
			title: 'Deliver 企业消息推送平台'
		}
	}
]

const router = createRouter({
	history: createWebHistory(),
	routes
})
export default router

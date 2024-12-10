import { createRouter, type RouteRecordRaw, createWebHistory } from 'vue-router'
const routes: RouteRecordRaw[] = [
	{
		path: '/login',
		name: '登录',
		component: () => import('@/views/Login/index.vue'),
		meta: {
			title: 'Deliver 企业消息推送平台',
		},
	},
	{
		path: '/',
		name: '首页',
		component: () => import('@/views/Layout/index.vue'),
		redirect: 'welcome',
		children: [
			{
				path: 'welcome',
				name: '欢迎',
				component: () => import('@/views/Welcome/index.vue'),
				meta: {
					parent: 'welcome',
					title: '欢迎 - Deliver 企业消息推送平台',
				},
			},
			// {
			// 	path: 'dashboard',
			// 	name: '控制面板',
			// 	component: async () => await import('@/views/Dashboard/index.vue'),
			// 	meta: {
			// 		parent: '首页',
			// 		title: '控制面板 - Deliver 企业消息推送平台',
			// 	},
			// },
			{
				path: 'template',
				name: '模板配置',
				component: () => import('@/views/Template/index.vue'),
				meta: {
					parent: '首页',
					title: '模板配置 - Deliver 企业消息推送平台',
				},
			},
			{
				path: 'app',
				name: '应用配置',
				component: () => import('@/views/App/index.vue'),
				meta: {
					parent: '首页',
					title: '应用配置 - Deliver 企业消息推送平台',
				},
			},
			{
				path: 'file',
				name: '文件管理',
				component: () => import('@/views/PlatformFile/index.vue'),
				meta: {
					parent: '首页',
					title: '平台文件管理 - Deliver 企业消息推送平台',
				},
			},
			{
				path: 'flowControlRule',
				name: '规则配置',
				component: () => import('@/views/FlowControlRule/index.vue'),
				meta: {
					parent: '首页',
					name: '规则配置 - Deliver 企业消息推送平台',
				},
			},
			{
				path: 'systemSettings',
				name: '系统设置',
				component: () => import('@/views/SystemSettings/index.vue'),
				meta: {
					parent: '首页',
					name: '系统设置 - Deliver 企业消息推送平台',
				},
			},
		],
	},
	{
		path: '/:pathMatch(.*)*',
		name: '404',
		component: () => import('@/views/404.vue'),
		meta: {
			title: 'Deliver 企业消息推送平台',
		},
	},
]

const router = createRouter({
	history: createWebHistory(),
	routes,
})
router.beforeEach(function (to, _from, next) {
	if (!localStorage.getItem('access_token')) {
		if (to.path !== '/login') {
			return next('/login')
		}
	}
	next()
})
export default router

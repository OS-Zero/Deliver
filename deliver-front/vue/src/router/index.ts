import { createRouter, type RouteRecordRaw, createWebHistory } from 'vue-router';
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
		redirect: '/welcome',
		children: [
			{
				path: '/groupManage',
				name: '分组管理',
				component: () => import('@/views/GroupManage/index.vue'),
				meta: {
					title: '分组管理 - Deliver 企业消息推送平台',
				},
				children: [
					{
						path: 'template',
						name: '消息模板配置',
						component: () => import('@/views/GroupManage/pages/MessageTemplate/index.vue'),
						meta: {
							title: '模板配置 - Deliver 企业消息推送平台',
						},
					},
					{
						path: 'app',
						name: '渠道应用配置',
						component: () => import('@/views/GroupManage/pages/ChannelApp/index.vue'),
						meta: {
							title: '渠道应用配置 - Deliver 企业消息推送平台',
						},
					},
					{
						path: 'file',
						name: '文件管理',
						component: () => import('@/views/GroupManage/pages/PlatformFile/index.vue'),
						meta: {
							title: '平台文件管理 - Deliver 企业消息推送平台',
						},
					},
					{
						path: 'task',
						name: '群发任务配置',
						component: () => import('@/views/GroupManage/pages/TaskManage/index.vue'),
						meta: {
							title: '群发任务配置 - Deliver 企业消息推送平台',
						},
					},
					{
						path: 'peopleGroup',
						name: '人群模板配置',
						component: () => import('@/views/GroupManage/pages/TaskManage/PeopleGroup/index.vue'),
						meta: {
							title: '人群模板配置 - Deliver 企业消息推送平台',
						},
					},
				],
			},
			{
				path: '/systemManage',
				name: '系统管理',
				component: () => import('@/views/SystemManage/index.vue'),
				meta: {
					title: '系统管理 - Deliver 企业消息推送平台',
				},
				redirect: '/systemManage/myAccount',
				children: [
					{
						path: 'myAccount',
						name: '我的账户',
						component: () => import('@/views/SystemManage/pages/MyAccount/index.vue'),
						meta: {
							name: '我的账户 - Deliver 企业消息推送平台',
						},
					},
				],
			},
			{
				path: '/welcome',
				name: '欢迎',
				component: () => import('@/views/Welcome/index.vue'),
				meta: {
					title: '欢迎 - Deliver 企业消息推送平台',
				},
			},
		],
	},
	{
		path: '/:pathMatch(.*)*',
		name: '404',
		component: () => import('@/views/404.vue'),
		meta: {
			title: 'Not Found - Deliver 企业消息推送平台',
		},
	},
];

const router = createRouter({
	history: createWebHistory(),
	routes,
});
router.beforeEach(function (to, from, next) {
	if (!localStorage.getItem('access_token') && to.path !== '/login') return next('/login');
	//如果想访问groupManage子目录必须要有group_id
	const arr_to = to.path.split('/').slice(1);
	const arr_from = from.path.split('/').slice(1);
	const hasGroupId = !!localStorage.getItem('group_id');
	if (arr_to[0] === 'groupManage' && arr_to.length > 1 && !hasGroupId) {
		return next('/groupManage');
	}
	if (arr_to[0] === 'groupManage' && arr_to.length === 1 && hasGroupId) {
		if (from.path.includes('groupManage') && arr_from.length > 1) return next(from.path);
		else return next('/groupManage/template');
	}
	next();
});
export default router;

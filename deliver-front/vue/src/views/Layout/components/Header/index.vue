<script lang="ts" setup>
import { watch, reactive, ref } from 'vue'
import { AppstoreOutlined, ExclamationCircleOutlined, QuestionCircleOutlined, GithubOutlined, SettingOutlined } from '@ant-design/icons-vue'
import { useRouter, useRoute } from 'vue-router';
import { logout } from "@/api/user";
const state = reactive({
	showAbout: false,
	topTab: 'groupManage'
})
const setShowAbout = (open: boolean) => {
	state.showAbout = open
}
const router = useRouter()
const route = useRoute()
const handleAction = async (opt: string) => {
	switch (opt) {
		case 'account':
			router.push('/systemManage')
			break;
		case 'logout':
			await logout()
			localStorage.removeItem("access_token")
			localStorage.removeItem("user_info")
			router.push('/login')
			break;
		default:
			break;
	}
}
const emits = defineEmits<{
	onTabChange: [tab: string]
}>()
const handleClickTabs = (path: any) => {
	console.log(path);

	emits("onTabChange", path)
	router.push(`/${path}`)
}
const activeKey = ref('')
watch(route, (newRoute) => {
	activeKey.value = ''
	newRoute.path.includes('groupManage') && (activeKey.value = 'groupManage')
	newRoute.path.includes('systemManage') && (activeKey.value = 'systemManage')
}, {
	immediate: true
})
</script>
<template>
	<a-layout-header class="header">
		<div class="header-container">
			<div class="organization">
				<a href="/">
					<img class="organization-img" src="/logo.png" style="" alt="空" />
				</a>
				<a href="/">
					<h1>Deliver 企业消息推送平台</h1>
				</a>
			</div>
			<a-tabs v-model:activeKey="activeKey" @change="handleClickTabs" size="small">
				<a-tab-pane key="groupManage">
					<template #tab>
						<span>
							<AppstoreOutlined />
							分组管理
						</span>
					</template>
				</a-tab-pane>
				<a-tab-pane key="systemManage">
					<template #tab>
						<span>
							<SettingOutlined />
							系统管理
						</span>
					</template>
				</a-tab-pane>
			</a-tabs>
		</div>
		<div div class=" extra">
			<div>
				<a-tooltip title="关于">
					<a @click="state.showAbout = true">
						<ExclamationCircleOutlined />
					</a>
				</a-tooltip>
				<a-modal v-model:open="state.showAbout" title="关于" centered @ok="setShowAbout(false)"
					:ok-button-props="{ disabled: true }" :cancel-button-props="{ disabled: true }" :footer="null">
					<div class="modal-container">
						<div class="container-info">
							<img class="info_img" src="/logo.png" alt="null" />
							<h1 class="info_title">Deliver</h1>
						</div>
						<div style="margin-left: 60px">
							<p>产品：Deliver 企业消息推送平台</p>
							<p>版本：v1.0.0</p>
							<a target="_blank"
								href="https://os-zero.gitee.io/deliver-website">https://os-zero.gitee.io/deliver-website</a>
						</div>
					</div>
				</a-modal>
				<a-tooltip title="疑问">
					<a target="_blank" href="https://os-zero.gitee.io/deliver-website">
						<QuestionCircleOutlined />
					</a>
				</a-tooltip>
				<a-tooltip title="Gitee">
					<a href="https://gitee.com/OS-Zero/deliver" target="_blank">
						<GithubOutlined />
					</a>
				</a-tooltip>
			</div>
			<a-dropdown placement="bottom">
				<div class="avatar">
					<a-avatar src="../../../../../public/mayi.png"></a-avatar>
					<span class="name">Deliver</span>
				</div>
				<template #overlay>
					<a-menu class="dropdown">
						<a-menu-item>
							<a @click="handleAction('account')">我的账户</a>
						</a-menu-item>
						<a-menu-item>
							<a @click="handleAction('logout')">退出登录</a>
						</a-menu-item>
					</a-menu>
				</template>
			</a-dropdown>
		</div>
	</a-layout-header>
</template>
<style lang="scss" scoped>
.header {
	display: flex;
	justify-content: space-between;
	align-items: center;
	background-color: var(--white-color);
	height: 60px;
	padding-inline: var(--spacing-md);
	border-bottom: 1px solid var(--gray-lighter);

	.organization {
		display: flex;

		h1 {
			color: var(--dark-color);
			font-size: var(--font-size-large);
		}

		a {
			display: flex;
			align-items: center;
			text-decoration: none;
			height: 60px;
		}
	}

	.organization-img {
		height: 28px;
		width: 33px;
	}

	.extra {
		display: flex;
		justify-content: space-between;
		align-items: center;
		font-size: var(--font-size-base);
		color: var(--dark-color);

		a {
			color: var(--gray-medium-dark);
			font-size: var(--font-size-base);
			margin-right: var(--spacing-sm);
			padding: 0 var(--spacing-xs);

			&:hover {
				background-color: var(--gray-lightest);
				border-radius: 4px;
			}
		}

		.avatar {
			height: 50px;
			display: flex;
			align-items: center;
			padding: 0 var(--spacing-sm);

			.ant-avatar {
				width: 25px;
				height: 25px;
				font-size: var(--font-size-base);
			}

			.name {
				margin-left: var(--spacing-sm);
				font-size: var(--font-size-small);
			}

			&:hover {
				cursor: pointer;
				background-color: var(--gray-lightest);
			}
		}
	}
}

.header-container {
	display: flex;
	align-items: center;
}

.ant-tabs {
	margin-left: var(--spacing-md);
}

:deep(:where(.css-dev-only-do-not-override-1p3hq3p).ant-tabs .ant-tabs-tab+.ant-tabs-tab) {
	margin: 0 0 0 var(--spacing-md)
}

:deep(.ant-tabs-nav) {
	margin: 0;
}

.modal-container {
	display: flex;
	margin-top: var(--spacing-md);

	.container-info {
		display: flex;
	}

	.info_img {
		width: 66px;
		height: 56px;
	}

	.info_title {
		display: inline-block;
		height: 60px;
		line-height: 60px;
	}
}

.dropdown {
	a {
		text-decoration: none
	}
}
</style>

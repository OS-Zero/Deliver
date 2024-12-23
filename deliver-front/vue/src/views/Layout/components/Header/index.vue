<script lang="ts" setup>
import { reactive, ref, onMounted } from 'vue'
import { ExclamationCircleOutlined, QuestionCircleOutlined, GithubOutlined } from '@ant-design/icons-vue'
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
const tabItemDoms = ref()
const handleAction = async (opt: string) => {
	switch (opt) {
		case 'account':
			router.push('/systemSettings')
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
const tabItems = [
	{
		name: '分组管理',
		path: 'groupManage'
	},
	{
		name: '系统管理',
		path: 'systemManage'
	}
]
const handleClickTabs = (path: string) => {
	emits("onTabChange", path)
	router.push(`/${path}`)
}
onMounted(() => {

	// console.dir(window.getComputedStyle(tabItemDoms.value[0]).width);

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
			<div class="header-tabs">
				<!-- <a-tabs v-model:activeKey="state.topTab">
					<a-tab-pane key="groupManage" tab="分组管理" @click="handleClickTabs('groupManage')"></a-tab-pane>
					<a-tab-pane key="systemManage" tab="系统管理" @click="handleClickTabs('systemManage')"> </a-tab-pane>
				</a-tabs> -->
				<a ref="tabItemDoms" v-for="item in tabItems" class="tab-item"
					:class="{ active: route.path.includes(item.path) }" @click="handleClickTabs(item.path)">
					{{ item.name }}
				</a>
				<div class="slider"></div>
			</div>
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
					<a-avatar src="mayi.png"></a-avatar>
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
			height: 60px;
			line-height: 60px;
		}

		a {
			text-decoration: none;
		}
	}

	.organization-img {
		height: 28px;
		width: 33px;
		margin-top: var(--spacing-md);
		margin-right: var(--spacing-sm)
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

.header-tabs {
	position: relative;
	display: flex;
	height: 30px;
	line-height: 30px;
	margin-left: var(--spacing-md);
	border-bottom: 1px solid var(--gray-lighter);
}

.tab-item {
	color: var(--dark-color);
	text-decoration: none;

	&:not(:last-child) {
		margin-right: var(--spacing-md);
	}

	&.active {
		color: var(--blue-darker);
		border-bottom: 2px solid var(--blue-darker);
	}
}

.slider {
	position: absolute;
	background-color: red;
	height: 2px;
	bottom: 0;
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

<script lang="ts" setup>
import { ref } from 'vue'
import {
	ExclamationCircleOutlined,
	QuestionCircleOutlined,
	GithubOutlined,
	UserOutlined
} from '@ant-design/icons-vue'
const showAbout = ref(false)
const setShowAbout = (open: boolean): void => {
	showAbout.value = open
}
</script>
<template>
	<a-layout-header class="header">
		<div class="left">
			<RouterLink to="/">
				<img src="logo.png" style="height: 42px; width: 42px; margin-top: 8px" alt="空" />
			</RouterLink>
			<RouterLink to="/"><h1>Deliver 企业消息推送平台</h1></RouterLink>
		</div>
		<div class="right">
			<a style="color: #8b8b8b" @click="showAbout = true"><ExclamationCircleOutlined /></a>
			<a-modal
				v-model:open="showAbout"
				title="关于"
				centered
				@ok="setShowAbout(false)"
				:ok-button-props="{ disabled: true }"
				:cancel-button-props="{ disabled: true }"
				:footer="null">
				<div style="display: flex; margin-top: 20px">
					<div style="display: flex">
						<img src="logo.png" style="width: 60px; height: 60px" alt="" />
						<h1 style="display: inline-block; height: 60px; line-height: 60px">Deliver</h1>
					</div>
					<div style="margin-left: 60px">
						<p>产品：Deliver 企业消息推送平台</p>
						<p>版本：v1.0.0</p>
						<a target="_blank" href="https://os-zero.gitee.io/deliver-website">
							https://os-zero.gitee.io/deliver-website
						</a>
					</div>
				</div>
			</a-modal>
			<a target="_blank" href="https://os-zero.gitee.io/deliver-website" style="color: #8b8b8b">
				<QuestionCircleOutlined />
			</a>
			<a href="https://gitee.com/OS-Zero/deliver" target="_blank" style="color: #8b8b8b">
				<GithubOutlined />
			</a>
			<span class="avatar">
				<a-avatar style="width: 30px; height: 30px">
					<template #icon><UserOutlined /></template>
				</a-avatar>
				<span class="name">admin</span>
			</span>
		</div>
	</a-layout-header>
</template>
<style lang="scss" scoped>
.header {
	display: flex;
	justify-content: space-between;
	background-color: #fff;
	height: 60px;
	.left {
		display: flex;
		h1 {
			color: #000;
			font-size: 20px;
			height: 60px;
			line-height: 60px;
		}
	}

	.right {
		font-size: 20px;
		color: #8b8b8b;

		a {
			margin-right: 10px;
		}

		.avatar {
			display: inline-block;
			height: 100%;
			padding-right: 20px;
			//border-left: 1px solid #c0c0c0;
			padding-left: 20px;

			.name {
				margin-left: 10px;
				font-size: 16px;
			}
		}

		.avatar:hover {
			cursor: pointer;
			background-color: #f7f7f7;
		}
	}
}
</style>

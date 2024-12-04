<script lang="ts" setup>
import { ref } from 'vue'
import { ExclamationCircleOutlined, QuestionCircleOutlined, GithubOutlined } from '@ant-design/icons-vue'
const showAbout = ref(false)
const setShowAbout = (open: boolean): void => {
	showAbout.value = open
}
</script>
<template>
	<a-layout-header class="header">
		<div class="left">
			<a href="/">
				<img src="logo.png" style="height: 28px; width: 33px; margin-top: 16px; margin-right: 8px" alt="空" />
			</a>
			<a href="/"><h1>Deliver 企业消息推送平台</h1></a>
		</div>
		<div class="right">
			<a-tooltip title="关于">
				<a style="color: #8b8b8b; font-size: 16px" @click="showAbout = true">
					<ExclamationCircleOutlined />
				</a>
			</a-tooltip>
			<a-modal
				v-model:open="showAbout"
				title="关于"
				centered
				@ok="setShowAbout(false)"
				:ok-button-props="{ disabled: true }"
				:cancel-button-props="{ disabled: true }"
				:footer="null"
			>
				<div style="display: flex; margin-top: 20px">
					<div style="display: flex">
						<img src="logo.png" style="width: 66px; height: 56px" alt="" />
						<h1 style="display: inline-block; height: 60px; line-height: 60px">Deliver</h1>
					</div>
					<div style="margin-left: 60px">
						<p>产品：Deliver 企业消息推送平台</p>
						<p>版本：v1.0.0</p>
						<a target="_blank" href="https://os-zero.gitee.io/deliver-website"
							>https://os-zero.gitee.io/deliver-website</a
						>
					</div>
				</div>
			</a-modal>
			<a-tooltip title="疑问">
				<a target="_blank" href="https://os-zero.gitee.io/deliver-website" style="color: #8b8b8b; font-size: 16px">
					<QuestionCircleOutlined />
				</a>
			</a-tooltip>
			<a-tooltip title="Gitee">
				<a href="https://gitee.com/OS-Zero/deliver" target="_blank" style="color: #8b8b8b; font-size: 16px">
					<GithubOutlined />
				</a>
			</a-tooltip>
			<span class="avatar">
				<a-avatar style="width: 30px; height: 30px; font-size: 16px" src="mayi.png"></a-avatar>
				<span class="name">Deliver</span>
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
	margin-left: -28px;
	margin-right: -28px;
	.left {
		display: flex;
		h1 {
			color: #000;
			font-size: 18px;
			height: 60px;
			line-height: 60px;
		}
	}

	.right {
		font-size: 20px;
		color: #8b8b8b;

		a {
			margin-right: 12px;
		}

		.avatar {
			display: inline-block;
			height: 100%;
			padding-right: 20px;
			padding-left: 20px;

			.name {
				margin-left: 10px;
				font-size: 15px;
			}
		}

		.avatar:hover {
			cursor: pointer;
			background-color: #f7f7f7;
		}
	}
}
</style>

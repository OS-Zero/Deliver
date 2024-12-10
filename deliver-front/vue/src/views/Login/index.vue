<script lang="ts" setup>
import { reactive } from "vue"
import LoginForm from "./components/LoginForm.vue";
import RegisterForm from "./components/RegisterForm.vue";
import ForgotForm from "./components/ForgotForm.vue";
import { LeftOutlined } from '@ant-design/icons-vue'
const state = reactive({
	activeKey: 'login',
	autoLogin: false,
	showForgot: false
})

const registerOk = () => {
	state.activeKey = 'login'
}
const forgotOk = () => {
	state.showForgot = false
}
</script>

<template>
	<div class="login">
		<div class="login-card" :class="{ forgot: state.showForgot }">
			<template v-if="!state.showForgot">
				<header>
					<div class="header_logo">
						<img class="logo" src="../../../public/logo.png" alt="">
						<span>Deliver</span>
					</div>
					<div class="header_desc">
						开源的企业消息推送平台
					</div>
				</header>
				<section>
					<a-tabs v-model:activeKey="state.activeKey" centered>
						<a-tab-pane key="login" tab="邮箱密码登录">
							<LoginForm />
						</a-tab-pane>
						<a-tab-pane key="register" tab="用户注册">
							<RegisterForm @onOk="registerOk" />
						</a-tab-pane>
					</a-tabs>
					<div class="login--auto">
						<a-checkbox v-model:checked="state.autoLogin">自动登录</a-checkbox>
						<a-button type="link" @click="state.showForgot = true">忘记密码</a-button>
					</div>
				</section>
			</template>
			<template v-else>
				<div class="forgot">
					<a-button type="text" class="forgot--back" @click="state.showForgot = false">
						<LeftOutlined /> <span>返回</span>
					</a-button>
					<h1 class="forgot_title">
						重置密码
					</h1>
					<ForgotForm @onOk="forgotOk"></ForgotForm>
				</div>
			</template>
		</div>
	</div>
</template>

<style lang="scss" scoped>
.login {
	height: 100%;
	background-image: url(https://demo.topiam.cn/login-background.png);
	position: relative;
}

.login-card {
	background-color: var(--white-color);
	position: absolute;
	top: 50%;
	transform: translateY(-50%);
	right: 10%;
	min-height: 600px;
	min-width: 350px;
	padding: var(--spacing-lg);
	border-radius: 6px;
	box-shadow: 0px 0px 24px 0px rgba(0, 0, 0, 0.1);

	&.forgot {
		min-height: auto;
	}
}

.header_logo {
	display: flex;
	justify-content: center;
	align-items: center;
	font-size: xx-large;
	font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
	font-weight: var(--font-weight-bold);

	.logo {
		width: 60px;
		height: 50px;
		margin-right: var(--spacing-sm)
	}
}

.header_desc {
	text-align: center;
	color: var(--gray-medium-dark);

	margin: {
		top: var(--spacing-lg);
		bottom: var(--spacing-md);
	}
}

.login--auto {
	display: flex;
	justify-content: space-between;
	align-items: center;
}

.forgot_title {
	margin: var(--spacing-md) 0
}

.forgot--back {
	padding-left: 0;
}
</style>

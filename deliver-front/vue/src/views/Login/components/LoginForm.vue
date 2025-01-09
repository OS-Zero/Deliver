<script lang="ts" setup>
import { ref, reactive } from 'vue';
import { UserInfo } from '../../../types/user';
import type { Rule } from 'ant-design-vue/es/form';
import { login, } from "@/api/user";
import { useRouter } from 'vue-router';
import { message } from 'ant-design-vue'
import { getRules } from '@/config/rules';
import { startup } from '@/api/startup';
const router = useRouter()
const loginData = reactive<UserInfo>({
	userEmail: '',
	userPassword: ''
})
const rules: Record<string, Rule[]> = getRules(['userEmail', 'userPassword'])
const formRef = ref()
const handleLogin = function () {
	formRef.value
		.validate()
		.then(async () => {
			const res = await login(loginData)
			localStorage.setItem('access_token', res)
			message.success('登录成功')
			router.push('/')
			const _res = await startup()
			localStorage.setItem('startup', JSON.stringify(_res))
		})
		.catch(error => {
			console.log('error', error);
		});
}
</script>

<template>
	<a-form ref="formRef" :model="loginData" :rules="rules">
		<a-form-item name="userEmail">
			<a-input v-model:value.trim="loginData.userEmail" placeholder="请输入邮箱" />
		</a-form-item>
		<a-form-item name="userPassword">
			<a-input-password v-model:value.trim="loginData.userPassword" placeholder="请输入密码" />
		</a-form-item>
		<a-form-item>
			<a-button class="submit_btn" type="primary" html-type="submit" @click="handleLogin">登录</a-button>
		</a-form-item>
	</a-form>
</template>

<style lang="scss" scoped>
.submit_btn {
	width: 100%
}
</style>

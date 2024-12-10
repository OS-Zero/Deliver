<script lang="ts" setup>
import { ref, reactive } from 'vue';
import { UserInfo } from '../type';
import type { Rule } from 'ant-design-vue/es/form';
import { validateEmail } from '@/utils/validate';
import { login } from "@/api/user";
import { useRouter } from 'vue-router';
import { message } from 'ant-design-vue'
const router = useRouter()
const loginData = reactive<UserInfo>({
	userEmail: '',
	userPassword: ''
})
const rules: Record<string, Rule[]> = {
	userEmail: [{ required: true, message: '请输入邮箱!', trigger: 'blur' }, { validator: validateEmail, trigger: 'blur' }],
	userPassword: [{ required: true, message: '请输入密码!', trigger: 'blur' }, { min: 6, message: '密码长度范围为6-16位', trigger: 'blur' }, { max: 16, message: '密码长度范围为6-16位', trigger: 'blur' }],
};
const formRef = ref()
const handleLogin = function () {
	formRef.value
		.validate()
		.then(async () => {
			const res = await login(loginData)
			localStorage.setItem('access_token', res.token)
			message.success('登录成功')
			router.push('/')
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

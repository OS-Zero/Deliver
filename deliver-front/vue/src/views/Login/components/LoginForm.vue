<script lang="ts" setup>
import { UserInfo } from '../type';
import type { Rule } from 'ant-design-vue/es/form';
import { validateEmail } from '@/utils/validate';
interface LoginFormProps {
	formData: UserInfo
}
interface LoginFormEmits {
	ok: [data: UserInfo]
	failed: [err: any]
}
defineProps<LoginFormProps>()
defineEmits<LoginFormEmits>()
const rules: Record<string, Rule[]> = {
	userEmail: [{ required: true, validator: validateEmail, trigger: 'blur' }],
	userPassword: [{ required: true, message: '请输入密码!' }, { min: 6, message: '密码长度范围为6-16位' }, { max: 16, message: '密码长度范围为6-16位' }],
};
</script>

<template>
	<a-form :model="formData" :rules="rules" @finish="onOk" @finishFailed="onFailed">
		<a-form-item name="userEmail">
			<a-input v-model:value.trim="formData.userEmail" placeholder="请输入邮箱" />
		</a-form-item>
		<a-form-item name="userPassword">
			<a-input-password v-model:value.trim="formData.userPassword" placeholder="请输入密码" />
		</a-form-item>
		<a-form-item>
			<a-button class="submit_btn" type="primary" html-type="submit">登录</a-button>
		</a-form-item>
	</a-form>
</template>

<style lang="scss" scoped>
.submit_btn {
	width: 100%
}
</style>

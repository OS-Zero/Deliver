<script lang="ts" setup>
import { ForgotInfo } from '../type';
import type { Rule } from 'ant-design-vue/es/form';
import { validateEmail } from '@/utils/validate';
interface ForgotProps {
	formData: ForgotInfo
}
interface ForgotEmits {
	ok: [data: ForgotInfo]
	failed: [err: any]
}
defineProps<ForgotProps>()
defineEmits<ForgotEmits>()
const rules: Record<string, Rule[]> = {
	userEmail: [{ required: true, message: '请输入邮箱!', validator: validateEmail }],
	userPassword: [{ required: true, message: '请输入密码!' }, { min: 6, message: '密码长度范围为6-16位' }, { max: 16, message: '密码长度范围为6-16位' }],
	confirmPwd: [{ required: true, message: '请确认密码!' }],
	verificationCode: [{ required: true, message: '请输入验证码!' }, { len: 6, message: '验证码长度为6位' }],
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
		<a-form-item name="confirmPwd">
			<a-input-password v-model:value.trim="formData.confirmPwd" placeholder="请确认密码" />
		</a-form-item>
		<a-form-item name="verificationCode">
			<div class="verify">
				<a-input v-model:value.trim="formData.verificationCode" placeholder="请输入验证码" />
				<a-button class="verify_btn">获取验证码</a-button>
			</div>
		</a-form-item>
		<a-form-item>
			<a-button class="submit_btn" type="primary" html-type="submit">确认</a-button>
		</a-form-item>
	</a-form>
</template>

<style lang="scss" scoped>
.verify {
	display: flex;

	.verify_btn {
		margin-left: var(--spacing-sm)
	}
}

.submit_btn {
	width: 100%
}
</style>

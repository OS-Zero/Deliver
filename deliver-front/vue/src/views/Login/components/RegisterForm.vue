<script lang="ts" setup>
import { ref, reactive } from 'vue';
import { RegisterInfo } from '../type';
import type { Rule } from 'ant-design-vue/es/form';
import { validateEmail } from '@/utils/validate';
import { register } from '@/api/user';
import { message } from 'ant-design-vue'
import { omitProperty } from "@/utils/utils"

const emits = defineEmits<{
	onOk: []
}>()
const registerData = reactive<RegisterInfo>({
	userEmail: '3233891353@qq.com',
	userPassword: 'xxxxxx',
	userRealName: 'xxxxxx',
	confirmPwd: 'xxxxxx',
	verificationCode: 'xxxxxx'
})
const validatePwd = () => {
	if (registerData.confirmPwd === '') return
	return registerData.confirmPwd === registerData.userPassword ? Promise.resolve() : Promise.reject("两次输入密码不相同")
}
const rules: Record<string, Rule[]> = {
	userEmail: [{ required: true, message: '请输入邮箱!', trigger: 'blur' }, { validator: validateEmail, trigger: 'blur' }],
	userPassword: [{ required: true, message: '请输入密码!', trigger: 'blur' }, { min: 6, message: '密码长度范围为6-16位', trigger: 'blur' }, { max: 16, message: '密码长度范围为6-16位', trigger: 'blur' }],
	confirmPwd: [{ required: true, message: '请确认密码!', trigger: 'blur' }, { validator: validatePwd, trigger: 'blur' }],
	userRealName: [{ required: true, message: '请输入真实姓名!', trigger: 'blur' }, { min: 1, message: '姓名长度范围为1-10位', trigger: 'blur' }, { max: 10, message: '姓名长度范围为1-10位', trigger: 'blur' }],
	verificationCode: [{ required: true, message: '请输入验证码!', trigger: 'blur' }, { len: 6, message: '验证码长度为6位', trigger: 'blur' }],
};

const formRef = ref()

const handleRegister = () => {
	formRef.value
		.validate()
		.then(async () => {
			await register(omitProperty(registerData, 'confirmPwd'))
			message.success('注册成功')
			emits("onOk")
		})
		.catch(error => {
			console.log('error', error);
		});
}
</script>

<template>
	<a-form ref="formRef" :model="registerData" :rules="rules">
		<a-form-item name="userEmail">
			<a-input v-model:value.trim="registerData.userEmail" placeholder="请输入邮箱" />
		</a-form-item>
		<a-form-item name="userPassword">
			<a-input-password v-model:value.trim="registerData.userPassword" placeholder="请输入密码" />
		</a-form-item>
		<a-form-item name="confirmPwd">
			<a-input-password v-model:value.trim="registerData.confirmPwd" placeholder="请确认密码" />
		</a-form-item>
		<a-form-item name="userRealName">
			<a-input v-model:value.trim="registerData.userRealName" placeholder="请输入真实姓名" />
		</a-form-item>
		<a-form-item name="verificationCode">
			<div class="verify">
				<a-input v-model:value.trim="registerData.verificationCode" placeholder="请输入验证码" />
				<a-button class="verify_btn">获取验证码</a-button>
			</div>
		</a-form-item>
		<a-form-item>
			<a-button class="submit_btn" type="primary" html-type="submit" @click="handleRegister">注册</a-button>
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

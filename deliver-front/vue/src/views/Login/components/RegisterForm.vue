<script lang="ts" setup>
import { ref, reactive, watch } from 'vue';
import { RegisterInfo } from '../../../types/user';
import type { Rule } from 'ant-design-vue/es/form';
import { validateEmail } from '@/utils/validate';
import { register } from '@/api/user';
import { message } from 'ant-design-vue'
import { omitProperty } from "@/utils/utils"
import { getRules } from '@/config/rules';
import { useVerify } from '@/hooks/verify';

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
	if (registerData.confirmPwd === '') return Promise.reject("请确认密码")
	return registerData.confirmPwd === registerData.userPassword ? Promise.resolve() : Promise.reject("两次输入密码不相同")
}
const rules: Record<string, Rule[]> = {
	confirmPwd: [{ validator: validatePwd, trigger: 'blur' }],
	...getRules(['userEmail', 'userPassword', 'userRealName', 'verificationCode'])
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
const { state, handleVarify, onFinished } = useVerify()
const _validateEmail = async () => {
	try {
		await validateEmail(null, registerData.userEmail)
		!state.loading && (state.verifyDisabled = false)
	} catch (error) {
		state.verifyDisabled = true
	}
}
onFinished(() => {
	_validateEmail()
})
watch(registerData, _validateEmail, {
	immediate: true
})
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
				<a-button class="verify_btn" :disabled="state.verifyDisabled" @click="handleVarify(registerData.userEmail)">{{
					state.verifyContent
				}}</a-button>
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

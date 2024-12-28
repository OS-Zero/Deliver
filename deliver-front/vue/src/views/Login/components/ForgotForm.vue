<script lang="ts" setup>
import { ref, reactive, watch } from 'vue';
import { ForgotInfo } from '../../../types/user';
import type { Rule } from 'ant-design-vue/es/form';
import { validateEmail } from '@/utils/validate';
import { forgotPwd } from '@/api/user';
import { omitProperty } from '@/utils/utils';
import { message } from 'ant-design-vue';
import { useVerify } from '@/hooks/verify';
import { getRules } from '@/config/rules';

const emits = defineEmits<{
	onOk: []
}>()
const forgotData = reactive<ForgotInfo>({
	userEmail: '',
	userPassword: '',
	confirmPwd: '',
	verificationCode: ''
})
const validatePwd = () => {
	if (forgotData.confirmPwd === '') return Promise.reject("请确认密码")
	return forgotData.confirmPwd === forgotData.userPassword ? Promise.resolve() : Promise.reject("两次输入密码不相同")
}
const rules: Record<string, Rule[]> = {
	confirmPwd: [{ validator: validatePwd, trigger: 'blur' }],
	...getRules(['userEmail', 'userPassword', 'verificationCode'])
};

const formRef = ref()

const handleForgot = () => {
	formRef.value
		.validate()
		.then(async () => {
			await forgotPwd(omitProperty(forgotData, "confirmPwd"))
			message.success("修改密码成功")
			emits("onOk")
		})
		.catch(error => {
			console.log('error', error);
		});
}
const { state, handleVarify, onFinished } = useVerify()
const _validateEmail = async () => {
	try {
		await validateEmail(null, forgotData.userEmail)
		!state.loading && (state.verifyDisabled = false)
	} catch (error) {
		state.verifyDisabled = true
	}
}
onFinished(() => {
	_validateEmail()
})
watch(forgotData, _validateEmail, {
	immediate: true
})
</script>

<template>
	<a-form ref="formRef" :model="forgotData" :rules="rules">
		<a-form-item name="userEmail">
			<a-input v-model:value.trim="forgotData.userEmail" placeholder="请输入邮箱" />
		</a-form-item>
		<a-form-item name="userPassword">
			<a-input-password v-model:value.trim="forgotData.userPassword" placeholder="请输入密码" />
		</a-form-item>
		<a-form-item name="confirmPwd">
			<a-input-password v-model:value.trim="forgotData.confirmPwd" placeholder="请确认密码" />
		</a-form-item>
		<a-form-item name="verificationCode">
			<div class="verify">
				<a-input v-model:value.trim="forgotData.verificationCode" placeholder="请输入验证码" />
				<a-button class="verify_btn" :disabled="state.verifyDisabled" @click="handleVarify(forgotData.userEmail)">{{
					state.verifyContent
				}}</a-button>
			</div>
		</a-form-item>
		<a-form-item>
			<a-button class="submit_btn" type="primary" html-type="submit" @click="handleForgot">确认</a-button>
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

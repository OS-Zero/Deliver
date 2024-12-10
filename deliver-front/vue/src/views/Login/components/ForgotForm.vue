<script lang="ts" setup>
import { ref, reactive } from 'vue';
import { ForgotInfo } from '../type';
import type { Rule } from 'ant-design-vue/es/form';
import { validateEmail } from '@/utils/validate';
import { forgotPwd } from '@/api/user';
import { omitProperty } from '@/utils/utils';
import { message } from 'ant-design-vue';

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
	return forgotData.confirmPwd === forgotData.userPassword ? Promise.resolve() : Promise.reject("两次输入密码不相同")
}
const rules: Record<string, Rule[]> = {
	userEmail: [{ required: true, message: '请输入邮箱!', trigger: 'blur' }, { validator: validateEmail, trigger: 'blur' }],
	userPassword: [{ required: true, message: '请输入密码!', trigger: 'blur' }, { min: 6, message: '密码长度范围为6-16位', trigger: 'blur' }, { max: 16, message: '密码长度范围为6-16位', trigger: 'blur' }],
	confirmPwd: [{ required: true, message: '请确认密码!', trigger: 'blur' }, { validator: validatePwd, trigger: 'blur' }],
	verificationCode: [{ required: true, message: '请输入验证码!', trigger: 'blur' }, { len: 6, message: '验证码长度为6位', trigger: 'blur' }],
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
const handleVarify = () => {

}
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
				<a-button class="verify_btn" @click="handleVarify">获取验证码</a-button>
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

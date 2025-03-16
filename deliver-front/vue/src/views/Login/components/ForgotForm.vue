<script lang="ts" setup>
import { reactive } from 'vue';
import { ForgotInfo } from '../../../types/user';
import { getRangeRule, getRequiredRule } from '@/utils/validate';
import { forgotPwd, getVerificationCode } from '@/api/user';
import { notUndefined } from '@/utils/utils';
import { message } from 'ant-design-vue';
import { Schema } from '@/types';
import { omit } from 'lodash';
import { useForm } from '@/hooks/form';
interface RegisterForm extends ForgotInfo {
	$forgot: string
}
const emit = defineEmits(['ok'])
const validateEmail = () => {
	const config = forgotFormSchema.verificationCode.verifyCodeConfig
	const regExp = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
	if (!forgotFormSchema.userEmail.value) {
		config!.disabled = true
		return Promise.reject('请输入邮箱');
	} else if (regExp.test(forgotFormSchema.userEmail.value)) {
		config!.disabled = false
		return Promise.resolve()
	}
	config!.disabled = true
	return Promise.reject('邮箱格式错误')
}
const validatePwd = () => {
	if (forgotFormSchema.confirmPwd.value === '') return Promise.reject("请确认密码")
	return forgotFormSchema.confirmPwd.value === forgotFormSchema.userPassword.value ? Promise.resolve() : Promise.reject("两次输入密码不相同")
}
const forgotFormSchema = reactive<Schema<RegisterForm>>({
	userEmail: {
		type: 'input',
		fieldName: 'userEmail',
		inputConfig: {
			placeholder: '请输入邮箱',
		},
		rules: [{ validator: validateEmail, trigger: 'change' }]
	},
	userPassword: {
		type: 'inputPassword',
		fieldName: 'userPassword',
		inputConfig: {
			placeholder: '请输入用户密码',
			maxlength: 16,
			onChange: () => {
				notUndefined(forgotFormSchema.confirmPwd.value) && formRef.value?.validateFields([['confirmPwd', 'value']])
			}
		},
		rules: [getRequiredRule('请输入用户密码', 'change'), ...getRangeRule(6, 16, '密码长度范围为6-16位', 'change'),],
	},
	confirmPwd: {
		value: '',
		type: 'inputPassword',
		fieldName: 'confirmPwd',
		inputConfig: {
			placeholder: '请确认用户密码',
			maxlength: 16
		},
		rules: [{ validator: validatePwd, trigger: 'change', required: true }],
	},
	verificationCode: {
		type: 'verifyCode',
		fieldName: 'verificationCode',
		rules: [getRequiredRule('请输入验证码', 'change'), ...getRangeRule(6, 6, '验证码长度为6位', 'change')],
		verifyCodeConfig: {
			disabled: true,
			submit: () => {
				getVerificationCode({ userEmail: forgotFormSchema.userEmail.value })
			}
		}
	},
	$forgot: {
		type: 'button',
		fieldName: '$forgot',
		buttonConfig: {
			type: 'primary',
			name: '确认',
			onClick: async () => {
				await formRef.value?.validate()
				await forgotPwd(omit(forgotForm.value, 'confirmPwd'))
				message.success('重置成功')
				emit("ok")
			}
		}
	},
})
const { formRef, formData: forgotForm } = useForm(forgotFormSchema)
</script>

<template>
	<Form ref="formRef" :form-schema="forgotFormSchema"></Form>
</template>

<style lang="scss" scoped></style>

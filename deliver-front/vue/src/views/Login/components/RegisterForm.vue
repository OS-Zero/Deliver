<script lang="ts" setup>
import { reactive } from 'vue';
import { RegisterInfo } from '../../../types/user';
import { getRangeRule, getRequiredRule } from '@/utils/validate';
import { getVerificationCode, register } from '@/api/user';
import { message } from 'ant-design-vue'
import { notUndefined } from "@/utils/utils"
import { Schema } from '@/types';
import { useForm } from '@/hooks/form';
import { omit } from 'lodash'

interface RegisterForm extends RegisterInfo {
	$register: string
}
const emit = defineEmits(['ok'])
const validateEmail = () => {
	const config = registerFormSchema.verificationCode.verifyCodeConfig
	const regExp = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
	if (!registerFormSchema.userEmail.value) {
		config!.disabled = true
		return Promise.reject('请输入邮箱');
	} else if (regExp.test(registerFormSchema.userEmail.value)) {
		config!.disabled = false
		return Promise.resolve()
	}
	config!.disabled = true
	return Promise.reject('邮箱格式错误')
}

const validatePwd = () => {
	if (!registerFormSchema.confirmPwd.value) return Promise.reject("请确认密码")
	return registerFormSchema.confirmPwd.value === registerFormSchema.userPassword.value ? Promise.resolve() : Promise.reject("两次输入密码不相同")
}

const registerFormSchema = reactive<Schema<RegisterForm>>({
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
				notUndefined(registerFormSchema.confirmPwd.value) && formRef.value?.validateFields([['confirmPwd', 'value']])
			}
		},
		rules: [getRequiredRule('请输入用户密码', 'change'), ...getRangeRule(6, 16, '密码长度范围为6-16位', 'change')],
	},
	confirmPwd: {
		value: '',
		type: 'inputPassword',
		fieldName: 'confirmPwd',
		inputConfig: {
			placeholder: '请确认用户密码',
			maxlength: 16
		},
		rules: [{ validator: validatePwd, trigger: 'change' }],
	},
	userRealName: {
		type: 'input',
		fieldName: 'userRealName',
		inputConfig: {
			placeholder: '请输入用户真实姓名',
			maxlength: 50
		},
		rules: [getRequiredRule('请输入用户真实姓名'), ...getRangeRule(1, 50, '用户真实姓名长度为1-50位', 'change')],
	},
	verificationCode: {
		type: 'verifyCode',
		fieldName: 'verificationCode',
		rules: [getRequiredRule('请输入验证码', 'change'), ...getRangeRule(6, 6, '验证码长度为6位', 'change')],
		verifyCodeConfig: {
			disabled: true,
			submit: () => {
				getVerificationCode({ userEmail: registerFormSchema.userEmail.value })
			}
		}
	},
	$register: {
		type: 'button',
		fieldName: '$register',
		buttonConfig: {
			type: 'primary',
			name: '注册',
			onClick: async () => {
				await formRef.value?.validate()
				await register(omit(registerForm.value, 'confirmPwd'))
				message.success('注册成功')
				emit("ok")
			}
		}
	},
})
const { formRef, formData: registerForm } = useForm(registerFormSchema)
</script>

<template>
	<Form ref="formRef" :form-schema="registerFormSchema"></Form>
</template>

<style lang="scss" scoped></style>

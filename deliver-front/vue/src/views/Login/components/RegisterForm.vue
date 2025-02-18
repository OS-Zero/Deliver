<script lang="ts" setup>
import { ref, reactive } from 'vue';
import { RegisterInfo } from '../../../types/user';
import { getRangeRule, getRequiredRule } from '@/utils/validate';
import { getVerificationCode, register } from '@/api/user';
import { message } from 'ant-design-vue'
import { getDataFromSchema, notUndefined, omitProperty } from "@/utils/utils"
import { Schema } from '@/types';

interface RegisterForm extends RegisterInfo {
	$register: string
}

const emit = defineEmits(['ok'])
const formRef = ref()
const verificationBtnDisabled = ref(true)
const validateEmail = () => {
	const regExp = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
	if (!registerForm.userEmail.value) {
		verificationBtnDisabled.value = true
		return Promise.reject('请输入邮箱');
	} else if (regExp.test(registerForm.userEmail.value)) {
		verificationBtnDisabled.value = false
		return Promise.resolve()
	}
	verificationBtnDisabled.value = true
	return Promise.reject('邮箱格式错误')
}

const validatePwd = () => {
	if (!registerForm.confirmPwd.value) return Promise.reject("请确认密码")
	return registerForm.confirmPwd.value === registerForm.userPassword.value ? Promise.resolve() : Promise.reject("两次输入密码不相同")
}
const registerForm = reactive<Schema<RegisterForm>>({
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
				notUndefined(registerForm.confirmPwd.value) && formRef.value?.validateFields([['confirmPwd', 'value']])
			}
		},
		rules: [getRequiredRule('请输入用户密码', 'change'), ...getRangeRule(6, 16, '密码长度范围为6-16位', 'change')],
	},
	confirmPwd: {
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
		type: 'verificationCode',
		fieldName: 'verificationCode',
		inputConfig: {
			placeholder: '请输入验证码',
			maxlength: 6
		},
		rules: [getRequiredRule('请输入验证码'), ...getRangeRule(6, 6, '验证码长度为6位', 'change')],
		buttonConfig: {
			disabled: verificationBtnDisabled.value,
			onClick: () => {
				getVerificationCode({ userEmail: registerForm.userEmail.value })
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
				await formRef.value.validate()
				await register(omitProperty(getDataFromSchema(registerForm), 'confirmPwd'))
				message.success('注册成功')
				emit("ok")
			}
		}
	},
})
</script>

<template>
	<Form ref="formRef" :form-schema="registerForm"></Form>
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

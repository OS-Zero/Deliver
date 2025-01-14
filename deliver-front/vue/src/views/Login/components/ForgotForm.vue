<script lang="ts" setup>
import { ref, reactive } from 'vue';
import { ForgotInfo } from '../../../types/user';
import { getRangeRule, getRequiredRule } from '@/utils/validate';
import { forgotPwd, getVerificationCode } from '@/api/user';
import { getDataFromSchema, omitProperty } from '@/utils/utils';
import { message } from 'ant-design-vue';
import { Schema } from '@/types';
interface RegisterForm extends ForgotInfo {
	$forgot: string
}
const emit = defineEmits(['ok'])
const formRef = ref()
const verificationBtnDisabled = ref(true)
const validateEmail = () => {
	const regExp = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
	if (!forgotForm.userEmail.value) {
		verificationBtnDisabled.value = true
		return Promise.reject('请输入邮箱');
	} else if (regExp.test(forgotForm.userEmail.value)) {
		verificationBtnDisabled.value = false
		return Promise.resolve()
	}
	verificationBtnDisabled.value = true
	return Promise.reject('邮箱格式错误')
}
const validatePwd = () => {
	if (forgotForm.confirmPwd.value === '') return Promise.reject("请确认密码")
	return forgotForm.confirmPwd.value === forgotForm.userPassword.value ? Promise.resolve() : Promise.reject("两次输入密码不相同")
}
const forgotForm = reactive<Schema<RegisterForm>>({
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
		},
		rules: [getRequiredRule('请输入用户密码'), ...getRangeRule(6, 16, '密码长度范围为6-16位')],
	},
	confirmPwd: {
		type: 'inputPassword',
		fieldName: 'confirmPwd',
		inputConfig: {
			placeholder: '请确认用户密码',
		},
		rules: [getRequiredRule('请确认用户密码'), { validator: validatePwd }],
	},
	verificationCode: {
		type: 'verificationCode',
		fieldName: 'verificationCode',
		inputConfig: {
			placeholder: '请输入验证码',

		},
		rules: [getRequiredRule('请输入验证码'), ...getRangeRule(6, 6, '验证码长度为6位')],
		buttonConfig: {
			onClick: () => {
				getVerificationCode({ userEmail: forgotForm.userEmail.value })
			}
		}
	},
	$forgot: {
		type: 'button',
		fieldName: '$forgot',
		buttonConfig: {
			type: 'primary',
			name: '确认',
			style: { width: '100%' },
			onClick: async () => {
				await formRef.value.validate()
				await forgotPwd(omitProperty(getDataFromSchema(forgotForm), 'confirmPwd'))
				message.success('重置成功')
				emit("ok")
			}
		}
	},
})

</script>

<template>
	<Form ref="formRef" :form-schema="forgotForm"></Form>
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

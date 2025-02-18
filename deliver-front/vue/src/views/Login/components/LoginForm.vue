<script lang="ts" setup>
import { ref, reactive } from 'vue';
import { UserInfo } from '../../../types/user';
import { getCurrentLoginUserInfo, login, } from "@/api/user";
import { useRouter } from 'vue-router';
import { message } from 'ant-design-vue'
import { startup } from '@/api/startup';
import { Schema } from '@/types';
import { getDataFromSchema } from '@/utils/utils';
import { getRangeRule, getRequiredRule } from '@/utils/validate';
const router = useRouter()
const formRef = ref()
interface LoginForm extends UserInfo {
	$login: string
}
const validateEmail = () => {
	const regExp = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
	if (!loginForm.userEmail.value) {
		return Promise.reject('请输入邮箱');
	} else if (regExp.test(loginForm.userEmail.value)) {
		return Promise.resolve()
	}
	return Promise.reject('邮箱格式错误')
}
const isTest = import.meta.env.MODE === 'test'
const loginForm = reactive<Schema<LoginForm>>({
	userEmail: {
		value: isTest && 'oszero@qq.com',
		type: 'input',
		fieldName: 'userEmail',
		inputConfig: {
			placeholder: '请输入邮箱',
		},
		rules: [{ validator: validateEmail, trigger: 'change' }]
	},
	userPassword: {
		value: isTest && 'oszero',
		type: 'inputPassword',
		fieldName: 'userPassword',
		inputConfig: {
			placeholder: '请输入用户密码',
			maxlength: 16
		},
		rules: [getRequiredRule('请输入用户密码'), ...getRangeRule(6, 16, '密码长度范围为6-16位', 'change')],
	},
	$login: {
		type: 'button',
		fieldName: '$login',
		buttonConfig: {
			type: 'primary',
			name: '登录',
			onClick: async () => {
				if (import.meta.env.MODE === 'test' && localStorage.getItem('qrcode') !== 'oszero666') {
					open.value = true
				} else {
					await formRef.value.validate()
					let res: any = await login(getDataFromSchema(loginForm))
					localStorage.setItem('access_token', res)
					message.success('登录成功')
					res = await startup()
					localStorage.setItem('startup', JSON.stringify(res))
					res = await getCurrentLoginUserInfo()
					localStorage.setItem('user_info', JSON.stringify(res))
					router.push('/')
				}
			}
		}
	},
})
const open = ref(false)
const code = ref('')
const handleValidate = () => {
	if (code.value === 'oszero666') {
		localStorage.setItem('qrcode', 'oszero666')
		open.value = false
		if (typeof loginForm.$login.buttonConfig?.onClick === 'function') loginForm.$login.buttonConfig.onClick(new MouseEvent('click'))
	} else {
		message.error('验证码错误')
	}
}
</script>

<template>
	<Form ref="formRef" :form-schema="loginForm"></Form>
	<a-modal width="600px" class="modal" v-model:open="open" title="人机验证" centered @ok="handleValidate">
		<p>扫描下方二维码，关注后回复：deliver，获取Deliver企业消息推送平台人机验证码</p>
		<img style="width: 100%;margin: 12px 0;" src="../../../../public/qrcode.jpg" alt="" srcset="">
		<a-input v-model:value="code" placeholder="请输入验证码" />
	</a-modal>
</template>

<style lang="scss" scoped>
.submit_btn {
	width: 100%
}
</style>

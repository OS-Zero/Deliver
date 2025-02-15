<script lang="ts" setup>
import { ref, reactive, onBeforeMount } from 'vue';
import { getVerificationCode, updatePwd } from '@/api/user';
import { getDataFromSchema, notUndefined, omitProperty } from '@/utils/utils';
import { message } from 'ant-design-vue';
import { UserInfo } from '@/types/user';
import { Schema } from '@/types';
import { getRangeRule, getRequiredRule } from '@/utils/validate';
interface EditorForm {
	userPassword: string,
	confirmPwd: string,
	verificationCode: string
}
const userInfo = reactive<UserInfo>({
	userEmail: '',
	userRealName: '',
	userRole: ''
})
const validatePwd = () => {
	if (editorForm.confirmPwd.value === '') return Promise.reject("请确认密码")
	return editorForm.confirmPwd.value === editorForm.userPassword.value ? Promise.resolve() : Promise.reject("两次输入密码不相同")
}
const editorForm = reactive<Schema<EditorForm>>({
	userPassword: {
		type: 'input',
		fieldName: 'userPassword',
		inputConfig: {
			placeholder: '请输入用户密码',
			maxlength: 16,
			onChange: () => {
				notUndefined(editorForm.confirmPwd.value) && formRef.value?.validateFields([['confirmPwd', 'value']])
			}
		},
		rules: [getRequiredRule('请输入用户密码', 'change'), ...getRangeRule(6, 16, '密码长度范围为6-16位', 'change')],
	},
	confirmPwd: {
		type: 'input',
		fieldName: 'confirmPwd',
		inputConfig: {
			maxlength: 16,
			placeholder: '请确认用户密码',
		},
		rules: [getRequiredRule('请确认用户密码', 'change'), { validator: validatePwd, trigger: 'change' }],
	},
	verificationCode: {
		type: 'verificationCode',
		fieldName: 'verificationCode',
		inputConfig: {
			maxlength: 6,
			placeholder: '请输入验证码',
		},
		rules: [getRequiredRule('请输入验证码', 'change'), ...getRangeRule(6, 6, '验证码长度为6位', 'change')],
		buttonConfig: {
			onClick: () => {
				getVerificationCode({ userEmail: userInfo.userEmail })
			}
		}
	},
})
const open = ref<boolean>(false);
const formRef = ref()
const showDrawer = () => {
	open.value = true;
};
const onClose = () => {
	formRef.value.resetFields()
	open.value = false;
};
const onSubmit = async () => {
	await formRef.value.validate()
	await updatePwd(omitProperty(getDataFromSchema(editorForm), "confirmPwd"))
	message.success("修改密码成功")
	onClose()
};

onBeforeMount(async () => {
	Object.assign(userInfo, JSON.parse(localStorage.getItem('user_info') || '{}'))
})
</script>

<template>
	<div>
		<a-card title="基础信息">
			<div class="card_content">
				<p>用户邮箱:&nbsp;&nbsp;&nbsp; {{ userInfo.userEmail }}</p>
				<p>用户姓名:&nbsp;&nbsp;&nbsp; {{ userInfo.userRealName }}</p>
				<p>用户类型: &nbsp;&nbsp;&nbsp;{{ userInfo.userRole }}</p>
				<a-button @click="showDrawer">修改密码</a-button>
			</div>
			<a-drawer title="修改密码" :open="open" placement="right" @close="onClose">
				<Form ref="formRef" :form-schema="editorForm"></Form>
				<template #extra>
					<a-button style="margin-right: 8px" @click="onClose">取消</a-button>
					<a-button type="primary" @click="onSubmit">确认</a-button>
				</template>
			</a-drawer>
		</a-card>
	</div>

</template>

<style lang="scss" scoped>
.ant-card {
	width: 500px;

	p {
		margin: var(--spacing-md) 0;
	}
}

.verify {
	display: flex;

	.verify_btn {
		margin-left: var(--spacing-sm)
	}
}
</style>

<script lang="ts" setup>
import { ref, reactive } from 'vue';
import type { Rule } from 'ant-design-vue/es/form';
import { updatePwd } from '@/api/user';
import { omitProperty } from '@/utils/utils';
import { message } from 'ant-design-vue';
import { getRules } from '@/config/rules';
import { useVerify } from '@/utils/hooks';

const editorData = reactive({
	userPassword: '',
	confirmPwd: '',
	verificationCode: ''
})
const open = ref<boolean>(false);
const formRef = ref()
const showDrawer = () => {
	open.value = true;
};
const onClose = () => {
	open.value = false;
};
const onSubmit = () => {
	formRef.value
		.validate()
		.then(async () => {
			await updatePwd(omitProperty(editorData, "confirmPwd"))
			message.success("修改密码成功")
			onClose()
		})
		.catch(error => {
			console.log('error', error);
		});
};

const validatePwd = () => {
	return editorData.confirmPwd === editorData.userPassword ? Promise.resolve() : Promise.reject("两次输入密码不相同")
}
const rules: Record<string, Rule[]> = {
	confirmPwd: [{ required: true, message: '请确认密码!', trigger: 'blur' }, { validator: validatePwd, trigger: 'blur' }],
	...getRules(['userPassword', 'verificationCode'])
};
const { state, handleVarify } = useVerify()
</script>

<template>
	<a-card title="基础信息">
		<div class="card_content">
			<p>邮箱</p>
			<p>姓名:xxx</p>
			<p>用户类型:xxx</p>
			<a-button @click="showDrawer">修改密码</a-button>
		</div>
		<a-drawer title="修改密码" :open="open" placement="right" @close="onClose">
			<a-form ref="formRef" :model="editorData" :rules="rules">
				<a-form-item name="userPassword">
					<a-input-password v-model:value.trim="editorData.userPassword" placeholder="请输入密码" />
				</a-form-item>
				<a-form-item name="confirmPwd">
					<a-input-password v-model:value.trim="editorData.confirmPwd" placeholder="请确认密码" />
				</a-form-item>
				<a-form-item name="verificationCode">
					<div class="verify">
						<a-input v-model:value.trim="editorData.verificationCode" placeholder="请输入验证码" />
						<a-button class="verify_btn" :disabled="state.verifyDisabled">{{
							state.verifyContent
						}}</a-button>
					</div>
				</a-form-item>
			</a-form>
			<template #extra>
				<a-button style="margin-right: 8px" @click="onClose">取消</a-button>
				<a-button type="primary" @click="onSubmit">确认</a-button>
			</template>
		</a-drawer>
	</a-card>
</template>

<style lang="scss" scoped>
.ant-card {
	width: 300px;

	.card_content {
		text-align: center;
	}

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

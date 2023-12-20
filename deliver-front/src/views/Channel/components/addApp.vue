<script setup lang="ts">
import type { Rule } from 'ant-design-vue/es/form'
import { ref, reactive } from 'vue'
import type { addTemp } from '../type'
import JsonEditorVue from 'json-editor-vue3'
import { getAppConfigByChannelType } from '@/api/channel.ts'
// 新增操作
interface DelayLoading {
	delay: number
}

interface Channel {
	value: string
	label: string
}

const templateItem: addTemp = reactive({
	appName: '',
	channelType: undefined,
	appStatus: 0,
	appConfig: ''
})

const open = ref<boolean>(false)

const labelCol = { style: { width: '80px' } }
const wrapperCol = { span: 36 }

const iconLoading = ref<boolean | DelayLoading>(false)

const templateForm = ref()

const jsonstr = ref<string>('{}')

const jsonobj = ref<object>(JSON.parse(jsonstr.value))

const addModules = (): void => {
	templateItem.appName = ''
	templateItem.channelType = undefined
	templateItem.appStatus = 0
	templateItem.appConfig = ''
	jsonstr.value = '{}'
	jsonobj.value = JSON.parse(jsonstr.value)
	open.value = true
}

const options = ref({
	search: false,
	history: false
})

const modeList = ref(['code']) // 可选模式

const appConfigValidate = async (): Promise<any> => {
	const newjsonstr = JSON.stringify(jsonobj.value)
	if (jsonstr.value == newjsonstr) {
		throw new Error('请正确输入 APP 配置')
	}
	await Promise.resolve()
}

const channelData = ref<Channel[]>([])
channelData.value = [
	{ value: '1', label: '电话' },
	{ value: '2', label: '短信' },
	{ value: '3', label: '邮件' },
	{ value: '4', label: '钉钉' },
	{ value: '5', label: '企业微信' },
	{ value: '6', label: '飞书' }
]

const channelTypeSelect = (value) => {
	getAppConfigByChannelType({ channelType: value }).then((res) => {
		jsonobj.value = JSON.parse(res.data)
		jsonstr.value = JSON.stringify(jsonobj.value)
	})
}

// 提交并传递
const emit = defineEmits(['add'])

const handleOk = (): void => {
	// 异步关闭，先添加，渲染成功后关闭
	templateForm.value
		.validate()
		.then(() => {
			templateItem.appConfig = JSON.stringify(jsonobj.value)
			emit('add')
		})
		.catch((error) => {
			console.log('error', error)
		})
}

const handleCancel = (): void => {
	templateForm.value.resetFields()
	jsonstr.value = '{}'
	jsonobj.value = JSON.parse(jsonstr.value)
	templateItem.channelType = undefined
	templateItem.appName = ''
	templateItem.appStatus = 0
	templateItem.appConfig = '{}'
}

const rules: Record<string, Rule[]> = {
	appName: [
		{ required: true, message: '请输入模板名', trigger: 'change' },
		{ min: 3, max: 20, message: '长度在 3 到 20 个字符', trigger: 'blur' }
	],
	channelType: [
		{
			required: true,
			message: '请选择渠道',
			trigger: 'change'
		}
	],
	appConfig: [{ required: true, validator: appConfigValidate, trigger: 'change' }]
}

const jsonChange = () => {
	templateForm.value?.validate('appConfig').then(() => {})
}

defineExpose({
	open,
	templateItem,
	iconLoading
})
</script>

<template>
	<a-button type="primary" class="addModule" @click="addModules">新增 APP</a-button>
	<a-drawer v-model:open="open" title="新增 APP" width="650px" :footer="null" @cancel="handleCancel">
		<a-form ref="templateForm" :model="templateItem" :rules="rules" :label-col="labelCol" :wrapper-col="wrapperCol" class="temform">
			<a-form-item ref="templateName" label="APP 名称" name="appName" class="tem-item">
				<a-input :maxlength="20" v-model:value="templateItem.appName" placeholder="请填写长度在 3 到 20 个字符的 APP 名" />
			</a-form-item>
			<a-form-item label="渠道选择" name="channelType" class="tem-item">
				<a-select
					placeholder="请选择渠道"
					v-model:value="templateItem.channelType"
					:options="channelData.map((pro) => ({ value: pro.value, label: pro.label }))"
					@select="channelTypeSelect" />
			</a-form-item>
			<a-form-item label="APP 配置" name="appConfig" class="tem-item">
				<json-editor-vue
					class="editor"
					v-model="jsonobj"
					currentMode="code"
					:modeList="modeList"
					:options="options"
					@change="jsonChange"
					language="cn" />
			</a-form-item>
			<a-form-item label="APP 状态" name="appStatus" class="tem-item">
				<a-switch v-model:checked="templateItem.appStatus" checked-children="启用" un-checked-children="禁用" :checkedValue="1" :unCheckedValue="0" />
			</a-form-item>
		</a-form>

		<template #extra>
			<a-button @click="handleCancel">重置</a-button>
			<a-button type="primary" style="margin-left: 10px" @click="handleOk" :loading="iconLoading">确认新建</a-button>
		</template>
	</a-drawer>
</template>

<style scoped>
.addModule {
	margin: 0 20px;
}

.temform {
	.tem-item {
		margin-top: 20px;
	}

	.tem-item:nth-child(8) {
		margin-left: 300px;
		text-align: right;
	}
}
.between {
	margin-right: 16px;
	padding: 0;
	display: flex;
	justify-content: space-between;
}
</style>

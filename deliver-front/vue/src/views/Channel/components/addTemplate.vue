<script setup lang="ts">
import type { Rule } from 'ant-design-vue/es/form'
import { ref, reactive } from 'vue'
import type { addTemp } from '../type'
import { getApp, getMessageType } from '@/api/message'
import JsonEditorVue from 'json-editor-vue3'
// 新增操作
interface DelayLoading {
	delay: number
}

interface Channel {
	value: string
	label: string
}

interface mess {
	code: string
	name: string
}

interface appItem {
	appId: number
	appName: string
}

const templateItem: addTemp = reactive({
	appName: '',
	channelType: undefined,
	appStatus: 0,
	appConfig: ''
})

const open = ref<boolean>(false)

const labelCol = { span: 4 }

const wrapperCol = { span: 20 }

const iconLoading = ref<boolean | DelayLoading>(false)

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
	appConfig: [{ required: true, message: '请输入 APP 配置', trigger: 'change' }],
	appStatus: [{ required: true, message: '请选择 APP 状态', trigger: 'change' }]
}

const templateForm = ref()

const jsonstr = ref<string>('{}')

const jsonobj = ref<object>(JSON.parse(jsonstr.value))

const addModules = (): void => {
	open.value = true
}

const options = ref({
	search: false,
	history: false
})

const modeList = ref(['code']) // 可选模式

const remarkValidate = (): void => {
	const newjsonstr = JSON.stringify(jsonobj.value)
	if (jsonstr.value === newjsonstr) {
		console.log('no change')
	} else {
		jsonstr.value = newjsonstr
	}
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

const messageData = ref<mess[]>([])

const appData = ref<appItem[]>([])

// 选择完渠道后，此时应该是已经拿到下面两项的选择项
const selectValues = (channelType): void => {
	messageData.value.length = 0
	appData.value.length = 0
	getMessageType({ channelType: Number(channelType) })
		.then((res) => {
			res.data.forEach((item: mess) => {
				messageData.value.push(item)
			})
		})
		.catch((err) => {
			console.error('An error occurred:', err)
		})
	getApp({ channelType: Number(channelType) })
		.then((res) => {
			res.data.forEach((item: any) => {
				appData.value.push(item)
			})
		})
		.catch((err) => {
			console.error('An error occurred:', err)
		})
}

// 提交并传递
const emit = defineEmits(['add'])

const handleOk = (): void => {
	// 异步关闭，先添加，渲染成功后关闭
	templateForm.value
		.validate()
		.then(() => {
			// eslint-disable-next-line
			templateItem.appStatus = templateItem.appStatus === true ? 1 : 0
			templateItem.appConfig = JSON.stringify(jsonobj.value)
			emit('add')
			handleCancel()
		})
		.catch((error) => {
			console.log('error', error)
		})
}

const handleCancel = (): void => {
	templateForm.value.resetFields()
	// userdisabled.value = userdisabled.value.map(item => ({ ...item, disabled: true }))
	templateItem.channelType = undefined
	templateItem.appName = ''
	templateItem.appStatus = 1
	templateItem.appConfig = ''
	// channelDisabled.value = true
}

defineExpose({
	open,
	templateItem,
	iconLoading
})
</script>

<template>
	<a-button type="primary" class="addModule" @click="addModules">新增 APP</a-button>
	<a-modal v-model:open="open" title="新增 APP" width="650px" :footer="null" @cancel="handleCancel">
		<a-form
			ref="templateForm"
			:model="templateItem"
			:rules="rules"
			:label-col="labelCol"
			:wrapper-col="wrapperCol"
			class="temform">
			<a-form-item ref="templateName" label="APP 名称" name="appName" class="tem-item">
				<a-input
					v-model:value="templateItem.appName"
					placeholder="请填写长度在3到20个字符的模板名"
					style="width: 70%" />
			</a-form-item>
			<a-form-item label="渠道选择" name="channelType" class="tem-item">
				<a-select
					v-model:value="templateItem.channelType"
					:options="channelData.map((pro) => ({ value: pro.value, label: pro.label }))"
					style="width: 70%"
					@change="selectValues" />
			</a-form-item>
			<a-form-item label="APP 配置" name="appConfig" class="tem-item">
				<json-editor-vue
					class="editor"
					v-model="jsonobj"
					@blur="remarkValidate"
					currentMode="code"
					:modeList="modeList"
					:options="options"
					language="cn" />
			</a-form-item>
			<a-form-item label="APP 状态" name="appStatus" class="tem-item">
				<a-switch
					v-model:checked="templateItem.appStatus"
					checked-children="启用"
					un-checked-children="禁用" />
			</a-form-item>
			<a-form-item :wrapper-col="{ span: 25, offset: 4 }" class="tem-item">
				<div class="between">
					<a-button @click="handleCancel">重置</a-button>
					<a-button type="primary" @click="handleOk" :loading="iconLoading">确认新建</a-button>
				</div>
			</a-form-item>
		</a-form>
	</a-modal>
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
	margin: 0;
	padding: 0;
	display: flex;
	justify-content: space-between;
}
</style>

<script setup lang="ts">
import type { Rule } from 'ant-design-vue/es/form'
import { ref, reactive, watch } from 'vue'
import type { addTemp } from '../type'
import { getPushWays } from '@/utils/date'
import { getApp, getMessageType } from '@/api/message'

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
	templateName: '',
	pushRange: undefined,
	usersType: undefined,
	pushWays: '',
	templateStatus: 0,
	appId: undefined,
	channelType: undefined,
	messageType: ''
})

const open = ref<boolean>(false)

const labelCol = { span: 5 }

const wrapperCol = { span: 20 }

const iconLoading = ref<boolean | DelayLoading>(false)

const validatePass = async (): Promise<any> => {
	if (!appDisabled.value && appData.value.length === 0) {
		throw new Error('请先进行渠道 App 配置')
	}
	await Promise.resolve()
}

const rules: Record<string, Rule[]> = {
	templateName: [
		{ required: true, message: '请输入模板名', trigger: 'change' },
		{ min: 3, max: 20, message: '长度在 3 到 20 个字符', trigger: 'blur' }
	],
	pushRange: [{ required: true, message: '请选择推送范围', trigger: 'change' }],
	usersType: [{ required: true, message: '请选择用户类型', trigger: 'change' }],
	channelType: [
		{
			required: true,
			message: '请选择渠道',
			trigger: 'change'
		}
	],
	appId: [
		{
			required: true,
			validator: validatePass,
			trigger: 'blur'
		}
	],
	messageType: [{ required: true, message: '请选择消息类型', trigger: 'change' }],
	templateStatus: [{ required: true, message: '请选择模板状态', trigger: 'change' }]
}

const templateForm = ref()

const addModules = (): void => {
	// 重置初始值
	templateItem.templateName = ''
	templateItem.pushRange = undefined
	templateItem.usersType = undefined
	templateItem.pushWays = ''
	templateItem.templateStatus = 0
	templateItem.appId = undefined
	templateItem.channelType = undefined
	templateItem.messageType = ''
	open.value = true
}

// 处理推送范围和用户类型之间的关系
const userdisabled = ref([
	{ value: 1, label: '企业账号', disabled: true },
	{ value: 2, label: '电话', disabled: true },
	{ value: 3, label: '邮箱', disabled: true },
	{ value: 4, label: '平台 UserId', disabled: true }
])

const handlePushRangeChange = (e): void => {
	if (e.target.value === 0 || e.target.value === 1) {
		userdisabled.value = userdisabled.value.map((item) => ({ ...item, disabled: false }))
	} else if (e.target.value === 2) {
		userdisabled.value = userdisabled.value.map((item) => {
			if (item.value === 2 || item.value === 3) {
				return { ...item, disabled: false }
			} else {
				return { ...item, disabled: true }
			}
		})
	}
}

const channelData = ref<Channel[]>([])

// 推送范围 + 用户类型 => 渠道
const pickChannel = (e): void => {
	channelDisabled.value = false
	templateItem.channelType = undefined
	const Data: Channel[] = [
		{ value: '1', label: '电话' },
		{ value: '2', label: '短信' },
		{ value: '3', label: '邮件' },
		{ value: '4', label: '钉钉' },
		{ value: '5', label: '企业微信' },
		{ value: '6', label: '飞书' }
	]
	if (e.target.value === 1) {
		channelData.value = [...Data]
	} else if (e.target.value === 2) {
		if (templateItem.pushRange === 2) {
			channelData.value = Data.splice(0, 2)
		} else {
			channelData.value = Data.filter((item) => item.value !== '3')
		}
	} else if (e.target.value === 3) {
		channelData.value = Data.filter((item) => item.value === '3')
	} else if (e.target.value === 4) {
		channelData.value = Data.slice(3)
	}
}

const channelDisabled = ref(true)

const messageDisabled = ref(true)

const appDisabled = ref(true)

const messageData = ref<mess[]>([])

const appData = ref<appItem[]>([])

// 选择完渠道后，此时应该是已经拿到下面两项的选择项
const selectValues = (channelType): void => {
	templateItem.appId = undefined
	templateItem.messageType = ''
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

const selectApp = (): void => {
	messageDisabled.value = false
}

// 提交并传递
const emit = defineEmits(['add'])

const handleOk = (): void => {
	// 异步关闭，先添加，渲染成功后关闭
	templateForm.value
		.validate()
		.then(() => {
			// 处理templateItem的pushways
			templateItem.pushWays = getPushWays(templateItem.channelType, templateItem.messageType)
			emit('add')
		})
		.catch((error) => {
			console.log('error', error)
		})
}

const handleCancel = (): void => {
	iconLoading.value = false
	templateForm.value.resetFields()
	userdisabled.value = userdisabled.value.map((item) => ({ ...item, disabled: true }))
	templateItem.channelType = undefined
	channelDisabled.value = true
}

watch(
	() => templateItem.channelType,
	(newVal) => {
		// 防止不能及时重置到数据，直接清零
		if (newVal === undefined) {
			templateItem.appId = undefined
			templateItem.messageType = ''
			messageDisabled.value = true
			appDisabled.value = true
			if (templateItem.pushRange === undefined && templateItem.usersType === undefined) {
				channelDisabled.value = true
			}
		} else {
			appDisabled.value = false
		}
	}
)

defineExpose({
	open,
	templateItem,
	iconLoading
})
</script>

<template>
	<a-button type="primary" class="addModule" @click="addModules">新增模板</a-button>
	<a-modal v-model:open="open" title="新增模板" width="650px" :footer="null" @cancel="handleCancel">
		<a-form
			ref="templateForm"
			:model="templateItem"
			:rules="rules"
			:label-col="labelCol"
			:wrapper-col="wrapperCol"
			class="temform">
			<a-form-item ref="templateName" label="模板名" name="templateName" class="tem-item">
				<a-input
					:maxlength="20"
					v-model:value="templateItem.templateName"
					placeholder="请填写长度在3到20个字符的模板名"
					style="width: 70%" />
			</a-form-item>
			<a-form-item label="推送范围" name="pushRange" class="tem-item">
				<a-radio-group
					v-model:value="templateItem.pushRange"
					button-style="solid"
					@change="handlePushRangeChange">
					<a-radio-button :value="0">不限</a-radio-button>
					<a-radio-button :value="1">企业内部</a-radio-button>
					<a-radio-button :value="2">企业外部</a-radio-button>
				</a-radio-group>
			</a-form-item>
			<a-form-item label="用户类型" name="usersType" class="tem-item">
				<a-radio-group
					v-model:value="templateItem.usersType"
					button-style="solid"
					@change="pickChannel">
					<a-radio-button
						v-for="u in userdisabled"
						:key="u.value"
						:disabled="u.disabled"
						:value="u.value">
						{{ u.label }}
					</a-radio-button>
				</a-radio-group>
			</a-form-item>
			<a-form-item label="渠道选择" name="channelType" class="tem-item">
				<a-select
					v-model:value="templateItem.channelType"
					:options="channelData.map((pro) => ({ value: pro.value, label: pro.label }))"
					style="width: 70%"
					@change="selectValues"
					:disabled="channelDisabled" />
			</a-form-item>
			<a-form-item label="渠道 App" name="appId" class="tem-item">
				<a-select
					v-model:value="templateItem.appId"
					style="width: 70%"
					:disabled="appDisabled"
					@change="selectApp">
					<a-select-option v-for="item in appData" :key="item.appId" :value="item.appId">
						{{ item.appName }}
					</a-select-option>
				</a-select>
			</a-form-item>
			<a-form-item label="消息类型" name="messageType" class="tem-item">
				<a-select
					v-model:value="templateItem.messageType"
					style="width: 70%"
					:disabled="messageDisabled">
					<a-select-option v-for="item in messageData" :key="item.code" :value="item.code">
						{{ item.name }}
					</a-select-option>
				</a-select>
			</a-form-item>
			<a-form-item label="模板状态" name="templateStatus" class="tem-item">
				<a-switch
					v-model:checked="templateItem.templateStatus"
					checked-children="启用"
					un-checked-children="禁用"
					:checkedValue="1"
					:unCheckedValue="0" />
			</a-form-item>
			<a-form-item :wrapper-col="{ span: 25, offset: 6 }" class="tem-item">
				<a-button @click="handleCancel">重置</a-button>
				<a-button style="margin-left: 10px" type="primary" @click="handleOk" :loading="iconLoading">
					确认新建
				</a-button>
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
</style>

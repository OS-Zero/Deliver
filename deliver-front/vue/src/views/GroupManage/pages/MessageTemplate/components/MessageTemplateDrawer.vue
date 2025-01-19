<script lang="ts" setup>
import { messageTemplateLocale, messageTemplateForm, testMessageForm, setOptionsDispatch } from '@/config/messageTemplate';
import { DrawerProps } from '@/types/components';
import { copyToClipboard, getDataFromSchema } from '@/utils/utils';
import { FormInstance, message } from 'ant-design-vue';
import { ref, reactive, watch, nextTick } from 'vue';
import { CopyOutlined } from '@ant-design/icons-vue';
import { getMessageParam } from '@/api/system';
import { addMessageTemplate, testSendMessage, updateMessageTemplate } from '@/api/messageTemplate';
type Operation = 'add' | 'edit' | 'more' | 'testSend'
const props = defineProps<{
	open: boolean
	operation: Operation
	record: Record<string, any>
}>()
const emit = defineEmits(['close'])
const formRef = ref<FormInstance>()

const titleList: Record<Operation, string> = {
	add: '新增模板',
	edit: '编辑模板',
	more: '模板详情',
	testSend: '测试发送'
}
const copyId = async (text: string) => {
	try {
		await copyToClipboard(text)
		message.success('复制成功')
	} catch (error) {
		message.error('复制失败')
	}
}
const drawerState = reactive<DrawerProps>({
	open: props.open,
	title: titleList[props.operation],
	placement: props.operation === 'more' ? 'left' : 'right',
	extra: props.operation === 'more' ? false : true,
	okText: props.operation === 'testSend' ? '发送' : '确认',
})
watch(props, (newProps) => {
	Object.assign(drawerState, {
		open: newProps.open,
		title: titleList[newProps.operation],
		placement: newProps.operation === 'more' ? 'left' : 'right',
		extra: props.operation === 'more' ? false : true,
		okText: props.operation === 'testSend' ? '发送' : '确认',
	});
	newProps.operation === 'edit' && newProps.open && initFormDate();
	newProps.operation === 'more' && newProps.open && initMoreDate();
	newProps.operation === 'testSend' && initTestMessageFormDate();
})
const initFormDate = () => {
	nextTick(async () => {
		for (const key in messageTemplateForm) {
			messageTemplateForm[key].value = props.record[key]
		}
		setOptionsDispatch['channelType']({ usersType: messageTemplateForm.usersType.value });
		setOptionsDispatch['channelProviderType']({ channelType: messageTemplateForm.channelType.value });
		setOptionsDispatch['messageType']({ channelType: messageTemplateForm.channelType.value, channelProviderType: messageTemplateForm.channelProviderType.value });
		setOptionsDispatch['appId']({ channelType: messageTemplateForm.channelType.value, channelProviderType: messageTemplateForm.channelProviderType.value });
	})
}
const initTestMessageFormDate = () => {
	nextTick(() => {
		testMessageForm.users.value = []
		testMessageForm.templateId.value = props.record.templateId
	})
	getMessageParam({ messageType: props.record.messageType }).then(res => {
		testMessageForm.messageParam.value = JSON.parse(res || '{}')
	})
}
const initMoreDate = () => {
	const obj = {
		templateInfo: ['templateId', 'templateName', 'templateDescription', 'templateStatus', 'createUser', 'createTime'],
		typeInfo: ['usersTypeName', 'channelTypeName', 'channelProviderTypeName', 'messageTypeName'],
		linkInfo: ['appName']
	}
	for (const key in obj) {
		let group: any = {
			config: {
				title: ''
			},
			data: []
		}
		if (key === 'templateInfo') {
			group.config.title = '模板信息'
		} else if (key === 'typeInfo') {
			group.config.title = '类型信息'
		} else if (key === 'linkInfo') {
			group.config.title = '关联信息'
		}
		obj[key].forEach((name: string) => {
			group.data.push({
				label: messageTemplateLocale[name],
				value: props.record[name]
			})
		});
		groups.push(group)
	}
}
const groups = reactive<any>([])
const operationDispatch = {
	add: async () => {
		await formRef.value?.validate()
		await addMessageTemplate(getDataFromSchema(messageTemplateForm))
		message.success('新增成功')
		handleCancel(true)
	},
	edit: async () => {
		await formRef.value?.validate()
		await updateMessageTemplate(getDataFromSchema(messageTemplateForm))
		message.success('编辑成功')
		handleCancel(true)
	},
	testSend: async () => {
		await formRef.value?.validate()
		await testSendMessage(getDataFromSchema(testMessageForm))
	}
}

const handleCancel = (flash: boolean = false) => {
	props.operation !== 'more' && formRef.value?.resetFields()
	drawerState.open = false
	groups.length = 0
	emit('close', flash)
}

</script>

<template>
	<Drawer v-bind="drawerState" @ok="operationDispatch[operation]" @close="handleCancel">
		<Form ref="formRef" v-if="operation === 'add' || operation === 'edit'" :form-schema="messageTemplateForm" />
		<Form ref="formRef" v-else-if="operation === 'testSend'" :form-schema="testMessageForm" />
		<div v-else-if="operation === 'more'">
			<Descriptions :groups="groups">
				<template #value="{ item }">
					<template v-if="item.label === '模板 Id'">
						{{ item.value }}
						<CopyOutlined v-if="item.label === '模板 Id'" class="id--copy" @click="copyId(item.value)" />
					</template>
					<template v-if="item.label === '模板状态'">
						{{ item.value ? '开启' : '关闭' }}
						<Status :success="item.value"></Status>
					</template>
				</template>
			</Descriptions>
		</div>
	</Drawer>
</template>

<style lang="scss" scoped>
.id--copy {
	color: var(--primary-color);
	margin-left: var(--spacing-xs);
}
</style>

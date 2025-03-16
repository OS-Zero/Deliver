<script lang="ts" setup>
import { messageTemplateFormSchema, testMessageFormSchema } from '@/config/messageTemplate';
import { message } from 'ant-design-vue';
import { computed } from 'vue';
import { addMessageTemplate, testSendMessage, updateMessageTemplate } from '@/api/messageTemplate';
import { useForm } from "@/hooks/form";
import { MessageTemplate, MessageTemplateForm, TestSendMessage } from '@/types/messageTemplate';
import MessageTemplateDescriptions from "./MessageTemplateDescriptions.vue"
type Operation = 'add' | 'edit' | 'more' | 'testSend'
const props = defineProps<{
	open: boolean
	operation: Operation
	record: MessageTemplate
}>()
const emit = defineEmits(['close'])
const { formRef: messageTemplateFormRef, formData: messageTemplateForm } = useForm<MessageTemplateForm>(messageTemplateFormSchema)
const { formRef: testMessageFormRef, formData: testMessageForm } = useForm<TestSendMessage>(testMessageFormSchema)
const titleList: Record<Operation, string> = {
	add: '新增模板',
	edit: '编辑模板',
	more: '模板详情',
	testSend: '测试发送'
}
const drawerState = computed(() => {
	return {
		open: props.open,
		title: titleList[props.operation],
		placement: props.operation === 'more' ? 'left' : 'right',
		extra: props.operation === 'more' ? false : true,
		okText: props.operation === 'testSend' ? '发送' : '确认',
	}
})
const operationDispatch = {
	add: async () => {
		await messageTemplateFormRef.value?.validate()
		await addMessageTemplate(messageTemplateForm.value)
		message.success('新增成功')
		handleCancel(true)
	},
	edit: async () => {
		await messageTemplateFormRef.value?.validate()
		await updateMessageTemplate(messageTemplateForm.value)
		message.success('编辑成功')
		handleCancel(true)
	},
	testSend: async () => {
		await testMessageFormRef.value?.validate()
		await testSendMessage(testMessageForm.value)
	}
}

const handleCancel = (flash: boolean = false) => {
	emit('close', flash)
}
</script>

<template>
	<Drawer v-bind="drawerState" @ok="operationDispatch[operation]" @close="handleCancel">
		<Form ref="messageTemplateFormRef" :form-schema="messageTemplateFormSchema" :record="record"
			v-if="operation === 'add' || operation === 'edit'" />
		<Form ref="testMessageFormRef" :form-schema="testMessageFormSchema" :record="record"
			v-else-if="operation === 'testSend'" />
		<MessageTemplateDescriptions :record="record" v-else-if="operation === 'more'"></MessageTemplateDescriptions>
	</Drawer>
</template>

<style lang="scss" scoped></style>

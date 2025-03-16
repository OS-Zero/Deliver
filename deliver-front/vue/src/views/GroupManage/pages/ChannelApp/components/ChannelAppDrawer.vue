<script lang="ts" setup>
import { message } from 'ant-design-vue';
import { computed } from 'vue';
import { channelAppFormSchema } from '@/config/channelApp';
import { saveChannelApp, updateChannelApp } from '@/api/channelApp';

import ChannelAppDescriptions from './ChannelAppDescriptions.vue';
import { ChannelApp } from '@/types/channelApp';
import { useForm } from '@/hooks/form';
type Operation = 'add' | 'edit' | 'more'
const props = defineProps<{
	open: boolean
	operation: Operation
	record: ChannelApp
}>()
const emit = defineEmits(['close'])

const titleList: Record<Operation, string> = {
	add: '新增应用',
	edit: '编辑应用',
	more: '应用详情',
}

const drawerState = computed(() => ({
	open: props.open,
	title: titleList[props.operation],
	placement: props.operation === 'more' ? 'left' : 'right',
	extra: props.operation === 'more' ? false : true,
}))
const { formRef, formData: channelAppForm } = useForm(channelAppFormSchema)
const operationDispatch = {
	add: async () => {
		await formRef.value?.validate()
		await saveChannelApp(channelAppForm.value)
		message.success('新增成功')
		handleCancel(true)
	},
	edit: async () => {
		await formRef.value?.validate()
		await updateChannelApp(channelAppForm.value)
		message.success('编辑成功')
		handleCancel(true)
	},
}

const handleCancel = (flash: boolean = false) => {
	emit('close', flash)
}
</script>

<template>
	<Drawer v-bind="drawerState" @ok="operationDispatch[operation]" @close="handleCancel">
		<Form ref="formRef" v-if="operation === 'add' || operation === 'edit'" :record="record"
			:form-schema="channelAppFormSchema" />
		<ChannelAppDescriptions :record="record" v-else-if="operation === 'more'"></ChannelAppDescriptions>
	</Drawer>
</template>

<style lang="scss" scoped></style>

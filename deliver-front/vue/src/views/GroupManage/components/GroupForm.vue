<script lang="ts" setup>
import { reactive, onUnmounted, watch, nextTick } from 'vue';
import { saveGroup, updateGroup } from '@/api/group';
import { Schema } from '@/types';
import { DrawerProps } from '@/types/components';
import { getRangeRule, getRequiredRule } from '@/utils/validate';
import { message } from 'ant-design-vue';
import { useForm } from '@/hooks/form';
import { GroupCard } from '@/types/group';

type Operation = 'add' | 'edit'
const props = defineProps<{
	open: boolean
	operation: Operation
	record: Record<string, any>
}>()
const emit = defineEmits(['close'])
const groupFormSchema = reactive<Schema<GroupCard>>({
	groupId: {
		type: 'none',
		fieldName: 'groupName',
	},
	groupName: {
		type: 'input',
		fieldName: 'groupName',
		label: '分组名',
		rules: [getRequiredRule('请输入分组名'), ...getRangeRule(1, 10, '字符长度限制在1-10')],
	},
	groupDescription: {
		type: 'textarea',
		fieldName: 'groupDescription',
		label: '分组描述',
		rules: [getRequiredRule('请输入分组描述'), ...getRangeRule(3, 20, '字符长度限制在3-20')],
	},
})
const { formRef, formData } = useForm(groupFormSchema)
const titleList: Record<Operation, string> = {
	add: '新增分组',
	edit: '编辑分组',
}
const drawerState = reactive<DrawerProps>({
	open: props.open,
	title: titleList[props.operation],
	placement: 'right',
	extra: true,
})
watch(props, (newProps) => {
	drawerState.open = newProps.open
	drawerState.title = titleList[newProps.operation]
	newProps.operation === 'edit' && newProps.open === true && initFormDate();
})
const initFormDate = () => {
	nextTick(() => {
		for (const key in groupFormSchema) {
			groupFormSchema[key].value = props.record[key]
		}
	})
}
const handleCancel = () => {
	formRef.value?.resetFields()
	drawerState.open = false
	emit('close')
}
const operationDispatch = {
	add: async () => {
		await saveGroup(formData.value)
		message.success('新增成功')
		handleCancel()
	},
	edit: async () => {
		await updateGroup(formData.value)
		message.success('编辑成功')
		handleCancel()
	}
}

onUnmounted(() => {
	stop()
})

</script>

<template>
	<Drawer v-bind="drawerState" @ok="operationDispatch[operation]" @close="handleCancel">
		<Form ref="formRef" :label-col="{ span: 4 }" :form-schema="groupFormSchema" />
	</Drawer>
</template>

<style lang="scss" scoped></style>

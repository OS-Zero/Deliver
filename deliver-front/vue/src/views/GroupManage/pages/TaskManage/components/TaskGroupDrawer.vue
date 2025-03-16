<script lang="ts" setup>
import { saveTask, updateTask } from '@/api/task';
import { taskFormSchema } from '@/config/task';
import { useForm } from '@/hooks/form';
import { message } from 'ant-design-vue';
import { computed } from 'vue';
import TaskGroupDescriptions from './TaskGroupDescriptions.vue';
import { Task } from '@/types/task';

type Operation = 'add' | 'edit' | 'more'
const props = defineProps<{
	open: boolean
	operation: Operation
	record: Task
}>()
const emit = defineEmits(['close'])
const { formRef, formData: taskForm } = useForm(taskFormSchema)

const titleList: Record<Operation, string> = {
	add: '新增任务',
	edit: '编辑任务',
	more: '任务详情',
}

const drawerState = computed(() => ({
	open: props.open,
	title: titleList[props.operation],
	placement: props.operation === 'more' ? 'left' : 'right',
	extra: props.operation === 'more' ? false : true,
}))

const operationDispatch = {
	add: async () => {
		await formRef.value?.validate()
		await saveTask(taskForm.value)
		message.success('新增成功')
		handleCancel(true)
	},
	edit: async () => {
		await formRef.value?.validate()
		await updateTask(taskForm.value)
		message.success('编辑成功')
		handleCancel(true)
	}
}

const handleCancel = (flash: boolean = false) => {
	emit('close', flash)
}
</script>

<template>
	<Drawer v-bind="drawerState" @ok="operationDispatch[operation]" @close="handleCancel">
		<Form ref="formRef" v-if="operation === 'add' || operation === 'edit'" :record="record"
			:form-schema="taskFormSchema" />
		<TaskGroupDescriptions :record="record" v-else-if="operation === 'more'"></TaskGroupDescriptions>
	</Drawer>
</template>

<style lang="scss" scoped></style>

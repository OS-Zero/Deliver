<script lang="ts" setup>
import { savePeopleGroup, updatePeopleGroup } from '@/api/peopleGroup';
import { taskLocale, taskSchema } from '@/config/task';
import { DrawerProps } from '@/types/components';
import { getDataFromSchema } from '@/utils/utils';
import { message } from 'ant-design-vue';
import { ref, reactive, onUnmounted, watch, nextTick } from 'vue';

type Operation = 'add' | 'edit' | 'more'
const props = defineProps<{
	open: boolean
	operation: Operation
	record: Record<string, any>
}>()
const emit = defineEmits(['close'])
const formRef = ref()

const titleList: Record<Operation, string> = {
	add: '新增任务',
	edit: '编辑任务',
	more: '任务详情',
}

const drawerState = reactive<DrawerProps>({
	open: props.open,
	title: titleList[props.operation],
	placement: props.operation === 'more' ? 'left' : 'right',
	extra: props.operation === 'more' ? false : true,
})
watch(props, (newProps) => {
	Object.assign(drawerState, {
		open: newProps.open,
		title: titleList[newProps.operation],
		placement: newProps.operation === 'more' ? 'left' : 'right',
		extra: props.operation === 'more' ? false : true,
	});
	newProps.operation === 'edit' && newProps.open === true && initFormDate();
	newProps.operation === 'more' && initMoreDate();
})

const taskForm = reactive(taskSchema)
const initFormDate = () => {
	nextTick(() => {
		for (const key in taskForm) {
			if (key === 'taskParam') taskForm[key].value = JSON.parse(props.record[key] || '{}')
			else taskForm[key].value = props.record[key]
		}
	})
}
const initMoreDate = () => {
	const set = new Set(['taskId', 'taskType'])
	const arr: Array<{ label: string; value: any }> = []
	for (const key in props.record) {
		if (!set.has(key)) {
			arr.push({
				label: taskLocale[key],
				value: props.record[key]
			})
		}
	}
	Object.assign(moreInfo, arr)
}
const moreInfo = reactive<Array<{ label: string; value: any }>>([])
const operationDispatch = {
	add: async () => {
		await formRef.value.validate()
		await savePeopleGroup(getDataFromSchema(taskForm))
		message.success('新增成功')
		handleCancel()
	},
	edit: async () => {
		await formRef.value.validate()
		await updatePeopleGroup(getDataFromSchema(taskForm))
		message.success('编辑成功')
		handleCancel()
	}
}

const handleCancel = () => {
	props.operation !== 'more' && formRef.value.resetFields()
	drawerState.open = false
	emit('close')
}
onUnmounted(() => {
	stop()
})

</script>

<template>
	<Drawer v-bind="drawerState" @ok="operationDispatch[operation]" @close="handleCancel">
		<Form ref="formRef" v-if="operation === 'add' || operation === 'edit'" :form-schema="taskForm" />
		<div v-else-if="operation === 'more'">
			<Descriptions :data="moreInfo" :config="{ column: 1 }">
				<template #content="{ item }">
					<template v-if="item.label === '任务类型'">
						{{ item.value === 1 ? '实时' : '定时' }}
					</template>
					<template v-else-if="item.label === '任务状态'">
						{{ !!item.value ? '开启' : '关闭' }}
					</template>
				</template>
			</Descriptions>
		</div>
	</Drawer>
</template>

<style lang="scss" scoped></style>

<script lang="ts" setup>
import { searchTemplateByName } from '@/api/messageTemplate';
import { searchPeopleGroupByName } from '@/api/peopleGroup';
import { saveTask, updateTask } from '@/api/task';
import { taskLocale, taskForm, validateCronExpression } from '@/config/task';
import { DrawerProps } from '@/types/components';
import { getDataFromSchema } from '@/utils/utils';
import { getRequiredRule } from '@/utils/validate';
import { FormInstance, message } from 'ant-design-vue';
import { ref, reactive, watch, nextTick } from 'vue';

type Operation = 'add' | 'edit' | 'more'
const props = defineProps<{
	open: boolean
	operation: Operation
	record: Record<string, any>
}>()
const emit = defineEmits(['close'])
const formRef = ref<FormInstance>()

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
	if (newProps.operation === 'add') {
		taskForm.taskTimeExpression.type = 'none';
		taskForm.taskTimeExpression.value = undefined
	}
	newProps.operation === 'edit' && newProps.open === true && initFormDate();
	newProps.operation === 'more' && initMoreDate();
})

const initFormDate = () => {
	nextTick(() => {
		for (const key in taskForm) {
			if (key === 'taskMessageParam') taskForm[key].value = JSON.parse(props.record[key] || '{}')
			else taskForm[key].value = props.record[key]
		}
		searchTemplateByName({ templateName: props.record.templateName }).then((res) => {
			taskForm.templateId.selectConfig!.options = res.map((item) => ({
				value: item.templateId,
				label: item.templateName,
			}));
		});
		searchPeopleGroupByName({ peopleGroupName: props.record.peopleGroupName }).then((res) => {
			taskForm.peopleGroupId.selectConfig!.options = res.map((item) => ({
				value: item.peopleGroupId,
				label: item.peopleGroupName,
			}));
		});
		if (taskForm.taskType.value === 1) {
			taskForm.taskTimeExpression.type = 'none';
		} else if (taskForm.taskType.value === 2) {
			taskForm.taskTimeExpression.type = 'input';
			taskForm.taskTimeExpression.rules = [{ validator: validateCronExpression }];
			taskForm.taskTimeExpression.customConfig!.tip =
				'Cron 表达式格式：秒(0-59) 分钟(0-59) 小时(0-23) 日(1-31) 月(1-12) 星期(0-6或SUN-SAT) 年(可选,1970-2099)。例如：20 3 * * * ? 表示每天 3:20 执行任务；20 3 8 * * ? 表示每月 8 号 3:20 执行任务；* 表示任意值；? 用于表示不指定某个字段的值。';
		} else if (taskForm.taskType.value === 3) {
			taskForm.taskTimeExpression.rules = [getRequiredRule('请选择日期')];
			taskForm.taskTimeExpression.type = 'datePicker';
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
		await formRef.value?.validate()
		await saveTask(getDataFromSchema(taskForm))
		message.success('新增成功')
		handleCancel()
	},
	edit: async () => {
		await formRef.value?.validate()
		await updateTask(getDataFromSchema(taskForm))
		message.success('编辑成功')
		handleCancel()
	}
}

const handleCancel = () => {
	props.operation !== 'more' && formRef.value?.resetFields()
	drawerState.open = false
	emit('close')
}

watch(() => taskForm.taskTimeExpression.type, () => {
	formRef.value?.clearValidate(['taskTimeExpression', 'value'])
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

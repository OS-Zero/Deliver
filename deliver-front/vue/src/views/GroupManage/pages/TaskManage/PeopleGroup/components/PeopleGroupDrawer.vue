<script lang="ts" setup>
import { savePeopleGroup, updatePeopleGroup } from '@/api/peopleGroup';
import { peopleGroupLocale, peopleGroupSchema, peopleGroupSchemaDeps } from '@/config/peopleGroup';
import { DrawerProps } from '@/types/components';
import { dynamic, getDataFromSchema } from '@/utils/utils';
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
	add: '新增人群',
	edit: '编辑人群',
	more: '人群详情',
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

const { dynamicData: peopleGroupForm, stop } = dynamic(peopleGroupSchema, peopleGroupSchemaDeps)
const initFormDate = () => {
	nextTick(() => {
		for (const key in peopleGroupForm) {
			peopleGroupForm[key].value = props.record[key]
		}
	})
}
const initMoreDate = () => {
	const set = new Set(['peopleGroupId', 'usersType'])
	const arr: Array<{ label: string; value: any }> = []
	for (const key in props.record) {
		if (!set.has(key)) {
			arr.push({
				label: peopleGroupLocale[key],
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
		await savePeopleGroup(getDataFromSchema(peopleGroupForm))
		message.success('新增成功')
		handleCancel()
	},
	edit: async () => {
		await formRef.value.validate()
		await updatePeopleGroup(getDataFromSchema(peopleGroupForm))
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
		<Form ref="formRef" v-if="operation === 'add' || operation === 'edit'" :label-col="{ span: 4 }"
			:form-schema="peopleGroupForm" />
		<div v-else-if="operation === 'more'">
			<Descriptions :data="moreInfo" :config="{ column: 1 }">
				<template #content="{ item }">
					<template v-if="item.label === '人群类型'">
						{{ item.value === 1 ? '实时' : '定时' }}
					</template>
					<template v-else-if="item.label === '人群状态'">
						{{ !!item.value ? '开启' : '关闭' }}
					</template>
				</template>
			</Descriptions>
		</div>
	</Drawer>
</template>

<style lang="scss" scoped></style>

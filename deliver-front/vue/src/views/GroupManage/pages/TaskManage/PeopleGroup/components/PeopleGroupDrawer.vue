<script lang="ts" setup>
import { savePeopleGroup, updatePeopleGroup } from '@/api/peopleGroup';
import { peopleGroupLocale, peopleGroupForm, setPeopleGroupListTip } from '@/config/peopleGroup';
import { DrawerProps } from '@/types/components';
import { getDataFromSchema } from '@/utils/utils';
import { message } from 'ant-design-vue';
import { ref, reactive, watch, nextTick } from 'vue';

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
	newProps.operation === 'edit' && newProps.open && initFormDate();
	newProps.operation === 'more' && newProps.open && initMoreDate();
})
const initFormDate = () => {
	nextTick(() => {
		for (const key in peopleGroupForm) {
			if (key === '$radioGroup') peopleGroupForm[key].value = 1
			else peopleGroupForm[key].value = props.record[key]
		}
		peopleGroupForm.$excelTemplateFile.type = 'none';
		peopleGroupForm.peopleGroupList.type = 'textarea';
		setPeopleGroupListTip(peopleGroupForm.peopleGroupList.value)
	})
}
const groups = reactive<any>([])
const initMoreDate = () => {
	const obj = {
		peopleGroupInfo: ['peopleGroupName', 'peopleGroupDescription', 'peopleGroupList', 'createUser', 'createTime'],
		typeInfo: ['usersTypeName'],
	}
	for (const key in obj) {
		let group: any = {
			config: {
				title: ''
			},
			data: []
		}
		if (key === 'peopleGroupInfo') {
			group.config.title = '人群信息'
		} else if (key === 'typeInfo') {
			group.config.title = '类型信息'
		}
		obj[key].forEach((name: string) => {
			group.data.push({
				label: peopleGroupLocale[name],
				value: props.record[name]
			})
		});
		groups.push(group)
	}
}

const operationDispatch = {
	add: async () => {
		await formRef.value.validate()
		await savePeopleGroup(getDataFromSchema(peopleGroupForm))
		message.success('新增成功')
		handleCancel(true)
	},
	edit: async () => {
		await formRef.value.validate()
		await updatePeopleGroup(getDataFromSchema(peopleGroupForm))
		message.success('编辑成功')
		handleCancel(true)
	}
}

const handleCancel = (flash: boolean = false) => {
	props.operation !== 'more' && formRef.value.resetFields()
	drawerState.open = false
	groups.length = 0
	emit('close', flash)
}

</script>

<template>
	<Drawer v-bind="drawerState" @ok="operationDispatch[operation]" @close="handleCancel">
		<Form ref="formRef" v-if="operation === 'add' || operation === 'edit'" :label-col="{ span: 4 }"
			:form-schema="peopleGroupForm" />
		<div v-else-if="operation === 'more'">
			<Descriptions :groups="groups">
				<template #value="{ item }">
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

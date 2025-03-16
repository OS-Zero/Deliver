<script lang="ts" setup>
import { savePeopleGroup, updatePeopleGroup } from '@/api/peopleGroup';
import { peopleGroupFormSchema } from '@/config/peopleGroup';
import { message } from 'ant-design-vue';
import { computed } from 'vue';
import PeopleGroupDescriptions from './PeopleGroupDescriptions.vue';
import { PeopleGroup } from '@/types/peopleGroup';
import { useForm } from '@/hooks/form';

type Operation = 'add' | 'edit' | 'more'
const props = defineProps<{
	open: boolean
	operation: Operation
	record: PeopleGroup
}>()
const emit = defineEmits(['close'])

const titleList: Record<Operation, string> = {
	add: '新增人群',
	edit: '编辑人群',
	more: '人群详情',
}
const { formRef, formData: peopleGroupForm } = useForm(peopleGroupFormSchema)
const drawerState = computed(() => ({
	open: props.open,
	title: titleList[props.operation],
	placement: props.operation === 'more' ? 'left' : 'right',
	extra: props.operation === 'more' ? false : true,
}))

const operationDispatch = {
	add: async () => {
		await formRef.value?.validate()
		await savePeopleGroup(peopleGroupForm.value)
		message.success('新增成功')
		handleCancel(true)
	},
	edit: async () => {
		await formRef.value?.validate()
		await updatePeopleGroup(peopleGroupForm.value)
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
		<Form ref="formRef" v-if="operation === 'add' || operation === 'edit'" :label-col="{ span: 4 }"
			:form-schema="peopleGroupFormSchema" :record="record" />
		<PeopleGroupDescriptions :record="record" v-else-if="operation === 'more'"></PeopleGroupDescriptions>
	</Drawer>
</template>

<style lang="scss" scoped></style>

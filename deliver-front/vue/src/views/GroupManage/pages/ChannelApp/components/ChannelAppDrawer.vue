<script lang="ts" setup>
import { DrawerProps } from '@/types/components';
import { dynamic, getDataFromSchema } from '@/utils/utils';
import { message } from 'ant-design-vue';
import { ref, reactive, onUnmounted, watch, nextTick } from 'vue';
import { channelAppLocale, channelAppSchema, channelAppSchemaDeps } from '@/config/channelApp';
import { saveChannelApp, updateChannelApp } from '@/api/channelApp';
type Operation = 'add' | 'edit' | 'more'
const props = defineProps<{
	open: boolean
	operation: Operation
	record: Record<string, any>
}>()
const emit = defineEmits(['close'])
const formRef = ref()

const titleList: Record<Operation, string> = {
	add: '新增应用',
	edit: '编辑应用',
	more: '应用详情',
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

const { dynamicData: channelAppForm, stop } = dynamic(channelAppSchema, channelAppSchemaDeps)
const initFormDate = () => {
	nextTick(() => {
		for (const key in channelAppForm) {
			channelAppForm[key].value = props.record[key]
		}
	})
}
const initMoreDate = () => {
	const set = new Set(['usersType', 'channelType', 'channelProviderType', 'appId'])
	const arr: Array<{ label: string; value: any }> = []
	for (const key in props.record) {
		if (!set.has(key)) {
			arr.push({
				label: channelAppLocale[key],
				value: props.record[key]
			})
		}
	}
	Object.assign(moreInfo, arr)
}
const moreInfo = reactive<Array<{ label: string; value: any }>>([])
const operationDispatch = {
	add: async () => {
		await saveChannelApp(getDataFromSchema(channelAppForm))
		message.success('新增成功')
		handleCancel()
	},
	edit: async () => {
		await updateChannelApp(getDataFromSchema(channelAppForm))
		message.success('编辑成功')
		handleCancel()
	},
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
		<Form ref="formRef" v-if="operation === 'add' || operation === 'edit'" :form-schema="channelAppForm" />
		<div v-else-if="operation === 'more'">
			<Descriptions :data="moreInfo" :config="{ column: 1 }">
			</Descriptions>
		</div>
	</Drawer>
</template>

<style lang="scss" scoped></style>

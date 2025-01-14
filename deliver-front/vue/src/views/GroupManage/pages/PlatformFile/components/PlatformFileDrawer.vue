<script lang="ts" setup>
import { DrawerProps } from '@/types/components';
import { getDataFromSchema } from '@/utils/utils';
import { message } from 'ant-design-vue';
import { ref, reactive, watch, onBeforeMount } from 'vue';
import { platformFileLocale, platformFileForm, setOptionsDispatch } from '@/config/platformFile';
import { updateChannelApp } from '@/api/channelApp';
type Operation = 'upload' | 'more'
const props = defineProps<{
	open: boolean
	operation: Operation
	record: Record<string, any>
}>()
const emit = defineEmits(['close'])
const formRef = ref()

const titleList: Record<Operation, string> = {
	upload: '上传文件',
	more: '文件详情'
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
	newProps.operation === 'more' && initMoreDate();
})

const initMoreDate = () => {
	const set = new Set(['channelType', 'appId', 'platformFileType'])
	const arr: Array<{ label: string; value: any }> = []
	for (const key in props.record) {
		if (!set.has(key)) {
			arr.push({
				label: platformFileLocale[key],
				value: props.record[key]
			})
		}
	}
	Object.assign(moreInfo, arr)
}
const moreInfo = reactive<Array<{ label: string; value: any }>>([])
const operationDispatch = {
	upload: async () => {
		await formRef.value.validate()
		await updateChannelApp(getDataFromSchema(platformFileForm))
		message.success('上传成功')
		handleCancel()
	},
}

const handleCancel = () => {
	props.operation !== 'more' && formRef.value.resetFields()
	drawerState.open = false
	emit('close')
}
onBeforeMount(() => {
	setOptionsDispatch['channelType']()
})
</script>

<template>
	<Drawer v-bind="drawerState" @ok="operationDispatch[operation]" @close="handleCancel">
		<Form ref="formRef" v-if="operation === 'upload'" :form-schema="platformFileForm" />
		<div v-else-if="operation === 'more'">
			<Descriptions :data="moreInfo" :config="{ column: 1 }">
			</Descriptions>
		</div>
	</Drawer>
</template>

<style lang="scss" scoped></style>

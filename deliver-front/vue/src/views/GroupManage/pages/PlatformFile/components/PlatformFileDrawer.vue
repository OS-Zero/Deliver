<script lang="ts" setup>
import { DrawerProps } from '@/types/components';
import { getDataFromSchema } from '@/utils/utils';
import { message } from 'ant-design-vue';
import { ref, reactive, watch, onBeforeMount } from 'vue';
import { platformFileLocale, platformFileForm, setOptionsDispatch } from '@/config/platformFile';
import { uploadPlatformFile } from '@/api/platformFile';
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
	newProps.operation === 'more' && newProps.open && initMoreDate();
})
const groups = reactive<any>([])
const initMoreDate = () => {
	const obj = {
		platformFileInfo: ['platformFileName', 'platformFileDescription', 'platformFileTypeName', 'platformFileKey', 'platformFileStatus', 'createUser', 'createTime'],
		typeInfo: ['channelTypeName'],
		linkInfo: ['appName']
	}
	for (const key in obj) {
		let group: any = {
			config: {
				title: ''
			},
			data: []
		}
		if (key === 'platformFileInfo') {
			group.config.title = '文件信息'
		} else if (key === 'typeInfo') {
			group.config.title = '类型信息'
		} else if (key === 'linkInfo') {
			group.config.title = '关联信息'
		}
		obj[key].forEach((name: string) => {
			group.data.push({
				label: platformFileLocale[name],
				value: props.record[name]
			})
		});
		groups.push(group)
	}
}
const operationDispatch = {
	upload: async () => {
		await formRef.value.validate()
		await uploadPlatformFile(getDataFromSchema(platformFileForm))
		message.success('上传成功')
		handleCancel(true)
	},
}

const handleCancel = (flash: boolean = false) => {
	props.operation !== 'more' && formRef.value.resetFields()
	drawerState.open = false
	emit('close', flash)
}
onBeforeMount(() => {
	setOptionsDispatch['channelType']()
})
</script>

<template>
	<Drawer v-bind="drawerState" @ok="operationDispatch[operation]" @close="handleCancel">
		<Form ref="formRef" v-if="operation === 'upload'" :form-schema="platformFileForm" />
		<div v-else-if="operation === 'more'">
			<Descriptions :groups="groups">
				<template #value="{ item }">
					<template v-if="item.label === '文件状态'">
						{{ item.value ? '开启' : '关闭' }}
						<Status :success="item.value"></Status>
					</template>
				</template>
			</Descriptions>
		</div>
	</Drawer>
</template>

<style lang="scss" scoped></style>

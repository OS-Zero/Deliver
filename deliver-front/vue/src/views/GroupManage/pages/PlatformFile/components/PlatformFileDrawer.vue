<script lang="ts" setup>
import { message } from 'ant-design-vue';
import { computed } from 'vue';
import { platformFileFormSchema } from '@/config/platformFile';
import { uploadPlatformFile } from '@/api/platformFile';
import { useForm } from '@/hooks/form';
import PlatformFileDescriptions from './PlatformFileDescriptions.vue';
import { PlatformFile } from '@/types/platformFile';
type Operation = 'upload' | 'more'
const props = defineProps<{
	open: boolean
	operation: Operation
	record: PlatformFile
}>()
const emit = defineEmits(['close'])
const { formRef, formData: platformFileForm } = useForm(platformFileFormSchema)

const titleList: Record<Operation, string> = {
	upload: '上传文件',
	more: '文件详情'
}

const drawerState = computed(() => ({
	open: props.open,
	title: titleList[props.operation],
	placement: props.operation === 'more' ? 'left' : 'right',
	extra: props.operation === 'more' ? false : true,
}))
const operationDispatch = {
	upload: async () => {
		await formRef.value?.validate()
		await uploadPlatformFile(platformFileForm.value)
		message.success('上传成功')
		handleCancel(true)
	},
}

const handleCancel = (flash: boolean = false) => {
	emit('close', flash)
}
</script>

<template>
	<Drawer v-bind="drawerState" @ok="operationDispatch[operation]" @close="handleCancel">
		<Form ref="formRef" v-if="operation === 'upload'" :form-schema="platformFileFormSchema" />
		<PlatformFileDescriptions :record="record" v-else-if="operation === 'more'"></PlatformFileDescriptions>
	</Drawer>
</template>

<style lang="scss" scoped></style>

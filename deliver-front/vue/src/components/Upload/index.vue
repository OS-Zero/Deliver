<script lang="ts" setup>
import { UploadFile, UploadProps } from 'ant-design-vue';
import { InboxOutlined } from '@ant-design/icons-vue';
import { ref, watch } from 'vue';
const props = defineProps<{
	modelValue: File | UploadFile<any>[]
	config?: UploadProps
}>()
const emit = defineEmits(['update:modelValue'])
const fileList = ref()
const handleChange = ({ file }) => {
	emit('update:modelValue', (props.config?.maxCount && props.config.maxCount === 1) ? file : [file])
}
const beforeUpload: UploadProps['beforeUpload'] = file => {
	fileList.value = [...(fileList.value || []), file];
	return false;
};
watch(props, () => {
	(!props.modelValue || (Array.isArray(props.modelValue) && !props.modelValue.length)) && (fileList.value = []);
}, {
	immediate: true,
})
</script>

<template>
	<a-upload-dragger v-model:fileList="fileList" v-bind="config" :before-upload="beforeUpload" @change="handleChange">
		<p class="ant-upload-drag-icon">
			<InboxOutlined />
		</p>
		<p class="ant-upload-text">{{ config?.name ? config.name : '点击或拖拽上传' }}</p>
	</a-upload-dragger>
</template>

<style lang="scss" scoped></style>

<script lang="ts" setup>
import { UploadFile, UploadProps } from 'ant-design-vue';
import { InboxOutlined } from '@ant-design/icons-vue';
import { ref, watch } from 'vue';
import { UploadConfig } from '@/types/form';

const props = defineProps<{
	modelValue: File | UploadFile<any>[]
	config?: UploadConfig
}>()
const emit = defineEmits(['update:modelValue', 'change'])
const fileList = ref()
const handleChange = ({ file }) => {
	emit('change', file)
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
		<p class="ant-upload-text">{{ config?.title ? config.title : '点击或拖拽上传' }}</p>
		<p class="ant-upload-hint">
			{{ config?.description }}
		</p>
	</a-upload-dragger>
</template>

<style lang="scss" scoped>
p {
	padding: 0 var(--spacing-xs);
}
</style>

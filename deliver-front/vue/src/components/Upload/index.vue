<script lang="ts" setup>
import { UploadProps } from 'ant-design-vue';
import { InboxOutlined } from '@ant-design/icons-vue';
import { ref } from 'vue';

defineProps<{
	modelValue: File
	config?: UploadProps
}>()
const emit = defineEmits(['update:modelValue'])
const fileList = ref()
const handleChange = ({ file }) => {
	emit('update:modelValue', file)
}
const beforeUpload: UploadProps['beforeUpload'] = file => {
	fileList.value = [...(fileList.value || []), file];
	return false;
};

</script>

<template>
	<a-upload-dragger v-model:fileList="fileList" v-bind="config" :before-upload="beforeUpload" @change="handleChange">
		<p class="ant-upload-drag-icon">
			<InboxOutlined />
		</p>
		<p class="ant-upload-text">点击或拖拽上传</p>
	</a-upload-dragger>
</template>

<style lang="scss" scoped></style>

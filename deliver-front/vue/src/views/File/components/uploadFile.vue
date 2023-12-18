<script setup lang="ts">
import { reactive, ref } from 'vue'
import { message, type DrawerProps, type FormInstance } from 'ant-design-vue'
import type { uploadFile } from '../type'
import type { Rule } from 'ant-design-vue/es/form'
import { uploadFileApi } from '@/api/platformFile'
import type { UploadProps } from 'ant-design-vue'
import { InboxOutlined } from '@ant-design/icons-vue'

const placement = ref<DrawerProps['placement']>('right')

const uploadFileTable = reactive<uploadFile>({
	fileName: '',
	appType: undefined,
	fileType: '',
	appId: undefined
})

const open = ref<boolean>(false)

const uploadFileForm = ref<FormInstance>()

const fileList = ref<UploadProps['fileList']>([])

const beforeUpload: UploadProps['beforeUpload'] = (file) => {
	// 获取上传的文件，仅单个文件
	fileList.value = [file]
	return false
}

const handleRemove = () => {
	fileList.value = []
}

const iconLoading = ref(false)

const showDrawer = (): void => {
	open.value = true
}

const onClose = (): void => {
	open.value = false
	clearForm()
}
// 新增自定义事件，当保存按钮被点击时，可以立即告知父组件
const emit = defineEmits(['mes'])

// 上传文件
const upload = (): void => {
	uploadFileForm.value
		?.validate() // 参数校验
		.then(() => {
			// 组装 formData
			const formData = new FormData()
			formData.append('fileName', String(uploadFileTable.fileName))
			formData.append('appType', String(uploadFileTable.appType))
			formData.append('fileType', String(uploadFileTable.fileType))
			formData.append('appId', String(uploadFileTable.appId))
			iconLoading.value = true
			if (fileList.value) {
				formData.append('platformFile', fileList.value[0].originFileObj as any)
			}
			uploadFileApi(formData)
				.then((res) => {
					if (res.code === 200) {
						void message.success('发送成功~ (*^▽^*)')
						onClose()
						emit('mes')
					}
					iconLoading.value = false
				})
				.catch(() => {})
		})
		.catch((error) => {
			console.log('error', error)
		})
}

// 清空表单
const clearForm = (): void => {
	uploadFileForm.value?.resetFields()
	fileList.value = []
}

// 表单规则校验
const validateFileList = async (): Promise<any> => {
	if (fileList.value) {
		if (fileList.value.length === 0) {
			throw new Error('请上传文件')
		}
	}
	await Promise.resolve()
}
const rules: Record<string, Rule[]> = {
	fileName: [
		{ required: true, message: '请输入文件名', trigger: 'change' },
		{ min: 3, max: 50, message: '长度在 3 到 50 个字符', trigger: 'blur' }
	],
	appType: [{ required: true, message: '请选择 APP 类型', trigger: 'change' }],
	fileType: [{ required: true, message: '请选择文件类型', trigger: 'change' }],
	appId: [{ required: true, message: '请输入 AppId', trigger: 'change' }],
	file: [{ required: true, validator: validateFileList, trigger: 'change' }]
}
// 表单对齐设置
const labelCol = { style: { width: '90px' } }
const wrapperCol = { span: 20 }
</script>

<template>
	<a-button type="primary" @click="showDrawer" style="margin-left: 20px">上传平台文件</a-button>
	<a-drawer title="上传平台文件" :placement="placement" :closable="true" :open="open" @close="onClose" :width="660">
		<a-form ref="uploadFileForm" :model="uploadFileTable" :rules="rules" :label-col="labelCol" :wrapper-col="wrapperCol">
			<a-form-item label="文件名" name="fileName" style="margin-left: 10px">
				<a-input show-count :maxlength="50" v-model:value="uploadFileTable.fileName" placeholder="请输入文件名"></a-input>
			</a-form-item>
			<a-form-item label="APP 类型" name="appType" style="margin-left: 10px">
				<a-select v-model:value="uploadFileTable.appType" placeholder="请选择 APP 类型" @change="uploadFileTable.fileType = undefined">
					<a-select-option value="1">钉钉</a-select-option>
					<a-select-option value="2">企业微信</a-select-option>
					<a-select-option value="3">飞书</a-select-option>
				</a-select>
			</a-form-item>
			<a-form-item label="文件类型" name="fileType" style="margin-left: 10px">
				<a-select v-model:value="uploadFileTable.fileType" placeholder="请选择文件类型">
					<a-select-option v-if="uploadFileTable.appType == 1 || uploadFileTable.appType == undefined" value="image">钉钉-图片</a-select-option>
					<a-select-option v-if="uploadFileTable.appType == 1 || uploadFileTable.appType == undefined" value="voice">钉钉-语音</a-select-option>
					<a-select-option v-if="uploadFileTable.appType == 1 || uploadFileTable.appType == undefined" value="video">钉钉-视频</a-select-option>
					<a-select-option v-if="uploadFileTable.appType == 1 || uploadFileTable.appType == undefined" value="file">钉钉-文件</a-select-option>
					<a-select-option v-if="uploadFileTable.appType == 2 || uploadFileTable.appType == undefined" value="image">企业微信-图片</a-select-option>
					<a-select-option v-if="uploadFileTable.appType == 2 || uploadFileTable.appType == undefined" value="voice">企业微信-语音</a-select-option>
					<a-select-option v-if="uploadFileTable.appType == 2 || uploadFileTable.appType == undefined" value="video">企业微信-视频</a-select-option>
					<a-select-option v-if="uploadFileTable.appType == 2 || uploadFileTable.appType == undefined" value="file">
						企业微信-普通文件
					</a-select-option>
					<a-select-option v-if="uploadFileTable.appType == 3 || uploadFileTable.appType == undefined" value="image">飞书-图片</a-select-option>
					<a-select-option v-if="uploadFileTable.appType == 3 || uploadFileTable.appType == undefined" value="opus">
						飞书-opus音频文件
					</a-select-option>
					<a-select-option v-if="uploadFileTable.appType == 3 || uploadFileTable.appType == undefined" value="mp4">飞书-mp4视频文件</a-select-option>
					<a-select-option v-if="uploadFileTable.appType == 3 || uploadFileTable.appType == undefined" value="pdf">飞书-pdf格式文件</a-select-option>
					<a-select-option v-if="uploadFileTable.appType == 3 || uploadFileTable.appType == undefined" value="doc">飞书-doc格式文件</a-select-option>
					<a-select-option v-if="uploadFileTable.appType == 3 || uploadFileTable.appType == undefined" value="xls">飞书-xls格式文件</a-select-option>
					<a-select-option v-if="uploadFileTable.appType == 3 || uploadFileTable.appType == undefined" value="ppt">飞书-ppt格式文件</a-select-option>
					<a-select-option v-if="uploadFileTable.appType == 3 || uploadFileTable.appType == undefined" value="stream">
						飞书-stream格式文件
					</a-select-option>
				</a-select>
			</a-form-item>
			<a-form-item label="关联 AppId" name="appId" style="margin-left: 10px">
				<a-input :maxlength="50" v-model:value="uploadFileTable.appId" placeholder="请输入关联 AppId"></a-input>
			</a-form-item>
			<a-form-item label="文件" name="file" style="margin-left: 10px">
				<a-upload-dragger :max-count="1" v-model:fileList="fileList" name="file" :before-upload="beforeUpload" @remove="handleRemove">
					<p class="ant-upload-drag-icon">
						<inbox-outlined></inbox-outlined>
					</p>
					<p class="ant-upload-text">单击此处选择文件上传</p>
				</a-upload-dragger>
			</a-form-item>
		</a-form>
		<template #extra>
			<a-button style="margin: 0 8px" @click="clearForm">清空</a-button>
			<a-button type="primary" @click="upload" :loading="iconLoading">上传</a-button>
		</template>
	</a-drawer>
</template>

<style scoped>
.btn-manager {
	margin-right: 10px;
}
</style>

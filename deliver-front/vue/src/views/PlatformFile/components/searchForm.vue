<script setup lang="ts">
import { ref, reactive } from 'vue'
import { DownOutlined, UpOutlined } from '@ant-design/icons-vue'
import locale from 'ant-design-vue/es/date-picker/locale/zh_CN'
import 'dayjs/locale/zh-cn'
import type { FormInstance } from 'ant-design-vue'
import type { Dayjs } from 'dayjs'
import { searchPlatformFile } from '@/views/File/type'

interface DelayLoading {
	delay: number
}

const expand = ref(false)

const formRef = ref<FormInstance>()

const searchPage = reactive<searchPlatformFile>({
	fileName: undefined,
	appType: undefined,
	fileType: undefined,
	fileKey: undefined,
	appId: undefined,
	currentPage: 1,
	pageSize: 10,
	startTime: undefined,
	endTime: undefined,
	perid: undefined,
})

const selectedRange = ref<Array<Dayjs | null>>([null, null])

const iconLoading = ref<boolean | DelayLoading>(false)

const clearForm = (): void => {
	selectedRange.value = [null, null]
	formRef.value?.resetFields()
	searchPage.startTime = undefined
	searchPage.endTime = undefined
	searchMes()
}

const onRangeChange = (value: [Dayjs, Dayjs] | [string, string], dateString: [string, string]): void => {
	if (Array.isArray(value)) {
		searchPage.startTime = dateString[0] + ' 00:00:00'
		searchPage.endTime = dateString[1] + ' 23:59:59'
	}
}

// 新增自定义事件，当查询按钮被点击时，可以立即告知父组件
const emit = defineEmits(['mes'])

const searchMes = (): void => {
	iconLoading.value = true
	emit('mes')
}

defineExpose({
	searchPage,
	iconLoading,
})
</script>

<template>
	<a-form ref="formRef" name="advanced_search" class="search" :model="searchPage">
		<a-row :gutter="24">
			<a-col :span="8">
				<a-form-item name="fileName" label="文件名">
					<a-input :maxlength="50" v-model:value="searchPage.fileName" placeholder="请输入文件名"></a-input>
				</a-form-item>
			</a-col>
			<a-col :span="8">
				<a-form-item name="appType" label="APP 类型">
					<a-select
						v-model:value="searchPage.appType"
						placeholder="请选择 APP 类型"
						@change="searchPage.fileType = undefined"
					>
						<a-select-option value="1">钉钉</a-select-option>
						<a-select-option value="2">企业微信</a-select-option>
						<a-select-option value="3">飞书</a-select-option>
					</a-select>
				</a-form-item>
			</a-col>
			<a-col :span="8" v-if="expand">
				<a-form-item name="fileType" label="文件类型">
					<a-select v-model:value="searchPage.fileType" placeholder="请选择文件类型">
						<a-select-option v-if="searchPage.appType == 1 || searchPage.appType == undefined" value="1-image"
							>钉钉-图片</a-select-option
						>
						<a-select-option v-if="searchPage.appType == 1 || searchPage.appType == undefined" value="1-voice"
							>钉钉-语音</a-select-option
						>
						<a-select-option v-if="searchPage.appType == 1 || searchPage.appType == undefined" value="1-video"
							>钉钉-视频</a-select-option
						>
						<a-select-option v-if="searchPage.appType == 1 || searchPage.appType == undefined" value="1-file"
							>钉钉-文件</a-select-option
						>
						<a-select-option v-if="searchPage.appType == 2 || searchPage.appType == undefined" value="2-image"
							>企业微信-图片</a-select-option
						>
						<a-select-option v-if="searchPage.appType == 2 || searchPage.appType == undefined" value="2-voice"
							>企业微信-语音</a-select-option
						>
						<a-select-option v-if="searchPage.appType == 2 || searchPage.appType == undefined" value="2-video"
							>企业微信-视频</a-select-option
						>
						<a-select-option v-if="searchPage.appType == 2 || searchPage.appType == undefined" value="2-file"
							>企业微信-普通文件</a-select-option
						>
						<a-select-option v-if="searchPage.appType == 3 || searchPage.appType == undefined" value="3-image"
							>飞书-图片</a-select-option
						>
						<a-select-option v-if="searchPage.appType == 3 || searchPage.appType == undefined" value="3-opus"
							>飞书-opus音频文件</a-select-option
						>
						<a-select-option v-if="searchPage.appType == 3 || searchPage.appType == undefined" value="3-mp4"
							>飞书-mp4视频文件</a-select-option
						>
						<a-select-option v-if="searchPage.appType == 3 || searchPage.appType == undefined" value="3-pdf"
							>飞书-pdf格式文件</a-select-option
						>
						<a-select-option v-if="searchPage.appType == 3 || searchPage.appType == undefined" value="3-doc"
							>飞书-doc格式文件</a-select-option
						>
						<a-select-option v-if="searchPage.appType == 3 || searchPage.appType == undefined" value="3-xls"
							>飞书-xls格式文件</a-select-option
						>
						<a-select-option v-if="searchPage.appType == 3 || searchPage.appType == undefined" value="3-ppt"
							>飞书-ppt格式文件</a-select-option
						>
						<a-select-option v-if="searchPage.appType == 3 || searchPage.appType == undefined" value="3-stream"
							>飞书-stream格式文件</a-select-option
						>
					</a-select>
				</a-form-item>
			</a-col>
			<a-col :span="8" v-if="expand">
				<a-form-item name="templateName" label="FileKey">
					<a-input :maxlength="100" v-model:value="searchPage.fileKey" placeholder="请输入 FileKey"></a-input>
				</a-form-item>
			</a-col>
			<a-col :span="8" v-if="expand">
				<a-form-item name="appId" label="AppId">
					<a-input :maxlength="50" v-model:value="searchPage.appId" placeholder="请输入 AppId"></a-input>
				</a-form-item>
			</a-col>
			<a-col :span="16" v-if="expand">
				<a-form-item name="perid" label="创建时间">
					<a-range-picker
						:locale="locale"
						v-model:value="searchPage.perid"
						@change="onRangeChange"
						format="YYYY-MM-DD"
						:placeholder="['起始日期', '结束日期']"
					/>
				</a-form-item>
			</a-col>
			<a-col
				:span="8"
				:style="{
					'text-align': expand === true ? 'right' : 'center',
					'margin-bottom': expand === true ? '24px' : '0',
				}"
			>
				<a-button type="primary" html-type="submit" @click="searchMes" :loading="iconLoading">查询</a-button>
				<a-button style="margin: 0 8px" @click="clearForm">清空</a-button>
				<a style="font-size: 14px" @click="expand = !expand">
					<template v-if="expand">
						<UpOutlined />
						收起
					</template>
					<template v-else>
						<DownOutlined />
						展开
					</template>
				</a>
			</a-col>
		</a-row>
	</a-form>
</template>

<style scoped>
.search {
	padding: 24px 24px 0;
	background: #fff;
	border-radius: 6px;
}
</style>

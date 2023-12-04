<script setup lang="ts">
import { ref, watch } from 'vue'
import { DownOutlined, UpOutlined } from '@ant-design/icons-vue'
import locale from 'ant-design-vue/es/date-picker/locale/zh_CN'
import 'dayjs/locale/zh-cn'
import type { FormInstance } from 'ant-design-vue'
import type { Dayjs } from 'dayjs'
import Form from '@/types/form'

interface DelayLoading {
	delay: number
}
interface Props {
	fieldList: Form.FieldItem[]
	model?: Record<Form.FieldItem['field'], Form.FieldItem['value']>
	options?: Form.Options
}
interface EmitEvent {
	(e: 'submit', params: any): void
}

const iconLoading = ref<boolean | DelayLoading>(false)
const props = defineProps<Props>()
const emit = defineEmits<EmitEvent>()
const expand = ref(false)
const formRef = ref<FormInstance>()
const searchModel = ref<Record<string, any>>({})
watch(
	() => props.model,
	() => {
		if (props.model) {
			searchModel.value = props.model
		}
	},
	{ immediate: true }
)

/**
 * 时间选择器变动触发
 * @param value
 * @param dateString
 */
const onRangeChange = (_value: [Dayjs, Dayjs] | [string, string], dateString: [string, string]): void => {
	searchModel.value.startTime = dateString[0] + ' 00:00:00'
	searchModel.value.endTime = dateString[1] + ' 23:59:59'
}

/**
 * 提交搜索框
 */
const onSubmit = (): void => {
	if (!formRef.value) return
	console.log(searchModel.value)
	emit('submit', searchModel.value)
}

/**
 * 重置搜索框
 */
const resetForm = () => {
	if (!formRef.value) return
	formRef.value.resetFields()
}

defineExpose({
	// searchInfo,
	// iconLoading
})
</script>

<template>
	<a-form ref="formRef" name="search" class="search" :model="searchModel">
		<a-row :gutter="24">
			<template v-for="(item, index) in fieldList" :key="item.label">
				<a-col :span="8" v-if="item.type === 'input'">
					<a-form-item :name="item.field" :label="item.label">
						<a-input :maxlength="20" v-model:value="searchModel[item.field]" :placeholder="item.placeholder" />
					</a-form-item>
				</a-col>
				<a-col :span="8" v-if="(index < 2 || (expand && index > 1)) && item.type === 'select'">
					<a-form-item :name="item.field" :label="item.label">
						<a-select v-model:value="searchModel[item.field]" :placeholder="item.placeholder" :options="item.options" />
					</a-form-item>
				</a-col>
				<a-col :span="16" v-if="expand && item.type === 'timePicker'">
					<a-form-item :name="item.field" :label="item.label">
						<a-range-picker
							:locale="locale"
							v-model:value="searchModel[item.field]"
							@change="onRangeChange"
							:format="item.format"
							:placeholder="item.placeholder" />
					</a-form-item>
				</a-col>
			</template>
			<a-col
				:span="8"
				:style="{
					'text-align': expand === true ? 'right' : 'center',
					'margin-bottom': expand === true ? '24px' : '0'
				}">
				<a-button type="primary" html-type="submit" @click="onSubmit" :loading="iconLoading">查询</a-button>
				<a-button style="margin: 0 8px" @click="resetForm">重置</a-button>
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

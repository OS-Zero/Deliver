<script lang="ts" setup>
import { watch, ref } from 'vue'
import Form from '@/types/form'
import type { FormInstance } from 'ant-design-vue'
import JsonEditorVue from 'json-editor-vue3'
interface Props {
	config: Form.Feedback
	model: Record<Form.FieldItem['field'], Form.FieldItem['value']>
	_options: any
}
interface EmitEvent {
	(e: 'submit', params: any, callback: (err: boolean) => void): void
}
const props = defineProps<Props>()
const emit = defineEmits<EmitEvent>()
const drawerModel = ref<Record<string, any>>({})
const modeList = ref(['code'])
const cache = new Map()
watch(
	() => props.model,
	() => {
		drawerModel.value[props._options.rowKey] = props.model[props._options.rowKey]
		props.config.formData.forEach((item) => {
			if (item.type === 'cascader') {
				item.options.forEach((opt: any) => {
					drawerModel.value[opt.field] = props.model[opt.field]
				})
			} else {
				drawerModel.value[item.field] = props.model[item.field]
			}
		})
	},
	{ immediate: true }
)
const open = ref<boolean>(false)

const openModel = () => {
	if (props.config.cascader) {
		selectChange(props.config.cascader, props.config.cascader.length - 1)
	} else {
		props.config.requests?.forEach((item) => {
			const args: Array<any> = []
			if (item.params) {
				item.params.forEach((field: string) => {
					args.push(props.model[field])
				})
			}
			item.request(...args).then((res: any) => {
				drawerModel.value[item.field] = res
				cache.set(item.field, res)
			})
		})
	}
	open.value = true
}
const options = ref<Array<Array<Record<string, any>>>>([])
const selectChange = (opts: Array<Record<string, any>> | undefined, index: number) => {
	if (!opts) return []
	const res: Array<number> = []
	const len = opts.length
	opts.forEach((opt: Record<string, any>, idx: number) => {
		if (idx <= index) {
			res.push(drawerModel.value[opt.field])
			if (len - 1 === idx) lastNumber.value = drawerModel.value[opt.field]
		} else {
			drawerModel.value[opt.field] = undefined
			lastNumber.value = undefined
			res.push(-1)
		}
	})
	options.value = props.config.filter(res)
}
const lastNumber = ref<number | undefined>(undefined)
const requestOptions = ref<Record<string, any>>({})

/**
 * 选中最后开始请求
 */
watch(lastNumber, () => {
	props.config.requests?.forEach((item: Record<string, any>) => {
		if (lastNumber.value === undefined) {
			requestOptions.value[item.field] = []
			drawerModel.value[item.field] = undefined
		} else {
			item.request(lastNumber.value as number).then((res: Array<Record<string, any>>) => {
				requestOptions.value[item.field] = res
			})
		}
	})
})

const formRef = ref<FormInstance>()
/**
 * 重置对话框
 */
const resetForm = () => {
	if (!formRef.value) return
	formRef.value.resetFields()
	if (cache.size) {
		props.config.requests?.forEach((item) => {
			drawerModel.value[item.field] = cache.get(item.field)
		})
	}
}
/**
 * 提交表单
 */
const submit = () => {
	formRef.value
		.validate()
		.then(() => {
			const callback = (err: boolean) => {
				if (err) return
				resetForm()
				open.value = false
			}
			emit('submit', drawerModel.value, callback)
		})
		.catch((err: any) => {
			console.log('errot', err)
		})
}
</script>
<template>
	<span class="center-modal">
		<slot name="button" :openModel="openModel">
			<a-button @click="openModel" type="primary">{{ config.title }}</a-button>
		</slot>
		<a-drawer v-model:open="open" :title="config.title" width="660px" :footer="null" @close="resetForm">
			<a-form
				ref="formRef"
				:model="drawerModel"
				:rules="config.rules"
				:label-col="{ style: { width: '90px' } }"
				:wrapper-col="{ span: 20 }"
				class="centerForm">
				<template v-for="item in config.formData" :key="item.field">
					<a-form-item :label="item.label" :name="item.field" class="center-item" v-if="item.type === 'input'">
						<a-input :maxlength="20" v-model:value="drawerModel[item.field]" :placeholder="item.placeholder" />
					</a-form-item>
					<a-form-item :label="item.label" :name="item.field" class="center-item" v-if="item.type === 'inputNumber'">
						<a-input-number v-model:value="drawerModel[item.field]" :min="0" :max="3" />
					</a-form-item>
					<a-form-item :label="item.label" :name="item.field" class="center-item" v-else-if="item.type === 'radio'">
						<a-radio-group v-model:value="drawerModel[item.field]" button-style="solid">
							<a-radio-button :value="radio.value" v-for="radio in item.options" :key="radio.value">{{ item.label }}</a-radio-button>
						</a-radio-group>
					</a-form-item>
					<a-form-item :label="item.label" :name="item.field" class="center-item" v-else-if="item.type === 'select'">
						<a-select
							v-model:value="drawerModel[item.field]"
							:options="item.options === 'function' ? requestOptions[item.field] : item.options"
							:disabled="item.options === 'function' ? !requestOptions[item.field] || !requestOptions[item.field].length : !item.options.lengh" />
					</a-form-item>
					<a-form-item :label="item.label" :name="item.field" class="center-item" v-else-if="item.type === 'list'">
						<List v-model:value="drawerModel[item.field]" :options="item.options"></List>
					</a-form-item>
					<a-form-item :label="item.label" :name="item.field" class="center-item" v-else-if="item.type === 'jsonEditor'">
						<json-editor-vue v-model="drawerModel[item.field]" langua="zh" :mode="'text'" :modeList="modeList"></json-editor-vue>
					</a-form-item>
					<template v-else-if="item.type === 'cascader'">
						<a-form-item :label="option.label" :name="option.field" class="center-item" v-for="(option, index) in item.options" :key="option.field">
							<a-select
								v-model:value="drawerModel[option.field]"
								:options="index === 0 ? option.options : Array.isArray(options[index]) ? options[index] : []"
								:disabled="index !== 0 && (!Array.isArray(options[index]) || !options[index].length)"
								@change="selectChange(item.options, index)" />
						</a-form-item>
					</template>
					<a-form-item :label="item.label" :name="item.field" class="center-item" v-else-if="item.type === 'switch'">
						<a-switch
							v-model:checked="drawerModel[item.field]"
							checked-children="启用"
							un-checked-children="禁用"
							:checkedValue="1"
							:unCheckedValue="0" />
					</a-form-item>
				</template>
			</a-form>
			<template #extra>
				<a-button @click="resetForm">{{ _options.resetButtonText }}</a-button>
				<a-button style="margin-left: 10px" type="primary" @click="submit">{{ _options.submitButtonText }}</a-button>
			</template>
		</a-drawer>
	</span>
</template>
<style lang="scss" scoped></style>

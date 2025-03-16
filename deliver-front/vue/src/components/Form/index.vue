<script lang="ts" setup>
import { onBeforeUnmount, onMounted, ref } from 'vue';
import { FormItem } from '@/types/form';
import { FormInstance } from 'ant-design-vue/es/form';
import JsonEditor from "json-editor-vue3"
import { InboxOutlined } from '@ant-design/icons-vue';
import { cloneDeep, assign } from 'lodash'
import { Schema } from '@/types';
const props = withDefaults(defineProps<{
	formSchema: Record<string, FormItem<string>>;
	name?: (field: FormItem<string>) => string | number | (string | number)[];
	labelCol?: { span: number };
	layout?: 'horizontal' | 'vertical' | 'inline';
	record?: Record<string, any>
}>(), {
	name: (field: FormItem<string>) => [field.fieldName, 'value'],
	layout: 'vertical'
})
const formRef = ref<FormInstance>()

const expose = {
	validate: async () => { await formRef.value?.validate() },
	validateFields: async (...args: any) => {
		await formRef.value?.validateFields(...args)
	},
	resetFields: () => { assign(props.formSchema, cloneDeep(cloneSchema)) },
	clearValidate: () => { formRef.value?.clearValidate() }
}
defineExpose({
	...expose
})
let originSchema: Schema<any>
let cloneSchema: Schema<any>
onMounted(async () => {
	const { formSchema, record } = props
	//让所有表单项有value属性
	for (const key in formSchema) {
		formSchema[key].value = formSchema[key].value;
		const init = formSchema[key].init
		const watch = formSchema[key].watch
		typeof init === 'function' && !init.length && await init();
		typeof watch === 'function' && watch(expose);
	}
	originSchema = cloneDeep(formSchema)

	//数据回显
	if (record && Object.keys(record).length !== 0) {
		for (const key in formSchema) {
			formSchema[key].value = Reflect.has(record, key) ? record[key] : formSchema[key].value
			const init = formSchema[key].init
			typeof init === 'function' && init.length && await init(record, formRef);
		}
	}
	cloneSchema = cloneDeep(formSchema)
})
onBeforeUnmount(() => {
	//恢复原始数据
	assign(props.formSchema, cloneDeep(originSchema));
})
</script>

<template>
	<a-form ref="formRef" :label-col="labelCol" :model="formSchema" :layout="layout">
		<template v-for="field in formSchema">
			<a-form-item v-if="field.type === 'none'" :name="name(field)" v-show="false">
				<input v-model="formSchema[field.fieldName].value" />
			</a-form-item>
			<a-form-item v-else-if="field.type === 'button'">
				<a-button style="width: 100%;" v-bind="field.buttonConfig" @click="field.buttonConfig?.onClick">{{
					field.buttonConfig?.name
				}}</a-button>
			</a-form-item>
			<a-form-item v-else :label="field.label" :name="name(field)" :rules="field.rules">
				<template v-if="field.type === 'input'">
					<a-input v-model:value="formSchema[field.fieldName!].value" v-bind="field.inputConfig" />
					<p class="input-desc" v-html="field.customConfig?.tip"></p>
				</template>
				<template v-if="field.type === 'inputPassword'">
					<a-input-password v-model:value.trim="formSchema[field.fieldName!].value" v-bind="field.inputConfig" />
				</template>
				<template v-else-if="field.type === 'select'">
					<a-select v-model:value="formSchema[field.fieldName].value" v-bind="field.selectConfig" />
				</template>
				<template v-else-if="field.type === 'textarea'">
					<a-textarea v-model:value="formSchema[field.fieldName].value" v-bind="field.textareaConfig" />
					<p class="textarea-desc" v-html="field.customConfig?.tip"></p>
				</template>
				<template v-else-if="field.type === 'list'">
					<div class="list-item" v-for="(item, index) in formSchema[field.fieldName].value" :key="item.id">
						<a-form-item :name="[...name(field), index]" :rules="field.rules">
							<a-input v-model:value="formSchema[field.fieldName].value[index]" v-bind="field.inputConfig" />
						</a-form-item>
						<a-button v-bind="field.customConfig?.deleteConfig" @click="field.customConfig?.deleteConfig.click(item)">
						</a-button>
					</div>
					<a-form-item>
						<a-button style="width: 100%;" v-bind="field.customConfig?.addConfig"></a-button>
					</a-form-item>
				</template>
				<template v-else-if="field.type === 'jsonEditor'">
					<JsonEditor style="height: 400px;" v-model="formSchema[field.fieldName].value"
						v-bind="formSchema[field.fieldName].editorConfig">
					</JsonEditor>
				</template>
				<template v-else-if="field.type === 'datePicker'">
					<a-date-picker style="width: 100%;" v-model:value="formSchema[field.fieldName].value" show-time
						format="YYYY-MM-DD HH:mm:ss" value-format="YYYY-MM-DD HH:mm:ss" />
				</template>
				<template v-else-if="field.type === 'upload'">
					<a-upload-dragger v-model:fileList="formSchema[field.fieldName].value" v-bind="field.uploadConifg">
						<p class=" ant-upload-drag-icon">
							<component :is="field.customConfig?.icon || InboxOutlined"></component>
						</p>
						<p class="ant-upload-text">{{ field.customConfig?.title ? field.customConfig.title : '点击或拖拽上传' }}</p>
						<p class="ant-upload-hint">
							{{ field.customConfig?.description }}
						</p>
					</a-upload-dragger>
				</template>
				<template v-else-if="field.type === 'radioGroup'">
					<a-radio-group v-model:value="formSchema[field.fieldName].value" v-bind="field.radioGroupConfig" />
				</template>
				<template v-else-if="field.type === 'verifyCode'">
					<VerifyCode v-model:value="formSchema[field.fieldName].value" v-bind="field.verifyCodeConfig" />
				</template>
			</a-form-item>
		</template>
	</a-form>
</template>

<style lang="scss" scoped>
.ant-form-item {
	margin-bottom: var(--spacing-md);
}

.list-item {
	display: flex;
	align-items: top;
	margin-bottom: var(--spacing-md);

	.ant-form-item {
		flex: 1;
		margin: 0;
		margin-right: var(--spacing-xs);
	}
}

.verify {
	display: flex;

	.verify_btn {
		margin-left: var(--spacing-sm)
	}
}

.textarea-desc,
.input-desc {
	color: var(--gray-medium-dark);
	font-size: small;
}

.ant-upload-hint {
	padding: 0 var(--spacing-xs);
}
</style>

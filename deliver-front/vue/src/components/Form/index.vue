<script lang="ts" setup>
import { h, ref } from 'vue';
import { FormItem } from '@/types/form';
import { FormInstance } from 'ant-design-vue/es/form';
import { DeleteOutlined, PlusOutlined } from '@ant-design/icons-vue';
import JsonEditor from "json-editor-vue3"

withDefaults(defineProps<{
	formSchema: Record<string, FormItem<string>>;
	name?: (field: FormItem<string>) => string | number | (string | number)[];
	labelCol?: { span: number };
	layout?: 'horizontal' | 'vertical' | 'inline'
}>(), {
	name: (field: FormItem<string>) => [field.fieldName, 'value']
})
const formRef = ref<FormInstance>()
const validate = async () => { await formRef.value?.validate() }
const resetFields = () => { formRef.value?.resetFields() }
defineExpose({
	validate,
	resetFields
})
const listAdd = (list: string[]) => {
	list.push('')
}
const listRemove = (list: string[], id: string) => {
	const index = list.indexOf(id)
	index !== -1 && list.splice(index, 1)
}
</script>

<template>
	<a-form ref="formRef" :label-col="labelCol" :model="formSchema" :layout="layout">
		<template v-for="field in formSchema">
			<a-form-item v-if="field.type !== 'none'" :label="field.label" :name="name(field)" :rules="field.rules">
				<template v-if="field.type === 'input'">
					<a-input v-model:value="formSchema[field.fieldName].value" />
				</template>
				<template v-else-if="field.type === 'select'">
					<a-select v-model:value="formSchema[field.fieldName].value" :options="field.options"
						:disabled="!!!field.options?.length" />
				</template>
				<template v-else-if="field.type === 'textarea'">
					<a-textarea v-model:value="formSchema[field.fieldName].value" />
				</template>
				<template v-else-if="field.type === 'list'">
					<div class="list-item" v-for="(item, index) in formSchema[field.fieldName].value" :key="item.id">
						<a-form-item :name="[...name(field), index]" :rules="field.rules">
							<a-input v-model:value="formSchema[field.fieldName].value[index]" :placeholder="field.placeholder" />
						</a-form-item>
						<a-button :icon="h(DeleteOutlined)" danger type="text" shape="circle"
							@click="listRemove(formSchema[field.fieldName].value, item)" />
					</div>
					<a-form-item>
						<a-button :disabled="formSchema[field.fieldName].value.length >= field.max!" type="dashed" block
							@click="listAdd(formSchema[field.fieldName].value)">
							<PlusOutlined />
						</a-button>
					</a-form-item>
				</template>
				<template v-else-if="field.type === 'jsonEditor'">
					<JsonEditor v-model="formSchema[field.fieldName].value" v-bind="formSchema[field.fieldName].editorConfig">
					</JsonEditor>
				</template>
				<template v-else-if="field.type === 'datePicker'">
					<a-date-picker style="width: 100%;" v-model:value="formSchema[field.fieldName].value" show-time
						format="YYYY-MM-DD HH:mm:ss" value-format="YYYY-MM-DD HH:mm:ss" />
				</template>
				<template v-else-if="field.type === 'upload'">
					<Upload v-model="formSchema[field.fieldName].value" :config="field.uploadConifg"></Upload>
				</template>
			</a-form-item>
		</template>

	</a-form>
</template>

<style lang="scss" scoped>
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
</style>

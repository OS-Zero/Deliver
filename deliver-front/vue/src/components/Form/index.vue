<script lang="ts" setup>
import { h, ref } from 'vue';
import { FormItem } from '@/types/form';
import { FormInstance } from 'ant-design-vue/es/form';
import { DeleteOutlined, PlusOutlined } from '@ant-design/icons-vue';
import JsonEditor from "json-editor-vue3"
import { useVerify } from '@/hooks/verify';

withDefaults(defineProps<{
	formSchema: Record<string, FormItem<string>>;
	name?: (field: FormItem<string>) => string | number | (string | number)[];
	labelCol?: { span: number };
	layout?: 'horizontal' | 'vertical' | 'inline'
}>(), {
	name: (field: FormItem<string>) => [field.fieldName, 'value'],
	layout: 'vertical'
})
const formRef = ref<FormInstance>()
const validate = async () => { await formRef.value?.validate() }
const resetFields = () => { formRef.value?.resetFields() }
const listAdd = (list: string[]) => {
	list.push('')
}
const listRemove = (list: string[], id: string) => {
	const index = list.indexOf(id)
	index !== -1 && list.splice(index, 1)
}

const { state, handleVarify } = useVerify()
defineExpose({
	validate,
	resetFields
})
</script>

<template>
	<a-form ref="formRef" :label-col="labelCol" :model="formSchema" :layout="layout">
		<template v-for="field in formSchema">
			<a-form-item v-if="field.type === 'none'" :name="name(field)" v-show="false">
				<a-input v-model:value="formSchema[field.fieldName].value" />
			</a-form-item>
			<a-form-item v-else-if="field.type === 'button'">
				<a-button style="width: 100%;" v-bind="field.buttonConfig" @click="field.buttonConfig?.onClick">{{
					field.buttonConfig?.name
				}}</a-button>
			</a-form-item>
			<a-form-item v-if="field.type !== 'none'" :label="field.label" :name="name(field)" :rules="field.rules">
				<template v-if="field.type === 'input'">
					<a-input v-model:value="formSchema[field.fieldName!].value" :placeholder="field.placeholder" />
				</template>
				<template v-if="field.type === 'inputPassword'">
					<a-input-password v-model:value.trim="formSchema[field.fieldName!].value" :placeholder="field.placeholder" />
				</template>
				<template v-else-if="field.type === 'select'">
					<a-select v-model:value="formSchema[field.fieldName].value" :options="field.options"
						:disabled="!!!field.options?.length" />
				</template>
				<template v-else-if="field.type === 'textarea'">
					<a-textarea v-model:value="formSchema[field.fieldName].value" v-bind="field.textareaConfig" />
					<p class="textarea-desc" v-html="field.textareaConfig?.description"></p>
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
				<template v-else-if="field.type === 'radioGroup'">
					<a-radio-group v-model:value="formSchema[field.fieldName].value" v-bind="field.radioGroupConfig" />
				</template>
				<template v-else-if="field.type === 'verificationCode'">
					<div class="verify">
						<a-input v-model:value.trim="formSchema[field.fieldName].value"
							:placeholder="(field.placeholder || '请输入验证码')" />
						<a-button class="verify_btn" :disabled="state.loading || field.buttonConfig?.disabled"
							@click="handleVarify(field.buttonConfig?.onClick)">
							{{ state.verifyContent }}
						</a-button>
					</div>
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

.verify {
	display: flex;

	.verify_btn {
		margin-left: var(--spacing-sm)
	}
}

.textarea-desc {
	color: var(--gray-medium-dark);
}
</style>

<script lang="ts" setup>
import { watch, ref } from 'vue'
import Form from '@/types/form'
interface Props {
	config: Form.Modal
	model: Record<Form.FieldItem['field'], Form.FieldItem['value']>
}
const props = defineProps<Props>()

const centerModel = ref<Record<string, any>>({})
watch(
	() => props.model,
	() => {
		if (props.model) {
			centerModel.value = props.model
		}
	},
	{ immediate: true }
)
const open = ref<boolean>(false)
const openModel = () => {
	open.value = true
}
</script>
<template>
	<span class="center-modal">
		<slot name="button" :openModel="openModel">
			<a-button @click="open = true" type="primary">{{ config.title }}</a-button>
		</slot>
		<a-modal v-model:open="open" :title="config.title" width="650px" :footer="null">
			<a-form ref="templateForm" :model="centerModel" :rules="config.rules" :label-col="{ span: 5 }" :wrapper-col="{ span: 20 }" class="centerForm">
				<template v-for="item in config.formData" :key="item.field">
					<a-form-item :label="item.label" :name="item.field" class="center-item" v-if="item.type === 'input'">
						<a-input :maxlength="20" v-model:value="centerModel[item.field]" :placeholder="item.placeholder" style="width: 70%" />
					</a-form-item>
					<a-form-item :label="item.label" :name="item.field" class="center-item" v-else-if="item.type === 'radio'">
						<a-radio-group v-model:value="centerModel[item.field]" button-style="solid">
							<a-radio-button :value="radio.value" v-for="radio in item.options" :key="radio.value">{{ item.label }}</a-radio-button>
						</a-radio-group>
					</a-form-item>
					<a-form-item :label="item.label" :name="item.field" class="center-item" v-else-if="item.type === 'select'">
						<a-select v-model:value="centerModel[item.field]" :options="item.options" style="width: 70%" />
					</a-form-item>
					<a-form-item :label="item.label" :name="item.field" class="center-item" v-else-if="item.type === 'switch'">
						<a-switch
							v-model:checked="centerModel[item.field]"
							checked-children="启用"
							un-checked-children="禁用"
							:checkedValue="1"
							:unCheckedValue="0" />
					</a-form-item>
				</template>
				<a-form-item :wrapper-col="{ span: 25, offset: 6 }" class="center-item">
					<a-button>重置</a-button>
					<a-button style="margin-left: 10px" type="primary">确认新建</a-button>
				</a-form-item>
			</a-form>
		</a-modal>
	</span>
</template>
<style lang="scss" scoped></style>

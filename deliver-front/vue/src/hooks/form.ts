import { Schema } from '@/types';
import { FormInstance } from 'ant-design-vue';
import { computed, ref } from 'vue';
function getDataFromSchema<T>(record: Schema<T>): T {
	const _obj: any = {};
	for (const key in record) {
		if (key[0] === '$') continue;
		if (record[key].type === 'upload') _obj[key] = record[key].value[0].originFileObj;
		else _obj[key] = record[key].value;
	}
	return _obj;
}
export function useForm<K extends Object>(formSchema: Schema<K>) {
	const formRef = ref<FormInstance>();
	const formData = computed(() => getDataFromSchema<K>(formSchema));
	return {
		formData,
		formRef,
	};
}

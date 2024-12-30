import { FormItem } from '@/types/form';
import { reactive, WatchEffect, watchEffect, WatchHandle } from 'vue';

export function omitProperty(obj: Object, key: string) {
	return JSON.parse(
		JSON.stringify(
			obj,
			Object.keys(obj).filter((e) => e !== key),
		),
	);
}

export async function copyToClipboard(text: string) {
	const clipboard = navigator.clipboard || {
		writeText: (text: string) => {
			const copyInput = document.createElement('input');
			copyInput.value = text;
			document.body.appendChild(copyInput);
			copyInput.select();
			document.execCommand('copy');
			document.body.removeChild(copyInput);
		},
	};
	await clipboard.writeText(text);
}

export function getDataFromSchema<T>(record: Record<keyof T, FormItem<keyof T>>): T {
	const _obj: any = {};
	for (const key in record) {
		_obj[key] = record[key].value;
	}
	return _obj as T;
}

export function dynamic<T extends object>(obj: T, effects: WatchEffect[]) {
	const dynamicData = reactive(obj);
	const stopHandle: WatchHandle[] = [];
	effects.forEach((effect) => {
		stopHandle.push(watchEffect(effect));
	});
	const stop = () => {
		stopHandle.forEach((handle) => handle());
	};
	return { dynamicData, stop };
}

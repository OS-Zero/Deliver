import type { Rule } from 'ant-design-vue/es/form';
export async function validateList(_rule: any, value: any) {
	if (value.length > 0) {
		return Promise.resolve();
	}
	return Promise.reject('请添加至少一个用户');
}

export function getRangeRule(min: number, max: number, message: string, trigger: 'blur' | 'change' = 'blur'): Rule[] {
	if (min === max) {
		return [{ len: min, message, trigger }];
	}
	return [
		{ min, message, trigger },
		{ max, message, trigger },
	];
}
export function getRequiredRule(message: string, trigger: 'blur' | 'change' = 'blur') {
	return {
		required: true,
		message,
		trigger,
	};
}

import type { Rule } from 'ant-design-vue/es/form';
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

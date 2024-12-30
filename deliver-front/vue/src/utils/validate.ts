import type { Rule } from 'ant-design-vue/es/form';
export async function validateList(_rule: any, value: any) {
	if (value.length > 0) {
		return Promise.resolve();
	}
	return Promise.reject('请添加至少一个用户');
}

export async function validateEmail(_r: any, value: string) {
	if (value === '') return Promise.reject('请输入邮箱');
	const regExp = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
	return regExp.test(value) ? Promise.resolve() : Promise.reject('邮箱格式错误');
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

import { validateEmail } from '@/utils/validate';
import type { Rule } from 'ant-design-vue/es/form';
type RuleType = 'userEmail' | 'userPassword' | 'userRealName' | 'verificationCode' | 'groupName' | 'groupDescription';
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
const rulesList: Record<RuleType, Rule[]> = {
	userEmail: [{ validator: validateEmail, trigger: 'blur' }],
	userPassword: [getRequiredRule('请输入密码'), ...getRangeRule(6, 16, '密码长度范围为6-16位')],
	userRealName: [getRequiredRule('请输入真实姓名'), ...getRangeRule(1, 10, '姓名长度范围为1-10位')],
	verificationCode: [getRequiredRule('请输入验证码'), ...getRangeRule(6, 6, '验证码长度为6位')],
	groupName: [getRequiredRule('请输入分组名'), ...getRangeRule(1, 10, '分组名范围为1-10位')],
	groupDescription: [getRequiredRule('请输入分组描述'), ...getRangeRule(1, 50, '分组描述范围为1-50位')],
};
export function getRules(opts: RuleType[]) {
	const res: Record<string, Rule[]> = {};
	opts.forEach((key) => (res[key] = rulesList[key]));
	return res;
}

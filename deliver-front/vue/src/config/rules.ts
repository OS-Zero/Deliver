import { validateEmail } from '@/utils/validate'
import type { Rule } from 'ant-design-vue/es/form'
type RuleType = 'userEmail' | 'userPassword' | 'userRealName' | 'verificationCode'
const rulesList: Record<RuleType, Rule[]> = {
	userEmail: [{ validator: validateEmail, trigger: 'blur' }],
	userPassword: [
		{ required: true, message: '请输入密码', trigger: 'blur' },
		{ min: 6, message: '密码长度范围为6-16位', trigger: 'blur' },
		{ max: 16, message: '密码长度范围为6-16位', trigger: 'blur' },
	],
	userRealName: [
		{ required: true, message: '请输入真实姓名', trigger: 'blur' },
		{ min: 1, message: '姓名长度范围为1-10位', trigger: 'blur' },
		{ max: 10, message: '姓名长度范围为1-10位', trigger: 'blur' },
	],
	verificationCode: [
		{ required: true, message: '请输入验证码', trigger: 'blur' },
		{ len: 6, message: '验证码长度为6位', trigger: 'blur' },
	],
}
export function getRules(opts: RuleType[]) {
	const res: Record<string, Rule[]> = {}
	opts.forEach((key) => (res[key] = rulesList[key]))
	return res
}

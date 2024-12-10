import request from '@/utils/request.ts'
import { UserInfo, RegisterInfo, ForgotInfo } from '@/views/Login/type'

export function login(data: UserInfo): Promise<{ token: string }> {
	return request({
		url: '/user/login',
		method: 'post',
		data,
	})
}
export function register(data: Omit<RegisterInfo, 'confirmPwd'>): Promise<never> {
	return request({
		url: '/user/register',
		method: 'post',
		data,
	})
}
export function forgotPwd(data: Omit<ForgotInfo, 'confirmPwd'>): Promise<never> {
	return request({
		url: '/user/forgetPassword',
		method: 'post',
		data,
	})
}

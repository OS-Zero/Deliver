export interface UserInfo {
	userEmail: string
	userPassword: string
}
export interface RegisterInfo extends UserInfo {
	confirmPwd: string
	userRealName: string
	verificationCode: string
}
export interface ForgotInfo extends UserInfo {
	confirmPwd: string
	verificationCode: string
}

export interface UserInfo {
	userEmail: string;
	userPassword?: string;
	userRealName?: string;
	userRole?: string;
}
export interface RegisterInfo extends UserInfo {
	confirmPwd: string;
	verificationCode: string;
}
export interface ForgotInfo extends UserInfo {
	confirmPwd: string;
	verificationCode: string;
}

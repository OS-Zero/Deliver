export interface LoginState {
  activeKey: string;
  autoLogin: boolean;
  showForgot: boolean;
}

export interface UserInfo {
  userEmail: string;
  userPassWord: string;
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

import request from '@/utils/request.ts';
import { UserInfo, RegisterInfo, ForgotInfo } from '@/views/Login/type.ts';

export function login(data: UserInfo): Promise<{ token: string }> {
  return request({
    url: '/user/login',
    method: 'post',
    data
  });
}

export function logout(): Promise<never> {
  return request({
    url: '/user/logout',
    method: 'post'
  });
}

export function register(data: Omit<RegisterInfo, 'confirmPwd'>): Promise<never> {
  return request({
    url: '/user/register',
    method: 'post',
    data
  });
}

export function forgotPwd(data: Omit<ForgotInfo, 'confirmPwd'>): Promise<never> {
  return request({
    url: '/user/forgetPassWord',
    method: 'post',
    data
  });
}

export function updatePwd(
  data: Omit<RegisterInfo, 'confirmPwd' | 'userEmail' | 'userRealName'>
): Promise<never> {
  return request({
    url: '/user/updatePassWord',
    method: 'post',
    data
  });
}

export function getCurrentLoginUserInfo(): Promise<UserInfo> {
  return request({
    url: '/user/getCurrentLoginUserInfo',
    method: 'post'
  });
}

export function getVerificationCode(data: { userEmail: string }): Promise<never> {
  return request({
    url: '/user/getVerificationCode',
    method: 'post',
    data
  });
}

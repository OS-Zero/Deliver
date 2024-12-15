import { Rule } from 'antd/es/form';

const EMAIL_REGEX = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;

export const LOGIN_TABS = {
  LOGIN: 'login',
  REGISTER: 'register'
};

export const validateEmail = async (_: any, value: string) => {
  if (!value) {
    return Promise.reject(new Error('请输入邮箱'));
  }
  if (!EMAIL_REGEX.test(value)) {
    return Promise.reject(new Error('邮箱格式错误'));
  }
  return Promise.resolve();
};

export const emailValidationRule: Rule = {
  validator: validateEmail
};

export const passwordValidationRule: Rule = {
  validator: async (_: any, value: string) => {
    if (!value) {
      return Promise.reject(new Error('请输入密码'));
    }
    if (value.length < 6 || value.length > 16) {
      return Promise.reject(new Error('密码长度必须在6-16位之间'));
    }
    return Promise.resolve();
  }
};

export const verificationCodeValidationRule: Rule = {
  validator: async (_: any, value: string) => {
    if (!value) {
      return Promise.reject(new Error('请输入验证码'));
    }
    if (value.length !== 6) {
      return Promise.reject(new Error('验证码必须是6位数字'));
    }
    return Promise.resolve();
  }
};

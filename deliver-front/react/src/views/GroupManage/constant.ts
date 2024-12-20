import { Rule } from 'antd/es/form';

export const groupDescriptionRule: Rule = {
  validator: async (_: any, value: string) => {
    if (!value) {
      return Promise.reject(new Error('请输入分组描述'));
    }
    if (value.length < 1 || value.length > 50) {
      return Promise.reject(new Error('分组描述范围为1-50位'));
    }
    return Promise.resolve();
  }
};

export const groupNameRule: Rule = {
  validator: async (_: any, value: string) => {
    if (!value) {
      return Promise.reject(new Error('请输入分组名'));
    }
    if (value.length !== 6) {
      return Promise.reject(new Error('分组名范围为1-10位'));
    }
    return Promise.resolve();
  }
};

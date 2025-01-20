import { Rule } from 'antd/es/form';

export const groupDescriptionRule: Rule = {
  validator: async (_: any, value: string) => {
    if (!value) {
      return Promise.reject(new Error('请输入分组描述'));
    }
    return Promise.resolve();
  }
};

export const groupNameRule: Rule = {
  validator: async (_: any, value: string) => {
    if (!value) {
      return Promise.reject(new Error('请输入分组名'));
    }
    return Promise.resolve();
  }
};

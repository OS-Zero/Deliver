import axios from 'axios';
import { message } from 'antd';
const authorizationBlackList = ['/user/login', '/user/register', '/user/forgetPassword'];
const service = axios.create({
  baseURL: '/backend',
  timeout: 20000
});

service.interceptors.request.use(
  (config) => {
    config.headers['FrontPlatform'] = 'React';
    config.headers['Language'] = 'zh_CN';
    config.headers['Authorization'] = localStorage.getItem('access_token');
    config.headers['GroupId'] = localStorage.getItem('group_id') || -1;
    if (!authorizationBlackList.includes(config.url || '')) {
      config.headers['Authorization'] = localStorage.getItem('access_token');
    }
    return config;
  },
  (err) => {
    message.error(err);
    return Promise.reject(err);
  }
);

service.interceptors.response.use(
  (res) => {
    if (res.status === 200) {
      const { code, data, errorMessage } = res.data;
      if (code === 1) {
        message.error(errorMessage);
        return Promise.reject(errorMessage);
      }
      if (code === 0) return data;
    } else if (res.status === 302) {
      return (window.location.href = res.headers['location']);
    }
    message.error('服务端错误');
    return Promise.reject('服务端错误');
  },
  (err) => {
    message.error(err);
    return Promise.reject(err);
  }
);
export default service;

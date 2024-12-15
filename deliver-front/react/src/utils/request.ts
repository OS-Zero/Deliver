import axios from 'axios';
import { message } from 'antd';
const service = axios.create({
  baseURL: '/backend',
  timeout: 20000
});

service.interceptors.request.use(
  (config) => {
    config.headers['FrontPlatform'] = 'react';
    config.headers['Authorization'] = 'Bearer ' + localStorage.getItem('access_token');
    return config;
  },
  (err) => {
    message.error(err);
    return Promise.reject(err);
  }
);

service.interceptors.response.use(
  (res) => {
    const { code, data, errorMessage } = res.data;
    if (code === 600) {
      message.error(errorMessage);
      return Promise.reject(errorMessage);
    }
    if (code === 200) return data;
    message.error('请求错误');
    return Promise.reject('请求错误');
  },
  (err) => {
    message.error(err);
    return Promise.reject(err);
  }
);
export default service;

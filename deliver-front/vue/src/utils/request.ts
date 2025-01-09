import axios from 'axios';
import { message } from 'ant-design-vue';
const authorizationBlackList = ['/user/login', '/user/register', '/user/forgetPassword'];
const service = axios.create({
	baseURL: '/backend',
	timeout: 20000,
});

service.interceptors.request.use(
	(config) => {
		config.headers['FrontPlatform'] = 'vue';
		config.headers['Language'] = 'zh_CN';
		config.headers['GroupId'] = localStorage.getItem('group_id') || -1;
		!authorizationBlackList.includes(config.url || '') && (config.headers['Authorization'] = localStorage.getItem('access_token'));
		return config;
	},
	(err) => {
		message.error(err);
		return Promise.reject(err);
	},
);

service.interceptors.response.use(
	(res) => {
		if (res.status === 200) {
			const _res = res.data;
			if (_res.code === 0) return _res.data;
			if (_res.code === 1) {
				message.error(_res.errorMessage);
				return Promise.reject(_res.errorMessage);
			}
			message.error('服务端错误');
			return Promise.reject('服务端错误');
		} else if (res.status === 302) {
			return (window.location.href = res.headers['location']);
		}
		message.error('服务端错误');
		return Promise.reject('服务端错误');
	},
	(err) => {
		return Promise.reject(err);
	},
);
export default service;

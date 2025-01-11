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
			const contentType = res.headers['content-type']; // 获取返回的 Content-Type
			const _res = res.data;
			if (contentType && contentType.includes('application/json')) {
				// 如果是 JSON 响应，按照 code 进行处理
				if (_res.code === 0) return _res.data;
				if (_res.code === 1) {
					message.error(_res.errorMessage);
					return Promise.reject(_res.errorMessage);
				}
				message.error('服务端错误');
				return Promise.reject('服务端错误');
			}
			if (contentType && contentType.includes('application/vnd.openxmlformats-officedocument.spreadsheetml.sheet')) {
				// 如果是 Excel 文件（application/vnd.openxmlformats-officedocument.spreadsheetml.sheet），启动下载
				// 尝试从 Content-Disposition 中提取文件名
				let fileName = 'downloaded-file.xlsx';  // 默认文件名
				// 创建 Blob 对象并下载
				const blob = new Blob([_res], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' });
				const link = document.createElement('a');
				link.href = URL.createObjectURL(blob);
				link.download = fileName;
				link.click();
				return;
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

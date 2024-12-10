import axios from 'axios'
import { message } from 'ant-design-vue'
const service = axios.create({
	baseURL: '/backend',
	timeout: 20000,
})

service.interceptors.request.use(
	(config) => {
		config.headers['FrontPlatform'] = 'vue'
		config.headers['Authorization'] = 'Bearer ' + localStorage.getItem('access_token')
		return config
	},
	(err) => {
		message.error(err)
		return Promise.reject(err)
	},
)

service.interceptors.response.use(
	(res) => {
		const _res = res.data
		if (_res?.data.code === 600) {
			message.error(_res?.data.errorMessage)
			return Promise.reject(_res?.data.errorMessage)
		}
		if (res?.data.code === 200) return _res.data
		message.error('请求错误')
		return Promise.reject('请求错误')
	},
	(err) => {
		message.error(err)
		return Promise.reject(err)
	},
)
export default service

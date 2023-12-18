import { message } from 'ant-design-vue'
import axios from 'axios'

const service = axios.create({
	baseURL: '/backend',
	timeout: 5000
})

service.interceptors.request.use(
	(config) => {
		return config
	},
	async (err) => {
		return await Promise.reject(err)
	}
)

service.interceptors.response.use(
	async (res) => {
		if (res?.data.code === 200) return res.data
		message.error(res?.data.errorMessage)
		return res.data
	},
	async (err) => {
		return await Promise.reject(err)
	}
)
export default service

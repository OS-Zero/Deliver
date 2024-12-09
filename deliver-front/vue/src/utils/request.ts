import axios from 'axios'

const service = axios.create({
	baseURL: '/backend',
	timeout: 20000,
})

service.interceptors.request.use(
	(config) => {
		return config
	},
	(err) => {
		return Promise.reject(err)
	},
)

service.interceptors.response.use(
	(res) => {
		if (res?.data.code === 600) return Promise.reject(res?.data.errorMessage)
		if (res?.data.code === 200) return res.data
		return Promise.reject('请求错误')
	},
	(err) => {
		return Promise.reject(err)
	},
)
export default service

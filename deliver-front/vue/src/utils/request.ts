import axios from 'axios'
import { message } from 'ant-design-vue'

const service = axios.create({
  baseURL: '/admin',
  timeout: 5000
})

service.interceptors.request.use(
  config => {
    return config
  },
  async err => {
    return await Promise.reject(err)
  }
)

service.interceptors.response.use(
  async res => {
    if (res?.data.code === 200) return res.data
    else {
      void message.error(`操作失败，${res?.data.errorMessage} (＞︿＜)`)
      return res?.data.errorMessage
    }
  },
  async err => {
    return await Promise.reject(err)
  }
)
export default service

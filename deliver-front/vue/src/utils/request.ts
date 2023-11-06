import axios from 'axios'

const service = axios.create({
  baseURL: '',
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
    if (res?.data.code === 200) return res
    else {
      return await Promise.reject(res?.data.errorMessage)
    }
  },
  async err => {
    return await Promise.reject(err)
  }
)
export default service

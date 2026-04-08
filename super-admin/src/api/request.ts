import axios from 'axios'
import type { AxiosInstance } from 'axios'
import { useAuthStore } from '@/stores/auth'

const request: AxiosInstance = axios.create({
  baseURL: '/api',
  timeout: 10000,
})

request.interceptors.request.use((config) => {
  const auth = useAuthStore()
  if (auth.token) {
    config.headers.Authorization = `Bearer ${auth.token}`
  }
  return config
})

request.interceptors.response.use(
  (res) => res.data,
  (err) => {
    if (err.response?.status === 401) {
      const auth = useAuthStore()
      auth.logout()
      window.location.href = '/login'
    }
    return Promise.reject(err)
  },
)

export default request

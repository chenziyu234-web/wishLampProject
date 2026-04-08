import request from './request'

export function login(phone: string, password: string) {
  return request.post('/auth/login', { phone, password })
}

export function getMe() {
  return request.get('/auth/me')
}

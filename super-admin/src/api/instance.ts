import request from './request'

export function getInstances() {
  return request.get('/super/instances')
}

export function createInstance(data: { tenantId: number; productId: number; name: string; config?: string }) {
  return request.post('/super/instances', data)
}

export function getStats() {
  return request.get('/super/stats')
}

import request from './request'

export function getProducts() {
  return request.get('/super/products')
}

export function createProduct(data: { name: string; slug: string; description?: string; type: string; iconUrl?: string }) {
  return request.post('/super/products', data)
}

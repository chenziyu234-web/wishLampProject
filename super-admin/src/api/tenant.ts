import request from './request'

export function getTenants() {
  return request.get('/super/tenants')
}

export function getTenant(id: number) {
  return request.get(`/super/tenants/${id}`)
}

export function createTenant(data: { code: string; name: string; contactPhone?: string; contactName?: string }) {
  return request.post('/super/tenants', data)
}

export function updateTenant(id: number, data: { name?: string; contactPhone?: string; contactName?: string; status?: string }) {
  return request.put(`/super/tenants/${id}`, data)
}

export function createTenantAdmin(tenantId: number, data: { phone: string; password: string; nickname?: string }) {
  return request.post(`/super/tenants/${tenantId}/admin`, data)
}

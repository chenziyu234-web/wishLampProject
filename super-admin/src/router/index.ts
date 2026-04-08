import { createRouter, createWebHistory } from 'vue-router'
import { useAuthStore } from '@/stores/auth'

const router = createRouter({
  history: createWebHistory(),
  routes: [
    {
      path: '/login',
      name: 'Login',
      component: () => import('@/pages/LoginPage.vue'),
      meta: { requiresAuth: false },
    },
    {
      path: '/',
      component: () => import('@/components/Layout.vue'),
      meta: { requiresAuth: true },
      children: [
        { path: '', name: 'Dashboard', component: () => import('@/pages/DashboardPage.vue') },
        { path: 'tenants', name: 'Tenants', component: () => import('@/pages/TenantListPage.vue') },
        { path: 'tenants/:id', name: 'TenantDetail', component: () => import('@/pages/TenantDetailPage.vue') },
        { path: 'products', name: 'Products', component: () => import('@/pages/ProductListPage.vue') },
        { path: 'instances', name: 'Instances', component: () => import('@/pages/InstanceListPage.vue') },
      ],
    },
  ],
})

router.beforeEach((to) => {
  const auth = useAuthStore()
  if (to.meta.requiresAuth !== false && !auth.isLoggedIn) {
    return { name: 'Login' }
  }
  if (to.name === 'Login' && auth.isLoggedIn) {
    return { name: 'Dashboard' }
  }
})

export default router

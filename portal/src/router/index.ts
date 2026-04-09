import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory(),
  routes: [
    { path: '/', name: 'Home', component: () => import('@/pages/HomePage.vue') },
    { path: '/templates', name: 'Templates', component: () => import('@/pages/TemplatesPage.vue') },
    { path: '/templates/:slug', name: 'TemplateDetail', component: () => import('@/pages/TemplateDetailPage.vue') },
    { path: '/contact', name: 'Contact', component: () => import('@/pages/ContactPage.vue') },
    { path: '/activity/:instanceId', name: 'ActivityBlessing', component: () => import('@/pages/ActivityBlessingPage.vue') },
  ],
  scrollBehavior(_to, _from, savedPosition) {
    return savedPosition || { top: 0 }
  },
})

export default router

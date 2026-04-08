import { defineStore } from 'pinia'
import { ref, computed } from 'vue'

export const useAuthStore = defineStore('auth', () => {
  const token = ref(localStorage.getItem('super_admin_token') || '')
  const user = ref<any>(JSON.parse(localStorage.getItem('super_admin_user') || 'null'))
  const isLoggedIn = computed(() => !!token.value)

  function setAuth(t: string, u: any) {
    token.value = t
    user.value = u
    localStorage.setItem('super_admin_token', t)
    localStorage.setItem('super_admin_user', JSON.stringify(u))
  }

  function logout() {
    token.value = ''
    user.value = null
    localStorage.removeItem('super_admin_token')
    localStorage.removeItem('super_admin_user')
  }

  return { token, user, isLoggedIn, setAuth, logout }
})

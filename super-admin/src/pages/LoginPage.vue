<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { useAuthStore } from '@/stores/auth'
import { login } from '@/api/auth'
import { ElMessage } from 'element-plus'

const router = useRouter()
const auth = useAuthStore()
const form = ref({ phone: '', password: '' })
const loading = ref(false)

async function handleLogin() {
  if (!form.value.phone || !form.value.password) {
    ElMessage.warning('请输入手机号和密码')
    return
  }
  loading.value = true
  try {
    const res: any = await login(form.value.phone, form.value.password)
    if (res.success) {
      auth.setAuth(res.token, res.user)
      router.push('/')
      ElMessage.success('登录成功')
    } else {
      ElMessage.error(res.error || '登录失败')
    }
  } catch (e: any) {
    ElMessage.error(e.response?.data?.error || '登录失败')
  } finally {
    loading.value = false
  }
}
</script>

<template>
  <div class="min-h-screen flex items-center justify-center bg-gradient-to-br from-blue-500 to-purple-600">
    <el-card class="w-96" shadow="always">
      <template #header>
        <div class="text-center">
          <h2 class="text-xl font-bold text-gray-800">WishLamp 超管后台</h2>
          <p class="text-sm text-gray-500 mt-1">请使用超级管理员账号登录</p>
        </div>
      </template>
      <el-form @submit.prevent="handleLogin">
        <el-form-item>
          <el-input v-model="form.phone" placeholder="手机号" prefix-icon="Phone" size="large" />
        </el-form-item>
        <el-form-item>
          <el-input v-model="form.password" type="password" placeholder="密码" prefix-icon="Lock" size="large"
                    show-password @keyup.enter="handleLogin" />
        </el-form-item>
        <el-button type="primary" class="w-full" size="large" :loading="loading" @click="handleLogin">
          登 录
        </el-button>
      </el-form>
    </el-card>
  </div>
</template>

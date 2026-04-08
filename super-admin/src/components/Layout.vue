<script setup lang="ts">
import { ref, computed } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { useAuthStore } from '@/stores/auth'

const router = useRouter()
const route = useRoute()
const auth = useAuthStore()
const isCollapse = ref(false)

const activeMenu = computed(() => {
  const path = route.path
  if (path.startsWith('/tenants')) return '/tenants'
  if (path.startsWith('/products')) return '/products'
  if (path.startsWith('/instances')) return '/instances'
  return '/'
})

function handleLogout() {
  auth.logout()
  router.push('/login')
}
</script>

<template>
  <el-container class="h-screen">
    <el-aside :width="isCollapse ? '64px' : '220px'" class="transition-all duration-300">
      <div class="h-14 flex items-center justify-center border-b border-gray-200 bg-blue-600">
        <span v-if="!isCollapse" class="text-white text-lg font-bold">WishLamp 管理</span>
        <span v-else class="text-white text-lg font-bold">W</span>
      </div>
      <el-menu
        :default-active="activeMenu"
        :collapse="isCollapse"
        router
        class="border-r-0"
      >
        <el-menu-item index="/">
          <el-icon><DataAnalysis /></el-icon>
          <span>数据概览</span>
        </el-menu-item>
        <el-menu-item index="/tenants">
          <el-icon><OfficeBuilding /></el-icon>
          <span>客户管理</span>
        </el-menu-item>
        <el-menu-item index="/products">
          <el-icon><Goods /></el-icon>
          <span>活动产品</span>
        </el-menu-item>
        <el-menu-item index="/instances">
          <el-icon><Operation /></el-icon>
          <span>活动实例</span>
        </el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header class="flex items-center justify-between border-b border-gray-200 bg-white">
        <el-button :icon="isCollapse ? 'Expand' : 'Fold'" text @click="isCollapse = !isCollapse" />
        <div class="flex items-center gap-3">
          <span class="text-gray-600">{{ auth.user?.nickname || '超级管理员' }}</span>
          <el-button type="danger" text size="small" @click="handleLogout">退出</el-button>
        </div>
      </el-header>
      <el-main class="bg-gray-50">
        <router-view />
      </el-main>
    </el-container>
  </el-container>
</template>

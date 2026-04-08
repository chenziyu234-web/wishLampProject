<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { getStats } from '@/api/instance'

const stats = ref<any>({})
const loading = ref(true)

onMounted(async () => {
  try {
    const res: any = await getStats()
    if (res.success) stats.value = res.stats
  } finally {
    loading.value = false
  }
})
</script>

<template>
  <div>
    <h2 class="text-2xl font-bold mb-6">数据概览</h2>
    <el-row :gutter="20" v-loading="loading">
      <el-col :span="6">
        <el-card shadow="hover">
          <div class="text-center">
            <div class="text-3xl font-bold text-blue-600">{{ stats.totalTenants || 0 }}</div>
            <div class="text-gray-500 mt-2">客户总数</div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover">
          <div class="text-center">
            <div class="text-3xl font-bold text-green-600">{{ stats.totalInstances || 0 }}</div>
            <div class="text-gray-500 mt-2">活动实例</div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover">
          <div class="text-center">
            <div class="text-3xl font-bold text-orange-500">{{ stats.activeInstances || 0 }}</div>
            <div class="text-gray-500 mt-2">进行中活动</div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover">
          <div class="text-center">
            <div class="text-3xl font-bold text-purple-600">{{ stats.totalUsers || 0 }}</div>
            <div class="text-gray-500 mt-2">用户总数</div>
          </div>
        </el-card>
      </el-col>
    </el-row>
    <el-row :gutter="20" class="mt-5">
      <el-col :span="8">
        <el-card shadow="hover">
          <div class="text-center">
            <div class="text-2xl font-bold text-red-500">{{ stats.totalBlessings || 0 }}</div>
            <div class="text-gray-500 mt-2">祈福总数</div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card shadow="hover">
          <div class="text-center">
            <div class="text-2xl font-bold text-indigo-500">{{ stats.totalRiddles || 0 }}</div>
            <div class="text-gray-500 mt-2">灯谜总数</div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card shadow="hover">
          <div class="text-center">
            <div class="text-2xl font-bold text-teal-500">{{ stats.totalRiddleAnswers || 0 }}</div>
            <div class="text-gray-500 mt-2">答题总数</div>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

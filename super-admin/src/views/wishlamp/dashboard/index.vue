<script setup lang="ts">
import { ref, onMounted } from 'vue';
import { getStats } from '@/api/wishlamp/instance';
import type { WishStats } from '@/api/wishlamp/instance/types';

const stats = ref<WishStats>({
  totalInstances: 0,
  activeInstances: 0,
  totalProducts: 0,
  totalEntries: 0,
});
const loading = ref(true);

async function fetchStats() {
  loading.value = true;
  try {
    const res = await getStats();
    stats.value = res.data;
  } finally {
    loading.value = false;
  }
}

onMounted(() => {
  fetchStats();
});
</script>

<template>
  <div class="p-4">
    <el-row :gutter="20" v-loading="loading">
      <el-col :span="6">
        <el-card shadow="hover">
          <div class="stat-card">
            <div class="stat-value text-blue-600">{{ stats.totalProducts }}</div>
            <div class="stat-label">活动产品总数</div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover">
          <div class="stat-card">
            <div class="stat-value text-green-600">{{ stats.totalInstances }}</div>
            <div class="stat-label">活动实例总数</div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover">
          <div class="stat-card">
            <div class="stat-value text-orange-500">{{ stats.activeInstances }}</div>
            <div class="stat-label">进行中活动</div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover">
          <div class="stat-card">
            <div class="stat-value text-purple-600">{{ stats.totalEntries }}</div>
            <div class="stat-label">祝福参与总数</div>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<style scoped>
.stat-card {
  text-align: center;
  padding: 20px 0;
}
.stat-value {
  font-size: 2.5rem;
  font-weight: 700;
  line-height: 1;
}
.stat-label {
  margin-top: 12px;
  color: #909399;
  font-size: 14px;
}
</style>

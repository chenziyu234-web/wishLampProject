<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { getTenant, updateTenant } from '@/api/tenant'
import { ElMessage } from 'element-plus'

const route = useRoute()
const router = useRouter()
const tenant = ref<any>({})
const loading = ref(true)
const editing = ref(false)
const editForm = ref({ name: '', contactPhone: '', contactName: '', status: '' })

async function fetchTenant() {
  loading.value = true
  try {
    const res: any = await getTenant(Number(route.params.id))
    if (res.success) {
      tenant.value = res.tenant
      editForm.value = {
        name: res.tenant.name,
        contactPhone: res.tenant.contactPhone || '',
        contactName: res.tenant.contactName || '',
        status: res.tenant.status,
      }
    }
  } finally {
    loading.value = false
  }
}

async function handleSave() {
  try {
    const res: any = await updateTenant(Number(route.params.id), editForm.value)
    if (res.success) {
      ElMessage.success('保存成功')
      editing.value = false
      fetchTenant()
    }
  } catch (e: any) {
    ElMessage.error(e.response?.data?.error || '保存失败')
  }
}

onMounted(fetchTenant)
</script>

<template>
  <div v-loading="loading">
    <div class="flex items-center gap-3 mb-6">
      <el-button @click="router.push('/tenants')" :icon="'ArrowLeft'" text />
      <h2 class="text-2xl font-bold">{{ tenant.name || '客户详情' }}</h2>
      <el-tag :type="tenant.status === 'ACTIVE' ? 'success' : 'danger'" v-if="tenant.status">
        {{ tenant.status === 'ACTIVE' ? '启用' : '禁用' }}
      </el-tag>
    </div>

    <el-card>
      <template #header>
        <div class="flex items-center justify-between">
          <span>基本信息</span>
          <el-button v-if="!editing" type="primary" text @click="editing = true">编辑</el-button>
          <div v-else class="flex gap-2">
            <el-button @click="editing = false">取消</el-button>
            <el-button type="primary" @click="handleSave">保存</el-button>
          </div>
        </div>
      </template>

      <el-descriptions :column="2" border v-if="!editing">
        <el-descriptions-item label="编码">{{ tenant.code }}</el-descriptions-item>
        <el-descriptions-item label="名称">{{ tenant.name }}</el-descriptions-item>
        <el-descriptions-item label="联系人">{{ tenant.contactName || '-' }}</el-descriptions-item>
        <el-descriptions-item label="联系电话">{{ tenant.contactPhone || '-' }}</el-descriptions-item>
        <el-descriptions-item label="管理员数量">{{ tenant.adminCount }}</el-descriptions-item>
        <el-descriptions-item label="活动数量">{{ tenant.instanceCount }}</el-descriptions-item>
        <el-descriptions-item label="创建时间">{{ tenant.createdAt }}</el-descriptions-item>
        <el-descriptions-item label="状态">
          <el-tag :type="tenant.status === 'ACTIVE' ? 'success' : 'danger'">
            {{ tenant.status === 'ACTIVE' ? '启用' : '禁用' }}
          </el-tag>
        </el-descriptions-item>
      </el-descriptions>

      <el-form v-else label-width="80px">
        <el-form-item label="名称">
          <el-input v-model="editForm.name" />
        </el-form-item>
        <el-form-item label="联系人">
          <el-input v-model="editForm.contactName" />
        </el-form-item>
        <el-form-item label="联系电话">
          <el-input v-model="editForm.contactPhone" />
        </el-form-item>
        <el-form-item label="状态">
          <el-select v-model="editForm.status">
            <el-option label="启用" value="ACTIVE" />
            <el-option label="禁用" value="DISABLED" />
          </el-select>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

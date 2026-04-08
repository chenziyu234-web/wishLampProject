<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { getInstances, createInstance } from '@/api/instance'
import { getTenants } from '@/api/tenant'
import { getProducts } from '@/api/product'
import { ElMessage } from 'element-plus'

const instances = ref<any[]>([])
const tenants = ref<any[]>([])
const products = ref<any[]>([])
const loading = ref(true)
const showDialog = ref(false)
const form = ref({ tenantId: null as number | null, productId: null as number | null, name: '' })

const statusMap: Record<string, { label: string; type: string }> = {
  DRAFT: { label: '草稿', type: 'info' },
  ACTIVE: { label: '进行中', type: 'success' },
  PAUSED: { label: '已暂停', type: 'warning' },
  ENDED: { label: '已结束', type: 'danger' },
}

async function fetchData() {
  loading.value = true
  try {
    const [instRes, tenantRes, productRes]: any[] = await Promise.all([
      getInstances(),
      getTenants(),
      getProducts(),
    ])
    if (instRes.success) instances.value = instRes.list
    if (tenantRes.success) tenants.value = tenantRes.list
    if (productRes.success) products.value = productRes.list
  } finally {
    loading.value = false
  }
}

async function handleCreate() {
  if (!form.value.tenantId || !form.value.productId || !form.value.name) {
    ElMessage.warning('请填写必填项')
    return
  }
  try {
    const res: any = await createInstance(form.value as any)
    if (res.success) {
      ElMessage.success('创建成功')
      showDialog.value = false
      form.value = { tenantId: null, productId: null, name: '' }
      fetchData()
    }
  } catch (e: any) {
    ElMessage.error(e.response?.data?.error || '创建失败')
  }
}

onMounted(fetchData)
</script>

<template>
  <div>
    <div class="flex items-center justify-between mb-6">
      <h2 class="text-2xl font-bold">活动实例</h2>
      <el-button type="primary" @click="showDialog = true">
        <el-icon class="mr-1"><Plus /></el-icon>开通活动
      </el-button>
    </div>

    <el-table :data="instances" v-loading="loading" stripe>
      <el-table-column prop="id" label="ID" width="80" />
      <el-table-column prop="name" label="实例名称" />
      <el-table-column prop="tenantName" label="所属客户" width="150" />
      <el-table-column prop="productName" label="活动产品" width="130" />
      <el-table-column prop="productType" label="类型" width="100">
        <template #default="{ row }">
          <el-tag :type="row.productType === 'BLESSING' ? 'warning' : 'success'" size="small">
            {{ row.productType === 'BLESSING' ? '祈福' : '灯谜' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="status" label="状态" width="100">
        <template #default="{ row }">
          <el-tag :type="(statusMap[row.status]?.type as any) || 'info'" size="small">
            {{ statusMap[row.status]?.label || row.status }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="createdAt" label="创建时间" width="180" />
    </el-table>

    <el-dialog v-model="showDialog" title="开通活动实例" width="500">
      <el-form label-width="80px">
        <el-form-item label="客户" required>
          <el-select v-model="form.tenantId" class="w-full" placeholder="选择客户">
            <el-option v-for="t in tenants" :key="t.id" :label="t.name" :value="t.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="活动" required>
          <el-select v-model="form.productId" class="w-full" placeholder="选择活动产品">
            <el-option v-for="p in products" :key="p.id" :label="`${p.name} (${p.type})`" :value="p.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="实例名" required>
          <el-input v-model="form.name" placeholder="如：XX 公司灯笼祈福" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="showDialog = false">取消</el-button>
        <el-button type="primary" @click="handleCreate">创建</el-button>
      </template>
    </el-dialog>
  </div>
</template>

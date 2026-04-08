<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { getProducts, createProduct } from '@/api/product'
import { ElMessage } from 'element-plus'

const products = ref<any[]>([])
const loading = ref(true)
const showDialog = ref(false)
const form = ref({ name: '', slug: '', description: '', type: 'BLESSING', iconUrl: '' })

const typeOptions = [
  { label: '灯笼祈福', value: 'BLESSING' },
  { label: '猜灯谜', value: 'RIDDLE' },
]

async function fetchProducts() {
  loading.value = true
  try {
    const res: any = await getProducts()
    if (res.success) products.value = res.list
  } finally {
    loading.value = false
  }
}

async function handleCreate() {
  if (!form.value.name || !form.value.slug || !form.value.type) {
    ElMessage.warning('请填写必填项')
    return
  }
  try {
    const res: any = await createProduct(form.value)
    if (res.success) {
      ElMessage.success('创建成功')
      showDialog.value = false
      form.value = { name: '', slug: '', description: '', type: 'BLESSING', iconUrl: '' }
      fetchProducts()
    }
  } catch (e: any) {
    ElMessage.error(e.response?.data?.error || '创建失败')
  }
}

onMounted(fetchProducts)
</script>

<template>
  <div>
    <div class="flex items-center justify-between mb-6">
      <h2 class="text-2xl font-bold">活动产品</h2>
      <el-button type="primary" @click="showDialog = true">
        <el-icon class="mr-1"><Plus /></el-icon>新增产品
      </el-button>
    </div>

    <el-table :data="products" v-loading="loading" stripe>
      <el-table-column prop="id" label="ID" width="80" />
      <el-table-column prop="name" label="名称" />
      <el-table-column prop="slug" label="标识" width="150" />
      <el-table-column prop="type" label="类型" width="120">
        <template #default="{ row }">
          <el-tag :type="row.type === 'BLESSING' ? 'warning' : 'success'">
            {{ row.type === 'BLESSING' ? '灯笼祈福' : '猜灯谜' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="enabled" label="状态" width="100">
        <template #default="{ row }">
          <el-tag :type="row.enabled ? 'success' : 'info'">{{ row.enabled ? '启用' : '禁用' }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="description" label="描述" show-overflow-tooltip />
    </el-table>

    <el-dialog v-model="showDialog" title="新增活动产品" width="500">
      <el-form label-width="80px">
        <el-form-item label="名称" required>
          <el-input v-model="form.name" placeholder="产品名称" />
        </el-form-item>
        <el-form-item label="标识" required>
          <el-input v-model="form.slug" placeholder="URL 标识，如 blessing" />
        </el-form-item>
        <el-form-item label="类型" required>
          <el-select v-model="form.type" class="w-full">
            <el-option v-for="opt in typeOptions" :key="opt.value" :label="opt.label" :value="opt.value" />
          </el-select>
        </el-form-item>
        <el-form-item label="描述">
          <el-input v-model="form.description" type="textarea" :rows="3" placeholder="产品描述" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="showDialog = false">取消</el-button>
        <el-button type="primary" @click="handleCreate">创建</el-button>
      </template>
    </el-dialog>
  </div>
</template>

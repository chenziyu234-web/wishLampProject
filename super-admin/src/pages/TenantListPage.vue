<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { getTenants, createTenant, createTenantAdmin } from '@/api/tenant'
import { ElMessage } from 'element-plus'

const router = useRouter()
const tenants = ref<any[]>([])
const loading = ref(true)
const showCreateDialog = ref(false)
const showAdminDialog = ref(false)
const currentTenantId = ref<number | null>(null)

const createForm = ref({ code: '', name: '', contactPhone: '', contactName: '' })
const adminForm = ref({ phone: '', password: '', nickname: '' })

async function fetchTenants() {
  loading.value = true
  try {
    const res: any = await getTenants()
    if (res.success) tenants.value = res.list
  } finally {
    loading.value = false
  }
}

async function handleCreate() {
  if (!createForm.value.code || !createForm.value.name) {
    ElMessage.warning('请填写编码和名称')
    return
  }
  try {
    const res: any = await createTenant(createForm.value)
    if (res.success) {
      ElMessage.success('创建成功')
      showCreateDialog.value = false
      createForm.value = { code: '', name: '', contactPhone: '', contactName: '' }
      fetchTenants()
    }
  } catch (e: any) {
    ElMessage.error(e.response?.data?.error || '创建失败')
  }
}

function openAdminDialog(tenantId: number) {
  currentTenantId.value = tenantId
  adminForm.value = { phone: '', password: '', nickname: '' }
  showAdminDialog.value = true
}

async function handleCreateAdmin() {
  if (!adminForm.value.phone || !adminForm.value.password) {
    ElMessage.warning('请填写手机号和密码')
    return
  }
  try {
    const res: any = await createTenantAdmin(currentTenantId.value!, adminForm.value)
    if (res.success) {
      ElMessage.success('管理员创建成功')
      showAdminDialog.value = false
      fetchTenants()
    }
  } catch (e: any) {
    ElMessage.error(e.response?.data?.error || '创建失败')
  }
}

onMounted(fetchTenants)
</script>

<template>
  <div>
    <div class="flex items-center justify-between mb-6">
      <h2 class="text-2xl font-bold">客户管理</h2>
      <el-button type="primary" @click="showCreateDialog = true">
        <el-icon class="mr-1"><Plus /></el-icon>新增客户
      </el-button>
    </div>

    <el-table :data="tenants" v-loading="loading" stripe>
      <el-table-column prop="id" label="ID" width="80" />
      <el-table-column prop="code" label="编码" width="150" />
      <el-table-column prop="name" label="名称" />
      <el-table-column prop="contactName" label="联系人" width="120" />
      <el-table-column prop="contactPhone" label="联系电话" width="140" />
      <el-table-column prop="status" label="状态" width="100">
        <template #default="{ row }">
          <el-tag :type="row.status === 'ACTIVE' ? 'success' : 'danger'">
            {{ row.status === 'ACTIVE' ? '启用' : '禁用' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="adminCount" label="管理员" width="90" align="center" />
      <el-table-column prop="instanceCount" label="活动数" width="90" align="center" />
      <el-table-column label="操作" width="200" fixed="right">
        <template #default="{ row }">
          <el-button type="primary" link size="small" @click="router.push(`/tenants/${row.id}`)">详情</el-button>
          <el-button type="success" link size="small" @click="openAdminDialog(row.id)">添加管理员</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog v-model="showCreateDialog" title="新增客户" width="500">
      <el-form label-width="80px">
        <el-form-item label="编码" required>
          <el-input v-model="createForm.code" placeholder="小写字母、数字和连字符" />
        </el-form-item>
        <el-form-item label="名称" required>
          <el-input v-model="createForm.name" placeholder="客户名称" />
        </el-form-item>
        <el-form-item label="联系人">
          <el-input v-model="createForm.contactName" placeholder="联系人姓名" />
        </el-form-item>
        <el-form-item label="联系电话">
          <el-input v-model="createForm.contactPhone" placeholder="联系电话" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="showCreateDialog = false">取消</el-button>
        <el-button type="primary" @click="handleCreate">创建</el-button>
      </template>
    </el-dialog>

    <el-dialog v-model="showAdminDialog" title="添加租户管理员" width="500">
      <el-form label-width="80px">
        <el-form-item label="手机号" required>
          <el-input v-model="adminForm.phone" placeholder="管理员手机号" />
        </el-form-item>
        <el-form-item label="密码" required>
          <el-input v-model="adminForm.password" type="password" placeholder="登录密码" show-password />
        </el-form-item>
        <el-form-item label="昵称">
          <el-input v-model="adminForm.nickname" placeholder="管理员昵称" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="showAdminDialog = false">取消</el-button>
        <el-button type="primary" @click="handleCreateAdmin">创建</el-button>
      </template>
    </el-dialog>
  </div>
</template>

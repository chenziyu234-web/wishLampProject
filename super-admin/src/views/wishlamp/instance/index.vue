<script setup lang="ts">
import { ref, reactive, onMounted, toRefs } from 'vue';
import { listInstance, addInstance, updateInstance, delInstance } from '@/api/wishlamp/instance';
import { listAllProduct } from '@/api/wishlamp/product';
import type { WishInstanceVO, WishInstanceForm, WishInstanceQuery } from '@/api/wishlamp/instance/types';
import type { WishProductVO } from '@/api/wishlamp/product/types';
import { ElMessage, ElMessageBox } from 'element-plus';

const instanceList = ref<WishInstanceVO[]>([]);
const productOptions = ref<WishProductVO[]>([]);
const loading = ref(true);
const showDialog = ref(false);
const dialogTitle = ref('');
const total = ref(0);

const statusMap: Record<string, { label: string; type: string }> = {
  DRAFT: { label: '草稿', type: 'info' },
  ACTIVE: { label: '进行中', type: 'success' },
  PAUSED: { label: '已暂停', type: 'warning' },
  ENDED: { label: '已结束', type: 'danger' }
};

const data = reactive<{
  form: WishInstanceForm;
  queryParams: WishInstanceQuery;
}>({
  form: {
    instanceId: undefined,
    productId: undefined,
    name: '',
    status: 'DRAFT',
    config: '',
    startTime: '',
    endTime: ''
  },
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    name: '',
    status: '',
    productId: undefined
  }
});
const { form, queryParams } = toRefs(data);

async function fetchList() {
  loading.value = true;
  try {
    const res = await listInstance(queryParams.value);
    instanceList.value = res.rows;
    total.value = res.total;
  } finally {
    loading.value = false;
  }
}

async function fetchProducts() {
  try {
    const res = await listAllProduct();
    productOptions.value = res.data;
  } catch {
    productOptions.value = [];
  }
}

function handleQuery() {
  queryParams.value.pageNum = 1;
  fetchList();
}

function resetQuery() {
  queryParams.value = { pageNum: 1, pageSize: 10, name: '', status: '', productId: undefined };
  fetchList();
}

function handleAdd() {
  resetForm();
  dialogTitle.value = '开通活动实例';
  showDialog.value = true;
}

function handleUpdate(row: WishInstanceVO) {
  resetForm();
  form.value = { ...row, productId: row.productId };
  dialogTitle.value = '修改活动实例';
  showDialog.value = true;
}

function resetForm() {
  form.value = { instanceId: undefined, productId: undefined, name: '', status: 'DRAFT', config: '', startTime: '', endTime: '' };
}

async function submitForm() {
  try {
    if (form.value.instanceId) {
      await updateInstance(form.value);
      ElMessage.success('修改成功');
    } else {
      await addInstance(form.value);
      ElMessage.success('开通成功');
    }
    showDialog.value = false;
    fetchList();
  } catch (e) {
    // request.ts 已处理错误提示
  }
}

async function handleDelete(row: WishInstanceVO) {
  await ElMessageBox.confirm(`确认删除实例「${row.name}」?`, '提示', { type: 'warning' });
  await delInstance(row.instanceId);
  ElMessage.success('删除成功');
  fetchList();
}

onMounted(() => {
  fetchList();
  fetchProducts();
});
</script>

<template>
  <div class="p-2">
    <!-- 搜索栏 -->
    <el-form :model="queryParams" :inline="true">
      <el-form-item label="实例名称" prop="name">
        <el-input v-model="queryParams.name" placeholder="请输入实例名称" clearable @keyup.enter="handleQuery" />
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="全部" clearable>
          <el-option v-for="(item, key) in statusMap" :key="key" :label="item.label" :value="key" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="handleQuery">搜索</el-button>
        <el-button @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <!-- 操作栏 -->
    <el-row :gutter="10" class="mb-2">
      <el-col :span="1.5">
        <el-button type="primary" plain @click="handleAdd" v-hasPermi="['wishlamp:instance:add']">开通活动</el-button>
      </el-col>
    </el-row>

    <!-- 表格 -->
    <el-table :data="instanceList" v-loading="loading" stripe>
      <el-table-column prop="instanceId" label="ID" width="100" />
      <el-table-column prop="name" label="实例名称" />
      <el-table-column prop="productName" label="活动产品" width="140" />
      <el-table-column prop="productType" label="类型" width="110">
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
      <el-table-column prop="createTime" label="创建时间" width="180" />
      <el-table-column label="操作" width="180" fixed="right">
        <template #default="{ row }">
          <el-button type="primary" link size="small" @click="handleUpdate(row)" v-hasPermi="['wishlamp:instance:edit']">编辑</el-button>
          <el-button type="danger" link size="small" @click="handleDelete(row)" v-hasPermi="['wishlamp:instance:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页 -->
    <pagination v-show="total > 0" :total="total" v-model:page="queryParams.pageNum" v-model:limit="queryParams.pageSize" @pagination="fetchList" />

    <!-- 新增/编辑对话框 -->
    <el-dialog v-model="showDialog" :title="dialogTitle" width="500px" append-to-body>
      <el-form :model="form" label-width="80px">
        <el-form-item label="活动产品" prop="productId">
          <el-select v-model="form.productId" class="w-full" placeholder="选择活动产品">
            <el-option v-for="p in productOptions" :key="p.productId" :label="`${p.name} (${p.type})`" :value="p.productId" />
          </el-select>
        </el-form-item>
        <el-form-item label="实例名称" prop="name">
          <el-input v-model="form.name" placeholder="如：XX公司灯笼祈福" />
        </el-form-item>
        <el-form-item label="状态" prop="status" v-if="form.instanceId">
          <el-select v-model="form.status" class="w-full">
            <el-option v-for="(item, key) in statusMap" :key="key" :label="item.label" :value="key" />
          </el-select>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="showDialog = false">取消</el-button>
        <el-button type="primary" @click="submitForm">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

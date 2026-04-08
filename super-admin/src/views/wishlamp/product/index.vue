<script setup lang="ts">
import { ref, reactive, onMounted, toRefs } from 'vue';
import { listProduct, addProduct, updateProduct, delProduct } from '@/api/wishlamp/product';
import type { WishProductVO, WishProductForm, WishProductQuery } from '@/api/wishlamp/product/types';
import { ElMessage, ElMessageBox } from 'element-plus';

const productList = ref<WishProductVO[]>([]);
const loading = ref(true);
const showDialog = ref(false);
const dialogTitle = ref('');
const total = ref(0);

const data = reactive<{
  form: WishProductForm;
  queryParams: WishProductQuery;
}>({
  form: {
    productId: undefined,
    name: '',
    slug: '',
    type: 'BLESSING',
    description: '',
    iconUrl: '',
    enabled: 1
  },
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    name: '',
    type: '',
    slug: '',
    enabled: undefined
  }
});
const { form, queryParams } = toRefs(data);

const typeOptions = [
  { label: '灯笼祈福', value: 'BLESSING', tagType: 'warning' },
  { label: '猜灯谜', value: 'RIDDLE', tagType: 'success' }
];

function getTypeLabel(type: string) {
  return typeOptions.find(o => o.value === type)?.label || type;
}
function getTypeTag(type: string) {
  return typeOptions.find(o => o.value === type)?.tagType || 'info';
}

async function fetchList() {
  loading.value = true;
  try {
    const res = await listProduct(queryParams.value);
    productList.value = res.rows;
    total.value = res.total;
  } finally {
    loading.value = false;
  }
}

function handleQuery() {
  queryParams.value.pageNum = 1;
  fetchList();
}

function resetQuery() {
  queryParams.value = { pageNum: 1, pageSize: 10, name: '', type: '', slug: '', enabled: undefined };
  fetchList();
}

function handleAdd() {
  resetForm();
  dialogTitle.value = '新增活动产品';
  showDialog.value = true;
}

function handleUpdate(row: WishProductVO) {
  resetForm();
  form.value = { ...row };
  dialogTitle.value = '修改活动产品';
  showDialog.value = true;
}

function resetForm() {
  form.value = { productId: undefined, name: '', slug: '', type: 'BLESSING', description: '', iconUrl: '', enabled: 1 };
}

async function submitForm() {
  try {
    if (form.value.productId) {
      await updateProduct(form.value);
      ElMessage.success('修改成功');
    } else {
      await addProduct(form.value);
      ElMessage.success('新增成功');
    }
    showDialog.value = false;
    fetchList();
  } catch (e) {
    // request.ts 已处理错误提示
  }
}

async function handleDelete(row: WishProductVO) {
  await ElMessageBox.confirm(`确认删除产品「${row.name}」?`, '提示', { type: 'warning' });
  await delProduct(row.productId);
  ElMessage.success('删除成功');
  fetchList();
}

onMounted(() => {
  fetchList();
});
</script>

<template>
  <div class="p-2">
    <!-- 搜索栏 -->
    <el-form :model="queryParams" ref="queryRef" :inline="true">
      <el-form-item label="产品名称" prop="name">
        <el-input v-model="queryParams.name" placeholder="请输入产品名称" clearable @keyup.enter="handleQuery" />
      </el-form-item>
      <el-form-item label="产品类型" prop="type">
        <el-select v-model="queryParams.type" placeholder="全部" clearable>
          <el-option v-for="item in typeOptions" :key="item.value" :label="item.label" :value="item.value" />
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
        <el-button type="primary" plain @click="handleAdd" v-hasPermi="['wishlamp:product:add']">新增</el-button>
      </el-col>
    </el-row>

    <!-- 表格 -->
    <el-table :data="productList" v-loading="loading" stripe>
      <el-table-column prop="productId" label="ID" width="100" />
      <el-table-column prop="name" label="名称" />
      <el-table-column prop="slug" label="标识" width="140" />
      <el-table-column prop="type" label="类型" width="120">
        <template #default="{ row }">
          <el-tag :type="getTypeTag(row.type) as any" size="small">{{ getTypeLabel(row.type) }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="enabled" label="状态" width="100">
        <template #default="{ row }">
          <el-tag :type="row.enabled ? 'success' : 'info'" size="small">{{ row.enabled ? '启用' : '禁用' }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="description" label="描述" show-overflow-tooltip />
      <el-table-column label="操作" width="180" fixed="right">
        <template #default="{ row }">
          <el-button type="primary" link size="small" @click="handleUpdate(row)" v-hasPermi="['wishlamp:product:edit']">编辑</el-button>
          <el-button type="danger" link size="small" @click="handleDelete(row)" v-hasPermi="['wishlamp:product:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页 -->
    <pagination v-show="total > 0" :total="total" v-model:page="queryParams.pageNum" v-model:limit="queryParams.pageSize" @pagination="fetchList" />

    <!-- 新增/编辑对话框 -->
    <el-dialog v-model="showDialog" :title="dialogTitle" width="500px" append-to-body>
      <el-form :model="form" label-width="80px">
        <el-form-item label="名称" prop="name">
          <el-input v-model="form.name" placeholder="产品名称" />
        </el-form-item>
        <el-form-item label="标识" prop="slug">
          <el-input v-model="form.slug" placeholder="URL 标识，如 blessing" />
        </el-form-item>
        <el-form-item label="类型" prop="type">
          <el-select v-model="form.type" class="w-full">
            <el-option v-for="opt in typeOptions" :key="opt.value" :label="opt.label" :value="opt.value" />
          </el-select>
        </el-form-item>
        <el-form-item label="描述" prop="description">
          <el-input v-model="form.description" type="textarea" :rows="3" placeholder="产品描述" />
        </el-form-item>
        <el-form-item label="状态" prop="enabled">
          <el-radio-group v-model="form.enabled">
            <el-radio :value="1">启用</el-radio>
            <el-radio :value="0">禁用</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="showDialog = false">取消</el-button>
        <el-button type="primary" @click="submitForm">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

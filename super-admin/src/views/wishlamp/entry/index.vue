<script setup lang="ts">
import { ref, reactive, onMounted, toRefs } from 'vue';
import { listEntry, delEntry } from '@/api/wishlamp/entry';
import { listAllProduct } from '@/api/wishlamp/product';
import { listInstance } from '@/api/wishlamp/instance';
import type { WishEntryVO, WishEntryQuery } from '@/api/wishlamp/entry/types';
import type { WishProductVO } from '@/api/wishlamp/product/types';
import type { WishInstanceVO } from '@/api/wishlamp/instance/types';
import { ElMessage, ElMessageBox } from 'element-plus';

const entryList = ref<WishEntryVO[]>([]);
const instanceOptions = ref<WishInstanceVO[]>([]);
const loading = ref(true);
const total = ref(0);

const cardStyleMap: Record<string, string> = {
  lake: '碧波龙舟',
  festive: '传统红韵',
  bamboo: '青竹清雅',
  default: '默认',
};

const data = reactive<{
  queryParams: WishEntryQuery;
}>({
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    instanceId: undefined,
    participantName: '',
  },
});
const { queryParams } = toRefs(data);

async function fetchList() {
  loading.value = true;
  try {
    const res = await listEntry(queryParams.value);
    entryList.value = res.rows;
    total.value = res.total;
  } finally {
    loading.value = false;
  }
}

async function fetchInstances() {
  try {
    const res = await listInstance({ pageNum: 1, pageSize: 200, name: '', status: '', productId: undefined });
    instanceOptions.value = res.rows || [];
  } catch {
    instanceOptions.value = [];
  }
}

function handleQuery() {
  queryParams.value.pageNum = 1;
  fetchList();
}

function resetQuery() {
  queryParams.value = { pageNum: 1, pageSize: 10, instanceId: undefined, participantName: '' };
  fetchList();
}

async function handleDelete(row: WishEntryVO) {
  await ElMessageBox.confirm(
    `确认删除「${row.participantName || '匿名'} → ${row.toName}」的祝福记录?`,
    '提示',
    { type: 'warning' },
  );
  await delEntry(row.entryId);
  ElMessage.success('删除成功');
  fetchList();
}

onMounted(() => {
  fetchList();
  fetchInstances();
});
</script>

<template>
  <div class="p-2">
    <!-- 搜索栏 -->
    <el-form :model="queryParams" :inline="true">
      <el-form-item label="活动实例" prop="instanceId">
        <el-select
          v-model="queryParams.instanceId"
          placeholder="全部实例"
          clearable
          style="width: 200px"
        >
          <el-option
            v-for="inst in instanceOptions"
            :key="inst.instanceId"
            :label="inst.name"
            :value="inst.instanceId"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="参与者" prop="participantName">
        <el-input
          v-model="queryParams.participantName"
          placeholder="参与者姓名"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="handleQuery">搜索</el-button>
        <el-button @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <!-- 统计提示 -->
    <div class="mb-3 text-sm text-gray-500">
      共 <span class="font-semibold text-emerald-600">{{ total }}</span> 条祝福记录
    </div>

    <!-- 表格 -->
    <el-table :data="entryList" v-loading="loading" stripe border>
      <el-table-column prop="entryId" label="ID" width="90" />
      <el-table-column prop="instanceName" label="活动实例" width="160" show-overflow-tooltip />
      <el-table-column prop="participantName" label="参与者" width="120">
        <template #default="{ row }">
          <span>{{ row.participantName || '—' }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="toName" label="祝福对象" width="120" />
      <el-table-column prop="message" label="祝福语" show-overflow-tooltip />
      <el-table-column prop="cardStyle" label="卡片风格" width="120">
        <template #default="{ row }">
          <el-tag type="success" size="small">
            {{ cardStyleMap[row.cardStyle] || row.cardStyle }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="createTime" label="参与时间" width="180" />
      <el-table-column label="操作" width="100" fixed="right">
        <template #default="{ row }">
          <el-button
            type="danger"
            link
            size="small"
            @click="handleDelete(row)"
            v-hasPermi="['wishlamp:entry:remove']"
          >
            删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页 -->
    <pagination
      v-show="total > 0"
      :total="total"
      v-model:page="queryParams.pageNum"
      v-model:limit="queryParams.pageSize"
      @pagination="fetchList"
    />
  </div>
</template>

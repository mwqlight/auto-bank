<template>
  <div class="transaction-list">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>交易记录</span>
        </div>
      </template>
      
      <el-table :data="transactions" style="width: 100%" v-loading="loading">
        <el-table-column prop="transactionNumber" label="交易编号" width="180" />
        <el-table-column prop="fromAccountId" label="转出账户ID" width="120" />
        <el-table-column prop="toAccountId" label="转入账户ID" width="120" />
        <el-table-column prop="amount" label="金额" width="120">
          <template #default="scope">
            ¥{{ scope.row.amount.toFixed(2) }}
          </template>
        </el-table-column>
        <el-table-column prop="transactionType" label="交易类型" width="120">
          <template #default="scope">
            {{ getTransactionType(scope.row.transactionType) }}
          </template>
        </el-table-column>
        <el-table-column prop="status" label="状态" width="100">
          <template #default="scope">
            <el-tag :type="getTransactionStatusType(scope.row.status)">
              {{ getTransactionStatus(scope.row.status) }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="createdAt" label="创建时间" width="180" />
      </el-table>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { TransactionResponse } from '@/types/transaction'
import { ElMessage } from 'element-plus'

// Mock data for transactions
const transactions = ref<TransactionResponse[]>([
  {
    id: 1,
    transactionNumber: 'TXN202301010001',
    fromAccountId: 1,
    toAccountId: 2,
    amount: 5000.00,
    transactionType: 0,
    status: 0,
    createdAt: '2023-01-01 10:00:00'
  },
  {
    id: 2,
    transactionNumber: 'TXN202301010002',
    fromAccountId: 3,
    toAccountId: 1,
    amount: 2000.00,
    transactionType: 1,
    status: 0,
    createdAt: '2023-01-01 11:30:00'
  },
  {
    id: 3,
    transactionNumber: 'TXN202301010003',
    fromAccountId: 2,
    toAccountId: 4,
    amount: 10000.00,
    transactionType: 0,
    status: 1,
    createdAt: '2023-01-01 14:15:00'
  }
])

const loading = ref(false)

// In a real application, you would fetch transactions from an API
const loadTransactions = async () => {
  try {
    loading.value = true
    // Simulate API call
    await new Promise(resolve => setTimeout(resolve, 1000))
    // transactions.value = response.data || []
  } catch (error) {
    ElMessage.error('加载交易记录失败')
  } finally {
    loading.value = false
  }
}

const getTransactionType = (type: number) => {
  const types = ['转账', '存款', '取款', '利息']
  return types[type] || '未知'
}

const getTransactionStatus = (status: number) => {
  const statuses = ['成功', '失败', '处理中', '已撤销']
  return statuses[status] || '未知'
}

const getTransactionStatusType = (status: number) => {
  const types = ['success', 'danger', 'warning', 'info']
  return types[status] || 'info'
}

onMounted(() => {
  loadTransactions()
})
</script>

<style scoped>
.transaction-list {
  padding: 20px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>
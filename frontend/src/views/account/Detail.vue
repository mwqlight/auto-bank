<template>
  <div class="account-detail" v-if="account">
    <el-descriptions title="账户信息" :column="2" border>
      <el-descriptions-item label="账户编号">{{ account.accountNumber }}</el-descriptions-item>
      <el-descriptions-item label="客户ID">{{ account.customerId }}</el-descriptions-item>
      <el-descriptions-item label="账户类型">
        {{ getAccountType(account.accountType) }}
      </el-descriptions-item>
      <el-descriptions-item label="余额">¥{{ account.balance.toFixed(2) }}</el-descriptions-item>
      <el-descriptions-item label="币种">{{ account.currency }}</el-descriptions-item>
      <el-descriptions-item label="状态">
        <el-tag :type="getAccountStatusType(account.status)">
          {{ getAccountStatus(account.status) }}
        </el-tag>
      </el-descriptions-item>
      <el-descriptions-item label="创建时间">{{ account.createdAt }}</el-descriptions-item>
    </el-descriptions>
    
    <el-divider />
    
    <h3>账户操作</h3>
    <div class="account-actions">
      <el-button type="primary" @click="showDepositDialog">存款</el-button>
      <el-button type="success" @click="showWithdrawDialog">取款</el-button>
      <el-button type="warning" @click="showTransferDialog">转账</el-button>
      <el-button type="danger" @click="freezeAccount" v-if="account.status === 0">冻结账户</el-button>
      <el-button type="info" @click="unfreezeAccount" v-if="account.status === 1">解冻账户</el-button>
    </div>
    
    <el-divider />
    
    <h3>交易记录</h3>
    <BaseTable
      :data="transactions"
      :loading="loadingTransactions"
      title=""
      :show-pagination="false"
    >
      <el-table-column prop="transactionNumber" label="交易编号" />
      <el-table-column prop="amount" label="金额" width="120">
        <template #default="scope">
          ¥{{ scope.row.amount.toFixed(2) }}
        </template>
      </el-table-column>
      <el-table-column prop="transactionType" label="交易类型" width="100">
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
      <el-table-column prop="createdAt" label="创建时间" />
    </BaseTable>
    
    <div style="margin-top: 20px; text-align: center;">
      <el-button @click="goBack">返回</el-button>
    </div>
  </div>
  
  <div v-else class="account-detail-empty">
    <el-empty description="请选择账户查看详情" />
  </div>
  
  <!-- Deposit Dialog -->
  <AccountForm
    v-model:visible="depositDialogVisible"
    :account-id="account?.id"
    action-type="deposit"
    @success="handleActionSuccess"
  />
  
  <!-- Withdraw Dialog -->
  <AccountForm
    v-model:visible="withdrawDialogVisible"
    :account-id="account?.id"
    action-type="withdraw"
    @success="handleActionSuccess"
  />
  
  <!-- Transfer Dialog -->
  <el-dialog v-model="transferDialogVisible" title="转账" width="500px">
    <el-form :model="transferForm" :rules="transferFormRules" ref="transferFormRef" label-width="100px">
      <el-form-item label="转入账户ID" prop="toAccountId">
        <el-input v-model.number="transferForm.toAccountId" />
      </el-form-item>
      <el-form-item label="转账金额" prop="amount">
        <el-input v-model.number="transferForm.amount" />
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="transferDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitTransfer">确定</el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script setup lang="ts">
import { ref, onMounted, reactive } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { accountApi } from '@/api/modules/account'
import { AccountResponse } from '@/types/account'
import { TransactionResponse } from '@/types/transaction'
import BaseTable from '@/components/base/BaseTable.vue'
import AccountForm from '@/components/business/AccountForm.vue'
import { ElMessage, ElMessageBox, FormInstance, FormRules } from 'element-plus'

const route = useRoute()
const router = useRouter()

const account = ref<AccountResponse | null>(null)
const transactions = ref<TransactionResponse[]>([])
const loadingTransactions = ref(false)

const depositDialogVisible = ref(false)
const withdrawDialogVisible = ref(false)
const transferDialogVisible = ref(false)

const transferForm = reactive({
  toAccountId: null as number | null,
  amount: 0
})

const transferFormRef = ref<FormInstance>()

const transferFormRules = ref<FormRules>({
  toAccountId: [{ required: true, message: '请输入转入账户ID', trigger: 'blur' }],
  amount: [
    { required: true, message: '请输入转账金额', trigger: 'blur' },
    { type: 'number', min: 0.01, message: '金额必须大于0', trigger: 'blur' }
  ]
})

const loadAccount = async (id: string) => {
  try {
    const response = await accountApi.getAccountById(Number(id))
    account.value = response.data
    loadTransactions(Number(id))
  } catch (error) {
    ElMessage.error('加载账户信息失败')
  }
}

const loadTransactions = async (accountId: number) => {
  try {
    loadingTransactions.value = true
    // In a real application, you would fetch transactions for this specific account
    // For now, we'll use mock data
    transactions.value = [
      {
        id: 1,
        transactionNumber: 'TXN202301010001',
        fromAccountId: accountId,
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
        toAccountId: accountId,
        amount: 2000.00,
        transactionType: 1,
        status: 0,
        createdAt: '2023-01-01 11:30:00'
      }
    ]
  } catch (error) {
    ElMessage.error('加载交易记录失败')
  } finally {
    loadingTransactions.value = false
  }
}

const showDepositDialog = () => {
  depositDialogVisible.value = true
}

const showWithdrawDialog = () => {
  withdrawDialogVisible.value = true
}

const showTransferDialog = () => {
  transferDialogVisible.value = true
  // Reset form
  transferForm.toAccountId = null
  transferForm.amount = 0
}

const submitTransfer = async () => {
  if (!transferFormRef.value) return
  
  await transferFormRef.value.validate(async (valid) => {
    if (valid) {
      try {
        // In a real application, you would call the transfer API
        // await accountApi.transfer(account.value!.id, transferForm.toAccountId!, transferForm.amount)
        ElMessage.success('转账成功')
        transferDialogVisible.value = false
        loadAccount(route.params.id as string)
      } catch (error) {
        ElMessage.error('转账失败')
      }
    }
  })
}

const freezeAccount = async () => {
  try {
    await ElMessageBox.confirm('确定要冻结该账户吗？', '提示', {
      type: 'warning'
    })
    
    // In a real application, you would call the freeze API
    // await accountApi.freezeAccount(account.value!.id)
    ElMessage.success('账户冻结成功')
    loadAccount(route.params.id as string)
  } catch (error) {
    if (error !== 'cancel') {
      ElMessage.error('账户冻结失败')
    }
  }
}

const unfreezeAccount = async () => {
  try {
    await ElMessageBox.confirm('确定要解冻该账户吗？', '提示', {
      type: 'warning'
    })
    
    // In a real application, you would call the unfreeze API
    // await accountApi.unfreezeAccount(account.value!.id)
    ElMessage.success('账户解冻成功')
    loadAccount(route.params.id as string)
  } catch (error) {
    if (error !== 'cancel') {
      ElMessage.error('账户解冻失败')
    }
  }
}

const handleActionSuccess = () => {
  // Reload account details after successful action
  loadAccount(route.params.id as string)
}

const goBack = () => {
  router.push('/accounts')
}

const getAccountType = (type: number) => {
  const types = ['储蓄账户', '支票账户', '定期账户']
  return types[type] || '未知'
}

const getAccountStatus = (status: number) => {
  const statuses = ['正常', '冻结', '注销']
  return statuses[status] || '未知'
}

const getAccountStatusType = (status: number) => {
  const types = ['success', 'warning', 'danger']
  return types[status] || 'info'
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
  const accountId = route.params.id as string
  if (accountId) {
    loadAccount(accountId)
  }
})
</script>

<style scoped>
.account-detail {
  padding: 20px;
}

.account-detail-empty {
  padding: 100px 0;
  text-align: center;
}

.account-actions {
  display: flex;
  gap: 10px;
  flex-wrap: wrap;
}
</style>
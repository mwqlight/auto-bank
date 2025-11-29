<template>
  <div class="account-list">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>账户管理</span>
          <el-button type="primary" @click="showCreateDialog">新增账户</el-button>
        </div>
      </template>
      
      <el-table :data="accounts" style="width: 100%" v-loading="loading">
        <el-table-column prop="accountNumber" label="账户编号" width="180" />
        <el-table-column prop="customerId" label="客户ID" width="100" />
        <el-table-column prop="accountType" label="账户类型" width="120">
          <template #default="scope">
            {{ getAccountType(scope.row.accountType) }}
          </template>
        </el-table-column>
        <el-table-column prop="balance" label="余额" width="120">
          <template #default="scope">
            ¥{{ scope.row.balance.toFixed(2) }}
          </template>
        </el-table-column>
        <el-table-column prop="currency" label="币种" width="80" />
        <el-table-column prop="status" label="状态" width="100">
          <template #default="scope">
            <el-tag :type="getAccountStatusType(scope.row.status)">
              {{ getAccountStatus(scope.row.status) }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="createdAt" label="创建时间" width="180" />
        <el-table-column label="操作" width="200">
          <template #default="scope">
            <el-button size="small" @click="deposit(scope.row)">存款</el-button>
            <el-button size="small" @click="withdraw(scope.row)">取款</el-button>
            <el-button size="small" type="danger" @click="deleteAccount(scope.row.id)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
    
    <!-- Create Account Dialog -->
    <el-dialog v-model="createDialogVisible" title="新增账户" width="500px">
      <el-form :model="accountForm" :rules="accountFormRules" ref="accountFormRef" label-width="100px">
        <el-form-item label="客户ID" prop="customerId">
          <el-input v-model.number="accountForm.customerId" />
        </el-form-item>
        <el-form-item label="账户类型" prop="accountType">
          <el-select v-model="accountForm.accountType" placeholder="请选择账户类型">
            <el-option label="储蓄账户" :value="0" />
            <el-option label="支票账户" :value="1" />
            <el-option label="定期账户" :value="2" />
          </el-select>
        </el-form-item>
        <el-form-item label="初始余额" prop="initialBalance">
          <el-input v-model.number="accountForm.initialBalance" />
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="createDialogVisible = false">取消</el-button>
          <el-button type="primary" @click="createAccount">确定</el-button>
        </span>
      </template>
    </el-dialog>
    
    <!-- Deposit Dialog -->
    <el-dialog v-model="depositDialogVisible" title="存款" width="500px">
      <el-form :model="transactionForm" :rules="transactionFormRules" ref="transactionFormRef" label-width="100px">
        <el-form-item label="账户编号">
          <el-input v-model="currentAccount.accountNumber" disabled />
        </el-form-item>
        <el-form-item label="存款金额" prop="amount">
          <el-input v-model.number="transactionForm.amount" />
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="depositDialogVisible = false">取消</el-button>
          <el-button type="primary" @click="submitDeposit">确定</el-button>
        </span>
      </template>
    </el-dialog>
    
    <!-- Withdraw Dialog -->
    <el-dialog v-model="withdrawDialogVisible" title="取款" width="500px">
      <el-form :model="transactionForm" :rules="transactionFormRules" ref="transactionFormRef" label-width="100px">
        <el-form-item label="账户编号">
          <el-input v-model="currentAccount.accountNumber" disabled />
        </el-form-item>
        <el-form-item label="取款金额" prop="amount">
          <el-input v-model.number="transactionForm.amount" />
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="withdrawDialogVisible = false">取消</el-button>
          <el-button type="primary" @click="submitWithdraw">确定</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { accountApi } from '@/api/modules/account'
import { AccountResponse } from '@/types/account'
import { ElMessage, ElMessageBox, FormInstance, FormRules } from 'element-plus'

const accounts = ref<AccountResponse[]>([])
const loading = ref(false)
const createDialogVisible = ref(false)
const depositDialogVisible = ref(false)
const withdrawDialogVisible = ref(false)
const currentAccount = ref<AccountResponse>({} as AccountResponse)

const accountForm = ref({
  customerId: null,
  accountType: 0,
  initialBalance: 0
})

const transactionForm = ref({
  amount: 0
})

const accountFormRef = ref<FormInstance>()
const transactionFormRef = ref<FormInstance>()

const accountFormRules = ref<FormRules>({
  customerId: [{ required: true, message: '请输入客户ID', trigger: 'blur' }],
  accountType: [{ required: true, message: '请选择账户类型', trigger: 'change' }],
  initialBalance: [{ required: true, message: '请输入初始余额', trigger: 'blur' }]
})

const transactionFormRules = ref<FormRules>({
  amount: [
    { required: true, message: '请输入金额', trigger: 'blur' },
    { type: 'number', min: 0.01, message: '金额必须大于0', trigger: 'blur' }
  ]
})

const loadAccounts = async () => {
  try {
    loading.value = true
    const response = await accountApi.getAllAccounts()
    accounts.value = response.data || []
  } catch (error) {
    ElMessage.error('加载账户列表失败')
  } finally {
    loading.value = false
  }
}

const showCreateDialog = () => {
  createDialogVisible.value = true
  // Reset form
  accountForm.value = {
    customerId: null,
    accountType: 0,
    initialBalance: 0
  }
}

const createAccount = async () => {
  if (!accountFormRef.value) return
  
  await accountFormRef.value.validate(async (valid) => {
    if (valid) {
      try {
        await accountApi.createAccount({
          customerId: accountForm.value.customerId!,
          accountType: accountForm.value.accountType,
          initialBalance: accountForm.value.initialBalance
        })
        ElMessage.success('账户创建成功')
        createDialogVisible.value = false
        loadAccounts()
      } catch (error) {
        ElMessage.error('账户创建失败')
      }
    }
  })
}

const deposit = (account: AccountResponse) => {
  currentAccount.value = account
  transactionForm.value.amount = 0
  depositDialogVisible.value = true
}

const submitDeposit = async () => {
  if (!transactionFormRef.value) return
  
  await transactionFormRef.value.validate(async (valid) => {
    if (valid) {
      try {
        await accountApi.deposit(currentAccount.value.id, transactionForm.value.amount)
        ElMessage.success('存款成功')
        depositDialogVisible.value = false
        loadAccounts()
      } catch (error) {
        ElMessage.error('存款失败')
      }
    }
  })
}

const withdraw = (account: AccountResponse) => {
  currentAccount.value = account
  transactionForm.value.amount = 0
  withdrawDialogVisible.value = true
}

const submitWithdraw = async () => {
  if (!transactionFormRef.value) return
  
  await transactionFormRef.value.validate(async (valid) => {
    if (valid) {
      try {
        await accountApi.withdraw(currentAccount.value.id, transactionForm.value.amount)
        ElMessage.success('取款成功')
        withdrawDialogVisible.value = false
        loadAccounts()
      } catch (error) {
        ElMessage.error('取款失败')
      }
    }
  })
}

const deleteAccount = async (id: number) => {
  try {
    await ElMessageBox.confirm('确定要删除该账户吗？', '提示', {
      type: 'warning'
    })
    
    await accountApi.deleteAccount(id)
    ElMessage.success('账户删除成功')
    loadAccounts()
  } catch (error) {
    if (error !== 'cancel') {
      ElMessage.error('账户删除失败')
    }
  }
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

onMounted(() => {
  loadAccounts()
})
</script>

<style scoped>
.account-list {
  padding: 20px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>
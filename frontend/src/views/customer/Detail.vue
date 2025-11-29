<template>
  <div class="customer-detail" v-if="customer">
    <el-descriptions title="客户信息" :column="2" border>
      <el-descriptions-item label="客户编号">{{ customer.customerNumber }}</el-descriptions-item>
      <el-descriptions-item label="姓名">{{ customer.name }}</el-descriptions-item>
      <el-descriptions-item label="身份证号">{{ customer.idCardNumber }}</el-descriptions-item>
      <el-descriptions-item label="手机号">{{ customer.phone }}</el-descriptions-item>
      <el-descriptions-item label="邮箱">{{ customer.email }}</el-descriptions-item>
      <el-descriptions-item label="地址">{{ customer.address }}</el-descriptions-item>
      <el-descriptions-item label="风险等级">
        <el-tag :type="getRiskLevelType(customer.riskLevel)">
          {{ getRiskLevel(customer.riskLevel) }}
        </el-tag>
      </el-descriptions-item>
      <el-descriptions-item label="客户类型">
        {{ getCustomerType(customer.customerType) }}
      </el-descriptions-item>
      <el-descriptions-item label="状态">
        <el-tag :type="getCustomerStatusType(customer.status)">
          {{ getCustomerStatus(customer.status) }}
        </el-tag>
      </el-descriptions-item>
      <el-descriptions-item label="创建时间">{{ customer.createdAt }}</el-descriptions-item>
    </el-descriptions>
    
    <el-divider />
    
    <h3>关联账户</h3>
    <BaseTable
      :data="accounts"
      :loading="loadingAccounts"
      title=""
      :show-pagination="false"
    >
      <el-table-column prop="accountNumber" label="账户编号" />
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
      <el-table-column prop="createdAt" label="创建时间" />
    </BaseTable>
    
    <div style="margin-top: 20px; text-align: center;">
      <el-button @click="goBack">返回</el-button>
    </div>
  </div>
  
  <div v-else class="customer-detail-empty">
    <el-empty description="请选择客户查看详情" />
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { customerApi } from '@/api/modules/customer'
import { accountApi } from '@/api/modules/account'
import { CustomerResponse } from '@/types/customer'
import { AccountResponse } from '@/types/account'
import BaseTable from '@/components/base/BaseTable.vue'
import { ElMessage } from 'element-plus'

const route = useRoute()
const router = useRouter()

const customer = ref<CustomerResponse | null>(null)
const accounts = ref<AccountResponse[]>([])
const loadingAccounts = ref(false)

const loadCustomer = async (id: string) => {
  try {
    const response = await customerApi.getCustomerById(Number(id))
    customer.value = response.data
    loadAccounts(Number(id))
  } catch (error) {
    ElMessage.error('加载客户信息失败')
  }
}

const loadAccounts = async (customerId: number) => {
  try {
    loadingAccounts.value = true
    const response = await accountApi.getAllAccounts()
    // In a real application, you would filter accounts by customerId
    // For now, we'll just show all accounts
    accounts.value = response.data || []
  } catch (error) {
    ElMessage.error('加载账户信息失败')
  } finally {
    loadingAccounts.value = false
  }
}

const goBack = () => {
  router.push('/customers')
}

const getRiskLevel = (level: number) => {
  const levels = ['低', '中', '高']
  return levels[level] || '未知'
}

const getRiskLevelType = (level: number) => {
  const types = ['success', 'warning', 'danger']
  return types[level] || 'info'
}

const getCustomerType = (type: number) => {
  const types = ['个人', '企业']
  return types[type] || '未知'
}

const getCustomerStatus = (status: number) => {
  const statuses = ['正常', '冻结', '注销']
  return statuses[status] || '未知'
}

const getCustomerStatusType = (status: number) => {
  const types = ['success', 'warning', 'danger']
  return types[status] || 'info'
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
  const customerId = route.params.id as string
  if (customerId) {
    loadCustomer(customerId)
  }
})
</script>

<style scoped>
.customer-detail {
  padding: 20px;
}

.customer-detail-empty {
  padding: 100px 0;
  text-align: center;
}
</style>
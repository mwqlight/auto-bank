<template>
  <div class="customer-list">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>客户管理</span>
          <el-button type="primary" @click="showCreateDialog">新增客户</el-button>
        </div>
      </template>
      
      <el-table :data="customers" style="width: 100%" v-loading="loading">
        <el-table-column prop="customerNumber" label="客户编号" width="180" />
        <el-table-column prop="name" label="姓名" width="120" />
        <el-table-column prop="idCardNumber" label="身份证号" width="180" />
        <el-table-column prop="phone" label="手机号" width="120" />
        <el-table-column prop="email" label="邮箱" width="180" />
        <el-table-column prop="riskLevel" label="风险等级" width="100">
          <template #default="scope">
            {{ getRiskLevel(scope.row.riskLevel) }}
          </template>
        </el-table-column>
        <el-table-column prop="customerType" label="客户类型" width="100">
          <template #default="scope">
            {{ getCustomerType(scope.row.customerType) }}
          </template>
        </el-table-column>
        <el-table-column prop="status" label="状态" width="100">
          <template #default="scope">
            <el-tag :type="getCustomerStatusType(scope.row.status)">
              {{ getCustomerStatus(scope.row.status) }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="createdAt" label="创建时间" width="180" />
        <el-table-column label="操作" width="150">
          <template #default="scope">
            <el-button size="small" @click="viewCustomer(scope.row)">查看</el-button>
            <el-button size="small" type="danger" @click="deleteCustomer(scope.row.id)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
    
    <!-- Create Customer Dialog -->
    <el-dialog v-model="createDialogVisible" title="新增客户" width="500px">
      <el-form :model="customerForm" :rules="customerFormRules" ref="customerFormRef" label-width="100px">
        <el-form-item label="姓名" prop="name">
          <el-input v-model="customerForm.name" />
        </el-form-item>
        <el-form-item label="身份证号" prop="idCardNumber">
          <el-input v-model="customerForm.idCardNumber" />
        </el-form-item>
        <el-form-item label="手机号" prop="phone">
          <el-input v-model="customerForm.phone" />
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="customerForm.email" />
        </el-form-item>
        <el-form-item label="地址" prop="address">
          <el-input v-model="customerForm.address" type="textarea" />
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="createDialogVisible = false">取消</el-button>
          <el-button type="primary" @click="createCustomer">确定</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { customerApi } from '@/api/modules/customer'
import { CustomerResponse, CustomerCreateRequest } from '@/types/customer'
import { ElMessage, ElMessageBox, FormInstance, FormRules } from 'element-plus'

const customers = ref<CustomerResponse[]>([])
const loading = ref(false)
const createDialogVisible = ref(false)

const customerForm = ref<CustomerCreateRequest>({
  name: '',
  idCardNumber: '',
  phone: '',
  email: '',
  address: ''
})

const customerFormRef = ref<FormInstance>()

const customerFormRules = ref<FormRules>({
  name: [{ required: true, message: '请输入姓名', trigger: 'blur' }],
  idCardNumber: [{ required: true, message: '请输入身份证号', trigger: 'blur' }],
  phone: [{ required: true, message: '请输入手机号', trigger: 'blur' }]
})

const loadCustomers = async () => {
  try {
    loading.value = true
    const response = await customerApi.getAllCustomers()
    customers.value = response.data || []
  } catch (error) {
    ElMessage.error('加载客户列表失败')
  } finally {
    loading.value = false
  }
}

const showCreateDialog = () => {
  createDialogVisible.value = true
  // Reset form
  customerForm.value = {
    name: '',
    idCardNumber: '',
    phone: '',
    email: '',
    address: ''
  }
}

const createCustomer = async () => {
  if (!customerFormRef.value) return
  
  await customerFormRef.value.validate(async (valid) => {
    if (valid) {
      try {
        await customerApi.createCustomer(customerForm.value)
        ElMessage.success('客户创建成功')
        createDialogVisible.value = false
        loadCustomers()
      } catch (error) {
        ElMessage.error('客户创建失败')
      }
    }
  })
}

const viewCustomer = (customer: CustomerResponse) => {
  ElMessageBox.alert(
    `客户编号: ${customer.customerNumber}<br/>` +
    `姓名: ${customer.name}<br/>` +
    `身份证号: ${customer.idCardNumber}<br/>` +
    `手机号: ${customer.phone}<br/>` +
    `邮箱: ${customer.email || '无'}<br/>` +
    `地址: ${customer.address || '无'}`,
    '客户详情',
    {
      dangerouslyUseHTMLString: true,
      confirmButtonText: '确定'
    }
  )
}

const deleteCustomer = async (id: number) => {
  try {
    await ElMessageBox.confirm('确定要删除该客户吗？', '提示', {
      type: 'warning'
    })
    
    await customerApi.deleteCustomer(id)
    ElMessage.success('客户删除成功')
    loadCustomers()
  } catch (error) {
    if (error !== 'cancel') {
      ElMessage.error('客户删除失败')
    }
  }
}

const getRiskLevel = (level: number) => {
  const levels = ['低', '中', '高']
  return levels[level] || '未知'
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

onMounted(() => {
  loadCustomers()
})
</script>

<style scoped>
.customer-list {
  padding: 20px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>
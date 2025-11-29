<template>
  <div class="account-create">
    <el-card class="account-create-card">
      <template #header>
        <div class="card-header">
          <span>新开账户</span>
          <el-button @click="goBack" type="primary" link>返回</el-button>
        </div>
      </template>
      
      <el-form
        ref="formRef"
        :model="accountForm"
        :rules="formRules"
        label-width="120px"
        class="account-form"
      >
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="客户" prop="customerId">
              <el-select 
                v-model="accountForm.customerId" 
                placeholder="请选择客户"
                filterable
                remote
                :remote-method="searchCustomers"
                :loading="searching"
                style="width: 100%"
              >
                <el-option
                  v-for="item in customerOptions"
                  :key="item.id"
                  :label="`${item.name} (${item.customerNumber})`"
                  :value="item.id"
                />
              </el-select>
            </el-form-item>
          </el-col>
          
          <el-col :span="12">
            <el-form-item label="账户类型" prop="accountType">
              <el-select v-model="accountForm.accountType" placeholder="请选择账户类型">
                <el-option label="储蓄账户" :value="1" />
                <el-option label="支票账户" :value="2" />
                <el-option label="定期账户" :value="3" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        
        <div class="form-actions">
          <el-button @click="resetForm" :disabled="submitting">重置</el-button>
          <el-button 
            type="primary" 
            @click="submitForm" 
            :loading="submitting"
          >
            {{ submitting ? '提交中...' : '提交' }}
          </el-button>
        </div>
      </el-form>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage, FormInstance, FormRules } from 'element-plus'
import { accountApi } from '@/api/modules/account'
import { customerApi } from '@/api/modules/customer'
import { CustomerResponse } from '@/types/customer'

const router = useRouter()
const formRef = ref<FormInstance>()

const accountForm = reactive({
  customerId: null as number | null,
  accountType: null as number | null
})

const formRules = reactive<FormRules>({
  customerId: [
    { required: true, message: '请选择客户', trigger: 'change' }
  ],
  accountType: [
    { required: true, message: '请选择账户类型', trigger: 'change' }
  ]
})

const submitting = ref(false)
const searching = ref(false)
const customerOptions = ref<CustomerResponse[]>([])

// 搜索客户
const searchCustomers = async (query: string) => {
  try {
    searching.value = true
    const response = await customerApi.getAllCustomers()
    customerOptions.value = response.data.filter(customer => 
      customer.name.includes(query) || customer.customerNumber.includes(query)
    )
  } catch (error) {
    ElMessage.error('获取客户列表失败')
  } finally {
    searching.value = false
  }
}

// 提交表单
const submitForm = async () => {
  if (!formRef.value) return
  
  await formRef.value.validate(async (valid) => {
    if (valid && accountForm.customerId && accountForm.accountType) {
      try {
        submitting.value = true
        
        // 调用真实API
        await accountApi.createAccount(accountForm.customerId, accountForm.accountType)
        
        ElMessage.success('账户创建成功')
        router.push('/accounts')
      } catch (error) {
        ElMessage.error('账户创建失败，请稍后重试')
      } finally {
        submitting.value = false
      }
    }
  })
}

// 重置表单
const resetForm = () => {
  formRef.value?.resetFields()
}

// 返回上一页
const goBack = () => {
  router.push('/accounts')
}

// 初始化客户列表
onMounted(() => {
  searchCustomers('')
})
</script>

<style scoped>
.account-create {
  padding: 20px;
}

.account-create-card {
  max-width: 800px;
  margin: 0 auto;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.account-form {
  margin-top: 20px;
}

.form-actions {
  display: flex;
  justify-content: flex-end;
  gap: 10px;
  margin-top: 30px;
}
</style>
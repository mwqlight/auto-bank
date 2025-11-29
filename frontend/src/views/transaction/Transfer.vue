<template>
  <div class="transaction-transfer">
    <el-card class="transaction-transfer-card">
      <template #header>
        <div class="card-header">
          <span>转账</span>
          <el-button @click="goBack" type="primary" link>返回</el-button>
        </div>
      </template>
      
      <el-form
        ref="formRef"
        :model="transferForm"
        :rules="formRules"
        label-width="120px"
        class="transfer-form"
      >
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="付款账户" prop="fromAccountId">
              <el-select 
                v-model="transferForm.fromAccountId" 
                placeholder="请选择付款账户"
                filterable
                remote
                :remote-method="searchAccounts"
                :loading="searching"
                style="width: 100%"
              >
                <el-option
                  v-for="item in accountOptions"
                  :key="item.id"
                  :label="`${item.accountNumber} (${item.currency})`"
                  :value="item.id"
                />
              </el-select>
            </el-form-item>
          </el-col>
          
          <el-col :span="12">
            <el-form-item label="收款账户" prop="toAccountId">
              <el-select 
                v-model="transferForm.toAccountId" 
                placeholder="请选择收款账户"
                filterable
                remote
                :remote-method="searchAccounts"
                :loading="searching"
                style="width: 100%"
              >
                <el-option
                  v-for="item in accountOptions"
                  :key="item.id"
                  :label="`${item.accountNumber} (${item.currency})`"
                  :value="item.id"
                />
              </el-select>
            </el-form-item>
          </el-col>
          
          <el-col :span="12">
            <el-form-item label="转账金额" prop="amount">
              <el-input 
                v-model="transferForm.amount" 
                placeholder="请输入转账金额"
                type="number"
                min="0.01"
                step="0.01"
              >
                <template #append>元</template>
              </el-input>
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
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage, FormInstance, FormRules } from 'element-plus'
import { accountApi } from '@/api/modules/account'
import { transactionApi } from '@/api/modules/transaction'
import { AccountResponse } from '@/types/account'

const router = useRouter()
const formRef = ref<FormInstance>()

const transferForm = reactive({
  fromAccountId: null as number | null,
  toAccountId: null as number | null,
  amount: null as number | null
})

const formRules = reactive<FormRules>({
  fromAccountId: [
    { required: true, message: '请选择付款账户', trigger: 'change' }
  ],
  toAccountId: [
    { required: true, message: '请选择收款账户', trigger: 'change' }
  ],
  amount: [
    { required: true, message: '请输入转账金额', trigger: 'blur' },
    { type: 'number', min: 0.01, message: '转账金额必须大于0', trigger: 'blur' }
  ]
})

const submitting = ref(false)
const searching = ref(false)
const accountOptions = ref<AccountResponse[]>([])

// 搜索账户
const searchAccounts = async (query: string) => {
  try {
    searching.value = true
    const response = await accountApi.getAllAccounts()
    accountOptions.value = response.data.filter(account => 
      account.accountNumber.includes(query)
    )
  } catch (error) {
    ElMessage.error('获取账户列表失败')
  } finally {
    searching.value = false
  }
}

// 提交表单
const submitForm = async () => {
  if (!formRef.value) return
  
  await formRef.value.validate(async (valid) => {
    if (valid) {
      try {
        submitting.value = true
        
        // 调用转账API
        await transactionApi.transfer(
          transferForm.fromAccountId!, 
          transferForm.toAccountId!, 
          transferForm.amount!
        )
        
        ElMessage.success('转账成功')
        resetForm()
      } catch (error: any) {
        ElMessage.error(error.message || '转账失败，请稍后重试')
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
  router.push('/transactions')
}

// 初始化账户列表
searchAccounts('')
</script>

<style scoped>
.transaction-transfer {
  padding: 20px;
}

.transaction-transfer-card {
  max-width: 800px;
  margin: 0 auto;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.transfer-form {
  margin-top: 20px;
}

.form-actions {
  display: flex;
  justify-content: flex-end;
  gap: 10px;
  margin-top: 30px;
}
</style>
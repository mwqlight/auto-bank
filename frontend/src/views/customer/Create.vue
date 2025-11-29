<template>
  <div class="customer-create">
    <el-card class="customer-create-card">
      <template #header>
        <div class="card-header">
          <span>新增客户</span>
          <el-button @click="goBack" type="primary" link>返回</el-button>
        </div>
      </template>
      
      <el-form
        ref="formRef"
        :model="customerForm"
        :rules="formRules"
        label-width="120px"
        class="customer-form"
      >
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="姓名" prop="name">
              <el-input v-model="customerForm.name" placeholder="请输入客户姓名" />
            </el-form-item>
          </el-col>
          
          <el-col :span="12">
            <el-form-item label="身份证号" prop="idCardNumber">
              <el-input v-model="customerForm.idCardNumber" placeholder="请输入身份证号" />
            </el-form-item>
          </el-col>
          
          <el-col :span="12">
            <el-form-item label="手机号" prop="phone">
              <el-input v-model="customerForm.phone" placeholder="请输入手机号" />
            </el-form-item>
          </el-col>
          
          <el-col :span="12">
            <el-form-item label="邮箱" prop="email">
              <el-input v-model="customerForm.email" placeholder="请输入邮箱地址" />
            </el-form-item>
          </el-col>
          
          <el-col :span="24">
            <el-form-item label="地址" prop="address">
              <el-input 
                v-model="customerForm.address" 
                type="textarea" 
                placeholder="请输入详细地址" 
                :rows="3" 
              />
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
import { ElMessage, ElMessageBox, FormInstance, FormRules } from 'element-plus'
import { customerApi } from '@/api/modules/customer'
import { CustomerCreateRequest } from '@/types/customer'
import { useForm } from '@/composables/useForm'

const router = useRouter()
const formRef = ref<FormInstance>()

const customerForm = reactive({
  name: '',
  idCardNumber: '',
  phone: '',
  email: '',
  address: ''
})

const formRules = reactive<FormRules>({
  name: [
    { required: true, message: '请输入客户姓名', trigger: 'blur' }
  ],
  idCardNumber: [
    { required: true, message: '请输入身份证号', trigger: 'blur' },
    { pattern: /^[1-9]\d{5}(18|19|20)\d{2}((0[1-9])|(1[0-2]))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$/, message: '身份证号格式不正确', trigger: 'blur' }
  ],
  phone: [
    { required: true, message: '请输入手机号', trigger: 'blur' },
    { pattern: /^1[3-9]\d{9}$/, message: '手机号格式不正确', trigger: 'blur' }
  ],
  email: [
    { pattern: /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/, message: '邮箱格式不正确', trigger: 'blur' }
  ]
})

const submitting = ref(false)

const submitForm = async () => {
  if (!formRef.value) return
  
  await formRef.value.validate(async (valid) => {
    if (valid) {
      try {
        submitting.value = true
        
        // 调用真实API
        await customerApi.createCustomer({
          name: customerForm.name,
          idCardNumber: customerForm.idCardNumber,
          phone: customerForm.phone,
          email: customerForm.email || undefined,
          address: customerForm.address || undefined
        })
        
        ElMessage.success('客户创建成功')
        router.push('/customers')
      } catch (error) {
        ElMessage.error('客户创建失败，请稍后重试')
      } finally {
        submitting.value = false
      }
    }
  })
}

const resetForm = () => {
  formRef.value?.resetFields()
}

const goBack = () => {
  router.push('/customers')
}
</script>

<style scoped>
.customer-create {
  padding: 20px;
}

.customer-create-card {
  max-width: 800px;
  margin: 0 auto;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.customer-form {
  margin-top: 20px;
}

.form-actions {
  display: flex;
  justify-content: flex-end;
  gap: 10px;
  margin-top: 30px;
}
</style>
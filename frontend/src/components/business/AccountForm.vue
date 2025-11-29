<template>
  <BaseForm
    v-model:visible="formVisible"
    :title="formTitle"
    :model="formData"
    :rules="formRules"
    :submitting="submitting"
    @submit="handleSubmit"
    @close="handleClose"
  >
    <template #default="{ form, rules }">
      <el-form-item label="客户ID" prop="customerId" v-if="!isEdit">
        <el-input v-model.number="form.customerId" />
      </el-form-item>
      <el-form-item label="账户类型" prop="accountType">
        <el-select v-model="form.accountType" placeholder="请选择账户类型" :disabled="isEdit">
          <el-option label="储蓄账户" :value="0" />
          <el-option label="支票账户" :value="1" />
          <el-option label="定期账户" :value="2" />
        </el-select>
      </el-form-item>
      <el-form-item label="初始余额" prop="initialBalance" v-if="!isEdit">
        <el-input v-model.number="form.initialBalance" />
      </el-form-item>
      <el-form-item label="存款金额" prop="depositAmount" v-if="actionType === 'deposit'">
        <el-input v-model.number="form.depositAmount" />
      </el-form-item>
      <el-form-item label="取款金额" prop="withdrawAmount" v-if="actionType === 'withdraw'">
        <el-input v-model.number="form.withdrawAmount" />
      </el-form-item>
    </template>
  </BaseForm>
</template>

<script setup lang="ts">
import { ref, computed, reactive } from 'vue'
import { ElMessage } from 'element-plus'
import BaseForm from '@/components/base/BaseForm.vue'
import { accountApi } from '@/api/modules/account'

interface Props {
  visible: boolean
  accountId?: number
  actionType: 'create' | 'deposit' | 'withdraw'
}

const props = withDefaults(defineProps<Props>(), {
  visible: false,
  accountId: undefined,
  actionType: 'create'
})

const emit = defineEmits<{
  (e: 'update:visible', val: boolean): void
  (e: 'success'): void
}>()

const formVisible = computed({
  get: () => props.visible,
  set: (val) => emit('update:visible', val)
})

const isEdit = computed(() => !!props.accountId)

const formTitle = computed(() => {
  switch (props.actionType) {
    case 'deposit':
      return '存款'
    case 'withdraw':
      return '取款'
    default:
      return isEdit.value ? '编辑账户' : '新增账户'
  }
})

const submitting = ref(false)

const formData = reactive({
  customerId: null as number | null,
  accountType: 0,
  initialBalance: 0,
  depositAmount: 0,
  withdrawAmount: 0
})

const formRules = computed(() => {
  const rules: any = {}
  
  if (!isEdit.value && props.actionType === 'create') {
    rules.customerId = [{ required: true, message: '请输入客户ID', trigger: 'blur' }]
    rules.initialBalance = [{ required: true, message: '请输入初始余额', trigger: 'blur' }]
  }
  
  if (props.actionType === 'deposit') {
    rules.depositAmount = [
      { required: true, message: '请输入存款金额', trigger: 'blur' },
      { type: 'number', min: 0.01, message: '金额必须大于0', trigger: 'blur' }
    ]
  }
  
  if (props.actionType === 'withdraw') {
    rules.withdrawAmount = [
      { required: true, message: '请输入取款金额', trigger: 'blur' },
      { type: 'number', min: 0.01, message: '金额必须大于0', trigger: 'blur' }
    ]
  }
  
  return rules
})

const handleSubmit = async (data: any) => {
  try {
    submitting.value = true
    
    switch (props.actionType) {
      case 'create':
        await accountApi.createAccount({
          customerId: data.customerId!,
          accountType: data.accountType,
          initialBalance: data.initialBalance
        })
        break
      case 'deposit':
        await accountApi.deposit(props.accountId!, data.depositAmount)
        break
      case 'withdraw':
        await accountApi.withdraw(props.accountId!, data.withdrawAmount)
        break
    }
    
    ElMessage.success(`${formTitle.value}成功`)
    emit('success')
    handleClose()
  } catch (error) {
    ElMessage.error(`${formTitle.value}失败`)
  } finally {
    submitting.value = false
  }
}

const handleClose = () => {
  formVisible.value = false
  // 重置表单数据
  Object.assign(formData, {
    customerId: null,
    accountType: 0,
    initialBalance: 0,
    depositAmount: 0,
    withdrawAmount: 0
  })
}
</script>
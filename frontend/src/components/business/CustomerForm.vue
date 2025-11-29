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
      <el-form-item label="姓名" prop="name">
        <el-input v-model="form.name" />
      </el-form-item>
      <el-form-item label="身份证号" prop="idCardNumber">
        <el-input v-model="form.idCardNumber" />
      </el-form-item>
      <el-form-item label="手机号" prop="phone">
        <el-input v-model="form.phone" />
      </el-form-item>
      <el-form-item label="邮箱" prop="email">
        <el-input v-model="form.email" />
      </el-form-item>
      <el-form-item label="地址" prop="address">
        <el-input v-model="form.address" type="textarea" />
      </el-form-item>
    </template>
  </BaseForm>
</template>

<script setup lang="ts">
import { ref, computed, reactive } from 'vue'
import { ElMessage } from 'element-plus'
import BaseForm from '@/components/base/BaseForm.vue'
import { customerApi } from '@/api/modules/customer'
import { CustomerCreateRequest } from '@/types/customer'

interface Props {
  visible: boolean
  customer?: Partial<CustomerCreateRequest>
}

const props = withDefaults(defineProps<Props>(), {
  visible: false,
  customer: () => ({})
})

const emit = defineEmits<{
  (e: 'update:visible', val: boolean): void
  (e: 'success'): void
}>()

const formVisible = computed({
  get: () => props.visible,
  set: (val) => emit('update:visible', val)
})

const formTitle = computed(() => {
  return props.customer && props.customer.id ? '编辑客户' : '新增客户'
})

const submitting = ref(false)

const formData = reactive<CustomerCreateRequest>({
  name: '',
  idCardNumber: '',
  phone: '',
  email: '',
  address: '',
  ...props.customer
})

const formRules = {
  name: [{ required: true, message: '请输入姓名', trigger: 'blur' }],
  idCardNumber: [{ required: true, message: '请输入身份证号', trigger: 'blur' }],
  phone: [{ required: true, message: '请输入手机号', trigger: 'blur' }]
}

const handleSubmit = async (data: CustomerCreateRequest) => {
  try {
    submitting.value = true
    if (props.customer && props.customer.id) {
      // 编辑客户
      // await customerApi.updateCustomer(props.customer.id, data)
    } else {
      // 新增客户
      await customerApi.createCustomer(data)
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
    name: '',
    idCardNumber: '',
    phone: '',
    email: '',
    address: ''
  })
}
</script>
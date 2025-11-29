<template>
  <el-dialog
    :model-value="visible"
    :title="title"
    :width="width"
    @close="handleClose"
    :before-close="handleBeforeClose"
  >
    <el-form
      ref="formRef"
      :model="model"
      :rules="rules"
      :label-width="labelWidth"
      v-bind="$attrs"
    >
      <slot :form="model" :rules="rules"></slot>
    </el-form>
    
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="handleCancel">取消</el-button>
        <el-button 
          type="primary" 
          @click="handleSubmit" 
          :loading="submitting"
          v-if="!hideSubmit"
        >
          {{ submitText }}
        </el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import type { FormInstance, FormRules } from 'element-plus'

interface Props {
  visible: boolean
  title?: string
  width?: string
  model: Record<string, any>
  rules?: FormRules
  labelWidth?: string
  submitting?: boolean
  submitText?: string
  hideSubmit?: boolean
}

const props = withDefaults(defineProps<Props>(), {
  title: '',
  width: '500px',
  rules: () => ({}),
  labelWidth: '100px',
  submitting: false,
  submitText: '确定',
  hideSubmit: false
})

const emit = defineEmits<{
  (e: 'update:visible', val: boolean): void
  (e: 'submit', val: Record<string, any>): void
  (e: 'cancel'): void
  (e: 'close'): void
}>()

const formRef = ref<FormInstance>()

const handleClose = () => {
  emit('update:visible', false)
  emit('close')
}

const handleBeforeClose = (done: () => void) => {
  // 可以在这里添加关闭前的确认逻辑
  done()
}

const handleCancel = () => {
  emit('cancel')
  handleClose()
}

const handleSubmit = async () => {
  if (!formRef.value) return
  
  await formRef.value.validate((valid) => {
    if (valid) {
      emit('submit', props.model)
    }
  })
}
</script>

<style scoped>
.dialog-footer {
  display: flex;
  justify-content: flex-end;
}
</style>
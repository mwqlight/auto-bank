import { ref, reactive } from 'vue'
import { ElMessage } from 'element-plus'

interface FormOptions<T> {
  submitData: (data: T) => Promise<any>
  resetForm?: () => void
}

export function useForm<T>(options: FormOptions<T>) {
  const submitting = ref(false)
  const formData = reactive<Record<string, any>>({})

  const submitForm = async (data: T) => {
    try {
      submitting.value = true
      await options.submitData(data)
      ElMessage.success('操作成功')
      if (options.resetForm) {
        options.resetForm()
      }
    } catch (error) {
      ElMessage.error('操作失败')
    } finally {
      submitting.value = false
    }
  }

  const resetForm = () => {
    Object.keys(formData).forEach(key => {
      // @ts-ignore
      formData[key] = undefined
    })
    if (options.resetForm) {
      options.resetForm()
    }
  }

  return {
    submitting,
    formData,
    submitForm,
    resetForm
  }
}
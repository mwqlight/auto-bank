import { ref, reactive } from 'vue'
import { ElMessage } from 'element-plus'

interface Pagination {
  page: number
  size: number
  total: number
}

interface TableOptions<T> {
  fetchData: (params: any) => Promise<{ data: T[]; total: number }>
  deleteItem?: (id: number) => Promise<any>
}

export function useTable<T>(options: TableOptions<T>) {
  const loading = ref(false)
  const tableData = ref<T[]>([])
  const pagination = reactive<Pagination>({
    page: 1,
    size: 10,
    total: 0
  })

  const loadData = async () => {
    try {
      loading.value = true
      const params = {
        page: pagination.page,
        size: pagination.size
      }
      const response = await options.fetchData(params)
      tableData.value = response.data
      pagination.total = response.total
    } catch (error) {
      ElMessage.error('数据加载失败')
    } finally {
      loading.value = false
    }
  }

  const handleDelete = async (id: number) => {
    if (!options.deleteItem) {
      ElMessage.warning('未提供删除方法')
      return
    }

    try {
      await options.deleteItem(id)
      ElMessage.success('删除成功')
      loadData()
    } catch (error) {
      ElMessage.error('删除失败')
    }
  }

  const handleSizeChange = (val: number) => {
    pagination.size = val
    loadData()
  }

  const handleCurrentChange = (val: number) => {
    pagination.page = val
    loadData()
  }

  return {
    loading,
    tableData,
    pagination,
    loadData,
    handleDelete,
    handleSizeChange,
    handleCurrentChange
  }
}
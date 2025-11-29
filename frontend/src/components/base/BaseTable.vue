<template>
  <div class="base-table">
    <el-card>
      <template #header>
        <div class="card-header">
          <slot name="header">
            <span>{{ title }}</span>
          </slot>
          <div class="header-actions">
            <slot name="actions"></slot>
          </div>
        </div>
      </template>
      
      <el-table 
        :data="data" 
        v-loading="loading"
        v-bind="$attrs"
        @selection-change="handleSelectionChange"
      >
        <slot></slot>
      </el-table>
      
      <div class="pagination" v-if="showPagination">
        <el-pagination
          v-model:current-page="pagination.page"
          v-model:page-size="pagination.size"
          :page-sizes="[10, 20, 50, 100]"
          :total="pagination.total"
          layout="total, sizes, prev, pager, next, jumper"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
        />
      </div>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'

interface Props {
  title?: string
  data: any[]
  loading?: boolean
  pagination?: {
    page: number
    size: number
    total: number
  }
  showPagination?: boolean
}

const props = withDefaults(defineProps<Props>(), {
  title: '',
  loading: false,
  pagination: () => ({
    page: 1,
    size: 10,
    total: 0
  }),
  showPagination: true
})

const emit = defineEmits<{
  (e: 'selection-change', val: any[]): void
  (e: 'size-change', val: number): void
  (e: 'current-change', val: number): void
}>()

const handleSelectionChange = (val: any[]) => {
  emit('selection-change', val)
}

const handleSizeChange = (val: number) => {
  emit('size-change', val)
}

const handleCurrentChange = (val: number) => {
  emit('current-change', val)
}
</script>

<style scoped>
.base-table {
  width: 100%;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.pagination {
  display: flex;
  justify-content: flex-end;
  margin-top: 20px;
}
</style>
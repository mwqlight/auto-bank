<template>
  <div class="dashboard">
    <h1>银行管理系统仪表盘</h1>
    <el-row :gutter="20">
      <el-col :span="6">
        <el-card class="stat-card">
          <div class="stat-content">
            <h3>客户总数</h3>
            <p class="stat-number">1,234</p>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card class="stat-card">
          <div class="stat-content">
            <h3>账户总数</h3>
            <p class="stat-number">5,678</p>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card class="stat-card">
          <div class="stat-content">
            <h3>今日交易</h3>
            <p class="stat-number">¥1,234,567</p>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card class="stat-card">
          <div class="stat-content">
            <h3>待处理</h3>
            <p class="stat-number">24</p>
          </div>
        </el-card>
      </el-col>
    </el-row>
    
    <el-row :gutter="20" style="margin-top: 20px;">
      <el-col :span="16">
        <el-card>
          <template #header>
            <div class="card-header">
              <span>最近交易</span>
            </div>
          </template>
          <el-table :data="recentTransactions" style="width: 100%">
            <el-table-column prop="transactionNumber" label="交易号" width="180" />
            <el-table-column prop="amount" label="金额" width="120">
              <template #default="scope">
                ¥{{ scope.row.amount }}
              </template>
            </el-table-column>
            <el-table-column prop="transactionType" label="类型" width="120">
              <template #default="scope">
                {{ getTransactionType(scope.row.transactionType) }}
              </template>
            </el-table-column>
            <el-table-column prop="transactionTime" label="时间" width="180" />
            <el-table-column prop="status" label="状态" width="120">
              <template #default="scope">
                <el-tag :type="getTransactionStatusType(scope.row.status)">
                  {{ getTransactionStatus(scope.row.status) }}
                </el-tag>
              </template>
            </el-table-column>
          </el-table>
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card>
          <template #header>
            <div class="card-header">
              <span>账户分布</span>
            </div>
          </template>
          <div ref="chartContainer" style="height: 300px;"></div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import * as echarts from 'echarts'

// Mock data
const recentTransactions = ref([
  {
    transactionNumber: 'TX20230615001',
    amount: 5000,
    transactionType: 0,
    transactionTime: '2023-06-15 14:30:22',
    status: 1
  },
  {
    transactionNumber: 'TX20230615002',
    amount: 12000,
    transactionType: 1,
    transactionTime: '2023-06-15 13:45:10',
    status: 1
  },
  {
    transactionNumber: 'TX20230615003',
    amount: 8000,
    transactionType: 0,
    transactionTime: '2023-06-15 12:15:45',
    status: 2
  }
])

const chartContainer = ref<HTMLDivElement | null>(null)
let chart: echarts.ECharts | null = null

const getTransactionType = (type: number) => {
  const types = ['转账', '存款', '取款', '支付']
  return types[type] || '未知'
}

const getTransactionStatus = (status: number) => {
  const statuses = ['处理中', '成功', '失败', '取消']
  return statuses[status] || '未知'
}

const getTransactionStatusType = (status: number) => {
  const types = ['', 'success', 'danger', 'warning']
  return types[status] || 'info'
}

onMounted(() => {
  if (chartContainer.value) {
    chart = echarts.init(chartContainer.value)
    
    const option = {
      tooltip: {
        trigger: 'item'
      },
      legend: {
        top: '5%',
        left: 'center'
      },
      series: [
        {
          name: '账户类型',
          type: 'pie',
          radius: ['40%', '70%'],
          avoidLabelOverlap: false,
          itemStyle: {
            borderRadius: 10,
            borderColor: '#fff',
            borderWidth: 2
          },
          label: {
            show: false,
            position: 'center'
          },
          emphasis: {
            label: {
              show: true,
              fontSize: 40,
              fontWeight: 'bold'
            }
          },
          labelLine: {
            show: false
          },
          data: [
            { value: 1048, name: '活期账户' },
            { value: 735, name: '定期账户' },
            { value: 580, name: '信用卡账户' },
            { value: 484, name: '投资账户' }
          ]
        }
      ]
    }
    
    chart.setOption(option)
  }
})
</script>

<style scoped>
.dashboard {
  padding: 20px;
}

.stat-card {
  height: 120px;
}

.stat-content {
  text-align: center;
}

.stat-number {
  font-size: 24px;
  font-weight: bold;
  color: #0A2463;
  margin: 10px 0;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>
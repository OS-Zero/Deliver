<script lang="ts" setup>
import { MessageOutlined, FunnelPlotOutlined, FileTextOutlined, UserOutlined } from '@ant-design/icons-vue'
import Echarts from '@/components/Echarts/index.vue'
import { type EChartsOption } from 'echarts'
const chartsMessageOption: EChartsOption = {
  title: {
    text: '消息量',
    top: 10,
    left: 10
  },
  legend: {
    top: '20%'
  },
  tooltip: {},
  dataset: {
    source: [
      ['product', '成功', '失败'],
      ['Matcha Latte', 650, 20],
      ['Milk Tea', 140, 100],
      ['Cheese Cocoa', 86.4, 65.2],
      ['Walnut Brownie', 72.4, 53.9]
    ]
  },
  grid: {
    top: '30%',
    bottom: '10%'
  },
  xAxis: { type: 'category' },
  yAxis: {},
  // Declare several bar series, each will be mapped
  // to a column of dataset.source by default.
  series: [
    { type: 'bar', color: '#5470C6' },
    { type: 'bar', color: '#a90000' }
  ]
}
const chartsTemplateOption: EChartsOption = {
  title: {
    text: '模板使用TOP5',
    left: '10',
    top: '10'
  },
  tooltip: {
    trigger: 'item'
  },
  series: [
    {
      name: 'Access From',
      type: 'pie',
      radius: '50%',
      center: ['50%', '60%'],
      data: [
        { value: 1048, name: 'Search Engine' },
        { value: 735, name: 'Direct' },
        { value: 580, name: 'Email' },
        { value: 484, name: 'Union Ads' },
        { value: 300, name: 'Video Ads' }
      ]
    }
  ]
}
const chartsChannelOption: EChartsOption = {
  title: {
    text: '渠道APP使用TOP5',
    left: '10',
    top: '10'
  },
  tooltip: {
    trigger: 'item'
  },
  series: [
    {
      name: 'Access From',
      type: 'pie',
      radius: '50%',
      center: ['50%', '60%'],
      data: [
        { value: 1048, name: 'Search Engine' },
        { value: 735, name: 'Direct' },
        { value: 580, name: 'Email' },
        { value: 484, name: 'Union Ads' },
        { value: 300, name: 'Video Ads' }
      ],
      emphasis: {
        itemStyle: {
          shadowBlur: 10,
          shadowOffsetX: 0,
          shadowColor: 'rgba(0, 0, 0, 0.5)'
        }
      }
    }
  ]
}
const chartsAccountOption: EChartsOption = {
  title: {
    text: '发送账号TOP10',
    left: '10',
    top: '10'
  },
  tooltip: {
    trigger: 'item'
  },
  series: [
    {
      name: 'Access From',
      type: 'pie',
      radius: ['50%', '60%'],
      center: ['50%', '60%'],
      avoidLabelOverlap: false,
      itemStyle: {
        borderRadius: 10,
        borderColor: '#fff',
        borderWidth: 2
      },
      data: [
        { value: 1048, name: 'Search Engine' },
        { value: 735, name: 'Direct' },
        { value: 580, name: 'Email' },
        { value: 484, name: 'Union Ads' },
        { value: 300, name: 'Video Ads' }
      ]
    }
  ]
}
</script>

<template>
  <div id="dashboard-container">
    <div class="dashboard-info">
      <a-row justify="space-around" align="middle">
        <a-col :span="4">
          <a-card>
            <a-statistic title="今日消息数" :value="100">
              <template #prefix>
                <MessageOutlined />
              </template>
            </a-statistic>
          </a-card>
        </a-col>
        <a-col :span="4">
          <a-card>
            <a-statistic title="模板数" :value="100">
              <template #prefix>
                <FileTextOutlined />
              </template>
            </a-statistic>
          </a-card>
        </a-col>
        <a-col :span="4">
          <a-card>
            <a-statistic title="渠道APP数" :value="100">
              <template #prefix>
                <FunnelPlotOutlined />
              </template>
            </a-statistic>
          </a-card>
        </a-col>
        <a-col :span="4">
          <a-card>
            <a-statistic title="账号数" :value="100">
              <template #prefix>
                <UserOutlined />
              </template>
            </a-statistic>
          </a-card>
        </a-col>
      </a-row>
    </div>
    <div class="dashboard-charts">
      <Echarts name="chartsMessage" :option="chartsMessageOption"></Echarts>
      <Echarts name="chartsTemplate" :option="chartsTemplateOption"></Echarts>
      <Echarts name="chartsChannel" :option="chartsChannelOption"></Echarts>
      <Echarts name="chartsAccount" :option="chartsAccountOption"></Echarts>
    </div>
  </div>
</template>
<style lang="scss" scoped>
#dashboard-container {
  height: 100%;
}
.dashboard-info {
  height: 20%;
}
.dashboard-charts {
  display: flex;
  justify-content: space-around;
  flex-wrap: wrap;
  height: 80%;
  div {
    width: 42%;
    height: 45%;
  }
}
</style>

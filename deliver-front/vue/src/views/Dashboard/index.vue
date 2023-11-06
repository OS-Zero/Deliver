<script lang="ts" setup>
import {
  MessageOutlined,
  FunnelPlotOutlined,
  FileTextOutlined,
  UserOutlined,
  QuestionCircleTwoTone
} from '@ant-design/icons-vue'
import Echarts from '@/components/Echarts/index.vue'
import { type EChartsOption } from 'echarts'
const chartsMessageOption: EChartsOption = {
  legend: {
    top: '20%'
  },
  tooltip: {},
  dataset: {
    source: [
      ['product', '成功', '失败'],
      ['0-4', 650, 20],
      ['4-8', 650, 20],
      ['8-12', 140, 100],
      ['12-16', 86.4, 65.2],
      ['16-20', 72.4, 53.9],
      ['20-24', 650, 20]
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
      <a-row justify="space-between" align="middle" :gutter="24">
        <a-col :span="6">
          <a-card style="height: 96px">
            <div class="card-statistic">
              <span class="icon"><MessageOutlined /></span>
              <a-statistic :value="100">
                <template #title>
                  <span>今日消息量</span>
                  <a-tooltip placement="right">
                    <template #title>
                      <span>今日消息量</span>
                    </template>
                    <question-circle-two-tone style="margin-left: 5px" />
                  </a-tooltip>
                </template>
              </a-statistic>
            </div>
          </a-card>
        </a-col>
        <a-col :span="6">
          <a-card style="height: 96px">
            <div class="card-statistic">
              <span class="icon"><FileTextOutlined /></span>
              <a-statistic :value="100">
                <template #title>
                  <span>平台文件数</span>
                  <a-tooltip placement="right">
                    <template #title>
                      <span>平台文件数</span>
                    </template>
                    <question-circle-two-tone style="margin-left: 5px" />
                  </a-tooltip>
                </template>
              </a-statistic>
            </div>
          </a-card>
        </a-col>
        <a-col :span="6">
          <a-card style="height: 96px">
            <div class="card-statistic">
              <span class="icon"><FunnelPlotOutlined /></span>
              <a-statistic :value="100">
                <template #title>
                  <span>累计模板数</span>
                  <a-tooltip placement="right">
                    <template #title>
                      <span>累计模板数</span>
                    </template>
                    <question-circle-two-tone style="margin-left: 5px" />
                  </a-tooltip>
                </template>
              </a-statistic>
            </div>
          </a-card>
        </a-col>
        <a-col :span="6">
          <a-card style="height: 96px">
            <div class="card-statistic">
              <span class="icon"><UserOutlined /></span>
              <a-statistic :value="100">
                <template #title>
                  <span>渠道APP数</span>
                  <a-tooltip placement="right">
                    <template #title>
                      <span>渠道APP数</span>
                    </template>
                    <question-circle-two-tone style="margin-left: 5px" />
                  </a-tooltip>
                </template>
              </a-statistic>
            </div>
          </a-card>
        </a-col>
      </a-row>
    </div>
    <div class="dashboard-charts">
      <a-row justify="space-between" align="middle" :gutter="[24, 24]">
        <a-col :span="12">
          <a-card>
            <Echarts cardName="消息量" name="chartsMessage" :option="chartsMessageOption"></Echarts>
          </a-card>
        </a-col>
        <a-col :span="12">
          <a-card>
            <Echarts cardName="模板使用Top5" name="chartsTemplate" :option="chartsTemplateOption"></Echarts>
          </a-card>
        </a-col>
        <a-col :span="12">
          <a-card>
            <Echarts cardName="渠道使用Top5" name="chartsChannel" :option="chartsChannelOption"></Echarts>
          </a-card>
        </a-col>
        <a-col :span="12">
          <a-card>
            <Echarts cardName="用户数" name="chartsAccount" :option="chartsAccountOption"></Echarts>
          </a-card>
        </a-col>
      </a-row>
    </div>
  </div>
</template>
<style lang="scss" scoped>
.card-statistic {
  display: flex;
}
.icon {
  height: 50px;
  width: 50px;
  border-radius: 25px;
  line-height: 50px;
  text-align: center;
  font-size: 24px;
  margin-right: 15px;
  color: #1890ff;
  background: #e5f4ff;
}
.dashboard-charts {
  margin-top: 12px;
}
</style>

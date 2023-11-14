import { defineStore } from 'pinia'
import { getDashboardHeadData, getMessageInfo, getTemplateInfo, getAppInfo, getPushUserInfo } from '@/api/dashboard'
import { type EChartsOption } from 'echarts'
const chartsMessageOption: EChartsOption = {
  legend: {
    top: '20%'
  },
  tooltip: {},
  dataset: {},
  grid: {
    top: '30%',
    bottom: '10%'
  },
  xAxis: { type: 'category' },
  yAxis: {},
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
      name: 'TemplateId',
      type: 'pie',
      radius: '50%',
      center: ['50%', '60%'],
      label: {
        alignTo: 'edge',
        formatter: '{name|{b}}\n{value|计数:{c}}',
        minMargin: 5,
        lineHeight: 15,
        rich: {
          time: {
            fontSize: 10,
            color: '#999'
          }
        }
      },
      data: []
    }
  ]
}
const chartsChannelOption: EChartsOption = {
  tooltip: {
    trigger: 'item'
  },
  series: [
    {
      name: 'AppId',
      type: 'pie',
      radius: '50%',
      center: ['50%', '60%'],
      label: {
        alignTo: 'edge',
        formatter: '{name|{b}}\n{value|计数:{c}}',
        minMargin: 5,
        lineHeight: 15,
        rich: {
          time: {
            fontSize: 10,
            color: '#999'
          }
        }
      },
      data: [],
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
      name: 'UserId',
      type: 'pie',
      radius: ['50%', '60%'],
      center: ['50%', '60%'],
      avoidLabelOverlap: false,
      label: {
        alignTo: 'edge',
        formatter: '{name|{b}}\n{value|计数:{c}}',
        minMargin: 5,
        lineHeight: 15,
        rich: {
          time: {
            fontSize: 10,
            color: '#999'
          }
        }
      },
      itemStyle: {
        borderRadius: 10,
        borderColor: '#fff',
        borderWidth: 2
      },
      data: []
    }
  ]
}

export const useDashboardStore = defineStore('dashboard', {
  state: () => {
    return {
      num: 0
    }
  },
  actions: {
    async getDashboardHeadData() {
      const dashboardHeadData = await getDashboardHeadData()
      return dashboardHeadData.data
    },
    async getMessageInfo(dateSelect: number): Promise<EChartsOption> {
      const messageData = await getMessageInfo({ dateSelect })
      if (chartsMessageOption.dataset !== undefined) {
        chartsMessageOption.dataset = {
          dimensions: ['product', '成功', '失败'],
          source: messageData.data.messageInfoList
        }
      }
      return chartsMessageOption
    },
    async getTemplateInfo(dateSelect: number): Promise<EChartsOption> {
      const messageData = await getTemplateInfo({ dateSelect })
      if (chartsTemplateOption.series !== undefined) {
        chartsTemplateOption.series[0].data = messageData.data?.dashboardInfoList
      }
      return chartsTemplateOption
    },
    async getAppInfo(dateSelect: number): Promise<EChartsOption> {
      const messageData = await getAppInfo({ dateSelect })
      if (chartsChannelOption.series !== undefined) {
        chartsChannelOption.series[0].data = messageData.data?.dashboardInfoList
      }
      return chartsChannelOption
    },
    async getPushUserInfo(dateSelect: number): Promise<EChartsOption> {
      const messageData = await getPushUserInfo({ dateSelect })
      if (chartsAccountOption.series !== undefined) {
        chartsAccountOption.series[0].data = messageData.data?.dashboardInfoList
      }
      return chartsAccountOption
    }
  },
  getters: {}
})

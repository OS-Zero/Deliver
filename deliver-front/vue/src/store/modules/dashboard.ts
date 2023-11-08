import { defineStore } from 'pinia'
import { getDashboardHeadData, getMessageInfo, type DashboardHeadData } from '@/api/dashboard'
import { type EChartsOption } from 'echarts'

const chartsMessageOption: EChartsOption = {
  legend: {
    top: '20%'
  },
  tooltip: {},
  dataset: {
    dimensions: ['product', '成功', '失败'],
    source: [
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
  series: [
    { type: 'bar', color: '#5470C6' },
    { type: 'bar', color: '#a90000' }
  ]
}
// const chartsTemplateOption = {
//   tooltip: {
//     trigger: 'item'
//   },
//   series: [
//     {
//       name: 'Access From',
//       type: 'pie',
//       radius: '50%',
//       center: ['50%', '60%'],
//       data: [
//         { value: 1048, name: 'Search Engine' },
//         { value: 735, name: 'Direct' },
//         { value: 580, name: 'Email' },
//         { value: 484, name: 'Union Ads' },
//         { value: 300, name: 'Video Ads' }
//       ]
//     }
//   ]
// }
// const chartsChannelOption = {
//   tooltip: {
//     trigger: 'item'
//   },
//   series: [
//     {
//       name: 'Access From',
//       type: 'pie',
//       radius: '50%',
//       center: ['50%', '60%'],
//       data: [
//         { value: 1048, name: 'Search Engine' },
//         { value: 735, name: 'Direct' },
//         { value: 580, name: 'Email' },
//         { value: 484, name: 'Union Ads' },
//         { value: 300, name: 'Video Ads' }
//       ],
//       emphasis: {
//         itemStyle: {
//           shadowBlur: 10,
//           shadowOffsetX: 0,
//           shadowColor: 'rgba(0, 0, 0, 0.5)'
//         }
//       }
//     }
//   ]
// }
// const chartsAccountOption = {
//   tooltip: {
//     trigger: 'item'
//   },
//   series: [
//     {
//       name: 'Access From',
//       type: 'pie',
//       radius: ['50%', '60%'],
//       center: ['50%', '60%'],
//       avoidLabelOverlap: false,
//       itemStyle: {
//         borderRadius: 10,
//         borderColor: '#fff',
//         borderWidth: 2
//       },
//       data: [
//         { value: 1048, name: 'Search Engine' },
//         { value: 735, name: 'Direct' },
//         { value: 580, name: 'Email' },
//         { value: 484, name: 'Union Ads' },
//         { value: 300, name: 'Video Ads' }
//       ]
//     }
//   ]
// }

export const useDashboardStore = defineStore('dashboard', {
  state: () => {
    return {
      num: 0
    }
  },
  actions: {
    async getDashboardHeadData() {
      const dashboardHeadData: DashboardHeadData = await getDashboardHeadData()
      console.log(dashboardHeadData)
      return dashboardHeadData
    },
    async getMessageInfo(dateSelect: number): Promise<EChartsOption> {
      const messageData: messageDataSource = await getMessageInfo({ dateSelect })
      if (chartsMessageOption.dataset !== undefined) {
        chartsMessageOption.dataset.source = messageData
      }
      return chartsMessageOption
    }
  },
  getters: {}
})

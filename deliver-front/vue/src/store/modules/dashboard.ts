import { defineStore } from 'pinia'
import { getDashboardHeadData, type DashboardHeadData } from '@/api/dashboard'

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
    }
  },
  getters: {}
})

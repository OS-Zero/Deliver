import request from '@/utils/request'

export interface DashboardHeadData {
  numberOfMessagesToday?: string
  numberOfPlatformFiles?: string
  accumulatedTemplateOwnership?: string
  numberOfApps?: string
}

export async function getDashboardHeadData(): Promise<DashboardHeadData> {
  return await request({
    url: '/dashboard/getDashboardHeadData',
    method: 'post'
  })
}

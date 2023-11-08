import request from '@/utils/request'

export interface DashboardHeadData {
  numberOfMessagesToday?: string
  numberOfPlatformFiles?: string
  accumulatedTemplateOwnership?: string
  numberOfApps?: string
}

type barDataPoint = [string, number, number]
interface pieDataPoint {
  value: number
  name: string
}

export type messageDataSource = barDataPoint[]
export type templeteDataSource = pieDataPoint[]
export type channelDataSource = pieDataPoint[]
export type userDataSource = pieDataPoint[]

export async function getDashboardHeadData(): Promise<DashboardHeadData> {
  return await request({
    url: '/dashboard/getDashboardHeadData',
    method: 'post'
  })
}
export async function getMessageInfo(data: { dateSelect: number }): Promise<messageDataSource> {
  return await request({
    url: '/dashboard/getMessageInfo',
    method: 'post',
    data
  })
}

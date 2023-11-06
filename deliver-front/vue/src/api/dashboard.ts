import request from '@/utils/request'

// interface DashboardHeadData {
//   numberOfMessagesToday: string
//   numberOfPlatformFiles: string
//   accumulatedTemplateOwnership: string
//   numberOfApps: string
// }

export async function getDashboardHeadData(): Promise<any> {
  return await request({
    url: '/api/dashboard/getDashboardHeadData',
    method: 'post'
  })
}

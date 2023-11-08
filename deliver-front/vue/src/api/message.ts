import request from '@/utils/request'
import type { searchMessage } from '@/views/Message/type'

export async function getTemplatePages(data: searchMessage): Promise<any> {
  return await request({
    url: '/template/search',
    method: 'post',
    data
  })
}

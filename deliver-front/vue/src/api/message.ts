import request from '@/utils/request'
import type { searchMessage, addTemp } from '@/views/Message/type'

export async function getTemplatePages(data: searchMessage): Promise<any> {
  return await request({
    url: '/template/search',
    method: 'post',
    data
  })
}

export async function getMessageType(data: { channelType: number }): Promise<any> {
  return await request({
    url: '/template/getMessageTypeByChannelType',
    method: 'post',
    data
  })
}

export async function getApp(data: { channelType: number }): Promise<any> {
  return await request({
    url: '/app/getAppByChannelType',
    method: 'post',
    data
  })
}

export async function addTemplatePages(data: addTemp): Promise<any> {
  return await request({
    url: '/template/saveTemplate',
    method: 'post',
    data
  })
}

export async function updateStatus(data: { templateId: number; templateStatus: number }): Promise<any> {
  return await request({
    url: '/template/updateStatusById',
    method: 'post',
    data
  })
}

export async function deleteTemplate(data: string[]): Promise<any> {
  return await request({
    url: '/template/deleteByIds',
    method: 'post',
    data
  })
}

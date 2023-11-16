import type { messageTemplate, updateTemp } from '@/views/Message/type'

export const getDate = (d): string => {
  const date = new Date(d)
  const year = date.getFullYear()
  const month = String(date.getMonth() + 1).padStart(2, '0')
  const day = String(date.getDate()).padStart(2, '0')
  const hour = String(date.getHours()).padStart(2, '0')
  const minute = String(date.getMinutes()).padStart(2, '0')
  const second = String(date.getSeconds()).padStart(2, '0')
  return `${year}-${month}-${day} ${hour}:${minute}:${second}`
}

export const getPushWays = (channelType: string | undefined, messageType: string | undefined): string => {
  const obj = {
    channelType: Number(channelType),
    messageType
  }
  return JSON.stringify(obj)
}

export const getPushRange = (s: string): number => {
  const pusharr = ['不限', '企业内部', '企业外部']
  return pusharr.indexOf(s)
}

export const getChannelType = (s: string): number => {
  const ChannelType = ['电话', '短信', '邮件', '钉钉', '企业微信', '飞书']
  return ChannelType.indexOf(s) + 1
}

export const getUsersType = (s: string): number => {
  const usersarr = ['企业账号', '电话', '邮箱', '平台 UserId']
  return usersarr.indexOf(s)
}

export const getAllMessage = (mod: updateTemp, record: messageTemplate): void => {
  mod.templateId = record.templateId
  mod.templateName = record.templateName
  mod.pushRange = getPushRange(record.pushRange as string)
  mod.usersType = getUsersType(record.usersType as string)
  mod.appId = record.appName
  mod.templateStatus = record.templateStatus as number
  mod.channelType = record.channelType
  mod.messageType = record.messageType
}

// templateId: undefined,
// templateName: '',
// pushRange: undefined,
// usersType: undefined,
// pushWays: '',
// templateStatus: 0,
// appId: undefined,
// channelType: undefined,
// messageType: ''

import { getApp, getMessageType } from '@/api/message'
import type { messageTemplate } from '@/views/Message/type'

interface Channel {
  value: string
  label: string
}

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

export const getAllMessage = (mod, record: messageTemplate): void => {
  // 表格渲染数据
  mod.updateTemp.templateId = record.templateId
  mod.updateTemp.templateName = record.templateName
  mod.updateTemp.pushRange = getPushRange(record.pushRange as string)
  mod.updateTemp.usersType = getUsersType(record.usersType as string) + 1
  mod.updateTemp.appId = record.appName
  mod.updateTemp.templateStatus = record.templateStatus as number
  mod.updateTemp.channelType = record.channelType
  mod.updateTemp.messageType = record.messageType

  // 选项渲染
  mod.messageData.length = 0
  mod.appData.length = 0

  const Data: Channel[] = [
    { value: '1', label: '电话' },
    { value: '2', label: '短信' },
    { value: '3', label: '邮件' },
    { value: '4', label: '钉钉' },
    { value: '5', label: '企业微信' },
    { value: '6', label: '飞书' }
  ]
  const user = getUsersType(record.usersType as string)
  if (user === 1) {
    mod.channelData = [...Data]
  } else if (user === 2) {
    mod.channelData = Data.filter(item => item.value !== '3')
  } else if (user === 3) {
    mod.channelData = Data.filter(item => item.value === '3')
  } else if (user === 4) {
    mod.channelData = Data.slice(3)
  }

  getMessageType({ channelType: getChannelType(record.channelType as string) })
    .then(res => {
      res.data.forEach(item => {
        mod.messageData.push(item)
      })
    })
    .catch(err => {
      console.error('An error occurred:', err)
    })
  getApp({ channelType: getChannelType(record.channelType as string) })
    .then(res => {
      res.data.forEach(item => {
        mod.appData.push(item)
      })
    })
    .catch(err => {
      console.error('An error occurred:', err)
    })
}

export const changeTable = (item): any => {
  item.channelType = JSON.parse(item.pushWays).channelType
  item.messageType = JSON.parse(item.pushWays).messageType
  item.createTime = getDate(item.createTime)
  // eslint-disable-next-line
  item.templateStatus = item.templateStatus === 1 ? true : false
  item.key = item.templateId
  // pushrange
  const pusharr = ['不限', '企业内部', '企业外部']
  item.pushRange = pusharr[item.pushRange]
  // userstype
  const userarr = ['', '企业账号', '电话', '邮箱', '平台 UserId']
  item.usersType = userarr[Number(item.usersType)]
  return item
}

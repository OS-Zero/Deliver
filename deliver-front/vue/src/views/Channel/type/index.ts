import type { Dayjs } from 'dayjs'

export interface appSearch {
  appName: string
  channelType: number
  /**
   * 当前页面序号
   */
  currentPage: number
  /**
   * 页面大小
   */
  pageSize: number
  /**
   * 起始日期
   */
  startTime?: string
  /**
   * 结束日期
   */
  endTime?: string
  /**
   * 时间段
   */
  perid?: [Dayjs, Dayjs]
}

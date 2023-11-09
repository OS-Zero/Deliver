export interface messageTemplate {
  /**
   * 模板id
   */
  templateId: string
  /**
   * 模板名
   */
  templateName: string
  /**
   * 推送范围
   */
  pushRange: number
  /**
   * 用户类型
   */
  usersType: number
  // /**
  //  * 推送方式
  //  */
  pushWays?: string
  /**
   * 渠道选择
   */
  channelType: number
  /**
   * 消息类型
   */
  messageType: string
  /**
   * 模板累计使用数
   */
  useCount: number
  /**
   * 模板状态
   */
  templateStatus: number
  /**
   * 创建用户
   */
  createUser: string
  /**
   * 创建时间
   */
  createTime: string
  /**
   * Appid
   */
  appId: number
  /**
   * App名称
   */
  appName: string
}

// 搜索框接口
export interface searchMessage {
  /**
   * 模板名
   */
  templateName?: string
  /**
   * 推送范围
   */
  pushRange?: number
  /**
   * 用户类型
   */
  usersType?: number
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
}

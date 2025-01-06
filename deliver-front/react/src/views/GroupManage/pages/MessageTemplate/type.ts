export interface Pagination {
  currentPage: number; // 当前页面序号
  pageSize: number; // 页面大小
}

// 搜索框接口
export interface SearchModel {
  templateName?: string; // 模板名
  usersType?: number; // 用户类型
  period?: string; // 日期
  startTime?: string; // 日期开始时间
  endTime?: string; // 日期结束时间
}

// 发送搜索框信息接口
export interface SearchMessage extends SearchModel, Pagination {
  templateStatus?: number | boolean; // 模板状态
}

// 表单数据
export interface MessageTemplate {
  templateId: number; // 模板id
  templateName: string; // 模板名
  templateDescription: string; // 描述
  usersType: number; // 用户类型
  usersTypeName: string; // 用户类型描述
  pushWays?: string; // 推送方式
  channelType: number | string; // 渠道选择
  channelTypeName: string; // 渠道选择描述
  channelProviderType: number;
  channelProviderTypeName: string;
  messageType: number; // 消息类型
  messageTypeName: string; // 消息类型描述
  useCount: number; // 模板累计使用数
  templateStatus: number | boolean; // 模板状态
  createUser: string; // 创建用户
  createTime: string; // 创建时间
  appId: number; // Appid
  appName: string; // App名称
}

// 新增接口
export interface AddTemp {
  templateName: string; // 模板名
  usersType: number | undefined; // 用户类型
  pushWays: string; // 推送方式
  channelType?: string | undefined; // 渠道选择
  messageType?: string; // 消息类型
  appId: number | undefined; // Appid
  templateStatus: number | boolean; // 模板状态
}

// 发送消息接口
export interface SendTestMessage {
  templateId: number; // 模板id
  users: string[]; // 用户列表
  paramMap: JSON; // 传递参数
  retry: number | undefined; // 重试次数
}

export interface SearchParams
  extends Pagination,
    Pick<
      Partial<MessageTemplate>,
      'usersType' | 'channelType' | 'channelProviderType' | 'messageType' | 'templateStatus'
    > {
  startTime?: string;
  endTime?: string;
}

export interface Message {
  messageType: string;
  messageTypeName: string;
}

export interface TestSendMessage {
	templateId: Pick<MessageTemplate, 'templateId'>;
	users: string[];
	paramMap: Record<string, any>;
}
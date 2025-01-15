/**
 * 返回数据通用接口
 */
export interface R<T> {
  code: number;
  data: T;
  errorMessage: string | null;
  records?: Array<any>;
  total?: number;
}

/**
 * 查询数据通用接口
 */
export interface SearchData<T> {
  records: Array<T>;
  total: number;
  size: number;
  current: number;
  orders: Array<any>;
  optimizeCountSql: boolean;
  searchCount: boolean;
  countId: number | null;
  maxLimit: number | null;
  pages: number;
}

/**
 * 分页通用接口
 */
export interface Pagination {
  currentPage: number; // 当前页面序号
  pageSize: number; // 页面大小
}

export interface Channel {
  channelType: number;
  channelTypeName: string;
}

export interface ChannelProvider {
  channelProviderType: number;
  channelProviderTypeName: string;
}

export interface PlatFormFile {
  platformFileType: number;
  platformFileTypeName: string;
}

export interface Users {
  usersType: number;
  usersTypeName: string;
}

export interface Message {
  messageType: string | number;
  messageTypeName: string;
}

export interface App {
  appId: number;
  appName: string;
}

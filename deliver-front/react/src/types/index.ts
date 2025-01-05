/**
 * 返回数据通用接口
 */
export interface R<T> {
  code: number;
  data: T;
  errorMessage: string | null;
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
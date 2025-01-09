import { FormItem } from './form';

/**
 * 返回数据通用接口
 */
export interface R<T> {
	code: number;
	data: T;
	errorMessage: string | null;
}

export interface TableSearchParams {
	currentPage?: number;
	pageSize?: number;
}

export interface TableData<T> {
	records: Array<T>;
	total: number;
	size: number;
	current: number;
	pages: number;
}

export type Schema<T> = Record<string, FormItem<keyof T>>;

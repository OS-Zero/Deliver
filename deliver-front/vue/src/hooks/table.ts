import { TablePaginationConfig } from 'ant-design-vue';
import { reactive } from 'vue';

type Callback = (...args: any[]) => any;
type Options = {
	defaultSize: number;
	pageSizeOptions: string[] | number[];
};
export function usePagination(cb: Callback, options?: Options) {
	const pagination = reactive<TablePaginationConfig>({
		current: 1,
		defaultPageSize: 10,
		pageSize: 10,
		total: 0,
		pageSizeOptions: ['10', '20', '50', '100'],
		showSizeChanger: true,
		showTotal: (total: number) => {
			return `共${total}条数据`;
		},
		onChange: (current: number, pageSize: number) => {
			pagination.current = current;
			pagination.pageSize = pageSize;
			cb();
		},
		onShowSizeChange: (current: number, pageSize: number) => {
			pagination.current = current;
			pagination.pageSize = pageSize;
		},
		...options,
	});
	const setTotal = (value: number) => {
		pagination.total = value;
	};
	const resetPagination = () => {
		pagination.current = 1;
		pagination.pageSize = options?.defaultSize || 10;
	};
	return {
		setTotal,
		pagination,
		resetPagination,
	};
}

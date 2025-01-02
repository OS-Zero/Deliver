import { TablePaginationConfig } from 'ant-design-vue';
import { reactive } from 'vue';

type Callback = () => void;
type Options = {
	defaultSize: number;
	pageSizeOptions: string[] | number[];
};
export function usePagination(cb: Callback, options: Options = { defaultSize: 10, pageSizeOptions: ['10', '20', '50', '100'] }) {
	const pagination = reactive<TablePaginationConfig>({
		current: 1,
		defaultPageSize: options.defaultSize,
		pageSize: options.defaultSize,
		total: 0,
		pageSizeOptions: options.pageSizeOptions,
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
	});
	const setTotal = (value: number) => {
		pagination.total = value;
	};
	return {
		setTotal,
		pagination,
	};
}

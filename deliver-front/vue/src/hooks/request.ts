import { ref, type Ref } from 'vue';
import { debounce, throttle } from 'lodash';
import { LRUCache } from '@/utils/LRUCache';
export interface UseRequestOption<T = any> {
	// 是否开启防抖 时长
	debounce?: boolean;
	debounceInterval?: number;
	// 是否开启节流 时长
	throttle?: boolean;
	throttleInterval?: number;
	// 是否轮询
	polling?: boolean;
	pollingInterval?: number;
	// 是否自动调用
	autoRun?: boolean;
	// 调用完毕可执行的函数
	onFinish?: (data: T) => void;
	onError?: (error: any) => void;
	//缓存时间，单位ms
	cacheTime?: number;
	cacheLimit?: number;
}
export interface UseRequestRequest<T, S> {
	loading: Ref<boolean>;
	data: Ref<S | undefined>;
	run: (params?: T) => void; // 手动请求方法
}

const defaultOption: UseRequestOption = {
	// 是否开启防抖 时长
	debounce: false,
	debounceInterval: 500,
	// 是否开启节流 时长
	throttle: false,
	throttleInterval: 500,
	// 是否自动调用
	autoRun: false,
	// 调用完毕可执行的函数
	onFinish: undefined,
	onError: undefined,
	cacheTime: 0,
	cacheLimit: 20,
};
const getData = <T>(params: T | (() => T)) => {
	return params instanceof Function ? params() : params;
};
const isExpired = (cacheTime: number, lastUpdated: number) => {
	return Date.now() - lastUpdated > cacheTime;
};
const useRequest = <
	ParamType = any, // 参数的类型
	PromiseResponseType = any, //  返回的data的类型
>(
	request: (p: ParamType) => Promise<PromiseResponseType>, // 异步请求函数
	params: ParamType | (() => ParamType), // 参数
	opt?: UseRequestOption<PromiseResponseType>, // 配置项
): UseRequestRequest<ParamType, PromiseResponseType> => {
	const option = Object.assign({}, defaultOption, opt);
	const loading = ref(true);
	const data = ref<PromiseResponseType>();
	let run: (_params?: ParamType) => Promise<void> = async () => {};
	const cache = new LRUCache<string, { data: PromiseResponseType; time: number }>(option.cacheLimit!);
	const rq = (_params: ParamType = getData(params)) => {
		loading.value = true;
		const key = JSON.stringify(_params);
		const cacheVal = cache.get(key);
		const updateData = () => {
			request(_params)
				.then((res) => {
					data.value = res;
					cache.put(key, {
						data: data.value,
						time: Date.now(),
					});
					option.onFinish && option.onFinish(data.value);
					loading.value = false;
				})
				.catch((err) => {
					option.onError && option.onError(err);
				});
		};
		//如果有过期时间并且存在缓存
		if (option.cacheTime && cacheVal) {
			data.value = cacheVal.data;
			//缓存过期、更新缓存、但是仍然使用过期数据
			if (isExpired(option.cacheTime, cacheVal.time)) {
				updateData();
			}
			option.onFinish && option.onFinish(data.value);
			setTimeout(() => {
				loading.value = false;
			}, 100);
			return;
		}
		updateData();
	};
	option.autoRun && rq(getData(params));
	option.debounce && (run = debounce<any>(rq, option.debounceInterval, { leading: true }));
	option.throttle && (run = throttle<any>(rq, option.throttleInterval));
	return {
		run,
		loading,
		data,
	};
};

export default useRequest;

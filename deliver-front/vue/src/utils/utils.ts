import { reactive, watchEffect, WatchHandle } from 'vue';

export function notUndefined(data: any) {
	return typeof data !== 'undefined';
}
export function dynamic<T extends object>(obj: T, effects?: Function[]) {
	const dynamicData = reactive(obj);
	const stopHandle: WatchHandle[] = [];
	effects &&
		effects.forEach((effect) => {
			stopHandle.push(watchEffect(effect.bind(null, dynamicData)));
		});
	const stop = () => {
		stopHandle.forEach((handle) => handle());
	};
	return { dynamicData, stop };
}

export function isAsync(fn: any) {
	return Object.prototype.toString.call(fn) === '[object AsyncFunction]';
}

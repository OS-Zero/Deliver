export function omitProperty(obj: Object, key: string) {
	return JSON.parse(
		JSON.stringify(
			obj,
			Object.keys(obj).filter((e) => e !== key),
		),
	);
}
export function stopPropagation(e: MouseEvent, cb: (...args: any[]) => any) {
	e.stopPropagation();
	cb();
}

export function omitProperty(obj: Object, key: string) {
	return JSON.parse(
		JSON.stringify(
			obj,
			Object.keys(obj).filter((e) => e !== key),
		),
	)
}

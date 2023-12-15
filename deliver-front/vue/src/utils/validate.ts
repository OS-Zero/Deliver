export async function validateList(_rule: any, value: any) {
	if (value.length > 0) {
		return Promise.resolve()
	}
	return Promise.reject('请添加至少一个用户')
}

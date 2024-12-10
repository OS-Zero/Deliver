export async function validateList(_rule: any, value: any) {
	if (value.length > 0) {
		return Promise.resolve()
	}
	return Promise.reject('请添加至少一个用户')
}

export async function validateEmail(_r: any, value: string) {
	if (value === '') return
	const regExp = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/
	return regExp.test(value) ? Promise.resolve() : Promise.reject('邮箱格式错误')
}

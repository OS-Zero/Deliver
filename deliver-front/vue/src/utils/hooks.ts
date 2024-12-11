import { reactive } from 'vue'
import { getVerificationCode } from '@/api/user'
export function useVerify() {
	const state = reactive({
		verifyDisabled: true,
		verifyContent: '获取验证码',
		clock: 10,
	})
	const handleVarify = (userEmail: string) => {
		getVerificationCode({
			userEmail,
		})
		state.verifyDisabled = true
		state.verifyContent = `${state.clock}s 后获取`
		const t = setInterval(() => {
			if (state.clock > 0) {
				state.verifyContent = `${state.clock--}s 后获取`
			} else {
				state.clock = 10
				state.verifyContent = '获取验证码'
				state.verifyDisabled = false
				clearInterval(t)
			}
		}, 1000)
	}
	return { state, handleVarify }
}

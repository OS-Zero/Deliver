import { reactive } from 'vue'
import { getVerificationCode } from '@/api/user'
export function useVerify() {
	const state = reactive({
		verifyDisabled: true,
		verifyContent: '获取验证码',
		clock: 10,
		loading: false,
	})
	let finished: () => void
	const onFinished = (cb: () => void) => {
		finished = cb
	}
	const handleVarify = (userEmail: string) => {
		getVerificationCode({
			userEmail,
		})
		state.loading = true
		state.verifyDisabled = true
		state.verifyContent = `${state.clock}s 后获取`
		const t = setInterval(() => {
			if (state.clock > 0) {
				state.verifyContent = `${state.clock--}s 后获取`
			} else {
				state.clock = 10
				state.loading = false
				state.verifyContent = '获取验证码'
				state.verifyDisabled = false
				finished && finished()
				clearInterval(t)
			}
		}, 1000)
	}
	return { state, handleVarify, onFinished }
}

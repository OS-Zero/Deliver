import { reactive } from 'vue';
const CLOCK = 60;
export function useVerify() {
	const state = reactive({
		verifyContent: '获取验证码',
		clock: CLOCK,
		loading: false,
	});
	const handleVarify = (cb: Function) => {
		cb();
		state.loading = true;
		state.verifyContent = `${state.clock}s 后获取`;
		const t = setInterval(() => {
			if (state.clock > 0) {
				state.verifyContent = `${state.clock--}s 后获取`;
			} else {
				state.clock = CLOCK;
				state.verifyContent = '获取验证码';
				state.loading = false;
				clearInterval(t);
			}
		}, 1000);
	};
	return { state, handleVarify };
}

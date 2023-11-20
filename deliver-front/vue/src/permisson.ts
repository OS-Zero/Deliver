import router from './router'
import { start, close } from './utils/nprogreess'

router.beforeEach(async (to, _from, next) => {
	start()
	document.title = to.meta.title as string
	next()
})
router.afterEach(() => {
	close()
})

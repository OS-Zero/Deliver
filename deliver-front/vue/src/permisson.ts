import router from './router'

router.beforeEach(async (to, _from, next) => {
  document.title = to.meta.title as string
  next()
})

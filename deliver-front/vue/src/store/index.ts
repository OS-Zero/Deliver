import { defineStore } from 'pinia'

export const useStore = defineStore('store', {
  state: () => {
    return {
      collapse: false
    }
  },
  getters: {},
  actions: {
    changeCollapse() {
      this.collapse = !this.collapse
    }
  }
})

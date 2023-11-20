import { defineStore } from 'pinia'

export const useStore = defineStore('store', {
	state: () => {
		return {
			collapse: false
		}
	},
	getters: {},
	actions: {
		getCollapse() {
			return this.collapse
		},
		changeCollapse() {
			this.collapse = !this.collapse
		}
	}
})

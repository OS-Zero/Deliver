import { defineStore } from 'pinia'
import { getTemplatePages } from '@/api/message'
import { SearchModel } from '@/views/Message/type'
export const useMessageStore = defineStore('message', {
	state: () => {
		return {}
	},
	getters: {},
	actions: {
		async getTemplatePages(data: SearchModel) {
			try {
				const res = await getTemplatePages(Object.assign(data, { currentPage: 1, pageSize: 10 }))
				return res.data.records
			} catch (error) {
				console.log(error)
				throw error
			}
		}
	}
})

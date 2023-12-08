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
				res.data.records.forEach((item) => {
					if (item.pushWays) {
						const obj = JSON.parse(item.pushWays)
						item.channelType = obj.channelType
						item.messageType = obj.messageType
					}
				})
				return res.data
			} catch (error) {
				console.log(error)
				throw error
			}
		}
	}
})

import { defineStore } from 'pinia'
import { getTemplatePages, getMessageType, getApp, addTemplatePages, updatetemplate, getMessageParamByMessageType, sendTestMes } from '@/api/message'
import { SearchModel, AddTemp, SendTestMessage } from '@/views/Message/type'
export const useMessageStore = defineStore('message', {
	state: () => {
		return {}
	},
	getters: {},
	actions: {
		async getTemplatePages(data: SearchModel, page?: { currentPage: number; pageSize: number }) {
			try {
				const res = await getTemplatePages(Object.assign(data, page ? page : { currentPage: 1, pageSize: 10 }))
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
		},
		async getMessageType(num: number) {
			try {
				const res = await getMessageType({ channelType: num })
				return res.data.map((item) => {
					return { label: item.name, value: item.code }
				})
			} catch (error) {
				console.log(error)
				throw error
				throw error
			}
		},
		async getApp(num: number) {
			try {
				const res = await getApp({ channelType: num })
				return res.data.map((item) => {
					return { label: item.appName, value: item.appId }
				})
			} catch (error) {
				console.log(error)
				throw error
				throw error
			}
		},
		async addTemplatePages(obj: AddTemp) {
			try {
				obj.pushWays = JSON.stringify({
					channelType: obj.channelType,
					messageType: obj.messageType
				})
				await addTemplatePages(obj)
			} catch (error) {
				console.log(error)
				throw error
			}
		},
		async updatetemplate(obj: AddTemp) {
			try {
				obj.pushWays = JSON.stringify({
					channelType: obj.channelType,
					messageType: obj.messageType
				})
				await updatetemplate(obj)
			} catch (error) {
				console.log(error)
				throw error
			}
		},
		async getMessageParamByMessageType(messageType: number, channelType: number) {
			try {
				const res = await getMessageParamByMessageType({ messageType, channelType })
				return JSON.parse(res.data)
			} catch (error) {
				console.log(error)
				throw error
			}
		},
		async sendTestMes(data: SendTestMessage) {
			try {
				await sendTestMes(data)
			} catch (error) {
				console.log(error)
				throw error
			}
		}
	}
})

import { defineStore } from 'pinia'
import { getDashboardHeadData, getMessageInfo, getTemplateInfo, getAppInfo, getPushUserInfo } from '@/api/dashboard.ts'
import { DashboardHeadData } from '@/views/Dashboard/type'
import { type EChartsOption } from 'echarts'
import { chartsAppOption, chartsMessageOption, chartsPushUserOption, chartsTemplateOption } from '@/config/echart.ts'
export const useDashboardStore = defineStore('dashboard', {
	state: () => {
		return {}
	},
	getters: {},
	actions: {
		/**
		 * 同时获取echarts四个数据
		 * @returns {Promise<any>} 四个echarts配置数据
		 */
		async getDashboardData(): Promise<any> {
			try {
				const res = await Promise.all([this.getMessageInfo(1), this.getTemplateInfo(1), this.getAppInfo(1), this.getPushUserInfo(1)])
				return res
			} catch (error) {
				console.log('Error:', error)
				throw error
			}
		},
		/**
		 * 获取控制面板头部数据
		 * @returns {Promise<DashboardHeadData>} 控制面板头部数据
		 */
		async getDashboardHeadData(): Promise<DashboardHeadData> {
			const dashboardHeadData = await getDashboardHeadData()
			return dashboardHeadData.data
		},
		/**
		 * 获取消息配置数据
		 * @returns {Promise<EChartsOption>} 消息配置数据
		 */
		async getMessageInfo(dateSelect: number): Promise<EChartsOption> {
			const messageData = await getMessageInfo({ dateSelect })
			if (chartsMessageOption.dataset !== undefined) {
				chartsMessageOption.dataset = {
					dimensions: ['product', '成功', '失败'],
					source: messageData.data.messageInfoList
				}
			}
			return chartsMessageOption
		},
		/**
		 * 获取模板配置数据
		 * @returns {Promise<EChartsOption>} 模板配置数据
		 */
		async getTemplateInfo(dateSelect: number): Promise<EChartsOption> {
			const templateInfo = await getTemplateInfo({ dateSelect })
			if (chartsTemplateOption.series !== undefined) {
				chartsTemplateOption.series[0].data = templateInfo.data.dashboardInfoList
			}
			return chartsTemplateOption
		},
		/**
		 * 获取 APP 配置数据
		 * @returns {Promise<EChartsOption>} APP 配置数据
		 */
		async getAppInfo(dateSelect: number): Promise<EChartsOption> {
			const appInfo = await getAppInfo({ dateSelect })
			if (chartsAppOption.series !== undefined) {
				chartsAppOption.series[0].data = appInfo.data.dashboardInfoList
			}
			return chartsAppOption
		},
		/**
		 * 获取推送用户配置数据
		 * @returns {Promise<DashboardHeadData>} 推送用户配置数据
		 */
		async getPushUserInfo(dateSelect: number): Promise<EChartsOption> {
			const pushUserInfo = await getPushUserInfo({ dateSelect })
			if (chartsPushUserOption.series !== undefined) {
				chartsPushUserOption.series[0].data = pushUserInfo.data.dashboardInfoList
			}
			return chartsPushUserOption
		}
	}
})

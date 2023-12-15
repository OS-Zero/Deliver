export interface DashboardHeadData {
	numberOfMessagesToday?: string
	numberOfPlatformFiles?: string
	accumulatedTemplateOwnership?: string
	numberOfApps?: string
}

type MessageInfo = [string, string, string]
export interface MessageInfoList {
	messageInfoList: MessageInfo[]
}

interface DashboardInfo {
	value: number
	name: string
}
export interface DashboardInfoList {
	dashboardInfoList: Array<DashboardInfo>
}

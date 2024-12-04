import { type EChartsOption } from 'echarts'
export interface EChartsOptionData {
	chartsMessageOption?: EChartsOption
	chartsTemplateOption?: EChartsOption
	chartsAppOption?: EChartsOption
	chartsPushUserOption?: EChartsOption
}

export const chartsMessageOption: EChartsOption = {
	legend: {
		top: '20%',
	},
	tooltip: {},
	dataset: {},
	grid: {
		top: '30%',
		bottom: '10%',
	},
	xAxis: { type: 'category' },
	yAxis: {},
	series: [
		{ type: 'bar', color: '#5470C6' },
		{ type: 'bar', color: '#a90000' },
	],
}
export const chartsTemplateOption: EChartsOption = {
	tooltip: {
		trigger: 'item',
	},
	series: [
		{
			name: 'TemplateId',
			type: 'pie',
			radius: '50%',
			center: ['50%', '60%'],
			label: {
				alignTo: 'edge',
				formatter: '{name|{b}}\n{value|计数:{c}}',
				minMargin: 5,
				lineHeight: 15,
				rich: {
					time: {
						fontSize: 10,
						color: '#999',
					},
				},
			},
			data: [],
		},
	],
}
export const chartsAppOption: EChartsOption = {
	tooltip: {
		trigger: 'item',
	},
	series: [
		{
			name: 'AppId',
			type: 'pie',
			radius: '50%',
			center: ['50%', '60%'],
			label: {
				alignTo: 'edge',
				formatter: '{name|{b}}\n{value|计数:{c}}',
				minMargin: 5,
				lineHeight: 15,
				rich: {
					time: {
						fontSize: 10,
						color: '#999',
					},
				},
			},
			data: [],
			emphasis: {
				itemStyle: {
					shadowBlur: 10,
					shadowOffsetX: 0,
					shadowColor: 'rgba(0, 0, 0, 0.5)',
				},
			},
		},
	],
}
export const chartsPushUserOption: EChartsOption = {
	tooltip: {
		trigger: 'item',
	},
	series: [
		{
			name: 'UserId',
			type: 'pie',
			radius: ['50%', '60%'],
			center: ['50%', '60%'],
			avoidLabelOverlap: false,
			label: {
				alignTo: 'edge',
				formatter: '{name|{b}}\n{value|计数:{c}}',
				minMargin: 5,
				lineHeight: 15,
				rich: {
					time: {
						fontSize: 10,
						color: '#999',
					},
				},
			},
			itemStyle: {
				borderRadius: 10,
				borderColor: '#fff',
				borderWidth: 2,
			},
			data: [],
		},
	],
}

<script lang="ts" setup>
import Echarts from '@/components/Echarts/index.vue'
import { useDashboardStore } from '@/store/modules/dashboard'
import { onBeforeMount, reactive, ref } from 'vue'
import { EChartsOptionData } from '@/store/modules/dashboard'
import { globalMap } from '@/utils/mapping'
import { IconFont } from '@/utils/iconFont'
import { DashboardHeadData } from './type'
const dashboardStore = useDashboardStore()
const echartsOptionData = reactive<EChartsOptionData>({
	chartsMessageOption: {},
	chartsTemplateOption: {},
	chartsAppOption: {},
	chartsPushUserOption: {}
})
const dashboardHeadData = ref<DashboardHeadData>({})

/**
 *  将请求的数组变成对象
 * @returns {Promise<void>}
 */
const getEChartsOptionData = async (): Promise<void> => {
	try {
		const res = await dashboardStore.getDashboardData()
		Object.keys(echartsOptionData).forEach((key, index) => (echartsOptionData[key] = res[index]))
	} catch (err) {
		console.log('err', err)
	}
}

/**
 *  请求控制面板头部数据
 * @returns {Promise<void>}
 */
const getDashboardHeadData = async (): Promise<void> => {
	try {
		dashboardHeadData.value = await dashboardStore.getDashboardHeadData()
	} catch (error) {
		console.log(error)
	}
}

// 控制面板头部图标名称
const icons = ['icon-message', 'icon-file', 'icon-template', 'icon-app']

onBeforeMount(() => {
	getDashboardHeadData()
	getEChartsOptionData()
})
</script>

<template>
	<div id="dashboard-container">
		<div class="dashboard-info">
			<a-row justify="space-between" align="middle" :gutter="24">
				<a-col :span="6" v-for="(value, name, index) in dashboardHeadData" :key="name">
					<a-card style="height: 96px">
						<div class="card-statistic">
							<span class="icon">
								<icon-font :type="icons[index]" />
							</span>
							<a-statistic :value="value">
								<template #title>
									<span>{{ globalMap.get(name) }}</span>
									<a-tooltip placement="right">
										<template #title>
											<span>{{ globalMap.get(name) }}</span>
										</template>
									</a-tooltip>
								</template>
							</a-statistic>
						</div>
					</a-card>
				</a-col>
			</a-row>
		</div>
		<div class="dashboard-charts">
			<a-row justify="space-between" align="middle" :gutter="[24, 24]">
				<a-col :span="12" v-for="(value, name) in echartsOptionData" :key="name">
					<a-card>
						<Echarts :cardName="globalMap.get(name)" :name="name" :option="value"></Echarts>
					</a-card>
				</a-col>
			</a-row>
		</div>
	</div>
</template>
<style lang="scss" scoped>
.card-statistic {
	display: flex;
}

.anticon {
	width: 50px;
	height: 50px;
	margin-right: 15px;
	font-size: 25px;
	line-height: 50px;
	color: #1890ff;
	text-align: center;
	background: #e5f4ff;
	border-radius: 25px;
}

.dashboard-charts {
	margin-top: 12px;
}
</style>

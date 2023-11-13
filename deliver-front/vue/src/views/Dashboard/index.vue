<script lang="ts" setup>
import {
  MessageOutlined,
  FunnelPlotOutlined,
  FileTextOutlined,
  AppstoreOutlined,
  QuestionCircleTwoTone
} from '@ant-design/icons-vue'
import Echarts from '@/components/Echarts/index.vue'
import { useDashboardStore } from '@/store/modules/dashboard'
import { type DashboardHeadData } from '@/api/dashboard'
import { onMounted, ref } from 'vue'
import { type EChartsOption } from 'echarts'
const dashboardStore = useDashboardStore()
const dashboardHeadData = ref<DashboardHeadData>({})
const chartsMessageOption = ref<EChartsOption>({})
const chartsTemplateOption = ref<EChartsOption>({})
const chartsApplOption = ref<EChartsOption>({})
const chartsUserOption = ref<EChartsOption>({})
const getDashboardHeadData = async (): Promise<void> => {
  dashboardHeadData.value = await dashboardStore.getDashboardHeadData()
}
const getMessageInfo = async (dataselect: number): Promise<void> => {
  chartsMessageOption.value = await dashboardStore.getMessageInfo(dataselect)
}
const getTemplateInfo = async (dataselect: number): Promise<void> => {
  chartsTemplateOption.value = await dashboardStore.getTemplateInfo(dataselect)
}
const getAppInfo = async (dataselect: number): Promise<void> => {
  chartsApplOption.value = await dashboardStore.getAppInfo(dataselect)
}
const getPushUserInfo = async (dataselect: number): Promise<void> => {
  chartsUserOption.value = await dashboardStore.getPushUserInfo(dataselect)
}
onMounted(async () => {
  await getDashboardHeadData()
  await getMessageInfo(1)
  await getTemplateInfo(1)
  await getAppInfo(1)
  await getPushUserInfo(1)
})
</script>

<template>
  <div id="dashboard-container">
    <div class="dashboard-info">
      <a-row justify="space-between" align="middle" :gutter="24">
        <a-col :span="6">
          <a-card style="height: 96px">
            <div class="card-statistic">
              <span class="icon"><MessageOutlined /></span>
              <a-statistic :value="dashboardHeadData.numberOfMessagesToday">
                <template #title>
                  <span>今日消息量</span>
                  <a-tooltip placement="right">
                    <template #title>
                      <span>今日消息量</span>
                    </template>
                    <question-circle-two-tone style="margin-left: 5px" />
                  </a-tooltip>
                </template>
              </a-statistic>
            </div>
          </a-card>
        </a-col>
        <a-col :span="6">
          <a-card style="height: 96px">
            <div class="card-statistic">
              <span class="icon"><FileTextOutlined /></span>
              <a-statistic :value="dashboardHeadData.numberOfPlatformFiles">
                <template #title>
                  <span>平台文件数</span>
                  <a-tooltip placement="right">
                    <template #title>
                      <span>平台文件数</span>
                    </template>
                    <question-circle-two-tone style="margin-left: 5px" />
                  </a-tooltip>
                </template>
              </a-statistic>
            </div>
          </a-card>
        </a-col>
        <a-col :span="6">
          <a-card style="height: 96px">
            <div class="card-statistic">
              <span class="icon"><FunnelPlotOutlined /></span>
              <a-statistic :value="dashboardHeadData.accumulatedTemplateOwnership">
                <template #title>
                  <span>累计模板数</span>
                  <a-tooltip placement="right">
                    <template #title>
                      <span>累计模板数</span>
                    </template>
                    <question-circle-two-tone style="margin-left: 5px" />
                  </a-tooltip>
                </template>
              </a-statistic>
            </div>
          </a-card>
        </a-col>
        <a-col :span="6">
          <a-card style="height: 96px">
            <div class="card-statistic">
              <span class="icon"><AppstoreOutlined /></span>
              <a-statistic :value="dashboardHeadData.numberOfApps">
                <template #title>
                  <span>渠道 APP 数</span>
                  <a-tooltip placement="right">
                    <template #title>
                      <span>渠道 APP 数</span>
                    </template>
                    <question-circle-two-tone style="margin-left: 5px" />
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
        <a-col :span="12">
          <a-card>
            <Echarts cardName="消息详情" name="chartsMessage" :option="chartsMessageOption"></Echarts>
          </a-card>
        </a-col>
        <a-col :span="12">
          <a-card>
            <Echarts cardName="模板使用 TOP5" name="chartsTemplate" :option="chartsTemplateOption"></Echarts>
          </a-card>
        </a-col>
        <a-col :span="12">
          <a-card>
            <Echarts cardName="渠道 APP 使用 TOP5" name="chartsChannel" :option="chartsApplOption"></Echarts>
          </a-card>
        </a-col>
        <a-col :span="12">
          <a-card>
            <Echarts cardName="推送用户 TOP5" name="chartsAccount" :option="chartsUserOption"></Echarts>
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
.icon {
  height: 50px;
  width: 50px;
  border-radius: 25px;
  line-height: 50px;
  text-align: center;
  font-size: 24px;
  margin-right: 15px;
  color: #1890ff;
  background: #e5f4ff;
}
.dashboard-charts {
  margin-top: 12px;
}
</style>

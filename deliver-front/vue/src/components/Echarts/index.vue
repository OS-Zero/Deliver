<script lang="ts" setup>
import { getCurrentInstance, onMounted, onBeforeUnmount, ref, watch } from 'vue'
import { type ECharts, init } from 'echarts'
import { useDashboardStore } from '@/store/modules/dashboard'
const props = defineProps(['name', 'option', 'cardName'])
const selectValue = ref<number>(1)
let myChart: ECharts
// 重绘图表函数
const resizeHandler = (): void => {
  myChart.resize()
}
// 防抖
const debounce = (fun: () => void, delay: number): (() => void) => {
  let timer: ReturnType<typeof setTimeout> | undefined

  return function () {
    if (timer !== undefined) {
      clearTimeout(timer)
    }

    timer = setTimeout(() => {
      fun()
    }, delay)
  }
}
const cancalDebounce = debounce(resizeHandler, 500)
const initChart = (): void => {
  const instance = getCurrentInstance()
  myChart = init(instance?.refs[props.name] as HTMLElement, null, { renderer: 'svg' })
  myChart.setOption(props.option, { notMerge: true })
  window.addEventListener('resize', cancalDebounce)
}

const dashboardStore = useDashboardStore()
const selectChange = async (e: any): Promise<void> => {
  switch (e.target.name) {
    case 'chartsMessage':
      myChart.setOption(await dashboardStore.getMessageInfo(e.target.value), { notMerge: true })
      break
    case 'chartsTemplate':
      myChart.setOption(await dashboardStore.getTemplateInfo(e.target.value), { notMerge: true })
      break
    case 'chartsChannel':
      myChart.setOption(await dashboardStore.getAppInfo(e.target.value), { notMerge: true })
      break
    case 'chartsAccount':
      myChart.setOption(await dashboardStore.getPushUserInfo(e.target.value), { notMerge: true })
      break
    default:
      break
  }
}

onMounted(() => {
  initChart()
})

// 页面销毁前，销毁事件和实例
onBeforeUnmount(() => {
  window.removeEventListener('resize', cancalDebounce)
  myChart.dispose()
})

watch(props, () => {
  myChart.setOption(props.option, { notMerge: true })
})
</script>
<template>
  <div class="echart-card">
    <div class="echart-header">
      <span class="card-name">{{ cardName }}</span>
      <a-radio-group
        :name="name"
        v-model:value="selectValue"
        button-style="solid"
        style="height: 25px"
        @change="selectChange"
      >
        <a-radio-button :value="1">今日</a-radio-button>
        <a-radio-button :value="2">本周</a-radio-button>
        <a-radio-button :value="3">本月</a-radio-button>
        <a-radio-button :value="4">本年</a-radio-button>
      </a-radio-group>
    </div>
    <div class="echart" :ref="name"></div>
  </div>
</template>

<style lang="scss" scoped>
.echart-card {
  width: 100%;
  height: 100%;
  .echart-header {
    display: flex;
    justify-content: space-between;
    height: 32px;
    .card-name {
      font-weight: 500;
      font-size: 16px;
    }
  }
  .echart {
    width: 100%;
    height: 290px;
  }
}

@media screen and (max-width: 1600px) {
  .ant-picker-range {
    display: none;
  }
}
</style>

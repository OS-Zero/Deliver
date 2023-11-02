<script lang="ts" setup>
import dayjs, { type Dayjs } from 'dayjs'
import { getCurrentInstance, onMounted, onBeforeUnmount, ref } from 'vue'
import { type ECharts, init } from 'echarts'
const props = defineProps(['name', 'option', 'cardName'])
const value1 = ref<string>('a')
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
  myChart.setOption(props.option)
  window.addEventListener('resize', cancalDebounce)
}
onMounted(() => {
  initChart()
})

// 页面销毁前，销毁事件和实例
onBeforeUnmount(() => {
  window.removeEventListener('resize', cancalDebounce)
  myChart.dispose()
})
const dateFormat = 'YYYY/MM/DD'
const dataValue = ref<[Dayjs, Dayjs]>([dayjs('2015/01/01', dateFormat), dayjs('2015/01/01', dateFormat)])
</script>
<template>
  <div class="echart-card">
    <div class="echart-header">
      <span class="card-name">{{ cardName }}</span>
      <a-radio-group v-model:value="value1" button-style="solid" style="height: 25px">
        <a-radio-button value="a">今日</a-radio-button>
        <a-radio-button value="b">本周</a-radio-button>
        <a-radio-button value="c">本月</a-radio-button>
        <a-radio-button value="d">本年</a-radio-button>
      </a-radio-group>
      <a-range-picker v-model:value="dataValue" :format="dateFormat" style="width: 210px" />
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

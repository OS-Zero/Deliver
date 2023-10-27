<script lang="ts" setup>
import { getCurrentInstance, onMounted, defineProps, onBeforeUnmount, ref } from 'vue'
import { type ECharts, init } from 'echarts'
const props = defineProps(['name', 'option'])
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
  myChart = init(instance?.refs[props.name] as HTMLElement)
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
</script>
<template>
  <div class="echart-card">
    <a-radio-group v-model:value="value1" button-style="solid" style="position: absolute; right: 20px; top: 10px">
      <a-radio-button value="a">今日</a-radio-button>
      <a-radio-button value="b">本周</a-radio-button>
      <a-radio-button value="c">本月</a-radio-button>
      <a-radio-button value="d">本年</a-radio-button>
    </a-radio-group>
    <div class="echart" :ref="name"></div>
  </div>
</template>

<style lang="scss" scoped>
.echart-card {
  width: 100%;
  height: 100%;
  border: 1px solid #f0f0f0;
  border-radius: 8px;
  position: relative;
  .a-radio-group {
    width: 100%;
  }
  .echart {
    width: 100%;
    height: 100%;
  }
}
</style>

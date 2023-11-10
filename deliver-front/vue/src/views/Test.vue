<script setup lang="ts">
import { onMounted, type Ref, ref } from 'vue'
import { type ECharts, type EChartsOption, init } from 'echarts'

let chart: ECharts
const chartRef: Ref<HTMLElement | null> = ref(null)

const initChart = (): void => {
  const option: EChartsOption = {
    legend: {},
    tooltip: {},
    dataset: {
      source: [
        ['product', '2015', '2016', '2017'],
        ['Matcha Latte', 43.3, 85.8, 93.7],
        ['Milk Tea', 83.1, 73.4, 55.1],
        ['Cheese Cocoa', 86.4, 65.2, 82.5],
        ['Walnut Brownie', 72.4, 53.9, 39.1]
      ]
    },
    xAxis: { type: 'category' },
    yAxis: {},
    series: [{ type: 'bar' }, { type: 'bar' }, { type: 'bar' }]
  }
  chart.setOption(option)
}

onMounted(() => {
  chart = init(chartRef.value as HTMLElement)
  initChart()
})
</script>

<template>
  <div class="chart" ref="chartRef"></div>
</template>

<style scoped>
.chart {
  width: 100%;
  height: 400px;
}
</style>

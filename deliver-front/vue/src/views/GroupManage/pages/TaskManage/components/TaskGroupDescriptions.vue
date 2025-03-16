<script lang="ts" setup>
import Descriptions from '@/components/Descriptions/index.vue'
import { taskLocale } from '@/config/task';
import { Task } from '@/types/task';
defineProps<{
  record: Task
}>()
const groups = [{
  config: {
    title: '任务信息'
  },
  data: ['taskName', 'taskDescription', 'taskType', 'taskTimeExpression', 'taskMessageParam', 'taskStatus', 'createUser', 'createTime']
}, {
  config: {
    title: '关联信息'
  },
  data: ['templateName', 'peopleGroupName']
}]
</script>

<template>
  <Descriptions :groups="groups" :local="taskLocale" :record="record">
    <template #value="{ item }">
      <template v-if="item.key === 'taskType'">
        {{ item.value === 1 ? '实时任务' : (item.value === 2 ? '定时循环任务' : '定时单次任务') }}
      </template>
      <template v-else-if="item.key === 'taskStatus'">
        {{ item.value ? '开启' : '关闭' }}
        <Status :success="item.value"></Status>
      </template>
      <template v-else-if="item.key === 'taskMessageParam'">
        <highlightjs class="highlightjs" lang="json" :code="JSON.stringify(JSON.parse(item.value), null, 2)">
        </highlightjs>
      </template>
    </template>
  </Descriptions>
</template>

<style lang="scss" scoped>
.highlightjs {
  width: 100%;
}
</style>
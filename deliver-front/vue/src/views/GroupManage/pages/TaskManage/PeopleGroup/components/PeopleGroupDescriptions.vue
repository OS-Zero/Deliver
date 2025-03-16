<script lang="ts" setup>
import Descriptions from '@/components/Descriptions/index.vue'
import { peopleGroupLocale } from '@/config/peopleGroup';
import { PeopleGroup } from '@/types/peopleGroup';
defineProps<{
  record: PeopleGroup
}>()
const groups = [{
  config: {
    title: '人群信息'
  },
  data: ['peopleGroupName', 'peopleGroupDescription', 'peopleGroupList', 'createUser', 'createTime']
}, {
  config: {
    title: '类型信息'
  },
  data: ['usersTypeName']
}]
</script>

<template>
  <Descriptions :groups="groups" :local="peopleGroupLocale" :record="record">
  </Descriptions>
</template>

<style lang="scss" scoped></style>
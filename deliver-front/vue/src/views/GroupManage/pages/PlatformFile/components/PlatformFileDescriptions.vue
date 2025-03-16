<script lang="ts" setup>
import Descriptions from '@/components/Descriptions/index.vue'
import { platformFileLocale } from '@/config/platformFile';
import { PlatformFile } from '@/types/platformFile';
defineProps<{
  record: PlatformFile
}>()

const groups = [{
  config: {
    title: '文件信息'
  },
  data: ['platformFileName', 'platformFileDescription', 'platformFileTypeName', 'platformFileKey', 'platformFileStatus', 'createUser', 'createTime']
}, {
  config: {
    title: '类型信息'
  },
  data: ['channelTypeName']
}, {
  config: {
    title: '关联信息'
  },
  data: ['appName']
}]
</script>

<template>
  <Descriptions :groups="groups" :local="platformFileLocale" :record="record">
    <template #value="{ item }">
      <template v-if="item.key === 'platformFileStatus'">
        {{ item.value ? '开启' : '关闭' }}
        <Status :success="item.value"></Status>
      </template>
    </template>
  </Descriptions>
</template>

<style lang="scss" scoped>
.id--copy {
  color: var(--primary-color);
  margin-left: var(--spacing-xs);
}
</style>
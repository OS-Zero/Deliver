<script lang="ts" setup>
import { messageTemplateLocale } from '@/config/messageTemplate'
import { useClipboard } from '@/hooks/clipboard';
import { MessageTemplate } from '@/types/messageTemplate';
import Descriptions from '@/components/Descriptions/index.vue'
import { CopyOutlined } from '@ant-design/icons-vue';
defineProps<{
  record: MessageTemplate
}>()
const groups = [{
  config: {
    title: '模板信息'
  },
  data: ['templateId', 'templateName', 'templateDescription', 'templateStatus', 'createUser', 'createTime']
}, {
  config: {
    title: '类型信息'
  },
  data: ['usersTypeName', 'channelTypeName', 'channelProviderTypeName', 'messageTypeName']
}, {
  config: {
    title: '关联信息'
  },
  data: ['appName']
}]
const { copy } = useClipboard()
</script>

<template>
  <Descriptions :groups="groups" :local="messageTemplateLocale" :record="record">
    <template #value="{ item }">
      <template v-if="item.key === 'templateId'">
        {{ item.value }}
        <CopyOutlined v-if="item.key === 'templateId'" class="id--copy" @click="copy(item.value)" />
      </template>
      <template v-if="item.key === 'templateStatus'">
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
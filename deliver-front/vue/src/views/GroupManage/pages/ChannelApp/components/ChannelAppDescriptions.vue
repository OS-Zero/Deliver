<script lang="ts" setup>
import { ref } from 'vue';
import { ChannelApp } from '@/types/channelApp';
import Descriptions from '@/components/Descriptions/index.vue'
import { channelAppLocale } from '@/config/channelApp';
import { EyeOutlined, EyeInvisibleOutlined } from '@ant-design/icons-vue';
defineProps<{
  record: ChannelApp
}>()
const groups = [{
  config: {
    title: '应用信息'
  },
  data: ['appName', 'appDescription', 'appConfig', 'appStatus', 'createUser', 'createTime']
}, {
  config: {
    title: '类型信息'
  },
  data: ['channelTypeName', 'channelProviderTypeName']
}]
const hide = ref(true)
</script>

<template>
  <Descriptions :groups="groups" :local="channelAppLocale" :record="record">
    <template #label="{ item }">
      <template v-if="item.key === 'appConfig'">
        {{ item.label }}
        <EyeOutlined v-if="!hide" @click="hide = true" />
        <EyeInvisibleOutlined v-else @click="hide = false" />
      </template>
    </template>
    <template #value="{ item }">
      <template v-if="item.key === 'appStatus'">
        {{ item.value ? '开启' : '关闭' }}
        <Status :success="item.value"></Status>
      </template>
      <template v-if="item.key === 'appConfig'">
        <highlightjs class="highlightjs" lang="json"
          :code="hide ? '***********' : JSON.stringify(JSON.parse(item.value), null, 2)">
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
<script setup lang="ts">
import type { Rule } from 'ant-design-vue/es/form'
import { ref, computed, watch, reactive } from 'vue'
import type { updateTemp } from '../type'
import { getPushWays } from '@/utils/date'

// 新增操作
interface DelayLoading {
  delay: number
}

const modifyTemp: updateTemp = reactive({
  templateId: 0,
  templateName: '',
  pushRange: undefined,
  usersType: undefined,
  pushWays: '',
  templateStatus: 0,
  appId: undefined,
  channelType: undefined,
  messageType: ''
})

const open = ref<boolean>(false)

const labelCol = { span: 4 }

const wrapperCol = { span: 20 }

const iconLoading = ref<boolean | DelayLoading>(false)

const rules: Record<string, Rule[]> = {
  templateName: [
    { required: true, message: '请输入模板名', trigger: 'change' },
    { min: 3, max: 20, message: '长度在 3 到 20 个字符', trigger: 'blur' }
  ],
  pushRange: [{ required: true, message: '请选择推送范围', trigger: 'change' }],
  usersType: [{ required: true, message: '请选择用户类型', trigger: 'change' }],
  channelType: [
    {
      required: true,
      message: '请选择渠道',
      trigger: 'change'
    }
  ]
}

const templateForm = ref()

const emit = defineEmits(['add'])

const handleOk = (): void => {
  // 异步关闭，先添加，渲染成功后关闭
  templateForm.value
    .validate()
    .then(() => {
      // 处理templateItem的pushways
      modifyTemp.pushWays = getPushWays(modifyTemp.channelType, modifyTemp.messageType)
      emit('add')
    })
    .catch(error => {
      console.log('error', error)
    })
}

const handleCancel = (): void => {
  templateForm.value.resetFields()
}

const addModules = (): void => {
  open.value = true
}

const channelData = [
  { value: '1', label: '电话' },
  { value: '2', label: '短信' },
  { value: '3', label: '邮件' },
  { value: '4', label: '钉钉' },
  { value: '5', label: '企业微信' },
  { value: '6', label: '飞书' }
]

const messageData = {
  1: ['text 消息'],
  2: ['text 消息'],
  3: ['text 消息'],
  4: [
    'text 消息',
    '钉钉图片消息',
    '钉钉语音消息',
    '钉钉文件消息',
    '钉钉链接消息',
    '钉钉 OA 消息',
    '钉钉 markdown 消息',
    '钉钉卡片消息'
  ],
  5: [
    'text 消息',
    '企业微信图片消息',
    '企业微信语音消息',
    '企业微信视频消息',
    '企业微信文件消息',
    '企业微信文本卡片消息',
    '企业微信图文消息（mpnews）',
    '企业微信 markdown 消息',
    '企业微信小程序通知消息'
  ],
  6: [
    '消息 text',
    '富文本 post',
    '图片 image',
    '消息卡片 interactive',
    '分享群名片 share_chat',
    '分享个人名片 share_user',
    '语音 audio',
    '视频 media',
    '文件 file',
    '表情包 sticker'
  ]
}

const appData = {
  1: ['电话'],
  2: ['短信'],
  3: ['邮件'],
  4: ['钉钉'],
  5: ['企业微信'],
  6: ['飞书']
}

const mesType = computed(() => {
  if (modifyTemp.channelType !== undefined) {
    const messageType = messageData[modifyTemp.channelType]
    return messageType === undefined ? [] : messageType
  } else {
    return []
  }
})

const appType = computed(() => {
  if (modifyTemp.channelType !== undefined) {
    const appTypeData = appData[modifyTemp.channelType]
    return appTypeData === undefined ? [] : appTypeData
  } else {
    return []
  }
})

watch(
  () => modifyTemp.channelType,
  newVal => {
    if (newVal !== undefined) {
      if (messageData[newVal] !== undefined) {
        modifyTemp.messageType = messageData[newVal][0]
      } else {
        modifyTemp.messageType = ''
        messageData[newVal] = []
      }
      if (appData[newVal] !== undefined) {
        modifyTemp.appId = appData[newVal][0]
      } else {
        modifyTemp.appId = undefined
        appData[newVal] = []
      }
    }
  }
)

// defineExpose({
//   open,
//   modifyTemp,
//   iconLoading
// })
</script>

<template>
  <a-button type="primary" class="addModule" @click="addModules">新增模板</a-button>
  <a-modal v-model:open="open" title="新增模板" width="650px" :footer="null" @cancel="handleCancel">
    <a-form
      ref="templateForm"
      :model="modifyTemp"
      :rules="rules"
      :label-col="labelCol"
      :wrapper-col="wrapperCol"
      class="temform"
    >
      <a-form-item ref="templateName" label="模板名" name="templateName" class="tem-item">
        <a-input
          v-model:value="modifyTemp.templateName"
          placeholder="请填写长度在3到20个字符的模板名"
          style="width: 70%"
        />
      </a-form-item>
      <a-form-item label="推送范围" name="pushRange" class="tem-item">
        <a-radio-group v-model:value="modifyTemp.pushRange" button-style="solid">
          <a-radio-button :value="0">不限</a-radio-button>
          <a-radio-button :value="1">企业内部</a-radio-button>
          <a-radio-button :value="2">企业外部</a-radio-button>
        </a-radio-group>
      </a-form-item>
      <a-form-item label="用户类型" name="usersType" class="tem-item">
        <a-radio-group v-model:value="modifyTemp.usersType" button-style="solid">
          <a-radio-button :value="1">企业账号</a-radio-button>
          <a-radio-button :value="2">电话</a-radio-button>
          <a-radio-button :value="3">邮箱</a-radio-button>
          <a-radio-button :value="4">平台 UserId</a-radio-button>
        </a-radio-group>
      </a-form-item>
      <a-form-item label="渠道选择" name="channelType" class="tem-item">
        <a-select
          v-model:value="modifyTemp.channelType"
          :options="channelData.map(pro => ({ value: pro.value, label: pro.label }))"
          style="width: 70%"
        />
      </a-form-item>
      <a-form-item label="渠道 App" name="appId" class="tem-item">
        <a-select
          v-model:value="modifyTemp.appId"
          :options="appType.map((pro: any) => ({ value: pro }))"
          style="width: 70%"
        />
      </a-form-item>
      <a-form-item label="消息类型" name="messageType" class="tem-item">
        <a-select
          v-model:value="modifyTemp.messageType"
          :options="mesType.map((pro: any) => ({ value: pro }))"
          style="width: 70%"
        />
      </a-form-item>
      <a-form-item :wrapper-col="{ span: 14, offset: 4 }" class="tem-item">
        <a-button type="primary" @click="handleOk" :loading="iconLoading">确认新建</a-button>
        <a-button style="margin-left: 10px" @click="handleCancel">重置</a-button>
      </a-form-item>
    </a-form>
  </a-modal>
</template>

<style scoped>
.addModule {
  margin: 0px 20px;
}

.temform {
  .tem-item {
    margin-top: 20px;
  }

  .tem-item:nth-child(7) {
    text-align: right;
    margin-left: 300px;
  }
}
</style>

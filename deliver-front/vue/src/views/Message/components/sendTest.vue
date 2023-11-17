<script setup lang="ts">
import { reactive, ref, defineProps } from 'vue'
import { message, type DrawerProps, type FormInstance } from 'ant-design-vue'
import type { sendMessageTest } from '../type'
import type { Rule } from 'ant-design-vue/es/form'
import JsonEditorVue from 'json-editor-vue3'
import { sendTestMes } from '@/api/message'

const props = defineProps({
  test: String
})

const placement = ref<DrawerProps['placement']>('right')

const jsonstr = ref('{ "content": {} }')

const jsonobj = ref(JSON.parse(jsonstr.value))

const sendTestTable = reactive<sendMessageTest>({
  templateId: Number(props.test),
  users: [] as string[],
  paramMap: jsonobj.value,
  retry: undefined
})

const open = ref<boolean>(false)

const sendtest = ref<FormInstance>()

const userItem = ref<string>('')

const iconLoading = ref(false)

// JSON处理

const options = ref({
  search: false,
  history: false
})

const modeList = ref(['code']) // 可选模式

const remarkValidate = (): void => {
  const newjsonstr = JSON.stringify(jsonobj.value)
  if (jsonstr.value !== newjsonstr) {
    jsonstr.value = newjsonstr
  }
}

const showDrawer = (): void => {
  open.value = true
}

const onClose = (): void => {
  open.value = false
}

const addUser = (): void => {
  if (userItem.value !== '') {
    if (!sendTestTable.users.includes(userItem.value) || sendTestTable.users.length === 0) {
      sendTestTable.users.push(userItem.value)
      userItem.value = ''
    }
  }
  userItem.value = ''
}

const searchMes = (): void => {
  sendtest.value
    ?.validate()
    .then(() => {
      iconLoading.value = true
      sendTestMes(sendTestTable)
        .then(res => {
          if (res.code === 200) {
            void message.success('发送成功~ (*^▽^*)')
            onClose()
          } else {
            void message.error('发送失败，请检查网络~ (＞︿＜)')
          }
          iconLoading.value = false
        })
        .catch(err => {
          console.error('An error occurred:', err)
        })
    })
    .catch(error => {
      console.log('error', error)
    })
}

const clearForm = (): void => {
  userItem.value = ''
  sendtest.value?.resetFields()
}

const rules: Record<string, Rule[]> = {
  users: [{ required: true, message: '请添加至少一个用户', trigger: 'change' }],
  paramMap: [{ required: true, message: '请输入请求参数', trigger: 'change' }]
}
</script>

<template>
  <a-button type="link" class="btn-manager" size="small" style="font-size: 14px; margin-left: -5px" @click="showDrawer">
    测试发送
  </a-button>
  <a-drawer title="测试发送" :placement="placement" :closable="true" :open="open" @close="onClose" :width="660">
    <a-form ref="sendtest" :model="sendTestTable" :rules="rules">
      <a-form-item label="用户 ID" name="userItem" style="margin-left: 22px">
        <a-input-group compact>
          <a-input v-model:value="userItem" placeholder="请输入用户 ID" style="width: 450px"></a-input>
          <a-button type="primary" @click="addUser">添加</a-button>
        </a-input-group>
      </a-form-item>
      <a-form-item label="用户列表" name="users">
        <a-select
          v-model:value="sendTestTable.users"
          mode="multiple"
          style="width: 100%"
          :max-tag-text-length="11"
          :open="false"
        ></a-select>
      </a-form-item>
      <a-form-item label="发送参数" name="paramMap">
        <json-editor-vue
          class="editor"
          v-model="sendTestTable.paramMap"
          @blur="remarkValidate"
          currentMode="code"
          :modeList="modeList"
          :options="options"
        />
      </a-form-item>
      <a-form-item label="重试次数" name="retry" style="margin-left: 10px">
        <a-input-number id="inputNumber" v-model:value="sendTestTable.retry" :max="3" />
      </a-form-item>
    </a-form>
    <template #extra>
      <a-button type="primary" html-type="submit" @click="searchMes" :loading="iconLoading">发送</a-button>
      <a-button style="margin: 0 8px" @click="clearForm">清空</a-button>
    </template>
  </a-drawer>
</template>

<style scoped>
.btn-manager {
  margin-right: 10px;
}
</style>

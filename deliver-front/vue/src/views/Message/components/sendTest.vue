<script setup lang="ts">
import { reactive, ref } from 'vue'
import type { DrawerProps, FormInstance } from 'ant-design-vue'
import { UserOutlined } from '@ant-design/icons-vue'
import type { sendMessageTest } from '../type'
import type { Rule } from 'ant-design-vue/es/form'

const placement = ref<DrawerProps['placement']>('right')

const sendTestTable = reactive<sendMessageTest>({
  templateId: -1,
  users: [] as string[],
  paramMap: '',
  retry: undefined
})

const open = ref<boolean>(false)

const sendtest = ref<FormInstance>()

const userItem = ref<string>('')

const iconLoading = ref(false)

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
      console.log(sendTestTable)
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
  paramMap: [{ required: true, message: '请输入请求参数', trigger: 'change' }],
  retry: [{ required: true, message: '请选择重试次数', trigger: 'change' }]
}
</script>

<template>
  <a-button type="link" class="btn-manager" size="small" style="font-size: 14px; margin-left: -5px" @click="showDrawer">
    测试发送
  </a-button>
  <a-drawer title="测试发送" :placement="placement" :closable="true" :open="open" @close="onClose" :width="660">
    <a-form ref="sendtest" :model="sendTestTable" :rules="rules">
      <a-form-item label="用户 Id" name="userItem" style="margin-left: 22px">
        <a-input-group compact>
          <a-input v-model:value="userItem" placeholder="请输入用户 Id" style="width: 470px">
            <template #prefix>
              <user-outlined />
            </template>
          </a-input>
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
        <a-textarea
          v-model:value="sendTestTable.paramMap"
          placeholder="请输入 JSON 类型请求参数"
          allow-clear
          :autosize="{ minRows: 6 }"
        />
      </a-form-item>
      <a-form-item label="重试次数" name="retry">
        <a-input-number id="inputNumber" v-model:value="sendTestTable.retry" :min="1" :max="3" />
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

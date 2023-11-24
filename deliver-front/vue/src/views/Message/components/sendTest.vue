<script setup lang="ts">
import { reactive, ref } from 'vue'
import { message, type DrawerProps, type FormInstance } from 'ant-design-vue'
import type { sendMessageTest } from '../type'
import type { Rule } from 'ant-design-vue/es/form'
import JsonEditorVue from 'json-editor-vue3'
import { sendTestMes } from '@/api/message'

const props = defineProps({
	test: Number
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
	clearForm()
}

const addUserFlag = ref<boolean>(true)

const addUser = (): void => {
	if (userItem.value.trim() !== '') {
		if (!sendTestTable.users.includes(userItem.value) || sendTestTable.users.length === 0) {
			sendTestTable.users.push(userItem.value)
			// 模拟提交，更新视图
			sendtest.value?.validate('users').then(() => {})
			addUserFlag.value = true
		} else {
			message.warn('已存在此用户 ID')
		}
		userItem.value = ''
	} else {
		message.error('请输入用户 ID')
	}
}

const deleteUserItem = (userItem): void => {
	const indexToDelete = sendTestTable.users.indexOf(userItem)
	if (indexToDelete !== -1) {
		sendTestTable.users.splice(indexToDelete, 1)
	}
}

const changeAddUser = (): void => {
	addUserFlag.value = false
}

const timeOut = ref<number>(3)

const showTime = ref<boolean>(false)

const searchMes = (): void => {
	sendtest.value
		?.validate()
		.then(() => {
			iconLoading.value = true
			console.log(sendTestTable.users)
			sendTestMes(sendTestTable)
				.then((res) => {
					if (res.code === 200) {
						void message.success('发送成功~ (*^▽^*)')
						// onClose()
					}
					showTime.value = true
					const setIntervals = setInterval(() => {
						timeOut.value = timeOut.value - 1
						if (timeOut.value === 0) {
							showTime.value = false
							iconLoading.value = false
							clearInterval(setIntervals)
							timeOut.value = 3
						}
					}, 1000)
				})
				.catch(() => {})
		})
		.catch((error) => {
			console.log('error', error)
		})
}

const clearForm = (): void => {
	userItem.value = ''
	sendtest.value?.resetFields()
}

const validatePass = async (): Promise<any> => {
	if (sendTestTable.users.length === 0) {
		throw new Error('请添加至少一个用户')
	}
	await Promise.resolve()
}

const rules: Record<string, Rule[]> = {
	users: [{ required: true, validator: validatePass, trigger: 'change' }],
	paramMap: [{ required: true, message: '请输入请求参数', trigger: 'change' }]
}
</script>

<template>
	<a-button
		type="link"
		class="btn-manager"
		size="small"
		style="font-size: 14px; margin-left: -5px"
		@click="showDrawer">
		测试发送
	</a-button>
	<a-drawer
		title="测试消息模版发送"
		:placement="placement"
		:closable="true"
		:open="open"
		@close="onClose"
		:width="660">
		<a-form ref="sendtest" :model="sendTestTable" :rules="rules">
			<a-form-item label="添加用户" name="userItem" style="margin-left: 10px">
				<a-button type="primary" v-if="addUserFlag" @click="changeAddUser">添加发送用户</a-button>
				<a-input-group compact v-if="!addUserFlag">
					<a-input
						v-model:value="userItem"
						:maxlength="100"
						placeholder="请输入用户 ID 添加至用户列表"
						style="width: 452px"></a-input>
					<a-button type="primary" @click="addUser">添加</a-button>
				</a-input-group>
			</a-form-item>
			<a-form-item label="用户列表" name="users">
				<a-list bordered v-model:value="sendTestTable.users" :data-source="sendTestTable.users">
					<template #renderItem="{ item }">
						<a-list-item>
							<div style="width: 80%; word-wrap: break-word; overflow-wrap: break-word">
								{{ item }}
							</div>
							<template #actions>
								<a @click="deleteUserItem(item)">删除</a>
							</template>
						</a-list-item>
					</template>
					<template #header>
						<div style="color: #1677ff">用户 ID 列表</div>
					</template>
				</a-list>
			</a-form-item>
			<a-form-item label="发送参数" name="paramMap">
				<json-editor-vue
					class="editor"
					v-model="sendTestTable.paramMap"
					@blur="remarkValidate"
					currentMode="code"
					:modeList="modeList"
					:options="options"
					language="cn" />
			</a-form-item>
			<a-form-item label="重试次数" name="retry" style="margin-left: 10px">
				<a-input-number id="inputNumber" v-model:value="sendTestTable.retry" :max="3" />
			</a-form-item>
		</a-form>
		<template #extra>
			<a-button style="margin: 0 8px" @click="clearForm">清空</a-button>
			<a-button type="primary" html-type="submit" @click="searchMes" :loading="iconLoading">
				<template v-if="showTime">{{ timeOut }}s</template>
				<template v-else>发送</template>
			</a-button>
		</template>
	</a-drawer>
</template>

<style scoped>
.btn-manager {
	margin-right: 10px;
}
</style>

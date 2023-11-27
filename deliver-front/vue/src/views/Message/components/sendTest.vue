<script setup lang="ts">
import { onMounted, reactive, ref, watch } from 'vue'
import { message, type DrawerProps, type FormInstance } from 'ant-design-vue'
import type { sendMessageTest } from '../type'
import type { Rule } from 'ant-design-vue/es/form'
import JsonEditorVue from 'json-editor-vue3'
import { getMessageParamByMessageType, sendTestMes } from '@/api/message'
import { ApiTwoTone, PlusSquareOutlined } from '@ant-design/icons-vue'
const props = defineProps({
	test: Number,
	messageType: String,
	channelType: String
})

const placement = ref<DrawerProps['placement']>('right')

const jsonstr = ref('{}')

const sendTestTable = reactive<sendMessageTest>({
	templateId: Number(props.test),
	users: [] as string[],
	paramMap: JSON.parse(jsonstr.value),
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

const changeAddUser = (flag: boolean): void => {
	addUserFlag.value = flag
	if (flag == true) {
		userItem.value = ''
	}
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
	addUserFlag.value = true
	sendtest.value?.resetFields()
	sendTestTable.paramMap = JSON.parse(jsonstr.value)
}
const jsonChange = () => {
	sendtest.value?.validate('paramMap').then(() => {})
}
const validatePass = async (): Promise<any> => {
	if (sendTestTable.users.length === 0) {
		throw new Error('请添加至少一个用户')
	}
	await Promise.resolve()
}
const remarkValidate = async (): Promise<any> => {
	const newjsonstr = JSON.stringify(sendTestTable.paramMap)
	if ('{}' == newjsonstr || newjsonstr == jsonstr.value) {
		throw new Error('请输入正确的请求参数')
	}
	await Promise.resolve()
}
const rules: Record<string, Rule[]> = {
	users: [{ required: true, validator: validatePass, trigger: 'change' }],
	paramMap: [{ required: true, validator: remarkValidate, trigger: 'change' }]
}
onMounted(() => {})
watch(open, (newValue) => {
	if (newValue == true) {
		getMessageParamByMessageType({
			messageType: props.messageType ? props.messageType : '',
			channelType: props.channelType ? props.channelType : ''
		})
			.then((res) => {
				sendTestTable.paramMap = JSON.parse(res.data)
				jsonstr.value = JSON.stringify(sendTestTable.paramMap)
			})
			.catch((err) => {
				console.error('An error occurred:', err)
			})
	}
})
</script>

<template>
	<a-button
		type="link"
		class="btn-manager"
		size="small"
		style="font-size: 14px; margin-left: -5px"
		@click="showDrawer">
		<a-tooltip title="测试消息模版发送">
			<ApiTwoTone style="font-size: 18px" />
		</a-tooltip>
	</a-button>
	<a-drawer
		title="测试消息模版发送"
		:placement="placement"
		:closable="true"
		:open="open"
		@close="onClose"
		:width="660">
		<a-form ref="sendtest" :model="sendTestTable" :rules="rules">
			<a-form-item label="用户列表" name="users">
				<a-list
					:locale="{ emptyText: ' ' }"
					bordered
					v-model:value="sendTestTable.users"
					:data-source="sendTestTable.users">
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
					<template #header><span style="color: #3883fa">用户 ID 列表</span></template>
					<template #footer>
						<a-form-item label="" name="userItem" style="text-align: center">
							<a v-if="addUserFlag" @click="changeAddUser(false)">
								<a-tooltip title="添加用户">
									<PlusSquareOutlined style="font-size: 40px; color: #c4c3c3" />
								</a-tooltip>
							</a>

							<a-input-group compact v-if="!addUserFlag">
								<a-input
									v-model:value="userItem"
									:maxlength="100"
									placeholder="请输入用户 ID 添加至用户列表"
									style="width: 332px; text-align: left"></a-input>
								<a-button @click="changeAddUser(true)">取消</a-button>
								<a-button type="primary" @click="addUser">确认</a-button>
							</a-input-group>
						</a-form-item>
					</template>
				</a-list>
			</a-form-item>
			<a-form-item label="发送参数" name="paramMap">
				<json-editor-vue
					class="editor"
					v-model="sendTestTable.paramMap"
					currentMode="code"
					:modeList="modeList"
					:options="options"
					@change="jsonChange"
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

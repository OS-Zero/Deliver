<script lang="ts" setup>
import { savePeopleGroup, updatePeopleGroup } from '@/api/peopleGroup';
import { messageTemplateLocale, messageTemplateSchema, messageTemplateSchemaDeps, testMessageSchema } from '@/config/messageTemplate';
import { DrawerProps } from '@/types/components';
import { copyToClipboard, dynamic, getDataFromSchema } from '@/utils/utils';
import { message } from 'ant-design-vue';
import { ref, reactive, onUnmounted, watch, nextTick } from 'vue';
import { CopyOutlined } from '@ant-design/icons-vue';
import { getMessageParam } from '@/api/system';
import { testSendMessage } from '@/api/messageTemplate';
type Operation = 'add' | 'edit' | 'more' | 'testSend'
const props = defineProps<{
	open: boolean
	operation: Operation
	record: Record<string, any>
}>()
const emit = defineEmits(['close'])
const formRef = ref()

const titleList: Record<Operation, string> = {
	add: '新增模板',
	edit: '编辑模板',
	more: '模板详情',
	testSend: '测试发送'
}
const copyId = async (text: string) => {
	try {
		await copyToClipboard(text)
		message.success('复制成功')
	} catch (error) {
		message.error('复制失败')
	}
}
const drawerState = reactive<DrawerProps>({
	open: props.open,
	title: titleList[props.operation],
	placement: props.operation === 'more' ? 'left' : 'right',
	extra: props.operation === 'more' ? false : true,
})
watch(props, (newProps) => {
	Object.assign(drawerState, {
		open: newProps.open,
		title: titleList[newProps.operation],
		placement: newProps.operation === 'more' ? 'left' : 'right',
		extra: props.operation === 'more' ? false : true,
	});
	newProps.operation === 'edit' && newProps.open === true && initFormDate();
	newProps.operation === 'more' && initMoreDate();
	newProps.operation === 'testSend' && initTestMessageFormDate();
})

const { dynamicData: messageTemplateForm, stop } = dynamic(messageTemplateSchema, messageTemplateSchemaDeps)
const testMessageForm = reactive(testMessageSchema)
const initFormDate = () => {
	nextTick(() => {
		for (const key in messageTemplateForm) {
			messageTemplateForm[key].value = props.record[key]
		}
	})
}
const initTestMessageFormDate = () => {
	nextTick(() => {
		testMessageForm.users.value = []
	})
	getMessageParam({ messageType: props.record.messageType, channelType: props.record.channelType }).then(res => {
		testMessageForm.paramMap.value = JSON.parse(res || '{}')
	})
}
const initMoreDate = () => {
	const set = new Set(['usersType', 'channelType', 'channelProviderType', 'messageType', 'appId'])
	const arr: Array<{ label: string; value: any }> = []
	for (const key in props.record) {
		if (!set.has(key)) {
			arr.push({
				label: messageTemplateLocale[key],
				value: props.record[key]
			})
		}
	}
	Object.assign(moreInfo, arr)
}
const moreInfo = reactive<Array<{ label: string; value: any }>>([])
const operationDispatch = {
	add: async () => {
		await savePeopleGroup(getDataFromSchema(messageTemplateForm))
		message.success('新增成功')
	},
	edit: async () => {
		await updatePeopleGroup(getDataFromSchema(messageTemplateForm))
		message.success('编辑成功')
	},
	testSend: async () => {
		await testSendMessage(getDataFromSchema(testMessageForm))
	}
}

const handleCancel = () => {
	props.operation !== 'more' && formRef.value.resetFields()
	drawerState.open = false
	emit('close')
}
onUnmounted(() => {
	stop()
})

</script>

<template>
	<Drawer v-bind="drawerState" @ok="operationDispatch[operation]" @close="handleCancel">
		<Form ref="formRef" v-if="operation === 'add' || operation === 'edit'" :form-schema="messageTemplateForm" />
		<Form ref="formRef" v-else-if="operation === 'testSend'" :form-schema="testMessageForm" />
		<div v-else-if="operation === 'more'">
			<Descriptions :data="moreInfo" :config="{ column: 1 }">
				<template #content="{ item }">
					<template v-if="item.label === '模板 Id'">
						{{ item.value }}
						<CopyOutlined v-if="item.label === '模板 Id'" class="id--copy" @click="copyId(item.value)" />
					</template>
					<template v-if="item.label === '模板状态'">
						{{ !!item.value ? '开启' : '关闭' }}
					</template>
				</template>
			</Descriptions>
		</div>
	</Drawer>
</template>

<style lang="scss" scoped></style>

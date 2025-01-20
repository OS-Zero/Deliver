<script lang="ts" setup>
import { DrawerProps } from '@/types/components';
import { getDataFromSchema } from '@/utils/utils';
import { message } from 'ant-design-vue';
import { ref, reactive, watch, nextTick, onBeforeMount } from 'vue';
import { channelAppLocale, channelAppForm, setOptionsDispatch } from '@/config/channelApp';
import { saveChannelApp, updateChannelApp } from '@/api/channelApp';
import { EyeOutlined, EyeInvisibleOutlined } from '@ant-design/icons-vue';
type Operation = 'add' | 'edit' | 'more'
const props = defineProps<{
	open: boolean
	operation: Operation
	record: Record<string, any>
}>()
const emit = defineEmits(['close'])
const formRef = ref()

const titleList: Record<Operation, string> = {
	add: '新增应用',
	edit: '编辑应用',
	more: '应用详情',
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
	newProps.operation === 'edit' && newProps.open && initFormDate();
	newProps.operation === 'more' && newProps.open && initMoreDate();
})

const initFormDate = () => {
	nextTick(() => {
		for (const key in channelAppForm) {
			if (key === 'appConfig') channelAppForm[key].value = JSON.parse(props.record[key] || "{}")
			else channelAppForm[key].value = props.record[key]
		}
		setOptionsDispatch['channelType']();
		setOptionsDispatch['channelProviderType']({ channelType: channelAppForm.channelType.value });
	})
}
const groups = reactive<any>([])
const initMoreDate = () => {
	const obj = {
		appInfo: ['appName', 'appDescription', 'appConfig', 'appStatus', 'createUser', 'createTime'],
		typeInfo: ['channelTypeName', 'channelProviderTypeName'],
	}
	for (const key in obj) {
		let group: any = {
			config: {
				title: ''
			},
			data: []
		}
		if (key === 'appInfo') {
			group.config.title = '应用信息'
		} else if (key === 'typeInfo') {
			group.config.title = '类型信息'
		}
		obj[key].forEach((name: string) => {
			if (name === 'appConfig') {
				group.data.push({
					label: channelAppLocale[name],
					value: props.record[name],
					extra: {
						close: true
					}
				})
			} else {
				group.data.push({
					label: channelAppLocale[name],
					value: props.record[name]
				})
			}
		});
		groups.push(group)
	}
}
const operationDispatch = {
	add: async () => {
		await formRef.value.validate()
		await saveChannelApp(getDataFromSchema(channelAppForm))
		message.success('新增成功')
		handleCancel(true)
	},
	edit: async () => {
		await formRef.value.validate()
		await updateChannelApp(getDataFromSchema(channelAppForm))
		message.success('编辑成功')
		handleCancel(true)
	},
}

const handleCancel = (flash: boolean = false) => {
	props.operation !== 'more' && formRef.value.resetFields()
	drawerState.open = false
	groups.length = 0
	emit('close', flash)
}
onBeforeMount(() => {
	setOptionsDispatch['channelType']()
})
</script>

<template>
	<Drawer v-bind="drawerState" @ok="operationDispatch[operation]" @close="handleCancel">
		<Form ref="formRef" v-if="operation === 'add' || operation === 'edit'" :form-schema="channelAppForm" />
		<div v-else-if="operation === 'more'">
			<Descriptions :groups="groups">
				<template #label="{ item }">
					<template v-if="item.label === '应用配置'">
						{{ item.label }}
						<EyeOutlined v-if="!item.extra?.close" @click="item.extra!.close = true" />
						<EyeInvisibleOutlined v-else @click="item.extra!.close = false" />
					</template>
				</template>
				<template #value="{ item }">
					<template v-if="item.label === '应用状态'">
						{{ item.value ? '开启' : '关闭' }}
						<Status :success="item.value"></Status>
					</template>
					<template v-if="item.label === '应用配置'">
						<highlightjs class="highlightjs" lang="json"
							:code="item.extra!.close ? '***********' : JSON.stringify(JSON.parse(item.value), null, 2)">
						</highlightjs>
					</template>
				</template>
			</Descriptions>
		</div>
	</Drawer>
</template>

<style lang="scss" scoped>
.highlightjs {
	width: 100%;
}
</style>

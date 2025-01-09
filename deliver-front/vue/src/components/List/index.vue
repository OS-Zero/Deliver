<script lang="ts" setup>
import { ref, watch } from 'vue'
import { PlusSquareOutlined } from '@ant-design/icons-vue'
import { message } from 'ant-design-vue'
interface Props {
	value: any
	options: Record<string, any>
}
const props = defineProps<Props>()
const emit = defineEmits(['update:value'])
const users = ref<Array<string>>([])
const input = ref<string>('')
const show = ref<boolean>(false)
watch(
	users.value,
	() => {
		emit('update:value', users.value)
	},
	{
		immediate: true
	}
)
watch(
	props,
	() => {
		users.value = props.value
	},
	{
		immediate: true
	}
)
const addItem = () => {
	if (users.value.includes(input.value)) {
		message.error('已存在相同 ID')
		return
	}
	if (input.value !== '') {
		users.value.push(input.value)
		input.value = ''
		show.value = false
	} else {
		message.error('请输入用户 ID')
	}
}
const deleteItem = (item: string) => {
	users.value = users.value.filter((s) => s != item)
}
const onBlur = () => {
	console.log('blur')
}
</script>
<template>
	<a-list :data-source="users" :locale="{ emptyText: ' ' }" bordered>
		<template #header>
			<span style="color: #3883fa">{{ options.header }}</span>
		</template>
		<template #renderItem="{ item }">
			<a-list-item>
				<div style="width: 80%; word-wrap: break-word; overflow-wrap: break-word">
					{{ item }}
				</div>
				<template #actions>
					<a @click="deleteItem(item)">删除</a>
				</template>
			</a-list-item>
		</template>
		<template #footer>
			<div style="text-align: center">
				<a-tooltip :title="options.tip">
					<PlusSquareOutlined style="font-size: 40px; color: #c4c3c3" @click="show = !show" v-show="!show" />
				</a-tooltip>
			</div>
			<a-input-groupInfo compact v-show="show">
				<a-input v-model:value="input" :maxlength="100" :placeholder="options.placeholder" style="width: 320px; text-align: left" @blur="onBlur" />
				<a-button @click="show = !show">取消</a-button>
				<a-button type="primary" @click="addItem">确认</a-button>
			</a-input-groupInfo>
			<p style="height: 32px"></p>
		</template>
	</a-list>
</template>
<style lang="scss" scoped></style>

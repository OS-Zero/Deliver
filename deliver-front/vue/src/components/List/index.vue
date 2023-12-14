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
const list = ref<Array<string>>([])
const input = ref<string>('')
const show = ref<boolean>(false)
watch(
	list,
	() => {
		emit('update:value', list.value)
	},
	{
		immediate: true
	}
)
watch(
	props,
	() => {
		list.value = props.value
	},
	{
		immediate: true
	}
)
const addItem = () => {
	if (list.value.includes(input.value)) {
		message.error('已存在相同 ID')
		return
	}
	if (input.value !== '') {
		list.value.push(input.value)

		input.value = ''
		show.value = false
	} else {
		message.error('请输入用户 ID')
	}
}
const deleteItem = (item: string) => {
	list.value = list.value.filter((s) => s != item)
}
</script>
<template>
	<a-list :data-source="list" :locale="{ emptyText: ' ' }" bordered>
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
			<a-input-group compact v-show="show">
				<a-input v-model:value="input" :maxlength="100" :placeholder="options.placeholder" style="width: 250px; text-align: left" />
				<a-button @click="show = !show">取消</a-button>
				<a-button type="primary" @click="addItem">确认</a-button>
			</a-input-group>
			<p style="height: 32px"></p>
		</template>
	</a-list>
</template>
<style lang="scss" scoped></style>

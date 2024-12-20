<script lang="ts" setup>
import { useRouter } from 'vue-router';
import { DeleteOutlined, EditOutlined, VerticalAlignTopOutlined, PlusOutlined } from '@ant-design/icons-vue';
import emitter from '@/utils/mitt'
import { GroupCard } from '@/types/group';
import { Modal } from 'ant-design-vue';
import { ExclamationCircleOutlined } from '@ant-design/icons-vue';
import { createVNode } from 'vue'
const props = defineProps<{
	data?: GroupCard
	isEmpty?: boolean
	showAction?: boolean
}>()
const emit = defineEmits(['onTop', 'onEdit', 'onDelete'])
const router = useRouter()
const handleMore = () => {
	router.push('groupManage/template')
	localStorage.setItem("group_id", props.data?.groupId + '')
	emitter.emit("card", props.data?.groupId + '')
}
const showConfirm = () => {
	Modal.confirm({
		title: '确认删除该分组?',
		icon: createVNode(ExclamationCircleOutlined),
		okText: '确认',
		cancelText: '取消',
		onOk() {
			emit('onDelete')
		},
	});
}
</script>

<template>
	<a-card hoverable v-if="!isEmpty">
		<template #cover>
			<img @click="handleMore" alt="example" src="../../../../public/folder.svg" />
		</template>
		<a-card-meta :description="data?.groupDescription" />
		<a-card-meta :description="data?.updateTime" />
		<template v-if="showAction" #actions>
			<VerticalAlignTopOutlined @click="emit('onTop')" />
			<EditOutlined @click="emit('onEdit')" />
			<DeleteOutlined @click="showConfirm" />
		</template>
	</a-card>
	<a-card class="empty-card" v-else>
		<PlusOutlined class="empty-icon" />
	</a-card>
</template>

<style lang="scss" scoped>
.ant-card {
	width: 200px;
}

.empty-card {
	display: flex;
	justify-content: center;
	align-items: center;


	&:hover {
		cursor: pointer;
		background-color: var(--blue-medium);

		.empty-icon {
			color: var(--white-color);
			transform: scale(1.2);
		}

	}
}

.empty-icon {
	color: var(--gray-darker);
	font-size: 60px;
	transition: 0.5s all;
}
</style>

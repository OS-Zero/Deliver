<script lang="ts" setup>
import { useRouter } from 'vue-router';
import { EllipsisOutlined, PlusOutlined, FieldTimeOutlined } from '@ant-design/icons-vue';
import emitter from '@/utils/mitt'
import { GroupCard } from '@/types/group';
import { Modal } from 'ant-design-vue';
import { ExclamationCircleOutlined } from '@ant-design/icons-vue';
import { createVNode } from 'vue'
type Op = 'onTop' | 'onEdit' | 'onDelete'

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
const handleOperation = (op: Op) => {
	(op === 'onTop' || op === 'onEdit') && emit(op)
	op === 'onDelete' && showConfirm()
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
const optionItems = [
	{
		name: '置顶',
		op: () => handleOperation('onTop')
	},
	{
		name: '编辑',
		op: () => handleOperation('onEdit')
	},
	{
		name: '删除',
		op: () => handleOperation('onDelete')
	},
]
</script>

<template>
	<div class="card" v-if="!isEmpty" @click="handleMore">
		<div class="card_more">
			<a-dropdown placement="bottom">
				<EllipsisOutlined @click.stop />
				<template #overlay>
					<a-menu>
						<a-menu-item v-for="item in optionItems" :key="item.name">
							<div @click.stop="item.op">{{ item.name }}</div>
						</a-menu-item>
					</a-menu>
				</template>
			</a-dropdown>
		</div>
		<h3 class="card_title">{{ data?.groupName }}</h3>
		<div class="card_content">
			{{ data?.groupDescription }}
		</div>
		<div class="card_time">
			<FieldTimeOutlined />
			{{ data?.updateTime }}
		</div>
	</div>
	<div class="card card--empty" v-else>
		<PlusOutlined class="empty_icon" />
		<div class="empty_desc">添加</div>
	</div>
</template>

<style lang="scss" scoped>
.card {
	position: relative;
	width: 200px;
	height: 200px;
	border-radius: var(--border-radius-small);
	padding: var(--spacing-md);
	border: 1px solid var(--gray-lightest);
	box-shadow: var(--box-shadow-small);
	background-image: url("../../../../public/folder.svg");
	background-position: center center;
	background-repeat: no-repeat;
	background-size: cover;

	&:hover {
		box-shadow: var(--box-shadow-large);
	}
}

.card_time {
	position: absolute;
	bottom: var(--spacing-md);
	right: var(--spacing-md);
	left: var(--spacing-md);
	text-align: center;
	box-shadow: var(--box-shadow-small);
	padding: var(--spacing-xs) 0;
	border-radius: var(--border-radius-large);
	color: var(--gray-medium-dark);
}

.card_more {
	position: absolute;
	top: var(--spacing-sm);
	right: var(--spacing-sm);
}

.card--empty {
	display: flex;
	flex-direction: column;
	justify-content: center;
	align-items: center;
	background-image: none;
	font-size: small;

	&:hover {
		cursor: pointer;
		background-color: var(--blue-medium);

		.empty_icon,
		.empty_desc {
			color: var(--white-color);
			transform: scale(1.2);
		}

	}
}

.empty_icon,
.empty_desc {
	transition: 0.5s all;
}

.empty_icon {
	font-size: 32px;
}
</style>

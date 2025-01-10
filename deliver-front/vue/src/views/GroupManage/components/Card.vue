<script lang="ts" setup>
import { useRouter } from 'vue-router';
import { EllipsisOutlined, PlusOutlined, FieldTimeOutlined, SoundFilled } from '@ant-design/icons-vue';
import { GroupCard } from '@/types/group';
type Op = 'onTop' | 'onEdit' | 'onDelete'

const props = defineProps<{
	data?: GroupCard
	isEmpty?: boolean
}>()
const emit = defineEmits(['onTop', 'onEdit', 'onDelete'])
const router = useRouter()
const jumpLink = () => {
	router.push('groupManage/template')
	localStorage.setItem("group_id", props.data?.groupId + '')
}
const handleOperation = (op: Op) => {
	emit(op, props.data)
}
</script>

<template>
	<div class="card" v-if="!isEmpty" @dblclick="jumpLink">
		<div class="card_more">
			<a-dropdown placement="bottom">
				<EllipsisOutlined />
				<template #overlay>
					<a-menu>
						<a-menu-item>
							<div @click="handleOperation('onTop')">{{ props.data?.topUp ? '取消置顶' : '置顶' }}</div>
						</a-menu-item>
						<a-menu-item>
							<div @click="handleOperation('onEdit')">编辑</div>
						</a-menu-item>
						<a-menu-item>
							<div @click="handleOperation('onDelete')">删除</div>
						</a-menu-item>
					</a-menu>
				</template>
			</a-dropdown>
		</div>
		<SoundFilled />
		<h4 class="card_title">{{ data?.groupName }}</h4>
		<div class="card_content">
			{{ data?.groupDescription }}
		</div>
		<div class="card_time">
			<FieldTimeOutlined />
			{{ data?.updateTime }}
		</div>
	</div>
	<div class="card card--empty" v-else>
		<slot>
			<PlusOutlined class="empty_icon" />
			<div class="empty_desc">添加分组</div>
		</slot>
	</div>
</template>

<style lang="scss" scoped>
.card {
	position: relative;
	width: 200px;
	height: 200px;
	border-radius: var(--border-radius-medium);
	padding: var(--spacing-md);
	border: 1px solid var(--gray-lightest);
	box-shadow: var(--box-shadow-small);

	&:hover {
		box-shadow: var(--box-shadow-large);
	}
}

.card_title {
	margin: var(--spacing-xs) 0;
}

.card_time {
	position: absolute;
	bottom: var(--spacing-sm);
	right: var(--spacing-md);
	left: var(--spacing-md);
	border-top: 1px solid var(--gray-light);
	text-align: center;
	padding: var(--spacing-xs) 0;
	color: var(--gray-medium-dark);
}

.anticon-field-time {
	margin-right: var(--spacing-xs);
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
	color: var(--gray-medium-dark);

	&:hover {
		cursor: pointer;

		.empty_icon,
		.empty_desc {
			color: var(--blue-medium);
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

.anticon-sound {
	padding: var(--spacing-sm);
	border-radius: var(--border-radius-medium);
	font-size: x-large;
	color: var(--yellow-dark);
	background-color: var(--yellow-lighter)
}

.card_content {
	color: var(--gray-medium-dark);
	@include show_line(4)
}
</style>

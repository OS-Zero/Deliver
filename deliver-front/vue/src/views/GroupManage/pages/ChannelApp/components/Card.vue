<script lang="ts" setup>
import { h } from "vue"
import { ChannelApp } from '@/types/channelApp';
import { getImg } from '@/utils/table';
import { EditOutlined } from '@ant-design/icons-vue';
defineProps<{
	data: ChannelApp
}>()
const emit = defineEmits(['changeStatus', 'handleActions'])
</script>

<template>
	<div class="card">
		<a-button type="link" :icon="h(EditOutlined)" class="edit_btn"
			@click="emit('handleActions', 'edit', data)"></a-button>
		<div class="card-header">
			<img class="card_img" :src="getImg(1).src" alt="">
			<div class="card_info">
				<div class="card_title">
					<h4>{{ data.appName }}</h4>
					<span class="card_status" :class="data.appStatus == 1 ? 'open' : 'close'"></span>
				</div>
				<div class="info_creator">创建者：{{ data.createUser }}</div>
			</div>
		</div>
		<div class="card_btns">
			<div class="btn--operation">
				<a-button v-show="data.appStatus" @click="emit('changeStatus', data)">禁用</a-button>
				<a-button v-show="!data.appStatus" @click="emit('changeStatus', data)">开启</a-button>
				<a-button v-show="!data.appStatus" @click="emit('handleActions', 'delete', data)">删除</a-button>
			</div>
			<a-button class="btn--more" type="text" @click="emit('handleActions', 'more', data)">更多信息</a-button>
		</div>
	</div>
</template>

<style lang="scss" scoped>
.card {
	position: relative;
	width: 100%;
	border: 1px solid var(--gray-lighter);
	padding: var(--spacing-md);
	border-radius: var(--border-radius-small);
}

.card_img {
	width: 40px;
	height: 40px;
}

.card-header {
	display: flex;
	padding-bottom: var(--spacing-sm);
	border-bottom: 1px solid var(--gray-lighter);
	margin-bottom: var(--spacing-sm);
}

.card_title {
	display: flex;
	margin-bottom: var(--spacing-xs);
}

.card_info {
	margin-left: var(--spacing-sm);
}

.edit_btn {
	position: absolute;
	right: var(--spacing-md);
	top: var(--spacing-sm);
}

.info_creator {
	font-size: var(--font-size-small);
	color: var(--gray-medium-dark);
}

.card_btns {
	margin-top: var(-spacing-sm);
	display: flex;
	justify-content: space-between;
	gap: var(--spacing-xs);
}

.btn--operation {
	display: flex;
	gap: var(--spacing-xs);
}

.card_status {
	display: inline-block;
	height: 8px;
	width: 8px;
	border-radius: 7px;
	vertical-align: middle;
	margin-left: 10px;
	margin-top: 5px;

	&.open {
		background-color: rgb(10, 191, 91);
	}

	&.close {
		background-color: rgb(229, 69, 69);
	}
}
</style>

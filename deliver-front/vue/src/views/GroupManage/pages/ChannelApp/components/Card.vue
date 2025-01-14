<script lang="ts" setup>
import { h } from "vue"
import { ChannelApp } from '@/types/channelApp';
import { getImg } from '@/utils/table';
import { EditOutlined } from '@ant-design/icons-vue';
defineProps<{
	data: ChannelApp
}>()
</script>

<template>
	<div class="card">
		<a-button type="link" :icon="h(EditOutlined)" class="edit_btn"></a-button>
		<div class="card-header">
			<img class="card_img" :src="getImg(1).src" alt="">
			<div class="card_info">
				<div class="card_title">
					<h4>飞书消息推送</h4>
					<span :style="{
						display: 'inline-block',
						height: '8px',
						width: '8px',
						background: data.appStatus == 1 ? 'rgb(10, 191, 91)' : 'rgb(229, 69, 69)',
						borderRadius: '7px',
						verticalAlign: 'middle',
						marginLeft: '10px',
						marginBottom: '5px'
					}"></span>
				</div>
				<div class="info_creator">创建者：z</div>
			</div>
		</div>
		<div class="card_btns">
			<a-button>禁用</a-button>
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
}

.card_status {
	font-size: large;
	color: green;
}
</style>

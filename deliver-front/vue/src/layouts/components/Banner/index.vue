<script lang="ts" setup>
import { CloseOutlined } from '@ant-design/icons-vue'
import { ref } from 'vue';
const closed = ref(false)
</script>
<template>
	<div class="banner" v-if="!closed">
		<div>
			⭐️ 如果你喜欢 Deliver 消息推送平台，请给它一个 Star&nbsp;
			<a target="_blank" rel="noopener noreferrer" href="https://gitee.com/OS-Zero/deliver">Gitee</a>
			，你的支持将是我们前行的动力，项目正在积极开发，欢迎大家提交 PR 提供建设、共建社区生态。👏🏻
			<a-tooltip title="关闭">
				<i class="icon--close" @click="closed = true">
					<CloseOutlined />
				</i>
			</a-tooltip>
		</div>
	</div>
</template>
<style lang="scss" scoped>
.banner {
	width: 100%;
	background: repeating-linear-gradient(35deg,
			rgb(179 235 255),
			rgb(179 235 255) 20px,
			rgb(230 248 255) 10px,
			rgb(230 248 255) 40px);
	overflow: hidden;
	color: var(--dark-color);
	text-align: center;
	text-overflow: ellipsis;
	white-space: nowrap
}

div {
	display: flex;
	align-items: center;
	justify-content: center;
	font-size: var(--font-size-small);
	line-height: 25px;
	text-align: center;
}

.ico {
	width: 25px;
	height: 25px;
}

.icon--close {
	display: inline-block;
	position: absolute;
	right: var(--spacing-sm);
	color: var(--gray-dark)
}
</style>

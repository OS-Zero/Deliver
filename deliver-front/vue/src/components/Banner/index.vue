<script lang="ts" setup>
import { useStore } from '@/store'
import { CloseOutlined } from '@ant-design/icons-vue'
const store = useStore()
</script>
<template>
	<div
		v-if="store.$state.showBannerFlag"
		data-show="true"
		role="alert"
		style="
			width: 100%;
			padding: 0;
			background: repeating-linear-gradient(
				35deg,
				rgb(179 235 255),
				rgb(179 235 255) 20px,
				rgb(230 248 255) 10px,
				rgb(230 248 255) 40px
			);
		"
	>
		<div style="overflow: hidden; color: black; text-align: center; text-overflow: ellipsis; white-space: nowrap">
			<span>
				⭐️ 如果你喜欢 Deliver 消息推送平台，请给它一个 Star&nbsp;
				<a target="_blank" rel="noopener noreferrer" href="https://gitee.com/OS-Zero/deliver">Gitee</a>
				，你的支持将是我们前行的动力，项目正在积极开发，欢迎大家提交 PR 提供建设、共建社区生态。👏🏻

				<a-tooltip title="关闭">
					<a style="display: inline-block; position: absolute; right: 10px" @click="store.closeBanner">
						<CloseOutlined style="color: #5d5b5b" />
					</a>
				</a-tooltip>
			</span>
		</div>
	</div>
</template>
<style lang="scss" scoped>
span {
	display: flex;
	align-items: center;
	justify-content: center;
	font-size: 14px;
	line-height: 25px;
	text-align: center;
}

.ico {
	width: 25px;
	height: 25px;
}
</style>

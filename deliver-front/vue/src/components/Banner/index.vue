<script lang="ts" setup></script>
<template>
	<div
		data-show="true"
		role="alert"
		style="
			position: fixed;
			z-index: 999;
			width: 100%;
			height: 25px;
			padding: 0;
			background: repeating-linear-gradient(
				35deg,
				rgb(179 235 255),
				rgb(179 235 255) 20px,
				rgb(230 248 255) 10px,
				rgb(230 248 255) 40px
			);
		">
		<div>
			<div>
				<div
					style="
						overflow: hidden;
						color: black;
						text-align: center;
						text-overflow: ellipsis;
						white-space: nowrap;
					">
					<span>
						⭐️ 如果你喜欢 Deliver 消息推送平台，请给它一个 Star&nbsp;
						<a target="_blank" rel="noopener noreferrer" href="https://gitee.com/OS-Zero/deliver">
							Gitee
						</a>
						，你的支持将是我们前行的动力，项目正在积极开发，欢迎大家提交 PR
						提供建设、共建社区生态。👏🏻
					</span>
				</div>
			</div>
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

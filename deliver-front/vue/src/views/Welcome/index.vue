<script lang="ts" setup>
import { ref } from 'vue'

interface Card {
	title: string
	desc: string
	link: string
}

const cards = ref<Card[]>([
	{
		title: '社区官网',
		desc: '欢迎访问 OSZero 官方网站，Open Source Zero（零号开源）由一群充满技术热情的开发者创建。加入我们，一同探索开源世界！',
		link: 'https://os-zero.gitee.io/deliver-website/',
	},
	{
		title: '快速开始',
		desc: '立即启动 Deliver 企业消息推送平台，发送属于您的首条消息~',
		link: 'https://os-zero.gitee.io/deliver-website/pages/1a1a14/',
	},
	{
		title: '开发者文档',
		desc: '探索开发者专属的设计和二次开发文档，创建您独一无二的企业消息推送平台！',
		link: 'https://os-zero.gitee.io/deliver-website/pages/1a1a16/',
	},
	{
		title: '用户指南',
		desc: '在这里，您将学会如何创建消息模板、配置必要信息以及发送各种类型的消息。现在就开始吧~',
		link: 'https://os-zero.gitee.io/deliver-website/pages/1a1a15/',
	},
	{
		title: '运维部署',
		desc: '支持多种部署方式（本地、Docker、Linux Shell），简单快捷的部署方式，让我们开始搭建吧！',
		link: 'https://os-zero.gitee.io/deliver-website/pages/1a1a13/',
	},
	{
		title: '交流讨论',
		desc: '欢迎加入我们的社区官方交流讨论群，快速获得答疑解惑，赶紧一起加入吧~',
		link: 'https://os-zero.gitee.io/deliver-website/pages/3a3a11/',
	},
])
</script>
<template>
	<div id="welcome-container">
		<h1>欢迎使用 Deliver 企业消息推送平台</h1>
		<p>
			Deliver
			是一个面向企业的全面消息推送平台，旨在提供企业内部沟通和协作的便捷解决方案。它以轻量级部署、简单易用、支持多种通信渠道为特点，为企业提供高效的消息传递和通知功能。
		</p>
		<a-row :gutter="[16, 16]">
			<a-col :span="8" v-for="(item, index) in cards" :key="item.title">
				<a-card style="height: 100%; border: 0px solid #ececec" class="gutter-row">
					<a-card-meta :title="item.title" :description="item.desc" style="height: 100px">
						<template #avatar>
							<a-avatar :size="40" class="avatar"
								src="https://gw.alipayobjects.com/zos/bmw-prod/daaf8d50-8e6d-4251-905d-676a24ddfa12.svg" />
							<span style="position: absolute; top: 24.5px; left: 38.5px; color: #fff">
								{{ index + 1 }}
							</span>
						</template>
					</a-card-meta>
					<template #actions>
						<a style="color: #1677ff" :href="item.link" target="_blank">了解更多 ></a>
					</template>
				</a-card>
			</a-col>
		</a-row>
	</div>
</template>
<style lang="scss" scoped>
#welcome-container {
	padding: 24px;
	background-image: linear-gradient(to bottom, #eff3ff, #f0f5ff);
	border-radius: 12px;
	border: 1px solid #ececec;

	.gutter-row {
		box-shadow:
			rgba(0, 0, 0, 0.08) 0px 6px 16px 0px,
			rgba(0, 0, 0, 0.12) 0px 3px 6px -4px,
			rgba(0, 0, 0, 0.05) 0px 9px 28px 8px;
		border-radius: 8px;
	}

	h1 {
		font-size: 20px;
	}

	&>p {
		width: 65%;
		margin-top: 16px;
		margin-bottom: 32px;
		font-size: 14px;
		line-height: 22px;
		color: rgb(0 0 0 / 65%);
	}

	a:hover {
		color: #6ea2d7 !important;
	}
}
</style>

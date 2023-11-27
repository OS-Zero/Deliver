<script lang="ts" setup>
import {
	MessageOutlined,
	FileTextOutlined,
	DashboardOutlined,
	LeftOutlined,
	RightOutlined,
	SmileOutlined,
	AppstoreOutlined,
	GithubOutlined
} from '@ant-design/icons-vue'
import { ref } from 'vue'
import ItemLink from '../ItemLink/index.vue'
import Breadcrumb from '../Breadcrumb/index.vue'
import { useRoute } from 'vue-router'
import { useStore } from '@/store/index'

const route = useRoute()
const store = useStore()
const selectedKeys2 = ref<string[]>([`${route.name as string}-1`])
const openKeys = ref<string[]>([route.name as string])
const collapsed = ref(false)
const toggleCollapsed = (): void => {
	collapsed.value = !collapsed.value
	store.changeCollapse()
}
</script>

<template>
	<a-layout-sider
		width="200"
		:style="{
			overflow: 'auto',
			height: '100vh',
			position: 'fixed',
			top: store.$state.showBannerFlag ? '85px' : '60px',
			bottom: 0,
			zIndex: 998,
			borderRight: '1px solid #ececec'
		}"
		style="background: #fff"
		:collapsed="collapsed">
		<a-button
			shape="circle"
			size="small"
			style="position: fixed"
			:style="{
				left: collapsed ? '70px' : '190px',
				top: store.$state.showBannerFlag ? '115px' : '90px'
			}"
			@click="toggleCollapsed">
			<RightOutlined v-if="collapsed" />
			<LeftOutlined v-else />
		</a-button>
		<a-menu
			v-model:selectedKeys="selectedKeys2"
			v-model:openKeys="openKeys"
			mode="inline"
			style="border-right: 0px solid #ececec">
			<a-sub-menu key="欢迎">
				<template #title>
					<span>
						<SmileOutlined />
						<span>欢迎</span>
					</span>
				</template>
				<ItemLink itemKey="欢迎-1" info="欢迎" to="welcome" />
			</a-sub-menu>
			<a-sub-menu key="控制面板">
				<template #title>
					<span>
						<DashboardOutlined />
						<span>系统监控看板</span>
					</span>
				</template>
				<ItemLink itemKey="控制面板-1" info="平台数据看板" to="dashboard" />
			</a-sub-menu>
			<a-sub-menu key="消息配置">
				<template #title>
					<span>
						<MessageOutlined />
						<span>消息模板配置</span>
					</span>
				</template>
				<ItemLink itemKey="消息配置-1" info="消息模板" to="message" />
			</a-sub-menu>
			<a-sub-menu key="APP 配置">
				<template #title>
					<span>
						<AppstoreOutlined />
						<span>渠道 APP 配置</span>
					</span>
				</template>
				<ItemLink itemKey="APP 配置-1" info="APP 配置" to="channel" />
			</a-sub-menu>
			<a-sub-menu key="文件管理">
				<template #title>
					<span>
						<FileTextOutlined />
						<span>平台文件管理</span>
					</span>
				</template>
				<ItemLink itemKey="文件管理-1" info="文件管理" to="file" />
			</a-sub-menu>
			<!-- <a-sub-menu key="规则配置">
				<template #title>
					<span>
						<FunnelPlotOutlined />
						<span>流控规则配置</span>
					</span>
				</template>
				<ItemLink itemKey="规则配置-1" info="规则配置" to="flowControlRule" />
			</a-sub-menu> -->
		</a-menu>
	</a-layout-sider>
	<a-layout
		style="padding: 0 42px 24px; background: #f8f8f8"
		:style="{
			marginLeft: collapsed ? '80px' : '200px',
			marginTop: store.$state.showBannerFlag ? '85px' : '60px'
		}">
		<Breadcrumb style="margin-bottom: 24px"></Breadcrumb>
		<a-layout-content>
			<RouterView></RouterView>
		</a-layout-content>
		<a-layout-footer
			:style="{
				textAlign: 'center',
				backgroundColor: '#F8F8F8',
				color: '#1E1E1E'
			}">
			<div style="margin-bottom: 6px">
				Deliver 企业消息推送平台
				<a
					href="https://gitee.com/OS-Zero"
					target="_blank"
					style="color: #1e1e1e; font-size: 16px; margin: 0 5px 0">
					<GithubOutlined />
				</a>
				OSZero 开源社区出品
			</div>
			<div>Copyright © 2023 OSZero. All rights reserved.</div>
		</a-layout-footer>
	</a-layout>
</template>
<style lang="scss" scoped></style>

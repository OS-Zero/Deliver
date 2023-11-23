<script lang="ts" setup>
import {
	MessageOutlined,
	// FunnelPlotOutlined,
	FileTextOutlined,
	DashboardOutlined,
	LeftOutlined,
	RightOutlined,
	SmileOutlined,
	AppstoreOutlined
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
			top: '85px',
			bottom: 0,
			zIndex: 999
		}"
		style="background: #fff"
		:collapsed="collapsed">
		<a-button
			shape="circle"
			size="small"
			style="position: fixed; margin-bottom: 16px"
			:style="{ left: collapsed ? '70px' : '190px', top: '120px' }"
			@click="toggleCollapsed">
			<RightOutlined v-if="collapsed" />
			<LeftOutlined v-else />
		</a-button>
		<a-menu v-model:selectedKeys="selectedKeys2" v-model:openKeys="openKeys" mode="inline">
			<a-sub-menu key="welcome">
				<template #title>
					<span>
						<SmileOutlined />
						<span>欢迎</span>
					</span>
				</template>
				<ItemLink itemKey="welcome-1" info="欢迎" to="welcome" />
			</a-sub-menu>
			<a-sub-menu key="dashboard">
				<template #title>
					<span>
						<DashboardOutlined />
						<span>系统监控看板</span>
					</span>
				</template>
				<ItemLink itemKey="dashboard-1" info="平台数据看板" to="dashboard" />
			</a-sub-menu>
			<a-sub-menu key="message">
				<template #title>
					<span>
						<MessageOutlined />
						<span>消息模板配置</span>
					</span>
				</template>
				<ItemLink itemKey="message-1" info="消息模板" to="message" />
			</a-sub-menu>
			<a-sub-menu key="channel">
				<template #title>
					<span>
						<AppstoreOutlined />
						<span>渠道 APP 配置</span>
					</span>
				</template>
				<ItemLink itemKey="channel-1" info="APP 配置" to="channel" />
			</a-sub-menu>
			<a-sub-menu key="file">
				<template #title>
					<span>
						<FileTextOutlined />
						<span>平台文件管理</span>
					</span>
				</template>
				<ItemLink itemKey="file-1" info="文件管理" to="file" />
			</a-sub-menu>
			<!-- <a-sub-menu key="flowControlRule">
				<template #title>
					<span>
						<FunnelPlotOutlined />
						<span>流控规则配置</span>
					</span>
				</template>
				<ItemLink itemKey="flowControlRule-1" info="规则配置" to="flowControlRule" />
			</a-sub-menu> -->
		</a-menu>
	</a-layout-sider>
	<a-layout
		style="padding: 0 24px 12px; background: #f8f8f8"
		:style="{ marginLeft: collapsed ? '80px' : '200px', marginTop: '85px' }">
		<Breadcrumb style="margin-bottom: 12px"></Breadcrumb>
		<a-layout-content>
			<RouterView></RouterView>
		</a-layout-content>
	</a-layout>
</template>
<style lang="scss" scoped></style>

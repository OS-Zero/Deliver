<script lang="ts" setup>
import { onBeforeMount, ref, watch } from 'vue'
import Header from './components/Header/index.vue'
import Banner from './components/Banner/index.vue'
import Breadcrumb from './components/Breadcrumb/index.vue'
import SideBar from './components/SideBar/index.vue'
import {
	GithubOutlined
} from '@ant-design/icons-vue'
import { ItemType } from 'ant-design-vue'
import { useRouter } from 'vue-router'
import type { CSSProperties } from 'vue';
import { useMenuConfig } from '@/hooks/menu'
const router = useRouter()
const { menuConfig, updateMenuConfig } = useMenuConfig()
let items = ref<ItemType[] | null>(null)
const onTabChange = (tab: string) => {
	const groupId = localStorage.getItem("group_id");
	(tab === 'systemManage' || groupId) && (items.value = menuConfig[tab])
}
const onCardSelected = (_card: string) => {
	items.value = menuConfig["groupManage"]
}
const showBreadcrumb = ref(false)
watch(() => router.currentRoute.value.path, (to: string) => {
	showBreadcrumb.value = false
	items.value = null
	to.includes("groupManage") && (showBreadcrumb.value = true) && onTabChange("groupManage");
	to.includes("systemManage") && (showBreadcrumb.value = true) && onTabChange("systemManage");
}, {
	immediate: true
})
onBeforeMount(() => {
	updateMenuConfig()
})
const headerStyle: CSSProperties = {
	paddingInline: 0,
	backgroundColor: '#fff',
	height: 'auto'
};

const contentStyle: CSSProperties = {
	backgroundColor: '#fff',
	padding: 'var(--spacing-md)',
	overflow: 'auto',
	display: 'flex',
	flexDirection: 'column',
	gap: 'var(--font-size-base)'
};
</script>

<template>
	<a-layout style="height: 100%;">
		<a-layout-header :style="headerStyle">
			<Banner class="header_banner" />
			<Header></Header>
		</a-layout-header>
		<a-layout>
			<SideBar v-if="items !== null" :items="items"></SideBar>
			<a-layout>
				<a-layout-content :style="contentStyle">
					<Breadcrumb v-if="showBreadcrumb" />
					<RouterView style="flex:1"></RouterView>
				</a-layout-content>
				<a-layout-footer>
					<div class="footer_organization">
						Deliver 企业消息推送平台
						<a class="organization_link" href="https://gitee.com/OS-Zero" target="_blank">
							<GithubOutlined />
						</a>
						OSZero 开源社区出品
					</div>
					<div>Copyright © 2023 OSZero. All rights reserved.</div>
				</a-layout-footer>
			</a-layout>
		</a-layout>
	</a-layout>
</template>
<style scoped lang="scss">
.ant-layout-footer {
	text-align: center;
	background-color: var(--gray-lightest);
	color: var(--gray-dark);
}

.footer_organization {
	margin-bottom: var(--spacing-sm)
}

.organization_link {
	color: var(--gray-dark);
	font-size: var(--spacing-md);
}
</style>

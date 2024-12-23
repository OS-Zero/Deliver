<script lang="ts" setup>
import { onUnmounted, ref, watch } from 'vue'
import Header from './components/Header/index.vue'
import Banner from './components/Banner/index.vue'
import Breadcrumb from './components/Breadcrumb/index.vue'
import SideBar from './components/SideBar/index.vue'
import {
	GithubOutlined
} from '@ant-design/icons-vue'
import { menuConfig } from "@/config/menu"
import { ItemType } from 'ant-design-vue'
import emitter from '@/utils/mitt'
import { useRouter } from 'vue-router'
const router = useRouter()
let items = ref<ItemType[] | null>(null)
const onTabChange = (tab: string) => {
	const groupId = localStorage.getItem("group_id");
	(tab === 'systemManage' || groupId) && (items.value = menuConfig[tab])
}
const onCardSelected = (_card: string) => {
	items.value = menuConfig["groupManage"]
}
emitter.on("card", onCardSelected)
const showBreadcrumb = ref(false)
watch(() => router.currentRoute.value.path, (to: string) => {
	showBreadcrumb.value = false
	items.value = null
	to.includes("groupManage") && (showBreadcrumb.value = true) && onTabChange("groupManage");
	to.includes("systemManage") && (showBreadcrumb.value = true) && onTabChange("systemManage");
}, {
	immediate: true
})
onUnmounted(() => {
	emitter.off("card", onCardSelected)
})

</script>

<template>
	<a-layout>
		<div>
			<Banner class="header_banner" />
			<Header></Header>
		</div>
		<a-layout>
			<SideBar v-if="items !== null" :items="items"></SideBar>
			<a-layout class="layout-section">
				<Breadcrumb v-if="showBreadcrumb" class="section_breadcrumb" />
				<a-layout-content class="layout-content">
					<RouterView></RouterView>
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
.ant-layout {
	height: 100%;

	.layout-section {
		padding: var(--spacing-md);

		.section_breadcrumb {
			margin-bottom: var(--spacing-md)
		}

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
	}
}

.ant-layout {
	background-color: var(--white-color);
}

.layout-content {
	overflow: auto;
}
</style>

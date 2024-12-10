<script lang="ts" setup>
import Header from './components/Header/index.vue'
import Banner from './components/Banner/index.vue'
import Breadcrumb from './components/Breadcrumb/index.vue'
import SideBar from './components/SideBar/index.vue'
import {
	GithubOutlined
} from '@ant-design/icons-vue'
</script>

<template>
	<a-layout>
		<div class="layout_header">
			<Banner class="header_banner" />
			<Header></Header>
		</div>
		<a-layout>
			<SideBar></SideBar>
			<a-layout class="layout-section">
				<Breadcrumb class="section_breadcrumb" />
				<a-layout-content>
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
</style>

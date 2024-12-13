<script lang="ts" setup>
import { reactive } from 'vue'
import {
	MenuUnfoldOutlined,
	MenuFoldOutlined
} from '@ant-design/icons-vue'
import { ItemType } from 'ant-design-vue'
defineProps<{
	items: ItemType[]
}>()
const state = reactive({
	collapsed: false
});
const toggleCollapsed = () => {
	state.collapsed = !state.collapsed;
};
</script>

<template>
	<a-layout-sider :collapsed="state.collapsed" theme="light">
		<a-menu :items="items" mode="inline" />
		<footer class="sider-footer">
			<a-button type="text" class="footer_button" @click="toggleCollapsed">
				<MenuUnfoldOutlined v-if="state.collapsed" />
				<MenuFoldOutlined v-else />
			</a-button>
		</footer>
	</a-layout-sider>
</template>

<style lang="scss" scoped>
.ant-layout-sider {
	position: relative;

	.sider-footer {
		position: absolute;
		display: flex;
		height: 40px;
		bottom: 0;
		width: 100%;
		border-top: 1px solid var(--gray-lightest);

		.footer_button {
			width: 100%;
			margin: auto;

			&:hover {
				background-color: transparent
			}
		}
	}
}
</style>

<script lang="ts" setup>
import { reactive, watch } from 'vue'
import {
	MenuUnfoldOutlined,
	MenuFoldOutlined
} from '@ant-design/icons-vue'
import { ItemType } from 'ant-design-vue'
import { useRouter } from 'vue-router';
defineProps<{
	items: ItemType[]
}>()
const state = reactive({
	collapsed: false,
	selectedKeys: ['']
});
const toggleCollapsed = () => {
	state.collapsed = !state.collapsed;
};
const router = useRouter()
watch(() => router.currentRoute.value.path, (to: string) => {
	state.selectedKeys = [to]
}, {
	immediate: true
})
</script>

<template>
	<a-layout-sider :collapsed="state.collapsed" theme="light">
		<a-menu v-model:selected-keys="state.selectedKeys" :items="items" mode="inline" />
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
	height: 100%;
	position: relative;
	border-right: 1px solid var(--gray-lighter);

	.ant-menu {
		border: none;
	}

	.sider-footer {
		position: absolute;
		display: flex;
		height: 40px;
		bottom: 0;
		width: 100%;
		border-top: 1px solid var(--gray-lighter);

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

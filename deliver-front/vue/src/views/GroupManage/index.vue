<script lang="ts" setup>
import { reactive, watch, h } from 'vue';
import { useRouter } from 'vue-router';
import Card from './components/Card.vue';
import { getGroupData, deleteGroup, toTopGroup } from '@/api/group';
import { GroupCardList } from '@/types/group';
import { message, Modal } from 'ant-design-vue';
import GroupForm from './components/GroupForm.vue';
import { ExclamationCircleOutlined } from '@ant-design/icons-vue';
import { debounce } from 'lodash';

type Operation = 'add' | 'edit' | 'delete' | 'top'
const router = useRouter()
const state = reactive({
	mainPage: true,
	search: '',
})
const drawerState = reactive<{
	open: boolean
	operation: 'add' | 'edit'
	record: Record<string, any>
}>({
	open: false,
	operation: 'add',
	record: {}
})
const handleSearch = async (groupName: string = state.search) => {
	const res = await getGroupData({ groupName: groupName })
	Object.assign(groupList, res)
}
const debounceSearch = debounce(handleSearch, 200)
const handleClose = () => {
	drawerState.open = false
	debounceSearch()
}
const groupList = reactive<GroupCardList>({
	topUpGroupList: [],
	defaultGroupList: []
})

const operationDispatch = {
	delete: async (record: Record<string, any> = {}) => {
		Modal.confirm({
			title: '确认删除该分组?',
			icon: h(ExclamationCircleOutlined),
			okText: '确认',
			cancelText: '取消',
			async onOk() {
				await deleteGroup({ ids: record.groupId })
				message.success('删除成功')
				debounceSearch()
			},
		});
	},
	top: async (record: Record<string, any> = {}) => {
		await toTopGroup({ groupId: record.groupId, topUp: Number(!record.topUp) })
		debounceSearch()
	}
}
const handleActions = (operation: Operation, record: Record<string, any> = {}) => {
	(operation === 'add' || operation === 'edit') && (drawerState.operation = operation, drawerState.open = true);
	(operation === 'edit') && (drawerState.record = record);
	(operation === 'delete' || operation === 'top') && operationDispatch[operation](record);
}

watch(() => router.currentRoute.value.path, async (to: string) => {
	if (!!!localStorage.getItem('group_id') && to === '/groupManage') {
		state.mainPage = true
		return debounceSearch()
	}
	state.mainPage = false
}, { immediate: true })
</script>

<template>
	<div class="groupManage">
		<template v-if="state.mainPage">
			<p class="desc">Lorem ipsum dolor sit amet consectetur adipisicing elit. Sunt et esse quo.</p>
			<div class="card-top">
				<h3>置顶分组</h3>
				<div class="top_cards">
					<Card is-empty v-if="groupList.topUpGroupList.length === 0">
						当前置顶分组为空，你可以选择置顶分组方便后续查找分组
					</Card>
					<Card v-for="item in groupList.topUpGroupList" :data="item" @on-top="handleActions('top', item)"
						@on-edit="handleActions('edit', item)" @on-delete="handleActions('delete', item)" :key="item.groupId">
					</Card>
				</div>
			</div>
			<div class="card-bottom">
				<div class="bottom-section">
					<h3>全部分组</h3>
					<SearchInput class="search_input" v-model="state.search" placeholder="请输入分组名" @search="debounceSearch">
					</SearchInput>
				</div>
				<div class="bottom_cards">
					<Card is-empty @click="handleActions('add')"></Card>
					<Card showAction v-for="item in groupList.defaultGroupList" :data="item" @on-top="handleActions('top', item)"
						@on-edit="handleActions('edit', item)" @on-delete="handleActions('delete', item)" :key="item.groupId">
					</Card>
				</div>
			</div>
			<GroupForm v-bind="drawerState" @close="handleClose"></GroupForm>
		</template>
		<template v-else>
			<RouterView />
		</template>
	</div>
</template>

<style lang="scss" scoped>
.groupManage {
	height: 100%;
}

.top_cards,
.bottom_cards {
	display: flex;
	gap: var(--spacing-md);
}

.card-top {
	margin-bottom: var(--spacing-md);

	h3 {
		margin-bottom: var(--spacing-md);
	}
}


.bottom-section {
	display: flex;
	justify-content: space-between;
	align-items: center;
	margin-bottom: var(--spacing-md);

	h3 {
		margin-right: var(--spacing-md);
	}
}

.desc {
	font-size: var(--font-size-small);
	background-color: var(--gray-lightest);
	padding: var(--spacing-xs);
	border-radius: var(--border-radius-small);
}

.search_input {
	width: 200px;
}
</style>

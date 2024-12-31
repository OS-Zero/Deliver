<script lang="ts" setup>
import { ref, reactive, watch, h } from 'vue';
import { useRouter } from 'vue-router';
import Card from './components/Card.vue';
import { addGroup, getGroupData, updateGroup, deleteGroup, toTopGroup } from '@/api/group';
import { GroupCard, GroupCardList } from '@/types/group';
import { getRules } from '@/config/rules';
import { message } from 'ant-design-vue';
import { SearchOutlined } from '@ant-design/icons-vue';
import Drawer from "@/components/Drawer/index.vue"
const router = useRouter()
const state = reactive({
	mainPage: true,
	open: false,
	operation: 'add',
	search: '',
})
const onOpen = () => {
	state.open = true
	console.log('hello');

}
const onClose = () => {
	state.open = false
}
const groupList = reactive<GroupCardList>({
	topUpGroupList: [],
	defaultGroupList: []
})
const groupFormData = reactive<GroupCard>({
	groupId: -1,
	groupName: '',
	groupDescription: '',
})
const flashData = async (groupName: string = '') => {
	const res = await getGroupData({ groupName: groupName })
	Object.assign(groupList, res)
}
const onOk = (cb: () => void) => {
	formRef.value
		.validate()
		.then(async () => {
			cb()
			onClose()
			flashData()
		})
		.catch(error => {
			console.log('error', error);
		});
}
const formMeta: Record<string, {
	title: string,
	success: () => void
}> = {
	add: {
		title: '添加分组',
		success: () => {
			onOk(async () => {
				await addGroup(groupFormData)
				message.success('添加成功')
			})
		}
	},
	edit: {
		title: '编辑分组',
		success: () => {
			console.log('编辑分组');

			onOk(async () => {
				await updateGroup(groupFormData)
				message.success('编辑成功')
			})
		}
	},
	delete: {
		title: '删除分组',
		success: async () => {
			await deleteGroup({ ids: [groupFormData.groupId] })
			message.success('删除成功')
			flashData()
		}
	},
	toTop: {
		title: '置顶分组',
		success: async () => {
			await toTopGroup({ groupId: groupFormData.groupId })
			flashData()
		}
	}
}
const groupRules = getRules(['groupName', 'groupDescription'])
const formRef = ref()
watch(() => router.currentRoute.value.path, async (to: string) => {
	if (!!!localStorage.getItem('group_id') && to === '/groupManage') {
		state.mainPage = true
		return flashData()
	}
	state.mainPage = false
}, { immediate: true })

const changeOperation = (op: 'add' | 'edit' | 'delete' | 'top', data?: GroupCard) => {
	state.operation = op
	data && Object.assign(groupFormData, data);
	(op === 'add' || op === 'edit') && onOpen()
	op === 'delete' && formMeta.delete.success()
	op === 'top' && formMeta.toTop.success()
}

</script>

<template>
	<div class="groupManage">
		<template v-if="state.mainPage">
			<div class="card-top">
				<h3>置顶分组</h3>
				<div class="top_cards">
					<Card v-for="item in groupList.topUpGroupList" :data="item" @on-top="changeOperation('top', item)"
						@on-edit="changeOperation('edit', item)" @on-delete="changeOperation('delete', item)"></Card>
				</div>
			</div>
			<div class="card-bottom">
				<div class="bottom-section">
					<h3>全部分组</h3>
					<div class="search-box">
						<a-button class="search-btn" shape="circle" :icon="h(SearchOutlined)" @click="flashData(state.search)" />
						<input v-model="state.search" type="text" class="search-txt" placeholder="请输入分组名" />
					</div>
				</div>
				<div class="bottom_cards">
					<Card is-empty @click="changeOperation('add')"></Card>
					<Card showAction v-for="item in groupList.defaultGroupList" :data="item"
						@on-top="changeOperation('top', item)" @on-edit="changeOperation('edit', item)"
						@on-delete="changeOperation('delete', item)"></Card>
				</div>
			</div>
			<Drawer :open="state.open" :title="formMeta[state.operation].title" @ok="formMeta[state.operation].success"
				@close="onClose">
				<a-form ref="formRef" :model="groupFormData" name="basic" :rules="groupRules" :label-col="{ span: 4 }">
					<a-form-item label="分组名" name="groupName">
						<a-input v-model:value="groupFormData.groupName" />
					</a-form-item>
					<a-form-item label="分组描述" name="groupDescription">
						<a-textarea v-model:value="groupFormData.groupDescription" />
					</a-form-item>
				</a-form>
			</Drawer>
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


.search-box {
	background-color: white;
	box-shadow: 0 2px 25px 0 rgba(0, 0, 0, 0.1);
	height: 40px;
	border-radius: 40px;
	display: flex;
	margin-right: var(--spacing-md);
}

.search-txt {
	border: none;
	background: none;
	outline: none;
	padding: 0;
	color: #222;
	font-size: 16px;
	// line-height: 40px;
	width: 0;
	transition: 0.4s;
}

.search-btn {
	border: none;
	width: 40px;
	height: 40px;
	border-radius: 50%;
	display: flex;
	justify-content: center;
	align-items: center;
	cursor: pointer;
	transition: 0.4s;
}

.search-box:hover .search-txt {
	width: 200px;
	padding: 0 12px;
}

.search-box:hover .search-btn {
	background-color: #fff;
	animation: rotate 0.4s linear;
}

.search-txt:focus {
	width: 200px;
	padding: 0 12px;
}


@keyframes rotate {
	0% {
		transform: rotate(0deg);
	}

	100% {
		transform: rotate(360deg);
	}
}
</style>

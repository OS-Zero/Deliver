<script lang="ts" setup>
import { ref, reactive, watch } from 'vue';
import { useRouter } from 'vue-router';
import Card from './components/Card.vue';
import { addGroup, getGroupData, updateGroup, deleteGroup, toTopGroup } from '@/api/group';
import { GroupCard, GroupCardList } from '@/types/group';
import { getRules } from '@/config/rules';
import { message } from 'ant-design-vue';

const router = useRouter()
const state = reactive({
	mainPage: true,
	open: false,
	operation: 'add',
	search: ''
})
const onOpen = () => {
	state.open = true
}
const onClose = () => {
	state.open = false
}
const groupList = reactive<GroupCardList>({
	topUpGroupList: [],
	defaultGroupList: []
})
const groupFormData = reactive<GroupCard>({
	groupId: 0,
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
const formMeta = {
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
	data && Object.assign(data);
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
					<Card v-for="item in groupList.topUpGroupList" :data="item"></Card>
				</div>
			</div>
			<div class="card-bottom">
				<div class="bottom-section">
					<h3>全部分组</h3>
					<a-input-search v-model:value="state.search" placeholder="请输入分组名" enter-button style="width: 200px"
						@search="flashData(state.search)" />
				</div>
				<div class="bottom_cards">
					<Card is-empty @click="changeOperation('add')"></Card>
					<Card showAction v-for="item in groupList.defaultGroupList" :data="item"
						@on-top="changeOperation('top', item)" @on-edit="changeOperation('edit', item)"
						@on-delete="changeOperation('delete', item)"></Card>
				</div>
			</div>
			<a-drawer :width="500" :title="formMeta[state.operation].title" placement="right" :open="state.open"
				@close="onClose">
				<template #extra>
					<a-button style="margin-right: 8px" @click="onClose">取消</a-button>
					<a-button type="primary" @click="formMeta[state.operation].success">确定</a-button>
				</template>
				<a-form ref="formRef" :model="groupFormData" name="basic" :rules="groupRules" :label-col="{ span: 4 }">
					<a-form-item label="分组名" name="groupName">
						<a-input v-model:value="groupFormData.groupName" />
					</a-form-item>
					<a-form-item label="分组描述" name="groupDescription">
						<a-textarea v-model:value="groupFormData.groupDescription" />
					</a-form-item>
				</a-form>
			</a-drawer>
		</template>
		<template v-else>
			<RouterView />
		</template>
	</div>
</template>

<style lang="scss" scoped>
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
</style>

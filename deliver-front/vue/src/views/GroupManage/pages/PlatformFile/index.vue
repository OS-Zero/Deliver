<script lang="ts" setup>
import { ref, onBeforeMount, reactive, h, watch } from 'vue'
import { CopyOutlined, DownOutlined, FilterOutlined, CloseOutlined } from '@ant-design/icons-vue';
import { copyToClipboard, getDataFromSchema } from '@/utils/utils';
import { FormInstance, message } from 'ant-design-vue';
import SearchInput from '@/components/SearchInput/index.vue'
import { debounce } from 'lodash'
import { usePagination } from '@/hooks/table';
import { getPlatformFile } from '@/api/platformFile';
import { PlatformFile } from '@/types/platformFile';
import { filterForm, platformFileColumns, setFilterOptionsDispatch } from '@/config/platformFile';
import PlatformFileDrawer from './components/PlatformFileDrawer.vue';
import { getColor } from '@/utils/table';
type Operation = 'upload' | 'more'
const dataSource = ref<PlatformFile[]>([])
const loading = ref(false)
const searchValue = ref('')
const handleSearch = async () => {
	try {
		loading.value = true
		const { records, total } = await getPlatformFile({ platformFileName: searchValue.value, ...getDataFromSchema(filterForm), pageSize: pagination.pageSize, currentPage: pagination.current })
		dataSource.value = records
		pagination.total = total
		loading.value = false
	} catch (error) {
		loading.value = false
	}

}
const debounceSearch = debounce(handleSearch, 200)
const { pagination, resetPagination } = usePagination(handleSearch)
watch(filterForm, () => {
	resetPagination()
	debounceSearch()
})
const copyId = async (text: string) => {
	try {
		await copyToClipboard(text)
		message.success('复制成功')
	} catch (error) {
		message.error('复制失败')
	}
}
const drawerState = reactive<{
	open: boolean
	operation: 'upload' | 'more'
	record: Record<string, any>
}>({
	open: false,
	operation: 'upload',
	record: {}
})
const filterState = reactive({
	open: false
})
const formRef = ref<FormInstance>();
const handleActions = async (operation: Operation, record: Record<string, any> = {}) => {
	(operation === 'upload' || operation === 'more') && (drawerState.operation = operation, drawerState.open = true);
	(operation === 'more') && (drawerState.record = record);
}
const handleFilterClose = () => {
	filterState.open = false
	formRef.value?.resetFields()
}
const handleDrawerClose = (flash: boolean = false) => {
	drawerState.open = false;
	flash && (resetPagination(), handleSearch());
}
onBeforeMount(() => {
	setFilterOptionsDispatch['channelType']()
	handleSearch()
})
</script>

<template>
	<div class="container">
		<div class="container-table">
			<div class="table-header">
				<SearchInput class="search_input" placeholder="请输入文件名" v-model="searchValue" @search="debounceSearch()">
				</SearchInput>
				<div class="operation">
					<a-button class="btn--add" @click="handleActions('upload')" type="primary">上传</a-button>
					<a-button :icon="h(FilterOutlined)" @click="filterState.open = !filterState.open"></a-button>
				</div>
			</div>
			<a-table row-key="platformFileId" :dataSource="dataSource" :columns="platformFileColumns" :pagination="pagination"
				:scroll="{ x: 1400 }" :loading="loading">
				<template #bodyCell="{ column, text, record }">
					<template v-if="column.key === 'platformFileId'">
						{{ text }}
						<CopyOutlined class="id--copy" @click="copyId(text)" />
					</template>
					<template v-else-if="column.key === 'platformFileStatus'">
						<a-tag v-if="record[column.key]" color="success">生效中</a-tag>
						<a-tag v-else="record[column.key]" color="error">已过期</a-tag>
					</template>
					<template v-else-if="['platformFileTypeName', 'channelTypeName'].includes(column.key as string)">
						<a-tag :color="getColor(text)">{{ text }}</a-tag>
					</template>
					<template v-else-if="column.key === 'appName'">
						<span style="color: var(--primary-color);">{{ text }}</span>
					</template>
					<template v-else-if="column.key === 'actions'">
						<a-dropdown placement="bottom">
							<a-button type="link">
								更多操作
								<DownOutlined />
							</a-button>
							<template #overlay>
								<a-menu>
									<a-menu-item>
										<div @click="handleActions('more', record)">查看更多</div>
									</a-menu-item>
								</a-menu>
							</template>
						</a-dropdown>
					</template>
				</template>
			</a-table>
		</div>
		<a-card size="small" class="filter-form" :class="{ open: filterState.open }" title="筛选">
			<template #extra><a-button type="text" :icon="h(CloseOutlined)" @click="handleFilterClose"></a-button></template>
			<Form ref="formRef" :form-schema="filterForm" />
		</a-card>
		<PlatformFileDrawer v-bind="drawerState" @close="handleDrawerClose"></PlatformFileDrawer>
	</div>
</template>

<style lang="scss" scoped>
.container {
	display: flex;
	height: 100%;
}

.btn--add {
	margin-right: var(--spacing-md);
}

.container-table {
	width: calc(100% - 300px);
	flex: 1 auto;
}

.ant-card {
	border-radius: 0;
	padding: 0;
	margin-left: var(--spacing-md);
}

::v-deep .ant-card-head {
	border: none;
	font-size: large;
}

.filter-form {
	overflow: hidden;
	transition: width 120ms;
	border: none;
	width: 0;
	height: 0;

	&.open {
		border-left: 1px solid var(--gray-lighter);
		width: 300px;
		height: 100%;
	}
}


.search_input {
	width: 200px;
}

.table-header {
	display: flex;
	justify-content: space-between;
	align-items: center;
	margin-bottom: var(--spacing-xs);
	padding: var(--spacing-xs) 0;
}

.id--copy {
	cursor: pointer;
	color: var(--blue-darker);
}

.selections--delete {
	border-radius: var(--border-radius-small);
	padding: var(--spacing-sm);
	background-color: var(--gray-lightest);
	margin-bottom: var(--spacing-md);
	color: var(--gray-dark);
	display: flex;
	align-items: center;
	justify-content: space-between;
}
</style>

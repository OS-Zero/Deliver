<script lang="ts" setup>
import { ref, onBeforeMount, reactive, h, onUnmounted, watch } from 'vue'
import { CopyOutlined, DownOutlined, FilterOutlined, CloseOutlined } from '@ant-design/icons-vue';
import { copyToClipboard, dynamic, getDataFromSchema } from '@/utils/utils';
import { FormInstance, message } from 'ant-design-vue';
import Drawer from '@/components/Drawer/index.vue'
import SearchInput from '@/components/SearchInput/index.vue'
import { debounce } from 'lodash'
import { usePagination } from '@/hooks/table';
import { getPlatformFile, uploadPlatformFile } from '@/api/platformFile';
import { PlatformFile } from '@/types/platformFile';
import { platformFileSchema, platformFileSchemaDeps, filterSchema, filterSchemaMaps, platformFileLocale, platformFileColumns } from '@/config/platformFile';
const dataSource = reactive<PlatformFile[]>([])


const { dynamicData: platformFileForm, stop } = dynamic(platformFileSchema, platformFileSchemaDeps)
const { dynamicData: filterForm, stop: stopFilterDynamic } = dynamic(filterSchema, filterSchemaMaps)

const handleSearch = async () => {
	const { records, total } = await getPlatformFile({ ...getDataFromSchema(filterForm), pageSize: pagination.pageSize, currentPage: pagination.current })
	Object.assign(dataSource, records)
	pagination.total = total
}
const debounceSearch = debounce(handleSearch, 200)
const { pagination } = usePagination(handleSearch)
watch(filterForm, () => {
	debounceSearch()
})
const moreInfo = reactive<Array<{ label: string; value: any }>>([])
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
	title: string
	operation: string
	placement: 'left' | 'right' | 'top' | 'bottom' | undefined
	extra: boolean
}>({
	open: false,
	title: '',
	operation: '',
	placement: "right",
	extra: true
})
const filterState = reactive({
	open: false
})


const formRef = ref<FormInstance>();
const handleActions = async (action: 'upload' | 'more', record?: Record<string, any>) => {
	drawerState.placement = 'right'
	if (action === 'upload') {
		drawerState.title = '上传文件'
		drawerState.open = true
		drawerState.operation = 'upload'
	} else if (action === 'more') {
		drawerState.title = '文件详情'
		drawerState.operation = 'showMore'
		drawerState.placement = 'left'
		drawerState.extra = false
		drawerState.open = true
		const set = new Set(['channelType', 'appId', 'platformFileType'])
		const arr: Array<{ label: string; value: any }> = []
		for (const key in record) {
			if (!set.has(key)) {
				arr.push({
					label: platformFileLocale[key],
					value: record[key]
				})
			}
		}
		Object.assign(moreInfo, arr)
	}
}
const handleDrawer = {
	ok: async () => {
		try {
			await formRef.value?.validate()
			if (drawerState.operation === 'upload') {
				await uploadPlatformFile(getDataFromSchema(platformFileForm))
				message.success('上传成功')
			} else if (drawerState.operation === 'more') {
				drawerState.placement = 'right'
			}
			drawerState.open = false
			formRef.value?.resetFields()
		} catch (error) {
			console.log(error);
		}
	},
	cancel: () => {
		drawerState.open = false
		drawerState.extra = true
		formRef.value?.resetFields()
	}
}
const handleFilterClose = () => {
	filterState.open = false
	formRef.value?.resetFields()
}
onBeforeMount(() => {
	handleSearch()
})
onUnmounted(() => {
	//停止监听动态数据
	stop()
	stopFilterDynamic()
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
					<a-button :icon="h(FilterOutlined)" shape="circle" type="text"
						@click="filterState.open = !filterState.open"></a-button>
				</div>
			</div>
			<a-table row-key="platformFileId" :dataSource="dataSource" :columns="platformFileColumns" :pagination="pagination"
				:scroll="{ x: 1400, y: 680 }">
				<template #bodyCell="{ column, text, record }">
					<template v-if="column.key === 'platformFileId'">
						{{ text }}
						<CopyOutlined class="id--copy" @click="copyId(text)" />
					</template>
					<template v-if="column.key === 'platformFileStatus'">
						<a-tag v-if="record[column.key]" color="success">生效中</a-tag>
						<a-tag v-else="record[column.key]" color="error">已过期</a-tag>
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
		<Drawer :placement="drawerState.placement" :open="drawerState.open" :title="drawerState.title"
			:extra="drawerState.extra" @ok="handleDrawer.ok" @close="handleDrawer.cancel">
			<Form v-if="drawerState.operation === 'upload'" ref="formRef" :label-col="{ span: 7 }"
				:form-schema="platformFileForm" />
			<div v-else-if="drawerState.operation === 'showMore'">
				<a-descriptions :column="1">
					<a-descriptions-item v-for="item in moreInfo" :label="item.label">{{ item.value }}</a-descriptions-item>
				</a-descriptions>
			</div>
		</Drawer>
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
	height: 100%;
	overflow: hidden;
	transition: width 120ms;
	border: none;
	width: 0;

	&.open {
		border-left: 1px solid var(--gray-lighter);
		width: 300px
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

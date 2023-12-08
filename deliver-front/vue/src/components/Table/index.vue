<script lang="ts" setup>
import { h } from 'vue'
import Table from '@/types/table'
import { getColor } from '@/utils/table'
interface Props {
	model: Array<Record<string, any>>
	columns: Table.Columns[]
}
defineProps<Props>()
</script>
<template>
	<a-table :columns="columns" :data-source="model" :scroll="{ x: 1200, y: undefined, scrollToFirstRowOnChange: true }">
		<template #headerCell="{ column }">
			<template v-if="column.head === 'icon'">
				<span>
					<component :is="h(column.icon)"></component>
					{{ column.title }}
				</span>
			</template>
		</template>
		<template #bodyCell="{ column, record, index }">
			<template v-if="column.type === 'tag'">
				<a-tag :color="getColor(record[column.dataIndex])" style="width: 70px; text-align: center">
					<span>{{ column.filter(record[column.dataIndex]) }}</span>
				</a-tag>
			</template>
			<template v-else-if="column.type === 'blue'">
				<span style="color: #1677ff">{{ column.filter(record[column.dataIndex]) }}</span>
			</template>
			<template v-else-if="column.type === 'img'">
				<img style="height: 30px; width: 30px" :src="column.filter(record[column.dataIndex])" :alt="record[column.dataIndex]" />
			</template>
			<template v-else-if="column.type === 'switch'">
				<a-switch
					v-model:checked="record[column.dataIndex]"
					checked-children="启用"
					un-checked-children="禁用"
					:checkedValue="1"
					:unCheckedValue="0" />
			</template>
			<template v-else-if="column.type === 'operation'">
				<a-tooltip v-for="btn in column.buttons" :key="btn.command">
					<template #title>{{ btn.tip }}</template>
					<CenterModal :config="btn.config" :model="model[index]" v-if="btn.modal === 'center'">
						<template #button="{ openModel }">
							<a-button :type="btn.type" :size="btn.size" @click="openModel">
								<component :is="h(btn.icon)" :two-tone-color="btn.color"></component>
							</a-button>
						</template>
					</CenterModal>
					<RightModal v-else-if="btn.modal === 'right'"></RightModal>
					<a-button v-else :type="btn.type" :size="btn.size">
						<component :is="h(btn.icon)" :two-tone-color="btn.color"></component>
					</a-button>
				</a-tooltip>
			</template>
		</template>
	</a-table>
</template>
<style lang="scss" scoped></style>

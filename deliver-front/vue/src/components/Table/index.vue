<script lang="ts" setup></script>
<template>
	<div id="message-container" :style="{ height: hasSelected ? 'calc(100% + 40px)' : 'auto' }">
		<div class="message-section">
			<TableHeader></TableHeader>
			<!-- 表格部分 -->
			<div class="describe" v-if="hasSelected">
				<template v-if="hasSelected">
					<span class="count">
						{{ `已选择 ${state.selectedRowKeys.length} 项` }}
					</span>
					<a-button type="link" class="cancel" @click="cancelSelect">取消选择</a-button>
				</template>
			</div>
			<a-table
				:columns="columns"
				:data-source="templateTable"
				:scroll="{ x: 1200, y: undefined, scrollToFirstRowOnChange: true }"
				class="components-table-demo-nested"
				@expand="getInnerData"
				:loading="tableLoadFlag"
				:expandIconColumnIndex="-1"
				:expandIconAsCell="false"
				:pagination="false"
				:expandedRowKeys="expandedRowKeys"
				:row-selection="{ selectedRowKeys: state.selectedRowKeys, onChange: onSelectChange }">
				>
				<template #headerCell="{ column }">
					<template v-if="column.key === 'templateId'">
						<span>
							<ThunderboltOutlined />
							模版 ID
						</span>
					</template>
					<template v-if="column.key === 'operation'">
						<span>
							<SettingOutlined />
							操作
						</span>
					</template>
				</template>
				<template #bodyCell="{ column, record }">
					<!-- 表格数据渲染 -->
					<template v-if="column.key === 'templateId'">
						<span style="font-weight: bold">
							{{ record.templateId }}
							<a-tooltip :color="'blue'">
								<template #title>复制 TemplateId 发送消息吧~</template>
								<CopyTwoTone @click="copyTemplateId(record.templateId)" />
							</a-tooltip>
						</span>
					</template>
					<template v-if="column.key === 'pushRange'">
						<a-tag color="green" v-if="record.pushRange == 0" style="width: 70px; text-align: center">
							<span>{{ getPushRange(Number(record.pushRange)) }}</span>
						</a-tag>
						<a-tag color="blue" v-if="record.pushRange == 1" style="width: 70px; text-align: center">
							<span>{{ getPushRange(Number(record.pushRange)) }}</span>
						</a-tag>
						<a-tag color="purple" v-if="record.pushRange == 2" style="width: 70px; text-align: center">
							<span>{{ getPushRange(Number(record.pushRange)) }}</span>
						</a-tag>
					</template>
					<template v-if="column.key === 'usersType'">
						<a-tag color="cyan" v-if="record.usersType == 1" style="width: 80px; text-align: center">
							<span>{{ getUsersType(Number(record.usersType)) }}</span>
						</a-tag>
						<a-tag color="orange" v-if="record.usersType == 2" style="width: 80px; text-align: center">
							<span>{{ getUsersType(Number(record.usersType)) }}</span>
						</a-tag>
						<a-tag color="pink" v-if="record.usersType == 3" style="width: 80px; text-align: center">
							<span>{{ getUsersType(Number(record.usersType)) }}</span>
						</a-tag>
						<a-tag color="red" v-if="record.usersType == 4" style="width: 80px; text-align: center">
							<span>{{ getUsersType(Number(record.usersType)) }}</span>
						</a-tag>
					</template>
					<template v-if="column.key === 'channelType'">
						<span>
							<!-- 根据 appType 的值显示不同的图片 -->
							<img style="height: 30px; width: 30px" v-if="record.channelType === 1" src="电话.png" alt="电话" />
							<img style="height: 30px; width: 30px" v-else-if="record.channelType === 2" src="短信.png" alt="短信" />
							<img style="height: 30px; width: 30px" v-else-if="record.channelType === 3" src="邮件.png" alt="邮件" />
							<img style="height: 30px; width: 30px" v-else-if="record.channelType === 4" src="钉钉.png" alt="钉钉" />
							<img style="height: 30px; width: 30px" v-else-if="record.channelType === 5" src="企业微信.png" alt="企业微信" />
							<img style="height: 30px; width: 30px" v-else-if="record.channelType === 6" src="飞书.png" alt="飞书" />
							<!-- 添加更多条件根据需要显示不同的图片 -->
						</span>
					</template>
					<template v-if="column.key === 'messageType'">
						<span style="color: #1677ff">{{ getMessageTypeArr(record.messageType) }}</span>
					</template>
					<template v-if="column.key === 'templateStatus'">
						<span>
							<a-switch
								v-model:checked="record.templateStatus"
								checked-children="启用"
								un-checked-children="禁用"
								:checkedValue="1"
								:unCheckedValue="0"
								@click="changeStatus(record.templateId, record.templateStatus)" />
						</span>
					</template>
					<template v-if="column.key === 'operation'">
						<a-button type="link" size="small" style="font-size: 14px" @click="getInnerData(false, record)" v-if="judgeInclude(record)">
							<UpCircleTwoTone style="font-size: 18px" />
						</a-button>
						<a-tooltip v-if="!judgeInclude(record)">
							<template #title>查看消息模版更多信息</template>
							<a-button type="link" size="small" style="font-size: 14px" @click="getInnerData(true, record)" v-if="!judgeInclude(record)">
								<DownCircleTwoTone style="font-size: 18px" />
							</a-button>
						</a-tooltip>

						<a-divider type="vertical" />
						<a-tooltip>
							<template #title>修改消息模版</template>
							<a-button type="link" class="btn-manager" size="small" style="font-size: 14px" @click="startModify(record)">
								<EditTwoTone two-tone-color="#1677FF" style="font-size: 18px" />
							</a-button>
						</a-tooltip>
						<modifyTemplate ref="modifytemplate" :mod="record" @update="updateTemplate()" />
						<a-divider type="vertical" />
						<sendTest :test="record.templateId" :message-type="record.messageType" :channel-type="record.channelType" />
						<a-divider type="vertical" />
						<a-popconfirm title="确认删除吗?" @confirm="onDelete(record.templateId)" ok-text="确定" cancel-text="取消">
							<a-tooltip placement="bottom">
								<template #title>删除消息模版</template>
								<a-button type="link" danger size="small" style="font-size: 14px; margin-left: -5px">
									<DeleteTwoTone two-tone-color="red" style="font-size: 18px" />
								</a-button>
							</a-tooltip>
						</a-popconfirm>
					</template>
				</template>
				<template #expandedRowRender="{ record }">
					<a-descriptions :column="4">
						<a-descriptions-item label="模板累计使用数" :span="2">
							<a-tag color="red">
								<span>{{ record.useCount }}</span>
							</a-tag>
						</a-descriptions-item>
						<a-descriptions-item label="关联 AppId">
							<strong>{{ record.appId }}</strong>
						</a-descriptions-item>
						<a-descriptions-item label="关联 AppName">
							{{ record.appName }}
						</a-descriptions-item>
						<a-descriptions-item label="创建者">{{ record.createUser }}</a-descriptions-item>
						<a-descriptions-item label="创建时间">{{ record.createTime }}</a-descriptions-item>
					</a-descriptions>
				</template>
			</a-table>
			<a-pagination
				v-model:current="current"
				v-model:pageSize="pageSize"
				class="pagination"
				show-quick-jumper
				:total="total"
				@change="change"
				showSizeChanger
				:locale="locale"
				:show-total="(total) => `共 ${total} 条数据`" />
		</div>
		<!-- 对表格的操作 -->
		<div class="showDelete" :style="{ width: `calc(100% - ${a}px)` }" v-if="hasSelected">
			<div class="box">{{ `已选择 ${state.selectedRowKeys.length} 项` }}</div>
			<div class="del">
				<a-button type="primary" style="font-size: 14px" :loading="state.loading" @click="showDeleteConfirm">批量删除</a-button>
				<contextHolder />
			</div>
		</div>
	</div>
</template>
<style lang="scss" scoped>
#message-container {
	position: relative;
	width: 100%;
	overflow: auto;
	// height: 100%;

	.box {
		width: 15%;
		height: 100%;
		margin-left: 2%;
	}

	.del {
		margin-left: 75%;
	}

	.splitter {
		display: flex;
		align-items: center;
		justify-content: right;
		width: 100%;
		height: 60px;
		margin-bottom: 6px;
	}

	.message-section {
		padding: 12px;
		margin-top: 12px;
		background: rgb(255, 255, 255);
		border-radius: 6px;

		.btn-manager {
			margin-right: 10px;
		}

		.pagination {
			display: flex;
			justify-content: right;
			margin-top: 20px;
		}

		.describe {
			width: 100%;
			height: 40px;
			margin-bottom: 20px;
			line-height: 40px;
			background-color: rgb(248, 248, 248);
			border-radius: 10px;

			.count {
				margin-left: 30px;
				color: gray;
			}

			.cancel {
				position: absolute;
				right: 50px;
				padding-top: 7px;
			}
		}
	}

	.showDelete {
		position: fixed; /* 将showDelete盒子设置为固定定位 */
		right: 0; /* 将showDelete盒子的左侧与页面左侧对齐 */
		bottom: 0; /* 将showDelete盒子的底部与页面底部对齐 */
		z-index: 999;
		box-sizing: border-box; /* 确保padding不会撑开盒子 */
		display: flex;
		align-items: center;
		height: 60px;
		padding: 10px;
		line-height: 40px;
		background-color: rgb(255 255 255 / 90%);
		transition: 0.2s;
		inset-inline-end: 0;
	}
}
</style>

<script lang="ts" setup>
import Form from '@/types/form'
interface Props {
	fieldList: Form.FieldItem[]
	model: Record<Form.FieldItem['field'], Form.FieldItem['value']>
}
defineProps<Props>()
</script>
<template>
	<a-modal v-model:open="open" title="新增模板" width="650px" :footer="null" @cancel="handleCancel">
		<a-form ref="templateForm" :model="templateItem" :rules="rules" :label-col="labelCol" :wrapper-col="wrapperCol" class="temform">
			<a-form-item ref="templateName" label="模板名" name="templateName" class="tem-item">
				<a-input :maxlength="20" v-model:value="templateItem.templateName" placeholder="请填写长度在3到20个字符的模板名" style="width: 70%" />
			</a-form-item>
			<a-form-item label="推送范围" name="pushRange" class="tem-item">
				<a-radio-group v-model:value="templateItem.pushRange" button-style="solid" @change="handlePushRangeChange">
					<a-radio-button :value="0">不限</a-radio-button>
					<a-radio-button :value="1">企业内部</a-radio-button>
					<a-radio-button :value="2">企业外部</a-radio-button>
				</a-radio-group>
			</a-form-item>
			<a-form-item label="用户类型" name="usersType" class="tem-item">
				<a-radio-group v-model:value="templateItem.usersType" button-style="solid" @change="pickChannel">
					<a-radio-button v-for="u in userdisabled" :key="u.value" :disabled="u.disabled" :value="u.value">
						{{ u.label }}
					</a-radio-button>
				</a-radio-group>
			</a-form-item>
			<a-form-item label="渠道选择" name="channelType" class="tem-item">
				<a-select
					v-model:value="templateItem.channelType"
					:options="channelData.map((pro) => ({ value: pro.value, label: pro.label }))"
					style="width: 70%"
					@change="selectValues"
					:disabled="channelDisabled" />
			</a-form-item>
			<a-form-item label="渠道 App" name="appId" class="tem-item">
				<a-select v-model:value="templateItem.appId" style="width: 70%" :disabled="appDisabled" @change="selectApp">
					<a-select-option v-for="item in appData" :key="item.appId" :value="item.appId">
						{{ item.appName }}
					</a-select-option>
				</a-select>
			</a-form-item>
			<a-form-item label="消息类型" name="messageType" class="tem-item">
				<a-select v-model:value="templateItem.messageType" style="width: 70%" :disabled="messageDisabled">
					<a-select-option v-for="item in messageData" :key="item.code" :value="item.code">
						{{ item.name }}
					</a-select-option>
				</a-select>
			</a-form-item>
			<a-form-item label="模板状态" name="templateStatus" class="tem-item">
				<a-switch
					v-model:checked="templateItem.templateStatus"
					checked-children="启用"
					un-checked-children="禁用"
					:checkedValue="1"
					:unCheckedValue="0" />
			</a-form-item>
			<a-form-item :wrapper-col="{ span: 25, offset: 6 }" class="tem-item">
				<a-button @click="handleCancel">重置</a-button>
				<a-button style="margin-left: 10px" type="primary" @click="handleOk" :loading="iconLoading">确认新建</a-button>
			</a-form-item>
		</a-form>
	</a-modal>
</template>
<style lang="scss" scoped></style>

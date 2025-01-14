import { forwardRef, useImperativeHandle, useRef, useState } from 'react';
import { Form, Input, Button, Select, Drawer, Space, message, FormInstance } from 'antd';
import { getChannelType, getParam } from '@/api/system';
import { useFormState } from '@/hooks/useFormState';
import { useGlobalContext } from '@/context/GlobalContext';
import { getApp } from '@/api/messageTemplate';
import { handleFieldValue } from '@/utils/handleFieldValue';

const { Option } = Select;
const { TextArea } = Input;

interface AddTemplateModalProps {
  onSubmit?: (values: any) => void;
  onSearch?: (params: any) => void;
}

const AddTemplateModal = forwardRef((props: AddTemplateModalProps, ref) => {
  const { onSubmit, onSearch } = props;
  const [open, setOpen] = useState(false);
  const formRef = useRef<FormInstance>(null);
  const { formState, updateFormState } = useFormState();
  const { userTypeParams } = useGlobalContext();

  const handleUsersTypeChange = async (value: number) => {
    formRef?.current?.resetFields(['channelType', 'channelProviderType', 'messageType', 'appId']);
    updateFormState({
      channelTypes: [],
      channelProviders: [],
      messageTypes: [],
      appIds: []
    });
    try {
      const response = await getChannelType({ usersType: value });
      updateFormState({ channelTypes: response });
    } catch (error) {
      console.error('获取渠道类型失败:', error);
    }
  };

  const handleChannelTypeChange = async (value: number) => {
    formRef?.current?.resetFields(['channelProviderType', 'messageType', 'appId']);
    updateFormState({ channelProviders: [], messageTypes: [], appIds: [] });
    try {
      const response = await getParam({ channelType: value });
      updateFormState({
        channelProviders: response.channelProviderTypeList,
        messageTypes: response.messageTypeList
      });
    } catch (error) {
      console.error('获取渠道供应商和消息类型失败:', error);
    }
  };

  const handleChannelProviderChange = async (value: number, editValue?: number) => {
    formRef?.current?.resetFields(['appId']);
    updateFormState({ appIds: [] });
    try {
      const params = {
        channelType: formRef?.current?.getFieldValue('channelType') || editValue,
        channelProviderType: value
      };
      const res = await getApp(params);
      updateFormState({
        appIds: res
      });
    } catch (error) {
      console.error('获取渠道供应商和消息类型失败:', error);
    }
  };

  const handleSubmit = async () => {
    try {
      const values = await formRef?.current?.validateFields();

      values.channelType = handleFieldValue(
        values.channelType,
        formState.channelTypes,
        'channelTypeName',
        'channelType'
      );

      // 处理 channelProviderType
      values.channelProviderType = handleFieldValue(
        values.channelProviderType,
        formState.channelProviders,
        'channelProviderTypeName',
        'channelProviderType'
      );

      values.appId = handleFieldValue(values.appId, formState.appIds, 'appName', 'appId');

      values.messageType = handleFieldValue(
        values.messageType,
        formState.messageTypes,
        'messageTypeName',
        'messageType'
      );

      values.usersType = handleFieldValue(
        values.usersType,
        userTypeParams,
        'usersTypeName',
        'usersType'
      );

      onSubmit?.(values);
      message.success('保存成功');
      formRef?.current?.resetFields();
      onSearch?.({ currentPage: 1, pageSize: 10 });
    } catch (error) {
      console.error('保存失败:', error);
    } finally {
      setOpen(false);
    }
  };

  useImperativeHandle(ref, () => ({
    addTemplateModal: () => {
      setOpen(true);
    },
    editTemplateModal: async (values: any) => {
      setOpen(true);
      await handleUsersTypeChange(Number(values.usersType));
      await handleChannelTypeChange(Number(values.channelType));
      await handleChannelProviderChange(
        Number(values.channelProviderType),
        Number(values.channelType)
      );
      formRef?.current?.setFieldsValue({
        ...values,
        usersType: values.usersTypeName,
        channelType: values.channelTypeName,
        channelProviderType: values.channelProviderTypeName,
        messageType: values.messageTypeName,
        appId: values.appName
      });
    }
  }));

  return (
    <Drawer
      title={formRef?.current?.getFieldValue('templateId') ? '编辑模板' : '新增模板'}
      open={open}
      onClose={() => setOpen(false)}
      width={500}
      extra={
        <Space>
          <Button onClick={() => setOpen(false)}>取消</Button>
          <Button type="primary" onClick={handleSubmit}>
            确定
          </Button>
        </Space>
      }
    >
      <Form ref={formRef} layout="vertical" wrapperCol={{ span: 24 }}>
        <Form.Item label="模板 ID" name="templateId" hidden></Form.Item>
        <Form.Item
          label="模板名"
          name="templateName"
          rules={[{ required: true, message: '请输入模板名' }]}
        >
          <Input placeholder="请输入模板名" />
        </Form.Item>
        <Form.Item
          label="模板描述"
          name="templateDescription"
          rules={[{ required: true, message: '请输入模板描述' }]}
        >
          <TextArea placeholder="请输入模板描述" rows={4} />
        </Form.Item>
        <Form.Item
          label="用户类型"
          name="usersType"
          rules={[{ required: true, message: '请选择用户类型' }]}
        >
          <Select placeholder="请选择用户类型" onChange={handleUsersTypeChange}>
            {userTypeParams.map((item) => (
              <Option key={item.usersType} value={item.usersType}>
                {item.usersTypeName}
              </Option>
            ))}
          </Select>
        </Form.Item>
        <Form.Item
          label="渠道类型"
          name="channelType"
          rules={[{ required: true, message: '请选择渠道类型' }]}
        >
          <Select placeholder="请选择渠道类型" onChange={handleChannelTypeChange}>
            {formState.channelTypes.map((channel) => (
              <Option key={channel.channelType} value={channel.channelType}>
                {channel.channelTypeName}
              </Option>
            ))}
          </Select>
        </Form.Item>
        <Form.Item
          label="渠道供应商类型"
          name="channelProviderType"
          rules={[{ required: true, message: '请选择渠道供应商类型' }]}
        >
          <Select placeholder="请选择渠道供应商类型" onChange={handleChannelProviderChange}>
            {formState.channelProviders.map((provider) => (
              <Option key={provider.channelProviderType} value={provider.channelProviderType}>
                {provider.channelProviderTypeName}
              </Option>
            ))}
          </Select>
        </Form.Item>
        <Form.Item
          label="消息类型"
          name="messageType"
          rules={[{ required: true, message: '请选择消息类型' }]}
        >
          <Select placeholder="请选择消息类型">
            {formState.messageTypes.map((messageType) => (
              <Option key={messageType.messageType} value={messageType.messageType}>
                {messageType.messageTypeName}
              </Option>
            ))}
          </Select>
        </Form.Item>
        <Form.Item
          label="关联应用"
          name="appId"
          rules={[{ required: true, message: '请选择关联应用' }]}
        >
          <Select placeholder="请选择关联应用">
            {formState.appIds.map((app) => (
              <Option key={app.appId} value={app.appId}>
                {app.appName}
              </Option>
            ))}
          </Select>
        </Form.Item>
      </Form>
    </Drawer>
  );
});

AddTemplateModal.displayName = 'AddTemplateModal';

export default AddTemplateModal;

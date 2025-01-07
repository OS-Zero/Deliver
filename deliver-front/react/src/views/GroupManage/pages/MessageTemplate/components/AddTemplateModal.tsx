import { forwardRef, useImperativeHandle, useRef, useState } from 'react';
import { Form, Input, Button, Select, Drawer, Space, message, FormInstance } from 'antd';
import { getChannelType, getParam } from '@/api/system';
import useFormState from '../../../../../hooks/useFormState';

const { Option } = Select;
const { TextArea } = Input;

interface AddTemplateModalProps {
  onSubmit?: (values: any) => void;
}

const AddTemplateModal = forwardRef((props: AddTemplateModalProps, ref) => {
  const { onSubmit } = props;
  const [open, setOpen] = useState(false);
  const formRef = useRef<FormInstance>(null);
  const { formState, updateFormState } = useFormState();

  const handleUsersTypeChange = async (value: number) => {
    formRef?.current?.resetFields(['channelType', 'channelProviderType', 'messageType', 'appId']);
    updateFormState({
      channelTypes: [],
      channelProviders: [],
      messageTypes: [],
      appIds: [],
      isChannelTypeDisabled: false
    });
    try {
      const response = await getChannelType({ usersType: value });
      updateFormState({ channelTypes: response });
    } catch (error) {
      console.error('获取渠道类型失败:', error);
      message.error('获取渠道类型失败，请稍后重试');
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
      message.error('获取渠道供应商和消息类型失败，请稍后重试');
    }
  };

  const handleChannelProviderChange = (value: number) => {
    formRef?.current?.resetFields(['appId']);
    updateFormState({ appIds: [] });
    // 模拟获取应用 ID 列表
    updateFormState({
      appIds: [
        { id: 0, name: '应用1' },
        { id: 1, name: '应用2' }
      ]
    });
  };

  const handleSubmit = () => {
    formRef?.current
      ?.validateFields()
      .then((values) => {
        onSubmit?.(values);
        formRef?.current?.resetFields();
        setOpen(false);
      })
      .catch((info) => {
        console.log('Validate Failed:', info);
      });
  };

  const handleReset = () => {
    formRef?.current?.resetFields();
    updateFormState({
      channelTypes: [],
      channelProviders: [],
      messageTypes: [],
      appIds: [],
      isChannelTypeDisabled: true
    });
  };

  useImperativeHandle(ref, () => ({
    addTemplateModal: () => {
      setOpen(true);
    },
    editTemplateModal: (values: any) => {
      setOpen(true);
      handleUsersTypeChange(values.usersType);
      handleChannelTypeChange(values.channelType);
      handleChannelProviderChange(values.channelProviderType);
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
      title="新增模板"
      open={open}
      onClose={() => {
        setOpen(false);
        handleReset();
      }}
      width={500}
      extra={
        <Space>
          <Button onClick={handleReset}>重置</Button>
          <Button type="primary" onClick={handleSubmit}>
            确定
          </Button>
        </Space>
      }
    >
      <Form ref={formRef} labelCol={{ span: 7 }} wrapperCol={{ span: 16 }}>
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
            <Option value={0}>选项1</Option>
            <Option value={1}>选项2</Option>
          </Select>
        </Form.Item>
        <Form.Item
          label="渠道类型"
          name="channelType"
          rules={[{ required: true, message: '请选择渠道类型' }]}
        >
          <Select
            placeholder="请选择渠道类型"
            onChange={handleChannelTypeChange}
            disabled={formState.isChannelTypeDisabled}
          >
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
          <Select
            placeholder="请选择渠道供应商类型"
            onChange={handleChannelProviderChange}
            disabled={!formRef?.current?.getFieldValue('channelType')}
          >
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
          <Select
            placeholder="请选择消息类型"
            disabled={!formRef?.current?.getFieldValue('channelType')}
          >
            {formState.messageTypes.map((messageType) => (
              <Option key={messageType.messageType} value={messageType.messageType}>
                {messageType.messageTypeName}
              </Option>
            ))}
          </Select>
        </Form.Item>
        <Form.Item
          label="应用 ID"
          name="appId"
          rules={[{ required: true, message: '请选择应用 ID' }]}
        >
          <Select
            placeholder="请选择应用 ID"
            disabled={!formRef?.current?.getFieldValue('channelProviderType')}
          >
            {formState.appIds.map((app) => (
              <Option key={app.id} value={app.id}>
                {app.name}
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

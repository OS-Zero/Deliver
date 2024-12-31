import { forwardRef, useImperativeHandle, useState } from 'react';
import { Modal, Form, Input, Button, Select, Switch, Radio, RadioChangeEvent } from 'antd';

const { Option } = Select;

interface AddTemplateModalProps {
  onSubmit?: (values: any) => void;
}

const AddTemplateModal = forwardRef((props: AddTemplateModalProps, ref) => {
  const { onSubmit } = props;
  const [open, setOpen] = useState(false);
  const [form] = Form.useForm();
  const [pushRange, setPushRange] = useState<number | undefined>(undefined);
  const [usersType, setUsersType] = useState<number | undefined>(undefined);
  const [channelType, setChannelType] = useState<number | undefined>(undefined);
  const [appId, setAppId] = useState<number | undefined>(undefined);

  const handlePushRangeChange = (e: RadioChangeEvent) => {
    const value = e.target.value;
    setPushRange(value);
    setUsersType(undefined);
    setChannelType(undefined);
    setAppId(undefined);
    form.resetFields(['usersType', 'channelType', 'appId', 'messageType']);
  };

  const handleUsersTypeChange = (e: RadioChangeEvent) => {
    const value = e.target.value;
    setUsersType(value);
    setChannelType(undefined);
    setAppId(undefined);
    form.resetFields(['channelType', 'appId', 'messageType']);
  };

  const handleChannelTypeChange = (value: number) => {
    setChannelType(value);
    setAppId(undefined);
    form.resetFields(['appId', 'messageType']);
  };

  const handleAppIdChange = (value: number) => {
    setAppId(value);
    form.resetFields(['messageType']);
  };

  const handleSubmit = () => {
    form.validateFields().then((values) => {
      onSubmit(values);
      form.resetFields();
      setOpen(false);
    });
  };

  const handleReset = () => {
    form.resetFields();
    setPushRange(undefined);
    setUsersType(undefined);
    setChannelType(undefined);
    setAppId(undefined);
  };

  useImperativeHandle(ref, () => ({
    addTemplate: () => {
      setOpen(true);
    }
  }));

  return (
    <Modal
      title="新增模版"
      open={open}
      onCancel={() => {
        setOpen(false);
        handleReset();
      }}
      width={600}
      footer={[
        <Button key="reset" onClick={handleReset}>
          重置
        </Button>,
        <Button key="submit" type="primary" onClick={handleSubmit}>
          确认新增
        </Button>
      ]}
    >
      <Form form={form} labelCol={{ span: 6 }} wrapperCol={{ span: 14 }}>
        <Form.Item
          label="模版名"
          name="templateName"
          rules={[{ required: true, message: '请输入模版名' }]}
        >
          <Input placeholder="请输入模版名" maxLength={20} />
        </Form.Item>
        <Form.Item
          label="推送范围"
          name="pushRange"
          rules={[{ required: true, message: '请选择推送范围' }]}
        >
          <Radio.Group value="middle" onChange={handlePushRangeChange}>
            <Radio.Button value={0}>不限</Radio.Button>
            <Radio.Button value={1}>企业内部</Radio.Button>
            <Radio.Button value={2}>企业外部</Radio.Button>
          </Radio.Group>
        </Form.Item>
        <Form.Item
          label="用户类型"
          name="usersType"
          rules={[{ required: true, message: '请选择用户类型' }]}
        >
          <Radio.Group
            value="middle"
            onChange={handleUsersTypeChange}
            disabled={pushRange === undefined}
          >
            <Radio.Button value={0}>企业账号</Radio.Button>
            <Radio.Button value={1}>电话</Radio.Button>
            <Radio.Button value={2}>邮箱</Radio.Button>
            <Radio.Button value={3}>平台 UserId</Radio.Button>
          </Radio.Group>
        </Form.Item>
        <Form.Item
          label="渠道类型"
          name="channelType"
          rules={[{ required: true, message: '请选择渠道类型' }]}
        >
          <Select
            placeholder="请选择渠道类型"
            onChange={handleChannelTypeChange}
            disabled={usersType === undefined}
          >
            <Option value={0}>电话</Option>
            <Option value={1}>短信</Option>
            <Option value={2}>邮件</Option>
            <Option value={3}>钉钉</Option>
            <Option value={4}>企业微信</Option>
            <Option value={5}>飞书</Option>
          </Select>
        </Form.Item>
        <Form.Item
          label="渠道 App"
          name="appId"
          rules={[{ required: true, message: '请选择渠道 App' }]}
        >
          <Select
            placeholder="请选择渠道 App"
            onChange={handleAppIdChange}
            disabled={channelType === undefined}
          >
            <Option value={0}>App1</Option>
            <Option value={1}>App2</Option>
          </Select>
        </Form.Item>
        <Form.Item
          label="消息类型"
          name="messageType"
          rules={[{ required: true, message: '请选择消息类型' }]}
        >
          <Select placeholder="请选择消息类型" disabled={appId === undefined}>
            <Option value={0}>文本消息</Option>
            <Option value={1}>图片消息</Option>
            <Option value={2}>视频消息</Option>
          </Select>
        </Form.Item>
        <Form.Item
          label="模版状态"
          name="templateStatus"
          valuePropName="checked"
          initialValue={false}
        >
          <Switch checkedChildren="启用" unCheckedChildren="禁用" />
        </Form.Item>
      </Form>
    </Modal>
  );
});

AddTemplateModal.displayName = 'AddTemplateModal';

export default AddTemplateModal;

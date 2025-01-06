import { Form, Select, Button, message, Switch, Card, FormInstance } from 'antd';
import { getChannelType, getParam } from '@/api/system';
import useFormState from '../hooks/useFormState';
import { CloseOutlined } from '@ant-design/icons';
import { useRef } from 'react';

const { Option } = Select;
interface FilterDrawerProps {
  onFilter: (filters: any) => void;
  onClose: (open: boolean) => void;
}

const FilterDrawer = (props: FilterDrawerProps) => {
  const { onClose, onFilter } = props;
  const formRef = useRef<FormInstance>(null);

  // 使用 useFormState 管理表单状态
  const { formState, updateFormState } = useFormState();

  // 用户类型变更处理
  const handleUsersTypeChange = async (value: number) => {
    formRef?.current?.resetFields(['channelType', 'channelProviderType', 'messageType']);
    // 更新表单状态
    updateFormState({
      channelTypes: [],
      channelProviders: [],
      messageTypes: [],
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

  // 渠道类型变更处理
  const handleChannelTypeChange = async (value: number) => {
    formRef?.current?.resetFields(['channelProviderType', 'messageType']);
    updateFormState({ channelProviders: [], messageTypes: [] });
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

  // 确认筛选
  const handleFilter = () => {
    const filters = formRef?.current?.getFieldsValue();
    onFilter(filters);
  };

  // 关闭筛选
  const handleReset = () => {
    formRef?.current?.resetFields();
    onClose(false);
  };

  return (
    <Card
      title="筛选"
      extra={<Button type="text" icon={<CloseOutlined />} onClick={handleReset} />}
      style={{
        width: 300,
        border: '0 solid transparent',
        borderLeft: '1px solid #f0f0f0',
        padding: '0 12px'
      }}
    >
      <Form ref={formRef} layout="vertical" onValuesChange={handleFilter}>
        <Form.Item label="用户类型" name="usersType">
          <Select placeholder="请选择用户类型" onChange={handleUsersTypeChange}>
            <Option value={0}>选项1</Option>
            <Option value={1}>选项2</Option>
          </Select>
        </Form.Item>
        <Form.Item label="渠道类型" name="channelType">
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
        <Form.Item label="渠道供应商类型" name="channelProviderType">
          <Select
            placeholder="请选择渠道供应商类型"
            disabled={!formRef?.current?.getFieldValue('channelType')}
          >
            {formState.channelProviders.map((provider) => (
              <Option key={provider.channelProviderType} value={provider.channelProviderType}>
                {provider.channelProviderTypeName}
              </Option>
            ))}
          </Select>
        </Form.Item>
        <Form.Item label="消息类型" name="messageType">
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
        <Form.Item name="templateStatus" label="模板状态">
          <Select placeholder="请选择模板状态">
            <Option value={0}>禁用</Option>
            <Option value={1}>启用</Option>
          </Select>
        </Form.Item>
      </Form>
    </Card>
  );
};

FilterDrawer.displayName = 'FilterDrawer';

export default FilterDrawer;

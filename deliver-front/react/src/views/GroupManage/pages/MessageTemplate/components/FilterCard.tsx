import { Form, Select, Button, Card, FormInstance, DatePicker } from 'antd';
import { CloseOutlined } from '@ant-design/icons';
import { useRef } from 'react';
import { useDebounce } from '@/hooks/useDebounce';
import { useGlobalContext } from '@/context/GlobalContext';
import { useFormOptions } from '@/hooks/useFormOptions';
import local from 'antd/lib/date-picker/locale/zh_CN.js';

const { Option } = Select;
interface FilterDrawerProps {
  onFilter: (filters: any) => void;
  onClose: (open: boolean) => void;
}

const FilterDrawer = (props: FilterDrawerProps) => {
  const { onClose, onFilter } = props;
  const formRef = useRef<FormInstance>(null);

  const { userTypeParams } = useGlobalContext();

  // 获取change函数以及设置初始获取渠道类型数据
  const {
    options,
    handleUsersTypeChange,
    handleChannelTypeChange,
    handleChannelProviderTypeChange
  } = useFormOptions({
    myRef: formRef,
    key: 'template'
  });

  const debouncedFilter = useDebounce(onFilter);

  // 确认筛选
  const handleFilter = () => {
    const filters = formRef?.current?.getFieldsValue();
    const { startTime, endTime, ...rest } = filters;
    debouncedFilter({
      startTime: startTime?.format('YYYY-MM-DD HH:mm:ss'),
      endTime: endTime?.format('YYYY-MM-DD HH:mm:ss'),
      ...rest
    });
  };

  // 关闭筛选
  const handleReset = () => {
    formRef?.current?.resetFields();
    onClose(false);
    handleFilter();
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
          <Select
            placeholder="请选择用户类型"
            onChange={(value: number) => {
              formRef?.current?.resetFields([
                'channelType',
                'channelProviderType',
                'messageType',
                'appId'
              ]);
              handleUsersTypeChange(value);
            }}
            allowClear
          >
            {userTypeParams.map((item) => (
              <Option key={item.usersType} value={item.usersType}>
                {item.usersTypeName}
              </Option>
            ))}
          </Select>
        </Form.Item>
        <Form.Item label="渠道类型" name="channelType">
          <Select
            placeholder="请选择渠道类型"
            onChange={(value: number) => {
              formRef?.current?.resetFields(['channelProviderType', 'messageType', 'appId']);
              handleChannelTypeChange(value);
            }}
            allowClear
          >
            {options.channelTypeOptions.map((channel) => (
              <Option key={channel.channelType} value={channel.channelType}>
                {channel.channelTypeName}
              </Option>
            ))}
          </Select>
        </Form.Item>
        <Form.Item label="渠道供应商类型" name="channelProviderType">
          <Select
            placeholder="请选择渠道供应商类型"
            onChange={(value: number) => {
              formRef?.current?.resetFields(['messageType', 'appId']);
              handleChannelProviderTypeChange(value);
            }}
            allowClear
          >
            {options.channelProvidersOptions.map((provider) => (
              <Option key={provider.channelProviderType} value={provider.channelProviderType}>
                {provider.channelProviderTypeName}
              </Option>
            ))}
          </Select>
        </Form.Item>
        <Form.Item label="消息类型" name="messageType">
          <Select placeholder="请选择消息类型" allowClear>
            {options.messageTypeOptions.map((messageType) => (
              <Option key={messageType.messageType} value={messageType.messageType}>
                {messageType.messageTypeName}
              </Option>
            ))}
          </Select>
        </Form.Item>
        <Form.Item name="templateStatus" label="模板状态">
          <Select placeholder="请选择模板状态" allowClear>
            <Option value={0}>禁用</Option>
            <Option value={1}>启用</Option>
          </Select>
        </Form.Item>
        <Form.Item label="开始时间" name="startTime">
          <DatePicker
            showTime
            format="YYYY-MM-DD HH:mm:ss"
            placeholder="请选择开始时间"
            locale={local}
          />
        </Form.Item>
        <Form.Item label="结束时间" name="endTime">
          <DatePicker
            showTime
            format="YYYY-MM-DD HH:mm:ss"
            placeholder="请选择结束时间"
            locale={local}
          />
        </Form.Item>
      </Form>
    </Card>
  );
};

FilterDrawer.displayName = 'FilterDrawer';

export default FilterDrawer;

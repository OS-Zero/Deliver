import { Form, Select, Button, Card, FormInstance, DatePicker } from 'antd';
import { CloseOutlined } from '@ant-design/icons';
import { useEffect, useRef, useState } from 'react';
import local from 'antd/lib/date-picker/locale/zh_CN.js';
import { getChannelType, getParam } from '@/api/system';
import { Channel, ChannelProvider } from '../type';
import { useDebounce } from '@/hooks/useDebounce';

interface FilterDrawerProps {
  onFilter: (filters: any) => void;
  onClose: (open: boolean) => void;
}

const FilterDrawer = (props: FilterDrawerProps) => {
  const { onClose, onFilter } = props;
  const formRef = useRef<FormInstance>(null);

  const [options, setOptions] = useState<{
    channelTypeOptions: Channel[];
    channelProvidersOptions: ChannelProvider[];
  }>({
    channelTypeOptions: [],
    channelProvidersOptions: []
  });

  // 渠道类型变更处理
  const handleChannelTypeChange = async (value: number) => {
    formRef?.current?.resetFields(['channelProviderType']);
    try {
      const response = await getParam({ channelType: value });
      setOptions((prev) => ({
        ...prev,
        channelProvidersOptions: response?.channelProviderTypeList || []
      }));
    } catch (error) {
      console.error('获取渠道供应商失败:', error);
    }
  };

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
  };

  useEffect(() => {
    if (!options?.channelTypeOptions?.length) {
      getChannelType({ usersType: -1 }).then((res: Channel[]) => {
        setOptions((prev) => ({
          ...prev,
          channelTypeOptions: res
        }));
      });
    }
  }, []);

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
        <Form.Item label="渠道类型" name="channelType">
          <Select
            placeholder="请选择渠道类型"
            onChange={handleChannelTypeChange}
            options={(options.channelTypeOptions || []).map((d) => ({
              value: d.channelType,
              label: d.channelTypeName
            }))}
          />
        </Form.Item>
        <Form.Item label="渠道供应商类型" name="channelProviderType">
          <Select
            placeholder="请选择渠道供应商类型"
            options={(options.channelProvidersOptions || []).map((d) => ({
              value: d.channelProviderType,
              label: d.channelProviderTypeName
            }))}
          />
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

import { Button, Card, FormInstance, DatePicker, Form, Select, Input } from 'antd';
import { CloseOutlined } from '@ant-design/icons';
import { memo, useRef } from 'react';
import local from 'antd/lib/date-picker/locale/zh_CN.js';
import { useFormOptions } from '@/hooks/useFormOptions';
import { useDebounce } from '@/hooks/useDebounce';

interface FilterDrawerProps {
  onFilter: (filters: any) => void;
  onClose: (open: boolean) => void;
}

const FilterDrawer = (props: FilterDrawerProps) => {
  const { onClose, onFilter } = props;
  const formRef = useRef<FormInstance>(null);

  const { options, handleChannelTypeChange } = useFormOptions({
    myRef: formRef,
    key: 'file'
  });

  const debouncedFilter = useDebounce(onFilter);

  // 确认筛选
  const handleFilter = () => {
    const filters = formRef?.current?.getFieldsValue();
    debouncedFilter(filters);
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
        <Form.Item label="文件 Key" name="platformFileKey">
          <Input placeholder="请输入文件 Key" />
        </Form.Item>
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
        <Form.Item label="文件类型" name="fileType">
          <Select
            placeholder="请选择文件类型"
            options={(options.fileTypeOptions || []).map((d) => ({
              value: d.platformFileType,
              label: d.platformFileTypeName
            }))}
          />
        </Form.Item>
        <Form.Item label="关联应用" name="appId">
          <Input placeholder="请输入关联应用" />
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

export default memo(FilterDrawer);

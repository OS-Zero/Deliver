import { Button, Card, FormInstance, DatePicker, Form, Select, Input } from 'antd';
import { CloseOutlined } from '@ant-design/icons';
import { memo, useRef } from 'react';
import local from 'antd/lib/date-picker/locale/zh_CN.js';
import { useFormOptions } from '@/hooks/useFormOptions';
import { useDebounce } from '@/hooks/useDebounce';
// import { handleValueChange } from '@/utils/handleFieldValue';

interface FilterDrawerProps {
  onFilter: (filters: any) => void;
  onClose: (open: boolean) => void;
}

const FilterDrawer = (props: FilterDrawerProps) => {
  const { onClose, onFilter } = props;
  const formRef = useRef<FormInstance>(null);

  const { options, handleChannelTypeChange, handleChannelProviderTypeChange } = useFormOptions({
    myRef: formRef,
    key: 'file'
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
      forceRender
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
          <Input placeholder="请输入文件 Key" allowClear />
        </Form.Item>
        <Form.Item label="渠道类型" name="channelType">
          <Select
            allowClear
            placeholder="请选择渠道类型"
            onChange={async (value: number) => {
              console.log(1);
              await formRef?.current?.resetFields([
                'channelProviderType',
                'platformFileType',
                'appId'
              ]);
              await handleChannelTypeChange(value);
              console.log(2);
            }}
            options={(options.channelTypeOptions || []).map((d) => ({
              value: d.channelType,
              label: d.channelTypeName
            }))}
          />
        </Form.Item>
        <Form.Item label="渠道供应商类型" name="channelProviderType">
          <Select
            allowClear
            placeholder="请选择渠道供应商类型"
            options={(options.channelProvidersOptions || []).map((d) => ({
              value: d.channelProviderType,
              label: d.channelProviderTypeName
            }))}
            onChange={(value: number) => {
              formRef?.current?.resetFields(['platformFileType', 'appId']);
              handleChannelProviderTypeChange(value);
            }}
          />
        </Form.Item>
        <Form.Item label="文件类型" name="platformFileType">
          <Select
            allowClear
            placeholder="请选择文件类型"
            options={(options.fileTypeOptions || []).map((d) => ({
              value: d.platformFileType,
              label: d.platformFileTypeName
            }))}
          />
        </Form.Item>
        <Form.Item label="关联应用" name="appId">
          <Select
            allowClear
            placeholder="请输入关联应用"
            options={(options.appOptions || []).map((d) => ({
              value: d.appId,
              label: d.appName
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

export default memo(FilterDrawer);

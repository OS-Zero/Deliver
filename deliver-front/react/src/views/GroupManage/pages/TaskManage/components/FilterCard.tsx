import { Form, Select, Button, Card, FormInstance, DatePicker, Input } from 'antd';
import { CloseOutlined } from '@ant-design/icons';
import { useRef } from 'react';
import local from 'antd/lib/date-picker/locale/zh_CN.js';
import { useDebounce } from '@/hooks/useDebounce';

interface FilterDrawerProps {
  onFilter: (filters: any) => void;
  onClose: (open: boolean) => void;
}

const { Option } = Select;

const FilterDrawer = (props: FilterDrawerProps) => {
  const { onClose, onFilter } = props;
  const formRef = useRef<FormInstance>(null);

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
        <Form.Item label="任务名" name="taskName">
          <Input placeholder="请输入任务名" />
        </Form.Item>
        <Form.Item name="templateStatus" label="任务类型">
          <Select placeholder="请选择任务类型">
            <Option value={1}>实时</Option>
            <Option value={2}>定时</Option>
          </Select>
        </Form.Item>
        <Form.Item name="taskStatus" label="任务状态">
          <Select placeholder="请选择任务状态">
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

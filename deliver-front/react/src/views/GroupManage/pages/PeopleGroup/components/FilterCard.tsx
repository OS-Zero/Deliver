import { Form, Select, Button, Card, FormInstance, DatePicker, Input } from 'antd';
import { CloseOutlined } from '@ant-design/icons';
import { useRef } from 'react';
import local from 'antd/lib/date-picker/locale/zh_CN.js';
import { useDebounce } from '@/hooks/useDebounce';
import { useGlobalContext } from '@/context/GlobalContext';
import { FilterDrawerProps } from '@/types';

const { Option } = Select;

const FilterDrawer = (props: FilterDrawerProps) => {
  const { onClose, onFilter } = props;
  const formRef = useRef<FormInstance>(null);

  const { userTypeParams } = useGlobalContext();
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
        <Form.Item label="人群名称" name="peopleGroupName">
          <Input placeholder="请输入人群名称" allowClear/>
        </Form.Item>
        <Form.Item label="用户类型" name="usersType">
          <Select placeholder="请选择用户类型" allowClear>
            {userTypeParams.map((item) => (
              <Option key={item.usersType} value={item.usersType}>
                {item.usersTypeName}
              </Option>
            ))}
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

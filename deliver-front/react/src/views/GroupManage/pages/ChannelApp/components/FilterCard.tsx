import { Form, Select, Button, Card, FormInstance, DatePicker } from 'antd';
// import { getChannelType, getParam } from '@/api/system';
import { CloseOutlined } from '@ant-design/icons';
import { useRef } from 'react';
import local from 'antd/lib/date-picker/locale/zh_CN.js';


const { Option } = Select;

interface FilterDrawerProps {
  onFilter: (filters: any) => void;
  onClose: (open: boolean) => void;
}

const FilterDrawer = (props: FilterDrawerProps) => {
  const { onClose, onFilter } = props;
  const formRef = useRef<FormInstance>(null);

  // 使用 useFormState 管理表单状态
  // const { formState, updateFormState } = useFormState();

  // // 渠道类型变更处理
  // const handleChannelTypeChange = async (value: number) => {
  //   formRef?.current?.resetFields(['channelProviderType', 'messageType']);
  //   updateFormState({ channelProviders: [], messageTypes: [] });
  //   try {
  //     const response = await getParam({ channelType: value });
  //     updateFormState({
  //       channelProviders: response.channelProviderTypeList,
  //       messageTypes: response.messageTypeList
  //     });
  //   } catch (error) {
  //     console.error('获取渠道供应商和消息类型失败:', error);
  //     message.error('获取渠道供应商和消息类型失败，请稍后重试');
  //   }
  // };

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
        <Form.Item label="渠道类型" name="channelType">
          <Select
            placeholder="请选择渠道类型"
            // onChange={handleChannelTypeChange}
            // disabled={formState.isChannelTypeDisabled}
          >
            <Option value={0}>选项1</Option>
            <Option value={1}>选项2</Option>
          </Select>
        </Form.Item>
        <Form.Item label="渠道供应商类型" name="channelProviderType">
          <Select placeholder="请选择渠道类型">
            <Option value={0}>选项1</Option>
            <Option value={1}>选项2</Option>
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
          <DatePicker showTime format="YYYY-MM-DD HH:mm:ss" placeholder="请选择结束时间" locale={local}/>
        </Form.Item>
      </Form>
    </Card>
  );
};

FilterDrawer.displayName = 'FilterDrawer';

export default FilterDrawer;

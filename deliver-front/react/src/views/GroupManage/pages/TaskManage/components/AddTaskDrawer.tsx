import { forwardRef, useImperativeHandle, useRef, useState } from 'react';
import { Button, Drawer, Space, FormInstance } from 'antd';
import { JsonEditor } from 'jsoneditor-react';
import { BetaSchemaForm, ProFormColumnsType } from '@ant-design/pro-components';
interface AddChannelDrawerProps {
  onSubmit?: (values: any) => void;
}

const AddChannelDrawer = forwardRef((props: AddChannelDrawerProps, ref) => {
  const { onSubmit } = props;
  const [open, setOpen] = useState(false);
  const formRef = useRef<FormInstance>(null);
  const [jsonEditorKey, setJsonEditorKey] = useState(0);

  const handleSubmit = async () => {
    try {
      const values = await formRef?.current?.validateFields();
      onSubmit?.(values);
      setOpen(false);
      formRef?.current?.resetFields();
    } catch (error) {
      console.error('保存失败:', error);
    }
  };

  const handleReset = () => {
    formRef?.current?.resetFields();
    formRef?.current?.setFieldsValue({ taskParam: {} }); // 重置时设置默认值为 {}
  };

  useImperativeHandle(ref, () => ({
    addTaskDrawer: () => {
      formRef?.current?.setFieldsValue({ taskParam: {} }); // 打开弹窗时设置默认值为 {}
      setOpen(true);
    },
    editTaskModal: async (values: any) => {
      setOpen(true);
      formRef?.current?.setFieldsValue({
        ...values,
        taskParam: JSON.parse(values.taskParam)
      });
      setJsonEditorKey(jsonEditorKey + 1);
    }
  }));

  const rule = (label: string) => {
    return {
      formItemProps: {
        rules: [
          {
            required: true,
            message: `${label}不可为空`
          }
        ]
      }
    };
  };

  const columns: ProFormColumnsType[] = [
    {
      title: '任务名',
      dataIndex: 'taskName',
      ...rule('任务名'),
      width: '100%'
    },
    {
      title: '任务描述',
      dataIndex: 'taskDescription',
      valueType: 'textarea',
      ...rule('任务描述'),
      width: '100%'
    },
    {
      title: '任务类型',
      dataIndex: 'taskType',
      valueType: 'select',
      ...rule('任务类型'),
      width: '100%',
      fieldProps: {
        options: [
          {
            value: 1,
            label: '实时'
          },
          {
            value: 2,
            label: '定时'
          }
        ]
      }
    },
    {
      title: '人群 ID',
      dataIndex: 'peopleGroupId',
      valueType: 'text',
      ...rule('人群 ID'),
      width: '100%'
    },
    {
      title: '发送参数',
      key: 'taskParam',
      dataIndex: 'taskParam',
      initialValue: {},
      renderFormItem: () => {
        return (
          <JsonEditor
            key={jsonEditorKey}
            mode="code"
            onChange={(e: object) => formRef.current?.setFieldsValue({ taskParam: e })}
          />
        );
      }
    }
  ];

  return (
    <Drawer
      title="新增任务"
      open={open}
      onClose={() => setOpen(false)}
      width={500}
      extra={
        <Space>
          <Button onClick={() => setOpen(false)}>取消</Button>
          <Button type="primary" onClick={handleSubmit}>
            确定
          </Button>
        </Space>
      }
    >
      <BetaSchemaForm
        width="100%"
        shouldUpdate={true}
        layout="vertical"
        wrapperCol={{ span: 24 }}
        submitter={false}
        formRef={formRef}
        onFinish={async (values) => {
          console.log(values);
        }}
        columns={columns}
      />
    </Drawer>
  );
});

AddChannelDrawer.displayName = 'AddChannelDrawer';

export default AddChannelDrawer;

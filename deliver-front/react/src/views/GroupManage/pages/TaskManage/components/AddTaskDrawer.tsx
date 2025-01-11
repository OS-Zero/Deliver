import { forwardRef, useImperativeHandle, useRef, useState } from 'react';
import { Button, Drawer, Space, FormInstance } from 'antd';
import { JsonEditor } from 'jsoneditor-react';
import { BetaSchemaForm, ProFormColumnsType } from '@ant-design/pro-components';
import { handleFieldValue } from '@/utils/handleFieldValue';
import { useFormOptions } from '@/hooks/useFormOptions';

interface AddChannelDrawerProps {
  onSubmit?: (values: any) => void;
}

const AddChannelDrawer = forwardRef((props: AddChannelDrawerProps, ref) => {
  const { onSubmit } = props;
  const [open, setOpen] = useState(false);
  const formRef = useRef<FormInstance>(null);
  const [jsonEditorKey, setJsonEditorKey] = useState(0);

  const { options, handleChannelTypeChange, handleChannelProviderTypeChange } = useFormOptions({
    myRef: formRef,
    setJsonEditorKey,
    key: 'channel',
  });

  const handleSubmit = async () => {
    try {
      const values = await formRef?.current?.validateFields();

      values.channelType = handleFieldValue(
        values.channelType,
        options.channelTypeOptions,
        'channelTypeName',
        'channelType'
      );

      // 处理 channelProviderType
      values.channelProviderType = handleFieldValue(
        values.channelProviderType,
        options.channelProvidersOptions,
        'channelProviderTypeName',
        'channelProviderType'
      );

      onSubmit?.(values);
      formRef?.current?.resetFields();
    } catch (error) {
      console.error('保存失败:', error);
    } finally {
      setOpen(false);
    }
  };

  const handleReset = () => {
    formRef?.current?.resetFields();
    formRef?.current?.setFieldsValue({ paramMap: {} }); // 重置时设置默认值为 {}
  };

  useImperativeHandle(ref, () => ({
    addTaskDrawer: () => {
      formRef?.current?.setFieldsValue({ paramMap: {} }); // 打开弹窗时设置默认值为 {}
      setOpen(true);
    },
    editTaskModal: async (values: any) => {
      setOpen(true);
      await handleChannelTypeChange(values.channelType);
      formRef?.current?.setFieldsValue({
        ...values,
        channelType: values.channelTypeName,
        channelProviderType: values.channelProviderTypeName
      });
    }
  }));

  const rule = {
    formItemProps: {
      rules: [
        {
          required: true,
          message: '应用描述不可为空'
        }
      ]
    }
  };

  const columns: ProFormColumnsType[] = [
    {
      title: '任务名',
      dataIndex: 'taskName',
      ...rule,
      width: '100%'
    },
    {
      title: '任务描述',
      dataIndex: 'taskDescription',
      valueType: 'textarea',
      ...rule,
      width: '100%'
    },
    {
      title: '任务类型',
      dataIndex: 'taskType',
      valueType: 'select',
      ...rule,
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
      },
    },
    {
      title: '人群 ID',
      dataIndex: 'peopleGroupId',
      valueType: 'select',
      ...rule,
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
      },
    },
    {
      title: '发送参数',
      key: 'paramMap',
      dataIndex: 'paramMap',
      initialValue: {},
      renderFormItem: () => {
        return (
          <JsonEditor
            key={jsonEditorKey}
            mode="code"
            onChange={(e: object) => formRef.current?.setFieldsValue({ paramMap: e })}
          />
        );
      }
    }
  ];

  return (
    <Drawer
      title="新增任务"
      open={open}
      onClose={() => {
        setOpen(false);
        handleReset();
      }}
      width={500}
      extra={
        <Space>
          <Button onClick={handleReset}>重置</Button>
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

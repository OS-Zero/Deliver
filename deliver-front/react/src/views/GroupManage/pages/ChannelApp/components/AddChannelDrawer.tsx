import { forwardRef, useImperativeHandle, useRef, useState } from 'react';
import { Button, Drawer, Space, FormInstance, message } from 'antd';
import { JsonEditor } from 'jsoneditor-react';
import { BetaSchemaForm, ProFormColumnsType } from '@ant-design/pro-components';
import { useFormOptions } from '@/hooks/useFormOptions';

interface AddChannelDrawerProps {
  onSubmit?: (values: any) => void;
  reFresh?: () => void;
}

const AddChannelDrawer = forwardRef((props: AddChannelDrawerProps, ref) => {
  const { onSubmit, reFresh } = props;
  const [open, setOpen] = useState(false);
  const formRef = useRef<FormInstance>(null);
  const [jsonEditorKey, setJsonEditorKey] = useState(0);

  const { options, handleChannelTypeChange, handleChannelProviderTypeChange } = useFormOptions({
    myRef: formRef,
    setJsonEditorKey,
    key: 'channel'
  });

  const handleSubmit = async () => {
    try {
      const values = await formRef?.current?.validateFields();
      values.appConfig = JSON.stringify(values.appConfig);
      await onSubmit?.(values);
      onClose();
      message.success('保存成功');
      reFresh?.();
    } catch (error) {
      console.error('保存失败:', error);
    }
  };

  const onClose = () => {
    setOpen(false);
    formRef?.current?.resetFields();
  };

  useImperativeHandle(ref, () => ({
    addChannelDrawer: () => {
      setOpen(true);
    },
    editChannelModal: async (values: any) => {
      setOpen(true);
      await handleChannelTypeChange(values.channelType);
      formRef?.current?.setFieldsValue({
        ...values,
        appConfig: JSON.parse(values.appConfig)
      });
      setJsonEditorKey((prev) => prev + 1);
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
      title: '应用ID',
      dataIndex: 'appId',
      valueType: 'text',
      formItemProps: {
        hidden: true
      }
    },
    {
      title: '应用名',
      dataIndex: 'appName',
      ...rule,
      width: '100%'
    },
    {
      title: '应用描述',
      dataIndex: 'appDescription',
      ...rule,
      width: '100%'
    },
    {
      title: '渠道类型',
      dataIndex: 'channelType',
      valueType: 'select',
      width: '100%',
      fieldProps: {
        options: options.channelTypeOptions.map((d) => ({
          value: d.channelType,
          label: d.channelTypeName
        })),
        onChange: handleChannelTypeChange
      },
      ...rule
    },
    {
      title: '渠道供应商类型',
      dataIndex: 'channelProviderType',
      valueType: 'select',
      width: '100%',
      fieldProps: {
        options: options.channelProvidersOptions.map((d) => ({
          value: d.channelProviderType,
          label: d.channelProviderTypeName
        })),
        onChange: handleChannelProviderTypeChange // 添加渠道供应商类型变更处理
      },
      ...rule
    },
    {
      title: '应用配置',
      key: 'appConfig',
      dataIndex: 'appConfig',
      initialValue: {},
      renderFormItem: () => {
        return <JsonEditor key={jsonEditorKey} mode="code" />;
      }
    }
  ];

  return (
    <Drawer
      title={formRef?.current?.getFieldValue('appId') ? '编辑应用' : '新增应用'}
      open={open}
      onClose={onClose}
      width={500}
      extra={
        <Space>
          <Button onClick={onClose}>取消</Button>
          <Button type="primary" onClick={handleSubmit}>
            确定
          </Button>
        </Space>
      }
    >
      <BetaSchemaForm
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

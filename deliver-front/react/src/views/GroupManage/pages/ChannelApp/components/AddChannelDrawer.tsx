import { forwardRef, useImperativeHandle, useRef, useState } from 'react';
import { Button, Drawer, Space, FormInstance, message } from 'antd';
import { JsonEditor } from 'jsoneditor-react';
import { BetaSchemaForm, ProFormColumnsType } from '@ant-design/pro-components';
import { handleFieldValue } from '@/utils/handleFieldValue';
import { useFormOptions } from '@/hooks/useFormOptions';

interface AddChannelDrawerProps {
  onSubmit?: (values: any) => void;
  onSearch?: (values: any) => void;
}

const AddChannelDrawer = forwardRef((props: AddChannelDrawerProps, ref) => {
  const { onSubmit, onSearch } = props;
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

      values.appConfig = JSON.stringify(values.appConfig);
      onSubmit?.(values);
      message.success('保存成功');
      formRef?.current?.resetFields();
      onSearch?.({ currentPage: 1, pageSize: 10 });
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
    addChannelDrawer: () => {
      formRef?.current?.setFieldsValue({ paramMap: {} }); // 打开弹窗时设置默认值为 {}
      setOpen(true);
    },
    editChannelModal: async (values: any) => {
      setOpen(true);
      await handleChannelTypeChange(values.channelType);
      formRef?.current?.setFieldsValue({
        ...values,
        channelType: values.channelTypeName,
        channelProviderType: values.channelProviderTypeName,
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
        disabled: !formRef?.current?.getFieldValue('channelType'),
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
        return (
          <JsonEditor
            key={jsonEditorKey}
            mode="code"
            onChange={(e: object) => formRef.current?.setFieldsValue({ appConfig: e })}
          />
        );
      }
    }
  ];

  return (
    <Drawer
      title={formRef?.current?.getFieldValue('appId') ? '编辑应用' : '新增应用'}
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

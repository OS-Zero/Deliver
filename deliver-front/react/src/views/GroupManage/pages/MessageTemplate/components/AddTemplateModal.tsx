import { forwardRef, useImperativeHandle, useRef, useState } from 'react';
import { Button, Drawer, Space, message, FormInstance } from 'antd';
import { useGlobalContext } from '@/context/GlobalContext';
import { BetaSchemaForm, ProFormColumnsType } from '@ant-design/pro-components';
import { useFormOptions } from '@/hooks/useFormOptions';

interface AddTemplateModalProps {
  onSubmit?: (values: any) => void;
  reFresh?: () => void;
}

const AddTemplateModal = forwardRef((props: AddTemplateModalProps, ref) => {
  const { onSubmit, reFresh } = props;
  const [open, setOpen] = useState(false);
  const formRef = useRef<FormInstance>(null);
  const { userTypeParams } = useGlobalContext();

  const {
    options,
    handleUsersTypeChange,
    handleChannelTypeChange,
    handleChannelProviderTypeChange
  } = useFormOptions({
    myRef: formRef,
    key: 'template'
  });

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
      title: '模板 ID',
      dataIndex: 'templateId',
      valueType: 'text',
      formItemProps: {
        hidden: true
      }
    },
    {
      title: '模板名',
      dataIndex: 'templateName',
      ...rule,
      width: '100%'
    },
    {
      title: '模板描述',
      dataIndex: 'templateDescription',
      ...rule,
      width: '100%'
    },
    {
      title: '用户类型',
      dataIndex: 'usersType',
      valueType: 'select',
      width: '100%',
      fieldProps: {
        options: userTypeParams.map((d) => ({
          value: d.usersType,
          label: d.usersTypeName
        })),
        onChange: (value: number) => {
          formRef?.current?.resetFields([
            'channelType',
            'channelProviderType',
            'messageType',
            'appId'
          ]);
          handleUsersTypeChange(value);
        }
      },
      ...rule
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
        onChange: (value: number) => {
          formRef?.current?.resetFields(['channelProviderType', 'messageType', 'appId']);
          handleChannelTypeChange(value);
        }
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
        onChange: (value: number) => {
          formRef?.current?.resetFields(['messageType', 'appId']);
          handleChannelProviderTypeChange(value);
        }
      },
      ...rule
    },
    {
      title: '消息类型',
      dataIndex: 'messageType',
      valueType: 'select',
      width: '100%',
      fieldProps: {
        options: options.messageTypeOptions.map((d) => ({
          value: d.messageType,
          label: d.messageTypeName
        }))
      },
      ...rule
    },
    {
      title: '关联应用',
      dataIndex: 'appId',
      valueType: 'select',
      width: '100%',
      fieldProps: {
        options: options.appOptions.map((d) => ({
          value: d.appId,
          label: d.appName
        }))
      },
      ...rule
    }
  ];

  const handleSubmit = async () => {
    try {
      const values = await formRef?.current?.validateFields();
      await onSubmit?.(values);
      onClose();
      message.success('保存成功');
      reFresh?.();
    } catch (error) {
      console.error('保存失败:', error);
    } finally {
      setOpen(false);
    }
  };

  const onClose = () => {
    setOpen(false);
    formRef?.current?.resetFields();
  };

  useImperativeHandle(ref, () => ({
    addTemplateModal: () => {
      setOpen(true);
    },
    editTemplateModal: async (values: any) => {
      setOpen(true);
      await handleChannelTypeChange(values.channelType);
      await handleChannelProviderTypeChange(values.channelProviderType, {
        channelType: values.channelType,
        channelProviderType: values.channelProviderType
      });
      formRef?.current?.setFieldsValue({
        ...values
      });
    }
  }));

  return (
    <Drawer
      title={formRef?.current?.getFieldValue('templateId') ? '编辑模板' : '新增模板'}
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
        columns={columns}
      />
    </Drawer>
  );
});

AddTemplateModal.displayName = 'AddTemplateModal';

export default AddTemplateModal;

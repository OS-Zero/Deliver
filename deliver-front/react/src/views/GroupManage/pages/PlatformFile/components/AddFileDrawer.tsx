import { forwardRef, useImperativeHandle, useRef, useState } from 'react';
import { Button, Drawer, Space, FormInstance, Upload } from 'antd';
import { BetaSchemaForm, ProFormColumnsType } from '@ant-design/pro-components';
import { useFormOptions } from '@/hooks/useFormOptions';
import { InboxOutlined } from '@ant-design/icons';

const { Dragger } = Upload;

interface AddFileDrawerProps {
  onSubmit?: (values: any) => void;
}

const AddFileDrawer = forwardRef((props: AddFileDrawerProps, ref) => {
  const { onSubmit } = props;
  const [open, setOpen] = useState(false);
  const formRef = useRef<FormInstance>(null);

  const { options, handleChannelTypeChange, handleChannelProviderTypeChange } = useFormOptions({
    myRef: formRef,
    key: 'file'
  });

  const handleSubmit = async () => {
    try {
      const values = await formRef?.current?.validateFields();
      console.log(values);
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
  };

  useImperativeHandle(ref, () => ({
    addFileDrawer: () => {
      setOpen(true);
    }
  }));

  const rule = (title: string) => {
    return {
      formItemProps: {
        rules: [
          {
            required: true,
            message: `${title}不可为空`
          }
        ]
      }
    };
  };

  const columns: ProFormColumnsType[] = [
    {
      title: '文件',
      dataIndex: 'platformFile',
      ...rule('请输入文件名'),
      width: 'm',
      renderFormItem: () => (
        <Dragger name="file" maxCount={1}>
          <p className="ant-upload-drag-icon">
            <InboxOutlined />
          </p>
          <p className="ant-upload-text">点击或拖拽上传</p>
        </Dragger>
      )
    },
    {
      title: '文件名',
      dataIndex: 'fileName',
      ...rule('文件名'),
      width: '100%'
    },
    {
      title: '文件描述',
      dataIndex: 'fileDescription',
      ...rule('文件描述'),
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
      ...rule('请选择渠道类型')
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
        onChange: handleChannelProviderTypeChange
      },
      ...rule('请选择渠道供应商类型')
    },
    {
      title: '文件类型',
      dataIndex: 'platformFileType',
      valueType: 'select',
      width: '100%',
      fieldProps: {
        options: options.fileTypeOptions.map((d) => ({
          value: d.platformFileType,
          label: d.platformFileTypeName
        })),
        disabled: !formRef?.current?.getFieldValue('channelType')
      },
      ...rule('文件类型')
    },
    {
      title: '关联应用',
      dataIndex: 'appId',
      ...rule('关联应用'),
      width: '100%'
    }
  ];

  return (
    <Drawer
      title="上传文件"
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
        shouldUpdate={true}
        layout="vertical"
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

AddFileDrawer.displayName = 'AddFileDrawer';

export default AddFileDrawer;

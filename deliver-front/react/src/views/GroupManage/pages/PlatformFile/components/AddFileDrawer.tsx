import { forwardRef, useImperativeHandle, useRef, useState } from 'react';
import { Button, Drawer, Space, FormInstance, Upload, message } from 'antd';
import { BetaSchemaForm, ProFormColumnsType } from '@ant-design/pro-components';
import { useFormOptions } from '@/hooks/useFormOptions';
import { InboxOutlined } from '@ant-design/icons';

const { Dragger } = Upload;

interface AddFileDrawerProps {
  onSubmit?: (values: any) => void;
  reFresh?: () => void;
}

const AddFileDrawer = forwardRef((props: AddFileDrawerProps, ref) => {
  const { onSubmit, reFresh } = props;
  const [fileList, setFileList] = useState<any[]>([]);
  const [open, setOpen] = useState(false);
  const formRef = useRef<FormInstance>(null);

  const { options, handleChannelTypeChange, handleChannelProviderTypeChange } = useFormOptions({
    myRef: formRef,
    key: 'file'
  });

  const handleSubmit = async () => {
    try {
      const values = await formRef?.current?.validateFields();
      const formData = new FormData();

      if (fileList.length > 0) {
        formData.append('file', fileList[0]);
      }

      const fileObject: Record<string, any> = {};
      formData.forEach((value, key) => {
        fileObject[key] = value;
      });

      const submitData = {
        ...values,
        platformFile: fileObject['file']
      };

      await onSubmit?.(submitData);
      formRef?.current?.resetFields();
      setFileList([]);
      onClose();
      message.success('上传成功');
      reFresh?.();
    } catch (error) {
      console.error('上传失败:', error);
    }
  };

  useImperativeHandle(ref, () => ({
    addFileDrawer: () => {
      setOpen(true);
    }
  }));

  const columns: ProFormColumnsType[] = [
    {
      title: '文件',
      dataIndex: 'platformFile',
      width: 'm',
      renderFormItem: () => (
        <Dragger
          name="file"
          maxCount={1}
          fileList={fileList}
          beforeUpload={(file: File) => {
            setFileList([file]);
            return false;
          }}
          onRemove={() => {
            setFileList([]);
          }}
          onChange={(info: any) => {
            if (info.file.status === 'removed') {
              setFileList([]);
            } else {
              message.success(`${info.file.name} 文件已选择`);
            }
          }}
        >
          <p className="ant-upload-drag-icon">
            <InboxOutlined />
          </p>
          <p className="ant-upload-text">点击或拖拽上传</p>
        </Dragger>
      ),
      formItemProps: {
        rules: [
          {
            required: true,
            validator: () => {
              if (fileList.length === 0) {
                return Promise.reject('请上传文件');
              }
              return Promise.resolve();
            }
          }
        ]
      }
    },
    {
      title: '文件名',
      dataIndex: 'platformFileName',
      width: '100%',
      formItemProps: {
        rules: [
          {
            required: true,
            message: '请输入文件名'
          }
        ]
      }
    },
    {
      title: '文件描述',
      dataIndex: 'platformFileDescription',
      width: '100%',
      formItemProps: {
        rules: [
          {
            required: true,
            message: '请输入文件描述'
          }
        ]
      }
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
          formRef?.current?.resetFields(['channelProviderType', 'platformFileType', 'appId']);
          handleChannelTypeChange(value);
        }
      },
      formItemProps: {
        rules: [
          {
            required: true,
            message: '请选择渠道类型'
          }
        ]
      }
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
          formRef?.current?.resetFields(['platformFileType', 'appId']);
          handleChannelProviderTypeChange(value);
        }
      },
      formItemProps: {
        rules: [
          {
            required: true,
            message: '请选择渠道供应商类型'
          }
        ]
      }
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
        }))
      },
      formItemProps: {
        rules: [
          {
            required: true,
            message: '请选择文件类型'
          }
        ]
      }
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
      formItemProps: {
        rules: [
          {
            required: true,
            message: '请选择关联应用'
          }
        ]
      }
    }
  ];

  const onClose = () => {
    setOpen(false);
    formRef?.current?.resetFields();
  };

  return (
    <Drawer
      title="上传文件"
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

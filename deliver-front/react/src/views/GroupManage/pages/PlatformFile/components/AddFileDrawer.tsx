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

      const fileObject = {};
      formData.forEach((value, key) => (fileObject[key] = value));

      const submitData = {
        ...values,
        ...fileObject
      };

      // ['channelType', 'channelProviderType', 'appId'].forEach((key) => {
      //   if (key in submitData) {
      //     submitData[key] = Number(submitData[key]);
      //   }
      // });
      console.log('Submit data:', submitData);
      onSubmit?.(submitData);
      formRef?.current?.resetFields();
      setFileList([]);
      setOpen(false);
    } catch (error) {
      console.error('保存失败:', error);
    }
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
      width: 'm',
      renderFormItem: () => (
        <Dragger
          name="file"
          maxCount={1}
          fileList={fileList}
          beforeUpload={(file) => {
            setFileList([file]);
            return false;
          }}
          onRemove={() => {
            setFileList([]);
          }}
          onChange={(info) => {
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
            validator: (_, value) => {
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
        }))
      },
      ...rule('文件类型')
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
      ...rule('关联应用')
    }
  ];

  return (
    <Drawer
      title="上传文件"
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

import { forwardRef, useImperativeHandle, useRef, useState, useEffect } from 'react';
import { Button, Drawer, Space, FormInstance } from 'antd';
import { getChannelType, getParam, getAppConfig } from '@/api/system';
import { JsonEditor } from 'jsoneditor-react';
import { BetaSchemaForm, ProFormColumnsType } from '@ant-design/pro-components';
import { Channel, ChannelProvider } from '../type';

interface AddChannelDrawerProps {
  onSubmit?: (values: any) => void;
}

const AddChannelDrawer = forwardRef((props: AddChannelDrawerProps, ref) => {
  const { onSubmit } = props;
  const [open, setOpen] = useState(false);
  const formRef = useRef<FormInstance>(null);

  const [options, setOptions] = useState<{
    channelTypeOptions: Channel[];
    channelProvidersOptions: ChannelProvider[];
  }>({
    channelTypeOptions: [],
    channelProvidersOptions: []
  });

  // 获取渠道类型数据
  useEffect(() => {
    if (!options.channelTypeOptions.length) {
      getChannelType({ usersType: -1 }).then((res: Channel[]) => {
        setOptions((prev) => ({
          ...prev,
          channelTypeOptions: res
        }));
      });
    }
  }, []);

  // 渠道类型变更处理
  const handleChannelTypeChange = async (value: number) => {
    formRef?.current?.resetFields(['channelProviderType', 'paramMap']);
    try {
      const response = await getParam({ channelType: value });
      setOptions((prev) => ({
        ...prev,
        channelProvidersOptions: response?.channelProviderTypeList || []
      }));
    } catch (error) {
      console.error('获取渠道供应商失败:', error);
    }
  };

  // 渠道供应商类型变更处理
  const handleChannelProviderTypeChange = async (value: number) => {
    const channelType = formRef?.current?.getFieldValue('channelType');
    if (channelType && value) {
      try {
        const response = await getAppConfig({ channelType, channelProviderType: value });
        console.log(response);
        formRef?.current?.setFieldsValue({ paramMap: response });
      } catch (error) {
        console.error('获取应用配置失败:', error);
      }
    }
  };

  const handleSubmit = () => {
    console.log(formRef?.current?.getFieldsValue());
    formRef?.current
      ?.validateFields()
      .then((values) => {
        console.log(values);
        onSubmit?.(values);
        formRef?.current?.resetFields();
        setOpen(false);
      })
      .catch((info) => {
        console.log('Validate Failed:', info);
      });
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
    editChannelModal: (values: any) => {
      setOpen(true);
      handleChannelTypeChange(values.channelType);
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
      title: '应用名',
      dataIndex: 'appName',
      ...rule,
      width: 'm'
    },
    {
      title: '应用描述',
      dataIndex: 'appDescription',
      ...rule,
      width: 'm'
    },
    {
      title: '渠道类型',
      dataIndex: 'channelType',
      valueType: 'select',
      width: 'm',
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
      width: 'm',
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
      title: '发送参数',
      key: 'paramMap',
      dataIndex: 'paramMap',
      initialValue: {},
      renderFormItem: () => {
        return (
          <JsonEditor
            // ref={jsonEditorRef}
            mode="code"
            onChange={(e: object) => formRef.current?.setFieldsValue({ paramMap: e })}
          />
        );
      }
    }
  ];

  return (
    <Drawer
      title="新增模板"
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
        layout="horizontal"
        labelCol={{ span: 7 }}
        wrapperCol={{ span: 16 }}
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

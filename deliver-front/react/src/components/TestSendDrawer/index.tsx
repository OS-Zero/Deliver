import { forwardRef, useEffect, useImperativeHandle, useRef, useState } from 'react';
import { Button, Drawer, Form, FormInstance, Input, Space } from 'antd';
import { DeleteOutlined, PlusOutlined } from '@ant-design/icons';
import { JsonEditor } from 'jsoneditor-react';
import styles from './index.module.scss';
import { getAppConfig } from '@/api/system';

const TestSendDrawer = forwardRef((_, ref) => {
  const [open, setOpen] = useState(false);
  const formRef = useRef<FormInstance>(null);
  const jsonEditorRef = useRef<any>(null);
  const [paramMap, setParamMap] = useState({});
  const [jsonEditorKey, setJsonEditorKey] = useState(0);

  const showDrawer = () => {
    setOpen(true);
  };

  const onClose = () => {
    setOpen(false);
  };

  // 渠道供应商类型变更处理
  const handleChannelProviderTypeChange = async (values: any) => {
    const { channelType, channelProviderType } = values;
    if (channelType && channelProviderType) {
      try {
        const response = await getAppConfig({ channelType, channelProviderType });
        setParamMap(JSON.parse(response));
        setJsonEditorKey((prev) => prev + 1); // 渲染视图，处理变更
      } catch (error) {
        console.error('获取应用配置失败:', error);
      }
    }
  };

  useImperativeHandle(ref, () => ({
    getTestSendDrawer: async (values: any) => {
      showDrawer();
      await handleChannelProviderTypeChange(values);
    }
  }));

  const onFinish = (values: any) => {
    console.log('Received values of form:', values);
    console.log('Received values of form:', paramMap);
  };

  useEffect(() => {
    formRef?.current?.resetFields();
    setParamMap({});
    setJsonEditorKey((prev) => prev + 1);
  }, [open]);

  return (
    <Drawer
      title="测试发送"
      placement="right"
      closable
      onClose={onClose}
      open={open}
      width={500}
      bodyStyle={{ padding: '24px' }}
      extra={
        <Space>
          <Button onClick={onClose}>取消</Button>
          <Button type="primary" onClick={() => formRef?.current?.submit()}>
            确认
          </Button>
        </Space>
      }
    >
      <Form ref={formRef} name="dynamic_user_ids" onFinish={onFinish}>
        <Form.List
          name="userIds"
          rules={[
            {
              validator: async (_, userIds) => {
                if (!userIds || userIds.length < 1) {
                  return Promise.reject(new Error('至少需要一个用户ID'));
                }
              }
            }
          ]}
        >
          {(fields, { add, remove }, { errors }) => (
            <>
              <Form.Item
                label="用户ID列表"
                labelCol={{ span: 24 }}
                wrapperCol={{ span: 24 }}
                style={{ marginBottom: 0 }}
                required
              >
                {fields.map((field) => (
                  <div key={field.key} style={{ display: 'flex' }}>
                    <Form.Item
                      {...field}
                      validateTrigger={['onChange', 'onBlur']}
                      rules={[
                        {
                          required: true,
                          whitespace: true,
                          message: '请输入用户ID或删除此字段'
                        }
                      ]}
                      noStyle
                    >
                      <Input placeholder="请输入用户ID" className={styles['user-input']} />
                    </Form.Item>
                    <Button
                      icon={<DeleteOutlined />}
                      danger
                      type="text"
                      shape="circle"
                      onClick={() => remove(field.name)}
                    />
                  </div>
                ))}
              </Form.Item>

              <Form.Item>
                <Button
                  type="dashed"
                  onClick={() => add()}
                  style={{ width: '100%', marginBottom: 'var(--spacing-md)' }}
                  icon={<PlusOutlined />}
                  disabled={fields.length >= 2}
                />
                <Form.ErrorList errors={errors} />
              </Form.Item>
            </>
          )}
        </Form.List>
        <Form.Item label="发送参数" labelCol={{ span: 24 }} wrapperCol={{ span: 24 }}>
          <JsonEditor
            key={jsonEditorKey}
            ref={jsonEditorRef}
            value={paramMap}
            mode="code"
            onChange={(e: object) => setParamMap(e)}
          />
        </Form.Item>
      </Form>
    </Drawer>
  );
});

TestSendDrawer.displayName = 'TestSendDrawer';

export default TestSendDrawer;

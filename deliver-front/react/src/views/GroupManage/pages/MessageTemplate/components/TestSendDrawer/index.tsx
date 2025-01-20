import { forwardRef, useEffect, useImperativeHandle, useRef, useState } from 'react';
import { Button, Drawer, Form, FormInstance, Input, Space } from 'antd';
import { DeleteOutlined, PlusOutlined } from '@ant-design/icons';
import { JsonEditor } from 'jsoneditor-react';
import styles from './index.module.scss';
import { getMessageParamByMessageType, testSendMessage } from '@/api/messageTemplate';
import { MessageTemplate } from '../../type';

const TestSendDrawer = forwardRef((_, ref) => {
  const [open, setOpen] = useState(false);
  const formRef = useRef<FormInstance>(null);
  const [jsonEditorKey, setJsonEditorKey] = useState(0);

  const showDrawer = () => {
    setOpen(true);
  };

  const onClose = () => {
    setOpen(false);
  };

  const handleParams = async (values: MessageTemplate) => {
    const { templateId } = values;
    if (templateId) {
      try {
        const response = await getMessageParamByMessageType({ templateId });
        formRef?.current?.setFieldsValue({
          messageParam: JSON.parse(response),
          templateId
        });
      } catch (error) {
        console.error('获取应用配置失败:', error);
      }
    }
  };

  useImperativeHandle(ref, () => ({
    getTestSendDrawer: async (values: MessageTemplate) => {
      showDrawer();
      await handleParams(values);
      setJsonEditorKey((prev) => prev + 1); // 渲染视图，处理变更
    }
  }));

  const onSubmit = async () => {
    try {
      await formRef?.current?.validateFields();
      await testSendMessage(formRef?.current?.getFieldsValue());
      onClose();
    } catch (error) {
      console.error('保存失败:', error);
    }
  };

  useEffect(() => {
    formRef?.current?.resetFields();
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
      forceRender
      bodyStyle={{ padding: '24px' }}
      extra={
        <Space>
          <Button onClick={onClose}>取消</Button>
          <Button type="primary" onClick={onSubmit}>
            确认
          </Button>
        </Space>
      }
    >
      <Form ref={formRef} name="dynamic_user_ids">
        <Form.List
          key="users"
          name="users"
          rules={[
            {
              validator: async (_, users) => {
                if (!users || users.length < 1) {
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
        <Form.Item
          name="messageParam"
          label="发送参数"
          labelCol={{ span: 24 }}
          wrapperCol={{ span: 24 }}
        >
          <JsonEditor
            key={jsonEditorKey}
            mode="code"
            // onChange={(e: object) => formRef.current?.setFieldsValue({ messageParam: e })}
          />
        </Form.Item>
        <Form.Item name="templateId" label="模版ID" hidden labelCol={{ span: 24 }}>
          <Input type="text" />
        </Form.Item>
      </Form>
    </Drawer>
  );
});

TestSendDrawer.displayName = 'TestSendDrawer';

export default TestSendDrawer;

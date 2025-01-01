import { forwardRef, useImperativeHandle, useState } from 'react';
import { Button, Drawer, Form, Input, Space } from 'antd';
import { DeleteOutlined, PlusOutlined } from '@ant-design/icons';
import { JsonEditor } from 'jsoneditor-react';
import styles from './index.module.scss';

const TestSendDrawer = forwardRef((_, ref) => {
  const [open, setOpen] = useState(false);
  const [form] = Form.useForm();
  const [paramMap, setParamMap] = useState({});

  const showDrawer = () => {
    setOpen(true);
  };

  const onClose = () => {
    setOpen(false);
  };

  useImperativeHandle(ref, () => ({
    getTestSendDrawer: () => {
      return showDrawer();
    }
  }));

  const onFinish = (values: any) => {
    console.log('Received values of form:', values);
  };

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

          <Button type="primary" onClick={() => form.submit()}>
            确认
          </Button>
        </Space>
      }
    >
      <Form form={form} name="dynamic_user_ids" onFinish={onFinish}>
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
          <JsonEditor value={paramMap} mode="code" onChange={(e: object) => setParamMap(e)} />
        </Form.Item>
      </Form>
    </Drawer>
  );
});

TestSendDrawer.displayName = 'TestSendDrawer';

export default TestSendDrawer;

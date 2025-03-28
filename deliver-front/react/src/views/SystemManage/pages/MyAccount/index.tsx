import React, { useRef, useState } from 'react';
import { Card, Button, Drawer, Form, Input, message } from 'antd';
import { updatePwd } from '@/api/user';
import { omitProperty } from '@/utils/omitProperty';
import { useVerify } from '@/hooks/useVerify';
import styles from './index.module.scss';
import { passwordValidationRule, verificationCodeValidationRule } from '@/views/Login/constant';
import { useGlobalContext } from '@/context/GlobalContext';

const MyAccount: React.FC = () => {
  const myAccountRef = useRef<any>(null);
  const [open, setOpen] = useState(false);
  const { userInfo } = useGlobalContext();

  const [editorData, setEditorData] = useState({
    userPassword: '',
    confirmPwd: '',
    verificationCode: '',
  });

  const { verifyContent, verifyDisabled, handleVerify } = useVerify({
    email: userInfo?.userEmail as string,
  });

  const validatePwd = (_: any, value: string) => {
    if (value !== editorData.userPassword || !value) {
      return Promise.reject('两次输入密码不相同');
    }
    return Promise.resolve();
  };

  const onSubmit = async () => {
    try {
      await myAccountRef.current.validateFields();
      await updatePwd(omitProperty(editorData, 'confirmPwd'));
      message.success('修改密码成功');
      setOpen(false);
    } catch (error) {
      console.log('error', error);
    }
  };

  return (
    <Card title="基础信息" className={styles['ant_card']}>
      <div className={styles['card_content']}>
        <p>用户邮箱: &nbsp;&nbsp;&nbsp;{userInfo?.userEmail}</p>
        <p>用户姓名: &nbsp;&nbsp;&nbsp;{userInfo?.userRealName}</p>
        <p>用户类型: &nbsp;&nbsp;&nbsp;{userInfo?.userRole}</p>
        <Button className={styles['btn']} onClick={() => setOpen(true)}>修改密码</Button>
      </div>
      <Drawer
        title="修改密码"
        open={open}
        placement="right"
        onClose={() => setOpen(false)}
        extra={
          <>
            <Button style={{ marginRight: 8 }} onClick={() => setOpen(false)}>
              取消
            </Button>
            <Button type="primary" onClick={onSubmit}>
              确认
            </Button>
          </>
        }
      >
        <Form
          ref={myAccountRef}
          initialValues={editorData}
          onValuesChange={(changedValues) => {
            setEditorData((prev) => ({ ...prev, ...changedValues }));
            if (editorData.confirmPwd && changedValues.userPassword) {
              myAccountRef.current.validateFields(['confirmPwd']);
            }
          }}
        >
          <Form.Item
            name="userPassword"
            rules={[passwordValidationRule]}
            validateTrigger={['onChange']}
          >
            <Input.Password placeholder="请输入密码" maxLength={16} />
          </Form.Item>
          <Form.Item
            name="confirmPwd"
            rules={[{ validator: validatePwd }]}
            validateTrigger={['onChange']}
          >
            <Input.Password placeholder="请确认密码" maxLength={16} />
          </Form.Item>
          <Form.Item
            name="verificationCode"
            rules={[verificationCodeValidationRule]}
            validateTrigger={['onChange']}
          >
            <div className={styles['verify']}>
              <Input placeholder="请输入6位验证码" maxLength={6} />
              <Button
                className={styles['verify_btn']}
                disabled={verifyDisabled}
                onClick={() => handleVerify()}
              >
                {verifyContent}
              </Button>
            </div>
          </Form.Item>
        </Form>
      </Drawer>
    </Card>
  );
};

export default MyAccount;

import { useState, useRef, useEffect } from 'react';
import { Form, Input, Button, message, Tooltip } from 'antd';
import { forgotPwd } from '@/api/user';
import { useVerify } from '@/hooks/useVerify';
import styles from './index.module.scss';
import {
  emailValidationRule,
  passwordValidationRule,
  verificationCodeValidationRule
} from '@/views/Login/constant';
import { omitProperty } from '@/utils/omitProperty';

interface ForgotInfo {
  userEmail: string;
  userPassword: string;
  confirmPwd: string;
  verificationCode: string;
}

const ForgotForm: React.FC<{ onOk: () => void }> = ({ onOk }) => {
  const [forgotData, setForgotData] = useState<ForgotInfo>({
    userEmail: '',
    userPassword: '',
    confirmPwd: '',
    verificationCode: ''
  });

  const formRef = useRef<any>(null);
  const { verifyDisabled, verifyContent, handleVerify, checkEmailFormat } = useVerify({
    email: forgotData.userEmail
  });

  const validatePwd = (_: any, value: string) => {
    if (value !== forgotData.userPassword) {
      return Promise.reject('两次输入密码不相同');
    }
    return Promise.resolve();
  };

  const handleBtnTips = () => {
    return verifyContent.includes('验证码') ? '请先填写正确的邮箱地址' : '';
  };

  const handleForgot = async () => {
    try {
      await formRef.current.validateFields();
      await forgotPwd(omitProperty(forgotData, 'confirmPwd'));
      message.success('修改密码成功');
      onOk();
    } catch (error) {
      console.log('error', error);
    }
  };

  useEffect(() => {
    const validateEmailFormat = async () => {
      if (forgotData.userEmail) {
        await checkEmailFormat();
      }
    };

    validateEmailFormat();
  }, [forgotData.userEmail, checkEmailFormat]);

  return (
    <Form ref={formRef} layout="vertical">
      <Form.Item name="userEmail" rules={[emailValidationRule]} validateTrigger={['onBlur']}>
        <Input
          value={forgotData.userEmail}
          onChange={(e: React.ChangeEvent<HTMLInputElement>) =>
            setForgotData({ ...forgotData, userEmail: e.target.value })
          }
          placeholder="请输入邮箱"
        />
      </Form.Item>
      <Form.Item name="userPassword" rules={[passwordValidationRule]} validateTrigger={['onBlur']}>
        <Input.Password
          maxLength={16}
          value={forgotData.userPassword}
          onChange={(e: React.ChangeEvent<HTMLInputElement>) => {
            setForgotData({ ...forgotData, userPassword: e.target.value })
            if (forgotData?.confirmPwd) {
              formRef.current.validateFields(['confirmPwd']);
            }
          }}
          placeholder="请输入密码"
        />
      </Form.Item>
      <Form.Item
        name="confirmPwd"
        rules={[{ validator: validatePwd }]}
        validateTrigger={['onBlur']}
      >
        <Input.Password
          maxLength={16}
          value={forgotData.confirmPwd}
          onChange={(e: React.ChangeEvent<HTMLInputElement>) =>
            setForgotData({ ...forgotData, confirmPwd: e.target.value })
          }
          placeholder="请确认密码"
        />
      </Form.Item>
      <Form.Item
        name="verificationCode"
        rules={[verificationCodeValidationRule]}
        validateTrigger={['onBlur']}
      >
        <div className={styles.verify}>
          <Input
            maxLength={6}
            value={forgotData.verificationCode}
            onChange={(e: React.ChangeEvent<HTMLInputElement>) =>
              setForgotData({ ...forgotData, verificationCode: e.target.value })
            }
            placeholder="请输入6位验证码"
          />
          <Tooltip title={verifyDisabled ? handleBtnTips() : ''}>
            <Button
              className={styles.verifyBtn}
              disabled={verifyDisabled}
              onClick={() => handleVerify()}
            >
              {verifyContent}
            </Button>
          </Tooltip>
        </div>
      </Form.Item>
      <Form.Item>
        <Button className={styles.submitBtn} type="primary" onClick={handleForgot}>
          确认
        </Button>
      </Form.Item>
    </Form>
  );
};

export default ForgotForm;

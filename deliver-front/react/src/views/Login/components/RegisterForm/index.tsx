import { useState, useRef, useEffect } from 'react';
import { Form, Input, Button, message, Tooltip } from 'antd';
import { register } from '@/api/user.ts';
import { useVerify } from '@/hooks/useVerify';
import styles from './index.module.scss';
import {
  emailValidationRule,
  passwordValidationRule,
  verificationCodeValidationRule
} from '@/views/Login/constant';
import { omitProperty } from '@/utils/omitProperty';

interface RegisterInfo {
  userEmail: string;
  userPassword: string;
  userRealName: string;
  confirmPwd: string;
  verificationCode: string;
}

interface RegisterFormProps {
  onOk: () => void;
}

const RegisterForm = (props: RegisterFormProps) => {
  const { onOk } = props;

  const [registerData, setRegisterData] = useState<RegisterInfo>({
    userEmail: '',
    userPassword: '',
    userRealName: '',
    confirmPwd: '',
    verificationCode: ''
  });

  const formRef = useRef<any>(null);

  const { verifyDisabled, verifyContent, handleVerify, checkEmailFormat } = useVerify({
    email: registerData.userEmail
  });

  const validatePwd = (_: any, value: string) => {
    if (value !== registerData.userPassword) {
      return Promise.reject('两次输入密码不相同');
    }
    return Promise.resolve();
  };

  const handleRegister = async () => {
    try {
      await formRef.current.validateFields();
      await register(omitProperty(registerData, 'confirmPwd'));
      message.success('注册成功');
      onOk();
    } catch (error) {
      console.log('error', error);
    }
  };

  const handleBtnTips = () => {
    return verifyContent.includes('验证码') ? '请先填写正确的邮箱地址' : '';
  };

  useEffect(() => {
    const validateEmailFormat = async () => {
      if (registerData.userEmail) {
        await checkEmailFormat();
      }
    };

    validateEmailFormat();
  }, [registerData.userEmail, checkEmailFormat]);

  return (
    <Form ref={formRef} layout="vertical">
      <Form.Item name="userEmail" rules={[emailValidationRule]} validateTrigger={['onBlur']}>
        <Input
          value={registerData.userEmail}
          onChange={(e: React.ChangeEvent<HTMLInputElement>) =>
            setRegisterData({ ...registerData, userEmail: e.target.value })
          }
          placeholder="请输入邮箱"
        />
      </Form.Item>
      <Form.Item name="userPassword" rules={[passwordValidationRule]} validateTrigger={['onBlur']}>
        <Input.Password
          maxLength={16}
          value={registerData.userPassword}
          onChange={(e: React.ChangeEvent<HTMLInputElement>) => {
            setRegisterData({ ...registerData, userPassword: e.target.value });
            if (registerData?.confirmPwd) {
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
          value={registerData.confirmPwd}
          onChange={(e: React.ChangeEvent<HTMLInputElement>) =>
            setRegisterData({ ...registerData, confirmPwd: e.target.value })
          }
          placeholder="请确认密码"
        />
      </Form.Item>
      <Form.Item
        name="userRealName"
        rules={[{ required: true, message: '请输入真实姓名' }]}
        validateTrigger={['onBlur']}
      >
        <Input
          value={registerData.userRealName}
          onChange={(e: React.ChangeEvent<HTMLInputElement>) =>
            setRegisterData({ ...registerData, userRealName: e.target.value })
          }
          placeholder="请输入真实姓名"
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
            value={registerData.verificationCode}
            onChange={(e: React.ChangeEvent<HTMLInputElement>) =>
              setRegisterData({
                ...registerData,
                verificationCode: e.target.value
              })
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
        <Button className={styles.submitBtn} type="primary" onClick={handleRegister}>
          注册
        </Button>
      </Form.Item>
    </Form>
  );
};

export default RegisterForm;

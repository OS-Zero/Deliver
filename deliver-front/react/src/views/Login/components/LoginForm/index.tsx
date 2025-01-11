import { useState, useRef } from 'react';
import { Form, Input, Button, message } from 'antd';
import { login, getCurrentLoginUserInfo } from '@/api/user';
import { useNavigate } from 'react-router-dom';
import { emailValidationRule, passwordValidationRule } from '@/views/Login/constant';

interface UserInfo {
  userEmail: string;
  userPassword: string;
}

const LoginForm: React.FC = () => {
  const [loginData, setLoginData] = useState<UserInfo>({
    userEmail: '',
    userPassword: ''
  });

  const formRef = useRef<any>(null);
  const navigate = useNavigate();

  const handleLogin = async () => {
    try {
      await formRef?.current?.validateFields();
      const res = await login(loginData);
      if (res) {
        localStorage.setItem('access_token', res);
        message.success('登录成功');
        const _res = await getCurrentLoginUserInfo();
        localStorage.setItem('userInfo', JSON.stringify(_res));
        navigate('/', { replace: true });
      }
    } catch (error) {
      console.log('error', error);
    }
  };

  return (
    <Form
      ref={formRef}
      layout="vertical"
      onValuesChange={(changedValues) => {
        setLoginData((prev) => ({ ...prev, ...changedValues }));
      }}
    >
      <Form.Item name="userEmail" rules={[emailValidationRule]} validateTrigger={['onBlur']}>
        <Input value={loginData.userEmail} placeholder="请输入邮箱" />
      </Form.Item>
      <Form.Item name="userPassword" rules={[passwordValidationRule]} validateTrigger={['onBlur']}>
        <Input.Password maxLength={16} value={loginData.userPassword} placeholder="请输入密码" />
      </Form.Item>
      <Form.Item>
        <Button style={{ width: '100%' }} type="primary" onClick={handleLogin}>
          登录
        </Button>
      </Form.Item>
    </Form>
  );
};

export default LoginForm;

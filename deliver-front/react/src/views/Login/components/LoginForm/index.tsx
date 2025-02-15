import { useState, useRef } from 'react';
import { Form, Input, Button, message, Modal } from 'antd';
import { login, getCurrentLoginUserInfo } from '@/api/user';
import { useNavigate } from 'react-router-dom';
import { emailValidationRule, passwordValidationRule } from '@/views/Login/constant';
import { useGlobalContext } from '@/context/GlobalContext';

interface UserInfo {
  userEmail: string;
  userPassword: string;
}

const LoginForm: React.FC = () => {
  const isTest = import.meta.env.MODE === 'test';
  const navigate = useNavigate();
  const formRef = useRef<any>(null);

  const { setUserInfo } = useGlobalContext();

  const [code, setCode] = useState('');
  const [open, setOpen] = useState(false);
  const [loginData, setLoginData] = useState<UserInfo>({
    userEmail: isTest ? 'oszero@qq.com' : '',
    userPassword: isTest ? '666666' : ''
  });

  // demo环境下，校验验证码是否正确
  const handleLogin = async () => {
    try {
      await formRef?.current?.validateFields();
      if (isTest) {
        setOpen(true);
        return;
      }
      // 非测试环境或验证码正确，执行登录
      await performLogin();
    } catch (error) {
      console.log('error', error);
    }
  };

  const performLogin = async () => {
    const res = await login(loginData);
    if (res) {
      localStorage.setItem('access_token', res);
      message.success('登录成功');
      const _res = await getCurrentLoginUserInfo();
      setUserInfo(_res);
      localStorage.setItem('userInfo', JSON.stringify(_res));
      navigate('/', { replace: true });
    }
  };

  const handleOk = async () => {
    if (code === 'oszero666') {
      setOpen(false);
      await performLogin();
      return true;
    } else {
      message.error('验证码错误');
    }
    return false;
  };

  return (
    <>
      <Form
        ref={formRef}
        layout="vertical"
        initialValues={loginData}
        onValuesChange={(changedValues) => {
          setLoginData((prev) => ({ ...prev, ...changedValues }));
        }}
        onFinish={handleLogin}
      >
        <Form.Item name="userEmail" rules={[emailValidationRule]} validateTrigger={['onChange']}>
          <Input value={loginData.userEmail} placeholder="请输入邮箱" onPressEnter={handleLogin} />
        </Form.Item>
        <Form.Item
          name="userPassword"
          rules={[passwordValidationRule]}
          validateTrigger={['onChange']}
        >
          <Input.Password
            maxLength={16}
            value={loginData.userPassword}
            placeholder="请输入密码"
            onPressEnter={handleLogin}
          />
        </Form.Item>
        <Form.Item>
          <Button style={{ width: '100%' }} type="primary" onClick={handleLogin}>
            登录
          </Button>
        </Form.Item>
      </Form>
      <Modal
        open={open && isTest}
        onCancel={() => setOpen(false)}
        onOk={handleOk}
        title="人机验证"
        okText="确认"
        cancelText="取消"
        centered
        width={600}
      >
        <p>扫描下方二维码，关注后回复：deliver，获取Deliver企业消息推送平台人机验证码</p>
        <img style={{ width: '100%', margin: '12px 0' }} src={'/qrcode.jpg'} alt="QR Code" />
        <Input value={code} onChange={(e) => setCode(e.target.value)} placeholder="请输入验证码" />
      </Modal>
    </>
  );
};

export default LoginForm;

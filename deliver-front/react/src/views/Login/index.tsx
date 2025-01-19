import { useState } from 'react';
import { Tabs, Checkbox, Button } from 'antd';
import { LeftOutlined } from '@ant-design/icons';
import LoginForm from './components/LoginForm';
import RegisterForm from './components/RegisterForm';
import ForgotForm from './components/ForgotForm';
import styles from './index.module.scss';

const Login: React.FC = () => {
  const [activeKey, setActiveKey] = useState('login');
  const [autoLogin, setAutoLogin] = useState(false);
  const [showForgot, setShowForgot] = useState(false);

  const registerOk = () => setActiveKey('login');
  const forgotOk = () => {
    setShowForgot(false);
    setActiveKey('login');
  }

  return (
    <div className={styles.login}>
      <div className={`${styles.loginCard} ${showForgot ? styles.forgot : ''}`}>
        {!showForgot ? (
          <>
            <header>
              <div className={styles.headerLogo}>
                <img className={styles.logo} src="../../../logo.png" alt="Logo" />
                <span>Deliver</span>
              </div>
              <div className={styles.headerDesc}>开源的企业消息推送平台</div>
            </header>
            <section>
              <Tabs
                activeKey={activeKey}
                onChange={setActiveKey}
                centered
                items={[
                  {
                    label: '邮箱密码登录',
                    key: 'login',
                    children: <LoginForm />
                  },
                  {
                    label: '用户注册',
                    key: 'register',
                    children: <RegisterForm onOk={registerOk} />
                  }
                ]}
              />
              <div className={styles.loginAuto}>
                <Checkbox checked={autoLogin} onChange={(e) => setAutoLogin(e.target.checked)}>
                  自动登录
                </Checkbox>
                <Button type="link" onClick={() => setShowForgot(true)}>
                  忘记密码
                </Button>
              </div>
            </section>
          </>
        ) : (
          <div className={styles.forgot}>
            <Button type="text" className={styles.forgotBack} onClick={() => setShowForgot(false)}>
              <LeftOutlined /> <span>返回</span>
            </Button>
            <h1 className={styles.forgotTitle}>重置密码</h1>
            <ForgotForm onOk={forgotOk} />
          </div>
        )}
      </div>
    </div>
  );
};

export default Login;

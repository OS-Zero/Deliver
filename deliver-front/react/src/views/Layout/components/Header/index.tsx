import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import {
  ExclamationCircleOutlined,
  QuestionCircleOutlined,
  GithubOutlined,
  AppstoreOutlined,
  SettingOutlined,
  UserOutlined
} from '@ant-design/icons';
import { Dropdown, Avatar, Modal, Menu, MenuProps, Tooltip } from 'antd';
import { logout } from '@/api/user';
import styles from './index.module.scss';
import { usePathMatch } from '../../../../hooks/usePathMatch';

const Header: React.FC = () => {
  const navigate = useNavigate();
  const { current } = usePathMatch();
  const [showAbout, setShowAbout] = useState(false);

  const items = [
    {
      label: '分组管理',
      key: 'groupManage',
      icon: <AppstoreOutlined />
    },
    {
      label: '系统管理',
      key: 'systemManage',
      icon: <SettingOutlined />
    }
  ];

  const handleAction = async (key: string) => {
    switch (key) {
      case 'account':
        navigate('/systemManage');
        break;
      case 'logout':
        await logout();
        localStorage.removeItem('access_token');
        localStorage.removeItem('user_info');
        navigate('/login');
        break;
      default:
        break;
    }
  };

  const userItems = [
    {
      label: <a onClick={() => handleAction('account')}>我的账户</a>,
      key: 'account'
    },
    {
      label: <a onClick={() => handleAction('logout')}>退出登录</a>,
      key: 'logout'
    }
  ];

  const onClick: MenuProps['onClick'] = (e) => {
    navigate(`/${e.key}`);
  };

  return (
    <div className={styles['header']}>
      <div className={styles['headerContainer']}>
        <div className={styles['organization']}>
          <a href="/">
            <img className={styles['organizationImg']} src="/logo.png" alt="Deliver Logo" />
          </a>
          <a href="/">
            <h1>Deliver 企业消息推送平台</h1>
          </a>
          <div className={styles['header-tabs']}>
            <Menu onClick={onClick} mode="horizontal" selectedKeys={[current]} items={items} />
          </div>
        </div>
        <div className={styles['headerRight']}>
          <Modal
            title="关于"
            open={showAbout}
            centered
            onCancel={() => setShowAbout(false)}
            footer={null}
          >
            <div className={styles['modal-container']}>
              <div className={styles['container-info']}>
                <img className={styles['info_img']} src="/logo.png" alt="Logo" />
                <h1 className={styles['info_title']}>Deliver</h1>
              </div>
              <div style={{ marginLeft: '60px' }}>
                <p>产品：Deliver 企业消息推送平台</p>
                <p>版本：v1.0.0</p>
                <a target="_blank" href="https://os-zero.gitee.io/deliver-website" rel="noreferrer">
                  https://os-zero.gitee.io/deliver-website
                </a>
              </div>
            </div>
          </Modal>
          <Tooltip title="关于">
            <a onClick={() => setShowAbout(true)}>
              <ExclamationCircleOutlined />
            </a>
          </Tooltip>
          <Tooltip title="疑问">
            <a target="_blank" rel="noreferrer" href="https://os-zero.gitee.io/deliver-website">
              <QuestionCircleOutlined />
            </a>
          </Tooltip>
          <Tooltip title="Gitee">
            <a href="https://gitee.com/OS-Zero/deliver" rel="noreferrer" target="_blank">
              <GithubOutlined />
            </a>
          </Tooltip>
          <Dropdown menu={{ items: userItems }} placement="bottom">
            <div className={styles['avatar']}>
              <Avatar style={{ backgroundColor: '#87d068' }} icon={<UserOutlined />} />
              <span className={styles['name']}>Deliver</span>
            </div>
          </Dropdown>
        </div>
      </div>
    </div>
  );
};

export default Header;

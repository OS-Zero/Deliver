import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import {
  ExclamationCircleOutlined,
  QuestionCircleOutlined,
  GithubOutlined,
  AppstoreOutlined,
  SettingOutlined
} from '@ant-design/icons';
import { Dropdown, Avatar, Modal, Menu, MenuProps, Tooltip } from 'antd';
import { logout } from '@/api/user';
import style from './index.module.scss';

const Header: React.FC = () => {
  const navigate = useNavigate();
  const [showAbout, setShowAbout] = useState(false);
  const [current, setCurrent] = useState('');

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
        navigate('/systemSettings');
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

  const onClick: MenuProps['onClick'] = (e) => {
    setCurrent(e.key);
    navigate(`/${e.key}`, { replace: true });
  };

  return (
    <div className={style['header']}>
      <div className={style['headerContainer']}>
        <div className={style['organization']}>
          <a href="/">
            <img className={style['organizationImg']} src="/logo.png" alt="Deliver Logo" />
          </a>
          <a href="/">
            <h1>Deliver 企业消息推送平台</h1>
          </a>
          <div className={style['header-tabs']}>
            <Menu onClick={onClick} mode="horizontal" selectedKeys={[current]} items={items} />
          </div>
        </div>
        <div className={style['headerRight']}>
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
          <Dropdown menu={{ items }} placement="bottomRight">
            <Avatar>U</Avatar>
          </Dropdown>
        </div>
      </div>

      <Modal
        title="关于 Deliver"
        open={showAbout}
        onCancel={() => setShowAbout(false)}
        footer={null}
      >
        <p>Deliver 企业消息推送平台 - 致力于提供高效、可靠的消息通知服务</p>
        <p>版本：1.0.0</p>
        <p>
          <ExclamationCircleOutlined /> 项目正在积极开发中，欢迎提交 PR 和 Issue
        </p>
      </Modal>
    </div>
  );
};

export default Header;

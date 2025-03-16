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
import { useGlobalContext } from '@/context/GlobalContext';

const Header: React.FC = () => {
  const navigate = useNavigate();
  const { current } = usePathMatch();
  const [showAbout, setShowAbout] = useState(false);

  const { userInfo } = useGlobalContext();
  const hasGroupId = localStorage.getItem('group_id');
  const isGroupManage = window.location.pathname.includes('/groupManage');

  const items = [
    {
      icon: <AppstoreOutlined />,
      label:
        hasGroupId && isGroupManage ? (
          <Dropdown
            menu={{
              items: [
                {
                  key: 'exitGroup',
                  label: '退出分组',
                  onClick: () => {
                    localStorage.removeItem('group_id');
                    navigate('/groupManage', { replace: true });
                  }
                }
              ]
            }}
            placement="bottom"
            autoAdjustOverflow
            arrow
            dropdownRender={(menu) => (
              <div style={{ marginLeft: '-20px', marginTop: '-1px' }}>{menu}</div>
            )}
          >
            <span>分组管理</span>
          </Dropdown>
        ) : (
          '分组管理'
        ),
      key: 'groupManage'
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
        navigate('/systemManage', { replace: true });
        break;
      case 'logout':
        await logout();
        localStorage.clear();
        navigate('/login', { replace: true });
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
    switch (e.key) {
      case 'groupManage':
        if (!localStorage.getItem('group_id')) {
          navigate('/groupManage', { replace: true });
        } else {
          navigate('/groupManage/template', { replace: true });
        }
        break;
      case 'systemManage':
        if (window.location.pathname === '/systemManage/myAccount') {
          break;
        }
        navigate('/systemManage', { replace: true });
        break;
      default:
        break;
    }
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
                <p>版本：v1.0.2</p>
                <a target="_blank" href="https://oszero.cn" rel="noreferrer">
                  https://oszero.cn
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
            <a target="_blank" rel="noreferrer" href="https://oszero.cn">
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
              <span className={styles['name']}>{userInfo?.userRealName}</span>
            </div>
          </Dropdown>
        </div>
      </div>
    </div>
  );
};

export default Header;

import React, { useState } from 'react';
import { Layout, Menu, Button } from 'antd';
import { MenuUnfoldOutlined, MenuFoldOutlined } from '@ant-design/icons';
import { useNavigate } from 'react-router-dom';
import type { MenuProps } from 'antd';
import styles from './index.module.scss';
import { usePathMatch } from '../../../../hooks/usePathMatch';

interface SideBarProps {
  items: MenuProps['items'];
}

const SideBar: React.FC<SideBarProps> = ({ items }) => {
  const [collapsed, setCollapsed] = useState(false);
  const navigate = useNavigate();
  const { selectedKeys } = usePathMatch();

  const toggleCollapsed = () => {
    setCollapsed(!collapsed);
  };

  const handleMenuClick: MenuProps['onClick'] = (info) => {
    navigate(info.key);
  };

  return (
    <Layout.Sider collapsed={collapsed} theme="light" className={styles['siderContainer']}>
      <Menu
        mode="inline"
        items={items}
        selectedKeys={selectedKeys}
        onClick={handleMenuClick}
      />
      <footer className={styles['siderFooter']}>
        <Button type="text" className={styles['footerButton']} onClick={toggleCollapsed}>
          {collapsed ? <MenuUnfoldOutlined /> : <MenuFoldOutlined />}
        </Button>
      </footer>
    </Layout.Sider>
  );
};

export default SideBar;

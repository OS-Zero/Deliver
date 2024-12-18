import React, { useState, useEffect } from 'react';
import { Layout } from 'antd';
import { useLocation, useNavigate, Outlet } from 'react-router-dom';
import { GithubOutlined } from '@ant-design/icons';
import Banner from './components/Banner';
import Header from './components/Header';
import SideBar from './components/SideBar';
import Breadcrumb from './components/Breadcrumb';
import styles from './index.module.scss';

// 模拟 Vue 中的 menuConfig
const menuConfig = {
  groupManage: [
    { key: '/groupManage/file', label: '文件管理' },
    { key: '/groupManage/flowControlRule', label: '规则配置' }
  ],
  systemManage: [{ key: '/systemManage/myAccount', label: '我的账户' }]
};

const { Content, Footer } = Layout;

const AppLayout: React.FC = () => {
  const location = useLocation();
  const navigate = useNavigate();
  const [menuItems, setMenuItems] = useState<{ key: string; label: string }[]>([]);

  useEffect(() => {
    const groupId = localStorage.getItem('groupId');

    if (location.pathname.includes('groupManage')) {
      setMenuItems(menuConfig.groupManage);
    } else if (location.pathname.includes('systemManage')) {
      setMenuItems(menuConfig.systemManage);
    } else {
      setMenuItems([]);
    }
  }, [location.pathname]);

  return (
    <Layout>
      <Layout>
        <Banner />
        <Header />
        <Layout>
          {/* {menuItems.length > 0 && (
            <SideBar
              items={menuItems.map((item) => ({
                key: item.key,
                label: item.label
              }))}
            />
          )} */}
          <Layout className={styles['layout-section']}>
            <Breadcrumb />
            <Content>
              <Outlet />
            </Content>
            <Footer style={{ textAlign: 'center', background: 'var(--gray-lightest)' }}>
              <div className={styles['footer_organization']}>
                Deliver 企业消息推送平台{' '}
                <a className={styles['organization_link']} href="https://gitee.com/OS-Zero" target="_blank" rel="noreferrer">
                  <GithubOutlined />
                </a>
                {' '}OSZero 开源社区出品
              </div>
              <div>Copyright © 2023 OSZero. All rights reserved.</div>
            </Footer>
          </Layout>
        </Layout>
      </Layout>
    </Layout>
  );
};

export default AppLayout;

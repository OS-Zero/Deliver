import React, { useState, useEffect } from 'react';
import { Layout } from 'antd';
import { useLocation, Outlet } from 'react-router-dom';
import { GithubOutlined } from '@ant-design/icons';
import Banner from './components/Banner';
import Header from './components/Header';
import SideBar from './components/SideBar';
import Breadcrumb from './components/Breadcrumb';
import styles from './index.module.scss';
import { menuConfig } from './constant';

const { Content, Footer } = Layout;

const AppLayout: React.FC = () => {
  const location = useLocation();
  const [menuItems, setMenuItems] = useState<
    { key: string; label: string | React.ReactElement; icon?: React.ReactElement }[]
  >([]);

  useEffect(() => {
    const path = location.pathname;
    let menuItems: any[] = [];

    switch (true) {
      case path.includes('groupManage') && !path.endsWith('groupManage'):
        menuItems = menuConfig?.groupManage;
        break;
      case path.includes('systemManage') && !path.endsWith('systemManage'):
        menuItems = menuConfig?.systemManage;
        break;
      default:
        menuItems = [];
    }

    setMenuItems(menuItems);
  }, [location.pathname]);

  return (
    <div style={{ height: '100%' }}>
      <Layout>
        <Banner />
        <Layout>
          <Header />
          <Layout>
            {menuItems.length > 0 && <SideBar items={menuItems} />}
            <Layout className={styles['layout-section']}>
              <Content style={{ paddingBottom: '20px', overflow: 'auto' }}>
                <div className={styles['section_breadcrumb']}>
                  <Breadcrumb />
                </div>
                <Outlet />
              </Content>
              <Footer style={{ textAlign: 'center', background: 'var(--gray-lightest)' }}>
                <div className={styles['footer_organization']}>
                  Deliver 企业消息推送平台{' '}
                  <a
                    className={styles['organization_link']}
                    href="https://gitee.com/OS-Zero"
                    target="_blank"
                    rel="noreferrer"
                  >
                    <GithubOutlined />
                  </a>{' '}
                  OSZero 开源社区出品
                </div>
                <div>Copyright 2023 OSZero. All rights reserved.</div>
              </Footer>
            </Layout>
          </Layout>
        </Layout>
      </Layout>
    </div>
  );
};

export default AppLayout;

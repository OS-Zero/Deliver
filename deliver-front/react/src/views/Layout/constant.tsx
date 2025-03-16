import { ReactNode } from 'react';
import {
  MessageOutlined,
  FileTextOutlined,
  AppstoreOutlined,
  UserOutlined,
  SoundOutlined,
  DashboardOutlined
} from '@ant-design/icons';
import { Link } from 'react-router-dom';
import { useGlobalContext } from '@/context/GlobalContext';

interface RouteItem {
  key: string;
  icon: string;
  label?: string;
  children?: RouteItem[];
}

// 定义菜单项类型
interface MenuItem {
  key: string;
  icon: ReactNode | null;
  label: ReactNode | string;
  children?: MenuItem[];
}

// 图标映射表
const iconMap = {
  MessageOutlined: <MessageOutlined />,
  FileTextOutlined: <FileTextOutlined />,
  AppstoreOutlined: <AppstoreOutlined />,
  UserOutlined: <UserOutlined />,
  SoundOutlined: <SoundOutlined />,
  DashboardOutlined: <DashboardOutlined />
};

// 渲染菜单项
const renderMenuItem = (item: RouteItem): MenuItem => {
  const icon = item.icon ? iconMap[item.icon as keyof typeof iconMap] : null;

  if (item.children && item.children.length > 0) {
    return {
      key: item.key,
      icon: icon,
      label: item.label,
      children: item.children.map((child) => renderMenuItem(child))
    };
  }

  return {
    key: item.key,
    icon: icon,
    label: <Link to={item.key}>{item.label}</Link>
  };
};

// 获取菜单配置的钩子
export const useMenuConfig = () => {
  const { routers } = useGlobalContext();

  // 处理路由数据，转换为菜单配置格式
  const menuConfig = {
    groupManage: (routers?.groupManage || []).map((item) => renderMenuItem(item)),
    systemManage: (routers?.systemManage || []).map((item) => renderMenuItem(item))
  };

  return menuConfig;
};

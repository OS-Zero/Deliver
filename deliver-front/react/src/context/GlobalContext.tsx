import React, { createContext, useState, useContext, useEffect } from 'react';
import { getCurrentLoginUserInfo, startUp } from '@/api/user';
import { UserInfo } from '@/views/Login/type';
import { Users } from '@/types';

// 添加路由项类型定义
interface RouteItem {
  key: string;
  icon: string;
  label?: string;
  children?: RouteItem[];
}

interface RouterData {
  groupManage?: RouteItem[];
  systemManage?: RouteItem[];
}

// 定义上下文类型
interface GlobalContextType {
  userInfo: UserInfo | null;
  setUserInfo: React.Dispatch<React.SetStateAction<UserInfo | null>>;
  userTypeParams: Users[];
  setUserTypeParams: React.Dispatch<React.SetStateAction<Users[]>>;
  routers: RouterData | undefined;
  setRouters: React.Dispatch<React.SetStateAction<RouterData | undefined>>;
}

// 创建上下文
export const GlobalContext = createContext<GlobalContextType>({
  userInfo: null,
  setUserInfo: () => {},
  userTypeParams: [],
  setUserTypeParams: () => {},
  routers: {},
  setRouters: () => {}
});

// 提供者组件
export const GlobalProvider: React.FC<{ children: React.ReactNode }> = ({ children }) => {
  const [userInfo, setUserInfo] = useState<UserInfo | null>(null);
  const [userTypeParams, setUserTypeParams] = useState<Users[]>([]);
  const [routers, setRouters] = useState<RouterData>(); // 添加路由状态
  const accessToken = localStorage.getItem('access_token');

  // 初始化用户信息和用户类型参数
  useEffect(() => {
    const fetchInitialData = async () => {
      try {
        // 检查是否有 access_token
        if (accessToken) {
          // 并行获取用户信息和用户类型参数
          const [userInfoResponse, userTypeParamsResponse] = await Promise.all([
            getCurrentLoginUserInfo(),
            startUp()
          ]);

          setUserInfo(userInfoResponse);
          setUserTypeParams(userTypeParamsResponse?.usersTypeParamList || []);
          setRouters(userTypeParamsResponse?.currentLoginUserMenu || {}); // 设置动态路由数据
        }
      } catch (error) {
        console.error('初始化用户信息失败', error);
        localStorage.removeItem('access_token');
      }
    };

    fetchInitialData();
  }, [accessToken]);

  return (
    <GlobalContext.Provider
      value={{
        userInfo,
        setUserInfo,
        userTypeParams,
        setUserTypeParams,
        routers,
        setRouters
      }}
    >
      {children}
    </GlobalContext.Provider>
  );
};

// 自定义 Hook 简化上下文使用
export const useGlobalContext = () => {
  const context = useContext(GlobalContext);
  if (!context) {
    throw new Error('useGlobalContext 必须在 GlobalProvider 内部使用');
  }
  return context;
};

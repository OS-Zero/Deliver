import React, { createContext, useState, useContext, useEffect } from 'react';
import { getCurrentLoginUserInfo, startUp } from '@/api/user';
import { UserInfo } from '@/views/Login/type';
import { Users } from '@/types';

// 定义上下文类型
interface GlobalContextType {
  userInfo: UserInfo | null;
  setUserInfo: React.Dispatch<React.SetStateAction<UserInfo | null>>;
  userTypeParams: Users[];
  setUserTypeParams: React.Dispatch<React.SetStateAction<Users[]>>;
}

// 创建上下文
export const GlobalContext = createContext<GlobalContextType>({
  userInfo: null,
  setUserInfo: () => {},
  userTypeParams: [],
  setUserTypeParams: () => {}
});

// 提供者组件
export const GlobalProvider: React.FC<{ children: React.ReactNode }> = ({ children }) => {
  const [userInfo, setUserInfo] = useState<UserInfo | null>(null);
  const [userTypeParams, setUserTypeParams] = useState<Users[]>([]);

  // 初始化用户信息和用户类型参数
  useEffect(() => {
    const fetchInitialData = async () => {
      try {
        // 检查是否有 access_token
        const accessToken = localStorage.getItem('access_token');
        if (accessToken) {
          // 并行获取用户信息和用户类型参数
          const [userInfoResponse, userTypeParamsResponse] = await Promise.all([
            getCurrentLoginUserInfo(),
            startUp()
          ]);

          setUserInfo(userInfoResponse);
          setUserTypeParams(userTypeParamsResponse?.usersTypeParamList || []);
        }
      } catch (error) {
        console.error('初始化用户信息失败', error);
        localStorage.removeItem('access_token');
      }
    };

    fetchInitialData();
  }, []);

  return (
    <GlobalContext.Provider
      value={{
        userInfo,
        setUserInfo,
        userTypeParams,
        setUserTypeParams
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

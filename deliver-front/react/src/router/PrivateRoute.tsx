import { Navigate, Outlet } from 'react-router-dom';
import { useGlobalContext } from '@/context/GlobalContext';

const PrivateRoute = () => {
  useGlobalContext();

  const isAuthenticated = !!localStorage.getItem('access_token');

  // 如果没有 token，重定向到登录
  if (!isAuthenticated) {
    return <Navigate to="/login" replace />;
  }

  // // 如果没有群组ID，重定向到群组管理
  // if (!hasGroupId) {
  //   const allowedPathsWithoutGroupId = ['/welcome', '/groupManage', '/login'];

  //   if (!allowedPathsWithoutGroupId.some((path) => location.pathname.startsWith(path))) {
  //     return <Navigate to="/groupManage" replace />;
  //   }
  // }

  // 都满足条件，正常渲染子路由
  return <Outlet />;
};

export default PrivateRoute;

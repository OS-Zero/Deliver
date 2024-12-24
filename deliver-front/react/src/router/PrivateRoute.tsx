import { Navigate, Outlet, useLocation } from 'react-router-dom';

// 路由守卫
const PrivateRoute = () => {
  const location = useLocation();
  const isAuthenticated = !!localStorage.getItem('access_token');
  const hasGroupId = !!localStorage.getItem('groupId');

  // 如果没有 token，重定向到登录
  if (!isAuthenticated) {
    return <Navigate to="/login" replace />;
  }

  // 如果没有 groupId，且当前不在 welcome 页面，重定向到欢迎页
  // if (!hasGroupId && location.pathname !== '/welcome') {
  //   return <Navigate to="/welcome" replace />;
  // }

  // 都满足条件，正常渲染子路由
  return <Outlet />;
};

export default PrivateRoute;

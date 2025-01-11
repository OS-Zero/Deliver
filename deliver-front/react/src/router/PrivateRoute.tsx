import { Navigate, Outlet, useLocation, useNavigate } from 'react-router-dom';

// 路由守卫
const PrivateRoute = () => {
  const location = useLocation();
  const isAuthenticated = !!localStorage.getItem('access_token');
  const hasGroupId = !!localStorage.getItem('groupId');

  const navigate = useNavigate();

  // 如果没有 token，重定向到登录
  if (!isAuthenticated) {
    return <Navigate to="/login" replace />;
  }

  // if (!hasGroupId && location.pathname.includes('groupManage')) {
  //   navigate('/groupManage', { replace: true });
  // }

  // 都满足条件，正常渲染子路由
  return <Outlet />;
};

export default PrivateRoute;

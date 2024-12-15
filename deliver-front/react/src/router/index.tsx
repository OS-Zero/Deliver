import { Suspense, lazy } from 'react';
import { RouteObject, Navigate } from 'react-router-dom';
import PrivateRoute from './PrivateRoute';

const LoginPage = lazy(() => import('../views/Login'));
const DashboardPage = lazy(() => import('../DashboardPage'));
const HomePage = lazy(() => import('../views/Home'));

const routes: RouteObject[] = [
  {
    path: '/',
    element: (
      <Suspense fallback={<div>Loading...</div>}>
        {localStorage.getItem('access_token') ? (
          <Navigate to="/home" replace />
        ) : (
          <Navigate to="/login" replace />
        )}
      </Suspense>
    )
  },
  {
    path: '/login',
    element: (
      <Suspense fallback={<div>Loading...</div>}>
        <LoginPage />
      </Suspense>
    )
  },
  {
    element: <PrivateRoute />,
    children: [
      {
        path: '/home',
        element: (
          <Suspense fallback={<div>Loading...</div>}>
            <HomePage />
          </Suspense>
        )
      },
      {
        path: '/dashboard',
        element: (
          <Suspense fallback={<div>Loading...</div>}>
            <DashboardPage />
          </Suspense>
        )
      }
      // 这里可以添加更多需要保护的路由
    ]
  }
];

export default routes;

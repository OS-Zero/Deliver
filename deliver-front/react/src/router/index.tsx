import { Suspense, lazy } from 'react';
import { Navigate } from 'react-router-dom';
import PrivateRoute from './PrivateRoute.tsx';
import Layout from '../views/Layout';

const LoadingPage = lazy(() => import('../components/Loading'));
const LoginPage = lazy(() => import('../views/Login'));
const GroupManagePage = lazy(() => import('../views/GroupManage'));
const TemplatePage = lazy(() => import('../views/GroupManage/pages/MessageTemplate'));
const AppPage = lazy(() => import('../views/GroupManage/pages/ChannelApp'));
const PlatformFilePage = lazy(() => import('../views/GroupManage/pages/PlatformFile'));
const TaskManagePage = lazy(() => import('../views/GroupManage/pages/TaskManage'));
const PeopleGroupPage = lazy(() => import('../views/GroupManage/pages/PeopleGroup'));
const SystemManagePage = lazy(() => import('../views/SystemManage'));
const MyAccountPage = lazy(() => import('../views/SystemManage/pages/MyAccount'));
const WelcomePage = lazy(() => import('../views/Welcome'));
const NotFoundPage = lazy(() => import('../views/NotFoundPage'));

const routes = [
  {
    path: '/login',
    element: (
      <Suspense fallback={<LoadingPage />}>
        <LoginPage />
      </Suspense>
    )
  },
  {
    path: '/',
    element: <Layout />,
    name: '首页',
    children: [
      {
        element: <PrivateRoute />,
        children: [
          {
            index: true,
            element: <Navigate to="/welcome" replace />
          },
          {
            path: 'welcome',
            element: (
              <Suspense fallback={<LoadingPage />}>
                <WelcomePage />
              </Suspense>
            ),
            name: '欢迎'
          },
          {
            path: 'groupManage',
            element: (
              <Suspense fallback={<LoadingPage />}>
                <GroupManagePage />
              </Suspense>
            ),
            name: '分组管理',
            children: [
              {
                index: true,
                element: <Navigate to="/groupManage" replace />
              },
              {
                path: 'template',
                element: (
                  <Suspense fallback={<LoadingPage />}>
                    <TemplatePage />
                  </Suspense>
                ),
                name: '模板管理'
              },
              {
                path: 'app',
                element: (
                  <Suspense fallback={<LoadingPage />}>
                    <AppPage />
                  </Suspense>
                ),
                name: '应用配置'
              },
              {
                path: 'file',
                element: (
                  <Suspense fallback={<LoadingPage />}>
                    <PlatformFilePage />
                  </Suspense>
                ),
                name: '文件管理'
              },
              {
                path: 'task',
                element: (
                  <Suspense fallback={<LoadingPage />}>
                    <TaskManagePage />
                  </Suspense>
                ),
                name: '群发任务配置'
              },
              {
                path: 'peopleGroup',
                element: (
                  <Suspense fallback={<LoadingPage />}>
                    <PeopleGroupPage />
                  </Suspense>
                ),
                name: '人群模版配置'
              }
            ]
          },
          {
            path: 'systemManage',
            element: (
              <Suspense fallback={<LoadingPage />}>
                <SystemManagePage />
              </Suspense>
            ),
            name: '系统管理',
            children: [
              {
                index: true,
                element: <Navigate to="myAccount" replace />
              },
              {
                path: 'myAccount',
                element: (
                  <Suspense fallback={<LoadingPage />}>
                    <MyAccountPage />
                  </Suspense>
                ),
                name: '我的账户'
              }
            ]
          }
        ]
      }
    ]
  },
  {
    path: '*',
    element: (
      <Suspense fallback={<LoadingPage />}>
        <NotFoundPage />
      </Suspense>
    )
  }
];

export default routes;

import { Suspense, lazy } from 'react';
import { RouteObject, Navigate } from 'react-router-dom';
import PrivateRoute from './PrivateRoute';

const LoadingPage = lazy(() => import('../components/Loading'));
const LoginPage = lazy(() => import('../views/Login'));
const GroupManagePage = lazy(() => import('../views/GroupManage'));
const TemplatePage = lazy(() => import('../views/GroupManage/pages/Template'));
const AppPage = lazy(() => import('../views/GroupManage/pages/AppConfig'));
const PlatformFilePage = lazy(() => import('../views/GroupManage/pages/PlatformFile'));
const FlowControlRulePage = lazy(() => import('../views/GroupManage/pages/FlowControlRule'));
const SystemManagePage = lazy(() => import('../views/SystemManage'));
const MyAccountPage = lazy(() => import('../views/SystemManage/pages/MyAccount'));
const WelcomePage = lazy(() => import('../views/Welcome'));
const NotFoundPage = lazy(() => import('../views/NotFoundPage'));

const routes: RouteObject[] = [
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
    element: <PrivateRoute />,
    children: [
      {
        path: '',
        element: <Navigate to="/welcome" replace />,
      },
      {
        path: 'welcome',
        element: (
          <Suspense fallback={<LoadingPage />}>
            <WelcomePage />
          </Suspense>
        )
      },
      {
        path: 'groupManage',
        element: (
          <Suspense fallback={<LoadingPage />}>
            <GroupManagePage />
          </Suspense>
        ),
        children: [
          {
            path: '',
            element: <Navigate to="template" replace />,
          },
          {
            path: 'template',
            element: (
              <Suspense fallback={<LoadingPage />}>
                <TemplatePage />
              </Suspense>
            )
          },
          {
            path: 'app',
            element: (
              <Suspense fallback={<LoadingPage />}>
                <AppPage />
              </Suspense>
            )
          },
          {
            path: 'file',
            element: (
              <Suspense fallback={<LoadingPage />}>
                <PlatformFilePage />
              </Suspense>
            )
          },
          {
            path: 'flowControlRule',
            element: (
              <Suspense fallback={<LoadingPage />}>
                <FlowControlRulePage />
              </Suspense>
            )
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
        children: [
          {
            path: '',
            element: <Navigate to="myAccount" replace />,
          },
          {
            path: 'myAccount',
            element: (
              <Suspense fallback={<LoadingPage />}>
                <MyAccountPage />
              </Suspense>
            )
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

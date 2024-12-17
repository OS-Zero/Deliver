import { Suspense, lazy } from 'react';
import { RouteObject, Navigate } from 'react-router-dom';
import PrivateRoute from './PrivateRoute';
import LoadingSpinner from '../components/LoadingSpinner';

const LoginPage = lazy(() => import('../views/Login'));
const LayoutPage = lazy(() => import('../views/Layout'));
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
    path: '/',
    element: (
      <Suspense fallback={<LoadingSpinner />}>
        {localStorage.getItem('access_token') ? (
          <Navigate to="/welcome" replace />
        ) : (
          <Navigate to="/login" replace />
        )}
      </Suspense>
    )
  },
  {
    path: '/login',
    element: (
      <Suspense fallback={<LoadingSpinner />}>
        <LoginPage />
      </Suspense>
    )
  },
  {
    element: <PrivateRoute />,
    children: [
      {
        path: '/',
        element: (
          <Suspense fallback={<LoadingSpinner />}>
            <LayoutPage />
          </Suspense>
        ),
        children: [
          {
            path: 'groupManage',
            element: (
              <Suspense fallback={<LoadingSpinner />}>
                <GroupManagePage />
              </Suspense>
            ),
            children: [
              {
                path: 'template',
                element: (
                  <Suspense fallback={<LoadingSpinner />}>
                    <TemplatePage />
                  </Suspense>
                )
              },
              {
                path: 'app',
                element: (
                  <Suspense fallback={<LoadingSpinner />}>
                    <AppPage />
                  </Suspense>
                )
              },
              {
                path: 'file',
                element: (
                  <Suspense fallback={<LoadingSpinner />}>
                    <PlatformFilePage />
                  </Suspense>
                )
              },
              {
                path: 'flowControlRule',
                element: (
                  <Suspense fallback={<LoadingSpinner />}>
                    <FlowControlRulePage />
                  </Suspense>
                )
              }
            ]
          },
          {
            path: 'systemManage',
            element: (
              <Suspense fallback={<LoadingSpinner />}>
                <SystemManagePage />
              </Suspense>
            ),
            children: [
              {
                path: 'myAccount',
                element: (
                  <Suspense fallback={<LoadingSpinner />}>
                    <MyAccountPage />
                  </Suspense>
                )
              }
            ]
          },
          {
            path: 'welcome',
            element: (
              <Suspense fallback={<LoadingSpinner />}>
                <WelcomePage />
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
      <Suspense fallback={<LoadingSpinner />}>
        <NotFoundPage />
      </Suspense>
    )
  }
];

export default routes;

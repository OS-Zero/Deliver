import React from 'react';
import { Breadcrumb as AntBreadcrumb } from 'antd';
import { useLocation, matchRoutes, useNavigate } from 'react-router-dom';
import routes from '@/router';

interface BreadcrumbRoute {
  path: string;
  breadcrumbName: string;
}

const Breadcrumb: React.FC = () => {
  const location = useLocation();
  const navigate = useNavigate();
  const matchedRoutes = matchRoutes(routes, location);

  const generateBreadcrumbs = (matchedRoutes: any) => {
    const breadcrumbs: BreadcrumbRoute[] = [];
    matchedRoutes?.forEach((match: any) => {
      const route = match.route;
      if (route.path) {
        breadcrumbs.push({
          path: match.pathname,
          breadcrumbName: route.name || 'Unknown'
        });
      }
    });
    return breadcrumbs;
  };

  const handleBreadcrumbClick = (route: BreadcrumbRoute) => {
    if (route.path.includes('/groupManage')) {
      // 清除 group 相关信息
      localStorage.removeItem('group_id');
      localStorage.removeItem('group_name');
    }

    navigate(route.path, { replace: true });
  };

  const breadcrumbRoutes = generateBreadcrumbs(matchedRoutes);

  const items = breadcrumbRoutes.map((route, index) => {
    const last = index === breadcrumbRoutes.length - 1;
    return {
      key: route.path,
      title: last ? (
        <span>{route.breadcrumbName}</span>
      ) : (
        <div
          onClick={() => handleBreadcrumbClick(route)}
          style={{ cursor: 'pointer', display: 'inline' }}
        >
          {route.breadcrumbName}
        </div>
      )
    };
  });

  return <AntBreadcrumb separator="/" items={items} />;
};

export default Breadcrumb;

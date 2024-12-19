import React from 'react';
import { Breadcrumb as AntBreadcrumb } from 'antd';
import { Link, useLocation, matchRoutes } from 'react-router-dom';
import routes from '@/router';

interface BreadcrumbRoute {
  path: string;
  breadcrumbName: string;
}
const Breadcrumb: React.FC = () => {
  const location = useLocation();
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

  const breadcrumbRoutes = generateBreadcrumbs(matchedRoutes);

  const items = breadcrumbRoutes.map((route, index) => {
    const last = index === breadcrumbRoutes.length - 1;
    return {
      key: route.path,
      title: last ? (
        <span>{route.breadcrumbName}</span>
      ) : (
        <Link to={route.path}>{route.breadcrumbName}</Link>
      )
    };
  });

  return <AntBreadcrumb separator="/" items={items} />;
};

export default Breadcrumb;

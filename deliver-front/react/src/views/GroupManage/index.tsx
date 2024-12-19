import React from 'react';
import { Navigate, Outlet, useLocation } from 'react-router-dom';

const GroupManage: React.FC = () => {
  const location = useLocation();

  if (location.pathname === '/groupManage') {
    return <Navigate to="/groupManage/template" replace />;
  }

  return <Outlet />;
};

export default GroupManage;

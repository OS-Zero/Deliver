import { useState, useEffect } from 'react';
import { useLocation } from 'react-router-dom';

export const usePathMatch = () => {
  const location = useLocation();
  const [current, setCurrent] = useState('');
  const [selectedKeys, setSelectedKeys] = useState<string[]>([]);

  useEffect(() => {
    const path = location.pathname;

    // 处理顶部导航栏的当前选中项
    if (path.includes('groupManage')) {
      setCurrent('groupManage');
    } else if (path.includes('systemManage')) {
      setCurrent('systemManage');
    } else {
      setCurrent('');
    }

    // 处理侧边栏的选中项
    if (path === '/groupManage') {
      setSelectedKeys(['/groupManage/template']);
    } else {
      setSelectedKeys([path]);
    }
  }, [location.pathname]);

  return { current, selectedKeys };
};

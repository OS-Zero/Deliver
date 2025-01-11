import {
  MessageOutlined,
  FileTextOutlined,
  AppstoreOutlined,
  UserOutlined,
  SoundOutlined
} from '@ant-design/icons';
import { Link } from 'react-router-dom';

export const menuConfig = {
  groupManage: [
    {
      key: 'MC',
      icon: <MessageOutlined />,
      label: '消息配置',
      children: [
        {
          key: '/groupManage/template',
          label: <Link to="/groupManage/template">消息模板配置</Link>
        }
      ]
    },
    {
      key: 'AC',
      icon: <AppstoreOutlined />,
      label: '应用配置',
      children: [
        {
          key: '/groupManage/app',
          label: <Link to="/groupManage/app">渠道应用配置</Link>
        }
      ]
    },
    {
      key: 'FM',
      icon: <FileTextOutlined />,
      label: '文件管理',
      children: [
        {
          key: '/groupManage/file',
          label: <Link to="/groupManage/file">平台文件管理</Link>
        }
      ]
    },
    {
      key: 'TM',
      icon: <SoundOutlined />,
      label: '群发任务',
      children: [
        {
          key: '/groupManage/task',
          label: <Link to="/groupManage/task">群发任务配置</Link>
        },
        {
          key: '/groupManage/peopleGroup',
          label: <Link to="/groupManage/peopleGroup">人群配置</Link>
        },
      ]
    },
  ],
  systemManage: [
    {
      key: '/systemManage/myAccount',
      icon: <UserOutlined />,
      label: <Link to="/systemManage/myAccount">我的账户</Link>
    }
  ]
};

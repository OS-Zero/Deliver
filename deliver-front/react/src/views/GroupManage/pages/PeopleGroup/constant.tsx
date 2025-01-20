import type { ProColumns, ProDescriptionsItemProps } from '@ant-design/pro-components';
import { PeopleGroup } from './type';
import { Tag } from 'antd';
import { getColor } from '@/utils/getTagStyle';

// 表格schema配置
export const peopleTableSchema: ProColumns<PeopleGroup>[] = [
  {
    title: '人群名',
    width: 120,
    dataIndex: 'peopleGroupName',
    valueType: 'text'
  },
  {
    title: '用户类型',
    width: 120,
    dataIndex: 'usersTypeName',
    render: (_, record) => <Tag color={getColor(record?.usersType)}>{_}</Tag>
  },
  {
    width: 120,
    title: '创建者',
    dataIndex: 'createUser',
    valueType: 'text'
  },
  {
    width: 120,
    title: '创建时间',
    dataIndex: 'createTime',
    valueType: 'dateTime'
  }
];

// 详情schema配置
export const peopleColumns: {
  title: string;
  key: string;
  children: ProDescriptionsItemProps<PeopleGroup>[];
}[] = [
  {
    title: '人群信息',
    key: 'basicInfo',
    children: [
      {
        title: '关联人群',
        valueType: 'text',
        dataIndex: 'peopleGroupName'
      },
      {
        valueType: 'textarea',
        title: '人群描述',
        dataIndex: 'peopleGroupDescription'
      },
      {
        title: '人群列表',
        dataIndex: 'peopleGroupList'
      },
      {
        valueType: 'text',
        title: '创建者',
        dataIndex: 'createUser'
      },
      {
        title: '创建时间',
        dataIndex: 'createTime',
        valueType: 'dateTime'
      }
    ]
  },
  {
    title: '类型信息',
    key: 'typeInfo',
    children: [
      {
        title: '用户类型',
        valueType: 'text',
        dataIndex: 'usersTypeName'
      }
    ]
  }
];

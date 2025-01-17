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
export const peopleColumns: ProDescriptionsItemProps<PeopleGroup>[] = [
  {
    title: '人群 ID',
    valueType: 'text',
    dataIndex: 'peopleGroupId',
    ellipsis: true,
    copyable: true
  },
  {
    title: '人群名',
    valueType: 'text',
    dataIndex: 'peopleGroupName'
  },
  {
    valueType: 'textarea',
    title: '人群描述',
    dataIndex: 'peopleGroupDescription'
  },
  {
    title: '用户类型',
    valueType: 'text',
    dataIndex: 'usersTypeName'
  },
  {
    valueType: 'code',
    title: '关联人群',
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
];

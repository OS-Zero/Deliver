import type { ProColumns, ProDescriptionsItemProps } from '@ant-design/pro-components';
import { PlatformFileDetail } from './type';
import { Tag } from 'antd';
import { getColor } from '@/utils/getTagStyle';

// 表格schema配置
export const platformTableSchema: ProColumns<PlatformFileDetail>[] = [
  {
    title: '文件名',
    width: 120,
    dataIndex: 'platformFileName',
    valueType: 'text'
  },
  {
    title: '文件类型',
    width: 120,
    dataIndex: 'platformFileTypeName',
    render: (_, record) => <Tag color={getColor(record?.platformFileType)}>{_}</Tag>
  },
  {
    title: '渠道类型',
    width: 120,
    dataIndex: 'channelTypeName',
    render: (_, record) => <Tag color={getColor(record?.channelType)}>{_}</Tag>
  },
  {
    title: '关联应用',
    width: 120,
    dataIndex: 'appName',
    render: (_, record) => <a color={getColor(record?.appId)}>{_}</a>
  },
  {
    title: '文件状态',
    width: 120,
    dataIndex: 'platformFileStatus',
    render: () => <Tag color="green">生效中</Tag>
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
export const platformColumns: {
  title: string;
  key: string;
  children: ProDescriptionsItemProps<PlatformFileDetail>[];
}[] = [
  {
    title: '文件信息',
    key: 'basicInfo',
    children: [
      {
        title: '文件名',
        dataIndex: 'platformFileName'
      },
      {
        title: '文件描述',
        dataIndex: 'platformFileDescription'
      },
      {
        title: '文件类型',
        dataIndex: 'platformFileTypeName'
      },
      {
        title: '文件 Key',
        dataIndex: 'platformFileKey'
      },
      {
        title: '文件状态',
        dataIndex: 'platformFileStatus',
        valueType: 'select',
        valueEnum: {
          0: { text: '禁用', status: 'Default' },
          1: { text: '启用', status: 'Success' }
        }
      },
      {
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
        key: 'text',
        title: '渠道类型',
        dataIndex: 'channelTypeName'
      }
    ]
  },
  {
    title: '关联信息',
    key: 'relatedInfo',
    children: [
      {
        key: 'text',
        title: '应用名',
        dataIndex: 'appName'
      }
    ]
  },
];

import type { ProColumns, ProDescriptionsItemProps } from '@ant-design/pro-components';
import { ChannelApp } from './type';
import { Tag, Typography } from 'antd';
import { getColor } from '@/utils/getTagStyle';

const { Paragraph } = Typography;

// 表格schema配置
export const appTableSchema: (statusConfig: ProColumns<ChannelApp>) => ProColumns<ChannelApp>[] = (
  statusConfig
) => [
  {
    title: '应用 ID',
    width: 80,
    dataIndex: 'appId',
    fixed: 'left',
    render: (_, record) => <Paragraph copyable>{record?.appId}</Paragraph>
  },
  {
    title: '应用名',
    width: 120,
    dataIndex: 'appName',
    valueType: 'text'
  },
  {
    title: '渠道类型',
    width: 120,
    dataIndex: 'channelTypeName',
    render: (_, record) => <Tag color={getColor(record?.channelType)}>{_}</Tag>
  },
  {
    title: '渠道供应商类型',
    width: 120,
    dataIndex: 'channelProviderTypeName',
    render: (_, record) => <Tag color={getColor(record?.channelProviderType)}>{_}</Tag>
  },
  statusConfig,
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
export const appColumns: ProDescriptionsItemProps<ChannelApp>[] = [
  {
    title: '应用 ID',
    key: 'text',
    dataIndex: 'appId',
    ellipsis: true,
    copyable: true
  },
  {
    title: '应用名',
    key: 'text',
    dataIndex: 'appName'
  },
  {
    key: 'textarea',
    title: '应用描述',
    dataIndex: 'appDescription'
  },
  {
    key: 'text',
    title: '渠道类型',
    dataIndex: 'channelTypeName'
  },
  {
    key: 'text',
    title: '渠道供应商类型',
    dataIndex: 'channelProviderTypeName'
  },
  {
    key: 'text',
    title: '应用配置',
    dataIndex: 'appConfig'
  },
  {
    title: '应用状态',
    key: 'state',
    dataIndex: 'appStatus',
    valueType: 'select',
    valueEnum: {
      0: { text: '禁用', status: 'Default' },
      1: { text: '启用', status: 'Success' }
    }
  },
  {
    key: 'text',
    title: '创建者',
    dataIndex: 'createUser'
  },
  {
    title: '创建时间',
    key: 'date',
    dataIndex: 'createTime',
    valueType: 'dateTime'
  }
];

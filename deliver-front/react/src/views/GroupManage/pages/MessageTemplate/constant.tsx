import type { ProColumns, ProDescriptionsItemProps } from '@ant-design/pro-components';
import { MessageTemplate } from './type';
import { Tag, Typography } from 'antd';
import { getColor } from '@/utils/getTagStyle';

const { Paragraph } = Typography;

// 表格schema配置
export const messageTableSchema: (
  statusConfig: ProColumns<MessageTemplate>
) => ProColumns<MessageTemplate>[] = (statusConfig) => [
  {
    title: '模板 ID',
    width: 80,
    dataIndex: 'templateId',
    fixed: 'left',
    render: (_, record) => <Paragraph copyable>{record?.templateId}</Paragraph>
  },
  {
    title: '模版名',
    width: 120,
    dataIndex: 'templateName'
  },
  {
    title: '消息类型',
    width: 120,
    dataIndex: 'messageTypeName',
    render: (_) => <a>{_}</a>
  },
  {
    title: '用户类型',
    width: 120,
    dataIndex: 'usersTypeName',
    render: (_, record) => <Tag color={getColor(record?.usersType)}>{_}</Tag>
  },
  {
    title: '渠道类型',
    width: 120,
    dataIndex: 'channelTypeName',
    render: (_, record) => <Tag color={getColor(Number(record?.channelType))}>{_}</Tag>
  },
  {
    title: '渠道供应商类型',
    width: 120,
    dataIndex: 'channelProviderTypeName',
    render: (_, record) => <Tag color={getColor(record?.channelProviderType)}>{_}</Tag>
  },
  statusConfig,
  {
    title: '关联应用',
    width: 120,
    dataIndex: 'appName',
    render: (_, record) => <Tag color={getColor(record?.appId)}>{_}</Tag>
  },
  {
    width: 120,
    title: '创建人',
    dataIndex: 'createUser',
    valueType: 'text'
  },
  {
    width: 150,
    title: '创建时间',
    dataIndex: 'createTime',
    valueType: 'dateTime'
  }
];

// 详情schema配置
export const templateColumns: ProDescriptionsItemProps<MessageTemplate>[] = [
  {
    title: '模版 ID',
    key: 'text',
    dataIndex: 'templateId',
    ellipsis: true,
    copyable: true
  },
  {
    title: '模版名',
    key: 'text',
    dataIndex: 'templateName'
  },
  {
    title: '消息类型',
    dataIndex: 'messageTypeName',
    render: (_) => <a>{_}</a>
  },
  {
    key: 'text',
    title: '用户类型',
    dataIndex: 'usersTypeName'
  },
  {
    key: 'text',
    title: '渠道类型',
    dataIndex: 'channelTypeName'
  },
  {
    title: '模版状态',
    key: 'state',
    dataIndex: 'templateStatus',
    valueType: 'select',
    valueEnum: {
      0: { text: '禁用', status: 'Default' },
      1: { text: '启用', status: 'Success' }
    }
  },
  {
    key: 'text',
    title: '创建人',
    dataIndex: 'createUser'
  },
  {
    title: '创建时间',
    key: 'date',
    dataIndex: 'createTime',
    valueType: 'dateTime'
  },
  {
    key: 'text',
    title: '应用名',
    dataIndex: 'appName'
  }
];

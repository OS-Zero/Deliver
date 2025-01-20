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
  {
    title: '消息类型',
    width: 120,
    dataIndex: 'messageTypeName',
    render: (_, record) => <Tag color={getColor(record?.messageType)}>{_}</Tag>
  },
  statusConfig,
  {
    title: '关联应用',
    width: 120,
    dataIndex: 'appName',
    render: (_, record) => <a color={getColor(record?.appId)}>{_}</a>
  },
  {
    width: 120,
    title: '创建者',
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
export const templateColumns: {
  title: string;
  key: string;
  children: ProDescriptionsItemProps<MessageTemplate>[];
}[] = [
  {
    title: '模板信息',
    key: 'templateInfo',
    children: [
      {
        title: '模版 ID',
        dataIndex: 'templateId',
        ellipsis: true,
        copyable: true
      },
      {
        title: '模版名',
        dataIndex: 'templateName'
      },
      {
        title: '模版描述',
        dataIndex: 'templateDescription'
      },
      {
        title: '模版状态',
        dataIndex: 'templateStatus',
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
        title: '用户类型',
        dataIndex: 'usersTypeName'
      },
      {
        title: '渠道类型',
        dataIndex: 'channelTypeName'
      },
      {
        title: '渠道供应商类型',
        dataIndex: 'channelProviderTypeName'
      },
      {
        title: '消息类型',
        dataIndex: 'messageTypeName'
      }
    ]
  },
  {
    title: '关联信息',
    key: 'relatedInfo',
    children: [
      {
        title: '关联应用',
        dataIndex: 'appName'
      }
    ]
  }
];

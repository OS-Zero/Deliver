import type { ProColumns, ProDescriptionsItemProps } from '@ant-design/pro-components';
import { MessageTemplate } from './type';
import { Tag, Typography } from 'antd';

const { Paragraph } = Typography;

const getColor = (num: number) => {
  const colors = ['green', 'blue', 'purple', 'cyan', 'orange', 'pink', 'red'];
  return colors[num];
};

const getImg = (num: number | string) => {
  const imgPaths = [
    { src: '', alt: '' },
    { src: '/assets/电话.png', alt: '电话' },
    { src: '/assets/短信.png', alt: '短信' },
    { src: '/assets/邮件.png', alt: '邮件' },
    { src: '/assets/钉钉.png', alt: '钉钉' },
    { src: '/assets/企业微信.png', alt: '企业微信' },
    { src: '/assets/飞书.png', alt: '飞书' }
  ];
  return imgPaths[Number(num)];
};

// 表格schema配置
export const messageTableSchema: ProColumns<MessageTemplate>[] = [
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
    title: '推送范围',
    width: 120,
    dataIndex: 'channelProviderTypeName',
    render: (_, record) => <Tag color={getColor(record?.channelProviderType)}>{_}</Tag>
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
    valueType: 'image',
    render: (_, record) => {
      const { src, alt } = getImg(record?.channelType);
      return <img src={src} alt={alt} style={{ width: 35, height: 35 }} />;
    }
  }
];

// 详情schema配置
export const detailColumns: ProDescriptionsItemProps<MessageTemplate>[] = [
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
    title: '推送范围',
    dataIndex: 'channelProviderTypeName',
  },
  {
    key: 'text',
    title: '用户类型',
    dataIndex: 'usersTypeName',
  },
  {
    key: 'text',
    title: '渠道类型',
    dataIndex: 'channelTypeName',
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
    dataIndex: 'createUser',
  },
  {
    title: '创建时间',
    key: 'date',
    dataIndex: 'createTime',
    valueType: 'dateTime'
  },
  {
    key: 'text',
    title: 'App 名称',
    dataIndex: 'appName',
  },
];

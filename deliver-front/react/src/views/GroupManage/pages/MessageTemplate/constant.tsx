import type { ProColumns } from '@ant-design/pro-components';
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

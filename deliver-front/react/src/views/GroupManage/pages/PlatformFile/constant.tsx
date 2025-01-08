import type { ProColumns, ProDescriptionsItemProps } from '@ant-design/pro-components';
import { ChannelApp } from './type';
import { Tag, Typography } from 'antd';
import { getColor, getImg } from '@/utils/getTagStyle';

const { Paragraph } = Typography;

// 表格schema配置
export const platformTableSchema: (statusConfig: ProColumns<ChannelApp>) => ProColumns<ChannelApp>[] = (
  statusConfig
) => [
  {
    title: '文件 ID',
    width: 80,
    dataIndex: 'platformFileId',
    fixed: 'left',
    render: (_, record) => <Paragraph copyable>{record?.platformFileId}</Paragraph>
  },
  {
    title: '文件名',
    width: 120,
    dataIndex: 'platformFileName',
    valueType: 'text'
  },
  {
    title: 'APP 类型',
    width: 120,
    dataIndex: 'appName',
    valueType: 'image',
    render: (_, record) => {
      const { src, alt } = getImg(record?.appId) || { src: '', alt: '' };
      return <img src={src} alt={alt} style={{ width: 35, height: 35 }} />;
    }
  },
  {
    title: '文件类型',
    width: 120,
    dataIndex: 'platformFileTypeName',
    render: (_, record) => <Tag color={getColor(record?.platformFileKey)}>{_}</Tag>
  },
  statusConfig,
  {
    width: 120,
    title: '创建用户',
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
export const platformColumns: ProDescriptionsItemProps<ChannelApp>[] = [
  {
    title: '文件 ID',
    key: 'text',
    dataIndex: 'platformFileId',
    ellipsis: true,
    copyable: true
  },
  {
    title: '文件名',
    key: 'text',
    dataIndex: 'platformFileName'
  },
  {
    key: 'textarea',
    title: '文件描述',
    dataIndex: 'platformFileDescription',
  },
  {
    key: 'text',
    title: '文件类型',
    dataIndex: 'platformFileTypeName',
  },
  {
    key: 'text',
    title: '渠道类型',
    dataIndex: 'channelType'
  },
  {
    title: '文件状态',
    key: 'state',
    dataIndex: 'platformFileStatus',
    valueType: 'select',
    valueEnum: {
      0: { text: '禁用', status: 'Default' },
      1: { text: '启用', status: 'Success' }
    }
  },
  {
    key: 'text',
    title: '应用名称',
    dataIndex: 'appName'
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
];

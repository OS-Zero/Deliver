import type { ProColumns, ProDescriptionsItemProps } from '@ant-design/pro-components';
import { PlatformFileDetail } from './type';
import { Tag, Typography } from 'antd';
import { getColor } from '@/utils/getTagStyle';

const { Paragraph } = Typography;

// 表格schema配置
export const platformTableSchema: ProColumns<PlatformFileDetail>[] = [
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
    render: (_, record) => <Tag color={getColor(record?.appId)}>{_}</Tag>
  },
  {
    title: '文件类型',
    width: 120,
    dataIndex: 'platformFileTypeName',
    render: (_, record) => <Tag color={getColor(Number(record?.platformFileType))}>{_}</Tag>
  },
  {
    title: '文件状态',
    width: 120,
    dataIndex: 'platformFileStatus',
    render: (_, record) => (record?.platformFileStatus ? '启用' : '禁用')
  },
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
export const platformColumns: ProDescriptionsItemProps<PlatformFileDetail>[] = [
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
    dataIndex: 'platformFileDescription'
  },
  {
    key: 'text',
    title: '文件类型',
    dataIndex: 'platformFileTypeName'
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
  }
];

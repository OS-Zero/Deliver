import type { ProColumns, ProDescriptionsItemProps } from '@ant-design/pro-components';
import { TaskDetail } from './type';
import { Tag, Typography } from 'antd';
import { getColor } from '@/utils/getTagStyle';

const { Paragraph } = Typography;

export const taskTypeOption = [
  { label: '实时', value: 1 },
  { label: '定时', value: 2 }
];

export const statusOption = [
  { label: '启用', value: 1 },
  { label: '禁用', value: 0 }
];

// 表格schema配置
export const taskTableSchema: (statusConfig: ProColumns<TaskDetail>) => ProColumns<TaskDetail>[] = (
  statusConfig
) => [
  {
    title: '任务名',
    width: 120,
    dataIndex: 'taskName',
    valueType: 'text'
  },
  {
    title: '任务类型',
    width: 120,
    dataIndex: 'taskType',
    render: (_, record) => (
      <Tag color={getColor(record?.taskType)}>
        {Number(record?.taskType) === 1 ? '实时' : '定时'}
      </Tag>
    )
  },
  statusConfig,
  {
    width: 120,
    title: '关联模版',
    dataIndex: 'templateName',
    valueType: 'text',
    render: (_) => <a>{_}</a>
  },
  {
    width: 120,
    title: '关联人群',
    dataIndex: 'peopleGroupName',
    valueType: 'text',
    render: (_) => <a>{_}</a>
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
export const taskColumns: ProDescriptionsItemProps<TaskDetail>[] = [
  {
    title: '任务 ID',
    key: 'text',
    dataIndex: 'taskId',
    ellipsis: true,
    copyable: true
  },
  {
    title: '任务名',
    key: 'text',
    dataIndex: 'taskName'
  },
  {
    key: 'textarea',
    title: '任务描述',
    dataIndex: 'taskDescription'
  },
  {
    key: 'text',
    title: '任务配置',
    dataIndex: 'taskParam'
  },
  {
    title: '任务类型',
    key: 'text',
    dataIndex: 'taskType',
    valueType: 'select',
    valueEnum: {
      1: { text: '不知道', status: 'Default' },
      2: { text: '不知道2', status: 'Success' }
    }
  },
  {
    title: '任务状态',
    key: 'state',
    dataIndex: 'taskStatus',
    valueType: 'select',
    valueEnum: {
      0: { text: '禁用', status: 'Default' },
      1: { text: '启用', status: 'Success' }
    }
  },
  {
    key: 'text',
    title: '关联人群',
    dataIndex: 'peopleGroupName'
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

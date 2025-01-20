import type { ProColumns, ProDescriptionsItemProps } from '@ant-design/pro-components';
import { TaskDetail } from './type';
import { Tag } from 'antd';
import { getColor } from '@/utils/getTagStyle';

export const handleTaskTypeName = (type: number) => {
  switch (type) {
    case 1:
      return '实时';
    case 2:
      return '定时循环';
    default:
      return '定时单次';
  }
};

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
      <Tag color={getColor(record?.taskType)}>{handleTaskTypeName(record?.taskType)}</Tag>
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
export const taskColumns: {
  title: string;
  key: string;
  children: ProDescriptionsItemProps<TaskDetail>[];
}[] = [
  {
    title: '任务信息',
    key: 'basicInfo',
    children: [
      {
        title: '任务名',
        dataIndex: 'taskName'
      },
      {
        title: '任务描述',
        dataIndex: 'taskDescription'
      },
      {
        title: '任务类型',
        dataIndex: 'taskType',
        valueType: 'select',
        valueEnum: {
          1: { text: '实时' },
          2: { text: '定时循环' },
          3: { text: '定时单次' }
        }
      },
      {
        title: '任务时间表达式',
        dataIndex: 'taskTimeExpression'
      },
      {
        title: '任务消息参数',
        dataIndex: 'taskMessageParam',
        valueType: 'jsonCode'
      },
      {
        title: '任务状态',
        dataIndex: 'taskStatus',
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
    title: '关联信息',
    key: 'relatedInfo',
    children: [
      {
        title: '关联模版',
        dataIndex: 'templateName'
      },
      {
        title: '关联人群',
        dataIndex: 'peopleGroupName'
      }
    ]
  }
];

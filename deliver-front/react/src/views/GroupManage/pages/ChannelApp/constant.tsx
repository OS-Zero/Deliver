import type { ProColumns, ProDescriptionsItemProps } from '@ant-design/pro-components';
import { ChannelApp } from './type';
import { Tag } from 'antd';
import { getColor } from '@/utils/getTagStyle';
import { EyeInvisibleOutlined, EyeOutlined } from '@ant-design/icons';
import { useState } from 'react';

// 表格schema配置
export const appTableSchema: (statusConfig: ProColumns<ChannelApp>) => ProColumns<ChannelApp>[] = (
  statusConfig
) => [
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
export const appColumns: {
  title: string;
  key: string;
  children: ProDescriptionsItemProps<ChannelApp>[];
}[] = [
  {
    title: '应用信息',
    key: 'basicInfo',
    children: [
      {
        title: '应用名',
        dataIndex: 'appName'
      },
      {
        title: '应用描述',
        dataIndex: 'appDescription'
      },
      {
        title: '应用配置',
        dataIndex: 'appConfig',
        valueType: 'jsonCode',
        render: (dom) => {
          const [visible, setVisible] = useState(false);
          const toggleVisible = () => setVisible(!visible);
          return (
            <div style={{ display: 'flex', justifyContent: 'flex-end', marginBottom: '8px' }}>
              {visible ? (
                <pre style={{ background: '#f5f5f5', padding: '12px', borderRadius: '4px' }}>
                  {dom}
                </pre>
              ) : (
                <pre style={{ background: '#f5f5f5', padding: '12px', borderRadius: '4px' }}>
                  {'******'}
                </pre>
              )}
              <div style={{ marginLeft: '8px' }}>
                {visible ? (
                  <EyeOutlined onClick={toggleVisible} style={{ cursor: 'pointer' }} />
                ) : (
                  <EyeInvisibleOutlined onClick={toggleVisible} style={{ cursor: 'pointer' }} />
                )}
              </div>
            </div>
          );
        }
      },
      {
        title: '应用状态',
        dataIndex: 'appStatus',
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
    title: '渠道信息',
    key: 'channelInfo',
    children: [
      {
        title: '渠道类型',
        dataIndex: 'channelTypeName'
      },
      {
        title: '渠道供应商类型',
        dataIndex: 'channelProviderTypeName'
      }
    ]
  }
];

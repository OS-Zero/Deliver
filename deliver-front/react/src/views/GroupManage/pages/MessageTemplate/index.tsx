import React from 'react';
import type { ProColumns } from '@ant-design/pro-components';
import { ProTable } from '@ant-design/pro-components';
import { Button, Space, Input, Tag, Typography } from 'antd';
import { FilterOutlined } from '@ant-design/icons';
import styles from './index.module.scss';
import { MessageTemplate } from './type';
import { getTemplatePages } from '@/api/message';

const Template: React.FC = () => {
  const { Paragraph } = Typography;
  const { Search } = Input;

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

  const columns: ProColumns<MessageTemplate>[] = [
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
        return <img src={src} alt={alt} style={{ width: 30, height: 30 }} />;
      }
    },
    {
      title: '操作',
      width: 80,
      key: 'option',
      valueType: 'option',
      fixed: 'right',
      render: () => [<a key="link">链路</a>]
    }
  ];

  /**
   * 搜索请求表单数据
   * @param data
   */
  const fetchTemplateData = async (params: { pageSize?: number; current?: number }) => {
    const { pageSize = 10, current = 1 } = params; // 提供默认值
    const res = await getTemplatePages({
      currentPage: current,
      pageSize: pageSize
    });
    return {
      data: res?.data?.records,
      success: true,
      total: res?.data?.total
    };
  };

  return (
    <div className={styles['template-container']}>
      <ProTable<MessageTemplate>
        columns={columns}
        request={fetchTemplateData}
        tableAlertRender={({ selectedRowKeys, selectedRows, onCleanSelected }) => {
          console.log(selectedRowKeys, selectedRows);
          return (
            <Space size={24}>
              <span>
                已选 {selectedRowKeys.length} 项
                <a style={{ marginInlineStart: 8 }} onClick={onCleanSelected}>
                  取消选择
                </a>
              </span>
            </Space>
          );
        }}
        tableAlertOptionRender={() => {
          return (
            <Space size={16}>
              <a>批量删除</a>
              <a>导出数据</a>
            </Space>
          );
        }}
        scroll={{ x: 1300 }}
        options={false}
        search={false}
        pagination={{
          defaultCurrent: 1,
          defaultPageSize: 10,
          showSizeChanger: true,
          showQuickJumper: true,
          showTotal: (_) => `共 ${_} 条`,
          size: 'default'
        }}
        rowKey="key"
        headerTitle={<Search placeholder="请输入" enterButton />}
        toolBarRender={() => [
          <>
            <Button shape="circle" icon={<FilterOutlined />} style={{ marginRight: '10px' }} />
            <Button key="add" type="primary">
              新增模版
            </Button>
          </>
        ]}
      />
    </div>
  );
};

export default Template;

import React from 'react';
import { ProColumns, ProTable } from '@ant-design/pro-components';
import { Button, Space, Input, Switch } from 'antd';
import { ApiOutlined, DeleteOutlined, EditOutlined, FilterOutlined } from '@ant-design/icons';
import styles from './index.module.scss';
import { MessageTemplate } from './type';
import { getTemplatePages } from '@/api/message';
import { messageTableSchema } from './constant';
import deleteConfirmModal from '@/components/DeleteConfirmModal';

const Template: React.FC = () => {
  const { Search } = Input;

  // 这两列涉及到状态的改变，于是写在视图层
  const columns: ProColumns<MessageTemplate>[] = [
    ...messageTableSchema,
    {
      title: '模板状态',
      width: 120,
      dataIndex: 'templateStatus',
      render: (_, record: MessageTemplate) => (
        <Switch
          checkedChildren="启用"
          unCheckedChildren="禁用"
          checked={Boolean(record?.templateStatus)}
        />
      )
    },
    {
      title: '操作',
      width: 160,
      valueType: 'option',
      fixed: 'right',
      render: (_, record) => [
        <a className={styles['link-button']} key="edit">
          <EditOutlined style={{ fontSize: '18px' }} />
        </a>,
        <a key="linkTest" className={styles['link-button']}>
          <ApiOutlined style={{ fontSize: '18px' }} />
        </a>,
        <a
          key="delete"
          onClick={() => deleteConfirmModal({ onConfirm: () => console.log('delete') })}
        >
          <DeleteOutlined style={{ fontSize: '18px', color: 'red' }} />
        </a>
      ]
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
      pageSize: pageSize,
      templateName: 'String',
      usersType: 0,
      templateStatus: 0,
      startTime: 'yyyy-MM-dd HH:mm:ss',
      endTime: 'yyyy-MM-dd HH:mm:ss'
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
        rowKey="templateId"
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

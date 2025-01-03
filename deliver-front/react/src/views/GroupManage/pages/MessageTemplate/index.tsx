import React, { useRef } from 'react';
import { ProColumns, ProTable } from '@ant-design/pro-components';
import { Button, Space, Input, Switch, Dropdown, MenuProps } from 'antd';
import { DownOutlined, FilterOutlined, SearchOutlined } from '@ant-design/icons';
import { MessageTemplate } from './type';
import { messageTableSchema } from './constant.tsx';
import useTemplateData from './useTemplateData';
import styles from './index.module.scss';
import DetailDrawer from './components/DetailDrawer';
import AddTemplateModal from './components/AddTemplateModal.tsx';
import TestSendDrawer from '@/components/TestSendDrawer/index.tsx';

const Template: React.FC = () => {
  const detailRef = useRef<{ getDetail: (record: MessageTemplate) => void }>();
  const addRef = useRef<{ addTemplateModal: () => void, editTemplateModal: () => void }>();
  const testRef = useRef<{ getTestSendDrawer: () => void }>();
  const { fetchTemplateData, deleteTemplateData, addTemplate } = useTemplateData();

  const items: MenuProps['items'] = [
    {
      label: '测试发送',
      key: 'test'
    },
    {
      label: '查看详情',
      key: 'detail'
    }
  ];

  const handleMenuClick = (e: any, record: MessageTemplate) => {
    if (e?.key === 'detail') {
      detailRef.current?.getDetail(record);
    } else {
      testRef.current?.getTestSendDrawer();
    }
  };

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
        <a
          className={styles['link-button']}
          key="edit"
          onClick={() => addRef?.current?.editTemplateModal(record)}
        >
          编辑
        </a>,
        <a
          className={styles['link-button-delete']}
          key="delete"
          onClick={() => deleteTemplateData([record?.templateId])}
        >
          删除
        </a>,
        <Dropdown
          menu={{ items, onClick: (e) => handleMenuClick(e, record) }}
          key="more"
          placement="bottom"
        >
          <a onClick={(e) => e.preventDefault()}>
            <Space>
              更多操作
              <DownOutlined />
            </Space>
          </a>
        </Dropdown>
      ]
    }
  ];

  return (
    <div className={styles['template-container']}>
      <ProTable<MessageTemplate>
        columns={columns}
        rowSelection={{}}
        request={fetchTemplateData}
        tableAlertRender={({ selectedRowKeys, onCleanSelected }) => {
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
        tableAlertOptionRender={({ selectedRowKeys }) => {
          return (
            <Space size={16}>
              <a onClick={() => deleteTemplateData(selectedRowKeys as number[])}>批量删除</a>
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
          showTotal: (_) => `共 ${_} 条`,
          size: 'default'
        }}
        rowKey="templateId"
        headerTitle={
          <div style={{ width: '300px' }}>
            <Input
              placeholder="请输入模板名进行查询"
              style={{ borderRadius: '50px' }}
              prefix={<SearchOutlined />}
            />
          </div>
        }
        toolBarRender={() => [
          <>
            <Button
              key="add"
              type="primary"
              style={{ marginRight: '5px' }}
              onClick={() => addRef?.current?.addTemplateModal()}
            >
              新增
            </Button>
            <Button shape="circle" icon={<FilterOutlined />} />
          </>
        ]}
      />
      <DetailDrawer ref={detailRef} />
      <AddTemplateModal ref={addRef} onSubmit={addTemplate} />
      <TestSendDrawer ref={testRef} />
    </div>
  );
};

export default Template;

import React, { useRef, useState } from 'react';
import { ProColumns, ProTable } from '@ant-design/pro-components';
import { Button, Space, Input, Switch, Dropdown, MenuProps } from 'antd';
import { DownOutlined, FilterOutlined, SearchOutlined } from '@ant-design/icons';
import { MessageTemplate } from './type';
import { templateColumns, messageTableSchema } from './constant.tsx';
import useTemplateData from './useTemplateData';
import styles from './index.module.scss';
import DetailDrawer from '@/components/DetailDrawer/index.tsx';
import AddTemplateModal from './components/AddTemplateModal.tsx';
import TestSendDrawer from '@/components/TestSendDrawer/index.tsx';
import FilterCard from './components/FilterCard.tsx';

interface AddRef {
  addTemplateModal: () => void;
  editTemplateModal: (record: MessageTemplate) => void;
}

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

const Template: React.FC = () => {
  const detailRef = useRef<{ getDetail: (record: MessageTemplate) => void }>();
  const addRef = useRef<AddRef>();
  const testRef = useRef<{ getTestSendDrawer: (record: MessageTemplate) => void }>();
  const [tableParams, setTableParams] = useState({});
  const [filterOpen, setFilterOpen] = useState(false);
  const { fetchTemplateData, deleteTemplateData, addTemplate, changeStatus } = useTemplateData();

  const handleMenuClick = (e: any, record: MessageTemplate) => {
    if (e?.key === 'detail') {
      detailRef.current?.getDetail(record);
    } else {
      testRef.current?.getTestSendDrawer(record);
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
          onClick={() => changeStatus(record.templateId, record.templateStatus === 0 ? 1 : 0)}
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

  // 筛选处理函数
  const handleFilter = (filters: any) => {
    setTableParams(filters);
  };

  return (
    <div className={styles['template-container']}>
      <ProTable<MessageTemplate>
        style={{
          width: filterOpen ? 'calc(100% - 300px)' : '100%',
          transition: 'width 0.3s ease-in-out'
        }}
        params={tableParams}
        columns={columns}
        rowSelection={{}}
        // TODO: 这里到底传什么？入参为currentPage，反参是current？
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
            <Button
              shape="circle"
              icon={<FilterOutlined />}
              onClick={() => setFilterOpen((pre) => !pre)}
            />
          </>
        ]}
      />
      <DetailDrawer ref={detailRef} columns={templateColumns} />
      <AddTemplateModal ref={addRef} onSubmit={addTemplate} />
      <TestSendDrawer ref={testRef} />
      {filterOpen && (
        <div className={styles['filter-container']}>
          <FilterCard onClose={() => setFilterOpen(false)} onFilter={handleFilter} />
        </div>
      )}
    </div>
  );
};

export default Template;

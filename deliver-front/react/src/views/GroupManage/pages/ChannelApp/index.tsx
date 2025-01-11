import React, { useRef, useState } from 'react';
import { ProColumns, ProTable } from '@ant-design/pro-components';
import { Button, Space, Switch, Dropdown, MenuProps } from 'antd';
import { DownOutlined, FilterOutlined } from '@ant-design/icons';
import { ChannelApp } from './type.ts';
import { appColumns, appTableSchema } from './constant.tsx';
import useChannelData from './useChannelData.ts';
import styles from './index.module.scss';
import DetailDrawer from '@/components/DetailDrawer/index.tsx';
import FilterCard from './components/FilterCard.tsx';
import AddChannelDrawer from './components/AddChannelDrawer.tsx';
import { proTableConfig } from '@/config/index.tsx';

interface AddRef {
  addChannelDrawer: () => void;
  editChannelModal: (record: ChannelApp) => void;
}

const items: MenuProps['items'] = [
  {
    label: '查看详情',
    key: 'detail'
  }
];

const Channel: React.FC = () => {
  const detailRef = useRef<{ getDetail: (record: ChannelApp) => void }>();
  const addRef = useRef<AddRef>();
  const testRef = useRef<{ getTestSendDrawer: () => void }>();
  const [tableParams, setTableParams] = useState({});
  const [filterOpen, setFilterOpen] = useState(false);
  const { fetchChannelData, deleteChannelData, saveChannelData, changeStatus } = useChannelData();

  const handleMenuClick = (e: any, record: ChannelApp) => {
    if (e?.key === 'detail') {
      detailRef.current?.getDetail(record);
    } else {
      testRef.current?.getTestSendDrawer();
    }
  };

  // 这两列涉及到状态的改变，于是写在视图层
  const columns: ProColumns<ChannelApp>[] = [
    ...appTableSchema({
      title: '模板状态',
      width: 120,
      dataIndex: 'appStatus',
      render: (_, record: ChannelApp) => (
        <Switch
          checkedChildren="启用"
          unCheckedChildren="禁用"
          checked={Boolean(record?.appStatus)}
          onClick={() => changeStatus(record.appId, record.appStatus === 0 ? 1 : 0)}
        />
      )
    }),
    {
      title: '操作',
      width: 160,
      valueType: 'option',
      fixed: 'right',
      render: (_, record) => [
        <a
          className={styles['link-button']}
          key="edit"
          onClick={() => addRef?.current?.editChannelModal(record)}
        >
          编辑
        </a>,
        <a
          className={styles['link-button-delete']}
          key="delete"
          onClick={() => deleteChannelData([record?.appId])}
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
    <div className={styles['app-container']}>
      <ProTable
        params={tableParams}
        columns={columns}
        rowSelection={{}}
        // TODO: 这里到底传什么？入参为currentPage，反参是current？
        request={fetchChannelData}
        rowKey="appId"
        toolBarRender={() => [
          <>
            <Button
              key="add"
              type="primary"
              style={{ marginRight: '5px' }}
              onClick={() => addRef?.current?.addChannelDrawer()}
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
        {...proTableConfig({
          filterOpen,
          deleteData: deleteChannelData,
          name: '应用名'
        })}
      />
      <DetailDrawer ref={detailRef} columns={appColumns} />
      <AddChannelDrawer ref={addRef} onSubmit={saveChannelData} />
      {filterOpen && (
        <div className={styles['filter-container']}>
          <FilterCard onClose={() => setFilterOpen(false)} onFilter={handleFilter} />
        </div>
      )}
    </div>
  );
};

export default Channel;

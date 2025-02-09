import React, { MutableRefObject, useEffect, useRef, useState } from 'react';
import { ActionType, ProColumns, ProTable } from '@ant-design/pro-components';
import { Space, Switch, Dropdown, MenuProps, Pagination } from 'antd';
import { DownOutlined } from '@ant-design/icons';
import { ChannelApp } from './type.ts';
import { appColumns, appTableSchema } from './constant.tsx';
import useChannelData from './useChannelData.ts';
import styles from './index.module.scss';
import DetailDrawer from '@/components/DetailDrawer/index.tsx';
import FilterCard from './components/FilterCard.tsx';
import AddChannelDrawer from './components/AddChannelDrawer.tsx';
import { proTableConfig } from '@/config/index.tsx';
import ChannelCardView from './components/ChannelCardView.tsx';
import { useButtons } from './hooks/useButton.tsx';

interface AddRef {
  addChannelDrawer: () => void;
  editChannelModal: (record: ChannelApp) => void;
}

const items: MenuProps['items'] = [
  {
    label: '查看更多',
    key: 'detail'
  }
];

const Channel: React.FC = () => {
  const detailRef = useRef<{ getDetail: (record: ChannelApp) => void }>();
  const proTableRef = useRef<ActionType>();
  const addRef = useRef<AddRef>();
  const [tableParams, setTableParams] = useState<any>({});
  const [filterOpen, setFilterOpen] = useState(false);
  const [isTableView, setIsTableView] = useState(true);
  const [keys, setKeys] = useState<number>(0);

  const { fetchChannelData, deleteChannelData, saveChannelData, changeStatus } = useChannelData({
    proTableRef
  });

  // 新增状态用于存储和共享数据
  const [channelData, setChannelData] = useState<{
    records: ChannelApp[];
    total: number;
  }>({
    records: [],
    total: 0
  });

  const columns: ProColumns<ChannelApp>[] = [
    ...appTableSchema({
      title: '应用状态',
      width: 120,
      dataIndex: 'appStatus',
      render: (_, record: ChannelApp) => {
        const handleStatusChange = async (checked: boolean) => {
          await changeStatus(record.appId, checked ? 1 : 0);
          record.appStatus = checked ? 1 : 0;
          setKeys((prev) => prev + 1);
        };
        return (
          <Switch
            key={keys}
            checkedChildren="启用"
            unCheckedChildren="禁用"
            checked={Boolean(record?.appStatus)}
            onChange={handleStatusChange}
          />
        );
      }
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
          menu={{
            items,
            onClick: (e) => e.key === 'detail' && detailRef.current?.getDetail(record)
          }}
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

  // 卡片视图获取数据
  const handleFetchData = async (params: any) => {
    const data = await fetchChannelData(params);
    setChannelData({
      records: data.data as ChannelApp[],
      total: data.total as number
    });
    return data;
  };

  // Input搜索处理函数
  const handleSearch = (e: any) => {
    setTableParams?.((prev: any) => ({
      ...prev,
      appName: e?.target?.value
    }));
  };

  // 筛选处理函数
  const handleFilter = (filters: any) => {
    setTableParams(filters);
  };

  // 处理分页
  const handlePageChange = (params: { current: number; pageSize: number }) => {
    setTableParams((prev: any) => ({
      ...prev,
      current: params.current,
      pageSize: params.pageSize
    }));
  };

  // 表格上方按钮hook
  const { renderTableButtons, renderCardButtons } = useButtons({
    addRef,
    filterOpen,
    isTableView,
    setIsTableView,
    setFilterOpen,
    handleFilter,
    handleSearch
  });

  // 分页组件
  const SharedPagination = () => (
    <Pagination
      align="end"
      showSizeChanger
      showTotal={(total: number) => `共 ${total} 条`}
      total={channelData?.total}
      current={tableParams?.current || 1}
      pageSize={tableParams?.pageSize || 10}
      onChange={(page, pageSize) => handlePageChange({ current: page, pageSize })}
    />
  );

  useEffect(() => {
    if (!isTableView) {
      handleFetchData(tableParams);
    }
  }, [tableParams]);

  return (
    <div className={styles['app-container']}>
      {isTableView ? (
        <>
          <ProTable
            key={keys}
            actionRef={proTableRef}
            params={tableParams}
            columns={columns}
            rowSelection={{}}
            request={handleFetchData}
            rowKey="appId"
            scroll={{ x: 1200 }}
            toolBarRender={() => [renderTableButtons()]}
            {...proTableConfig({
              filterOpen,
              deleteData: deleteChannelData,
              name: '应用名',
              onSearch: setTableParams
            })}
            pagination={false}
            tableRender={(_, dom) => (
              <div>
                {dom}
                <div style={{ paddingRight: 24 }}>{SharedPagination()}</div>
              </div>
            )}
          />
        </>
      ) : (
        <ChannelCardView
          dataSource={channelData}
          filterOpen={filterOpen}
          onChangeStatus={changeStatus}
          renderCardButtons={renderCardButtons}
          onHandleActions={(action, data) => {
            switch (action) {
              case 'edit':
                addRef.current?.editChannelModal(data);
                break;
              case 'delete':
                deleteChannelData([data.appId]);
                break;
              case 'more':
                detailRef.current?.getDetail(data);
                break;
              case 'search':
                handleSearch(data);
                break;
              case 'filter':
                handleFilter(data);
                break;
            }
          }}
          renderCardPagination={SharedPagination}
        />
      )}
      <DetailDrawer ref={detailRef} columns={appColumns} title={'应用详情'} />
      <AddChannelDrawer
        ref={addRef}
        onSubmit={saveChannelData}
        reFresh={() => {
          (proTableRef as MutableRefObject<ActionType>)?.current?.reset?.();
        }}
      />
      {filterOpen && (
        <div className={styles['filter-container']}>
          <FilterCard onClose={() => setFilterOpen(false)} onFilter={handleFilter} />
        </div>
      )}
    </div>
  );
};

export default Channel;

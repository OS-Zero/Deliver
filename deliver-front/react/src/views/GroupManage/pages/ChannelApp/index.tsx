import React, { MutableRefObject, useRef, useState, useEffect } from 'react';
import { ActionType, ProColumns, ProTable } from '@ant-design/pro-components';
import { Button, Space, Switch, Dropdown, MenuProps, Card, Row, Col, Tooltip } from 'antd';
import {
  DownOutlined,
  FilterOutlined,
  MenuOutlined,
  AppstoreOutlined,
  EditOutlined,
  DeleteOutlined
} from '@ant-design/icons';
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
    label: '查看更多',
    key: 'detail'
  }
];

const Channel: React.FC = () => {
  const detailRef = useRef<{ getDetail: (record: ChannelApp) => void }>();
  const proTableRef = useRef<ActionType>();
  const addRef = useRef<AddRef>();
  const [tableParams, setTableParams] = useState({});
  const [filterOpen, setFilterOpen] = useState(false);
  const [isTableView, setIsTableView] = useState(false);
  const [channelData, setChannelData] = useState<ChannelApp[]>([]);
  const [keys, setKeys] = useState<number>(0);

  const { fetchChannelData, deleteChannelData, saveChannelData, changeStatus } = useChannelData({
    proTableRef
  });

  // 获取卡片数据
  useEffect(() => {
    const loadChannelData = async () => {
      const response = await fetchChannelData({
        currentPage: 1,
        pageSize: 100,
        ...tableParams
      });
      setChannelData(response.data || []);
    };
    loadChannelData();
  }, [tableParams]);

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

  // 筛选处理函数
  const handleFilter = (filters: any) => {
    setTableParams(filters);
  };

  return (
    <div className={styles['app-container']}>
      {isTableView && (
        <ProTable
          actionRef={proTableRef}
          params={tableParams}
          columns={columns}
          rowSelection={{}}
          request={fetchChannelData}
          rowKey="appId"
          scroll={{ x: 1200 }}
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
              <div style={{ marginLeft: '10px', display: 'inline-block' }}>
                <Tooltip title="表格视图">
                  <Button
                    icon={<MenuOutlined />}
                    size="small"
                    type={isTableView ? 'primary' : 'text'}
                    onClick={() => setIsTableView(true)}
                    style={{ marginRight: '5px' }}
                  />
                </Tooltip>
                <Tooltip title="卡片视图">
                  <Button
                    icon={<AppstoreOutlined />}
                    size="small"
                    type={!isTableView ? 'primary' : 'text'}
                    onClick={() => setIsTableView(false)}
                  />
                </Tooltip>
              </div>
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
            name: '应用名',
            onSearch: setTableParams
          })}
        />
      )}
      {!isTableView && (
        <Row gutter={[16, 16]} style={{ padding: '16px' }}>
          {channelData.map((record) => (
            <Col key={record.appId} xs={24} sm={12} md={8} lg={6}>
              <Card
                title={record.appName}
                extra={
                  <Switch
                    checkedChildren="启用"
                    unCheckedChildren="禁用"
                    checked={Boolean(record?.appStatus)}
                    onChange={(checked) => changeStatus(record.appId, checked ? 1 : 0)}
                  />
                }
                actions={[
                  <EditOutlined
                    key="edit"
                    onClick={() => addRef?.current?.editChannelModal(record)}
                  />,
                  <DeleteOutlined key="delete" onClick={() => deleteChannelData([record?.appId])} />
                ]}
              >
                <p>应用ID: {record.appId}</p>
                <p>描述: {record.appDescription}</p>
              </Card>
            </Col>
          ))}
        </Row>
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

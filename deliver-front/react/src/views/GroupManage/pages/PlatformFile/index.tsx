import React, { useRef, useState } from 'react';
import { ProColumns, ProTable } from '@ant-design/pro-components';
import { Button, Input, Switch } from 'antd';
import { FilterOutlined, SearchOutlined } from '@ant-design/icons';
import { PlatformFileDetail } from './type.ts';
import { platformColumns, platformTableSchema } from './constant.tsx';
import styles from './index.module.scss';
import DetailDrawer from '@/components/DetailDrawer/index.tsx';
import useFileData from './useFileData.ts';

interface AddRef {
  addChannelDrawer: () => void;
}

const PlatformFile: React.FC = () => {
  const detailRef = useRef<{ getDetail: (record: PlatformFileDetail) => void }>();
  const addRef = useRef<AddRef>();
  const [tableParams, setTableParams] = useState({});
  const [filterOpen, setFilterOpen] = useState(false);
  const { fetchPlatformData, changeStatus } = useFileData();

  // 这两列涉及到状态的改变，于是写在视图层
  const columns: ProColumns<PlatformFileDetail>[] = [
    ...platformTableSchema({
      title: '文件状态',
      width: 120,
      dataIndex: 'platformFileStatus',
      render: (_, record: PlatformFileDetail) => (
        <Switch
          checkedChildren="启用"
          unCheckedChildren="禁用"
          checked={Boolean(record?.platformFileStatus)}
          onClick={() => changeStatus(record.appId, record.platformFileStatus === 0 ? 1 : 0)}
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
          key="detail"
          onClick={() => detailRef.current?.getDetail(record)}
        >
          查看详情
        </a>
      ]
    }
  ];

  // 筛选处理函数
  const handleFilter = (filters: any) => {
    setTableParams(filters);
  };

  return (
    <div className={styles['app-container']}>
      <ProTable<PlatformFileDetail>
        style={{
          width: filterOpen ? 'calc(100% - 300px)' : '100%',
          transition: 'width 0.3s ease-in-out'
        }}
        params={tableParams}
        columns={columns}
        // TODO: 这里到底传什么？入参为currentPage，反参是current？
        request={fetchPlatformData}
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
        rowKey="appId"
        headerTitle={
          <div style={{ width: '200px' }}>
            <Input
              placeholder="请输入文件名进行查询"
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
      />
      <DetailDrawer ref={detailRef} columns={platformColumns} />
      {/* {filterOpen && (
        <div className={styles['filter-container']}>
          <FilterCard onClose={() => setFilterOpen(false)} onFilter={handleFilter} />
        </div>
      )} */}
    </div>
  );
};

export default PlatformFile;

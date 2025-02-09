import React, { MutableRefObject, useRef, useState } from 'react';
import { ActionType, ProColumns, ProTable } from '@ant-design/pro-components';
import { Button } from 'antd';
import { FilterOutlined } from '@ant-design/icons';
import { PlatformFileDetail } from './type.ts';
import { platformColumns, platformTableSchema } from './constant.tsx';
import styles from './index.module.scss';
import DetailDrawer from '@/components/DetailDrawer/index.tsx';
import useFileData from './useFileData.ts';
import AddFileDrawer from './components/AddFileDrawer.tsx';
import FilterCard from './components/FilterCard.tsx';
import { proTableConfig } from '@/config/index.tsx';

interface AddRef {
  addFileDrawer: () => void;
}

const PlatformFile: React.FC = () => {
  const detailRef = useRef<{ getDetail: (record: PlatformFileDetail) => void }>();
  const addRef = useRef<AddRef>();
  const proTableRef = useRef<ActionType>();
  const [tableParams, setTableParams] = useState({});
  const [filterOpen, setFilterOpen] = useState(false);
  const { fetchPlatformData, savePlatform } = useFileData();

  // 这两列涉及到状态的改变，于是写在视图层
  const columns: ProColumns<PlatformFileDetail>[] = [
    ...platformTableSchema,
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
          查看更多
        </a>
      ]
    }
  ];

  // 筛选处理函数
  const handleFilter = (filters: any) => {
    setTableParams(filters);
  };

  return (
    <div className={styles['file-container']}>
      <ProTable
        actionRef={proTableRef}
        params={tableParams}
        columns={columns}
        request={fetchPlatformData}
        rowKey="platformFileId"
        toolBarRender={() => [
          <>
            <Button
              key="add"
              type="primary"
              style={{ marginRight: '5px' }}
              onClick={() => addRef?.current?.addFileDrawer()}
            >
              上传
            </Button>
            <Button
              shape="circle"
              icon={<FilterOutlined />}
              onClick={() => {
                setFilterOpen((pre) => !pre);
                handleFilter({});
              }}
            />
          </>
        ]}
        {...proTableConfig({
          filterOpen,
          name: '文件名',
          onSearch: setTableParams
        })}
      />
      <AddFileDrawer
        ref={addRef}
        onSubmit={savePlatform}
        reFresh={() => {
          (proTableRef as MutableRefObject<ActionType>)?.current?.reset?.();
        }}
      />
      <DetailDrawer ref={detailRef} columns={platformColumns} title={'文件详情'} />
      {filterOpen && (
        <div className={styles['filter-container']}>
          <FilterCard onClose={() => setFilterOpen(false)} onFilter={handleFilter} />
        </div>
      )}
    </div>
  );
};

export default PlatformFile;

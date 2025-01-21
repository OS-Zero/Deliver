import React, { MutableRefObject, useRef, useState } from 'react';
import { ActionType, ProColumns, ProTable } from '@ant-design/pro-components';
import { Button, Space, Dropdown, MenuProps } from 'antd';
import { DownOutlined, FilterOutlined } from '@ant-design/icons';
import { peopleColumns, peopleTableSchema } from './constant.tsx';
import useChannelData from './usePeopleData.ts';
import styles from './index.module.scss';
import DetailDrawer from '@/components/DetailDrawer/index.tsx';
import FilterCard from './components/FilterCard.tsx';
import AddChannelDrawer from './components/AddPeopleDrawer.tsx';
import { proTableConfig } from '@/config/index.tsx';
import { PeopleGroup } from './type';
import { getExcelTemplateFile } from '@/api/peopleGroup.ts';

interface AddRef {
  addPeopleDrawer: () => void;
  editPeopleDrawer: (record: PeopleGroup) => void;
}

const items: MenuProps['items'] = [
  {
    label: '查看更多',
    key: 'detail'
  }
];

const People: React.FC = () => {
  const detailRef = useRef<{ getDetail: (record: PeopleGroup) => void }>();
  const proTableRef = useRef<ActionType>();
  const addRef = useRef<AddRef>();
  const [tableParams, setTableParams] = useState({});
  const [filterOpen, setFilterOpen] = useState(false);

  const { fetchPeopleData, deletePeopleData, savePeopleData } = useChannelData({
    proTableRef
  });

  const handleMenuClick = (e: any, record: PeopleGroup) => {
    if (e?.key === 'detail') {
      detailRef.current?.getDetail(record);
    }
  };

  // 这两列涉及到状态的改变，于是写在视图层
  const columns: ProColumns<PeopleGroup>[] = [
    ...peopleTableSchema,
    {
      title: '操作',
      width: 160,
      valueType: 'option',
      fixed: 'right',
      render: (_, record) => [
        <a
          className={styles['link-button']}
          key="edit"
          onClick={() => addRef?.current?.editPeopleDrawer(record)}
        >
          编辑
        </a>,
        <a
          className={styles['link-button-delete']}
          key="delete"
          onClick={() => deletePeopleData([record?.peopleGroupId])}
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
        actionRef={proTableRef}
        params={tableParams}
        columns={columns}
        rowSelection={{}}
        request={fetchPeopleData}
        rowKey="peopleGroupId"
        scroll={{ x: 1200 }}
        toolBarRender={() => [
          <>
            <Button key="add" style={{ marginRight: '5px' }} onClick={getExcelTemplateFile}>
              下载人群模版文件
            </Button>
            <Button
              key="add"
              type="primary"
              style={{ marginRight: '5px' }}
              onClick={() => addRef?.current?.addPeopleDrawer()}
            >
              新增
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
          deleteData: deletePeopleData,
          name: '人群名',
          onSearch: setTableParams
        })}
      />
      <DetailDrawer ref={detailRef} columns={peopleColumns} title={'人群详情'} />
      <AddChannelDrawer
        ref={addRef}
        onSubmit={savePeopleData}
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

export default People;

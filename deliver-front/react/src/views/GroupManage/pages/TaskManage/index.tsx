import React, { useRef, useState } from 'react';
import { ProColumns, ProTable } from '@ant-design/pro-components';
import { Button, Space, Switch, Dropdown, MenuProps } from 'antd';
import { DownOutlined, FilterOutlined } from '@ant-design/icons';
import { TaskDetail } from './type.ts';
import { taskColumns, taskTableSchema } from './constant.tsx';
import useChannelData from './useTaskData.ts';
import styles from './index.module.scss';
import DetailDrawer from '@/components/DetailDrawer/index.tsx';
// import FilterCard from './components/FilterCard.tsx';
import AddTaskDrawer from './components/AddTaskDrawer.tsx';
import { proTableConfig } from '@/config/index.tsx';
import FilterCard from './components/FilterCard.tsx';

interface AddRef {
  addTaskDrawer: () => void;
  editTaskModal: (record: TaskDetail) => void;
}

const items: MenuProps['items'] = [
  {
    label: '查看详情',
    key: 'detail'
  }
];

const TaskManage: React.FC = () => {
  const detailRef = useRef<{ getDetail: (record: TaskDetail) => void }>();
  const addRef = useRef<AddRef>();
  const [tableParams, setTableParams] = useState({});
  const [filterOpen, setFilterOpen] = useState(false);
  const { fetchTaskData, deleteTaskData, saveTaskData, changeStatus } = useChannelData();

  const handleMenuClick = (e: any, record: TaskDetail) => {
    if (e?.key === 'detail') {
      detailRef.current?.getDetail(record);
    } else {
      // testRef.current?.getTestSendDrawer();
    }
  };

  // 这两列涉及到状态的改变，于是写在视图层
  const columns: ProColumns<TaskDetail>[] = [
    ...taskTableSchema({
      title: '模板状态',
      width: 120,
      dataIndex: 'taskStatus',
      render: (_, record: TaskDetail) => (
        <Switch
          checkedChildren="启用"
          unCheckedChildren="禁用"
          checked={Boolean(record?.taskStatus)}
          onClick={() => changeStatus(record.taskId, record.taskStatus === 0 ? 1 : 0)}
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
          onClick={() => addRef?.current?.editTaskModal(record)}
        >
          编辑
        </a>,
        <a
          className={styles['link-button-delete']}
          key="delete"
          onClick={() => deleteTaskData([record?.taskId])}
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
    <div className={styles['task-container']}>
      <ProTable
        params={tableParams}
        columns={columns}
        rowSelection={{}}
        // TODO: 这里到底传什么？入参为currentPage，反参是current？
        request={fetchTaskData}
        rowKey="taskId"
        toolBarRender={() => [
          <>
            <Button
              key="add"
              type="primary"
              style={{ marginRight: '5px' }}
              onClick={() => addRef?.current?.addTaskDrawer()}
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
          deleteData: deleteTaskData,
          name: '任务名'
        })}
      />
      <DetailDrawer ref={detailRef} columns={taskColumns} />
      <AddTaskDrawer ref={addRef} onSubmit={saveTaskData} />
      {filterOpen && (
        <div className={styles['filter-container']}>
          <FilterCard onClose={() => setFilterOpen(false)} onFilter={handleFilter} />
        </div>
      )}
    </div>
  );
};

export default TaskManage;

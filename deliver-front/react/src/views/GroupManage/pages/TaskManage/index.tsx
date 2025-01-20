import React, { MutableRefObject, useRef, useState } from 'react';
import { ActionType, ProColumns, ProTable } from '@ant-design/pro-components';
import { Button, Space, Switch, Dropdown, MenuProps, message } from 'antd';
import { DownOutlined, FilterOutlined } from '@ant-design/icons';
import { TaskDetail } from './type.ts';
import { taskColumns, taskTableSchema } from './constant.tsx';
import styles from './index.module.scss';
import DetailDrawer from '@/components/DetailDrawer/index.tsx';
import AddTaskDrawer from './components/AddTaskDrawer.tsx';
import { proTableConfig } from '@/config/index.tsx';
import FilterCard from './components/FilterCard.tsx';
import useTaskData from './useTaskData.ts';

interface AddRef {
  addTaskDrawer: () => void;
  editTaskModal: (record: TaskDetail) => void;
}

const TaskManage: React.FC = () => {
  const proTableRef = useRef<ActionType>();
  const detailRef = useRef<{ getDetail: (record: TaskDetail) => void }>();
  const addRef = useRef<AddRef>();
  const [tableParams, setTableParams] = useState({});
  const [filterOpen, setFilterOpen] = useState(false);
  const { fetchTaskData, deleteTaskData, saveTaskData, changeStatus, sendTask } = useTaskData({
    proTableRef
  });

  const getMenuItems = (record: TaskDetail): MenuProps['items'] => {
    const baseItems = [
      {
        label: '查看更多',
        key: 'detail'
      }
    ];
    if (record.taskType === 1) {
      baseItems.push({
        label: '发送群发任务',
        key: 'send'
      });
    }
    return baseItems;
  };
  const handleMenuClick = async (e: any, record: TaskDetail) => {
    if (e?.key === 'detail') {
      detailRef.current?.getDetail(record);
    } else {
      try {
        await sendTask(record?.taskId);
        message.success('发送成功');
      } catch {
        message.error('发送失败');
      }
    }
  };

  // 这两列涉及到状态的改变，于是写在视图层
  const columns: ProColumns<TaskDetail>[] = [
    ...taskTableSchema({
      title: '任务状态',
      width: 120,
      dataIndex: 'taskStatus',
      render: (_, record: TaskDetail) => {
        const handleStatusChange = async (checked: boolean) => {
          await changeStatus(record.taskId, checked ? 1 : 0);
          proTableRef?.current?.reload();
        };
        return (
          <Switch
            checkedChildren="启用"
            unCheckedChildren="禁用"
            checked={Boolean(record?.taskStatus)}
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
          menu={{
            items: getMenuItems(record),
            onClick: (e) => handleMenuClick(e, record)
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
    <div className={styles['task-container']}>
      <ProTable
        actionRef={proTableRef}
        params={tableParams}
        columns={columns}
        rowSelection={{}}
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
          name: '任务名',
          onSearch: setTableParams
        })}
      />
      <DetailDrawer ref={detailRef} columns={taskColumns} title={'任务详情'} />
      <AddTaskDrawer
        ref={addRef}
        onSubmit={saveTaskData}
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

export default TaskManage;

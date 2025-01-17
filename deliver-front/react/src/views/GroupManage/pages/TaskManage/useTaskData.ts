import {
  saveTask,
  deleteTask,
  getTaskPages,
  updateTask,
  updateTaskStatus,
  sendRealTimeMessage
} from '@/api/task';
import deleteConfirmModal from '@/components/DeleteConfirmModal';
import { MutableRefObject, useCallback } from 'react';
import { TaskForm, TaskParams } from './type';
import { ActionType } from '@ant-design/pro-components';
import { transformParams } from '@/utils/omitProperty';

const useTaskData = (props: { proTableRef: MutableRefObject<ActionType | undefined> }) => {
  const { proTableRef } = props;
  /**
   * 搜索请求表单数据
   * @param data
   */
  const fetchTaskData = async (filter: TaskParams) => {
    const params = transformParams(filter);
    const res = await getTaskPages(params);
    return {
      data: res?.records,
      success: true,
      total: res?.total
    };
  };

  /**
   * 新增模版
   * @param data
   */
  const saveTaskData = async (params: TaskForm) =>
    params.taskId ? await updateTask(params) : await saveTask(params);

  /**
   * 更新模板状态
   * @param data
   */
  const changeStatus = async (taskId: number, taskStatus: number) => {
    await updateTaskStatus({ taskId, taskStatus });
  };

  /**
   * 发送群发任务
   * @param data
   */
  const sendTask = async (taskId: number) => {
    await sendRealTimeMessage({ taskId });
  };

  /**
   * 删除数据
   * @param id
   */
  const deleteTaskData = useCallback((ids: number[]) => {
    deleteConfirmModal({
      onConfirm: () => {
        deleteTask({ ids });
        (proTableRef as MutableRefObject<ActionType>)?.current?.reset?.();
      }
    });
  }, []);

  return {
    fetchTaskData,
    deleteTaskData,
    saveTaskData,
    changeStatus,
    sendTask
  };
};

export default useTaskData;

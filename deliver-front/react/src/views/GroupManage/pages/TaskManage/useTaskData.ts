import { saveTask, deleteTask, getTaskPages, updateTask, updateTaskStatus } from '@/api/task';
import deleteConfirmModal from '@/components/DeleteConfirmModal';
import { useCallback, useState } from 'react';
import { TaskForm, TaskParams } from './type';

const useTemplateData = () => {
  const [currentParams, setCurrentParams] = useState({ currentPage: 1, pageSize: 10 });
  /**
   * 搜索请求表单数据
   * @param data
   */
  const fetchTaskData = async (params: TaskParams) => {
    setCurrentParams(params);
    const res = await getTaskPages(params);
    return {
      data: res?.data?.records,
      success: true,
      total: res?.data?.total
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
   * 删除数据
   * @param id
   */
  const deleteTaskData = useCallback((ids: number[]) => {
    deleteConfirmModal({
      onConfirm: () => {
        deleteTask({ ids });
        fetchTaskData(currentParams);
      }
    });
  }, []);

  return {
    fetchTaskData,
    deleteTaskData,
    saveTaskData,
    changeStatus
  };
};

export default useTemplateData;

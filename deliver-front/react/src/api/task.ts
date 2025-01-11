import { R, SearchData } from '@/types';
import { TaskDetail, TaskForm, TaskParams } from '@/views/GroupManage/pages/TaskManage/type';
import request from '@/utils/request.ts';

export function getTaskPages(data: TaskParams): Promise<R<SearchData<TaskDetail>>> {
  return request({
    url: '/sendTask/search',
    method: 'post',
    data
  });
}

export function saveTask(data: TaskForm): Promise<never> {
  return request({
    url: '/sendTask/save',
    method: 'post',
    data
  });
}

export function updateTask(data: TaskForm): Promise<never> {
  return request({
    url: '/sendTask/update',
    method: 'post',
    data
  });
}

export function updateTaskStatus(data: Pick<TaskDetail, 'taskId' | 'taskStatus'>): Promise<never> {
  return request({
    url: '/sendTask/updateStatus',
    method: 'post',
    data
  });
}

export function deleteTask(data: { ids: Array<TaskDetail['taskId']> }): Promise<never> {
  return request({
    url: '/sendTask/delete',
    method: 'post',
    data
  });
}

export function sendRealTimeMessage(data: Pick<TaskDetail, 'taskId'>): Promise<never> {
  return request({
    url: '/sendTask/sendRealTimeMessage',
    method: 'post',
    data
  });
}

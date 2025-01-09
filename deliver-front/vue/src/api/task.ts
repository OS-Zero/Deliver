import { TableData } from '@/types';
import { SearchParams, Task, TaskForm } from '@/types/task';
import request from '@/utils/request.ts';

export function getTask(data: SearchParams): Promise<TableData<Task>> {
	return request({
		url: '/sendTask/search',
		method: 'post',
		data,
	});
}
export function saveTask(data: TaskForm): Promise<never> {
	return request({
		url: '/sendTask/save',
		method: 'post',
		data,
	});
}
export function updateTask(data: TaskForm): Promise<never> {
	return request({
		url: '/sendTask/update',
		method: 'post',
		data,
	});
}
export function updateTaskStatus(data: Pick<Task, 'taskId' | 'taskStatus'>): Promise<never> {
	return request({
		url: '/sendTask/updateStatus',
		method: 'post',
		data,
	});
}

export function deleteTask(data: { ids: Array<Task['taskId']> }): Promise<never> {
	return request({
		url: '/sendTask/delete',
		method: 'post',
		data,
	});
}
export function sendRealTimeMessage(data: Pick<Task, 'taskId'>): Promise<never> {
	return request({
		url: '/sendTask/sendRealTimeMessage',
		method: 'post',
		data,
	});
}

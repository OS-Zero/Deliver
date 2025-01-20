export interface TaskDetail {
  taskId: number;
  taskName: string;
  taskDescription: string;
  taskParam: string;
  taskType: number;
  taskStatus: number;
  peopleGroupId: number;
  peopleGroupName: string;
  createUser: string;
  createTime: string;
}

export interface TaskForm {
  taskId?: number;
  taskName: string;
  taskDescription: string;
  taskParam: string;
  taskType: number;
  peopleGroupId: number;
}

export interface TaskParams {
  taskName?: string;
  taskType?: number;
  taskStatus?: number;
  startTime?: string;
  endTime?: string;
  currentPage: number;
  pageSize: number;
}

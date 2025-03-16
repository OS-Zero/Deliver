import { TableSearchParams } from '.';
import { PeopleGroup } from './peopleGroup';

export interface Task extends Pick<PeopleGroup, 'peopleGroupId' | 'peopleGroupName'> {
	taskId: number;
	taskName: string;
	taskDescription: string;
	taskMessageParam: string;
	taskType: number;
	taskTimeExpression: string;
	templateId: number;
	templateName: string;
	peopleGroupId: number;
	peopleGroupName: string;
	taskStatus: number;
	createUser: string;
	createTime: string;
}
export interface SearchParams extends TableSearchParams, Pick<Partial<Task>, 'taskName' | 'taskType' | 'taskStatus'> {
	startTime?: string;
	endTime?: string;
}
export type TaskForm = Pick<
	Task,
	'taskId' | 'taskName' | 'taskDescription' | 'taskMessageParam' | 'taskType' | 'peopleGroupId' | 'taskTimeExpression' | 'templateId'
>;

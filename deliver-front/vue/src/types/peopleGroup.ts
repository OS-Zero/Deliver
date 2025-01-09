import { TableSearchParams } from '.';
import { User } from './messageTemplate';
export interface PeopleGroup extends User {
	peopleGroupId: number;
	peopleGroupName: string;
	peopleGroupDescription: string;
	peopleGroupList: string;
	createUser: string;
	createTime: string;
}
export interface SearchParams extends TableSearchParams, Pick<Partial<PeopleGroup>, 'peopleGroupName' | 'usersType'> {
	startTime?: string;
	endTime?: string;
}
export interface PeopleGroupForm
	extends Pick<PeopleGroup, 'peopleGroupId' | 'peopleGroupName' | 'peopleGroupDescription' | 'peopleGroupList' | 'usersType'> {
	excelTemplateFile: File;
}

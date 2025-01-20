import { Pagination } from '@/types';

export interface SearchParams
  extends Pagination,
    Pick<Partial<PeopleGroup>, 'peopleGroupName' | 'usersType'> {
  startTime?: string;
  endTime?: string;
}

export interface PeopleGroup {
	peopleGroupId: number;
	peopleGroupName: string;
	peopleGroupDescription: string;
	peopleGroupList: string;
	createUser: string;
	createTime: string;
  usersType: number;
  usersTypeName: string;
}

export type PeopleGroupForm = Pick<PeopleGroup, 'peopleGroupId' | 'peopleGroupName' | 'peopleGroupDescription' | 'peopleGroupList' | 'usersType'>;

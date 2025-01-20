import { R, SearchData } from '@/types';
import { PeopleGroup, SearchParams, PeopleGroupForm } from '@/views/GroupManage/pages/PeopleGroup/type';
import request from '@/utils/request.ts';

export function getPeopleGroup(data: SearchParams): Promise<R<SearchData<PeopleGroup>>> {
	return request({
		url: '/peopleGroup/search',
		method: 'post',
		data,
	});
}
export function savePeopleGroup(data: PeopleGroupForm): Promise<never> {
	return request({
		url: '/peopleGroup/save',
		method: 'post',
		data,
	});
}
export function updatePeopleGroup(data: PeopleGroupForm): Promise<never> {
	return request({
		url: '/peopleGroup/update',
		method: 'post',
		data,
	});
}

export function deletePeopleGroup(data: { ids: Array<PeopleGroup['peopleGroupId']> }): Promise<never> {
	return request({
		url: '/peopleGroup/delete',
		method: 'post',
		data,
	});
}
export function getExcelTemplateFile(): Promise<never> {
	return request({
		responseType: 'blob',
		url: '/peopleGroup/getExcelTemplateFile',
		method: 'post',
	});
}
export function analysisExcelTemplateFile(data: { file: File }): Promise<string> {
	return request({
		headers: {
			'Content-Type': 'multipart/form-data',
		},
		url: '/peopleGroup/analysisExcelTemplateFile',
		method: 'post',
		data,
	});
}
export function searchPeopleGroupByName(
	data: Pick<PeopleGroup, 'peopleGroupName'>,
): Promise<Array<Pick<PeopleGroup, 'peopleGroupId' | 'peopleGroupName'>>> {
	return request({
		url: '/peopleGroup/searchByName',
		method: 'post',
		data,
	});
}

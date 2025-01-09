import { TableData } from '@/types';
import { PeopleGroup, SearchParams, PeopleGroupForm } from '@/types/peopleGroup';
import request from '@/utils/request.ts';

export function getPeopleGroup(data: SearchParams): Promise<TableData<PeopleGroup>> {
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
		url: '/peopleGroup/getExcelTemplateFile',
		method: 'post',
	});
}
export function analysisExcelTemplateFile(data: { file: File }): Promise<string> {
	return request({
		url: '/peopleGroup/analysisExcelTemplateFile',
		method: 'post',
		data,
	});
}

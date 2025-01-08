import request from '@/utils/request.ts';
import { GroupCardList, GroupCard } from '@/types/group';

export function getGroupData(data: Pick<GroupCard, 'groupName'>): Promise<GroupCardList> {
	return request({
		url: '/group/getGroupData',
		method: 'post',
		data,
	});
}
export function addGroup(data: Pick<GroupCard, 'groupName' | 'groupDescription'>): Promise<never> {
	return request({
		url: '/group/save',
		method: 'post',
		data,
	});
}
export function updateGroup(data: Omit<GroupCard, 'updateTime'>): Promise<never> {
	return request({
		url: '/group/update',
		method: 'post',
		data,
	});
}
export function deleteGroup(data: { ids: GroupCard['groupId'][] }): Promise<never> {
	return request({
		url: '/group/delete',
		method: 'post',
		data,
	});
}
export function toTopGroup(data: Pick<GroupCard, 'groupId' | 'topUp'>): Promise<never> {
	return request({
		url: '/group/topUp',
		method: 'post',
		data,
	});
}

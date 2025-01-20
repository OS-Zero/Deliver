import { GroupCardList, GroupCard } from '@/views/GroupManage/type.ts';
import request from '@/utils/request.ts';

export function getGroupData(data: Pick<GroupCard, 'groupName'>): Promise<GroupCardList> {
  return request({
    url: '/group/getGroupData',
    method: 'post',
    data
  });
}

export function addGroup(data: Pick<GroupCard, 'groupName' | 'groupDescription'>): Promise<never> {
  return request({
    url: '/group/save',
    method: 'post',
    data
  });
}

export function updateGroup(data: Omit<GroupCard, 'updateTime'>): Promise<never> {
  return request({
    url: '/group/update',
    method: 'post',
    data
  });
}

export function deleteGroup(data: { ids: number[] }): Promise<never> {
  return request({
    url: '/group/delete',
    method: 'post',
    data
  });
}

export function toTopGroup(data: { groupId: number; topUp: number }): Promise<never> {
  return request({
    url: '/group/topUp',
    method: 'post',
    data
  });
}

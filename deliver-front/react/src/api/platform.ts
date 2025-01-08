import { R, SearchData } from '@/types';
import request from '@/utils/request.ts';
import { PlatformFileDetail, PlatformFileSearchParams } from '@/views/GroupManage/pages/PlatformFile/type';

/**
 * 模版分页查询
 * @param data 搜索框数据
 * @returns 返回模板数据
 */
export async function getPlatformPages(
  data: PlatformFileSearchParams
): Promise<R<SearchData<PlatformFileDetail>>> {
  return await request({
    url: '/platformFile/search',
    method: 'post',
    data
  });
}

export function saveChannelApp(data): Promise<never> {
  return request({
    url: '/channelApp/save',
    method: 'post',
    data
  });
}

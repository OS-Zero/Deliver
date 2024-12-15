import request from '@/utils/request.ts';

export async function getAppInfo(data): Promise<unknown> {
  return await request({
    url: '/hzh',
    method: 'post',
    data
  });
}

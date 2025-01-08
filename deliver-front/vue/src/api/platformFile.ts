import { TableData } from '@/types';
import { SearchParams } from '@/types/channelApp';
import { PlatformFile, UploadPlatformFile } from '@/types/platformFile';
import request from '@/utils/request.ts';

export async function getPlatformFile(data: SearchParams): Promise<TableData<PlatformFile>> {
	return await request({
		url: '/platformFile/search',
		method: 'post',
		data,
	});
}
export async function uploadPlatformFile(data: UploadPlatformFile): Promise<never> {
	return await request({
		url: '/platformFile/uploadFile',
		method: 'post',
		headers: {
			'Content-Type': 'multipart/form-data',
		},
		data,
	});
}

import request from '@/utils/request.ts'
import { searchPlatformFile } from '@/views/File/type'

export async function getPagePlatformFile(data: searchPlatformFile): Promise<any> {
	return await request({
		url: '/platformFile/getPagePlatformFile',
		method: 'post',
		data,
	})
}
export async function uploadFileApi(formData): Promise<any> {
	return await request({
		url: '/platformFile/uploadFile',
		method: 'post',
		data: formData,
	})
}

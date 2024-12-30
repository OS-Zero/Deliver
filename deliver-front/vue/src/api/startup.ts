import request from '@/utils/request.ts';

export function startup(): Promise<{
	currentLoginUserMenuList: Array<{
		menuName: string;
	}>;
	usersTypeParamList: Array<{
		userType: number;
		userTypeName: string;
	}>;
}> {
	return request({
		url: '/startup',
		method: 'post',
	});
}

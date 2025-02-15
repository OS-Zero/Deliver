import Mock from 'mockjs';

Mock.mock('/user/login', 'post', () => {
	return {
		code: 0,
		data: Mock.Random.string('abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789', 8, 16),
		errorMessage: null,
	};
});
Mock.mock('/user/logout', 'post', () => {
	return {
		code: 0,
		data: null,
		errorMessage: null,
	};
});

Mock.mock('/user/getCurrentLoginUserInfo', 'post', () => {
	return {
		code: 0,
		data: { userEmail: 'xxxx@qq.com', userRealName: 'admin', userRole: 'admin' },
		errorMessage: null,
	};
});

Mock.mock('/startup', 'post', () => {
	return {
		code: 0,
		data: { currentLoginUserMenuList: [{ menuName: '分组管理' }], usersTypeParamList: [{ usersType: 1, usersTypeName: '用户类型一' }] },
		errorMessage: null,
	};
});

Mock.mock('/group/getGroupData', 'post', () => {
	return {
		code: 0,
		data: Mock.mock({
			'defaultGroupList|1-10': [
				{
					'groupId|+1': 1,
					groupName: 'dsa1',
					groupDescription: '121',
					topUp: 0,
					updateTime: 'yyyyMMdd HH:MM:ss',
				},
			],
		}),
		errorMessage: null,
	};
});

Mock.mock('/messageTemplate/search', 'post', () => {
	return {
		code: 0,
		data: Mock.mock({
			'records|21': [
				{
					'templateId|+1': 1,
					templateName: '系统邮件模板',
					templateDescription: '这是系统邮件消息模板，用于发送系统消息',
					usersType: 2,
					usersTypeName: '邮箱',
					channelType: 3,
					channelTypeName: '邮件',
					channelProviderType: 1,
					channelProviderTypeName: '默认平台',
					messageType: '3-1-5',
					messageTypeName: '邮件-文本消息',
					templateStatus: 0,
					createUser: 'deliver',
					createTime: '2025-01-13 02:23:42',
					appId: 1,
					appName: '系统邮件',
				},
			],
			total: 21,
			current: 1,
			size: 10,
			pages: 1,
		}),
		errorMessage: null,
	};
});

Mock.mock('/channelApp/search', 'post', () => {
	return {
		code: 0,
		data: Mock.mock({
			'records|10': [
				{
					'appId|+1': 1,
					appName: '飞书消息推送',
					appDescription: 'xxxxx',
					channelType: 6,
					channelTypeName: '渠道类型一',
					channelProviderType: 6,
					channelProviderTypeName: '渠道供应商类型',
					appConfig: '{}',
					appStatus: 1,
					createUser: 'oszero',
					createTime: '2025-01-13 02:23:42',
				},
			],
			total: 10,
			current: 1,
			size: 10,
			pages: 1,
		}),
		errorMessage: null,
	};
});

Mock.mock('/platformFile/search', 'post', () => {
	return {
		code: 0,
		data: Mock.mock({
			'records|10': [
				{
					'platformFileId|+1': 1,
					platformFileName: 'cede',
					platformFileDescription: '平台文件描述',
					platformFileType: 'image',
					platformFileTypeName: 'image',
					platformFileKey: Mock.Random.string('abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789', 8, 16),
					platformFileStatus: 1,
					channelType: 3,
					channelTypeName: '渠道类型',
					appId: 1,
					appName: 'xxx',
					createUser: null,
					createTime: '2023-11-16 16:15:49',
				},
			],
			total: 10,
			current: 1,
			size: 10,
			pages: 1,
		}),
		errorMessage: null,
	};
});

Mock.mock('/sendTask/search', 'post', () => {
	return {
		code: 0,
		data: Mock.mock({
			'records|10': [
				{
					'taskId|+1': 8,
					taskName: '测试测试124',
					taskDescription: 'oszero每天起床',
					taskType: 3,
					taskTimeExpression: '2025-01-13 2:38:00',
					taskMessageParam: '{"title":"oszero每天起床","content":"兄弟们该起床啦，6点啦，太阳晒屁股了","htmlFlag":true}',
					taskStatus: 0,
					templateId: 1,
					templateName: '系统邮件模板',
					peopleGroupId: 1,
					peopleGroupName: 'oszero全体人群',
					createUser: 'deliver',
					createTime: '2025-01-13 02:37:20',
				},
			],
			total: 10,
			current: 1,
			size: 10,
			pages: 1,
		}),
		errorMessage: null,
	};
});

Mock.mock('/peopleGroup/search', 'post', () => {
	return {
		code: 0,
		data: Mock.mock({
			'records|10': [
				{
					'peopleGroupId|+1': 1,
					peopleGroupName: 'oszero全体人群',
					peopleGroupDescription: '这是一个oszero全体定时消息人群',
					peopleGroupList: 'XXXX@qq.com',
					usersType: 2,
					usersTypeName: '邮箱',
					createUser: 'deliver',
					createTime: '2025-01-13 02:14:00',
				},
			],
			total: 10,
			current: 1,
			size: 10,
			pages: 1,
		}),
		errorMessage: null,
	};
});

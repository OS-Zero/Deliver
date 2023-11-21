// @ts-ignore
/* eslint-disable */
import { request } from '@umijs/max';

/** 获取当前的用户 GET /api/currentUser */
export async function currentUser(options?: { [key: string]: any }) {
  return request<{
    data: API.CurrentUser;
  }>('/api/currentUser', {
    method: 'GET',
    ...(options || {}),
  });
}

/** 退出登录接口 POST /api/login/outLogin */
export async function outLogin(options?: { [key: string]: any }) {
  return request<Record<string, any>>('/api/login/outLogin', {
    method: 'POST',
    ...(options || {}),
  });
}

/** 登录接口 POST /api/login/account */
export async function login(body: API.LoginParams, options?: { [key: string]: any }) {
  return request<API.LoginResult>('/api/login/account', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 GET /api/notices */
export async function getNotices(options?: { [key: string]: any }) {
  return request<API.NoticeIconList>('/api/notices', {
    method: 'GET',
    ...(options || {}),
  });
}

/** 获取规则列表 GET /api/rule */
export async function rule(
  params: {
    // query
    /** 当前的页码 */
    current?: number;
    /** 页面的容量 */
    pageSize?: number;
  },
  options?: { [key: string]: any },
) {
  return request<API.RuleList>('/api/rule', {
    method: 'GET',
    params: {
      ...params,
    },
    ...(options || {}),
  });
}

/** 新建规则 PUT /api/rule */
export async function updateRule(options?: { [key: string]: any }) {
  return request<API.RuleListItem>('/api/rule', {
    method: 'PUT',
    ...(options || {}),
  });
}

/** 新建规则 POST /api/rule */
export async function addRule(options?: { [key: string]: any }) {
  return request<API.RuleListItem>('/api/rule', {
    method: 'POST',
    ...(options || {}),
  });
}

/** 删除规则 DELETE /api/rule */
export async function removeRule(options?: { [key: string]: any }) {
  return request<Record<string, any>>('/api/rule', {
    method: 'DELETE',
    ...(options || {}),
  });
}

/** 平台监控看板接口 **/
export async function getDashboardHeadData(options?: { [key: string]: any }) {
  return request<API.DashboardHeadData>('/admin/dashboard/getDashboardHeadData', {
    method: 'POST',
    ...(options || {}),
  });
}
export async function getMessageInfoReact(options?: API.DashboardInfoSelectRequest ) {
  return request<API.MessageInfo[]>('/admin/dashboard/getMessageInfo/react', {
    method: 'POST',
    data: {
      ...(options || {}),
    }
  });
}
export async function getTemplateInfo(options?: API.DashboardInfoSelectRequest ) {
  return request<API.DashboardInfo[]>('/admin/dashboard/getTemplateInfo', {
    method: 'POST',
    data: {
      ...(options || {}),
    }
  });
}
export async function getAppInfo(options?: API.DashboardInfoSelectRequest ) {
  return request<API.DashboardInfo[]>('/admin/dashboard/getAppInfo', {
    method: 'POST',
    data: {
      ...(options || {}),
    }
  });
}
export async function getPushUserInfo(options?: API.DashboardInfoSelectRequest ) {
  return request<API.DashboardInfo[]>('/admin/dashboard/getPushUserInfo', {
    method: 'POST',
    data: {
      ...(options || {}),
    }
  });
}






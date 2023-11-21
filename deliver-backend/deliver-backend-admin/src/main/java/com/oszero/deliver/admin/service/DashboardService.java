package com.oszero.deliver.admin.service;

import com.oszero.deliver.admin.model.dto.request.DashboardDateSelectRequestDto;
import com.oszero.deliver.admin.model.dto.response.DashboardHeadDataResponseDto;
import com.oszero.deliver.admin.model.dto.response.DashboardInfoResponseDto;
import com.oszero.deliver.admin.model.dto.response.MessageInfoResponseReactDto;
import com.oszero.deliver.admin.model.dto.response.MessageInfoResponseVueDto;

import java.util.List;

/**
 * 仪表盘 service
 *
 * @author oszero
 * @version 1.0.0
 */
public interface DashboardService {
    /**
     * 获取仪表盘头部数据
     *
     * @return 头部数据
     */
    DashboardHeadDataResponseDto getDashboardHeadData();

    /**
     * 获取消息详情 vue 版本
     *
     * @param dto 日期选择
     * @return 消息详情
     */
    MessageInfoResponseVueDto getMessageInfoVue(DashboardDateSelectRequestDto dto);

    /**
     * 获取消息详情 React 版本
     *
     * @param dto 日期选择
     * @return 消息详情
     */
    List<MessageInfoResponseReactDto> getMessageInfoReact(DashboardDateSelectRequestDto dto);

    /**
     * 获取模版详情
     *
     * @param dto 日期选择
     * @return 模版详情
     */
    DashboardInfoResponseDto getTemplateInfo(DashboardDateSelectRequestDto dto);

    /**
     * 获取 APP 详情
     *
     * @param dto 日期选择
     * @return APP 详情
     */
    DashboardInfoResponseDto getAppInfo(DashboardDateSelectRequestDto dto);

    /**
     * 获取推送用户详情
     *
     * @param dto 日期选择
     * @return 推送用户详情
     */
    DashboardInfoResponseDto getPushUserInfo(DashboardDateSelectRequestDto dto);
}

package com.oszero.deliver.admin.service;

import com.oszero.deliver.admin.model.dto.request.DashboardDateSelectRequestDto;
import com.oszero.deliver.admin.model.dto.response.*;

import java.util.List;

/**
 * 仪表盘 service
 *
 * @author oszero
 * @version 1.0.0
 */
public interface DashboardService {
    DashboardHeadDataResponseDto getDashboardHeadData();

    MessageInfoResponseVueDto getMessageInfoVue(DashboardDateSelectRequestDto dto);

    List<MessageInfoResponseReactDto> getMessageInfoReact(DashboardDateSelectRequestDto dto);

    DashboardInfoResponseDto getTemplateInfo(DashboardDateSelectRequestDto dto);

    DashboardInfoResponseDto getAppInfo(DashboardDateSelectRequestDto dto);

    DashboardInfoResponseDto getPushUserInfo(DashboardDateSelectRequestDto dto);
}

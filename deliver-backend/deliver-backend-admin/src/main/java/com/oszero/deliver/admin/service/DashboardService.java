package com.oszero.deliver.admin.service;

import com.oszero.deliver.admin.model.dto.request.DashboardDateSelectRequestDto;
import com.oszero.deliver.admin.model.dto.response.*;

/**
 * 仪表盘 service
 *
 * @author oszero
 * @version 1.0.0
 */
public interface DashboardService {
    DashboardHeadDataResponseDto getDashboardHeadData();

    MessageInfoResponseDto getMessageInfo(DashboardDateSelectRequestDto dto);

    DashboardInfoResponseDto getTemplateInfo(DashboardDateSelectRequestDto dto);

    DashboardInfoResponseDto getAppInfo(DashboardDateSelectRequestDto dto);

    DashboardInfoResponseDto getPushUserInfo(DashboardDateSelectRequestDto dto);
}

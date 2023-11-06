package com.oszero.deliver.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.oszero.deliver.admin.model.dto.request.DashboardDateSelectRequestDto;
import com.oszero.deliver.admin.model.dto.response.*;
import com.oszero.deliver.admin.model.entity.MessageRecord;
import com.oszero.deliver.admin.service.AppService;
import com.oszero.deliver.admin.service.DashboardService;
import com.oszero.deliver.admin.service.MessageRecordService;
import com.oszero.deliver.admin.service.TemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    private final MessageRecordService messageRecordService;
    private final TemplateService templateService;
    private final AppService appService;

    @Override
    public DashboardHeadDataResponseDto getDashboardHeadData() {

        // 获取当日的本地日期
        LocalDate today = LocalDate.now();

        // 获取当日的开始时间（凌晨）
        LocalDateTime startOfDay = today.atStartOfDay();

        // 获取当日的结束时间（最后一刻）
        LocalDateTime endOfDay = today.atTime(23, 59, 59, 999999999);

        // 将本地时间转换为ZonedDateTime以便处理时区信息
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime startOfDayZoned = startOfDay.atZone(zoneId);
        ZonedDateTime endOfDayZoned = endOfDay.atZone(zoneId);

        LambdaQueryWrapper<MessageRecord> wrapper = new LambdaQueryWrapper<>();
        wrapper.ge(MessageRecord::getCreateTime, startOfDayZoned).lt(MessageRecord::getCreateTime, endOfDayZoned);

        long messageCount = messageRecordService.count(wrapper);
        long platformFiles = 100;
        long templateCount = templateService.count();
        long appCount = appService.count();

        return DashboardHeadDataResponseDto.builder()
                .numberOfMessagesToday(String.valueOf(messageCount))
                .numberOfPlatformFiles(String.valueOf(platformFiles))
                .accumulatedTemplateOwnership(String.valueOf(templateCount))
                .numberOfApps(String.valueOf(appCount)).build();
    }

    @Override
    public MessageInfoResponseDto getMessageInfo(DashboardDateSelectRequestDto dto) {
        return null;
    }

    @Override
    public TemplateInfoResponseDto getTemplateInfo(DashboardDateSelectRequestDto dto) {
        return null;
    }

    @Override
    public AppInfoResponseDto getAppInfo(DashboardDateSelectRequestDto dto) {
        return null;
    }

    @Override
    public PushUserInfoResponseDto getPushUserInfo(DashboardDateSelectRequestDto dto) {
        return null;
    }
}

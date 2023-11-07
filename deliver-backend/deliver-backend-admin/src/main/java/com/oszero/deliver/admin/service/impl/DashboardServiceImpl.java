package com.oszero.deliver.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.oszero.deliver.admin.enums.DateSelectEnum;
import com.oszero.deliver.admin.exception.BusinessException;
import com.oszero.deliver.admin.model.dto.request.DashboardDateSelectRequestDto;
import com.oszero.deliver.admin.model.dto.response.*;
import com.oszero.deliver.admin.model.entity.MessageRecord;
import com.oszero.deliver.admin.service.AppService;
import com.oszero.deliver.admin.service.DashboardService;
import com.oszero.deliver.admin.service.MessageRecordService;
import com.oszero.deliver.admin.service.TemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.*;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 仪表盘 serviceImpl
 *
 * @author oszero
 * @version 1.0.0
 */
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
        Integer dateSelect = dto.getDateSelect();
        DateSelectEnum instanceByCode = DateSelectEnum.getInstanceByCode(dateSelect);
        MessageInfoResponseDto messageInfoResponseDto = new MessageInfoResponseDto();
        List<List<String>> messageInfoList = new ArrayList<>();
        if (Objects.isNull(instanceByCode)) {
            throw new BusinessException("传递的日期选择错误，在 1-4！！！");
        }
        // 进行具体处理
        switch (instanceByCode) {
            case DAY:
                // 初始化一个基础时间，当日凌晨
                LocalDateTime baseTime = LocalDateTime.now().with(LocalTime.MIDNIGHT);
                for (int i = 0; i < 6; i++) {
                    LocalDateTime startTime = baseTime.plusHours(i * 4); // 起始时间
                    LocalDateTime endTime = baseTime.plusHours((i + 1) * 4); // 结束时间

                    // 获取消息数量
                    long successCount = getMessageCountByTime(startTime, endTime, 1);
                    long failCount = getMessageCountByTime(startTime, endTime, 0);

                    // 组装返回值
                    List<String> ans = new ArrayList<>();
                    ans.add(i * 4 + "-" + (1 + i) * 4 + "h");
                    ans.add(String.valueOf(successCount));
                    ans.add(String.valueOf(failCount));
                    messageInfoList.add(ans);
                }
                break;
            case WEEK:
                for (int i = 0; i < 7; i++) {
                    // 获取开始结束时间
                    LocalDateTime weekStart = LocalDateTime.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)).plusDays(i * 7);
                    LocalDateTime weekEnd = weekStart.plusDays(7);

                    // 获取消息数量
                    long successCount = getMessageCountByTime(weekStart, weekEnd, 1);
                    long failCount = getMessageCountByTime(weekStart, weekEnd, 0);

                    // 组装返回值
                    List<String> ans = new ArrayList<>();
                    ans.add("Week " + (i + 1));
                    ans.add(String.valueOf(successCount));
                    ans.add(String.valueOf(failCount));
                    messageInfoList.add(ans);
                }
                break;
            case MONTH:
                // 初始化一个基础时间，当月的第一天与最后一天
                LocalDate baseDate = LocalDate.now().withDayOfMonth(1);
                LocalDate lastDate = LocalDate.now().with(TemporalAdjusters.lastDayOfMonth());

                for (int i = 0; i < 4; i++) {
                    LocalDate periodStart = baseDate.plusDays(i * 7);
                    LocalDate periodEnd = baseDate.plusDays((i + 1) * 7).isAfter(lastDate) ? lastDate : baseDate.plusDays((i + 1) * 7);

                    // 获取开始结束时间
                    LocalDateTime startTime = periodStart.atStartOfDay();
                    LocalDateTime endTime = periodEnd.atStartOfDay();

                    // 获取消息数量
                    long successCount = getMessageCountByTime(startTime, endTime, 1);
                    long failCount = getMessageCountByTime(startTime, endTime, 0);

                    // 组装返回值
                    List<String> ans = new ArrayList<>();
                    ans.add((i * 7 + 1) + "-" + (i + 1) * 7 + "日");
                    ans.add(String.valueOf(successCount));
                    ans.add(String.valueOf(failCount));
                    messageInfoList.add(ans);
                }
                // 处理不足 7 天的剩余部分
                if (lastDate.getDayOfMonth() - baseDate.plusDays(28).getDayOfMonth() > 0) {
                    LocalDate remainingStart = baseDate.plusDays(28);

                    LocalDateTime startTime = remainingStart.atStartOfDay();
                    LocalDateTime endTime = lastDate.atStartOfDay();

                    long successCount = getMessageCountByTime(startTime, endTime, 1);
                    long failCount = getMessageCountByTime(startTime, endTime, 0);

                    // 组装返回值
                    List<String> ans = new ArrayList<>();
                    ans.add(((4 * 7) + 1) + "-" + lastDate.getDayOfMonth() + "日");
                    ans.add(String.valueOf(successCount));
                    ans.add(String.valueOf(failCount));
                    messageInfoList.add(ans);
                }
                break;
            case YEAR:
                // 初始化一个基础时间，当年的第一个月的第一天
                LocalDate baseMDate = LocalDate.now().withMonth(1).withDayOfMonth(1);
                LocalDate lastMDate = baseMDate.plusYears(1);

                for (int i = 0; i < 6; i++) {
                    LocalDate periodStart = baseMDate.plusMonths(i * 2);
                    LocalDate periodEnd = baseMDate.plusMonths((i + 1) * 2).isAfter(lastMDate) ? lastMDate : baseMDate.plusMonths((i + 1) * 2);

                    // 获取开始结束时间
                    LocalDateTime startTime = periodStart.atStartOfDay();
                    LocalDateTime endTime = periodEnd.atStartOfDay();

                    // 获取消息数量
                    long successCount = getMessageCountByTime(startTime, endTime, 1);
                    long failCount = getMessageCountByTime(startTime, endTime, 0);

                    // 组装返回值
                    List<String> ans = new ArrayList<>();
                    ans.add(Month.of((i * 2) + 1).getValue() + "-" + Month.of((i + 1) * 2).getValue() + "月");
                    ans.add(String.valueOf(successCount));
                    ans.add(String.valueOf(failCount));
                    messageInfoList.add(ans);
                }
                break;
        }
        messageInfoResponseDto.setMessageInfoList(messageInfoList);
        return messageInfoResponseDto;
    }

    private Long getMessageCountByTime(LocalDateTime startTime, LocalDateTime endTime, Integer status) {
        LambdaQueryWrapper<MessageRecord> wrapper = new LambdaQueryWrapper<>();
        wrapper.ge(MessageRecord::getCreateTime, startTime)
                .lt(MessageRecord::getCreateTime, endTime)
                .eq(MessageRecord::getMessageStatus, status);
        return messageRecordService.count(wrapper);
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

package com.oszero.deliver.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.oszero.deliver.admin.enums.DateSelectEnum;
import com.oszero.deliver.admin.exception.BusinessException;
import com.oszero.deliver.admin.model.dto.request.DashboardDateSelectRequestDto;
import com.oszero.deliver.admin.model.dto.response.DashboardHeadDataResponseDto;
import com.oszero.deliver.admin.model.dto.response.DashboardInfoResponseDto;
import com.oszero.deliver.admin.model.dto.response.MessageInfoResponseReactDto;
import com.oszero.deliver.admin.model.dto.response.MessageInfoResponseVueDto;
import com.oszero.deliver.admin.model.entity.MessageRecord;
import com.oszero.deliver.admin.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.*;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
    private final PlatformFileService platformFileService;

    /**
     * 获取数据面板头数据
     *
     * @return dto
     */
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
        long platformFiles = platformFileService.count();
        long templateCount = templateService.count();
        long appCount = appService.count();

        return DashboardHeadDataResponseDto.builder()
                .numberOfMessagesToday(String.valueOf(messageCount))
                .numberOfPlatformFiles(String.valueOf(platformFiles))
                .accumulatedTemplateOwnership(String.valueOf(templateCount))
                .numberOfApps(String.valueOf(appCount)).build();
    }

    /**
     * 获取消息柱状图
     *
     * @param dto 日期选择
     * @return 柱状图信息
     */
    @Override
    public MessageInfoResponseVueDto getMessageInfoVue(DashboardDateSelectRequestDto dto) {
        Integer dateSelect = dto.getDateSelect();
        DateSelectEnum instanceByCode = DateSelectEnum.getInstanceByCode(dateSelect);
        MessageInfoResponseVueDto messageInfoResponseVueDto = new MessageInfoResponseVueDto();
        List<List<Object>> messageInfoList = new ArrayList<>();
        if (Objects.isNull(instanceByCode)) {
            throw new BusinessException("传递的日期选择错误，在 1-4！！！");
        }
        // 进行具体处理
        switch (instanceByCode) {
            case DAY: {
                // 初始化一个基础时间，当日凌晨
                LocalDateTime baseTime = LocalDateTime.now().with(LocalTime.MIDNIGHT);
                for (int i = 0; i < 6; i++) {
                    LocalDateTime startTime = baseTime.plusHours(i * 4); // 起始时间
                    LocalDateTime endTime = baseTime.plusHours((i + 1) * 4); // 结束时间

                    // 获取消息数量
                    Long successCount = getMessageCountByTime(startTime, endTime, 1);
                    Long failCount = getMessageCountByTime(startTime, endTime, 0);

                    // 组装返回值
                    List<Object> ans = new ArrayList<>();
                    ans.add(i * 4 + "-" + (1 + i) * 4 + "h");
                    ans.add(successCount);
                    ans.add(failCount);
                    messageInfoList.add(ans);
                }
                break;
            }
            case WEEK: {
                // 得到这周开始日
                LocalDateTime weekStart = LocalDateTime.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)).toLocalDate().atTime(0, 0, 0, 0);
                for (int i = 0; i < 7; i++) {
                    // 获取开始结束时间
                    LocalDateTime startTime = weekStart.plusDays(i);
                    LocalDateTime endTime = startTime.plusDays(1);

                    // 获取消息数量
                    Long successCount = getMessageCountByTime(startTime, endTime, 1);
                    Long failCount = getMessageCountByTime(startTime, endTime, 0);

                    // 组装返回值
                    List<Object> ans = new ArrayList<>();
                    ans.add("Week " + (i + 1));
                    ans.add(successCount);
                    ans.add(failCount);
                    messageInfoList.add(ans);
                }
                break;
            }
            case MONTH: {
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
                    Long successCount = getMessageCountByTime(startTime, endTime, 1);
                    Long failCount = getMessageCountByTime(startTime, endTime, 0);

                    // 组装返回值
                    List<Object> ans = new ArrayList<>();
                    ans.add((i * 7 + 1) + "-" + (i + 1) * 7 + "日");
                    ans.add(successCount);
                    ans.add(failCount);
                    messageInfoList.add(ans);
                }
                // 处理不足 7 天的剩余部分
                if (lastDate.getDayOfMonth() - baseDate.plusDays(28).getDayOfMonth() > 0) {
                    LocalDate remainingStart = baseDate.plusDays(28);

                    LocalDateTime startTime = remainingStart.atStartOfDay();
                    LocalDateTime endTime = lastDate.atStartOfDay();

                    Long successCount = getMessageCountByTime(startTime, endTime, 1);
                    Long failCount = getMessageCountByTime(startTime, endTime, 0);

                    // 组装返回值
                    List<Object> ans = new ArrayList<>();
                    ans.add(((4 * 7) + 1) + "-" + lastDate.getDayOfMonth() + "日");
                    ans.add(successCount);
                    ans.add(failCount);
                    messageInfoList.add(ans);
                }
                break;
            }
            case YEAR: {
                // 初始化一个基础时间，当年的第一个月的第一天
                LocalDate baseDate = LocalDate.now().withMonth(1).withDayOfMonth(1);
                LocalDate lastDate = baseDate.plusYears(1);

                for (int i = 0; i < 6; i++) {
                    LocalDate periodStart = baseDate.plusMonths(i * 2);
                    LocalDate periodEnd = baseDate.plusMonths((i + 1) * 2).isAfter(lastDate) ? lastDate : baseDate.plusMonths((i + 1) * 2);

                    // 获取开始结束时间
                    LocalDateTime startTime = periodStart.atStartOfDay();
                    LocalDateTime endTime = periodEnd.atStartOfDay();

                    // 获取消息数量
                    Long successCount = getMessageCountByTime(startTime, endTime, 1);
                    Long failCount = getMessageCountByTime(startTime, endTime, 0);

                    // 组装返回值
                    List<Object> ans = new ArrayList<>();
                    ans.add(Month.of((i * 2) + 1).getValue() + "-" + Month.of((i + 1) * 2).getValue() + "月");
                    ans.add(successCount);
                    ans.add(failCount);
                    messageInfoList.add(ans);
                }
                break;
            }
            default: {
            }
        }
        messageInfoResponseVueDto.setMessageInfoList(messageInfoList);
        return messageInfoResponseVueDto;
    }

    /**
     * 获取消息柱状图
     *
     * @param dto 日期选择
     * @return 柱状图信息
     */
    @Override
    public List<MessageInfoResponseReactDto> getMessageInfoReact(DashboardDateSelectRequestDto dto) {
        Integer dateSelect = dto.getDateSelect();
        DateSelectEnum instanceByCode = DateSelectEnum.getInstanceByCode(dateSelect);
        List<MessageInfoResponseReactDto> messageInfoResponseReactDtoList = new ArrayList<>();
        if (Objects.isNull(instanceByCode)) {
            throw new BusinessException("传递的日期选择错误，在 1-4！！！");
        }
        // 进行具体处理
        switch (instanceByCode) {
            case DAY: {
                // 初始化一个基础时间，当日凌晨
                LocalDateTime baseTime = LocalDateTime.now().with(LocalTime.MIDNIGHT);
                for (int i = 0; i < 6; i++) {
                    LocalDateTime startTime = baseTime.plusHours(i * 4); // 起始时间
                    LocalDateTime endTime = baseTime.plusHours((i + 1) * 4); // 结束时间

                    // 获取消息数量
                    final Long successCount = getMessageCountByTime(startTime, endTime, 1);
                    final Long failCount = getMessageCountByTime(startTime, endTime, 0);

                    // 组装返回值
                    MessageInfoResponseReactDto messageSuccessInfoResponseReactDto = new MessageInfoResponseReactDto();
                    messageSuccessInfoResponseReactDto.setName("成功");
                    messageSuccessInfoResponseReactDto.setTime(i * 4 + "-" + (1 + i) * 4 + "h");
                    messageSuccessInfoResponseReactDto.setCount(successCount);

                    MessageInfoResponseReactDto messageFailInfoResponseReactDto = new MessageInfoResponseReactDto();
                    messageFailInfoResponseReactDto.setName("失败");
                    messageFailInfoResponseReactDto.setTime(i * 4 + "-" + (1 + i) * 4 + "h");
                    messageFailInfoResponseReactDto.setCount(failCount);

                    messageInfoResponseReactDtoList.add(messageSuccessInfoResponseReactDto);
                    messageInfoResponseReactDtoList.add(messageFailInfoResponseReactDto);

                }
                break;
            }
            case WEEK: {
                // 得到这周开始日
                LocalDateTime weekStart = LocalDateTime.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)).toLocalDate().atTime(0, 0, 0, 0);
                for (int i = 0; i < 7; i++) {
                    // 获取开始结束时间
                    LocalDateTime startTime = weekStart.plusDays(i);
                    LocalDateTime endTime = startTime.plusDays(1);

                    // 获取消息数量
                    final Long successCount = getMessageCountByTime(startTime, endTime, 1);
                    final Long failCount = getMessageCountByTime(startTime, endTime, 0);

                    // 组装返回值
                    MessageInfoResponseReactDto messageSuccessInfoResponseReactDto = new MessageInfoResponseReactDto();
                    messageSuccessInfoResponseReactDto.setName("成功");
                    messageSuccessInfoResponseReactDto.setTime("Week " + (i + 1));
                    messageSuccessInfoResponseReactDto.setCount(successCount);

                    MessageInfoResponseReactDto messageFailInfoResponseReactDto = new MessageInfoResponseReactDto();
                    messageFailInfoResponseReactDto.setName("失败");
                    messageFailInfoResponseReactDto.setTime("Week " + (i + 1));
                    messageFailInfoResponseReactDto.setCount(failCount);

                    messageInfoResponseReactDtoList.add(messageSuccessInfoResponseReactDto);
                    messageInfoResponseReactDtoList.add(messageFailInfoResponseReactDto);
                }
                break;
            }
            case MONTH: {
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
                    final Long successCount = getMessageCountByTime(startTime, endTime, 1);
                    final Long failCount = getMessageCountByTime(startTime, endTime, 0);

                    // 组装返回值
                    MessageInfoResponseReactDto messageSuccessInfoResponseReactDto = new MessageInfoResponseReactDto();
                    messageSuccessInfoResponseReactDto.setName("成功");
                    messageSuccessInfoResponseReactDto.setTime((i * 7 + 1) + "-" + (i + 1) * 7 + "日");
                    messageSuccessInfoResponseReactDto.setCount(successCount);

                    MessageInfoResponseReactDto messageFailInfoResponseReactDto = new MessageInfoResponseReactDto();
                    messageFailInfoResponseReactDto.setName("失败");
                    messageFailInfoResponseReactDto.setTime((i * 7 + 1) + "-" + (i + 1) * 7 + "日");
                    messageFailInfoResponseReactDto.setCount(failCount);

                    messageInfoResponseReactDtoList.add(messageSuccessInfoResponseReactDto);
                    messageInfoResponseReactDtoList.add(messageFailInfoResponseReactDto);
                }
                // 处理不足 7 天的剩余部分
                if (lastDate.getDayOfMonth() - baseDate.plusDays(28).getDayOfMonth() > 0) {
                    LocalDate remainingStart = baseDate.plusDays(28);

                    LocalDateTime startTime = remainingStart.atStartOfDay();
                    LocalDateTime endTime = lastDate.atStartOfDay();

                    final Long successCount = getMessageCountByTime(startTime, endTime, 1);
                    final Long failCount = getMessageCountByTime(startTime, endTime, 0);

                    // 组装返回值
                    MessageInfoResponseReactDto messageSuccessInfoResponseReactDto = new MessageInfoResponseReactDto();
                    messageSuccessInfoResponseReactDto.setName("成功");
                    messageSuccessInfoResponseReactDto.setTime(((4 * 7) + 1) + "-" + lastDate.getDayOfMonth() + "日");
                    messageSuccessInfoResponseReactDto.setCount(successCount);

                    MessageInfoResponseReactDto messageFailInfoResponseReactDto = new MessageInfoResponseReactDto();
                    messageFailInfoResponseReactDto.setName("失败");
                    messageFailInfoResponseReactDto.setTime(((4 * 7) + 1) + "-" + lastDate.getDayOfMonth() + "日");
                    messageFailInfoResponseReactDto.setCount(failCount);

                    messageInfoResponseReactDtoList.add(messageSuccessInfoResponseReactDto);
                    messageInfoResponseReactDtoList.add(messageFailInfoResponseReactDto);
                }
                break;
            }
            case YEAR: {
                // 初始化一个基础时间，当年的第一个月的第一天
                LocalDate baseDate = LocalDate.now().withMonth(1).withDayOfMonth(1);
                LocalDate lastDate = baseDate.plusYears(1);

                for (int i = 0; i < 6; i++) {
                    LocalDate periodStart = baseDate.plusMonths(i * 2);
                    LocalDate periodEnd = baseDate.plusMonths((i + 1) * 2).isAfter(lastDate) ? lastDate : baseDate.plusMonths((i + 1) * 2);

                    // 获取开始结束时间
                    LocalDateTime startTime = periodStart.atStartOfDay();
                    LocalDateTime endTime = periodEnd.atStartOfDay();

                    // 获取消息数量
                    final Long successCount = getMessageCountByTime(startTime, endTime, 1);
                    final Long failCount = getMessageCountByTime(startTime, endTime, 0);

                    // 组装返回值
                    MessageInfoResponseReactDto messageSuccessInfoResponseReactDto = new MessageInfoResponseReactDto();
                    messageSuccessInfoResponseReactDto.setName("成功");
                    messageSuccessInfoResponseReactDto.setTime(Month.of((i * 2) + 1).getValue() + "-" + Month.of((i + 1) * 2).getValue() + "月");
                    messageSuccessInfoResponseReactDto.setCount(successCount);

                    MessageInfoResponseReactDto messageFailInfoResponseReactDto = new MessageInfoResponseReactDto();
                    messageFailInfoResponseReactDto.setName("失败");
                    messageFailInfoResponseReactDto.setTime(Month.of((i * 2) + 1).getValue() + "-" + Month.of((i + 1) * 2).getValue() + "月");
                    messageFailInfoResponseReactDto.setCount(failCount);

                    messageInfoResponseReactDtoList.add(messageSuccessInfoResponseReactDto);
                    messageInfoResponseReactDtoList.add(messageFailInfoResponseReactDto);
                }
                break;
            }
            default: {
            }
        }
        return messageInfoResponseReactDtoList;
    }

    /**
     * 通获取消息柱状图用操作
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @param status    状态
     * @return 数值
     */
    private Long getMessageCountByTime(LocalDateTime startTime, LocalDateTime endTime, Integer status) {
        LambdaQueryWrapper<MessageRecord> wrapper = new LambdaQueryWrapper<>();
        wrapper.ge(MessageRecord::getCreateTime, startTime)
                .lt(MessageRecord::getCreateTime, endTime)
                .eq(MessageRecord::getMessageStatus, status);
        return messageRecordService.count(wrapper);
    }

    /**
     * 获取模板饼状图信息
     *
     * @param dto 日期选择
     * @return 饼状图信息
     */
    @Override
    public DashboardInfoResponseDto getTemplateInfo(DashboardDateSelectRequestDto dto) {
        return getDashboardInfo(dto, 1);
    }

    /**
     * 获取 APP 饼状图信息
     *
     * @param dto 日期选择
     * @return 饼状图信息
     */
    @Override
    public DashboardInfoResponseDto getAppInfo(DashboardDateSelectRequestDto dto) {
        return getDashboardInfo(dto, 2);
    }

    /**
     * 获取推送用户饼状图信息
     *
     * @param dto 日期选择
     * @return 饼状图信息
     */
    @Override
    public DashboardInfoResponseDto getPushUserInfo(DashboardDateSelectRequestDto dto) {
        return getDashboardInfo(dto, 3);
    }

    /**
     * 获取饼状图通用方法 1
     *
     * @param dto  日期选择
     * @param type 什么饼状图 1-模板 2-APP 3-推送用户
     * @return 饼状图信息
     */
    private DashboardInfoResponseDto getDashboardInfo(DashboardDateSelectRequestDto dto, Integer type) {
        Integer dateSelect = dto.getDateSelect();
        DateSelectEnum instanceByCode = DateSelectEnum.getInstanceByCode(dateSelect);
        if (Objects.isNull(instanceByCode)) {
            throw new BusinessException("传递的日期选择错误，在 1-4！！！");
        }
        DashboardInfoResponseDto dashboardInfoResponseDto = new DashboardInfoResponseDto();
        switch (instanceByCode) {
            case DAY: {
                // 获取当日的本地日期
                LocalDate today = LocalDate.now();
                // 获取当日的开始时间（凌晨）
                LocalDateTime startOfDay = today.atStartOfDay();
                // 获取当日的结束时间（最后一刻）
                LocalDateTime endOfDay = today.atTime(23, 59, 59, 999999999);
                getInfo(dashboardInfoResponseDto, startOfDay, endOfDay, 5, type);
                break;
            }
            case WEEK: {
                // 获取开始结束时间
                LocalDateTime weekStart = LocalDateTime.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)).toLocalDate().atTime(0, 0, 0, 0);
                LocalDateTime weekEnd = weekStart.plusDays(7).toLocalDate().atTime(23, 59, 59, 999);
                getInfo(dashboardInfoResponseDto, weekStart, weekEnd, 5, type);
                break;
            }
            case MONTH: {
                // 初始化一个基础时间，当月的第一天与最后一天
                LocalDateTime baseDate = LocalDate.now().withDayOfMonth(1).atTime(0, 0, 0, 0);
                LocalDateTime lastDate = LocalDate.now().with(TemporalAdjusters.lastDayOfMonth()).atTime(23, 59, 59, 999999999);
                getInfo(dashboardInfoResponseDto, baseDate, lastDate, 5, type);
                break;
            }
            case YEAR: {
                // 初始化一个基础时间，当年的第一个月的第一天
                LocalDateTime baseDate = LocalDate.now().withMonth(1).withDayOfMonth(1).atTime(0, 0, 0, 0);
                LocalDateTime lastDate = baseDate.with(TemporalAdjusters.lastDayOfYear()).toLocalDate().atTime(23, 59, 59, 999999999);
                getInfo(dashboardInfoResponseDto, baseDate, lastDate, 5, type);
                break;
            }
            default: {
            }
        }
        // 进行具体处理
        return dashboardInfoResponseDto;
    }

    /**
     * 获取饼状图通用方法 2
     *
     * @param dashboardInfoResponseDto 饼状图信息类
     * @param startTime                开始时间
     * @param endTime                  结束时间
     * @param size                     TOP 几 大小
     * @param type                     类型 什么饼状图 1-模板 2-APP 3-推送用户
     */
    private void getInfo(DashboardInfoResponseDto dashboardInfoResponseDto, LocalDateTime startTime, LocalDateTime endTime, Integer size, Integer type) {
        List<MessageRecord> messageRecordList;
        List<DashboardInfoResponseDto.DashboardInfo> collect = new ArrayList<>();
        if (1 == type) {
            messageRecordList = messageRecordService.getTemplateInfo(startTime, endTime, size);
            collect = messageRecordList.stream().map(messageRecord -> {
                DashboardInfoResponseDto.DashboardInfo dashboardInfo = new DashboardInfoResponseDto.DashboardInfo();
                dashboardInfo.setName("templateId:" + messageRecord.getTemplateId());
                dashboardInfo.setValue(messageRecord.getValue());
                return dashboardInfo;
            }).collect(Collectors.toList());
        } else if (2 == type) {
            messageRecordList = messageRecordService.getAppInfo(startTime, endTime, size);
            collect = messageRecordList.stream().map(messageRecord -> {
                DashboardInfoResponseDto.DashboardInfo dashboardInfo = new DashboardInfoResponseDto.DashboardInfo();
                dashboardInfo.setName("appId:" + messageRecord.getAppId());
                dashboardInfo.setValue(messageRecord.getValue());
                return dashboardInfo;
            }).collect(Collectors.toList());
        } else if (3 == type) {
            messageRecordList = messageRecordService.getPushUserInfo(startTime, endTime, size);
            collect = messageRecordList.stream().map(messageRecord -> {
                DashboardInfoResponseDto.DashboardInfo dashboardInfo = new DashboardInfoResponseDto.DashboardInfo();
                dashboardInfo.setName("userId:" + messageRecord.getPushUser());
                dashboardInfo.setValue(messageRecord.getValue());
                return dashboardInfo;
            }).collect(Collectors.toList());
        }

        dashboardInfoResponseDto.setDashboardInfoList(collect);
    }
}

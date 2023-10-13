package com.oszero.deliver.server.web.service.impl;

import cn.hutool.json.JSONUtil;
import com.oszero.deliver.server.enums.PushRangeEnum;
import com.oszero.deliver.server.exception.BusinessException;
import com.oszero.deliver.server.model.dto.PushWayDto;
import com.oszero.deliver.server.model.dto.SendTaskDto;
import com.oszero.deliver.server.model.dto.request.SendRequestDto;
import com.oszero.deliver.server.model.entity.Template;
import com.oszero.deliver.server.web.service.AppService;
import com.oszero.deliver.server.web.service.SendService;
import com.oszero.deliver.server.web.service.TemplateAppService;
import com.oszero.deliver.server.web.service.TemplateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class SendServiceImpl implements SendService {


    private final TemplateService templateService;
    private final AppService appService;
    private final TemplateAppService templateAppService;

    @Override
    public Integer send(SendRequestDto sendRequestDto) {
        log.info("s");
        Integer templateId = sendRequestDto.getTemplateId();
        Template template = templateService.getById(templateId);
        if (Objects.isNull(template)) {
            throw new BusinessException("");
        }
        List<String> users = sendRequestDto.getUsers();
        Map<String, String> paramMap = sendRequestDto.getParamMap();
        Integer retry = sendRequestDto.getRetry();
        PushRangeEnum pushRangeEnum = PushRangeEnum.findByCode(template.getPushRange());
        Integer usersType = template.getUsersType();
        List<PushWayDto> pushWayDtoList = JSONUtil.toList(template.getPushWays(), PushWayDto.class);

        // 前置用户类型校验
        SendTaskDto.SendTaskDtoBuilder sendTaskDto = SendTaskDto.builder()
                .users(users)
                .usersType(usersType);


        return null;
    }
}

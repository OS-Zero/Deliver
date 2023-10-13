package com.oszero.deliver.server.web.service.impl;

import cn.hutool.json.JSONUtil;
import com.oszero.deliver.server.enums.PushRangeEnum;
import com.oszero.deliver.server.enums.UsersTypeEnum;
import com.oszero.deliver.server.exception.BusinessException;
import com.oszero.deliver.server.message.producer.Producer;
import com.oszero.deliver.server.model.dto.PushWayDto;
import com.oszero.deliver.server.model.dto.SendTaskDto;
import com.oszero.deliver.server.model.dto.req.SendReqDto;
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
    public Integer send(SendReqDto sendReqDto) {
        log.info("s");
        Integer templateId = sendReqDto.getTemplateId();
        Template template = templateService.getById(templateId);
        if (Objects.isNull(template)) {
            throw new BusinessException("");
        }
        List<String> users = sendReqDto.getUsers();
        Map<String, String> paramMap = sendReqDto.getParamMap();
        Integer retry = sendReqDto.getRetry();
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

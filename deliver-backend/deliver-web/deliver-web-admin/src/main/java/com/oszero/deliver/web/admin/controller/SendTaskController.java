/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.oszero.deliver.web.admin.controller;

import com.oszero.deliver.business.admin.model.dto.request.common.DeleteIdsRequestDto;
import com.oszero.deliver.business.admin.model.dto.request.sendtask.*;
import com.oszero.deliver.business.admin.model.dto.response.common.SearchResponseDto;
import com.oszero.deliver.business.admin.model.dto.response.sendtask.SendTaskSearchResponseDto;
import com.oszero.deliver.business.admin.service.SendTaskService;
import com.oszero.deliver.business.common.model.common.CommonResult;
import com.oszero.deliver.web.admin.constant.AdminPathConstant;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.quartz.SchedulerException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

/**
 * @author oszero
 * @version 1.0.0
 */
@RestController
@RequiredArgsConstructor
public class SendTaskController {

    private final SendTaskService sendTaskService;

    @PostMapping(AdminPathConstant.SEND_TASK_SEARCH)
    public CommonResult<SearchResponseDto<SendTaskSearchResponseDto>> search(@RequestBody SendTaskSearchRequestDto dto) {
        return CommonResult.success(sendTaskService.search(dto));
    }

    @PostMapping(AdminPathConstant.SEND_TASK_SAVE)
    public CommonResult<Void> save(@Valid @RequestBody SendTaskSaveRequestDto dto) throws SchedulerException, ParseException {
        sendTaskService.save(dto);
        return CommonResult.success();
    }

    @PostMapping(AdminPathConstant.SEND_TASK_UPDATE)
    public CommonResult<Void> update(@Valid @RequestBody SendTaskUpdateRequestDto dto) throws SchedulerException, ParseException {
        sendTaskService.update(dto);
        return CommonResult.success();
    }

    @PostMapping(AdminPathConstant.SEND_TASK_UPDATE_STATUS)
    public CommonResult<Void> updateStatus(@Valid @RequestBody SendTaskUpdateStatusRequestDto dto) throws SchedulerException {
        sendTaskService.updateStatus(dto);
        return CommonResult.success();
    }

    @PostMapping(AdminPathConstant.SEND_TASK_DELETE)
    public CommonResult<Void> delete(@Valid @RequestBody DeleteIdsRequestDto dto) throws SchedulerException {
        sendTaskService.delete(dto);
        return CommonResult.success();
    }

    @PostMapping(AdminPathConstant.SEND_TASK_SEND_REAL_TIME_MESSAGE)
    public CommonResult<Void> sendRealTimeMessage(@Valid @RequestBody SendTaskSendMessageRequestDto dto) {
        sendTaskService.sendRealTimeMessage(dto);
        return CommonResult.success();
    }

}

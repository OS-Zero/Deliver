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
import com.oszero.deliver.business.admin.model.dto.request.messagetemplate.*;
import com.oszero.deliver.business.admin.model.dto.response.common.SearchResponseDto;
import com.oszero.deliver.business.admin.model.dto.response.messagetemplate.MessageTemplateSearchResponseDto;
import com.oszero.deliver.business.admin.service.MessageTemplateService;
import com.oszero.deliver.business.common.model.common.CommonResult;
import com.oszero.deliver.web.admin.constant.AdminPathConstant;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author oszero
 * @version 1.0.0
 */
@RestController
@RequiredArgsConstructor
public class MessageTemplateController {

    private final MessageTemplateService messageTemplateService;

    @PostMapping(AdminPathConstant.MESSAGE_TEMPLATE_SEARCH)
    public CommonResult<SearchResponseDto<MessageTemplateSearchResponseDto>> search(@Valid @RequestBody MessageTemplateSearchRequestDto templateSearchRequestDto) {
        return CommonResult.success(messageTemplateService.search(templateSearchRequestDto));
    }

    @PostMapping(AdminPathConstant.MESSAGE_TEMPLATE_SAVE)
    public CommonResult<Void> save(@Valid @RequestBody MessageTemplateSaveRequestDto dto) {
        messageTemplateService.save(dto);
        return CommonResult.success();
    }

    @PostMapping(AdminPathConstant.MESSAGE_TEMPLATE_UPDATE)
    public CommonResult<Void> update(@Valid @RequestBody MessageTemplateUpdateRequestDto dto) {
        messageTemplateService.update(dto);
        return CommonResult.success();
    }

    @PostMapping(AdminPathConstant.MESSAGE_TEMPLATE_UPDATE_STATUS)
    public CommonResult<Void> updateStatus(@Valid @RequestBody MessageTemplateUpdateStatusRequestDto dto) {
        messageTemplateService.updateStatus(dto);
        return CommonResult.success();
    }

    @PostMapping(AdminPathConstant.MESSAGE_TEMPLATE_DELETE)
    public CommonResult<Void> delete(@Valid @RequestBody DeleteIdsRequestDto dto) {
        messageTemplateService.delete(dto);
        return CommonResult.success();
    }

    @PostMapping(AdminPathConstant.MESSAGE_TEMPLATE_TEST_SEND_MESSAGE)
    public CommonResult<Void> testSendMessage(@Valid @RequestBody SendMessageRequestDto sendMessageRequestDto) {
        messageTemplateService.testSendMessage(sendMessageRequestDto);
        return CommonResult.success();
    }

    @PostMapping(AdminPathConstant.MESSAGE_TEMPLATE_GET_MESSAGE_PARAM)
    public CommonResult<String> getMessageParam(@Valid @RequestBody GetMessageParamRequestDto dto) {
        return CommonResult.success(messageTemplateService.getMessageParam(dto));
    }

}

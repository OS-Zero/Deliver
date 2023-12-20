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

package com.oszero.deliver.server.pretreatment.link.convert;

import com.oszero.deliver.server.exception.MessageException;
import com.oszero.deliver.server.model.dto.common.SendTaskDto;
import com.oszero.deliver.server.pretreatment.constant.PretreatmentCodeConstant;
import com.oszero.deliver.server.pretreatment.common.MessageLink;
import com.oszero.deliver.server.pretreatment.common.LinkContext;
import com.oszero.deliver.server.util.MessageLinkTraceUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 企业账号转换
 *
 * @author oszero
 * @version 1.0.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CompanyAccountConvert implements MessageLink<SendTaskDto> {

    private final CompanyAccount2Phone companyAccount2Phone;
    private final CompanyAccount2Mail companyAccount2Mail;

    private static final Map<String, String> codeUpdateMap = new HashMap<>();

    static {
        codeUpdateMap.put(PretreatmentCodeConstant.COMPANY_ACCOUNT_DING, PretreatmentCodeConstant.PHONE_DING);
        codeUpdateMap.put(PretreatmentCodeConstant.COMPANY_ACCOUNT_WECHAT, PretreatmentCodeConstant.PHONE_WECHAT);
        codeUpdateMap.put(PretreatmentCodeConstant.COMPANY_ACCOUNT_FEI_SHU, PretreatmentCodeConstant.PHONE_FEI_SHU);
    }

    @Override
    public void process(LinkContext<SendTaskDto> context) {
        String code = context.getCode();
        SendTaskDto sendTaskDto = context.getProcessModel();

        if (Objects.isNull(companyAccount2Phone) || Objects.isNull(companyAccount2Mail)) {
            throw new MessageException(sendTaskDto, "[CompanyAccountConvert#process]错误：请注入[CompanyAccount2Phone]、[CompanyAccount2Mail]的实现");
        }

        List<String> users = sendTaskDto.getUsers();
        // 企业账号转换
        if (PretreatmentCodeConstant.COMPANY_ACCOUNT_MAIL.equals(code)) {
            sendTaskDto.setUsers(companyAccount2Mail.convert(users));

            MessageLinkTraceUtils.recordMessageLifecycleInfoLog(sendTaskDto, "完成企业账号转换邮箱");
        } else {
            sendTaskDto.setUsers(companyAccount2Phone.convert(users));
            // 修改 code 码，以便于手机号转换平台 ID
            context.setCode(codeUpdateMap.get(code));

            MessageLinkTraceUtils.recordMessageLifecycleInfoLog(sendTaskDto, "完成企业账号转换手机号");
        }
    }

    public interface CompanyAccount2Phone {
        List<String> convert(List<String> companyAccountList);
    }

    public interface CompanyAccount2Mail {
        List<String> convert(List<String> companyAccountList);
    }
}

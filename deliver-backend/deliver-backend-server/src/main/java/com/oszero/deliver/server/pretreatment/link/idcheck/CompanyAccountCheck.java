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

package com.oszero.deliver.server.pretreatment.link.idcheck;

import com.oszero.deliver.server.exception.MessageException;
import com.oszero.deliver.server.model.dto.common.SendTaskDto;
import com.oszero.deliver.server.pretreatment.common.MessageLink;
import com.oszero.deliver.server.pretreatment.common.LinkContext;
import com.oszero.deliver.server.util.MessageLinkTraceUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * 企业账号检查
 *
 * @author oszero
 * @version 1.0.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CompanyAccountCheck implements MessageLink<SendTaskDto> {

    private final CheckCompanyAccount checkCompanyAccount;

    @Override
    public void process(LinkContext<SendTaskDto> context) {
        SendTaskDto sendTaskDto = context.getProcessModel();

        if (Objects.isNull(checkCompanyAccount)) {
            throw new MessageException(sendTaskDto, "[CompanyAccountCheck#process]错误：请注入[CheckCompanyAccount]实现");
        }

        // 企业账号检查
        sendTaskDto.getUsers().forEach(companyAccount -> checkCompanyAccount.check(companyAccount, sendTaskDto));

        MessageLinkTraceUtils.recordMessageLifecycleInfoLog(sendTaskDto, "完成企业账号检查");
    }

    /**
     * 企业账号检查
     */
    public interface CheckCompanyAccount {
        /**
         * 检查企业账号
         *
         * @param companyAccount 企业账号
         * @param sendTaskDto    发送任务
         */
        void check(String companyAccount, SendTaskDto sendTaskDto);
    }
}

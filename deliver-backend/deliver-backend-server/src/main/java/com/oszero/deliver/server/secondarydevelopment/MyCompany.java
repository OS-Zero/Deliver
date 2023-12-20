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

package com.oszero.deliver.server.secondarydevelopment;

import com.oszero.deliver.server.exception.MessageException;
import com.oszero.deliver.server.model.dto.common.SendTaskDto;
import com.oszero.deliver.server.pretreatment.link.convert.CompanyAccountConvert;
import com.oszero.deliver.server.pretreatment.link.idcheck.CompanyAccountCheck;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 我的企业二次开发配置
 *
 * @author oszero
 * @version 1.0.0
 */
@Configuration
public class MyCompany {

    /**
     * 企业账号真实性检测
     *
     * @author oszero
     * @version 1.0.0
     */
    @Component
    static class MyCheckCompanyAccount implements CompanyAccountCheck.CheckCompanyAccount {
        @Override
        public void check(String companyAccount, SendTaskDto sendTaskDto) {
            if (!"oszero".equals(companyAccount)) {
                throw new MessageException(sendTaskDto, "非法的企业账号");
            }
        }
    }

    /**
     * 企业账号转换电话
     *
     * @author oszero
     * @version 1.0.0
     */
    @Component
    static class MyCompanyAccount2Phone implements CompanyAccountConvert.CompanyAccount2Phone {

        @Override
        public List<String> convert(List<String> companyAccountList) {
            return companyAccountList.stream().map(this::convert).collect(Collectors.toList());
        }

        /**
         * 企业账号转换手机号码
         *
         * @param companyAccount 企业账号
         * @return 手机号码
         */
        private String convert(String companyAccount) {
            // 编写适配自己企业的转换逻辑
            return "";
        }
    }

    /**
     * 企业账号转换邮箱
     *
     * @author oszero
     * @version 1.0.0
     */
    @Component
    static class MyCompanyAccount2Mail implements CompanyAccountConvert.CompanyAccount2Mail {

        @Override
        public List<String> convert(List<String> companyAccountList) {
            return companyAccountList.stream().map(this::convert).collect(Collectors.toList());
        }

        /**
         * 企业账号转换邮箱
         *
         * @param companyAccount 企业账号
         * @return 邮箱
         */
        private String convert(String companyAccount) {
            // 编写适配自己企业的转换逻辑
            return "";
        }
    }
}

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

package com.oszero.deliver.business.server.pretreatment.common;

import com.oszero.deliver.business.server.pretreatment.constant.PretreatmentCodeConstant;
import com.oszero.deliver.business.server.pretreatment.link.convert.Phone2UserIdConvert;
import com.oszero.deliver.business.server.pretreatment.link.idcheck.EMailCheck;
import com.oszero.deliver.business.server.pretreatment.link.idcheck.PhoneCheck;
import com.oszero.deliver.business.server.pretreatment.link.paramhandle.call.CallParamCheck;
import com.oszero.deliver.business.server.pretreatment.link.paramhandle.ding.DingParamCheck;
import com.oszero.deliver.business.server.pretreatment.link.paramhandle.ding.DingParamSetting;
import com.oszero.deliver.business.server.pretreatment.link.paramhandle.feishu.FeiShuParamCheck;
import com.oszero.deliver.business.server.pretreatment.link.paramhandle.feishu.FeiShuParamSetting;
import com.oszero.deliver.business.server.pretreatment.link.paramhandle.mail.MailParamCheck;
import com.oszero.deliver.business.server.pretreatment.link.paramhandle.sms.SmsParamCheck;
import com.oszero.deliver.business.server.pretreatment.link.paramhandle.wechat.WeChatParamCheck;
import com.oszero.deliver.business.server.pretreatment.link.paramhandle.wechat.WeChatParamSetting;
import com.oszero.deliver.business.server.pretreatment.link.send.*;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.HashMap;

/**
 * @author oszero
 * @version 1.0.0
 */
@Configuration
@RequiredArgsConstructor
public class LinkConfig {

    /********** idCheck 发送人检查 **********/
    private final EMailCheck eMailCheck;
    private final PhoneCheck phoneCheck;

    /********** convert 转换 **********/
    private final Phone2UserIdConvert phone2UserIdConvert;

    /********** 参数处理 **********/
    private final CallParamCheck callParamCheck;
    private final SmsParamCheck smsParamCheck;
    private final MailParamCheck mailParamCheck;
    private final DingParamCheck dingParamCheck;
    private final WeChatParamCheck weChatParamCheck;
    private final FeiShuParamCheck feiShuParamCheck;
    private final DingParamSetting dingParamSetting;
    private final WeChatParamSetting weChatParamSetting;
    private final FeiShuParamSetting feiShuParamSetting;

    /********** send 渠道发送 **********/
    private final CallSend callSend;
    private final SmsSend smsSend;
    private final MailSend mailSend;
    private final WeChatSend weChatSend;
    private final DingSend dingSend;
    private final FeiShuSend feiShuSend;

    /**
     * @return 前置处理责任链处理器
     */
    @Bean
    public LinkHandler processHandler() {
        HashMap<String, LinkTemplate> map = new HashMap<>();
        // 电话
        phoneUsersType(map);
        // 邮箱
        mailUsersType(map);
        // 平台ID
        userIdUsersType(map);
        return new LinkHandler().setTemplateConfig(map);
    }

    /**
     * 手机号配置
     *
     * @param map 配置 map
     */
    private void phoneUsersType(HashMap<String, LinkTemplate> map) {
        // 电话-打电话
        LinkTemplate phone2CallTemplate = new LinkTemplate();
        phone2CallTemplate.setProcessList(Arrays.asList(
                phoneCheck, callParamCheck, callSend
        ));
        map.put(PretreatmentCodeConstant.PHONE_CALL, phone2CallTemplate);
        // 电话-发短信
        LinkTemplate phone2SmsTemplate = new LinkTemplate();
        phone2SmsTemplate.setProcessList(Arrays.asList(
                phoneCheck, smsParamCheck, smsSend
        ));
        map.put(PretreatmentCodeConstant.PHONE_SMS, phone2SmsTemplate);
        // 电话-钉钉
        LinkTemplate phone2DingTemplate = new LinkTemplate();
        phone2DingTemplate.setProcessList(Arrays.asList(
                phoneCheck, dingParamCheck, dingParamSetting, phone2UserIdConvert, dingSend
        ));
        map.put(PretreatmentCodeConstant.PHONE_DING, phone2DingTemplate);
        // 电话-企业微信
        LinkTemplate phone2WeChatTemplate = new LinkTemplate();
        phone2WeChatTemplate.setProcessList(Arrays.asList(
                phoneCheck, weChatParamCheck, weChatParamSetting, phone2UserIdConvert, weChatSend
        ));
        map.put(PretreatmentCodeConstant.PHONE_WECHAT, phone2WeChatTemplate);
        // 电话-飞书
        LinkTemplate phone2FeiShuTemplate = new LinkTemplate();
        phone2FeiShuTemplate.setProcessList(Arrays.asList(
                phoneCheck, feiShuParamCheck, feiShuParamSetting, phone2UserIdConvert, feiShuSend
        ));
        map.put(PretreatmentCodeConstant.PHONE_FEI_SHU, phone2FeiShuTemplate);
    }

    /**
     * 邮箱配置
     *
     * @param map 配置 map
     */
    private void mailUsersType(HashMap<String, LinkTemplate> map) {
        // 邮箱-发邮件
        LinkTemplate mail2MailTemplate = new LinkTemplate();
        mail2MailTemplate.setProcessList(Arrays.asList(
                eMailCheck, mailParamCheck, mailSend
        ));
        map.put(PretreatmentCodeConstant.EMAIL_MAIL, mail2MailTemplate);
    }

    /**
     * 平台ID配置
     *
     * @param map 配置 map
     */
    private void userIdUsersType(HashMap<String, LinkTemplate> map) {
        // 平台userId-钉钉
        LinkTemplate userId2DingTemplate = new LinkTemplate();
        userId2DingTemplate.setProcessList(Arrays.asList(
                dingParamCheck, dingParamSetting, dingSend
        ));
        map.put(PretreatmentCodeConstant.USERID_DING, userId2DingTemplate);

        // 平台userId-企业微信
        LinkTemplate userId2WeChatTemplate = new LinkTemplate();
        userId2WeChatTemplate.setProcessList(Arrays.asList(
                weChatParamCheck ,weChatParamSetting, weChatSend
        ));
        map.put(PretreatmentCodeConstant.USERID_WECHAT, userId2WeChatTemplate);

        // 平台userId-飞书
        LinkTemplate userId2FeiShuTemplate = new LinkTemplate();
        userId2FeiShuTemplate.setProcessList(Arrays.asList(
                feiShuParamCheck, feiShuParamSetting, feiShuSend
        ));
        map.put(PretreatmentCodeConstant.USERID_FEI_SHU, userId2FeiShuTemplate);
    }
}

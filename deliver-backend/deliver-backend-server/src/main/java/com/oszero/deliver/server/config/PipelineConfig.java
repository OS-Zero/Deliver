package com.oszero.deliver.server.config;

import com.oszero.deliver.server.pretreatment.action.idcheck.*;
import com.oszero.deliver.server.pretreatment.action.paramcheck.*;
import com.oszero.deliver.server.pretreatment.action.send.*;
import com.oszero.deliver.server.pretreatment.constant.PretreatmentCodeConstant;
import com.oszero.deliver.server.pretreatment.pipeline.ProcessHandler;
import com.oszero.deliver.server.pretreatment.pipeline.ProcessTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.HashMap;

@Configuration
@RequiredArgsConstructor
public class PipelineConfig {

    /**
     * idCheck
     */

    private final CompanyAccountCheckAction companyAccountCheckAction;
    private final MailCheckAction mailCheckAction;
    private final PhoneCheckAction phoneCheckAction;
    private final DingUserIdCheckAction dingUserIdCheckAction;
    private final WeChatUserIdCheckAction weChatUserIdCheckAction;
    private final FeiShuUserIdCheckAction feiShuUserIdCheckAction;

    /**
     * paramCheck
     */
    private final PhoneParamCheck phoneParamCheck;
    private final SmsParamCheck smsParamCheck;
    private final MailParamCheck mailParamCheck;
    private final DingParamCheck dingParamCheck;
    private final WeChatParamCheck weChatParamCheck;
    private final FeiShuParamCheck feiShuParamCheck;

    /**
     * send
     */
    private final CallSendAction callSendAction;
    private final SmsSendAction smsSendAction;
    private final MailSendAction mailSendAction;
    private final WeChatSendAction weChatSendAction;
    private final DingSendAction dingSendAction;
    private final FeiShuSendAction feiShuSendAction;


    @Bean
    public ProcessHandler processHandler() {

        HashMap<String, ProcessTemplate> map = new HashMap<>();

        // 电话-打电话
        ProcessTemplate phone2CallTemplate = new ProcessTemplate();
        phone2CallTemplate.setProcessList(Arrays.asList(
                phoneCheckAction, phoneParamCheck, callSendAction
        ));
        map.put(PretreatmentCodeConstant.PHONE_CALL, phone2CallTemplate);

        // 电话-发短信
        ProcessTemplate phone2SmsTemplate = new ProcessTemplate();
        phone2SmsTemplate.setProcessList(Arrays.asList(
                phoneCheckAction, smsParamCheck, smsSendAction
        ));
        map.put(PretreatmentCodeConstant.PHONE_SMS, phone2SmsTemplate);

        // 邮箱-发邮件
        ProcessTemplate mail2MailTemplate = new ProcessTemplate();
        mail2MailTemplate.setProcessList(Arrays.asList(
                mailCheckAction, mailParamCheck, mailSendAction
        ));
        map.put(PretreatmentCodeConstant.MAIL_MAIL, mail2MailTemplate);

        // 电话-钉钉
        ProcessTemplate phone2DingTemplate = new ProcessTemplate();
        phone2DingTemplate.setProcessList(Arrays.asList(
                phoneCheckAction, dingParamCheck, dingSendAction
        ));
        map.put(PretreatmentCodeConstant.PHONE_DING, phone2DingTemplate);

        // 电话-企业微信
        ProcessTemplate phone2WeChatTemplate = new ProcessTemplate();
        phone2WeChatTemplate.setProcessList(Arrays.asList(
                phoneCheckAction, weChatParamCheck, weChatSendAction
        ));
        map.put(PretreatmentCodeConstant.PHONE_WECHAT, phone2WeChatTemplate);

        // 电话-飞书
        ProcessTemplate phone2FeiShuTemplate = new ProcessTemplate();
        phone2FeiShuTemplate.setProcessList(Arrays.asList(
                phoneCheckAction, feiShuParamCheck, feiShuSendAction
        ));
        map.put(PretreatmentCodeConstant.PHONE_FEI_SHU, phone2FeiShuTemplate);

        // 企业账号-钉钉
        ProcessTemplate companyAccount2DingTemplate = new ProcessTemplate();
        companyAccount2DingTemplate.setProcessList(Arrays.asList(
                companyAccountCheckAction, dingParamCheck, dingSendAction
        ));
        map.put(PretreatmentCodeConstant.COMPANY_ACCOUNT_DING, companyAccount2DingTemplate);

        // 企业账号-企业微信
        ProcessTemplate companyAccount2WeChatTemplate = new ProcessTemplate();
        companyAccount2WeChatTemplate.setProcessList(Arrays.asList(
                companyAccountCheckAction, weChatParamCheck, weChatSendAction
        ));
        map.put(PretreatmentCodeConstant.COMPANY_ACCOUNT_WECHAT, companyAccount2WeChatTemplate);

        // 企业账号-飞书
        ProcessTemplate companyAccount2FeiShuTemplate = new ProcessTemplate();
        companyAccount2FeiShuTemplate.setProcessList(Arrays.asList(
                companyAccountCheckAction, feiShuParamCheck, feiShuSendAction
        ));
        map.put(PretreatmentCodeConstant.COMPANY_ACCOUNT_FEI_SHU, companyAccount2FeiShuTemplate);

        // 平台userId-钉钉
        ProcessTemplate userId2DingTemplate = new ProcessTemplate();
        userId2DingTemplate.setProcessList(Arrays.asList(
                dingUserIdCheckAction, dingParamCheck, dingSendAction
        ));
        map.put(PretreatmentCodeConstant.USERID_DING, userId2DingTemplate);

        // 平台userId-企业微信
        ProcessTemplate userId2WeChatTemplate = new ProcessTemplate();
        userId2WeChatTemplate.setProcessList(Arrays.asList(
                weChatUserIdCheckAction, weChatParamCheck, weChatSendAction
        ));
        map.put(PretreatmentCodeConstant.USERID_WECHAT, userId2WeChatTemplate);

        // 平台userId-飞书
        ProcessTemplate userId2FeiShuTemplate = new ProcessTemplate();
        userId2FeiShuTemplate.setProcessList(Arrays.asList(
                feiShuUserIdCheckAction, feiShuParamCheck, feiShuSendAction
        ));
        map.put(PretreatmentCodeConstant.USERID_FEI_SHU, userId2FeiShuTemplate);

        return new ProcessHandler().setTemplateConfig(map);
    }
}

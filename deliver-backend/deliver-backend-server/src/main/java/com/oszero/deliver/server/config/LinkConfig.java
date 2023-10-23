package com.oszero.deliver.server.config;

import com.oszero.deliver.server.pretreatment.link.idcheck.*;
import com.oszero.deliver.server.pretreatment.link.paramcheck.ding.DingParamCheck;
import com.oszero.deliver.server.pretreatment.link.paramcheck.feishu.FeiShuParamCheck;
import com.oszero.deliver.server.pretreatment.link.paramcheck.mail.MailParamCheck;
import com.oszero.deliver.server.pretreatment.link.paramcheck.phone.PhoneParamCheck;
import com.oszero.deliver.server.pretreatment.link.paramcheck.sms.SmsParamCheck;
import com.oszero.deliver.server.pretreatment.link.paramcheck.wechat.WeChatParamCheck;
import com.oszero.deliver.server.pretreatment.link.send.*;
import com.oszero.deliver.server.pretreatment.constant.PretreatmentCodeConstant;
import com.oszero.deliver.server.pretreatment.link.LinkHandler;
import com.oszero.deliver.server.pretreatment.link.LinkTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.HashMap;

/**
 * 前置责任链配置
 *
 * @author oszero
 * @version 1.0.0
 */
@Configuration
@RequiredArgsConstructor
public class LinkConfig {

    /**
     * idCheck
     */

    private final CompanyAccountCheck companyAccountCheck;
    private final MailCheck mailCheck;
    private final PhoneCheck phoneCheck;
    private final DingUserIdCheck dingUserIdCheck;
    private final WeChatUserIdCheck weChatUserIdCheck;
    private final FeiShuUserIdCheck feiShuUserIdCheck;

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
    private final CallSend callSend;
    private final SmsSend smsSendAction;
    private final MailSend mailSendAction;
    private final WeChatSend weChatSendAction;
    private final DingSend dingSendAction;
    private final FeiShuSend feiShuSend;


    @Bean
    public LinkHandler processHandler() {

        HashMap<String, LinkTemplate> map = new HashMap<>();

        // 电话-打电话
        LinkTemplate phone2CallTemplate = new LinkTemplate();
        phone2CallTemplate.setProcessList(Arrays.asList(
                phoneCheck, phoneParamCheck, callSend
        ));
        map.put(PretreatmentCodeConstant.PHONE_CALL, phone2CallTemplate);

        // 电话-发短信
        LinkTemplate phone2SmsTemplate = new LinkTemplate();
        phone2SmsTemplate.setProcessList(Arrays.asList(
                phoneCheck, smsParamCheck, smsSendAction
        ));
        map.put(PretreatmentCodeConstant.PHONE_SMS, phone2SmsTemplate);

        // 邮箱-发邮件
        LinkTemplate mail2MailTemplate = new LinkTemplate();
        mail2MailTemplate.setProcessList(Arrays.asList(
                mailCheck, mailParamCheck, mailSendAction
        ));
        map.put(PretreatmentCodeConstant.MAIL_MAIL, mail2MailTemplate);

        // 电话-钉钉
        LinkTemplate phone2DingTemplate = new LinkTemplate();
        phone2DingTemplate.setProcessList(Arrays.asList(
                phoneCheck, dingParamCheck, dingSendAction
        ));
        map.put(PretreatmentCodeConstant.PHONE_DING, phone2DingTemplate);

        // 电话-企业微信
        LinkTemplate phone2WeChatTemplate = new LinkTemplate();
        phone2WeChatTemplate.setProcessList(Arrays.asList(
                phoneCheck, weChatParamCheck, weChatSendAction
        ));
        map.put(PretreatmentCodeConstant.PHONE_WECHAT, phone2WeChatTemplate);

        // 电话-飞书
        LinkTemplate phone2FeiShuTemplate = new LinkTemplate();
        phone2FeiShuTemplate.setProcessList(Arrays.asList(
                phoneCheck, feiShuParamCheck, feiShuSend
        ));
        map.put(PretreatmentCodeConstant.PHONE_FEI_SHU, phone2FeiShuTemplate);

        // 企业账号-钉钉
        LinkTemplate companyAccount2DingTemplate = new LinkTemplate();
        companyAccount2DingTemplate.setProcessList(Arrays.asList(
                companyAccountCheck, dingParamCheck, dingSendAction
        ));
        map.put(PretreatmentCodeConstant.COMPANY_ACCOUNT_DING, companyAccount2DingTemplate);

        // 企业账号-企业微信
        LinkTemplate companyAccount2WeChatTemplate = new LinkTemplate();
        companyAccount2WeChatTemplate.setProcessList(Arrays.asList(
                companyAccountCheck, weChatParamCheck, weChatSendAction
        ));
        map.put(PretreatmentCodeConstant.COMPANY_ACCOUNT_WECHAT, companyAccount2WeChatTemplate);

        // 企业账号-飞书
        LinkTemplate companyAccount2FeiShuTemplate = new LinkTemplate();
        companyAccount2FeiShuTemplate.setProcessList(Arrays.asList(
                companyAccountCheck, feiShuParamCheck, feiShuSend
        ));
        map.put(PretreatmentCodeConstant.COMPANY_ACCOUNT_FEI_SHU, companyAccount2FeiShuTemplate);

        // 平台userId-钉钉
        LinkTemplate userId2DingTemplate = new LinkTemplate();
        userId2DingTemplate.setProcessList(Arrays.asList(
                dingUserIdCheck, dingParamCheck, dingSendAction
        ));
        map.put(PretreatmentCodeConstant.USERID_DING, userId2DingTemplate);

        // 平台userId-企业微信
        LinkTemplate userId2WeChatTemplate = new LinkTemplate();
        userId2WeChatTemplate.setProcessList(Arrays.asList(
                weChatUserIdCheck, weChatParamCheck, weChatSendAction
        ));
        map.put(PretreatmentCodeConstant.USERID_WECHAT, userId2WeChatTemplate);

        // 平台userId-飞书
        LinkTemplate userId2FeiShuTemplate = new LinkTemplate();
        userId2FeiShuTemplate.setProcessList(Arrays.asList(
                feiShuUserIdCheck, feiShuParamCheck, feiShuSend
        ));
        map.put(PretreatmentCodeConstant.USERID_FEI_SHU, userId2FeiShuTemplate);

        return new LinkHandler().setTemplateConfig(map);
    }
}

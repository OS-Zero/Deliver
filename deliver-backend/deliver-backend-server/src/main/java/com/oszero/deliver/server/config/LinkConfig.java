package com.oszero.deliver.server.config;

import com.oszero.deliver.server.pretreatment.constant.PretreatmentCodeConstant;
import com.oszero.deliver.server.pretreatment.link.LinkHandler;
import com.oszero.deliver.server.pretreatment.link.LinkTemplate;
import com.oszero.deliver.server.pretreatment.link.convert.CompanyAccountConvert;
import com.oszero.deliver.server.pretreatment.link.convert.Phone2UserIdConvert;
import com.oszero.deliver.server.pretreatment.link.idcheck.*;
import com.oszero.deliver.server.pretreatment.link.paramcheck.ding.DingCommonParamCheck;
import com.oszero.deliver.server.pretreatment.link.paramcheck.ding.DingParamCheck;
import com.oszero.deliver.server.pretreatment.link.paramcheck.feishu.FeiShuCommonParamCheck;
import com.oszero.deliver.server.pretreatment.link.paramcheck.feishu.FeiShuParamCheck;
import com.oszero.deliver.server.pretreatment.link.paramcheck.mail.MailParamCheck;
import com.oszero.deliver.server.pretreatment.link.paramcheck.phone.PhoneParamCheck;
import com.oszero.deliver.server.pretreatment.link.paramcheck.sms.SmsParamCheck;
import com.oszero.deliver.server.pretreatment.link.paramcheck.wechat.WeChatCommonParamCheck;
import com.oszero.deliver.server.pretreatment.link.paramcheck.wechat.WeChatParamCheck;
import com.oszero.deliver.server.pretreatment.link.pushrangecheck.PushRangeCheck;
import com.oszero.deliver.server.pretreatment.link.send.*;
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
     * 推送范围检查
     */
    private final PushRangeCheck pushRangeCheck;

    /**
     * 通用参数检查
     */
    private final DingCommonParamCheck dingCommonParamCheck;
    private final WeChatCommonParamCheck weChatCommonParamCheck;
    private final FeiShuCommonParamCheck feiShuCommonParamCheck;

    /**
     * idCheck 发送人检查
     */
    private final CompanyAccountCheck companyAccountCheck;
    private final MailCheck mailCheck;
    private final PhoneCheck phoneCheck;
    private final DingUserIdCheck dingUserIdCheck;
    private final WeChatUserIdCheck weChatUserIdCheck;
    private final FeiShuUserIdCheck feiShuUserIdCheck;

    /**
     * convert 转换
     */
    private final CompanyAccountConvert companyAccountConvert;
    private final Phone2UserIdConvert phone2UserIdConvert;

    /**
     * paramCheck 参数校验
     */
    private final PhoneParamCheck phoneParamCheck;
    private final SmsParamCheck smsParamCheck;
    private final MailParamCheck mailParamCheck;
    private final DingParamCheck dingParamCheck;
    private final WeChatParamCheck weChatParamCheck;
    private final FeiShuParamCheck feiShuParamCheck;

    /**
     * send 渠道发送
     */
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

        // 企业账号配置
        companyAccountUsersType(map);
        // 电话
        phoneUsersType(map);
        // 邮箱
        mailUsersType(map);
        // 平台 ID
        userIdUsersType(map);

        return new LinkHandler().setTemplateConfig(map);
    }

    /**
     * 企业账号配置
     *
     * @param map 配置 map
     */
    private void companyAccountUsersType(HashMap<String, LinkTemplate> map) {
        // 企业账号-打电话
        LinkTemplate companyAccount2CallTemplate = new LinkTemplate();
        companyAccount2CallTemplate.setProcessList(Arrays.asList(
                pushRangeCheck, companyAccountCheck, companyAccountConvert, phoneCheck, phoneParamCheck, callSend
        ));
        map.put(PretreatmentCodeConstant.COMPANY_ACCOUNT_CALL, companyAccount2CallTemplate);

        // 企业账号-短信
        LinkTemplate companyAccount2SmsTemplate = new LinkTemplate();
        companyAccount2SmsTemplate.setProcessList(Arrays.asList(
                pushRangeCheck, companyAccountCheck, companyAccountConvert, phoneCheck, smsParamCheck, smsSend
        ));
        map.put(PretreatmentCodeConstant.COMPANY_ACCOUNT_SMS, companyAccount2SmsTemplate);

        // 企业账号-邮件
        LinkTemplate companyAccount2MailTemplate = new LinkTemplate();
        companyAccount2MailTemplate.setProcessList(Arrays.asList(
                pushRangeCheck, companyAccountCheck, companyAccountConvert, mailCheck, mailParamCheck, mailSend
        ));
        map.put(PretreatmentCodeConstant.COMPANY_ACCOUNT_MAIL, companyAccount2MailTemplate);

        // 企业账号-钉钉
        LinkTemplate companyAccount2DingTemplate = new LinkTemplate();
        companyAccount2DingTemplate.setProcessList(Arrays.asList(
                pushRangeCheck, dingCommonParamCheck, companyAccountCheck, companyAccountConvert, phoneCheck, phone2UserIdConvert, dingParamCheck, dingSend
        ));
        map.put(PretreatmentCodeConstant.COMPANY_ACCOUNT_DING, companyAccount2DingTemplate);

        // 企业账号-企业微信
        LinkTemplate companyAccount2WeChatTemplate = new LinkTemplate();
        companyAccount2WeChatTemplate.setProcessList(Arrays.asList(
                pushRangeCheck, weChatCommonParamCheck, companyAccountCheck, companyAccountConvert, phoneCheck, phone2UserIdConvert, weChatParamCheck, weChatSend
        ));
        map.put(PretreatmentCodeConstant.COMPANY_ACCOUNT_WECHAT, companyAccount2WeChatTemplate);

        // 企业账号-飞书
        LinkTemplate companyAccount2FeiShuTemplate = new LinkTemplate();
        companyAccount2FeiShuTemplate.setProcessList(Arrays.asList(
                pushRangeCheck, feiShuCommonParamCheck, companyAccountCheck, companyAccountConvert, phoneCheck, phone2UserIdConvert, feiShuParamCheck, feiShuSend
        ));
        map.put(PretreatmentCodeConstant.COMPANY_ACCOUNT_FEI_SHU, companyAccount2FeiShuTemplate);

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
                pushRangeCheck, phoneCheck, phoneParamCheck, callSend
        ));
        map.put(PretreatmentCodeConstant.PHONE_CALL, phone2CallTemplate);

        // 电话-发短信
        LinkTemplate phone2SmsTemplate = new LinkTemplate();
        phone2SmsTemplate.setProcessList(Arrays.asList(
                pushRangeCheck, phoneCheck, smsParamCheck, smsSend
        ));
        map.put(PretreatmentCodeConstant.PHONE_SMS, phone2SmsTemplate);

        // 电话-钉钉
        LinkTemplate phone2DingTemplate = new LinkTemplate();
        phone2DingTemplate.setProcessList(Arrays.asList(
                pushRangeCheck, dingCommonParamCheck, phoneCheck, phone2UserIdConvert, dingParamCheck, dingSend
        ));
        map.put(PretreatmentCodeConstant.PHONE_DING, phone2DingTemplate);

        // 电话-企业微信
        LinkTemplate phone2WeChatTemplate = new LinkTemplate();
        phone2WeChatTemplate.setProcessList(Arrays.asList(
                pushRangeCheck, weChatCommonParamCheck, phoneCheck, phone2UserIdConvert, weChatParamCheck, weChatSend
        ));
        map.put(PretreatmentCodeConstant.PHONE_WECHAT, phone2WeChatTemplate);

        // 电话-飞书
        LinkTemplate phone2FeiShuTemplate = new LinkTemplate();
        phone2FeiShuTemplate.setProcessList(Arrays.asList(
                pushRangeCheck, feiShuCommonParamCheck, phoneCheck, phone2UserIdConvert, feiShuParamCheck, feiShuSend
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
                pushRangeCheck, mailCheck, mailParamCheck, mailSend
        ));
        map.put(PretreatmentCodeConstant.MAIL_MAIL, mail2MailTemplate);
    }

    /**
     * 平台 ID 配置
     *
     * @param map 配置 map
     */
    private void userIdUsersType(HashMap<String, LinkTemplate> map) {
        // 平台userId-钉钉
        LinkTemplate userId2DingTemplate = new LinkTemplate();
        userId2DingTemplate.setProcessList(Arrays.asList(
                pushRangeCheck, dingCommonParamCheck, dingUserIdCheck, dingParamCheck, dingSend
        ));
        map.put(PretreatmentCodeConstant.USERID_DING, userId2DingTemplate);

        // 平台userId-企业微信
        LinkTemplate userId2WeChatTemplate = new LinkTemplate();
        userId2WeChatTemplate.setProcessList(Arrays.asList(
                pushRangeCheck, weChatCommonParamCheck, weChatUserIdCheck, weChatParamCheck, weChatSend
        ));
        map.put(PretreatmentCodeConstant.USERID_WECHAT, userId2WeChatTemplate);

        // 平台userId-飞书
        LinkTemplate userId2FeiShuTemplate = new LinkTemplate();
        userId2FeiShuTemplate.setProcessList(Arrays.asList(
                pushRangeCheck, feiShuCommonParamCheck, feiShuUserIdCheck, feiShuParamCheck, feiShuSend
        ));
        map.put(PretreatmentCodeConstant.USERID_FEI_SHU, userId2FeiShuTemplate);
    }
}

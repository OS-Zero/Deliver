package com.oszero.deliver.server.config;

import com.oszero.deliver.server.pretreatment.action.idcheck.*;
import com.oszero.deliver.server.pretreatment.action.send.*;
import com.oszero.deliver.server.pretreatment.pipeline.ProcessHandler;
import com.oszero.deliver.server.pretreatment.pipeline.ProcessTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

@Configuration
@RequiredArgsConstructor
public class PipelineConfig {

    /**
     * idCheck
     */
    private final CompanyAccountCheckAction companyAccountCheckAction;
    private final DingUserIdCheckAction dingUserIdCheckAction;
    private final FeiShuUserIdCheckAction feiShuUserIdCheckAction;
    private final MailCheckAction mailCheckAction;
    private final PhoneCheckAction phoneCheckAction;
    private final WeChatUserIdCheckAction weChatUserIdCheckAction;

    /**
     * paramCheck
     */

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

        return new ProcessHandler().setTemplateConfig(map);
    }
}

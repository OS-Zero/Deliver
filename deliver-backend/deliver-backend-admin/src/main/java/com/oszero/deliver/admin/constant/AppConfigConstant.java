package com.oszero.deliver.admin.constant;

/**
 * APP 配置常量
 *
 * @author oszero
 * @version 1.0.0
 */
public interface AppConfigConstant {

    String CALL_CONFIG = "{}";

    String SMS_CONFIG = "{}";

    String MAIL_CONFIG = "{\n" +
            "  \"host\": \"xxx\",\n" +
            "  \"username\": \"xxx\",\n" +
            "  \"password\": \"xxx\",\n" +
            "  \"auth\": \"true\",\n" +
            "  \"sslEnable\": \"true\"\n" +
            "}";

    String DING_CONFIG = "{\n" +
            "  \"agentId\": 0,\n" +
            "  \"appKey\": \"\",\n" +
            "  \"appSecret\": \"\",\n" +
            " \"robotCode\": \"\"\n" +
            "}";

    String WECHAT_CONFIG = "{\n" +
            "  \"corpid\": \"\",\n" +
            "  \"corpsecret\": \"\",\n" +
            " \"agentid\": \"\"\n" +
            "}";

    String FEI_SHU_CONFIG = "{\n" +
            "  \"appId\": \"\",\n" +
            "  \"appSecret\": \"\"\n" +
            "}";

}

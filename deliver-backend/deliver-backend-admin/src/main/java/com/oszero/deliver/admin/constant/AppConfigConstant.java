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
            "  \"host\": \"\",\n" +
            "  \"username\": \"\",\n" +
            "  \"password\": \"\"\n" +
            "}";

    String DING_CONFIG = "{\n" +
            "  \"agentId\": 0,\n" +
            "  \"appKey\": \"\",\n" +
            "  \"appSecret\": \"\"\n" +
            " \"RobotCode\": \"\"\n" +
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

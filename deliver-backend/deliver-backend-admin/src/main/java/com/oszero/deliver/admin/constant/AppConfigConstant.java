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

    String MAIL_CONFIG = """
            {
              "host": "xxx",
              "username": "xxx",
              "password": "xxx",
              "auth": "true",
              "sslEnable": "true"
            }
            """;

    String DING_CONFIG = """
            {
              "agentId": 995,
              "appKey": "xxx",
              "appSecret": "xxx",
              "robotCode": "xxx"
            }
            """;

    String WECHAT_CONFIG = """
            {
              "corpid": "xxx",
              "corpsecret": "xxx",
              "agentid": "xxx"
            }
            """;

    String FEI_SHU_CONFIG = """
            {
              "appId": "xxx",
              "appSecret": "xxx"
            }
            """;

}

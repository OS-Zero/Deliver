package com.oszero.deliver.server.constant;

public interface MQConstant {
    /**
     * RocketMQ
     */
    String CALL_TOPIC = "topic_msg_call";
    String DING_TOPIC = "topic_msg_ding";
    String FEI_SHU_TOPIC = "topic_msg_feiShu";
    String MAIL_TOPIC = "topic_msg_mail";
    String SMS_TOPIC = "topic_msg_sms";
    String WECHAT_TOPIC = "topic_msg_wechat";

    String CALL_CONSUMER_GROUP = "consumer_group_msg_call";
    String DING_CONSUMER_GROUP = "consumer_group_msg_ding";
    String FEI_SHU_CONSUMER_GROUP = "consumer_group_msg_feiShu";
    String MAIL_CONSUMER_GROUP = "consumer_group_msg_mail";
    String SMS_CONSUMER_GROUP = "consumer_group_msg_sms";
    String WECHAT_CONSUMER_GROUP = "consumer_group_msg_wechat";

    /**
     * RabbitMQ
     */
    String DELIVER_EXCHANGE = "deliver_exchange";

    String CALL_QUEUE = "queue_msg_call";
    String DING_QUEUE = "queue_msg_ding";
    String FEI_SHU_QUEUE = "queue_msg_feiShu";
    String MAIL_QUEUE = "queue_msg_mail";
    String SMS_QUEUE = "queue_msg_sms";
    String WECHAT_QUEUE = "queue_msg_wechat";

    String CALL_KEY_NAME = "key_msg_call";
    String DING_KEY_NAME = "key_msg_ding";
    String FEI_SHU_KEY_NAME = "key_msg_feiShu";
    String MAIL_KEY_NAME = "key_msg_mail";
    String SMS_KEY_NAME = "key_msg_sms";
    String WECHAT_KEY_NAME = "key_msg_wechat";

    /**
     * kafka
     */
    String TOPIC = "";
}

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

package com.oszero.deliver.business.server.constant;

/**
 * @author oszero
 * @version 1.0.0
 */
public interface MQConstant {

    String MQ_TYPE = "mq-type";
    String MQ_TYPE_DISRUPTOR = "disruptor";
    String MQ_TYPE_RABBITMQ = "rabbitmq";
    String MQ_TYPE_ROCKETMQ = "rocketmq";

    /********** RocketMQ **********/
    String CALL_TOPIC = "topic_msg_call";
    String DING_TOPIC = "topic_msg_ding";
    String FEI_SHU_TOPIC = "topic_msg_feiShu";
    String MAIL_TOPIC = "topic_msg_mail";
    String SMS_TOPIC = "topic_msg_sms";
    String WECHAT_TOPIC = "topic_msg_wechat";
    String OFFICIAL_ACCOUNT_TOPIC = "topic_msg_official_account";

    String CALL_CONSUMER_GROUP = "consumer_group_msg_call";
    String DING_CONSUMER_GROUP = "consumer_group_msg_ding";
    String FEI_SHU_CONSUMER_GROUP = "consumer_group_msg_feiShu";
    String MAIL_CONSUMER_GROUP = "consumer_group_msg_mail";
    String SMS_CONSUMER_GROUP = "consumer_group_msg_sms";
    String WECHAT_CONSUMER_GROUP = "consumer_group_msg_wechat";
    String OFFICIAL_ACCOUNT_CONSUMER_GROUP = "consumer_group_msg_officialAccount";

    /********** RabbitMQ **********/
    String DELIVER_EXCHANGE = "deliver_exchange";

    String CALL_QUEUE = "queue_msg_call";
    String DING_QUEUE = "queue_msg_ding";
    String FEI_SHU_QUEUE = "queue_msg_feiShu";
    String MAIL_QUEUE = "queue_msg_mail";
    String SMS_QUEUE = "queue_msg_sms";
    String WECHAT_QUEUE = "queue_msg_wechat";
    String OFFICIAL_ACCOUNT_QUEUE = "queue_msg_officialAccount";

    String CALL_KEY_NAME = "key_msg_call";
    String DING_KEY_NAME = "key_msg_ding";
    String FEI_SHU_KEY_NAME = "key_msg_feiShu";
    String MAIL_KEY_NAME = "key_msg_mail";
    String SMS_KEY_NAME = "key_msg_sms";
    String WECHAT_KEY_NAME = "key_msg_wechat";
    String OFFICIAL_ACCOUNT_KEY_NAME = "key_msg_officialAccount";

    /********** Disruptor **********/
    int DISRUPTOR_BUFFER_SIZE = 102400;
    String DISRUPTOR_THREAD_NAME = "disruptor_thread_name";
    // Disruptor
    String CALL_DISRUPTOR = "call_disruptor";
    String SMS_DISRUPTOR = "sms_disruptor";
    String MAIL_DISRUPTOR = "mail_disruptor";
    String DING_DISRUPTOR = "ding_disruptor";
    String WECHAT_DISRUPTOR = "wechat_disruptor";
    String FEI_SHU_DISRUPTOR = "feiShu_disruptor";
    String OFFICIAL_ACCOUNT_DISRUPTOR = "officialAccount_disruptor";
}

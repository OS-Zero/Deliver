package com.oszero.deliver.server.constant;


public interface MailParamConstant {

    String CONTENT = "content";
    String SENDERS = "senders";
    String DEFAULT_SENDER = "";

    String SUBJECT = "title";
    String DEFAULT_SUBJECT = "您有一个新消息, 请注意查收";

    // 抄送人列表
    String TOCC = "toCC";
    String DEFAULT_TOCC = "[]";
    // 密送人列表
    String TOBCC = "toBCC";
    String DEFAULT_TOBCC = "[]";
}

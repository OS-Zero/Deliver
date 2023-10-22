package com.oszero.deliver.server.model.dto;

import com.oszero.deliver.server.model.app.AppConfig;
import com.oszero.deliver.server.pretreatment.link.LinkModel;
import lombok.*;

import javax.security.auth.login.AppConfigurationEntry;
import java.util.List;
import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SendTaskDto extends LinkModel {

    /**
     * 消息模板 Id
     */
    private Long templateId;
    /**
     * 发送用户列表
     */
    private List<String> users;

    /**
     * 不同消息的不同参数
     */
    private Map<String, Object> paramMap;

    /**
     * 转换后具体的参数
     */
    private Object param;

    private Long appId;
    private AppConfig appConfig;
    private String appConfigJson;
    private Integer pushRange;
    private Integer usersType;
    private Integer channelType;
    private String messageType;
    private Integer retry;

}



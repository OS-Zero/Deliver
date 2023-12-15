package com.oszero.deliver.server.model.messageparam.ding;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 钉钉消息内容通用类
 *
 * @author oszero
 * @version 1.0.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DingMessageParam {

    private Long agentId;
    private String useridList;
}

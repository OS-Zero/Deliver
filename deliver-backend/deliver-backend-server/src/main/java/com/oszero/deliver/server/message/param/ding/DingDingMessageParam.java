package com.oszero.deliver.server.message.param.ding;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author  oszero
 * @Date 2023/10/25
 * @Description
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DingDingMessageParam {

    private Long agentId;
    private String useridList;
}

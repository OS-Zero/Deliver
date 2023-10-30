package com.oszero.deliver.server.message.param.feishu;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 飞书通用消息内容
 *
 * @author oszero
 * @version 1.0.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeiShuMessageParam {
    private String msg_type;
    private List<String> user_ids;
}

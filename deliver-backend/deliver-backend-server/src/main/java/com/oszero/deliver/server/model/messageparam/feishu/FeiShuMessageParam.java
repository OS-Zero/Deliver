package com.oszero.deliver.server.model.messageparam.feishu;

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
    protected String msg_type;
    protected List<String> user_ids;
}

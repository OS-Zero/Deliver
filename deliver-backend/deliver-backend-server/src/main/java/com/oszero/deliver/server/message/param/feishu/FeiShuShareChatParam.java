package com.oszero.deliver.server.message.param.feishu;

import lombok.*;

/**
 * 飞书分享群消息内容
 *
 * @author oszero
 * @version 1.0.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class FeiShuShareChatParam extends FeiShuMessageParam {

    private Content content;

    @Data
    public static class Content {
        private String chat_id;
    }
}

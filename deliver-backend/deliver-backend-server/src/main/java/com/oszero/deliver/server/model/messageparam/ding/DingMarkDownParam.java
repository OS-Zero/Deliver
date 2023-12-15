package com.oszero.deliver.server.model.messageparam.ding;

import lombok.*;

/**
 * 钉钉MarkDown消息内容
 *
 * @author oszero
 * @version 1.0.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class DingMarkDownParam extends DingMessageParam {
    private MarkDownMessage msg;

    @Data
    public static class MarkDownMessage {
        private String msgtype;
        private MarkDown markdown;
    }

    @Data
    public static class MarkDown {
        private String title;
        private String text;


    }

}
